package com.remondis.cdc.consumer.pactbuilder.regression.generics;

import java.util.List;

public class GenericListDummy<S> {

  private List<S> list;

  public GenericListDummy() {
    super();
  }

  public GenericListDummy(List<S> list) {
    super();
    this.list = list;
  }

  public List<S> getList() {
    return list;
  }

  public void setList(List<S> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "GenericListDummy [list=" + list + "]";
  }

}
