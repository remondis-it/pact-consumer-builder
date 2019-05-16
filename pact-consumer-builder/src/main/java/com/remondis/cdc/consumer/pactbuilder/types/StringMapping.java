package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class StringMapping implements PactDslModifier<String> {

  @Override
  public void apply(PactDslJsonBody pactDslJsonBody, String fieldName, String fieldValue) {
    pactDslJsonBody.stringValue(fieldName, fieldValue);
  }

}
