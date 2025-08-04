package com.ia.core.view.components.attachment.page;

import java.util.UUID;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.attachment.form.AttachmentFormViewModel;
import com.ia.core.view.components.attachment.list.AttachmentListViewModel;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.viewModel.ListViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;
import com.ia.core.view.service.AttachmentService;
import com.ia.core.view.service.collection.DefaultCollectionBaseService;

/**
 * Implementação padrão de um {@link IViewModel} para {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public abstract class AttachmentCollectionPageViewModel<T extends AttachmentDTO<?>>
  extends CollectionPageViewModel<T> {
  /** Serviço de persistência de aqruivos */
  private AttachmentService<T> attachmentService;

  /**
   * @param service           serviço da coleção de anexos
   * @param attachmentService serviço de comunicação/API com os anexos
   */
  public AttachmentCollectionPageViewModel(DefaultCollectionBaseService<T> service,
                                           AttachmentService<T> attachmentService) {
    super(service);
    this.attachmentService = attachmentService;
  }

  @Override
  public IFormViewModel<T> createFormViewModel(boolean readOnly) {
    return new AttachmentFormViewModel<>(readOnly) {
    };
  }

  @Override
  protected ListViewModel<T> createListViewModel() {
    return new AttachmentListViewModel<>(() -> attachmentService) {
      @Override
      public Class<T> getType() {
        return AttachmentCollectionPageViewModel.this.getType();
      }
    };
  }

  @SuppressWarnings("unchecked")
  @Override
  public T copyObject(T object) {
    return (T) object.copyObject();
  }

  @SuppressWarnings("unchecked")
  @Override
  public T cloneObject(T object) {
    return (T) object.cloneObject();
  }

  @Override
  public UUID getId(T object) {
    return object.getId();
  }
}
