package com.remondis.cdc.consumer.pactbuilder.buildertests.ignoremissingvalues;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslResponse;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import au.com.dius.pact.model.matchingrules.MatchingRules;
import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilderException;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ConsumerBuilderImplTest {

  /**
   * Original behavior, just fail when a given field has a null value
   */
  @Test
  public void shouldFailOnMissingValues() {
    // Given a Structure instance with one field value not being filled in
    Structure instance = new Structure();
    instance.setField1(new SomeField("value1"));
    instance.setField2(null);
    // When we build a RequestResponsePact with the given instance
    // Then the field with the missing value won't be included in the Pact and we should get an exception...
    try {
      ConsumerExpects.type(Structure.class)
          .build(instance);
      fail("Expected a ConsumerBuilderException because of a missing value");
    } catch (ConsumerBuilderException e) {
      assertThat(e.getMessage()).contains("A property of the specified sample data object was null");
    }
  }

  /**
   * New behavior when using "ignoreMissingValues": don't include the empty field in the Pact.
   */
  @Test
  public void shouldIgnoreMissingValuesInsteadOfFailing() {
    // Given a Structure instance with one field value not being filled in
    Structure instance = new Structure();
    instance.setField1(new SomeField("value1"));
    instance.setField2(null);
    // When we build a RequestResponsePact with the given instance
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .ignoreMissingValues()
        .build(instance);
    // Then the field with the missing value shouldn't be included in the Pact
    PactDslWithProvider builder = new PactDslWithProvider(new ConsumerPactBuilder("consumer"), "provider");
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals("{\"field1\":{\"field\":\"value1\"}}", actualJson, JSONCompareMode.NON_EXTENSIBLE);
    // And we shouldn't get an exception...
    PactDslResponse dslResponse = builder.given("GET request")
        .uponReceiving("GET request")
        .path("/structure/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(pactDslJsonBody);
    RequestResponsePact pact = dslResponse.toPact();
    // Only field 1 should be included in the matching rules for body
    MatchingRules responseMatchingRules = Objects.requireNonNull(pact.getInteractions()
        .get(0)
        .getResponse()
        .getMatchingRules());
    assertThat(responseMatchingRules.rulesForCategory("body")
        .getMatchingRules()
        .size()).isEqualTo(1);
  }

}
