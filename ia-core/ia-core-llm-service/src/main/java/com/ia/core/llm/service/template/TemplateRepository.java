package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade Template. Implementa EntityGraph para evitar N+1
 * queries.
 *
 * @author Israel Araújo
 */
public interface TemplateRepository
  extends BaseEntityRepository<Template> {

}
