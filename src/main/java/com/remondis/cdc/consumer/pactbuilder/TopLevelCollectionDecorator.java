package com.remondis.cdc.consumer.pactbuilder;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * This is a decorator for {@link ConsumerBuilder} that produces a top-level collection in the resulting JSON structure.
 * Use {@link ConsumerExpects#collectionOf(Class)} to build a Pact Consumer that expected a top-level array/collection.
 *
 * @param <T> The type of object that's JSON structure is to be defined.
 */
public class TopLevelCollectionDecorator<T> {

  private static final Supplier<PactDslJsonBody> DEFAULT_SUPPLIER = () -> PactDslJsonArray.arrayMinLike(1);
  private Supplier<PactDslJsonBody> topLevelArraySupplier = DEFAULT_SUPPLIER;

  private ConsumerBuilder<T> arrayContentBuilder;

  /**
   * Overrides the default mapping for a top-level Pact Consumer JSON list. Per default
   * <code>PactDslJsonArray.arrayMinLike(1)</code> is used. To configure another strategy you can specify another
   * {@link Supplier} that produces a prepared {@link PactDslJsonBody}.
   *
   * @param supplier The supplier that always returns a <b>new</b> {@link PactDslJsonBody} prepared to represent an
   *        array.
   * @return Returns this {@link ConsumerBuilder} for further configuration.
   */
  public TopLevelCollectionDecorator<T> useArraySupplier(Supplier<PactDslJsonBody> supplier) {
    requireNonNull(supplier, "supplier must not be null!");
    this.topLevelArraySupplier = supplier;
    return this;
  }

  /**
   * Specifies the {@link ConsumerBuilder} for the array content.
   *
   * @param consumerBuilder The consumer builder for the array content.
   * @return Returns this instance for further configuration
   */
  public TopLevelCollectionDecorator<T> arrayContent(ConsumerBuilder<T> consumerBuilder) {
    requireNonNull(consumerBuilder, "suppconsumerBuilderlier must not be null!");
    this.arrayContentBuilder = consumerBuilder;
    return this;
  }

  /**
   * Builds the JSON structure with a top level list.
   *
   * @param sampleData The sample data for an example array item.
   * @return Returns the {@link PactDslJsonArray}.
   */
  public PactDslJsonArray build(T sampleData) {
    PactDslJsonBody pactDslJsonBody = topLevelArraySupplier.get();
    PactDslJsonBody jsonBody = arrayContentBuilder.build(pactDslJsonBody, sampleData);
    DslPart array = jsonBody.close();
    return (PactDslJsonArray) array;
  }

}
