package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Maps {@link String} to {@link PactDslJsonBody#stringMatcher(String, String)}.
 */
public class StringMapping implements PactDslModifier<String> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, String fieldValue) {
    return pactDslJsonBody.stringValue(fieldName, fieldValue);
  }

}
