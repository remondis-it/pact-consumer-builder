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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((determinationTime == null) ? 0 : determinationTime.hashCode());
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
    PricingResultMetaDataResource other = (PricingResultMetaDataResource) obj;
    if (determinationTime == null) {
      if (other.determinationTime != null)
        return false;
    } else if (!determinationTime.equals(other.determinationTime))
      return false;
    return true;
  }

}
