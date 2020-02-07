package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Thrown when no sample value was found while building the {@link PactDslJsonBody}.
 */
public class NoSampleValueException extends ConsumerBuilderException {

  private static final long serialVersionUID = 7171434853043392753L;

  public NoSampleValueException() {
  }

  public NoSampleValueException(String message) {
    super(message);
  }

  public NoSampleValueException(Throwable cause) {
    super(cause);
  }

  public NoSampleValueException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSampleValueException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
