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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((extraServiceId == null) ? 0 : extraServiceId.hashCode());
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
    PricingResultExtraServiceResource other = (PricingResultExtraServiceResource) obj;
    if (extraServiceId == null) {
      if (other.extraServiceId != null)
        return false;
    } else if (!extraServiceId.equals(other.extraServiceId))
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
