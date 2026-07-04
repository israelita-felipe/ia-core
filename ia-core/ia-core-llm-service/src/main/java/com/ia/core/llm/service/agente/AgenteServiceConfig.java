package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.service.DefaultCrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import java.util.List;

/**
 * Configuração de injeção de dependência para AgenteService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class AgenteServiceConfig
  extends DefaultCrudBaseServiceConfig<Agente, AgenteDTO> {

  public AgenteServiceConfig(BaseEntityRepository<Agente> repository,
                            BaseEntityMapper<Agente, AgenteDTO> mapper,
                            SearchRequestMapper searchRequestMapper,
                            Translator translator,
                            List<IServiceValidator<AgenteDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }
}
