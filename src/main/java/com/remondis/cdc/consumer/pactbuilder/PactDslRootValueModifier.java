package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;

/**
 * This interface is used to implement custom type mappings for values that can be represented as a simple string but
 * also as a field within a complex type definition. For representation as a field, the methods of
 * {@link PactDslModifier} are used. To generate an array, the string representation is needed as a
 * {@link PactDslJsonRootValue}. In those cases the Method {@link #asRootValue(Object)} is used.
 *
 * <h2>Implementation hints</h2>
 * <p>
 * The implementation must make sure that the representation as field, implemented by
 * {@link #apply(au.com.dius.pact.consumer.dsl.PactDslJsonBody, String, Object)} matches the representation as
 * {@link #asRootValue(Object)}.
 * </p>
 *
 * @param <T> The type this modifier converts.
 */
public interface PactDslRootValueModifier<T> extends PactDslModifier<T> {

  /**
   * Returns a {@link PactDslJsonRootValue} for the specified field value to represent it as a simple string. This
   * method is used for arrays.
   *
   * @param fieldValue The field value.
   * @return Returns a new {@link PactDslJsonRootValue} representing the field value.
   */
  public PactDslJsonRootValue asRootValue(T fieldValue);

}
