package com.remondis.cdc.consumer.pactbuilder;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.remondis.cdc.consumer.pactbuilder.types.TypeMappings;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class ConsumerBuilderImpl<T> implements ConsumerBuilder<T> {

  private Class<T> type;

  private Map<Class<?>, PactDslModifier<?>> basicTypeMappings;

  private Map<Class<?>, ConsumerBuilder<?>> consumerReferences;
  private Map<PropertyDescriptor, BiFunction<PactDslJsonBody, Object, PactDslJsonBody>> propertyMap;

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
  public PactDslJsonBody build(PactDslJsonBody pactDslJsonBody, T sampleData) {
    // Step 1: Expand property map with implicit conversions.
    Set<BiFunction<PactDslJsonBody, Object, PactDslJsonBody>> expandedPropertyMap = expandPropertyMap();

    Iterator<BiFunction<PactDslJsonBody, Object, PactDslJsonBody>> it = expandedPropertyMap.iterator();
    while (it.hasNext()) {
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = it.next();
      pactDslJsonBody = modifier.apply(pactDslJsonBody, sampleData);
    }
    return pactDslJsonBody;
  }

  private Set<BiFunction<PactDslJsonBody, Object, PactDslJsonBody>> expandPropertyMap() {
    // Generate the implicit default mappings.
    Set<BiFunction<PactDslJsonBody, Object, PactDslJsonBody>> collect = Properties.getProperties(type)
        .stream()
        .filter(((Predicate<PropertyDescriptor>) propertyMap::containsKey).negate())
        .map(pd -> {
          return getModifier(pd, null);
        })
        .collect(Collectors.toSet());

    // Add the custom field mappings.
    collect.addAll(propertyMap.entrySet()
        .stream()
        .map(Entry::getValue)
        .collect(Collectors.toList()));

    return collect;
  }

  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getModifier(PropertyDescriptor pd,
      String optionalFieldName) {
    Class<?> unwrappedType = ReflectionUtil.getTypeOrListType(pd);

    String fieldName = propertyNameOrOverride(pd, optionalFieldName);

    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = null;

    // Priorities:
    // 1. Field settings (filtered by caller of this method)
    // 2. Consumer references
    // 3. Basic type mappings.
    boolean isReference = consumerReferences.containsKey(unwrappedType);
    boolean isDefaultDatatype = basicTypeMappings.containsKey(unwrappedType);
    boolean isEnum = (Enum.class.isAssignableFrom(unwrappedType));

    if (isEnum) {
      modifier = getEnumModifier(pd, fieldName);
    } else if (isReference) {
      ConsumerBuilder<?> consumerBuilder = consumerReferences.get(unwrappedType);
      modifier = getEmbeddedObjectModifier(fieldName, consumerBuilder);
    } else if (isDefaultDatatype) {
      modifier = getDefaultDatatypeModifier(unwrappedType, fieldName);
    } else {
      // Method readMethod = pd.getReadMethod();
      // throw new ConsumerBuilderException("Could not find configuration for " + readMethod.getDeclaringClass()
      // .getName() + "." + readMethod.getName() + "() and type " + propertyType.getName());
      try {
        isReference = true;
        modifier = getEmbeddedObjectModifier(fieldName,
            new ConsumerBuilderImpl<>(unwrappedType, basicTypeMappings, consumerReferences));
      } catch (NotAJavaBeanException e) {
        String field = pd.getReadMethod()
            .getDeclaringClass()
            .getSimpleName() + "."
            + pd.getReadMethod()
                .getName()
            + "()";
        throw new NotAJavaBeanException("The field " + field + " with type " + unwrappedType.getSimpleName()
            + " cannot be created with the current configuration.\nPlease add type mapping for "
            + unwrappedType.getSimpleName() + ".");
      }
    }

    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> valueSupplierWrapper = wrapInValueExtractorWithCollectionSupport(
        pd, fieldName, modifier, isReference);

    return valueSupplierWrapper;
  }

  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> wrapInValueExtractorWithCollectionSupport(
      PropertyDescriptor pd, String fieldName, BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier,
      boolean isReference) {
    // If the field is a collection, wrap the modifier in an array modifier.
    if (ReflectionUtil.isCollection(pd.getPropertyType())) {
      modifier = getArrayModifier(fieldName, modifier);
    } else if (isReference) {
      modifier = getObjectReferenceModifier(fieldName, modifier);
    }

    // Wrap in value extractor.
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> valueSupplierWrapper = wrapInValueExtractor(pd, modifier);
    return valueSupplierWrapper;
  }

  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getObjectReferenceModifier(String fieldName,
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier) {
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody = pactDslJsonBody.object(fieldName);
      if (fieldName.equals("determinationTime")) {
        System.out.println("Ik habben!");
      }
      pactDslJsonBody = modifier.apply(pactDslJsonBody, sampleValue);
      pactDslJsonBody = (PactDslJsonBody) pactDslJsonBody.closeObject();
      return pactDslJsonBody;
    };
  }

  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> wrapInValueExtractor(PropertyDescriptor pd,
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier) {
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> valueSupplierWrapper = (pactDslJsonBody, sampleValue) -> {
      Object value = readOrFail(pd, sampleValue);
      return modifier.apply(pactDslJsonBody, value);
    };
    return valueSupplierWrapper;
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getEnumModifier(PropertyDescriptor pd,
      String optionalFieldName) {
    PactDslModifier pactDslModifier = basicTypeMappings.get(String.class);
    return (pactDslJsonBody, sampleValue) -> {
      return pactDslModifier.apply(pactDslJsonBody, optionalFieldName, sampleValue.toString());
    };
  }

  @SuppressWarnings({
      "rawtypes"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getArrayModifier(String fieldName,
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier) {
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody = pactDslJsonBody.minArrayLike(fieldName, 1);
      Collection collection = (Collection) sampleValue;
      Iterator it = collection.iterator();
      while (it.hasNext()) {
        Object sampleValueItem = it.next();
        pactDslJsonBody = modifier.apply(pactDslJsonBody, sampleValueItem);
      }
      return (PactDslJsonBody) pactDslJsonBody.closeArray();
    };
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getEmbeddedObjectModifier(String fieldName,
      ConsumerBuilder consumerBuilder) {
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody = consumerBuilder.build(pactDslJsonBody, sampleValue);
      return pactDslJsonBody;
    };
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getDefaultDatatypeModifier(Class<?> propertyType,
      String fieldName) {
    return (pactDslJsonBody, sampleValue) -> {
      PactDslModifier pactDslModifier = basicTypeMappings.get(propertyType);
      return pactDslModifier.apply(pactDslJsonBody, fieldName, sampleValue);
    };
  }

  void addFieldCustomNameAndConsumerBuilder(PropertyDescriptor pd, String jsonFieldName,
      ConsumerBuilder<?> anotherConsumer) {
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = getEmbeddedObjectModifier(jsonFieldName,
        anotherConsumer);
    modifier = wrapInValueExtractorWithCollectionSupport(pd, jsonFieldName, modifier, true);
    propertyMap.put(pd, modifier);
  }

  void addFieldWithCustomName(PropertyDescriptor pd, String fieldName) {
    requireNonNull(fieldName, "Field name must not be null!");
    propertyMap.put(pd, getModifier(pd, fieldName));
  }

  void addFieldWithCustomConsumer(PropertyDescriptor pd,
      Function<PactDslJsonBody, ? extends DslPart> pactDslJsonBodyConfigurator) {
    Class<?> unwrappedType = ReflectionUtil.getTypeOrListType(pd);
    boolean isReference = consumerReferences.containsKey(unwrappedType);
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = (pactDslJsonBody,
        sampleValue) -> (PactDslJsonBody) pactDslJsonBodyConfigurator.apply(pactDslJsonBody);
    modifier = wrapInValueExtractorWithCollectionSupport(pd, pd.getName(), modifier, isReference);
    propertyMap.put(pd, modifier);
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
