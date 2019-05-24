package com.remondis.cdc.consumer.pactbuilder.buildertests.usetypemapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;
import com.remondis.resample.Samples;
import com.remondis.resample.supplier.Suppliers;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class UseTypeMappingTest {

  public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter
      .ofPattern(Parent.DEFAULT_ZONED_DATE_TIME_PATTERN);

  @Test
  public void shouldUseGlobalTypeMappingsRecursively() throws JsonProcessingException {
    PactDslJsonBody pactDslJsonBody = new PactDslJsonBody();

    Parent parent = Samples.Default.of(Parent.class)
        .use(Suppliers.zonedDateTimeSampleSupplier(2019, 06, 1, 6, 45, 23, 0, ZoneId.of("Europe/Berlin")))
        .get();

    pactDslJsonBody = ConsumerExpects.type(Parent.class)
        .useTypeMapping(ZonedDateTime.class, zonedDateTimeMapping())
        .build(pactDslJsonBody, parent);

    String actualJson = TestUtil.toJson(pactDslJsonBody);

    JSONAssert.assertEquals(
        "{\"stringType\":\"stringType\",\"zonedDateTime\":\"2019-06-01T06:45:23.000+02:00\",\"structure\":{\"zonedDateTime\":\"2019-06-01T06:45:23.000+02:00\",\"string\":\"string\"}}",
        actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

  private static PactDslModifier<ZonedDateTime> zonedDateTimeMapping() {
    return (pactDslJsonBody, fieldName, fieldValue) -> {
      return pactDslJsonBody.stringType(fieldName, DEFAULT_FORMATTER.format(fieldValue));
    };
  }
}
