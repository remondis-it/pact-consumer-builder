package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Maps {@link Boolean} to {@link PactDslJsonBody#booleanType(String)}.
 */
public class BooleanMapping implements PactDslModifier<Boolean> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, Boolean fieldValue) {
    return pactDslJsonBody.booleanType(fieldName, fieldValue);
  }

}
