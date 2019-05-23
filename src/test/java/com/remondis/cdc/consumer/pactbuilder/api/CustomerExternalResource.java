package com.remondis.cdc.consumer.pactbuilder.api;

public class CustomerExternalResource {

  private Long id;
  private String name;
  private String customerNumber;
  private String vatRegNumber;
  private String taxNumber;
  private boolean isFiscalUnit;
  private boolean taxExempt;
  private boolean verified;
  private AddressExternalResource invoiceRecipientAddress;
  private OrganizationalUnitResource organizationalUnit;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getVatRegNumber() {
    return vatRegNumber;
  }

  public void setVatRegNumber(String vatRegNumber) {
    this.vatRegNumber = vatRegNumber;
  }

  public String getTaxNumber() {
    return taxNumber;
  }

  public void setTaxNumber(String taxNumber) {
    this.taxNumber = taxNumber;
  }

  public boolean isFiscalUnit() {
    return isFiscalUnit;
  }

  public void setFiscalUnit(boolean isFiscalUnit) {
    this.isFiscalUnit = isFiscalUnit;
  }

  public boolean isTaxExempt() {
    return taxExempt;
  }

  public void setTaxExempt(boolean taxExempt) {
    this.taxExempt = taxExempt;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public AddressExternalResource getInvoiceRecipientAddress() {
    return invoiceRecipientAddress;
  }

  public void setInvoiceRecipientAddress(AddressExternalResource invoiceRecipientAddress) {
    this.invoiceRecipientAddress = invoiceRecipientAddress;
  }

  public OrganizationalUnitResource getOrganizationalUnit() {
    return organizationalUnit;
  }

  public void setOrganizationalUnit(OrganizationalUnitResource organizationalUnit) {
    this.organizationalUnit = organizationalUnit;
  }

}
