package com.ia.core.llm.service.chat;

/**
 * Interface para gerenciamento de sessões de chat.
 * <p>
 * Responsável por gerenciar o estado e histórico de conversas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface ChatSessionService {

  /**
   * Inicia uma nova sessão de chat.
   *
   * @return identificador da sessão
   */
  String createSession(String sessionId);

  /**
   * Finaliza uma sessão de chat.
   *
   * @param sessionId identificador da sessão
   */
  void endSession(String sessionId);

  /**
   * Verifica se uma sessão está ativa.
   *
   * @param sessionId identificador da sessão
   * @return true se a sessão estiver ativa
   */
  boolean isSessionActive(String sessionId);

  /**
   * Limpa o histórico de uma sessão.
   *
   * @param sessionId identificador da sessão
   */
  void clearSessionHistory(String sessionId);
}
