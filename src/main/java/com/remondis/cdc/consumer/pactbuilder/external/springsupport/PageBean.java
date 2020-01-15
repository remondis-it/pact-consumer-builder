package com.remondis.cdc.consumer.pactbuilder.external.springsupport;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * This is a JavaBean representing a {@link PageImpl}. This class must be used to generate pact consumer from
 * {@link Page}, since the pact consumer needs a valid JavaBean to build the pact configuration.
 *
 * @param <T> The type of the page content.
 */
public class PageBean<T> extends PageImpl<T> {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public PageBean() {
    super(Collections.emptyList());
  }

  /**
   * See {@link PageImpl}.
   */
  public PageBean(List<T> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  /**
   * See {@link PageImpl}.
   */
  public PageBean(List<T> content) {
    super(content);
  }

  @Override
  public List<T> getContent() {
    return super.getContent();
  }

}
