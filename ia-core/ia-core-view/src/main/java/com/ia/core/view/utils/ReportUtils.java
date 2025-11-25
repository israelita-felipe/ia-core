package com.ia.core.view.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.ia.core.report.AbstractJasperReport;
import com.ia.core.report.AbstractJasperReport.ExportType;
import com.ia.core.report.DynamicJasperJsonObjectStreamReport;
import com.ia.core.report.DynamicJasperObjectArrayStreamReport;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.util.JsonUtil;
import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
import com.ia.core.view.components.dialog.frame.FrameDialogViewFactory;
import com.ia.core.view.components.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.HttpStatusCode;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public class ReportUtils {
  /**
   * Realiza a abertura de um relatório utilizando vaadin
   *
   * @param report {@link AbstractJasperReport} a ser exibido
   * @param type   tipo de exportação
   */
  public static void viewReport(AbstractJasperReport<?> report,
                                ExportType type) {
    try {
      FrameDialogViewFactory.show(report.getTitle(), "data:"
          + type.getContentType() + ";base64,"
          + Base64.getEncoder().encodeToString(type.export(report)));
    } catch (Exception e) {
      ExceptionViewFactory.showError(e);
    }
  }

  public static void downloadReport(AbstractJasperReport<?> report,
                                ExportType type) {

    DownloadHandler resource = new InputStreamDownloadHandler(event -> {
      try {
        byte[] data = type.export(report);
        return new DownloadResponse(new ByteArrayInputStream(data),
                                    UUID.randomUUID().toString(),
                                    type.getContentType(), data.length);
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
        return DownloadResponse.error(HttpStatusCode.INTERNAL_SERVER_ERROR,
                                      e.getLocalizedMessage());
      }
    });
    StreamRegistration registration = VaadinSession.getCurrent()
        .getResourceRegistry().registerResource(resource);

    UI.getCurrent().getPage()
        .open(registration.getResourceUri().toString());

  }

  public static void openObjectArrayReport(Translator translator,
                                           Class<?> dataType, String title,
                                           ListView<?> grid,
                                           ExportType type) {
    String[] columnKeys = extractKeys(grid);
    String[] columnLabels = extractLabels(dataType, columnKeys, translator);
    Supplier<Stream<Object[]>> dataSupplier = () -> grid
        .getGenericDataView().getItems().map(item -> {
          String json = JsonUtil.toJson(item);
          Map<String, Object> values = JsonUtil
              .getProperties(json, 0, true, AbstractBaseEntityDTO.CAMPOS.ID,
                             AbstractBaseEntityDTO.CAMPOS.VERSION);
          Object[] object = new Object[columnKeys.length];
          for (int i = 0; i < object.length; i++) {
            if (values.containsKey(columnKeys[i])) {
              object[i] = values.get(columnKeys[i]);
            }
          }
          return object;
        });
    viewReport(new DynamicJasperObjectArrayStreamReport(title, columnLabels,
                                                        dataSupplier),
               type);
  }

  public static void openJsonReport(Translator translator,
                                    Class<?> dataType, String title,
                                    ListView<?> grid, ExportType type) {
    String[] columnKeys = extractKeys(grid);
    String[] columnLabels = extractLabels(dataType, columnKeys, translator);
    Stream<String> dataSupplier = grid.getGenericDataView().getItems()
        .map(item -> {
          return JsonUtil.toJson(item);
        });
    viewReport(new DynamicJasperJsonObjectStreamReport(title, columnKeys,
                                                       dataSupplier),
               type);
  }

  /**
   * @param dataType
   * @param columnKeys
   * @param translator
   * @return
   */
  private static String[] extractLabels(Class<?> dataType,
                                        String[] columnKeys,
                                        Translator translator) {
    String[] labels = new String[columnKeys.length];
    String objectName = camelToDot(dataType.getSimpleName()) + ".";
    for (int i = 0; i < labels.length; i++) {
      labels[i] = translator
          .getTranslation(camelToDot(objectName + columnKeys[i]));
    }
    return labels;
  }

  /**
   * Extrai os cabeçalhos das colunas do Grid
   */
  private static String[] extractKeys(ListView<?> grid) {
    List<String> headers = new ArrayList<>();

    // Percorre todas as colunas do grid
    grid.getColumns().forEach(column -> {
      String header = getColumnHeader(column);
      if (header != null) {
        headers.add(header);
      }
    });

    return headers.toArray(new String[0]);
  }

  /**
   * Obtém o cabeçalho de uma coluna, tratando diferentes cenários
   */
  private static String getColumnHeader(ListView.Column<?> column) {
    return column.getKey();
  }

  public static String camelToDot(String camelCase) {
    // Adiciona um ponto antes de letras maiúsculas (exceto a primeira) e
    // converte para minúsculas
    return camelCase.replace("DTO", "").replaceAll("(?<=.)(?=[A-Z])", ".")
        .toLowerCase();
  }
}
