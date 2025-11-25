package com.ia.core.view.components.attachment.list;

import java.util.UUID;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.attachment.dto.AttachmentTranslator;
import com.ia.core.view.components.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;

/**
 * Implementação padrão de um {@link ListView} para {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public class AttachmentListView<T extends AttachmentDTO<?>>
  extends ListView<T> {

  /** Serial UID */
  private static final long serialVersionUID = -7594986986702785508L;

  /**
   * @param viewModel {@link AttachmentListViewModel}
   */
  public AttachmentListView(AttachmentListViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("filename").setHeader($(AttachmentTranslator.NOME));
    addColumn("mediaType").setHeader($(AttachmentTranslator.TIPO));
    addColumn("size").setHeader($(AttachmentTranslator.TAMANHO));
    addColumn(createDownloadAttachmentRenderer());
  }

  /**
   * @return {@link ComponentRenderer}
   */
  protected ComponentRenderer<Button, T> createDownloadAttachmentRenderer() {
    return new ComponentRenderer<Button, T>(this::createDownloadButton);
  }

  /**
   * @param model {@link AttachmentDTO}
   * @return {@link Button}
   */
  protected Button createDownloadButton(T model) {
    return new Button(VaadinIcon.DOWNLOAD.create(), onClick -> {
      download(model);
    });
  }

  /**
   * @param model {@link AttachmentDTO} a ser baixado
   */
  protected void download(T model) {
    UUID id = model.getId();
    id = id == null ? UUID.randomUUID() : id;
    DownloadHandler resource = new InputStreamDownloadHandler(event -> {
      return new DownloadResponse(getViewModel().download(model),
                                  model.getFilename(), model.getMediaType(),
                                  model.getSize());

    });
    StreamRegistration registration = VaadinSession.getCurrent()
        .getResourceRegistry().registerResource(resource);

    UI.getCurrent().getPage()
        .open(registration.getResourceUri().toString());
  }

  @Override
  public AttachmentListViewModel<T> getViewModel() {
    return (AttachmentListViewModel<T>) super.getViewModel();
  }
}
