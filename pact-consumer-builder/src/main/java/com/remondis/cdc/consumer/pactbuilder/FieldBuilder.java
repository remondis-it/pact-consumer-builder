package com.remondis.cdc.consumer.pactbuilder;

import java.util.function.Consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public interface FieldBuilder<R, T> {

  ConsumerBuilder<T> as(Consumer<PactDslJsonBody> pactDslJsonBodyConfigurator);

  ConsumerBuilder<T> as(String jsonFieldName);

  ConsumerBuilder<T> as(String jsonFieldName, ConsumerBuilder<?> anotherConsumer);

  ConsumerBuilder<T> as(ConsumerBuilder<?> anotherConsumer);

}
