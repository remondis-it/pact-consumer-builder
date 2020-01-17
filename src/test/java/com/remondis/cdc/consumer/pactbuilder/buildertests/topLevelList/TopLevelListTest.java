package com.remondis.cdc.consumer.pactbuilder.buildertests.topLevelList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

@RunWith(MockitoJUnitRunner.class)
public class TopLevelListTest {

  @Spy
  private Supplier<PactDslJsonBody> supplier = new Supplier<PactDslJsonBody>() {

    @Override
    public PactDslJsonBody get() {
      return PactDslJsonArray.arrayEachLike(1);
    }
  };

  @Test
  public void shouldCreateTopLevelList() throws Exception {
    PactDslJsonArray pactDslJsonArray = ConsumerExpects.collectionOf(ListParent.class)
        .useArraySupplier(supplier)
        .arrayContent(ConsumerExpects.type(ListParent.class))
        .build(Samples.Default.of(ListParent.class)
            .get());

    String actualJson = TestUtil.toJson(pactDslJsonArray);
    JSONAssert.assertEquals("[{\"children\":[{\"string\":\"string\"}]}]", actualJson, JSONCompareMode.NON_EXTENSIBLE);
    verify(supplier, times(1)).get();
  }

}
