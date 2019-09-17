package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * This is the builder used to configure the structure of JSON structures used in Pact consumer tests.
 *
 * @param <T> The type of object that's JSON structure is to be defined.
 */
public interface ConsumerBuilder<T> {

  /**
   * Registers a global type mapping. Each occurrence of the specified type will be converted to a JSON structure by
   * applying the specified modifier.
   * <p>
   * <b>Note:</b> The specified {@link PactDslModifier} is responsible to define the right
   * structures for the JSON
   * representation. For example: If an object should be declared, the {@link PactDslModifier} must invoke
   * {@link PactDslJsonBody#object(String)} and {@link PactDslJsonBody#closeObject()}.
   * </p>
   *
   * @param <S> The type to convert.
   * @param type The type to convert.
   * @param modifier The {@link PactDslModifier} that creates the JSON structures.
   * @return Returns this instance for method chaining.
   */
  <S> ConsumerBuilder<T> useTypeMapping(Class<S> type, PactDslModifier<S> modifier);

  /**
   * Overrides the default behavior for arrays. Per default, the {@link MinArrayLikeModifier} is used. To change the
   * global behavior use this method. <b>If you want to change the array mapping per field, use
   * {@link #field(TypedSelector)} and specify a custom field mapping instead.</b>
   *
   * @param customArrayModifier The custom implementation of {@link PactDslArrayModifier}.
   * @return Returns this instance for method chaining.
   */
  ConsumerBuilder<T> useArrayMapping(PactDslArrayModifier customArrayModifier);

  /**
   * Selects a field to specify a JSON mapping. Global type mappings added by
   * {@link #useTypeMapping(Class, PactDslModifier)}, {@link #referencing(ConsumerBuilder)} or default type mappings
   * will be overridden by field configurations.
   *
   * @param <R> The field type.
   * @param fieldSelector A lambda that performs a get-call on the target object, to select a field.
   * @return Returns this instance for method chaining.
   */
  <R> FieldBuilder<R, T> field(TypedSelector<R, T> fieldSelector);

  /**
   * Registers another {@link ConsumerBuilder} that specifies how a type should be mapped to a JSON structure.
   *
   * @param consumer The {@link ConsumerBuilder} instance specifying the JSON structure of a type.
   * @return Returns this instance for method chaining.
   */
  ConsumerBuilder<T> referencing(ConsumerBuilder<?> consumer);

  /**
   * Takes the specified {@link PactDslJsonBody} instance and creates the JSON structure.
   *
   * @param pactDslJsonBody The {@link PactDslJsonBody} to build.
   * @param sampleData The sample data instance that provides values for each field of the type.
   * @return Returns a {@link PactDslJsonBody} representing the JSON structure of the type, this {@link ConsumerBuilder}
   *         was configured for.
   */
  PactDslJsonBody build(PactDslJsonBody pactDslJsonBody, T sampleData);

  /**
   * Creates a new {@link PactDslJsonBody} and generates the JSON structure.
   *
   * @param sampleData The sample data instance that provides values for each field of the type.
   * @return Returns a {@link PactDslJsonBody} representing the JSON structure of the type, this {@link ConsumerBuilder}
   *         was configured for.
   */
  PactDslJsonBody build(T sampleData);

  /**
   * @return Returns the type this {@link ConsumerBuilder} is using.
   */
  Class<T> getType();

}
