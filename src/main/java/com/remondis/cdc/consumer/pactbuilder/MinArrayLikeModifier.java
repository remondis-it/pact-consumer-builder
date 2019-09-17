package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * The default array modifier for the {@link ConsumerBuilder}.
 */
public class MinArrayLikeModifier implements PactDslArrayModifier {

  @Override
  public PactDslJsonBody startArray(PactDslJsonBody pactDslJsonBody, String fieldName) {
    return pactDslJsonBody.minArrayLike(fieldName, 1);
  }

}
