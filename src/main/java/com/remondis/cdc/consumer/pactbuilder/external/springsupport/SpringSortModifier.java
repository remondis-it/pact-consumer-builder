package com.remondis.cdc.consumer.pactbuilder.external.springsupport;

import org.springframework.data.domain.Sort;

import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilder;
import com.remondis.cdc.consumer.pactbuilder.PactDslModifier;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Provides a {@link PactDslModifier} for a Spring REST {@link Sort} object. This object is not a Java Bean, therefore
 * the JSON body is specified manually.
 *
 * @author schuettec
 *
 */
public class SpringSortModifier {

  /**
   * @return Returns a new {@link PactDslModifier} to specify a {@link Sort} object in your PACT definition. Use the
   *         modifier in conjunction with {@link ConsumerBuilder#useTypeMapping(Class, PactDslModifier)} to register the
   *         type mapping.
   */
  public static PactDslModifier<Sort> sortModifier() {
    return (body, fieldName, fieldValue) -> {
      return (PactDslJsonBody) body.object(fieldName)
          .stringType("direction", "ASC")
          .stringType("property", "propertyName")
          .stringType("ignoreCase", "false")
          .stringType("nullHandling", "NATIVE")
          .stringType("ascending", "true")
          .stringType("descending", "false")
          .closeObject();
    };
  }

}
