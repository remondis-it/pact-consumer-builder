package com.remondis.cdc.consumer.pactbuilder.regression.primitivesFirstBug;

public class Child {

  private String string1;

  public Child(String string1) {
    super();
    this.string1 = string1;
  }

  public Child() {
    super();
  }

  public String getString1() {
    return string1;
  }

  public void setString1(String string1) {
    this.string1 = string1;
  }

  @Override
  public String toString() {
    return "Child [string1=" + string1 + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((string1 == null) ? 0 : string1.hashCode());
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
    Child other = (Child) obj;
    if (string1 == null) {
      if (other.string1 != null)
        return false;
    } else if (!string1.equals(other.string1))
      return false;
    return true;
  }

}
