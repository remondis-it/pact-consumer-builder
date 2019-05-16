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

}
