package com.remondis.cdc.consumer.pactbuilder.buildertests.customArrayMapping;

import java.util.LinkedList;
import java.util.List;

public class ListParent {
  private List<Structure> children = new LinkedList<>();

  public ListParent() {
    super();
  }

  public ListParent(List<Structure> children) {
    super();
    this.children = children;
  }

  public List<Structure> getChildren() {
    return children;
  }

  public void setChildren(List<Structure> children) {
    this.children = children;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((children == null) ? 0 : children.hashCode());
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
    ListParent other = (ListParent) obj;
    if (children == null) {
      if (other.children != null)
        return false;
    } else if (!children.equals(other.children))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ListParent [children=" + children + "]";
  }

}
