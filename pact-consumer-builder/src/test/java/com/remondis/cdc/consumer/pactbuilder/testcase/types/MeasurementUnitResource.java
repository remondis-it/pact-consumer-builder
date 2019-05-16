package com.remondis.cdc.consumer.pactbuilder.testcase.types;

public class MeasurementUnitResource {

  private Long id;

  private String description;

  private BaseUnitResource baseUnit;

  public MeasurementUnitResource() {
    super();
  }

  public MeasurementUnitResource(Long id, String description, BaseUnitResource baseUnit) {
    super();
    this.id = id;
    this.description = description;
    this.baseUnit = baseUnit;
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

  public BaseUnitResource getBaseUnit() {
    return baseUnit;
  }

  public void setBaseUnit(BaseUnitResource baseUnit) {
    this.baseUnit = baseUnit;
  }

}
