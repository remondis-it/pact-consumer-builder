package com.remondis.cdc.consumer.pactbuilder.buildertests.ignoremissingvalues;

public class PropertyA {

  private String fieldA1;

  public PropertyA() {
    // default constructor, required for ConsumerBuilder...
  }

  public PropertyA(String fieldA1) {
    this.fieldA1 = fieldA1;
  }

  public String getFieldA1() {
    return fieldA1;
  }

  public void setFieldA1(String fieldA1) {
    this.fieldA1 = fieldA1;
  }
}
