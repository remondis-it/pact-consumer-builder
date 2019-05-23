package com.remondis.cdc.consumer.pactbuilder.fieldtests;

public class Parent {

  private Structure structure;

  public Parent(Structure structure) {
    super();
    this.structure = structure;
  }

  public Parent() {
    super();
  }

  public Structure getStructure() {
    return structure;
  }

  public void setStructure(Structure structure) {
    this.structure = structure;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((structure == null) ? 0 : structure.hashCode());
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
    if (structure == null) {
      if (other.structure != null)
        return false;
    } else if (!structure.equals(other.structure))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Parent [structure=" + structure + "]";
  }

}
