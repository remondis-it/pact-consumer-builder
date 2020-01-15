package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslRootValueModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;

/**
 * Maps {@link Number} to {@link PactDslJsonBody#integerType(String, Long)}.
 */
public class LongMapping implements PactDslRootValueModifier<Long> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, Long fieldValue) {
    return pactDslJsonBody.integerType(fieldName, fieldValue);
  }

  @Override
  public PactDslJsonRootValue asRootValue(Long fieldValue) {
    return PactDslJsonRootValue.integerType(fieldValue);
  }

}
