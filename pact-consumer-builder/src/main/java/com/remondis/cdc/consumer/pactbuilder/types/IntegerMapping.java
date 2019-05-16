package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class IntegerMapping<N extends Number> implements PactDslModifier<N> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, N fieldValue) {
    return pactDslJsonBody.integerType(fieldName, (Long) fieldValue);
  }

}
