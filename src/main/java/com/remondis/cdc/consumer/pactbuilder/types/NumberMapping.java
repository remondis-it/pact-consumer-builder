package com.remondis.cdc.consumer.pactbuilder.types;

import com.remondis.cdc.consumer.pactbuilder.PactDslRootValueModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;

/**
 * Maps {@link Number} to {@link PactDslJsonBody#numberType(String, Number)}.
 */
public class NumberMapping implements PactDslRootValueModifier<Number> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, Number fieldValue) {
    return pactDslJsonBody.numberType(fieldName, fieldValue);
  }

  @Override
  public PactDslJsonRootValue asRootValue(Number fieldValue) {
    return PactDslJsonRootValue.numberType(fieldValue);
  }

}
