package com.remondis.cdc.consumer.pactbuilder.testcase.types;

public class BaseUnitResource {

  private Long id;

  private String description;

  public BaseUnitResource() {
    super();
  }

  public BaseUnitResource(Long id, String description) {
    super();
    this.id = id;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "BaseUnitResource [id=" + id + ", description=" + description + "]";
  }

}
