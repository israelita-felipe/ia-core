package com.ia.core.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import java.io.ByteArrayInputStream;
/**
 * Classe base abstrata para relatórios JasperReports baseados em XML.
 * <p>
 * Extende AbstractJasperReport para trabalhar com dados em formato XML.
 * Utiliza JRXmlDataSource como fonte de dados, permitindo que dados XML
 * sejam usados diretamente como fonte de dados para o relatório.
 * </p>
 * <p>
 * Subclasses devem fornecer o caminho do template do relatório através do método
 * getReportPath() herdado da classe pai.
 * </p>
 *
 * @author IA
 * @since 1.0
 */
public abstract class AbstractJasperXMLReport
  extends AbstractJasperReport<String> {

  /**
   * Construtor do relatório baseado em XML.
   *
   * @param title Título do relatório
   * @param data  String contendo os dados XML para o relatório
   */
  public AbstractJasperXMLReport(String title, String data) {
    super(data, title);
  }

  /**
   * Cria a fonte de dados para o relatório.
   * <p>
   * Utiliza JRXmlDataSource para transformar a string XML em uma fonte de dados
   * compatível com JasperReports através de um ByteArrayInputStream.
   * </p>
   *
   * @return JRXmlDataSource contendo os dados XML
   * @throws JRException Se ocorrer erro ao processar o XML
   */
  @Override
  JRDataSource dataSource()
    throws JRException {
    return new JRXmlDataSource(new ByteArrayInputStream(getData()
        .getBytes()));
  }

}
