package com.ia.core.report;

import lombok.Getter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe base abstrata para geração de relatórios JasperReports.
 * <p>
 * Fornece a infraestrutura comum para criar, compilar e exportar relatórios
 * JasperReports em diferentes formatos (PDF, XLS). Subclasses devem implementar
 * o método dataSource() para fornecer a fonte de dados específica e getReportPath()
 * para indicar o local do template do relatório.
 * </p>
 * <p>
 * Suporta exportação através do enum ExportType que define os formatos disponíveis.
 * </p>
 *
 * @param <T> Tipo de dados do relatório
 * @author IA
 * @since 1.0
 */
public abstract class AbstractJasperReport<T> {

  public static enum ExportType {
    PDF("application/pdf", "pdf") {
      @Override
      public byte[] export(AbstractJasperReport<?> report)
        throws JRException {
        return report.exportPDF().toByteArray();
      }
    },
    XLS("application/vnd.ms-excel", "xls") {

      @Override
      public byte[] export(AbstractJasperReport<?> report)
        throws JRException {
        return report.exportXLS().toByteArray();
      }

    };

    @Getter
    private final String contentType;
    @Getter
    private final String extension;

    /**
     * Construtor do tipo de exportação.
     *
     * @param contentType Tipo MIME do conteúdo exportado (ex: application/pdf)
     * @param extension  Extensão do arquivo exportado (ex: pdf)
     */
    private ExportType(String contentType, String extension) {
      this.contentType = contentType;
      this.extension = extension;
    }

    /**
     * Exporta o relatório no formato especificado.
     *
     * @param report Instância do relatório a ser exportado
     * @return Array de bytes contendo o relatório exportado
     * @throws JRException Se ocorrer erro durante a exportação
     */
    public abstract byte[] export(AbstractJasperReport<?> report)
      throws JRException;
  }

  @Getter
  private final T data;
  @Getter
  private String title;

  /**
   * Construtor do relatório.
   *
   * @param data  Dados do relatório
   * @param title Título do relatório
   */
  public AbstractJasperReport(final T data, final String title) {
    this.data = data;
    this.title = title;
  }

  /**
   * Cria a fonte de dados para o relatório.
   * <p>
   * Método abstrato que deve ser implementado pelas subclasses para fornecer
   * a fonte de dados apropriada (JRDataSource) baseada no tipo de dados.
   * </p>
   *
   * @return Fonte de dados JasperReports
   * @throws JRException Se ocorrer erro ao criar a fonte de dados
   */
  abstract JRDataSource dataSource()
    throws JRException;

  /**
   * Exporta o relatório para formato PDF.
   * <p>
   * Gera o relatório em PDF usando o JasperPrint e escreve em um ByteArrayOutputStream.
   * </p>
   *
   * @return ByteArrayOutputStream contendo o relatório em PDF
   * @throws JRException Se ocorrer erro durante a exportação
   */
  protected ByteArrayOutputStream exportPDF()
    throws JRException {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JasperExportManager.exportReportToPdfStream(print(), out);
    return out;
  }

  /**
   * Exporta o relatório para formato XLS (Excel).
   * <p>
   * Gera o relatório em XLS usando o JasperPrint e escreve em um ByteArrayOutputStream.
   * </p>
   *
   * @return ByteArrayOutputStream contendo o relatório em XLS
   * @throws JRException Se ocorrer erro durante a exportação
   */
  protected ByteArrayOutputStream exportXLS()
    throws JRException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JRXlsExporter exporter = new JRXlsExporter();
    exporter.setExporterInput(new SimpleExporterInput(print()));
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
    exporter.exportReport();
    return out;
  }

  /**
   * Retorna o caminho do arquivo de template Jasper (.jrxml ou .jasper).
   * <p>
   * Método abstrato que deve ser implementado pelas subclasses para fornecer
   * o caminho do arquivo de template. Para relatórios dinâmicos, este método
   * pode lançar IllegalArgumentException.
   * </p>
   *
   * @return Caminho do arquivo de template no classpath
   */
  public abstract String getReportPath();

  /**
   * Retorna os parâmetros do relatório.
   * <p>
   * Cria um mapa vazio de parâmetros. Subclasses podem sobrescrever este método
   * para fornecer parâmetros específicos do relatório.
   * </p>
   *
   * @return Mapa de parâmetros para o relatório
   */
  public Map<String, Object> params() {
    return new HashMap<>();
  }

  /**
   * Gera o JasperPrint do relatório.
   * <p>
   * Compila o relatório e preenche com os dados e parâmetros padrão.
   * </p>
   *
   * @return JasperPrint pronto para exportação
   * @throws JRException Se ocorrer erro ao gerar o relatório
   */
  public JasperPrint print()
    throws JRException {
    return print(report(), params());
  }

  /**
   * Gera o JasperPrint do relatório com parâmetros específicos.
   * <p>
   * Preenche o relatório compilado com a fonte de dados e parâmetros fornecidos.
   * </p>
   *
   * @param report Relatório compilado JasperReports
   * @param params Parâmetros do relatório
   * @return JasperPrint pronto para exportação
   * @throws JRException Se ocorrer erro ao preencher o relatório
   */
  public JasperPrint print(JasperReport report, Map<String, Object> params)
    throws JRException {
    return JasperFillManager.fillReport(report, params, dataSource());
  }

  /**
   * Compila o relatório a partir do caminho do template.
   * <p>
   * Carrega o arquivo de template e compila para JasperReport.
   * </p>
   *
   * @return Relatório compilado JasperReports
   * @throws JRException Se ocorrer erro ao compilar o relatório
   */
  public JasperReport report()
    throws JRException {
    return report(getReportPath());
  }

  /**
   * Compila o relatório a partir de um InputStream.
   * <p>
   * Compila o template de relatório fornecido como InputStream.
   * </p>
   *
   * @param reportStream InputStream contendo o template do relatório
   * @return Relatório compilado JasperReports
   * @throws JRException Se ocorrer erro ao compilar o relatório
   */
  public JasperReport report(InputStream reportStream)
    throws JRException {
    return JasperCompileManager.compileReport(reportStream);
  }

  /**
   * Compila o relatório a partir do caminho no classpath.
   * <p>
   * Carrega o arquivo de template do classpath e compila para JasperReport.
   * </p>
   *
   * @param reportPath Caminho do arquivo de template no classpath
   * @return Relatório compilado JasperReports
   * @throws JRException Se o arquivo não for encontrado ou ocorrer erro na compilação
   */
  public JasperReport report(String reportPath)
    throws JRException {
    Objects.requireNonNull(reportPath, "Caminho do relatório não pode ser null");
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(reportPath);
    if (inputStream == null) {
      throw new JRException("Relatório não encontrado no caminho: " + reportPath);
    }
    return report(inputStream);
  }
}
