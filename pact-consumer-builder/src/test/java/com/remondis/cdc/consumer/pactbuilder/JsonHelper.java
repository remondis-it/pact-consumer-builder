package com.remondis.cdc.consumer.pactbuilder;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonHelper {

  private static ObjectMapper jsonMapper;

  static {
    jsonMapper = new ObjectMapper();
    jsonMapper.registerModule(new JavaTimeModule());
    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  private JsonHelper() {
  }

  /**
   * Parst das übergebene JSON in ein Objekt der angegebenen Klasse.
   *
   * @param json der zu parsende JSON-String.
   * @param toClass die Ziel-Klasse, die aus dem JSON-String instanziiert werden soll.
   * @param <T> Typ der Klasse, die instanziiert werden soll.
   * @return Java-Repräsentation des übergebenen JSON-Strings.
   * @throws IOException wenn der JSON-String nicht korrekt geparst werden kann.
   */
  public static <T> T fromJson(String json, Class<T> toClass) throws IOException {
    return jsonMapper.readValue(json, toClass);
  }

  /**
   * Parst das übergebene JSON in ein Objekt der angegebenen {@link TypeReference}. Mit Angabe einer
   * {@link TypeReference} kann auch eine Klasse instanziiert werden, die Generics benutzt.
   *
   * @param json der zu parsende JSON-String.
   * @param typeReference ein {@link TypeReference} Objekt, dass die Ziel-Klasse beschreibt.
   * @param <T> Typ der Klasse, die instanziiert werden soll.
   * @return Java-Repräsentation des übergebenen JSON-Strings.
   * @throws IOException wenn der JSON-String nicht korrekt geparst werden kann.
   */
  public static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
    return jsonMapper.readValue(json, typeReference);
  }

  /**
   * Mappt das übergebene Objekt in einen JSON-String.
   *
   * @param object das Objekt, das in einen JSON-String gemappt werden soll.
   */
  public static <T> String toJson(T object) throws JsonProcessingException {
    return jsonMapper.writeValueAsString(object);
  }

}