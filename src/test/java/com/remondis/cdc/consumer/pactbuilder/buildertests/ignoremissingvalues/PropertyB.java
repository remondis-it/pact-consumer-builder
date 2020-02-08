package com.remondis.cdc.consumer.pactbuilder.buildertests.ignoremissingvalues;

public class PropertyB {

  private PropertyC fieldB1;

  private PropertyC fieldB2;

  public PropertyB() {
    // default constructor, required for ConsumerBuilder...
  }

  public PropertyC getFieldB1() {
    return fieldB1;
  }

  public void setFieldB1(PropertyC fieldB1) {
    this.fieldB1 = fieldB1;
  }

  public PropertyC getFieldB2() {
    return fieldB2;
  }

  public void setFieldB2(PropertyC fieldB2) {
    this.fieldB2 = fieldB2;
  }
}
