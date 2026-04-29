package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.llm.view.comando.ComandoSistemaManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

/**
 *
 */
/**
 * Classe de configuração para chat dialog view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ChatDialogViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class ChatDialogViewModelConfig
  extends FormViewModelConfig<ChatRequestDTO> {
  /** Serviço de chat */
  @Getter
  private final ChatManager chatService;
  @Getter
  private final ComandoSistemaManager comandoSistemaService;

  /**
   * @param readOnly
   * @param chatService
   * @param comandoSistemaService
   */
  public ChatDialogViewModelConfig(boolean readOnly,
                                   ChatManager chatService,
                                   ComandoSistemaManager comandoSistemaService) {
    super(readOnly);
    this.chatService = chatService;
    this.comandoSistemaService = comandoSistemaService;
  }

}
