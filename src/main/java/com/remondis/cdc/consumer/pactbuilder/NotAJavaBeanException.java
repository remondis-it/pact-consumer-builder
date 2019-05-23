package com.remondis.cdc.consumer.pactbuilder;

public class NotAJavaBeanException extends RuntimeException {

  public NotAJavaBeanException() {
  }

  public NotAJavaBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public NotAJavaBeanException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotAJavaBeanException(String message) {
    super(message);
  }

  public NotAJavaBeanException(Throwable cause) {
    super(cause);
  }

}
