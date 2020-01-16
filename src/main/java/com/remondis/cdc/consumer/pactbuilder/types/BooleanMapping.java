package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslRootValueModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;

/**
 * Maps {@link Boolean} to {@link PactDslJsonBody#booleanType(String)}.
 */
public class BooleanMapping implements PactDslRootValueModifier<Boolean> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, Boolean fieldValue) {
    return pactDslJsonBody.booleanType(fieldName, fieldValue);
  }

  @Override
  public PactDslJsonRootValue asRootValue(Boolean fieldValue) {
    return PactDslJsonRootValue.booleanType(fieldValue);
  }

}
