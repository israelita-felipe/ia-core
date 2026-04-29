package com.ia.core.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import java.io.ByteArrayInputStream;
/**
 * Classe responsável por abstract jasper x m l report.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AbstractJasperXMLReport
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public abstract class AbstractJasperXMLReport
  extends AbstractJasperReport<String> {

  /**
   * @param data
   */
  public AbstractJasperXMLReport(String title, String data) {
    super(data, title);
  }

  @Override
  JRDataSource dataSource()
    throws JRException {
    return new JRXmlDataSource(new ByteArrayInputStream(getData()
        .getBytes()));
  }

}
