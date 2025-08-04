package com.ia.core.view.utils;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import com.ia.core.report.AbstractJasperReport;
import com.ia.core.report.AbstractJasperReport.ExportType;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

/**
 * @author Israel Araújo
 */
public class ReportUtils {
  /**
   * Realiza a abertura de um relatório utilizando vaadin
   *
   * @param report {@link AbstractJasperReport} a ser exibido
   * @param type   tipo de exportação
   */
  public static void openReport(AbstractJasperReport<?> report,
                                ExportType type) {
    String id = UUID.randomUUID().toString();
    StreamResource resource = new StreamResource(id, () -> {
      try {
        return new ByteArrayInputStream(type.export(report));
      } catch (Exception e) {
        throw new RuntimeException(e.getLocalizedMessage(), e);
      }
    });
    resource
        .setHeader("Content-Disposition",
                   "filename=\"" + id + "." + type.getExtension() + "\"");
    resource.setContentType(type.getContentType());
    StreamRegistration registration = VaadinSession.getCurrent()
        .getResourceRegistry().registerResource(resource);

    UI.getCurrent().getPage()
        .open(registration.getResourceUri().toString());
  }

}
