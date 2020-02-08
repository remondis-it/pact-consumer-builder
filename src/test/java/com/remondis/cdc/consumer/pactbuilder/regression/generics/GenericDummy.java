package com.remondis.cdc.consumer.pactbuilder.regression.generics;

public class GenericDummy<S> {

  private S object;

  public GenericDummy() {
    super();
  }

  public GenericDummy(S object) {
    super();
    this.object = object;
  }

  public S getObject() {
    return object;
  }

  public void setObject(S object) {
    this.object = object;
  }

  @Override
  public String toString() {
    return "GenericDummy [object=" + object + "]";
  }

}
