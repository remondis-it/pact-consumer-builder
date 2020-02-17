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
  public void shouldDoItWithGenericLists_enums() throws JsonProcessingException {
    GenericListDummy<MyEnum> genericDummy = new GenericListDummy<>(asList(MyEnum.A, MyEnum.B));

    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(GenericListDummy.class)
        .build(genericDummy);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"list\":[\"A\"]}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void shouldDoItWithGenericLists_complexType() throws JsonProcessingException {
    GenericListDummy<Dummy> genericDummy = new GenericListDummy<>(asList(new Dummy("A"), new Dummy("B")));

    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(GenericListDummy.class)
        .referencing(ConsumerExpects.type(Dummy.class))
        .build(genericDummy);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"list\":[{\"string\":\"B\"}]}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void shouldDoItWithGenericLists_defaultType() throws JsonProcessingException {
    GenericListDummy<String> genericDummy = new GenericListDummy<String>(asList("A", "B"));

    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(GenericListDummy.class)
        .build(genericDummy);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"list\":[\"A\"]}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void shouldDoIt() throws JsonProcessingException {
    GenericDummy<String> genericDummy = new GenericDummy<>("String");
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(GenericDummy.class)
        .build(genericDummy);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"object\":\"String\"}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
