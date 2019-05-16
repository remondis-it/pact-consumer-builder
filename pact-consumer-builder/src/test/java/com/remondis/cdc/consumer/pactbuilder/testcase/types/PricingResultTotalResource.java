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

}
