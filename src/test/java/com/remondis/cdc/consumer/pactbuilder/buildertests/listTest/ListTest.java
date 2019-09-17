package com.remondis.cdc.consumer.pactbuilder.buildertests.listTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.MinArrayLikeModifier;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

@RunWith(MockitoJUnitRunner.class)
public class ListTest {

  @Spy
  private MinArrayLikeModifier arrayModifier = new MinArrayLikeModifier();

  @Test
  public void testList() {
    ListParent listParent = Samples.Default.of(ListParent.class)
        .newInstance();
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(ListParent.class)
        .useArrayMapping(arrayModifier)
        .referencing(ConsumerExpects.type(Structure.class))
        .build(new PactDslJsonBody(), listParent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"children\":[{\"string\":\"string\"}]}", actualJson, JSONCompareMode.NON_EXTENSIBLE);

    verify(arrayModifier, times(1)).startArray(any(), anyString());
  }

}
