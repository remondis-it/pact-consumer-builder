package com.remondis.cdc.consumer.pactbuilder.types;

import java.math.BigDecimal;

import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class DecimalMapping implements PactDslModifier<Number> {

  @Override
  public PactDslJsonBody apply(PactDslJsonBody pactDslJsonBody, String fieldName, Number fieldValue) {
    if (fieldValue instanceof BigDecimal) {
      BigDecimal bigDecimal = (BigDecimal) fieldValue;
      return pactDslJsonBody.decimalType(fieldName, bigDecimal);
    } else if (fieldValue instanceof Double) {
      Double d = (Double) fieldValue;
      return pactDslJsonBody.decimalType(fieldName, d);
    } else {
      throw new UnsupportedOperationException("The decimal mapping can only be used for BigDecimal or Double.");
    }
  }

}
