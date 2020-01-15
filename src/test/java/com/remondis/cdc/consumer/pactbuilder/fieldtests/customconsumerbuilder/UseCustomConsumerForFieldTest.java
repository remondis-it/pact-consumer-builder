package com.remondis.cdc.consumer.pactbuilder.fieldtests.customconsumerbuilder;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilder;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class UseCustomConsumerForFieldTest {
  @Test
  public void shouldUseCustomConsumerBuilderAndChangeFieldName() throws Exception {
    PactDslJsonBody pactDslJsonBody = new PactDslJsonBody();

    Parent parent = Samples.Default.of(Parent.class)
        .get();

    pactDslJsonBody = ConsumerExpects.type(Parent.class)
        // This structure definition is expected to be overridden
        .referencing(getToBeOverriddenStructureDefinition())
        .field(Parent::getStructure)
        // This structure definition is expected to be used instead
        .as("anotherStructureName", getExpectedStructureDefinition())
        .build(pactDslJsonBody, parent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);

    System.out.println(actualJson);

    JSONAssert.assertEquals("{\"anotherStructureName\":{\"anotherName\":\"string\"}}", actualJson,
        JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void shouldUseCustomConsumerBuilder() throws Exception {
    PactDslJsonBody pactDslJsonBody = new PactDslJsonBody();

    Parent parent = Samples.Default.of(Parent.class)
        .get();

    pactDslJsonBody = ConsumerExpects.type(Parent.class)
        .field(Parent::getStructure)
        .as(getExpectedStructureDefinition())
        .build(pactDslJsonBody, parent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"structure\":{\"anotherName\":\"string\"}}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  public ConsumerBuilder<Structure> getToBeOverriddenStructureDefinition() {
    return ConsumerExpects.type(Structure.class)
        .field(Structure::getString)
        .as("SHOULDnotBEused");
  }

  public ConsumerBuilder<Structure> getExpectedStructureDefinition() {
    return ConsumerExpects.type(Structure.class)
        .field(Structure::getString)
        .as("anotherName");
  }
}
