package com.remondis.cdc.consumer.pactbuilder;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * This is a decorator for {@link ConsumerBuilder} that produces a top-level collection in the resulting JSON structure.
 * Use {@link ConsumerExpects#collectionOf(Class)} to build a Pact Consumer that expected a top-level array/collection.
 *
 * @param <T> The type of object that's JSON structure is to be defined.
 */
public class TopLevelCollectionDecorator<T> extends ConsumerBuilderImpl<T> {

  private static final Supplier<PactDslJsonBody> DEFAULT_SUPPLIER = () -> PactDslJsonArray.arrayMinLike(1);
  private Supplier<PactDslJsonBody> topLevelArraySupplier = DEFAULT_SUPPLIER;

  TopLevelCollectionDecorator(Class<T> type) {
    super(type);
  }

  /**
   * Overrides the default mapping for a top-level Pact Consumer JSON list. Per default
   * <code>PactDslJsonArray.arrayMinLike(1)</code> is used. To configure another strategy you can specify another
   * {@link Supplier} that produces a prepared {@link PactDslJsonBody}.
   *
   * @param supplier The supplier that always returns a <b>new</b> {@link PactDslJsonBody} prepared to represent an
   *        array.
   * @return Returns this {@link ConsumerBuilder} for further configuration.
   */
  public ConsumerBuilder<T> useArraySupplier(Supplier<PactDslJsonBody> supplier) {
    requireNonNull(supplier, "supplier must not be null!");
    this.topLevelArraySupplier = supplier;
    return this;
  }

  @Override
  public PactDslJsonBody build(T sampleData) {
    return super.build(topLevelArraySupplier.get(), sampleData);
  }

  @Override
  public PactDslJsonBody build(PactDslJsonBody pactDslJsonBody, T sampleData) {
    return super.build(topLevelArraySupplier.get(), sampleData);
  }

}
