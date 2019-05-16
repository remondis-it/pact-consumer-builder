package com.remondis.cdc.consumer.pactbuilder.testcase.types;

public class CurrencyResource {

  private Long id;

  private String name;

  private String isoCode;

  private String symbol;

  public CurrencyResource() {
    super();
  }

  public CurrencyResource(Long id, String name, String isoCode, String symbol) {
    super();
    this.id = id;
    this.name = name;
    this.isoCode = isoCode;
    this.symbol = symbol;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIsoCode() {
    return isoCode;
  }

  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

}
