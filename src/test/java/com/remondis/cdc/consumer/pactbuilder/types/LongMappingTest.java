package com.remondis.cdc.consumer.pactbuilder.types;

import org.junit.Test;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class LongMappingTest {

  @Test
  public void shouldWorkForIntegerAndInt() {
    LongMapping mapping = new LongMapping();
    mapping.apply(new PactDslJsonBody(), "fieldName", 3L);
    mapping.apply(new PactDslJsonBody(), "fieldName", Long.valueOf(100L));
  }
}
