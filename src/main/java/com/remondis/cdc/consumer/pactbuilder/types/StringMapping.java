package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslRootValueModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;

/**
 * Maps {@link String} to {@link PactDslJsonBody#stringMatcher(String, String)}.
 */
public class StringMapping implements PactDslRootValueModifier<String> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, String fieldValue) {
    return pactDslJsonBody.stringType(fieldName, fieldValue);
  }

  @Override
  public PactDslJsonRootValue asRootValue(String fieldValue) {
    return PactDslJsonRootValue.stringType(fieldValue);
  }

}
