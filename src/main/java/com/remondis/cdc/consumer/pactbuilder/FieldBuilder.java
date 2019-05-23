package com.remondis.cdc.consumer.pactbuilder;

import java.util.function.Function;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public interface FieldBuilder<R, T> {

  ConsumerBuilder<T> as(Function<PactDslJsonBody, ? extends DslPart> pactDslJsonBodyConfigurator);

  ConsumerBuilder<T> as(String jsonFieldName);

  ConsumerBuilder<T> as(String jsonFieldName, ConsumerBuilder<?> anotherConsumer);

  ConsumerBuilder<T> as(ConsumerBuilder<?> anotherConsumer);

}
