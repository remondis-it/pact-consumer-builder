package com.remondis.cdc.consumer.pactbuilder.buildertests.ignoremissingvalues;

import java.io.Serializable;

/**
 * When working against generated code libraries,
 * it's not always possible to enforce the common naming conventions for fieldnames.
 * This class is used as a (contrived) example for our tests.
 */
public class CaseFieldObject implements Serializable {

  private String ABCFieldOne;

  private String abcFieldTwo;

  private String ABcFieldThree;

  public CaseFieldObject() {

  }

  public CaseFieldObject(String abcFieldOne, String abcFieldTwo, String aBcFieldThree) {
    this.ABCFieldOne = abcFieldOne;
    this.abcFieldTwo = abcFieldTwo;
    this.ABcFieldThree = aBcFieldThree;
  }

  public String getABCFieldOne() {
    return ABCFieldOne;
  }

  public void setABCFieldOne(String ABCFieldOne) {
    this.ABCFieldOne = ABCFieldOne;
  }

  public String getAbcFieldTwo() {
    return abcFieldTwo;
  }

  public void setAbcFieldTwo(String abcFieldTwo) {
    this.abcFieldTwo = abcFieldTwo;
  }

  public String getABcFieldThree() {
    return ABcFieldThree;
  }

  public void setABcFieldThree(String ABcFieldThree) {
    this.ABcFieldThree = ABcFieldThree;
  }
}
