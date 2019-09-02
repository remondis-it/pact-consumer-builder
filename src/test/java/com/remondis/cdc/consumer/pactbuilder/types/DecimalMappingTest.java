package com.remondis.cdc.consumer.pactbuilder.types;

import java.math.BigDecimal;

import org.junit.Test;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class DecimalMappingTest {

  @Test
  public void shouldWorkForIntegerAndInt() {
    DecimalMapping mapping = new DecimalMapping();
    mapping.apply(new PactDslJsonBody(), "fieldName", BigDecimal.ONE);
    mapping.apply(new PactDslJsonBody(), "fieldName", Double.valueOf(3.0));
    mapping.apply(new PactDslJsonBody(), "fieldName", 3.0d);
  }
}
