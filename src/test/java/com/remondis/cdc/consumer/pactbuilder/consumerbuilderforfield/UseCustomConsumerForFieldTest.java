package com.remondis.cdc.consumer.pactbuilder.consumerbuilderforfield;

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
        .field(Parent::getStructure)
        .as("anotherStructureName", getStructureDefinition())
        .build(pactDslJsonBody, parent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);

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
        .as(getStructureDefinition())
        .build(pactDslJsonBody, parent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"structure\":{\"anotherName\":\"string\"}}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  public ConsumerBuilder<Structure> getStructureDefinition() {
    return ConsumerExpects.type(Structure.class)
        .field(Structure::getString)
        .as("anotherName");
  }
}
