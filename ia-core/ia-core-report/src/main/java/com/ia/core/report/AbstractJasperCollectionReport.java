package com.ia.core.report;

import java.util.Collection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Israel Araújo
 */
public abstract class AbstractJasperCollectionReport<T>
  extends AbstractJasperReport<Collection<T>> {

  /**
   * @param data
   */
  public AbstractJasperCollectionReport(Collection<T> data) {
    super(data);
  }

  @Override
  JRDataSource dataSource() {
    return new JRBeanCollectionDataSource(getData());
  }

}
