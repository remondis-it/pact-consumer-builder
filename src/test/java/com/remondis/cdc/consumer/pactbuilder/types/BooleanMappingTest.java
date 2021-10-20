package com.remondis.cdc.consumer.pactbuilder.types;

import org.junit.Test;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class BooleanMappingTest {
  @Test
  public void shouldWorkForIntegerAndInt() {
    BooleanMapping mapping = new BooleanMapping();
    mapping.apply(new PactDslJsonBody(), "fieldName", true);
    mapping.apply(new PactDslJsonBody(), "fieldName", Boolean.TRUE);
//    mapping.apply(new PactDslJsonBody(), "fieldName", null);
  }
}
