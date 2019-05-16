package com.remondis.cdc.consumer.pactbuilder;

public class ConsumerExpects {
  public static <T> ConsumerBuilder<T> type(Class<T> type) {
    return new ConsumerBuilderImpl<T>(type);
  }
}
