package com.remondis.cdc.consumer.pactbuilder.types;

import java.math.BigDecimal;

import com.remondis.cdc.consumer.pactbuilder.PactDslRootValueModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;

/**
 * Maps {@link Number} to {@link PactDslJsonBody#decimalType(String, Double)}.
 */
public class DecimalMapping implements PactDslRootValueModifier<Number> {

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

  @Override
  public PactDslJsonRootValue asRootValue(Number fieldValue) {
    if (fieldValue instanceof BigDecimal) {
      BigDecimal bigDecimal = (BigDecimal) fieldValue;
      return PactDslJsonRootValue.decimalType(bigDecimal);
    } else if (fieldValue instanceof Double) {
      Double d = (Double) fieldValue;
      return PactDslJsonRootValue.decimalType(d);
    } else {
      throw new UnsupportedOperationException("The decimal mapping can only be used for BigDecimal or Double.");
    }
  }

}
