package com.remondis.cdc.consumer.pactbuilder;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.remondis.cdc.consumer.pactbuilder.types.TypeMappings;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class ConsumerBuilderImpl<T> implements ConsumerBuilder<T> {

  private Class<T> type;

  private Map<Class<?>, PactDslModifier<?>> basicTypeMappings;

  private Map<Class<?>, ConsumerBuilder<?>> consumerReferences;
  private Map<PropertyDescriptor, BiConsumer<PactDslJsonBody, Object>> propertyMap;

  ConsumerBuilderImpl(Class<T> type) {
    super();
    denyNoJavaBean(type);
    this.type = type;
    this.consumerReferences = new Hashtable<>();
    this.basicTypeMappings = TypeMappings.getDatatypeMappings();
    this.propertyMap = new Hashtable<>();
  }

  private ConsumerBuilderImpl(Class<T> type, Map<Class<?>, PactDslModifier<?>> basicTypeMappings,
      Map<Class<?>, ConsumerBuilder<?>> consumerReferences) throws NotAJavaBeanException {
    super();
    this.type = type;
    denyNoJavaBean(type);
    this.basicTypeMappings = basicTypeMappings;
    this.consumerReferences = consumerReferences;
    this.propertyMap = new Hashtable<>();
  }

  private void denyNoJavaBean(Class<T> type) throws NotAJavaBeanException {
    if (!ReflectionUtil.isBean(type)) {
      throw new NotAJavaBeanException(
          "Only Java Beans are allowed for consumer builder - " + type.getName() + " is not a valid Java Bean.");
    }
  }

  @Override
  public <R> FieldBuilder<R, T> field(TypedSelector<R, T> fieldSelector) {
    requireNonNull(fieldSelector, "Field selector must not be null!");
    PropertyDescriptor pd = getPropertyFromSelector(fieldSelector);
    return new FieldBuilderImpl<R, T>(this, pd);
  }

  @Override
  public ConsumerBuilder<T> referencing(ConsumerBuilder<?> consumerBuilder) {
    requireNonNull(consumerBuilder, "ConsumerBuilder must not be null!");
    addReference(consumerBuilder.getType(), consumerBuilder);
    return this;
  }

  @Override
  public void build(PactDslJsonBody pactDslJsonBody, T sampleData) {
    // Step 1: Expand property map with implicit conversions.
    Set<BiConsumer<PactDslJsonBody, Object>> expandedPropertyMap = expandPropertyMap();
    expandedPropertyMap.stream()
        .forEach(modifier -> modifier.accept(pactDslJsonBody, sampleData));
  }

  private Set<BiConsumer<PactDslJsonBody, Object>> expandPropertyMap() {
    return Properties.getProperties(type)
        .stream()
        .filter(((Predicate<PropertyDescriptor>) propertyMap::containsKey).negate())
        .map(pd -> {
          return getModifier(pd, null);
        })
        .collect(Collectors.toSet());
  }

  private BiConsumer<PactDslJsonBody, Object> getModifier(PropertyDescriptor pd, String optionalFieldName) {
    Class<?> propertyType = ReflectionUtil.getTypeOrListType(pd);
    BiConsumer<PactDslJsonBody, Object> modifier = null;

    // Priorities:
    // 1. Field settings (filtered by caller of this method)
    // 2. Consumer references
    // 3. Basic type mappings.
    boolean isConsumerReference = consumerReferences.containsKey(propertyType);
    boolean isDefaultDatatype = basicTypeMappings.containsKey(propertyType);
    boolean isEnum = (Enum.class.isAssignableFrom(propertyType));

    if (isEnum) {
      modifier = getEnumModifier(pd, optionalFieldName);
    } else if (isConsumerReference) {
      ConsumerBuilder<?> consumerBuilder = consumerReferences.get(propertyType);
      modifier = getReferenceModifier(pd, optionalFieldName, consumerBuilder);
    } else if (isDefaultDatatype) {
      modifier = getDefaultDatatypeModifier(pd, optionalFieldName);
    } else {
      // Method readMethod = pd.getReadMethod();
      // throw new ConsumerBuilderException("Could not find configuration for " + readMethod.getDeclaringClass()
      // .getName() + "." + readMethod.getName() + "() and type " + propertyType.getName());
      try {
        modifier = getReferenceModifier(pd, optionalFieldName,
            new ConsumerBuilderImpl<>(propertyType, basicTypeMappings, consumerReferences));
      } catch (NotAJavaBeanException e) {
        String field = pd.getReadMethod()
            .getDeclaringClass()
            .getSimpleName() + "."
            + pd.getReadMethod()
                .getName()
            + "()";
        throw new NotAJavaBeanException("The field " + field + " with type " + propertyType.getSimpleName()
            + " cannot be created with the current configuration.\nPlease add type mapping for "
            + propertyType.getSimpleName() + ".");
      }
    }

    // If the field is a collection, wrap the modifier in an array modifier.
    if (ReflectionUtil.isCollection(pd.getPropertyType())) {
      modifier = getArrayModifier(pd, optionalFieldName, modifier);
    }

    return modifier;
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiConsumer<PactDslJsonBody, Object> getEnumModifier(PropertyDescriptor pd, String optionalFieldName) {
    String fieldName = propertyNameOrOverride(pd, optionalFieldName);
    PactDslModifier pactDslModifier = basicTypeMappings.get(String.class);
    return (pactDslJsonBody, sampleValue) -> {
      Object sampleFieldValue = readOrFail(pd, sampleValue);
      pactDslModifier.apply(pactDslJsonBody, fieldName, sampleFieldValue.toString());
    };
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiConsumer<PactDslJsonBody, Object> getArrayModifier(PropertyDescriptor pd, String optionalFieldName,
      BiConsumer<PactDslJsonBody, Object> modifier) {
    String fieldName = propertyNameOrOverride(pd, optionalFieldName);
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody.minArrayLike(fieldName, 1);
      Object sampleFieldValue = readOrFail(pd, sampleValue);
      Collection collection = (Collection) sampleFieldValue;
      collection.stream()
          .forEach(sampleValueItem -> {
            modifier.accept(pactDslJsonBody, sampleValueItem);
          });
      pactDslJsonBody.closeArray();
    };
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiConsumer<PactDslJsonBody, Object> getReferenceModifier(PropertyDescriptor pd, String optionalFieldName,
      ConsumerBuilder consumerBuilder) {
    String fieldName = propertyNameOrOverride(pd, optionalFieldName);
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody.object(fieldName);
      Object sampleFieldValue = readOrFail(pd, sampleValue);
      consumerBuilder.build(pactDslJsonBody, sampleFieldValue);
      pactDslJsonBody.closeObject();
    };
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiConsumer<PactDslJsonBody, Object> getDefaultDatatypeModifier(PropertyDescriptor pd,
      String optionalFieldName) {
    return (pactDslJsonBody, sampleValue) -> {
      String fieldName = propertyNameOrOverride(pd, optionalFieldName);
      Class<?> propertyType = ReflectionUtil.getTypeOrListType(pd);
      PactDslModifier pactDslModifier = basicTypeMappings.get(propertyType);
      Object sampleFieldValue = readOrFail(pd, sampleValue);
      try {
        pactDslModifier.apply(pactDslJsonBody, fieldName, sampleFieldValue);
      } catch (ClassCastException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    };
  }

  void addFieldCustomNameAndConsumerBuilder(PropertyDescriptor pd, String jsonFieldName,
      ConsumerBuilder<?> anotherConsumer) {
    BiConsumer<PactDslJsonBody, Object> referenceModifier = getReferenceModifier(pd, jsonFieldName, anotherConsumer);
    propertyMap.put(pd, referenceModifier);
  }

  void addFieldWithCustomName(PropertyDescriptor pd, String fieldName) {
    requireNonNull(fieldName, "Field name must not be null!");
    propertyMap.put(pd, getModifier(pd, fieldName));
  }

  void addFieldWithCustomConsumer(PropertyDescriptor pd, Consumer<PactDslJsonBody> pactDslJsonBodyConfigurator) {
    BiConsumer<PactDslJsonBody, Object> customConfigurator = (pactDslJsonBody,
        sampleValue) -> pactDslJsonBodyConfigurator.accept(pactDslJsonBody);
    propertyMap.put(pd, customConfigurator);
  }

  void addReference(Class<?> type, ConsumerBuilder<?> consumer) {
    requireNonNull(type, "Type must not be null!");
    requireNonNull(consumer, "Consumer must not be null!");
    consumerReferences.put(type, consumer);
  }

  private String propertyNameOrOverride(PropertyDescriptor pd, String optionalFieldName) {
    if (isNull(optionalFieldName)) {
      return pd.getName();
    } else {
      return optionalFieldName;
    }
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  protected Object readOrFail(PropertyDescriptor property, Object source) {
    try {
      Method readMethod = property.getReadMethod();
      readMethod.setAccessible(true);
      return readMethod.invoke(source);
    } catch (InvocationTargetException e) {
      throw ReflectionException.invocationTarget(property, e);
    } catch (Exception e) {
      throw ReflectionException.invocationFailed(property, e);
    }
  }

  @Override
  public <S> ConsumerBuilder<T> useTypeMapping(Class<S> type, PactDslModifier<S> modifier) {
    requireNonNull(type, "type must not be null!");
    requireNonNull(modifier, "modifier must not be null!");
    this.basicTypeMappings.put(type, modifier);
    return this;
  }

  private PropertyDescriptor getPropertyFromSelector(TypedSelector<?, T> fieldSelector) {
    InvocationSensor<T> sensor = new InvocationSensor<>(type);
    T sensorObj = sensor.getSensor();
    fieldSelector.selectField(sensorObj);
    return sensor.getProperty();
  }
}
