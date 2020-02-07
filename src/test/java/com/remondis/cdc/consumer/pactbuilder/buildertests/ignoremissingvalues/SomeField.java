package com.remondis.cdc.consumer.pactbuilder.buildertests.ignoremissingvalues;

public class SomeField {

  private String field;

  public SomeField() {
    // default constructor, required for ConsumerBuilder...
  }

  public SomeField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
