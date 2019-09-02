package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Maps {@link Number} to {@link PactDslJsonBody#integerType(String, Long)}.
 */
public class LongMapping implements PactDslModifier<Long> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, Long fieldValue) {
    return pactDslJsonBody.integerType(fieldName, fieldValue);
  }

}
