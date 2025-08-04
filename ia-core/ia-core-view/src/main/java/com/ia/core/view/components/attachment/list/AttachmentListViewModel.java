package com.ia.core.view.components.attachment.list;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Supplier;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.util.ZipUtil;
import com.ia.core.view.components.list.viewModel.ListViewModel;
import com.ia.core.view.service.AttachmentService;

/**
 * Implementação padrão de um {@link ListViewModel} para {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public abstract class AttachmentListViewModel<T extends AttachmentDTO<?>>
  extends ListViewModel<T> {
  /** {@link Supplier} para o serviço de anexos */
  private Supplier<AttachmentService<T>> service;

  /**
   * @param service {@link Supplier} de serviço
   */
  public AttachmentListViewModel(Supplier<AttachmentService<T>> service) {
    super();
    this.service = service;
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

  /**
   * @return {@link AttachmentService}
   */
  public AttachmentService<T> getService() {
    return service.get();
  }

}
