package com.remondis.cdc.consumer.pactbuilder.buildertests.customArrayMapping;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class CustomeArrayMappingTest {

  @Test
  public void testList() {
    ListParent listParent = Samples.Default.of(ListParent.class)
        .newInstance();
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(ListParent.class)
        .referencing(ConsumerExpects.type(Structure.class))
        .build(new PactDslJsonBody(), listParent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"children\":[{\"string\":\"string\"}]}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
