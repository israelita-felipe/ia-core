package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.service.repository.BaseEntityRepository;

import java.util.Optional;

/**
 * Repositório para acesso a dados de Template.
 * <p>
 * Implementa EntityGraph para evitar N+1 queries.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface TemplateRepository
  extends BaseEntityRepository<Template> {

  /**
   * Busca um template pelo seu identificador.
   *
   * @param identificador identificador do template
   * @return Optional contendo o template se encontrado
   */
  Optional<Template> findByIdentificador(String identificador);
}
