package com.ia.core.report;

import java.io.ByteArrayInputStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

/**
 * @author Israel Araújo
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
