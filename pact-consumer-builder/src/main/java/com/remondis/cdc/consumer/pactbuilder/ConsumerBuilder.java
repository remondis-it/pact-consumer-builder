package com.remondis.cdc.consumer.pactbuilder;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public interface ConsumerBuilder<T> {

  <S> ConsumerBuilder<T> useTypeMapping(Class<S> type, PactDslModifier<S> modifier);

  <R> FieldBuilder<R, T> field(TypedSelector<R, T> fieldSelector);

  ConsumerBuilder<T> referencing(ConsumerBuilder<?> consumer);

  PactDslJsonBody build(PactDslJsonBody pactDslJsonBody, T sampleData);

  Class<T> getType();

}
