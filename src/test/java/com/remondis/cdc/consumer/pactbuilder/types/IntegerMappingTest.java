package com.remondis.cdc.consumer.pactbuilder.types;

import org.junit.Test;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class IntegerMappingTest {

  @Test
  public void shouldWorkForIntegerAndInt() {
    IntegerMapping mapping = new IntegerMapping();
    mapping.apply(new PactDslJsonBody(), "fieldName", 3);
    mapping.apply(new PactDslJsonBody(), "fieldName", Integer.valueOf(100));
  }

}
