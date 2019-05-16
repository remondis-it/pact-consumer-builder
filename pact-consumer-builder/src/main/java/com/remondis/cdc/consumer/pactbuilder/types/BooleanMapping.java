package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class BooleanMapping implements PactDslModifier<Boolean> {

  @Override
  public void apply(PactDslJsonBody pactDslJsonBody, String fieldName, Boolean fieldValue) {
    pactDslJsonBody.booleanType(fieldName, fieldValue);
  }

}
