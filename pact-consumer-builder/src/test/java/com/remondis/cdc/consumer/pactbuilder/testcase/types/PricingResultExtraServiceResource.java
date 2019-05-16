package com.remondis.cdc.consumer.pactbuilder.testcase.types;

public class PricingResultExtraServiceResource {

  private Long extraServiceId;

  private TaxResource tax;

  private PricingResultTotalResource totalPrice;

  private PricingResultUnitPriceResource unitPrice;

  private PricingResultPriceDetailsResource priceDetails;

  public PricingResultExtraServiceResource() {
    super();
  }

  public PricingResultExtraServiceResource(Long extraServiceId, TaxResource tax, PricingResultTotalResource totalPrice,
      PricingResultUnitPriceResource unitPrice, PricingResultPriceDetailsResource priceDetails) {
    super();
    this.extraServiceId = extraServiceId;
    this.tax = tax;
    this.totalPrice = totalPrice;
    this.unitPrice = unitPrice;
    this.priceDetails = priceDetails;
  }

  public Long getExtraServiceId() {
    return extraServiceId;
  }

  public void setExtraServiceId(Long extraServiceId) {
    this.extraServiceId = extraServiceId;
  }

  public TaxResource getTax() {
    return tax;
  }

  public void setTax(TaxResource tax) {
    this.tax = tax;
  }

  public PricingResultTotalResource getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(PricingResultTotalResource totalPrice) {
    this.totalPrice = totalPrice;
  }

  public PricingResultUnitPriceResource getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(PricingResultUnitPriceResource unitPrice) {
    this.unitPrice = unitPrice;
  }

  public PricingResultPriceDetailsResource getPriceDetails() {
    return priceDetails;
  }

  public void setPriceDetails(PricingResultPriceDetailsResource priceDetails) {
    this.priceDetails = priceDetails;
  }

}
