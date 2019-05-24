package com.remondis.cdc.consumer.pactbuilder.buildertests.usetypemapping;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Parent {

  public static final String DEFAULT_ZONED_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSxxx";

  private String stringType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_ZONED_DATE_TIME_PATTERN)
  private ZonedDateTime zonedDateTime;

  private Structure structure;

  public Parent() {
    super();
  }

  public Parent(String stringType, ZonedDateTime zonedDateTime, Structure structure) {
    super();
    this.stringType = stringType;
    this.zonedDateTime = zonedDateTime;
    this.structure = structure;
  }

  public Structure getStructure() {
    return structure;
  }

  public void setStructure(Structure structure) {
    this.structure = structure;
  }

  public String getStringType() {
    return stringType;
  }

  public void setStringType(String stringType) {
    this.stringType = stringType;
  }

  public ZonedDateTime getZonedDateTime() {
    return zonedDateTime;
  }

  public void setZonedDateTime(ZonedDateTime zonedDateTime) {
    this.zonedDateTime = zonedDateTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((stringType == null) ? 0 : stringType.hashCode());
    result = prime * result + ((structure == null) ? 0 : structure.hashCode());
    result = prime * result + ((zonedDateTime == null) ? 0 : zonedDateTime.hashCode());
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
    Parent other = (Parent) obj;
    if (stringType == null) {
      if (other.stringType != null)
        return false;
    } else if (!stringType.equals(other.stringType))
      return false;
    if (structure == null) {
      if (other.structure != null)
        return false;
    } else if (!structure.equals(other.structure))
      return false;
    if (zonedDateTime == null) {
      if (other.zonedDateTime != null)
        return false;
    } else if (!zonedDateTime.equals(other.zonedDateTime))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Parent [stringType=" + stringType + ", zonedDateTime=" + zonedDateTime + ", structure=" + structure + "]";
  }

}
