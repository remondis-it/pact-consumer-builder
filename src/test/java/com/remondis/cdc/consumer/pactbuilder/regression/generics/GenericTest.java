package com.remondis.cdc.consumer.pactbuilder.regression.generics;

import static java.util.Arrays.asList;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class GenericTest {

  @Test
  public void shouldDoItWithGenericLists() throws JsonProcessingException {
    GenericListDummy<String> genericDummy = new GenericListDummy<String>(asList("A", "B"));

    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(GenericListDummy.class)
        .build(genericDummy);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    System.out.println(actualJson);
    JSONAssert.assertEquals("{\"list\":\"A\"}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void shouldDoIt() throws JsonProcessingException {
    GenericDummy<String> genericDummy = new GenericDummy<String>("String");
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(GenericDummy.class)
        .build(genericDummy);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"object\":\"String\"}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
