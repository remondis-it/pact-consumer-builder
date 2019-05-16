package com.remondis.cdc.consumer.pactbuilder.testcase.types;

import java.util.List;

/**
 *
 */
public class PricingResultResource {

  private CurrencyResource currency;

  private PricingResultTotalResource total;

  private PricingResultArticleResource article;

  private List<PricingResultExtraServiceResource> extraServices;

  private PricingResultMetaDataResource metaData;

  public PricingResultResource(CurrencyResource currency, PricingResultTotalResource total,
      PricingResultArticleResource article, List<PricingResultExtraServiceResource> extraServices,
      PricingResultMetaDataResource metaData) {
    super();
    this.currency = currency;
    this.total = total;
    this.article = article;
    this.extraServices = extraServices;
    this.metaData = metaData;
  }

  public PricingResultResource() {
    super();
    // TODO Auto-generated constructor stub
  }

  public CurrencyResource getCurrency() {
    return currency;
  }

  public void setCurrency(CurrencyResource currency) {
    this.currency = currency;
  }

  public PricingResultTotalResource getTotal() {
    return total;
  }

  public void setTotal(PricingResultTotalResource total) {
    this.total = total;
  }

  public PricingResultArticleResource getArticle() {
    return article;
  }

  public void setArticle(PricingResultArticleResource article) {
    this.article = article;
  }

  public List<PricingResultExtraServiceResource> getExtraServices() {
    return extraServices;
  }

  public void setExtraServices(List<PricingResultExtraServiceResource> extraServices) {
    this.extraServices = extraServices;
  }

  public PricingResultMetaDataResource getMetaData() {
    return metaData;
  }

  public void setMetaData(PricingResultMetaDataResource metaData) {
    this.metaData = metaData;
  }

}