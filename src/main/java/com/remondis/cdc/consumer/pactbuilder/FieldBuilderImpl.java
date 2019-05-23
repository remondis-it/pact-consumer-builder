package com.remondis.cdc.consumer.pactbuilder;

import static java.util.Objects.requireNonNull;

import java.beans.PropertyDescriptor;
import java.util.function.Function;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class FieldBuilderImpl<R, T> implements FieldBuilder<R, T> {

  private ConsumerBuilderImpl<T> consumerBuilder;
  private PropertyDescriptor pd;

  public FieldBuilderImpl(ConsumerBuilderImpl<T> consumerBuilderImpl, PropertyDescriptor pd) {
    this.consumerBuilder = consumerBuilderImpl;
    this.pd = pd;
  }

  @Override
  public ConsumerBuilder<T> as(Function<PactDslJsonBody, ? extends DslPart> pactDslJsonBodyConfigurator) {
    requireNonNull(pactDslJsonBodyConfigurator, "PactDslJsonBodyConfigurator must not be null!");
    consumerBuilder.addFieldWithCustomConsumer(pd, pactDslJsonBodyConfigurator);
    return consumerBuilder;
  }

  @Override
  public ConsumerBuilder<T> as(String jsonFieldName) {
    consumerBuilder.addFieldWithCustomName(pd, jsonFieldName);
    return consumerBuilder;
  }

  @Override
  public ConsumerBuilder<T> as(String jsonFieldName, ConsumerBuilder<?> anotherConsumer) {
    consumerBuilder.addFieldCustomNameAndConsumerBuilder(pd, jsonFieldName, anotherConsumer);
    return consumerBuilder;
  }

  @Override
  public ConsumerBuilder<T> as(ConsumerBuilder<?> anotherConsumer) {
    consumerBuilder.addFieldCustomNameAndConsumerBuilder(pd, pd.getName(), anotherConsumer);
    return consumerBuilder;
  }

}
