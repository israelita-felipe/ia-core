package com.ia.core.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.Collection;
/**
 * Classe base abstrata para relatórios JasperReports baseados em coleções.
 * <p>
 * Extende AbstractJasperReport para trabalhar com coleções de objetos (Collection<T>).
 * Utiliza JRBeanCollectionDataSource como fonte de dados, permitindo que qualquer
 * coleção de Java Beans seja usada como fonte de dados para o relatório.
 * </p>
 * <p>
 * Subclasses devem fornecer o caminho do template do relatório através do método
 * getReportPath() herdado da classe pai.
 * </p>
 *
 * @param <T> Tipo dos elementos na coleção de dados
 * @author IA
 * @since 1.0
 */
public abstract class AbstractJasperCollectionReport<T>
  extends AbstractJasperReport<Collection<T>> {

  /**
   * Construtor do relatório baseado em coleção.
   *
   * @param title Título do relatório
   * @param data  Coleção de dados para o relatório
   */
  public AbstractJasperCollectionReport(String title, Collection<T> data) {
    super(data, title);
  }

  /**
   * Cria a fonte de dados para o relatório.
   * <p>
   * Utiliza JRBeanCollectionDataSource para transformar a coleção de objetos
   * em uma fonte de dados compatível com JasperReports.
   * </p>
   *
   * @return JRBeanCollectionDataSource contendo os dados da coleção
   */
  @Override
  JRDataSource dataSource() {
    return new JRBeanCollectionDataSource(getData());
  }

}
