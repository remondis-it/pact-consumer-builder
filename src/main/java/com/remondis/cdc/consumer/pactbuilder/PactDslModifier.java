package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * This interface is used to implement custom type mappings. The {@link PactDslJsonBody} as fell as the field name and
 * value are provided during the conversion of the Java Bean into a JSON structure. To add a global type conversion use
 * {@link ConsumerBuilder#useTypeMapping(Class, PactDslModifier)}.
 * 
 * @param <T> The type this modifier converts.
 */
@FunctionalInterface
public interface PactDslModifier<T> {

  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, T fieldValue);

}
