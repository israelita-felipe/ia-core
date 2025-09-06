package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * @author Israel Araújo
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
