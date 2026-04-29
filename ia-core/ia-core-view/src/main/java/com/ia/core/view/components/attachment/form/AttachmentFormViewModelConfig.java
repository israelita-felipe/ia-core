package com.ia.core.view.components.attachment.form;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 *
 */
/**
 * Classe de configuração para attachment form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AttachmentFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class AttachmentFormViewModelConfig<T extends AttachmentDTO<?>>
  extends FormViewModelConfig<T> {

  /**
   * @param readOnly
   */
  public AttachmentFormViewModelConfig(boolean readOnly) {
    super(readOnly);
  }

}
