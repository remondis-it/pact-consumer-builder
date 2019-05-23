package com.remondis.cdc.consumer.pactbuilder.api;

import com.remondis.cdc.consumer.pactbuilder.ConsumerBuilder;
import com.remondis.cdc.consumer.pactbuilder.ConsumerExpects;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

public class ConsumerApiTest {

  // @Test
  public void apiTest() {
    ConsumerBuilder<?> anotherConsumer = null;
    ConsumerBuilder<?> invoiceRecipientAddressConsumer = null;
    ConsumerBuilder<?> orgUnitConsumer = null;
    PactDslJsonBody pactDslJsonBody = null;

    ConsumerExpects.type(CustomerExternalResource.class)
        .referencing(anotherConsumer)
        .field(CustomerExternalResource::getCustomerNumber)
        .as(pact -> pact.stringType("customerNumber", "124"))
        .field(CustomerExternalResource::getName)
        .as("customFieldName")
        .field(CustomerExternalResource::getInvoiceRecipientAddress)
        .as(invoiceRecipientAddressConsumer)
        .field(CustomerExternalResource::getOrganizationalUnit)
        .as(orgUnitConsumer)
        .field(CustomerExternalResource::getOrganizationalUnit)
        .as("customFieldName", orgUnitConsumer)
        .build(pactDslJsonBody, new CustomerExternalResource());
  }

}
