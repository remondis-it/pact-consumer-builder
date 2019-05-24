package com.remondis.cdc.consumer.pactbuilder;

import java.util.function.Function;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public interface FieldBuilder<R, T> {

  /**
   * Specifies a custom {@link Function} that is used to translate the specified field to a structure in the
   * {@link PactDslJsonBody}. The specified {@link Function} is responsible to define the right structures for the JSON
   * representation. For example: If an object should be declared, the {@link Function} must invoke
   * {@link PactDslJsonBody#object(String)} and {@link PactDslJsonBody#closeObject()}.
   * 
   * @param pactDslJsonBodyConfigurator The {@link Function} that takes a {@link PactDslJsonBody}, invokes declares the
   *        correct structures on it and returns the resulting {@link DslPart}.
   * @return Returns this builder for method chaining.
   */
  ConsumerBuilder<T> as(Function<PactDslJsonBody, ? extends DslPart> pactDslJsonBodyConfigurator);

  /**
   * Specifies a new field name for a default mapping. The mapping will be performed with either default type mappings
   * or registered {@link ConsumerBuilder}s.
   * 
   * @param jsonFieldName The new JSON field name.
   * @return Returns this builder for method chaining.
   */
  ConsumerBuilder<T> as(String jsonFieldName);

  /**
   * Changes the JSON field name to the desired field name and additionally acts like {@link #as(ConsumerBuilder)}.
   * 
   * @param anotherConsumer Another {@link ConsumerBuilder} instance for the field type.
   * @return Returns this builder for method chaining.
   */
  ConsumerBuilder<T> as(String jsonFieldName, ConsumerBuilder<?> anotherConsumer);

  /**
   * Registers another {@link ConsumerBuilder} on field level, that defines the JSON structure for the field type. If
   * another configuration for the type is registered, it will be overridden and the field configuration applies.
   * 
   * @param anotherConsumer Another {@link ConsumerBuilder} instance for the field type.
   * @return Returns this builder for method chaining.
   */
  ConsumerBuilder<T> as(ConsumerBuilder<?> anotherConsumer);

}
