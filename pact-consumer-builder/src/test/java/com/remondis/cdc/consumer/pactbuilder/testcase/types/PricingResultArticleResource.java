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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((articleNumber == null) ? 0 : articleNumber.hashCode());
    result = prime * result + ((priceDetails == null) ? 0 : priceDetails.hashCode());
    result = prime * result + ((tax == null) ? 0 : tax.hashCode());
    result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
    result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
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
    PricingResultArticleResource other = (PricingResultArticleResource) obj;
    if (articleNumber == null) {
      if (other.articleNumber != null)
        return false;
    } else if (!articleNumber.equals(other.articleNumber))
      return false;
    if (priceDetails == null) {
      if (other.priceDetails != null)
        return false;
    } else if (!priceDetails.equals(other.priceDetails))
      return false;
    if (tax == null) {
      if (other.tax != null)
        return false;
    } else if (!tax.equals(other.tax))
      return false;
    if (totalPrice == null) {
      if (other.totalPrice != null)
        return false;
    } else if (!totalPrice.equals(other.totalPrice))
      return false;
    if (unitPrice == null) {
      if (other.unitPrice != null)
        return false;
    } else if (!unitPrice.equals(other.unitPrice))
      return false;
    return true;
  }

}
