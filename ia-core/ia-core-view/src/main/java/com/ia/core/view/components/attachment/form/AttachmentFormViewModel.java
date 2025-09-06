package com.ia.core.view.components.attachment.form;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * Implementação padrão de um {@link IViewModel} para formulário de
 * {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public abstract class AttachmentFormViewModel<T extends AttachmentDTO<?>>
  extends FormViewModel<T> {

  /**
   * @param readOnly Indicativo de somente leitura
   */
  public AttachmentFormViewModel(AttachmentFormViewModelConfig<T> config) {
    super(config);
  }

  /**
   * @return <code>true</code> se possuir conteúdo.
   */
  public boolean hasContent() {
    return getModel() != null && !getModel().isEmpty();
  }

}
