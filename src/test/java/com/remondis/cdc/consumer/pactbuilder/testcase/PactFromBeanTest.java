package com.remondis.cdc.consumer.pactbuilder.testcase;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.JsonHelper;
import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;
import com.remondis.cdc.consumer.pactbuilder.testcase.types.PricingResultArticleResource;
import com.remondis.cdc.consumer.pactbuilder.testcase.types.PricingResultExtraServiceResource;
import com.remondis.cdc.consumer.pactbuilder.testcase.types.PricingResultResource;
import com.remondis.resample.Samples;
import com.remondis.resample.supplier.Suppliers;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class PactFromBeanTest {

  public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");

  private static PactDslModifier<ZonedDateTime> zonedDateTimeMapping() {
    return (pactDslJsonBody, fieldName, fieldValue) -> {
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
      return pactDslJsonBody.stringType(fieldName, dateTimeFormatter.format(fieldValue));
    };
  }

  private PactDslJsonBody pactFromLibrary() {
    PricingResultResource expectedPricingResult = createSample();

    PactDslJsonBody jsonBody = new PactDslJsonBody();
    ConsumerExpects.type(PricingResultResource.class)
        .useTypeMapping(ZonedDateTime.class, zonedDateTimeMapping())
        .build(jsonBody, expectedPricingResult);
    return jsonBody;
  }

  private PricingResultResource createSample() {
    PricingResultResource expectedPricingResult = Samples.Default.of(PricingResultResource.class)
        .use(Suppliers.zonedDateTimeSampleSupplier(2019, 10, 1, 1, 00, 00, 00, ZoneId.of("Europe/Berlin")))
        .use(Suppliers.oneBigDecimalSampleSupplier())
        .get();
    return expectedPricingResult;
  }

  @Test
  public void test() throws Exception {
    PactDslJsonBody libJsonBody = pactFromLibrary();

    StringWriter libOutputWriter = new StringWriter();
    ((org.json.JSONObject) libJsonBody.getBody()).write(libOutputWriter);

    PactDslJsonBody originJsonBody = pactFromOriginal();

    StringWriter originOutputWriter = new StringWriter();
    ((org.json.JSONObject) originJsonBody.getBody()).write(originOutputWriter);

    String libContent = libOutputWriter.getBuffer()
        .toString();
    String originContent = originOutputWriter.getBuffer()
        .toString();

    PricingResultResource fromLib = JsonHelper.fromJson(libContent, PricingResultResource.class);
    PricingResultResource fromOrigin = JsonHelper.fromJson(originContent, PricingResultResource.class);

    assertEquals(fromLib, fromOrigin);
  }

  private PactDslJsonBody pactFromOriginal() {
    PricingResultResource expectedPricingResult = createSample();
    PricingResultArticleResource articleData = expectedPricingResult.getArticle();
    PricingResultExtraServiceResource extraServiceData = expectedPricingResult.getExtraServices()
        .get(0);

    PactDslJsonBody jsonBody = new PactDslJsonBody().object("currency")
        .numberType("id", expectedPricingResult.getCurrency()
            .getId())
        .stringType("name", expectedPricingResult.getCurrency()
            .getName())
        .stringType("isoCode", expectedPricingResult.getCurrency()
            .getIsoCode())
        .stringType("symbol", expectedPricingResult.getCurrency()
            .getSymbol())
        .closeObject()
        .object("total")
        .numberType("grossAmount", expectedPricingResult.getTotal()
            .getGrossAmount())
        .numberType("netAmount", expectedPricingResult.getTotal()
            .getNetAmount())
        .numberType("taxAmount", expectedPricingResult.getTotal()
            .getTaxAmount())
        .stringType("direction", expectedPricingResult.getTotal()
            .getDirection()
            .getKey())
        .closeObject()
        .object("article")
        .stringType("articleNumber", articleData.getArticleNumber())
        .object("tax")
        .numberType("id", articleData.getTax()
            .getId())
        .stringType("reason", articleData.getTax()
            .getReason())
        .stringType("name", articleData.getTax()
            .getName())
        .stringType("shortName", articleData.getTax()
            .getShortName())
        .numberType("rate", articleData.getTax()
            .getRate())
        .closeObject()
        .object("totalPrice")
        .numberType("grossAmount", articleData.getTotalPrice()
            .getGrossAmount())
        .numberType("netAmount", articleData.getTotalPrice()
            .getNetAmount())
        .numberType("taxAmount", articleData.getTotalPrice()
            .getTaxAmount())
        .stringType("direction", articleData.getTotalPrice()
            .getDirection()
            .getKey())
        .closeObject()
        .object("unitPrice")
        .stringType("priceType", articleData.getUnitPrice()
            .getPriceType())
        .numberType("grossAmount", articleData.getUnitPrice()
            .getGrossAmount())
        .numberType("netAmount", articleData.getUnitPrice()
            .getNetAmount())
        .numberType("taxAmount", articleData.getUnitPrice()
            .getTaxAmount())
        .stringType("direction", articleData.getUnitPrice()
            .getDirection()
            .getKey())
        .object("measurementUnit")
        .numberType("id", articleData.getUnitPrice()
            .getMeasurementUnit()
            .getId())
        .stringType("description", articleData.getUnitPrice()
            .getMeasurementUnit()
            .getDescription())
        .object("baseUnit")
        .numberType("id", articleData.getUnitPrice()
            .getMeasurementUnit()
            .getBaseUnit()
            .getId())
        .stringType("description", articleData.getUnitPrice()
            .getMeasurementUnit()
            .getBaseUnit()
            .getDescription())
        .closeObject()
        .closeObject()
        .closeObject()
        .object("priceDetails")
        .stringType("origin", articleData.getPriceDetails()
            .getOrigin()
            .getKey())
        .stringType("priceType", articleData.getPriceDetails()
            .getPriceType())
        .stringType("agreementNumber", articleData.getPriceDetails()
            .getAgreementNumber())
        .closeObject()
        .closeObject()

        .minArrayLike("extraServices", 1)
        .numberType("extraServiceId", extraServiceData.getExtraServiceId())
        .object("tax")
        .numberType("id", extraServiceData.getTax()
            .getId())
        .stringType("reason", extraServiceData.getTax()
            .getReason())
        .stringType("name", extraServiceData.getTax()
            .getName())
        .stringType("shortName", extraServiceData.getTax()
            .getShortName())
        .numberType("rate", extraServiceData.getTax()
            .getRate())
        .closeObject()
        .object("totalPrice")
        .numberType("grossAmount", extraServiceData.getTotalPrice()
            .getGrossAmount())
        .numberType("netAmount", extraServiceData.getTotalPrice()
            .getNetAmount())
        .numberType("taxAmount", extraServiceData.getTotalPrice()
            .getTaxAmount())
        .stringType("direction", extraServiceData.getTotalPrice()
            .getDirection()
            .getKey())
        .closeObject()
        .object("unitPrice")
        .stringType("priceType", extraServiceData.getUnitPrice()
            .getPriceType())
        .numberType("grossAmount", extraServiceData.getUnitPrice()
            .getGrossAmount())
        .numberType("netAmount", extraServiceData.getUnitPrice()
            .getNetAmount())
        .numberType("taxAmount", extraServiceData.getUnitPrice()
            .getTaxAmount())
        .stringType("direction", extraServiceData.getUnitPrice()
            .getDirection()
            .getKey())
        .object("measurementUnit")
        .numberType("id", extraServiceData.getUnitPrice()
            .getMeasurementUnit()
            .getId())
        .stringType("description", extraServiceData.getUnitPrice()
            .getMeasurementUnit()
            .getDescription())
        .object("baseUnit")
        .numberType("id", extraServiceData.getUnitPrice()
            .getMeasurementUnit()
            .getBaseUnit()
            .getId())
        .stringType("description", extraServiceData.getUnitPrice()
            .getMeasurementUnit()
            .getBaseUnit()
            .getDescription())
        .closeObject()
        .closeObject()
        .closeObject()
        .object("priceDetails")
        .stringType("origin", extraServiceData.getPriceDetails()
            .getOrigin()
            .getKey())
        .stringType("priceType", extraServiceData.getPriceDetails()
            .getPriceType())
        .stringType("agreementNumber", extraServiceData.getPriceDetails()
            .getAgreementNumber())
        .closeObject()
        .closeArray()
        .object("metaData")
        .stringType("determinationTime", expectedPricingResult.getMetaData()
            .getDeterminationTime()
            .format(DEFAULT_FORMATTER))
        .closeObject()
        .asBody();
    //@formatter:on
    return jsonBody;
  }

}
