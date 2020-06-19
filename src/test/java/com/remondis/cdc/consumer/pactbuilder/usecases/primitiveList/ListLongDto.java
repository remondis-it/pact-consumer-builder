package com.remondis.cdc.consumer.pactbuilder.usecases.primitiveList;

import java.util.List;

public class ListLongDto {

  private List<Long> ids;

  public ListLongDto() {
    super();
  }

  public ListLongDto(List<Long> ids) {
    super();
    this.ids = ids;
  }

  public List<Long> getIds() {
    return ids;
  }

  public void setIds(List<Long> ids) {
    this.ids = ids;
  }

  @Override
  public String toString() {
    return "ListLongDto [ids=" + ids + "]";
  }

}
