package com.ia.core.view.components.attachment.page;

import java.util.UUID;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.attachment.form.AttachmentFormViewModel;
import com.ia.core.view.components.attachment.list.AttachmentListViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.viewModel.ListViewModel;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;

/**
 * Implementação padrão de um {@link IViewModel} para {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public abstract class AttachmentCollectionPageViewModel<T extends AttachmentDTO<?>>
  extends CollectionPageViewModel<T> {

  /**
   * @param service           serviço da coleção de anexos
   * @param attachmentService serviço de comunicação/API com os anexos
   */
  public AttachmentCollectionPageViewModel(AttachmentCollectionPageViewModelConfig<T> config) {
    super(config);
  }

  @Override
  public IFormViewModel<T> createFormViewModel(FormViewModelConfig<T> config) {
    return new AttachmentFormViewModel<>(config.cast()) {

    };
  }

  @Override
  protected ListViewModel<T> createListViewModel(ListViewModelConfig<T> config) {
    return new AttachmentListViewModel<>(config.cast()) {
      @Override
      public Class<T> getType() {
        return AttachmentCollectionPageViewModel.this.getType();
      }
    };
  }

  @Override
  public AttachmentCollectionPageViewModelConfig<T> getConfig() {
    return (AttachmentCollectionPageViewModelConfig<T>) super.getConfig();
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
