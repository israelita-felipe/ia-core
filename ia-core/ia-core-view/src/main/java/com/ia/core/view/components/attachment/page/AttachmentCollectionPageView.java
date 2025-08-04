package com.ia.core.view.components.attachment.page;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.page.CollectionPageView;
import com.ia.core.view.components.page.ICollectionPageView;

/**
 * Implementação padrão de uma {@link ICollectionPageView} para
 * {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public abstract class AttachmentCollectionPageView<T extends AttachmentDTO<?>>
  extends CollectionPageView<T> {

  /** Serial UID */
  private static final long serialVersionUID = -6167195294883665704L;

  /**
   * @param viewModel {@link IViewModel} do anexo
   */
  public AttachmentCollectionPageView(AttachmentCollectionPageViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  public AttachmentCollectionPageViewModel<T> getViewModel() {
    return super.getViewModel().cast();
  }

}
