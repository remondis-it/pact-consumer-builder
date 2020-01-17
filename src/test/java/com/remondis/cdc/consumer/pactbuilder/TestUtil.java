package com.remondis.cdc.consumer.pactbuilder;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonProcessingException;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class TestUtil {

  public static class Result<T> {
    T object;

    String jsonString;

    public Result(T object, String jsonString) {
      super();
      this.object = object;
      this.jsonString = jsonString;
    }

    public String getJsonString() {
      return jsonString;
    }

    public T getObject() {
      return object;
    }

  }

  /**
   * Converts the {@link PactDslJsonBody} to the specified type.
   *
   * @param <T> The target type to convert to.
   * @param body The {@link PactDslJsonBody} describing the JSON structure of the target type.
   * @param type The target type to convert to.
   * @return Returns a new object of the specified type containing the values from the {@link PactDslJsonBody}.
   * @throws IOException Thrown if the conversion fails.
   */
  public static <T> Result<T> toObject(PactDslJsonBody body, Class<T> type) throws IOException {
    String jsonString = toJson(body);

    T object = JsonHelper.fromJson(jsonString, type);
    return new Result<T>(object, jsonString);
  }

  /**
   * Converts the specified object to a JSON object.
   *
   * @param object The object to convert.
   * @return Return the JSON as {@link String}.
   * @throws JsonProcessingException
   *
   */
  public static String toJson(Object object) throws JsonProcessingException {
    return JsonHelper.toJson(object);
  }

  /**
   * Converts the specified {@link PactDslJsonBody} to a JSON object.
   *
   * @param body The {@link PactDslJsonBody} describing the JSON structure.
   * @return Return the JSON as {@link String}.
   *
   */
  public static String toJson(PactDslJsonArray array) {
    StringWriter libOutputWriter = new StringWriter();
    ((org.json.JSONArray) array.getBody()).write(libOutputWriter);

    String jsonString = libOutputWriter.getBuffer()
        .toString();
    return jsonString;
  }

  /**
   * Converts the specified {@link PactDslJsonBody} to a JSON object.
   *
   * @param body The {@link PactDslJsonBody} describing the JSON structure.
   * @return Return the JSON as {@link String}.
   *
   */
  public static String toJson(PactDslJsonBody body) {
    StringWriter libOutputWriter = new StringWriter();
    ((org.json.JSONObject) body.getBody()).write(libOutputWriter);

    String jsonString = libOutputWriter.getBuffer()
        .toString();
    return jsonString;
  }

  /**
   * Compares the specified expected object with the result from converting a {@link PactDslJsonBody} and expects, that
   * the structures are equal. If so, this method returns. If any differences occur or the conversion fails due to
   * structural differences, this method throws an {@link AssertionError} and prints the JSON differences to sys/out.
   *
   * @param <T> The target type
   * @param expectedObject The expected object
   * @param result The actual result from converting a {@link PactDslJsonBody}.
   * @throws Exception Thrown if any parsing error occurs due to structural differences.
   */
  public static <T> void assertObjectEqualsResult(Object expectedObject, Result<T> result) throws Exception {
    boolean equals = expectedObject.equals(result.getObject());

    String expectedJson = JsonHelper.toJson(expectedObject);
    String actualJson = result.getJsonString();

    if (!equals) {
      System.out.println("The given objects do not equal - see diff:");
      System.out.println("Expected JSON String: " + expectedJson);
      System.out.println("  Actual JSON String: " + actualJson);

      throw new AssertionError("The given objects do not equal - see diff!");
    }
  }
}
