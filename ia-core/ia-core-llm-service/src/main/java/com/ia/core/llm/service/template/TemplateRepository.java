package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.service.repository.BaseEntityRepository;

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

}
