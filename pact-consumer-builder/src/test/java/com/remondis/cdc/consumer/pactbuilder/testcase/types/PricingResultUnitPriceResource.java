package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.math.BigDecimal;

public class PricingResultUnitPriceResource {

  private String priceType;

  private BigDecimal grossAmount;

  private BigDecimal netAmount;

  private BigDecimal taxAmount;

  private PriceDirection direction;

  private MeasurementUnitResource measurementUnit;

  public PricingResultUnitPriceResource() {
    super();
  }

  public PricingResultUnitPriceResource(String priceType, BigDecimal grossAmount, BigDecimal netAmount,
      BigDecimal taxAmount, PriceDirection direction, MeasurementUnitResource measurementUnit) {
    super();
    this.priceType = priceType;
    this.grossAmount = grossAmount;
    this.netAmount = netAmount;
    this.taxAmount = taxAmount;
    this.direction = direction;
    this.measurementUnit = measurementUnit;
  }

  public String getPriceType() {
    return priceType;
  }

  public void setPriceType(String priceType) {
    this.priceType = priceType;
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

  public MeasurementUnitResource getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(MeasurementUnitResource measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + ((grossAmount == null) ? 0 : grossAmount.hashCode());
    result = prime * result + ((measurementUnit == null) ? 0 : measurementUnit.hashCode());
    result = prime * result + ((netAmount == null) ? 0 : netAmount.hashCode());
    result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
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
    PricingResultUnitPriceResource other = (PricingResultUnitPriceResource) obj;
    if (direction != other.direction)
      return false;
    if (grossAmount == null) {
      if (other.grossAmount != null)
        return false;
    } else if (!grossAmount.equals(other.grossAmount))
      return false;
    if (measurementUnit == null) {
      if (other.measurementUnit != null)
        return false;
    } else if (!measurementUnit.equals(other.measurementUnit))
      return false;
    if (netAmount == null) {
      if (other.netAmount != null)
        return false;
    } else if (!netAmount.equals(other.netAmount))
      return false;
    if (priceType == null) {
      if (other.priceType != null)
        return false;
    } else if (!priceType.equals(other.priceType))
      return false;
    if (taxAmount == null) {
      if (other.taxAmount != null)
        return false;
    } else if (!taxAmount.equals(other.taxAmount))
      return false;
    return true;
  }

}
