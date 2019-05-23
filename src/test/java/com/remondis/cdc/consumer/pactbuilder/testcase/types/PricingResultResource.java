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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((article == null) ? 0 : article.hashCode());
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((extraServices == null) ? 0 : extraServices.hashCode());
    result = prime * result + ((metaData == null) ? 0 : metaData.hashCode());
    result = prime * result + ((total == null) ? 0 : total.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PricingResultResource other = (PricingResultResource) obj;
    if (article == null) {
      if (other.article != null)
        return false;
    } else if (!article.equals(other.article))
      return false;
    if (currency == null) {
      if (other.currency != null)
        return false;
    } else if (!currency.equals(other.currency))
      return false;
    if (extraServices == null) {
      if (other.extraServices != null)
        return false;
    } else if (!extraServices.equals(other.extraServices))
      return false;
    if (metaData == null) {
      if (other.metaData != null)
        return false;
    } else if (!metaData.equals(other.metaData))
      return false;
    if (total == null) {
      if (other.total != null)
        return false;
    } else if (!total.equals(other.total))
      return false;
    return true;
  }

}