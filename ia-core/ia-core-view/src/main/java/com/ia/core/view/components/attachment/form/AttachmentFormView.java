package com.ia.core.view.components.attachment.form;

import java.util.Base64;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.attachment.dto.AttachmentTranslator;
import com.ia.core.service.util.ZipUtil;
import com.ia.core.view.components.form.FormView;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.dom.PropertyChangeEvent;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import com.vaadin.flow.server.streams.UploadMetadata;

import elemental.json.impl.JreJsonArray;

/**
 * Implementação padrão do formulário de anexo
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
@SuppressWarnings("serial")
public class AttachmentFormView<T extends AttachmentDTO<?>>
  extends FormView<T> {
  /** Campo de upload */
  private Upload upload;

  /**
   * @param viewModel {@link AttachmentFormViewModel}
   */
  public AttachmentFormView(AttachmentFormViewModel<T> viewModel) {
    super(viewModel);
  }

  /**
   * Cria o campo de descrição
   *
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextArea}
   */
  public TextArea createDescricaoField(String label, String help) {
    TextArea field = createTextArea(label, help);
    add(field, 6);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    if (!isReadOnly()) {
      createUploader();
    }
    bind(AttachmentDTO.CAMPOS.FILE_NAME,
         createNomeField($(AttachmentTranslator.NOME),
                         $(AttachmentTranslator.NOME)));
    bind(AttachmentDTO.CAMPOS.DESCRIPTION,
         createDescricaoField($(AttachmentTranslator.DESCRICAO),
                              $(AttachmentTranslator.HELP.DESCRICAO)));
  }

  @Override
  public void refreshFields() {
    super.refreshFields();
    this.upload.setVisible(!isReadOnly());
  }

  /**
   * Cria o campo de nome
   *
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public TextField createNomeField(String label, String help) {
    TextField field = createTextField(label, help);
    field.setReadOnly(true);
    add(field, 6);
    return field;
  }

  /**
   * Cria o campo de upload
   */
  protected void createUploader() {
    UploadHandler receiver = createUploadHandler();
    this.upload = createUploadField($(AttachmentTranslator.ATTACHMENT),
                                    $(AttachmentTranslator.HELP.ATTACHMENT),
                                    receiver);
    this.upload.getElement().addPropertyChangeListener("files",
                                                       listener -> {
                                                         onFileChangeListener(listener,
                                                                              receiver);
                                                       });
    add(this.upload, 6);
  }

  /**
   * @return {@link UploadHandler}
   */
  public UploadHandler createUploadHandler() {
    return new InMemoryUploadHandler((metadata, data) -> {
      onSuccess(metadata, data);
    });
  }

  @Override
  public AttachmentFormViewModel<T> getViewModel() {
    return super.getViewModel().cast();
  }

  /**
   * Evento a ser disparado quando houver mudança no arquivo do uploader
   *
   * @param listener {@link PropertyChangeEvent}
   * @param receiver {@link UploadHandler}
   */
  protected void onFileChangeListener(PropertyChangeEvent listener,
                                      UploadHandler receiver) {
    JreJsonArray oldValue = (JreJsonArray) listener.getOldValue();
    JreJsonArray value = (JreJsonArray) listener.getValue();
    if ("[]".equals(value.toJson()) && oldValue != null) {
      onReset(listener, receiver);
    }
  }

  /**
   * Escutador a ser executando quando o arquivo do uploader é resetado
   *
   * @param listener {@link PropertyChangeEvent}
   * @param receiver {@link UploadHandler}
   */
  protected void onReset(PropertyChangeEvent listener,
                         UploadHandler receiver) {
    AttachmentDTO<?> model = getViewModel().getModel();
    if (model != null) {
      model.setSize(null);
      model.setFilename(null);
      model.setMediaType(null);
      model.setContent(null);
      refreshFields();
    }
  }

  /**
   * @param metadata evento de sucesso
   * @param data     {@link UploadHandler}
   */
  protected void onSuccess(UploadMetadata metadata, byte[] data) {
    try {
      AttachmentDTO<?> model = getViewModel().getModel();
      if (model != null) {
        model.setSize(metadata.contentLength());
        model.setFilename(metadata.fileName());
        model.setMediaType(metadata.contentType());
        model.setContent(ZipUtil
            .zipBase64(Base64.getEncoder().encodeToString(data)));
      }
    } catch (Exception e) {
      handleError(e);
    } finally {
      refreshFields();
    }
  }

}
