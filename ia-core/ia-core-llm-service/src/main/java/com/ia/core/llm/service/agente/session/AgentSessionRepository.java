package com.ia.core.llm.service.agente.session;

import com.ia.core.llm.model.agente.session.AgentSession;
import com.ia.core.llm.model.agente.session.AgentSession.AgentSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AgentSession.
 * <p>
 * Define métodos de acesso a dados para sessões de agente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Repository
public interface AgentSessionRepository
  extends JpaRepository<AgentSession, Long> {

  /**
   * Busca uma sessão pelo session ID.
   *
   * @param sessionId identificador da sessão
   * @return Optional com a AgentSession encontrada
   */
  Optional<AgentSession> findBySessionId(String sessionId);

  /**
   * Lista todas as sessões ativas de um agente.
   *
   * @param agenteId ID do agente
   * @return lista de sessões ativas
   */
  @Query("SELECT s FROM AgentSession s WHERE s.agente.id = :agenteId AND s.status = :status")
  List<AgentSession> findByAgenteIdAndStatus(@Param("agenteId") Long agenteId,
                                                @Param("status") AgentSessionStatus status);

  /**
   * Lista todas as sessões com um status específico.
   *
   * @param status status da sessão
   * @return lista de sessões
   */
  List<AgentSession> findByStatus(AgentSessionStatus status);
}
