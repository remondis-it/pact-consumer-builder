package com.remondis.cdc.consumer.pactbuilder.testcase.types;

public class PricingResultArticleResource {

  private String articleNumber;

  private TaxResource tax;

  private PricingResultTotalResource totalPrice;

  private PricingResultUnitPriceResource unitPrice;

  private PricingResultPriceDetailsResource priceDetails;

  public PricingResultArticleResource() {
    super();
  }

  public PricingResultArticleResource(String articleNumber, TaxResource tax, PricingResultTotalResource totalPrice,
      PricingResultUnitPriceResource unitPrice, PricingResultPriceDetailsResource priceDetails) {
    super();
    this.articleNumber = articleNumber;
    this.tax = tax;
    this.totalPrice = totalPrice;
    this.unitPrice = unitPrice;
    this.priceDetails = priceDetails;
  }

  public String getArticleNumber() {
    return articleNumber;
  }

  public void setArticleNumber(String articleNumber) {
    this.articleNumber = articleNumber;
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
