package com.ia.core.llm.service.prompt;

import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repositório para acesso a dados de Prompt.
 * <p>
 * Fornece métodos específicos para buscar e manipular prompts no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface PromptRepository
  extends BaseEntityRepository<Prompt> {
}
