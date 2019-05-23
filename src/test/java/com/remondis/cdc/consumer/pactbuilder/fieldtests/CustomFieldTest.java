package com.remondis.cdc.consumer.pactbuilder.fieldtests;

import org.junit.Test;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.cdc.consumer.pactbuilder.TestUtil.Result;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class CustomFieldTest {

  @Test
  public void shouldCreateCustomStructure() throws Exception {
    PactDslJsonBody pactDslJsonBody = new PactDslJsonBody();

    Parent parent = Samples.Default.of(Parent.class)
        .get();

    pactDslJsonBody = ConsumerExpects.type(Parent.class)
        .field(Parent::getStructure)
        .as(pactBody -> {
          return pactBody.object("structure")
              .stringType("string")
              .closeObject();
        })
        .build(pactDslJsonBody, parent);

    Result<Parent> result = TestUtil.toObject(pactDslJsonBody, Parent.class);

    TestUtil.assertObjectEqualsResult(parent, result);

  }
}
