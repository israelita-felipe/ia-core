package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.service.CrudBaseService.CrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para FerramentaService.
 * <p>
 * Fornece as dependências necessárias para o serviço de ferramentas,
 * incluindo o serviço de descoberta automática.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class FerramentaServiceConfig
  extends CrudBaseServiceConfig<Ferramenta, FerramentaDTO> {
 private final FerramentaDiscoveryService discoveryService;
  public FerramentaServiceConfig(FerramentaRepository repository,
                                 BaseEntityMapper<Ferramenta, FerramentaDTO> mapper,
                                 SearchRequestMapper searchRequestMapper,
                                 Translator translator,
                                 FerramentaDiscoveryService discoveryService,
                                 List<IServiceValidator<FerramentaDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
    this.discoveryService = discoveryService;
  }

  public FerramentaRepository getRepository() {
    return (FerramentaRepository) super.getRepository();
  }

  public FerramentaDiscoveryService getDiscoveryService() {
    return discoveryService;
  }
}
