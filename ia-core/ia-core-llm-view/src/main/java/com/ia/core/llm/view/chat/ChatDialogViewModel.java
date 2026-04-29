package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
/**
 * Model de dados para a view de chat dialog.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ChatDialogViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class ChatDialogViewModel
  extends FormViewModel<ChatRequestDTO> {

  /**
   * @param readOnly
   */
  public ChatDialogViewModel(ChatDialogViewModelConfig config) {
    super(config);
  }

  public String ask() {
    return this.getConfig().getChatService().ask(getModel());
  }

  @Override
  public ChatDialogViewModelConfig getConfig() {
    return (ChatDialogViewModelConfig) super.getConfig();
  }
}
