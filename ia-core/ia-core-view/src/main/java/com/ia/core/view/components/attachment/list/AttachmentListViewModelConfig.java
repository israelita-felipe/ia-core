package com.ia.core.view.components.attachment.list;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;
import com.ia.core.view.manager.AttachmentManager;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 */
/**
 * Classe de configuração para attachment list view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AttachmentListViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class AttachmentListViewModelConfig<T extends AttachmentDTO<?>>
  extends ListViewModelConfig<T> {

  @Getter
  private final AttachmentManager<T> service;

  /**
   * @param readOnly
   */
  public AttachmentListViewModelConfig(@Value(value = "false") boolean readOnly,
                                       AttachmentManager<T> service) {
    super(readOnly);
    this.service = service;
  }

}
