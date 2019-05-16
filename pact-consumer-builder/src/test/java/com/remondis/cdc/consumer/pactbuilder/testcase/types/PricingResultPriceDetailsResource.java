package com.remondis.cdc.consumer.pactbuilder.testcase.types;

public class PricingResultPriceDetailsResource {

  private PriceResultOrigin origin;

  private String priceType;

  private String agreementNumber;

  public PricingResultPriceDetailsResource() {
    super();
    // TODO Auto-generated constructor stub
  }

  public PricingResultPriceDetailsResource(PriceResultOrigin origin, String priceType, String agreementNumber) {
    super();
    this.origin = origin;
    this.priceType = priceType;
    this.agreementNumber = agreementNumber;
  }

  public PriceResultOrigin getOrigin() {
    return origin;
  }

  public void setOrigin(PriceResultOrigin origin) {
    this.origin = origin;
  }

  public String getPriceType() {
    return priceType;
  }

  public void setPriceType(String priceType) {
    this.priceType = priceType;
  }

  public String getAgreementNumber() {
    return agreementNumber;
  }

  public void setAgreementNumber(String agreementNumber) {
    this.agreementNumber = agreementNumber;
  }

}
