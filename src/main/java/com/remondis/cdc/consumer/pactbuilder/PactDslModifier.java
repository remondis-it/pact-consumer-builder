package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

@FunctionalInterface
public interface PactDslModifier<T> {

  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, T fieldValue);

}
