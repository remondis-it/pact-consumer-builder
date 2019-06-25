package com.remondis.cdc.consumer.pactbuilder.regression.primitivesFirstBug;

public class Parent {

  private String feld1;
  private Child child;
  private String feld2;

  private Child child2;

  public Parent() {
    super();
  }

  public String getFeld1() {
    return feld1;
  }

  public void setFeld1(String feld1) {
    this.feld1 = feld1;
  }

  public Child getChild() {
    return child;
  }

  public void setChild(Child child) {
    this.child = child;
  }

  public String getFeld2() {
    return feld2;
  }

  public void setFeld2(String feld2) {
    this.feld2 = feld2;
  }

  public Child getChild2() {
    return child2;
  }

  public void setChild2(Child child2) {
    this.child2 = child2;
  }

  public Parent(String feld1, Child child, String feld2, Child child2) {
    super();
    this.feld1 = feld1;
    this.child = child;
    this.feld2 = feld2;
    this.child2 = child2;
  }

  @Override
  public String toString() {
    return "Parent [feld1=" + feld1 + ", child=" + child + ", feld2=" + feld2 + ", child2=" + child2 + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((child == null) ? 0 : child.hashCode());
    result = prime * result + ((child2 == null) ? 0 : child2.hashCode());
    result = prime * result + ((feld1 == null) ? 0 : feld1.hashCode());
    result = prime * result + ((feld2 == null) ? 0 : feld2.hashCode());
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
    if (child == null) {
      if (other.child != null)
        return false;
    } else if (!child.equals(other.child))
      return false;
    if (child2 == null) {
      if (other.child2 != null)
        return false;
    } else if (!child2.equals(other.child2))
      return false;
    if (feld1 == null) {
      if (other.feld1 != null)
        return false;
    } else if (!feld1.equals(other.feld1))
      return false;
    if (feld2 == null) {
      if (other.feld2 != null)
        return false;
    } else if (!feld2.equals(other.feld2))
      return false;
    return true;
  }

}
