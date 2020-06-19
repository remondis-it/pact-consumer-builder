package com.remondis.cdc.consumer.pactbuilder.usecases.primitiveList;

import static java.util.Arrays.asList;

import org.junit.Test;

import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;
import com.remondis.cdc.consumer.pactbuilder.TestUtil;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class ListOfLongsTest {

  @Test
  public void shouldCreatePact() {
    ListLongDto dto = new ListLongDto(asList(1L, 2L, 3L));
    PactDslJsonBody pact = ConsumerExpects.type(ListLongDto.class)
        .build(dto);
    String json = TestUtil.toJson(pact);
    System.out.println(json);
  }

}
