package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.math.BigDecimal;

public class PricingResultTotalResource {

  private BigDecimal grossAmount;

  private BigDecimal netAmount;

  private BigDecimal taxAmount;

  private PriceDirection direction;

  public PricingResultTotalResource(BigDecimal grossAmount, BigDecimal netAmount, BigDecimal taxAmount,
      PriceDirection direction) {
    super();
    this.grossAmount = grossAmount;
    this.netAmount = netAmount;
    this.taxAmount = taxAmount;
    this.direction = direction;
  }

  public PricingResultTotalResource() {
    super();
    // TODO Auto-generated constructor stub
  }

  public BigDecimal getGrossAmount() {
    return grossAmount;
  }

  public void setGrossAmount(BigDecimal grossAmount) {
    this.grossAmount = grossAmount;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public BigDecimal getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(BigDecimal taxAmount) {
    this.taxAmount = taxAmount;
  }

  public PriceDirection getDirection() {
    return direction;
  }

  public void setDirection(PriceDirection direction) {
    this.direction = direction;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + ((grossAmount == null) ? 0 : grossAmount.hashCode());
    result = prime * result + ((netAmount == null) ? 0 : netAmount.hashCode());
    result = prime * result + ((taxAmount == null) ? 0 : taxAmount.hashCode());
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
    PricingResultTotalResource other = (PricingResultTotalResource) obj;
    if (direction != other.direction)
      return false;
    if (grossAmount == null) {
      if (other.grossAmount != null)
        return false;
    } else if (!grossAmount.equals(other.grossAmount))
      return false;
    if (netAmount == null) {
      if (other.netAmount != null)
        return false;
    } else if (!netAmount.equals(other.netAmount))
      return false;
    if (taxAmount == null) {
      if (other.taxAmount != null)
        return false;
    } else if (!taxAmount.equals(other.taxAmount))
      return false;
    return true;
  }

}
