package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.math.BigDecimal;

public class TaxResource {

  private Long id;

  private String reason;

  private String name;

  private String shortName;

  private BigDecimal rate;

  public TaxResource(Long id, String reason, String name, String shortName, BigDecimal rate) {
    super();
    this.id = id;
    this.reason = reason;
    this.name = name;
    this.shortName = shortName;
    this.rate = rate;
  }

  public TaxResource() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

}
