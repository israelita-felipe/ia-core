package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.llm.view.comando.ComandoSistemaService;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
public class ChatDialogViewModel
  extends FormViewModel<ChatRequestDTO> {
  /** Serviço de chat */
  @Getter
  private ChatService chatService;
  @Getter
  private ComandoSistemaService comandoSistemaService;

  /**
   * @param readOnly
   */
  public ChatDialogViewModel(ChatService chatService,
                             ComandoSistemaService comandoSistemaService) {
    super(false);
    this.chatService = chatService;
    this.comandoSistemaService = comandoSistemaService;
  }

  public String ask() {
    return this.chatService.ask(getModel());
  }

}
