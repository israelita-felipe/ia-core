package com.ia.core.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.Collection;
/**
 * Classe responsável por abstract jasper collection report.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AbstractJasperCollectionReport
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public abstract class AbstractJasperCollectionReport<T>
  extends AbstractJasperReport<Collection<T>> {

  /**
   * @param data
   */
  public AbstractJasperCollectionReport(String title, Collection<T> data) {
    super(data, title);
  }

  @Override
  JRDataSource dataSource() {
    return new JRBeanCollectionDataSource(getData());
  }

}
