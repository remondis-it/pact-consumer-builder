package com.remondis.cdc.consumer.pactbuilder.external.springsupport;

import java.util.Collections;

import org.springframework.data.domain.Sort;

/**
 * This is a JavaBean representing a {@link Sort}. This class must be used to generate pact consumer from
 * {@link Sort}, since the pact consumer needs a valid JavaBean to build the pact configuration.
 *
 */
public class SortBean extends Sort {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public SortBean() {
    super(Collections.emptyList());
  }

}
