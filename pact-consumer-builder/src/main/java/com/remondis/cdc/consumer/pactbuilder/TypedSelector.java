package com.remondis.cdc.consumer.pactbuilder;

/**
 * Defines a function that represents a get-call on a Java Bean property.
 *
 * @param <T>
 *        The object type selecting a field on.
 * @param <R>
 *        The type of the field.
 * @author schuettec
 */
@FunctionalInterface
public interface TypedSelector<R, T> {

  /**
   * This method is used to perform a get-method invocation of the specified
   * destination object and returning its value.
   *
   * @param destination
   *        The destination object to perform a get-method invocation on.
   * @return Returns the return value of the performed get-method call.
   */
  R selectField(T destination);

}
