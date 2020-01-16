package com.remondis.cdc.consumer.pactbuilder.regression.longListBug;

import java.util.LinkedList;
import java.util.List;

public class ListParent {

  private List<Long> list;

  public ListParent() {
    this.list = new LinkedList<>();
  }

  public List<Long> getList() {
    return list;
  }

  public void setList(List<Long> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "ListParent [list=" + list + "]";
  }

}
