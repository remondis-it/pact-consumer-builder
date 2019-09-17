package com.remondis.cdc.consumer.pactbuilder;

/**
 * This is the main entry point for the builder used to configure the JSON structure of a Java Bean used in Pact
 * consumer tests.
 *
 * @param <T> The type of object that's JSON structure is to be defined.
 */
public class ConsumerExpects {
  /**
   * Consumer expects an object structure. Specifies the type of Java Bean that is to be defined as a JSON structure.
   *
   * @param <T> The type.
   * @param type The type.
   * @return Returns a new {@link ConsumerBuilder} for further configuration.
   */
  public static <T> ConsumerBuilder<T> type(Class<T> type) {
    return new ConsumerBuilderImpl<T>(type);
  }

  public static <T> TopLevelCollectionDecorator<T> collectionOf(Class<T> type) {
    return new TopLevelCollectionDecorator<>(type);
  }

}
