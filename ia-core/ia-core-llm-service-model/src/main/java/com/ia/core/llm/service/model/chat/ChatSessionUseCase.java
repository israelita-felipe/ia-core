package com.ia.core.llm.service.model.chat;

import com.ia.core.service.usecase.CrudUseCase;

import java.util.Optional;

/**
 * Interface de Use Case para sessões de chat.
 * <p>
 * Define operações para gerenciamento de sessões de chat com agentes LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface ChatSessionUseCase
  extends CrudUseCase<ChatSessionDTO> {

  /**
   * Busca uma sessão de chat pelo session ID.
   *
   * @param sessionId identificador da sessão
   * @return Optional com o ChatSessionDTO encontrado
   */
  Optional<ChatSessionDTO> findBySessionId(String sessionId);

  /**
   * Lista todas as sessões ativas de um usuário.
   *
   * @param usuarioId ID do usuário
   * @return lista de sessões ativas
   */
  java.util.List<ChatSessionDTO> listAtivasByUsuario(String usuarioId);

  /**
   * Inicia uma nova sessão de chat.
   *
   * @param agenteId ID do agente
   * @param usuarioId ID do usuário
   * @param titulo título da sessão
   * @return ChatSessionDTO da sessão criada
   */
  ChatSessionDTO iniciarSessao(Long agenteId, String usuarioId, String titulo);

  /**
   * Encerra uma sessão de chat.
   *
   * @param sessionId identificador da sessão
   * @return ChatSessionDTO da sessão encerrada
   */
  ChatSessionDTO encerrarSessao(String sessionId);
}
