package com.remondis.cdc.consumer.pactbuilder.regression.primitivesFirstBug;

import org.junit.Test;

import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilder;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class PactBuilderPrimitivesFirstBug {

  @Test
  public void shouldHeul() {

    Parent parent = Samples.Default.of(Parent.class)
        .get();

    ConsumerBuilder<Child> childMapping = ConsumerExpects.type(Child.class);
    PactDslJsonBody pactBody = ConsumerExpects.type(Parent.class)
        .referencing(childMapping)
        .field(Parent::getFeld2)
        .as((body) -> {
          return body.stringType("bliblablubs", "example");
        })
        .field(Parent::getChild2)
        .as((body) -> {
          return body.stringType("TSCHEILD", "child2");
        })
        .build(new PactDslJsonBody(), parent);

    System.out.println(TestUtil.toJson(pactBody));

  }

  @Test
  public void shouldHeul1() {

  }

}
