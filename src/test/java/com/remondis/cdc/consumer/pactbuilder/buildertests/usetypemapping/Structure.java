package com.remondis.cdc.consumer.pactbuilder.buildertests.usetypemapping;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Structure {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Parent.DEFAULT_ZONED_DATE_TIME_PATTERN)
  private ZonedDateTime zonedDateTime;

  private String string;

  public Structure() {
    super();
  }

  public Structure(ZonedDateTime zonedDateTime, String string) {
    super();
    this.zonedDateTime = zonedDateTime;
    this.string = string;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
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
    result = prime * result + ((string == null) ? 0 : string.hashCode());
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
    Structure other = (Structure) obj;
    if (string == null) {
      if (other.string != null)
        return false;
    } else if (!string.equals(other.string))
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
    return "Structure [zonedDateTime=" + zonedDateTime + ", string=" + string + "]";
  }

}
