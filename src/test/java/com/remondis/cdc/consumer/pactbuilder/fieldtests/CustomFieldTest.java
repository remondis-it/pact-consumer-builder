package com.remondis.cdc.consumer.pactbuilder.fieldtests;

import org.junit.Test;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.cdc.consumer.pactbuilder.TestUtil.Result;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class CustomFieldTest {

  @Test
  public void shouldCreateCustomStructure_embedded() throws Exception {
    PactDslJsonBody pactDslJsonBody = new PactDslJsonBody();

    Structure expected = Samples.Default.of(Structure.class)
        .get();

    pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .field(Structure::getString)
        .as(pactBody -> {
          return pactBody.stringType("string");
        })
        .build(pactDslJsonBody, expected);

    Result<Parent> result = TestUtil.toObject(pactDslJsonBody, Parent.class);

    TestUtil.assertObjectEqualsResult(expected, result);

  }

  @Test
  public void shouldCreateCustomStructure_asReference() throws Exception {
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
