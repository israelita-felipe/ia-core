package com.ia.core.view.components.attachment.form;

import java.util.Base64;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.attachment.dto.AttachmentTranslator;
import com.ia.core.service.util.ZipUtil;
import com.ia.core.view.components.form.FormView;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.PropertyChangeEvent;

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
    bind("filename", createNomeField($(AttachmentTranslator.NOME),
                                           $(AttachmentTranslator.NOME)));
    bind("description",
         createDescricaoField($(AttachmentTranslator.DESCRICAO),
                              $(AttachmentTranslator.HELP.DESCRICAO)));
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
    MemoryBuffer receiver = new MemoryBuffer();
    this.upload = createUploadField($(AttachmentTranslator.ATTACHMENT),
                                    $(AttachmentTranslator.HELP.ATTACHMENT),
                                    receiver);
    this.upload.addSucceededListener(onSuccess -> {
      onSuccess(onSuccess, receiver);
    });
    this.upload.getElement().addPropertyChangeListener("files",
                                                       listener -> {
                                                         onFileChangeListener(listener,
                                                                              receiver);
                                                       });
    add(this.upload, 6);
  }

  @Override
  public AttachmentFormViewModel<T> getViewModel() {
    return super.getViewModel().cast();
  }

  /**
   * Evento a ser disparado quando houver mudança no arquivo do uploader
   *
   * @param listener {@link PropertyChangeEvent}
   * @param receiver {@link MemoryBuffer}
   */
  protected void onFileChangeListener(PropertyChangeEvent listener,
                                      MemoryBuffer receiver) {
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
   * @param receiver {@link MemoryBuffer}
   */
  protected void onReset(PropertyChangeEvent listener,
                         MemoryBuffer receiver) {
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
   * @param onSuccess evento de sucesso
   * @param receiver  {@link MemoryBuffer}
   */
  protected void onSuccess(SucceededEvent onSuccess,
                           MemoryBuffer receiver) {
    try {
      AttachmentDTO<?> model = getViewModel().getModel();
      if (model != null) {
        model.setSize(onSuccess.getContentLength());
        model.setFilename(onSuccess.getFileName());
        model.setMediaType(onSuccess.getMIMEType());
        model.setContent(ZipUtil.zipBase64(Base64.getEncoder()
            .encodeToString(receiver.getInputStream().readAllBytes())));
      }
    } catch (Exception e) {
      handleError(e);
    } finally {
      refreshFields();
    }
  }

}
