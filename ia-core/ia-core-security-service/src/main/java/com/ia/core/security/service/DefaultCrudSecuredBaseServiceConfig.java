package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import java.util.List;

/**
 * Configuração padrão reutilizável para serviços {@link CrudSecuredBaseService}.
 * <p>
 * Elimina a necessidade de criar subclasses de {@link CrudSecuredBaseService.CrudSecuredBaseServiceConfig}
 * que apenas repassam parâmetros ao construtor da superclasse sem adicionar
 * campos ou lógica própria.
 *
 * @param <T> tipo da entidade
 * @param <D> tipo do DTO
 * @author Israel Araújo
 * @since 1.0.0
 */
public class DefaultCrudSecuredBaseServiceConfig<T extends BaseEntity, D extends DTO<?>>
    extends CrudSecuredBaseService.CrudSecuredBaseServiceConfig<T, D> {

  public DefaultCrudSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
                                             Mapper<T, D> mapper,
                                             SearchRequestMapper searchRequestMapper,
                                             Translator translator,
                                             CoreSecurityAuthorizationManager authorizationManager,
                                             SecurityContextService securityContextService,
                                             LogOperationService logOperationService,
                                             List<IServiceValidator<D>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
        authorizationManager, securityContextService, logOperationService,
        validators);
  }
}
