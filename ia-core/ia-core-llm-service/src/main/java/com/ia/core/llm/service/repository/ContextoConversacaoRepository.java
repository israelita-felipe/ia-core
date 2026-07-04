package com.ia.core.llm.service.repository;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para ContextoConversacao.
 * <p>
 * Segue o padrão BaseEntityRepository do projeto ia-core.
 * Integra funcionalidades de AiInteractionLogRepository para auditoria de interações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Repository
public interface ContextoConversacaoRepository extends BaseEntityRepository<ContextoConversacao> {

  /**
   * Busca contexto de conversação por sessionId.
   *
   * @param sessionId ID da sessão
   * @return Optional com o contexto encontrado
   */
  Optional<ContextoConversacao> findBySessionId(String sessionId);

  /**
   * Verifica se existe contexto de conversação por sessionId.
   *
   * @param sessionId ID da sessão
   * @return true se existe, false caso contrário
   */
  boolean existsBySessionId(String sessionId);

  /**
   * Deleta contexto de conversação por sessionId.
   *
   * @param sessionId ID da sessão
   */
  @Modifying
  void deleteBySessionId(String sessionId);
}
