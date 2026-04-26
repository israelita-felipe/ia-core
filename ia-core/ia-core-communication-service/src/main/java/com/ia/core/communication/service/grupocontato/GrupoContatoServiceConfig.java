package com.ia.core.communication.service.grupocontato;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para GrupoContatoService.
 *
 * @author Israel Araújo
 */
@Getter
@Component
public class GrupoContatoServiceConfig
  extends DefaultSecuredBaseServiceConfig<GrupoContato, GrupoContatoDTO> {

  public GrupoContatoServiceConfig(BaseEntityRepository<GrupoContato> repository,
                                   BaseEntityMapper<GrupoContato, GrupoContatoDTO> mapper,
                                   SearchRequestMapper searchRequestMapper,
                                   Translator translator,
                                   CoreSecurityAuthorizationManager authorizationManager,
                                   SecurityContextService securityContextService,
                                   LogOperationService logOperationService,
                                   List<IServiceValidator<GrupoContatoDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
  }
}
