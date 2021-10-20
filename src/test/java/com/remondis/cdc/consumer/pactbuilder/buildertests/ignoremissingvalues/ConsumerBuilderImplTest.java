package com.remondis.cdc.consumer.pactbuilder.buildertests.ignoremissingvalues;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslResponse;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.matchingrules.MatchingRuleGroup;
import au.com.dius.pact.core.model.matchingrules.MatchingRules;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilder;
import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilderException;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    instance.setField1(new PropertyA("value1"));
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
    instance.setField1(new PropertyA("value1"));
    instance.setField2(null);
    // When we build a RequestResponsePact with the given instance
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .ignoreMissingValues()
        .build(instance);
    // Then the field with the missing value shouldn't be included in the Pact
    expectPactBuildResult(pactDslJsonBody, "{\"field1\":{\"fieldA1\":\"value1\"}}", "$.field1.fieldA1");
  }

  /**
   * Make sure nested objects with one or more missing values still get included.
   */
  @Test
  public void shouldIgnoreMissingValuesInsteadOfFailingNestedNull() {
    // Given a Structure instance with one field value not being filled in
    Structure instance = new Structure();
    instance.setField1(new PropertyA("value1"));
    instance.setField2(null);
    instance.setField3(new PropertyA("value3"));
    // When we build a RequestResponsePact with the given instance
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .ignoreMissingValues()
        .build(instance);
    // Then the field with the missing value shouldn't be included in the Pact
    expectPactBuildResult(pactDslJsonBody, "{\"field1\":{\"fieldA1\":\"value1\"},\"field3\":{\"fieldA1\":\"value3\"}}",
        "$.field1.fieldA1", "$.field3.fieldA1");
  }

  /**
   * Make sure nested objects with one or more missing values still get included.
   * In this case, we include an "empty instance", which will not result in a matching rule,
   * but will end up in the sample JSON.
   */
  @Test
  public void shouldIgnoreMissingValuesInsteadOfFailingNested() {
    // Given a Structure instance with one field value not being filled in
    Structure instance = new Structure();
    instance.setField1(new PropertyA("value1"));
    instance.setField2(new PropertyA(null));
    instance.setField3(new PropertyA("value3"));
    // When we build a RequestResponsePact with the given instance
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .ignoreMissingValues()
        .build(instance);
    // Then the field with the missing value shouldn't be included in the Pact
    expectPactBuildResult(pactDslJsonBody,
        "{\"field1\":{\"fieldA1\":\"value1\"},\"field2\":{},\"field3\":{\"fieldA1\":\"value3\"}}", "$.field1.fieldA1",
        "$.field3.fieldA1");
  }

  /**
   * Make sure nested objects with one or more missing values still get included.
   * Testing one level deeper.
   */
  @Test
  public void shouldIgnoreMissingValuesInsteadOfFailingAnotherNested() {
    // Given a Structure instance with one field value not being filled in
    Structure instance = new Structure();
    PropertyB anotherField = new PropertyB();
    anotherField.setFieldB1(new PropertyC("value1"));
    instance.setField4(anotherField);
    // When we build a RequestResponsePact with the given instance
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .ignoreMissingValues()
        .build(instance);
    // Then the field with the missing value shouldn't be included in the Pact
    expectPactBuildResult(pactDslJsonBody, "{\"field4\":{\"fieldB1\":{\"fieldC1\":\"value1\"}}}",
        "$.field4.fieldB1.fieldC1");
  }

  /**
   * Make sure nested objects with one or more missing values still get included.
   * Testing one level deeper.
   */
  @Test
  public void shouldIgnoreMissingValuesInsteadOfFailingAnotherNestedFull() {
    // Given a Structure instance with one field value not being filled in
    Structure instance = new Structure();
    PropertyB anotherField = new PropertyB();
    anotherField.setFieldB1(new PropertyC("value1"));
    anotherField.setFieldB2(new PropertyC("value2"));
    instance.setField4(anotherField);
    // When we build a RequestResponsePact with the given instance
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(Structure.class)
        .ignoreMissingValues()
        .build(instance);
    // Then the field with the missing value shouldn't be included in the Pact
    expectPactBuildResult(pactDslJsonBody,
        "{\"field4\":{\"fieldB1\":{\"fieldC1\":\"value1\"},\"fieldB2\":{\"fieldC1\":\"value2\"}}}",
        "$.field4.fieldB1.fieldC1", "$.field4.fieldB2.fieldC1");
  }

  /**
   * The {@link ObjectMapper} will change fieldnames that start with uppercase characters to start with lowercase.
   * The {@link com.remondis.cdc.consumer.pactbuilder.ConsumerBuilderImpl} is not doing this (by default).
   */
  @Test
  public void shouldNotLowercaseFirstFieldnameLettersByDefault() throws IOException {
    CaseFieldObject object = new CaseFieldObject("a", "b", "c");
    String defaultObjectMapperSerialized = new ObjectMapper().writeValueAsString(object);
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(CaseFieldObject.class)
        .build(object);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    Set<String> actualJsonProperties = getJsonPropertyNames(actualJson);
    Set<String> defaultObjectMapperProperties = getJsonPropertyNames(defaultObjectMapperSerialized);
    assertThat(actualJsonProperties).doesNotContainSequence(defaultObjectMapperProperties);
  }

  /**
   * The {@link ObjectMapper} will change fieldnames that start with uppercase characters to start with lowercase.
   * The {@link com.remondis.cdc.consumer.pactbuilder.ConsumerBuilderImpl} is not doing this (by default),
   * which leads to a Pact that would contain fields such as "ABCFieldOne" instead of "abcfieldOne".
   * In order to correctly match the provider (using ObjectMapper) and the consumer (using ConsumerBuilderImpl)
   * properties, {@link ConsumerBuilder#enforcePropertyNamingConvention()} has been provided.
   */
  @Test
  public void shouldLowercaseFirstFieldnameLettersWhenRequired() throws IOException {
    CaseFieldObject object = new CaseFieldObject("a", "b", "c");
    String objectMapperSerialized = new ObjectMapper().writeValueAsString(object);
    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(CaseFieldObject.class)
        .enforcePropertyNamingConvention()
        .build(object);
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    Set<String> actualJsonProperties = getJsonPropertyNames(actualJson);
    Set<String> defaultObjectMapperProperties = getJsonPropertyNames(objectMapperSerialized);
    assertThat(actualJsonProperties).containsExactlyInAnyOrder(defaultObjectMapperProperties.toArray(new String[0]));
  }

  @NotNull
  private Set<String> getJsonPropertyNames(String actualJson) throws IOException {
    return new ObjectMapper().readValue(actualJson, LinkedHashMap.class)
        .keySet();
  }

  /**
   * Build Pact and perform some assertions.
   *
   * @param pactDslJsonBody The {@link PactDslJsonBody} to assert against
   * @param expectedJson The expected resulting JSON string
   * @param expectedBodyMatcherKeys The expected keys for the body category matching rules
   */
  private void expectPactBuildResult(PactDslJsonBody pactDslJsonBody, String expectedJson,
      String... expectedBodyMatcherKeys) {
    PactDslWithProvider builder = new PactDslWithProvider(new ConsumerPactBuilder("consumer"), "provider");
    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    // We shouldn't get an exception...
    PactDslResponse dslResponse = builder.given("GET request")
        .uponReceiving("GET request")
        .path("/structure/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(pactDslJsonBody);
    RequestResponsePact pact = dslResponse.toPact();
    // Check the resulting matching rules
    MatchingRules responseMatchingRules = Objects.requireNonNull(pact.getInteractions()
        .get(0)
        .asSynchronousRequestResponse()
        .getResponse()
        .getMatchingRules());
    Map<String, MatchingRuleGroup> bodyMatchingRules = responseMatchingRules.rulesForCategory("body")
        .getMatchingRules();
    assertThat(bodyMatchingRules.size()).isEqualTo(expectedBodyMatcherKeys.length);
    assertThat(bodyMatchingRules.keySet()).containsExactlyInAnyOrder(expectedBodyMatcherKeys);
  }

}
