package com.remondis.cdc.consumer.pactbuilder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
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

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.BeanUtil;

public class ConsumerBuilderImpl<T> implements ConsumerBuilder<T> {

  private Class<T> type;

  private Map<Class<?>, PactDslModifier<?>> basicTypeMappings;

  private Map<Class<?>, ConsumerBuilder<?>> consumerReferences;
  private Map<PropertyDescriptor, BiFunction<PactDslJsonBody, Object, PactDslJsonBody>> propertyMap;

  /**
   * The default array modifier.
   */
  private PactDslArrayModifier arrayModifier = new MinArrayLikeModifier();

  /**
   * Ignore missing values when using {@link #wrapInValueExtractor(PropertyDescriptor, BiFunction)}.
   * This makes it possible to pass along a class that defines more fields than the actually received DTO.
   * Only the fields that have a value in the sample will end up in the Pact.
   */
  private boolean ignoreMissingValues = false;

  /**
   * Enforce a correct property naming convention.
   * This way property names that start with uppercase characters, are converted to use correct camel casing.
   */
  private boolean enforcePropertyNamingConvention = false;

  ConsumerBuilderImpl(Class<T> type) {
    super();
    denyNoJavaBean(type);
    this.type = type;
    this.consumerReferences = new Hashtable<>();
    this.basicTypeMappings = TypeMappings.getDatatypeMappings();
    this.propertyMap = new Hashtable<>();
  }

  private ConsumerBuilderImpl(Class<T> type, Map<Class<?>, PactDslModifier<?>> basicTypeMappings,
      Map<Class<?>, ConsumerBuilder<?>> consumerReferences, boolean ignoreMissingValues,
      boolean enforcePropertyNamingConvention) throws NotAJavaBeanException {
    super();
    this.type = type;
    denyNoJavaBean(type);
    this.basicTypeMappings = basicTypeMappings;
    this.consumerReferences = consumerReferences;
    this.ignoreMissingValues = ignoreMissingValues;
    this.enforcePropertyNamingConvention = enforcePropertyNamingConvention;
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
  public PactDslJsonBody build(T sampleData) {
    return build(new PactDslJsonBody(), sampleData);
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

  @Override
  public ConsumerBuilder<T> ignoreMissingValues() {
    this.ignoreMissingValues = true;
    return this;
  }

  @Override
  public ConsumerBuilder<T> enforcePropertyNamingConvention() {
    this.enforcePropertyNamingConvention = true;
    return this;
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

  @SuppressWarnings("rawtypes")
  BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getCollectionModifier(String field, Class unwrappedType,
      String fieldName) {
    boolean isPossibleGenericType = unwrappedType == Object.class;
    boolean isReference = consumerReferences.containsKey(unwrappedType);
    boolean isDefaultDatatype = basicTypeMappings.containsKey(unwrappedType);
    boolean isEnum = (Enum.class.isAssignableFrom(unwrappedType));

    if (isPossibleGenericType) {
      return (pactDslJsonBody, sampleValue) -> {
        Collection collection = (Collection) sampleValue;
        Object itemValue = collection.iterator()
            .next();
        Class itemType = itemValue.getClass();
        return getCollectionModifier(field, itemType, fieldName).apply(pactDslJsonBody, collection);
      };
    } else if (isEnum) {
      return (pactDslJsonBody, sampleValue) -> {
        Collection collection = (Collection) sampleValue;
        return arrayModifier.createRootValueArray(pactDslJsonBody, fieldName,
            PactDslJsonRootValue.stringType(collection.iterator()
                .next()
                .toString()));
      };
    } else if (isReference) {
      ConsumerBuilder<?> consumerBuilder = consumerReferences.get(unwrappedType);
      return getComplexObjectArrayModifier(fieldName, getEmbeddedObjectModifier(fieldName, consumerBuilder));
    } else if (isDefaultDatatype) {
      PactDslModifier pactDslModifier = basicTypeMappings.get(unwrappedType);
      if (pactDslModifier instanceof PactDslRootValueModifier) {
        PactDslRootValueModifier rootValueModifier = (PactDslRootValueModifier) pactDslModifier;
        return getRootValueArrayModifier(fieldName, rootValueModifier);
      } else {
        return getComplexObjectArrayModifier(fieldName, getDefaultDatatypeModifier(unwrappedType, fieldName));
      }
    } else {
      return getComplexObjectArrayModifier(fieldName,
          getEmbeddedObjectModifier(fieldName, new ConsumerBuilderImpl<>(unwrappedType, basicTypeMappings,
              consumerReferences, ignoreMissingValues, enforcePropertyNamingConvention)));
    }
  }

  @SuppressWarnings("rawtypes")
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getModifier(PropertyDescriptor pd,
      String optionalFieldName) {
    String field = pd.getReadMethod()
        .getDeclaringClass()
        .getSimpleName() + "."
        + pd.getReadMethod()
            .getName()
        + "()";
    String fieldName = propertyNameOrOverride(pd, optionalFieldName);

    Class propertyType = pd.getPropertyType();
    Class unwrappedType = ReflectionUtil.getTypeOrListType(pd);

    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = null;

    // Priorities:
    // 1. Field settings (filtered by caller of this method)
    // 2. Consumer references
    // 3. Basic type mappings.
    modifier = _getMethod(field, fieldName, propertyType, unwrappedType);

    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> valueSupplierWrapper = wrapInValueExtractor(pd, modifier);

    return valueSupplierWrapper;
  }

  @SuppressWarnings("rawtypes")
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> _getMethod(String field, String fieldName,
      Class propertyType, Class unwrappedType) {

    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = null;

    boolean isCollection = ReflectionUtil.isCollection(propertyType);
    boolean isPossibleGenericType = unwrappedType == Object.class;
    boolean isReference = consumerReferences.containsKey(unwrappedType);
    boolean isDefaultDatatype = basicTypeMappings.containsKey(unwrappedType);
    boolean isEnum = (Enum.class.isAssignableFrom(unwrappedType));

    if (isCollection) {
      modifier = getCollectionModifier(field, unwrappedType, fieldName);
    } else {
      if (isEnum) {
        modifier = getEnumModifier(fieldName);
      } else if (isReference) {
        ConsumerBuilder<?> consumerBuilder = consumerReferences.get(unwrappedType);
        modifier = getObjectReferenceModifier(fieldName, getEmbeddedObjectModifier(fieldName, consumerBuilder));
      } else if (isDefaultDatatype) {
        modifier = getDefaultDatatypeModifier(unwrappedType, fieldName);
      } else {
        try {
          if (isPossibleGenericType) {
            modifier = (pactDslJsonBody, sampleValue) -> {
              Class itemType = sampleValue.getClass();
              return _getMethod(field, fieldName, itemType, itemType).apply(pactDslJsonBody, sampleValue);
            };
          } else {
            modifier = getObjectReferenceModifier(fieldName,
                getEmbeddedObjectModifier(fieldName, new ConsumerBuilderImpl<>(unwrappedType, basicTypeMappings,
                    consumerReferences, ignoreMissingValues, enforcePropertyNamingConvention)));
          }
        } catch (NotAJavaBeanException e) {
          throw new NotAJavaBeanException("The field " + field + " with type " + unwrappedType.getSimpleName()
              + " cannot be created with the current configuration.\nPlease add type mapping for "
              + unwrappedType.getSimpleName() + ".");
        }
      }
    }
    return modifier;
  }

  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getObjectReferenceModifier(String fieldName,
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier) {
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody = pactDslJsonBody.object(fieldName);
      pactDslJsonBody = modifier.apply(pactDslJsonBody, sampleValue);
      pactDslJsonBody = (PactDslJsonBody) pactDslJsonBody.closeObject();
      return pactDslJsonBody;
    };
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getRootValueArrayModifier(String fieldName,
      PactDslRootValueModifier rootValueModifier) {
    return (pactDslJsonBody, sampleValue) -> {
      Collection sampleCollection = (Collection) sampleValue;
      return this.arrayModifier.createRootValueArray(pactDslJsonBody, fieldName,
          rootValueModifier.asRootValue(sampleCollection.iterator()
              .next()));
    };
  }

  @SuppressWarnings({
      "rawtypes"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getComplexObjectArrayModifier(String fieldName,
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier) {
    return (pactDslJsonBody, sampleValue) -> {
      pactDslJsonBody = this.arrayModifier.startArray(pactDslJsonBody, fieldName);
      Collection collection = (Collection) sampleValue;
      Iterator it = collection.iterator();
      while (it.hasNext()) {
        Object sampleValueItem = it.next();
        pactDslJsonBody = modifier.apply(pactDslJsonBody, sampleValueItem);
      }
      return (PactDslJsonBody) this.arrayModifier.closeArray(pactDslJsonBody);
    };
  }

  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> wrapInValueExtractor(PropertyDescriptor pd,
      BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier) {
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> valueSupplierWrapper = (pactDslJsonBody, sampleValue) -> {
      try {
        Object value = readOrFail(pd, sampleValue);
        return modifier.apply(pactDslJsonBody, value);
      } catch (NoSampleValueException e) {
        if (ignoreMissingValues) {
          return pactDslJsonBody;
        } else {
          throw e;
        }
      }
    };
    return valueSupplierWrapper;
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private BiFunction<PactDslJsonBody, Object, PactDslJsonBody> getEnumModifier(String optionalFieldName) {
    PactDslModifier pactDslModifier = basicTypeMappings.get(String.class);
    return (pactDslJsonBody, sampleValue) -> {
      return pactDslModifier.apply(pactDslJsonBody, optionalFieldName, sampleValue.toString());
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
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = null;
    if (ReflectionUtil.isCollection(pd.getPropertyType())) {
      modifier = getComplexObjectArrayModifier(jsonFieldName,
          getEmbeddedObjectModifier(jsonFieldName, anotherConsumer));
    } else {
      modifier = getObjectReferenceModifier(jsonFieldName, getEmbeddedObjectModifier(jsonFieldName, anotherConsumer));
    }
    modifier = wrapInValueExtractor(pd, modifier);
    propertyMap.put(pd, modifier);
  }

  void addFieldWithCustomName(PropertyDescriptor pd, String fieldName) {
    requireNonNull(fieldName, "Field name must not be null!");
    propertyMap.put(pd, getModifier(pd, fieldName));
  }

  void addFieldWithCustomConsumer(PropertyDescriptor pd,
      Function<PactDslJsonBody, ? extends DslPart> pactDslJsonBodyConfigurator) {
    BiFunction<PactDslJsonBody, Object, PactDslJsonBody> modifier = (pactDslJsonBody,
        sampleValue) -> (PactDslJsonBody) pactDslJsonBodyConfigurator.apply(pactDslJsonBody);
    modifier = wrapInValueExtractor(pd, modifier);
    propertyMap.put(pd, modifier);
  }

  void addReference(Class<?> type, ConsumerBuilder<?> consumer) {
    requireNonNull(type, "Type must not be null!");
    requireNonNull(consumer, "Consumer must not be null!");
    consumerReferences.put(type, consumer);
  }

  private String propertyNameOrOverride(PropertyDescriptor pd, String optionalFieldName) {
    // If an optionalFieldName has been passed along, use that one
    if (nonNull(optionalFieldName)) {
      return optionalFieldName;
    }
    // If enforcePropertyNamingConvention is set, then enforce a correct naming convention (don't start with uppercase)
    if (enforcePropertyNamingConvention && pd.getReadMethod() != null) {
      return BeanUtil.okNameForGetter(new AnnotatedMethod(null, pd.getReadMethod(), null, null), false);
    }
    // By default, just return the name of the PropertyDescriptor
    return pd.getName();
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  protected Object readOrFail(PropertyDescriptor property, Object source) {
    Object returnValue = null;
    Method readMethod = property.getReadMethod();
    try {
      readMethod.setAccessible(true);
      returnValue = readMethod.invoke(source);
    } catch (InvocationTargetException e) {
      throw ReflectionException.invocationTarget(property, e);
    } catch (Exception e) {
      throw ReflectionException.invocationFailed(property, e);
    }
    if (isNull(returnValue)) {
      throw new NoSampleValueException(
          "A property of the specified sample data object was null. The following get method returned null: "
              + readMethod.toGenericString());
    }
    return returnValue;
  }

  @Override
  public <S> ConsumerBuilder<T> useTypeMapping(Class<S> type, PactDslModifier<S> modifier) {
    requireNonNull(type, "type must not be null!");
    requireNonNull(modifier, "modifier must not be null!");
    this.basicTypeMappings.put(type, modifier);
    return this;
  }

  @Override
  public ConsumerBuilder<T> useArrayMapping(PactDslArrayModifier customArrayModifier) {
    requireNonNull(customArrayModifier, "customArrayModifier must not be null!");
    this.arrayModifier = customArrayModifier;
    return this;
  }

  private PropertyDescriptor getPropertyFromSelector(TypedSelector<?, T> fieldSelector) {
    InvocationSensor<T> sensor = new InvocationSensor<>(type);
    T sensorObj = sensor.getSensor();
    fieldSelector.selectField(sensorObj);
    return sensor.getProperty();
  }
}
