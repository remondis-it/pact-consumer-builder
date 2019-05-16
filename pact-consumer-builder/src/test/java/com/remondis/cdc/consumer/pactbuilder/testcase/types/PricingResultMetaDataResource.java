package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.time.ZonedDateTime;

public class PricingResultMetaDataResource {

  private ZonedDateTime determinationTime;

  public PricingResultMetaDataResource() {
    super();
  }

  public PricingResultMetaDataResource(ZonedDateTime determinationTime) {
    super();
    this.determinationTime = determinationTime;
  }

  public ZonedDateTime getDeterminationTime() {
    return determinationTime;
  }

  public void setDeterminationTime(ZonedDateTime determinationTime) {
    this.determinationTime = determinationTime;
  }

}
