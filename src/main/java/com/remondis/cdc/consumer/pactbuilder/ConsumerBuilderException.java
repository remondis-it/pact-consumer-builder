package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Thrown on any error while converting the sample values or building the {@link PactDslJsonBody}.
 */
public class ConsumerBuilderException extends RuntimeException {

  private static final long serialVersionUID = 2787755594910807663L;

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
