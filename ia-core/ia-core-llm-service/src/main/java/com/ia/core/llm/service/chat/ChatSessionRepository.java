package com.ia.core.llm.service.chat;

import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.model.chat.ChatSession.ChatSessionStatus;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ChatSession.
 * <p>
 * Define métodos de acesso a dados para sessões de chat.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Repository
public interface ChatSessionRepository
  extends BaseEntityRepository<ChatSession> {

  /**
   * Busca uma sessão pelo session ID.
   *
   * @param sessionId identificador da sessão
   * @return Optional com a ChatSession encontrada
   */
  Optional<ChatSession> findBySessionId(String sessionId);

  /**
   * Lista todas as sessões ativas de um usuário.
   *
   * @param usuarioId ID do usuário
   * @return lista de sessões ativas
   */
  @Query("SELECT s FROM ChatSession s WHERE s.usuarioId = :usuarioId AND s.status = :status")
  List<ChatSession> findByUsuarioIdAndStatus(@Param("usuarioId") String usuarioId,
                                              @Param("status") ChatSessionStatus status);

  /**
   * Lista todas as sessões de um agente.
   *
   * @param agenteId ID do agente
   * @return lista de sessões
   */
  @Query("SELECT s FROM ChatSession s WHERE s.agente.id = :agenteId")
  List<ChatSession> findByAgenteId(@Param("agenteId") Long agenteId);

  /**
   * Lista todas as sessões com um status específico.
   *
   * @param status status da sessão
   * @return lista de sessões
   */
  List<ChatSession> findByStatus(ChatSessionStatus status);
}
