package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.llm.view.comando.ComandoSistemaService;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class ChatDialogViewModelConfig
  extends FormViewModelConfig<ChatRequestDTO> {
  /** Serviço de chat */
  @Getter
  private final ChatService chatService;
  @Getter
  private final ComandoSistemaService comandoSistemaService;

  /**
   * @param readOnly
   * @param chatService
   * @param comandoSistemaService
   */
  public ChatDialogViewModelConfig(boolean readOnly,
                                   ChatService chatService,
                                   ComandoSistemaService comandoSistemaService) {
    super(readOnly);
    this.chatService = chatService;
    this.comandoSistemaService = comandoSistemaService;
  }

}
