package com.remondis.cdc.consumer.pactbuilder.customfieldname;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class CustomFieldNameTest {

  private static final String EXPECTED_JSON_NAME = "anotherName";

  @Test
  public void shouldCustomFieldName() throws Exception {
    PactDslJsonBody pactDslJsonBody = new PactDslJsonBody();

    Parent parent = Samples.Default.of(Parent.class)
        .get();

    pactDslJsonBody = ConsumerExpects.type(Parent.class)
        .field(Parent::getStructure)
        .as(EXPECTED_JSON_NAME)
        .build(pactDslJsonBody, parent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"anotherName\":{\"string\":\"string\"}}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }
}
