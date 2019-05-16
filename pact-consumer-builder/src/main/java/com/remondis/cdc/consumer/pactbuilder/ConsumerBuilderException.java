package com.remondis.cdc.consumer.pactbuilder;

public class ConsumerBuilderException extends RuntimeException {

  public ConsumerBuilderException() {
  }

  public ConsumerBuilderException(String message) {
    super(message);
  }

  public ConsumerBuilderException(Throwable cause) {
    super(cause);
  }

  public ConsumerBuilderException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConsumerBuilderException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
