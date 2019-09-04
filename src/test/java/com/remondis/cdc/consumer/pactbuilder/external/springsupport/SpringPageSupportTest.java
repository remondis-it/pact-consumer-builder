package com.remondis.cdc.consumer.pactbuilder.external.springsupport;

import static java.util.Arrays.asList;

import org.junit.Test;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class SpringPageSupportTest {

  @Test // Should not throw exceptions
  public void shuldGeneratePactFromPage() {
    PageBean<Dto> pageBean = new PageBean<>(asList(new Dto("forename1", "name1"), new Dto("forename2", "name2")));
    ConsumerExpects.type(PageBean.class)
        .field(PageBean::getSort)
        .as(ConsumerExpects.type(SortBean.class))
        .field(PageBean::getContent)
        .as(ConsumerExpects.type(Dto.class))
        .build(new PactDslJsonBody(), pageBean);
  }

}
