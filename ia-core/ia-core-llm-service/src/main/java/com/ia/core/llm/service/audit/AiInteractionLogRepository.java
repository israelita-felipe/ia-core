package com.ia.core.llm.service.audit;

import com.ia.core.llm.model.audit.AiInteractionLog;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repositório para acesso a dados de AiInteractionLog.
 * <p>
 * Fornece métodos específicos para buscar e manipular logs de interações com IA.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface AiInteractionLogRepository
  extends BaseEntityRepository<AiInteractionLog> {
}
