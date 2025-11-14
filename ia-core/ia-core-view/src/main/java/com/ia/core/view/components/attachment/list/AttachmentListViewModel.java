package com.ia.core.view.components.attachment.list;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Supplier;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.util.ZipUtil;
import com.ia.core.view.components.list.viewModel.ListViewModel;
import com.ia.core.view.manager.AttachmentManager;

/**
 * Implementação padrão de um {@link ListViewModel} para {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public abstract class AttachmentListViewModel<T extends AttachmentDTO<?>>
  extends ListViewModel<T> {

  /**
   * @param config {@link Supplier} de serviço
   */
  public AttachmentListViewModel(AttachmentListViewModelConfig<T> config) {
    super(config);
  }

  /**
   * @param model {@link AttachmentDTO}
   * @return {@link ByteArrayInputStream} do anexo
   */
  public ByteArrayInputStream download(T model) {
    try {
      byte[] bytes = null;
      if (model.hasContent()) {
        bytes = ZipUtil.unzip(model.getContent());
      } else {
        bytes = getService().download(model.getId());
      }
      return new ByteArrayInputStream(bytes);
    } catch (ServiceException | IOException e) {
      throw new RuntimeException(e.getLocalizedMessage(), e);
    }
  }

  @Override
  public AttachmentListViewModelConfig<T> getConfig() {
    return (AttachmentListViewModelConfig<T>) super.getConfig();
  }

  /**
   * @return {@link AttachmentManager}
   */
  public AttachmentManager<T> getService() {
    return getConfig().getService();
  }

}
