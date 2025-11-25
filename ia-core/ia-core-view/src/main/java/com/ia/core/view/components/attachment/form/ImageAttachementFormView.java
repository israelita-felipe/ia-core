package com.ia.core.view.components.attachment.form;

import java.util.Base64;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.dom.PropertyChangeEvent;
import com.vaadin.flow.server.streams.UploadHandler;
import com.vaadin.flow.server.streams.UploadMetadata;

/**
 * Abstração de um formulário de anexo para imagens
 *
 * @param <T> Tipo do anexo
 */
public abstract class ImageAttachementFormView<T extends AttachmentDTO<?>>
  extends AttachmentFormView<T> {
  /** Serial UID */
  private static final long serialVersionUID = 3767077429828213275L;
  /** Imagem */
  private Image image;

  /**
   * @param viewModel {@link AttachmentFormViewModel}
   */
  public ImageAttachementFormView(AttachmentFormViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  public Upload createUploadField(String label, String help,
                                  UploadHandler receiver) {
    Upload uploadField = super.createUploadField(label, help, receiver);
    uploadField.setAcceptedFileTypes(getAcceptedImageTypes());
    return uploadField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    createImageField();
  }

  /**
   *
   */
  public void createImageField() {
    if (image == null) {
      image = new Image();
    }
    add(image, 6);
    refreshImage(getViewModel().getModel());
  }

  @Override
  public void refreshFields() {
    super.refreshFields();
    refreshImage(getViewModel().getModel());
  }

  /**
   * @param model Modelo para atualizar a imagem
   */
  public void refreshImage(T model) {
    if (model != null && model.hasContent()) {
      try {
        refreshImage(model.getMediaType(), model.getContent());
      } catch (Exception e) {
        image.setAlt(e.getLocalizedMessage());
      }
    }
  }

  @Override
  public TextField createNomeField(String label, String help) {
    TextField field = super.createNomeField(label, help);
    field.setReadOnly(true);
    return field;
  }

  @Override
  protected void onSuccess(UploadMetadata metadata, byte[] data) {
    super.onSuccess(metadata, data);
    refreshImage(metadata.contentType(), data);
  }

  @Override
  protected void onReset(PropertyChangeEvent listener,
                         UploadHandler receiver) {
    super.onReset(listener, receiver);
    this.image.setSrc((String) null);
  }

  /**
   * Atualiza o campo de imagem
   *
   * @param mediaType o tipo de imagem
   * @param content   o conteúdo da imagem
   */
  public void refreshImage(String mediaType, byte[] content) {
    if (content != null) {
      refreshImage(mediaType, Base64.getEncoder().encodeToString(content));
    }
  }

  /**
   * Atualiza o campo de imagem
   *
   * @param mediaType     o tipo de imagem
   * @param base64Content o conteúdo da imagem em base64
   */
  public void refreshImage(String mediaType, String base64Content) {
    if (base64Content != null) {
      this.image.setSrc(String.format("data:%s;base64,%s", mediaType,
                                      base64Content));
    } else {
      this.image.setSrc((String) null);
    }
  }

  /**
   * @return tipos de imagem aceitos
   */
  public String[] getAcceptedImageTypes() {
    return new String[] { "image/*" };
  }
}
