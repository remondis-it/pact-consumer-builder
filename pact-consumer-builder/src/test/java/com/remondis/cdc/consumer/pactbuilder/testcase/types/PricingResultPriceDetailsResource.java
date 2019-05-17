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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agreementNumber == null) ? 0 : agreementNumber.hashCode());
    result = prime * result + ((origin == null) ? 0 : origin.hashCode());
    result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PricingResultPriceDetailsResource other = (PricingResultPriceDetailsResource) obj;
    if (agreementNumber == null) {
      if (other.agreementNumber != null)
        return false;
    } else if (!agreementNumber.equals(other.agreementNumber))
      return false;
    if (origin != other.origin)
      return false;
    if (priceType == null) {
      if (other.priceType != null)
        return false;
    } else if (!priceType.equals(other.priceType))
      return false;
    return true;
  }

}
