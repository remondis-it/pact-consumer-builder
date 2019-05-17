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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseUnit == null) ? 0 : baseUnit.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MeasurementUnitResource other = (MeasurementUnitResource) obj;
    if (baseUnit == null) {
      if (other.baseUnit != null)
        return false;
    } else if (!baseUnit.equals(other.baseUnit))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
