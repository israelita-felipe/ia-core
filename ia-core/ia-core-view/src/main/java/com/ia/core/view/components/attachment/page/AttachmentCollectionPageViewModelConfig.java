package com.ia.core.view.components.attachment.page;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.attachment.form.AttachmentFormViewModelConfig;
import com.ia.core.view.components.attachment.list.AttachmentListViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.AttachmentManager;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import lombok.Getter;

/**
 *
 */
public class AttachmentCollectionPageViewModelConfig<T extends AttachmentDTO<?>>
  extends CollectionPageViewModelConfig<T> {
  /** Serviço de persistência de aqruivos */
  @Getter
  private final AttachmentManager<T> attachmentService;

  /**
   *
   */
  public AttachmentCollectionPageViewModelConfig(DefaultCollectionBaseManager<T> service,
                                                 AttachmentManager<T> attachmentService) {
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
