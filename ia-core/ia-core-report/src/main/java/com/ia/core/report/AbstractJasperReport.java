package com.ia.core.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @author Israel Araújo
 * @return
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
     * @param contentType
     * @param extension
     */
    private ExportType(String contentType, String extension) {
      this.contentType = contentType;
      this.extension = extension;
    }

    public abstract byte[] export(AbstractJasperReport<?> report)
      throws JRException;
  }

  @Getter
  private final T data;
  @Getter
  private String title;

  public AbstractJasperReport(final T data, final String title) {
    this.data = data;
    this.title = title;
  }

  /**
   * @return
   * @throws JRException
   */
  abstract JRDataSource dataSource()
    throws JRException;

  protected ByteArrayOutputStream exportPDF()
    throws JRException {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JasperExportManager.exportReportToPdfStream(print(), out);
    return out;
  }

  protected ByteArrayOutputStream exportXLS()
    throws JRException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JRXlsExporter exporter = new JRXlsExporter();
    exporter.setExporterInput(new SimpleExporterInput(print()));
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
    exporter.exportReport();
    return out;
  }

  public abstract String getReportPath();

  public Map<String, Object> params() {
    return new HashMap<>();
  }

  public JasperPrint print()
    throws JRException {
    return print(report(), params());
  }

  public JasperPrint print(JasperReport report, Map<String, Object> params)
    throws JRException {
    return JasperFillManager.fillReport(report, params, dataSource());
  }

  public JasperReport report()
    throws JRException {
    return report(getReportPath());
  }

  public JasperReport report(InputStream reportStream)
    throws JRException {
    return JasperCompileManager.compileReport(reportStream);
  }

  public JasperReport report(String reportPath)
    throws JRException {
    return report(getClass().getClassLoader()
        .getResourceAsStream(reportPath));
  }
}
