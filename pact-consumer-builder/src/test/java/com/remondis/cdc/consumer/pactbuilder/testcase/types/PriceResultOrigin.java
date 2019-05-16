package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Herkunft des Basispreises.
 */
public enum PriceResultOrigin {
  DEFAULT("default"),
  CUSTOMER_GROUP("customer_group"),
  MANUAL("manual"),
  AGREEMENT("agreement");

  static Map<String, PriceResultOrigin> byKey = new HashMap<>();

  static {
    for (PriceResultOrigin unit : PriceResultOrigin.values()) {
      byKey.put(unit.getKey(), unit);
    }
  }

  public static PriceResultOrigin byKey(String key) {
    return byKey.get(key);
  }

  private String key;

  PriceResultOrigin(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(key);
  }

}
