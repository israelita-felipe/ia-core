package com.ia.core.view.components.attachment.page;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.attachment.form.AttachmentFormViewModelConfig;
import com.ia.core.view.components.attachment.list.AttachmentListViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.service.AttachmentService;
import com.ia.core.view.service.collection.DefaultCollectionBaseService;

import lombok.Getter;

/**
 *
 */
public class AttachmentCollectionPageViewModelConfig<T extends AttachmentDTO<?>>
  extends CollectionPageViewModelConfig<T> {
  /** Serviço de persistência de aqruivos */
  @Getter
  private final AttachmentService<T> attachmentService;

  /**
   *
   */
  public AttachmentCollectionPageViewModelConfig(DefaultCollectionBaseService<T> service,
                                                 AttachmentService<T> attachmentService) {
    super(service);
    this.attachmentService = attachmentService;
  }

  @Override
  protected AttachmentFormViewModelConfig<T> createFormViewModelConfig(boolean readOnly) {
    return new AttachmentFormViewModelConfig<>(readOnly);
  }

  @Override
  protected AttachmentListViewModelConfig<T> createListViewModelConfig(boolean readOnly) {
    return new AttachmentListViewModelConfig<>(readOnly, attachmentService);
  }
}
