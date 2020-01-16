package com.remondis.cdc.consumer.pactbuilder.external.springsupport;

import static com.remondis.cdc.consumer.pactbuilder.external.springsupport.SpringSortModifier.sortModifier;
import static java.util.Arrays.asList;

import java.io.IOException;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.data.domain.Sort;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class SpringPageSupportTest {

  @Test // Should not throw exceptions
  public void shuldGeneratePactFromPage() throws IOException {
    PageBean<Dto> pageBean = new PageBean<>(asList(new Dto("forename1", "name1"), new Dto("forename2", "name2")));

    PactDslJsonBody pactDslJsonBody = ConsumerExpects.type(PageBean.class)
        .useTypeMapping(Sort.class, sortModifier())
        .field(PageBean::getContent)
        .as(ConsumerExpects.type(Dto.class))
        .build(new PactDslJsonBody(), pageBean);

    String actualJson = TestUtil.toJson(pactDslJsonBody);
    JSONAssert.assertEquals(
        "{\"number\":0,\"last\":true,\"numberOfElements\":2,\"size\":2,\"totalPages\":1,\"sort\":{\"nullHandling\":\"NATIVE\",\"ignoreCase\":\"false\",\"property\":\"propertyName\",\"ascending\":\"true\",\"descending\":\"false\",\"direction\":\"ASC\"},\"first\":true,\"content\":[{\"forename\":\"forename2\",\"name\":\"name2\"}],\"totalElements\":2}",
        actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
