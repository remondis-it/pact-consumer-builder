package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * This interface defines a modifier to build arrays in a {@link PactDslJsonBody}. You can replace the default behaviour
 * for arrays by implementing your own array modifier an register it to the {@link ConsumerBuilder}.
 */
public interface PactDslArrayModifier {

  /**
   * Starts a new array by calling the respective method on the {@link PactDslJsonBody}.
   *
   * @param pactDslJsonBody The {@link PactDslJsonBody} to perform calls on.
   * @param fieldName The field name of the array.
   * @return Returns the value from the {@link PactDslJsonBody} call.
   */
  public PactDslJsonBody startArray(PactDslJsonBody pactDslJsonBody, String fieldName);

  public default DslPart closeArray(PactDslJsonBody pactDslJsonBody) {
    return pactDslJsonBody.closeArray();
  }

}
