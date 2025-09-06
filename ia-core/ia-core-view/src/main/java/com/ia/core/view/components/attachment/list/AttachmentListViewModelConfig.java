package com.ia.core.view.components.attachment.list;

import org.springframework.beans.factory.annotation.Value;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;
import com.ia.core.view.service.AttachmentService;

import lombok.Getter;

/**
 *
 */
public class AttachmentListViewModelConfig<T extends AttachmentDTO<?>>
  extends ListViewModelConfig<T> {

  @Getter
  private final AttachmentService<T> service;

  /**
   * @param readOnly
   */
  public AttachmentListViewModelConfig(@Value(value = "false") boolean readOnly,
                                       AttachmentService<T> service) {
    super(readOnly);
    this.service = service;
  }

}
