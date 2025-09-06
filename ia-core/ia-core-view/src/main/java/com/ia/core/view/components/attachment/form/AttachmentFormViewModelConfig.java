package com.ia.core.view.components.attachment.form;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 *
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
