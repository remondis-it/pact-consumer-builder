package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gibt die Richtung des Preises an (income/Einnahme, expenditure/Ausgabe).
 */
public enum PriceDirection
    implements
    HasDatabaseId {
  // Einnahme
  INCOME("income"),
  // Ausgabe
  EXPENDITURE("expenditure");

  /**
   * Entscheidet, ob es sich um eine Einnahme oder Ausgabe handelt.
   * Bei Nettopreisen kleiner 0€ handelt es sich um eine Ausgabe.
   * Ansonsten um eine Einnahme.
   *
   * @param netPrice gegebener Nettopreis
   * @return PriceDirection
   */
  public static PriceDirection determineByNetAmount(BigDecimal netPrice) {
    return netPrice.compareTo(BigDecimal.ZERO) < 0 ? PriceDirection.EXPENDITURE : PriceDirection.INCOME;
  }

  private String key;

  PriceDirection(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String getDatabaseId() {
    return this.key;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(key);
  }
}
