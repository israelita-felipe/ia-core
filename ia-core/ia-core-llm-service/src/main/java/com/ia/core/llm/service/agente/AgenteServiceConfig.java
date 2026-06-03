package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.ferramenta.FerramentaRepository;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.skill.SkillRepository;
import com.ia.core.service.CrudBaseService.CrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para AgenteService.
 * <p>
 * Fornece as dependências necessárias para o serviço de agentes.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class AgenteServiceConfig
  extends CrudBaseServiceConfig<Agente, AgenteDTO> {

  @Getter
  private final FerramentaRepository ferramentaRepository;

  @Getter
  private final SkillRepository skillRepository;

  public AgenteServiceConfig(BaseEntityRepository<Agente> repository,
                            BaseEntityMapper<Agente, AgenteDTO> mapper,
                            SearchRequestMapper searchRequestMapper,
                            Translator translator,
                            List<IServiceValidator<AgenteDTO>> validators,
                            FerramentaRepository ferramentaRepository,
                            SkillRepository skillRepository) {
    super(repository, mapper, searchRequestMapper, translator, validators);
    this.ferramentaRepository = ferramentaRepository;
    this.skillRepository = skillRepository;
  }
}
