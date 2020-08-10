package com.remondis.cdc.consumer.pactbuilder.buildertests.arraymapping;

import java.util.Arrays;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilder;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class ArrayMappingTest {

  @Test
  public void should() {
    Parent p = new Parent();
    p.setTest(Arrays.asList("testValue"));

    ConsumerBuilder<Parent> consumerBuilder = ConsumerExpects.type(Parent.class);
    PactDslJsonBody body = consumerBuilder.build(p);

    String actualJson = TestUtil.toJson(body);
    System.out.println(actualJson);
    JSONAssert.assertEquals("{\"test\":[\"testValue\"]}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
