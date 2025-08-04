package com.ia.core.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import lombok.Getter;

/**
 * Classe base de um serviço com todas as funcionalidades de um CRUD.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado {@link BaseEntity}
 * @param <D> Tipo de dado {@link DTO}
 * @see AbstractSecuredBaseService
 * @see CountSecuredBaseService
 * @see DeleteSecuredBaseService
 * @see FindSecuredBaseService
 * @see ListSecuredBaseService
 * @see SaveSecuredBaseService
 */
public abstract class DefaultSecuredBaseService<T extends BaseEntity, D extends DTO<T>>
  extends AbstractSecuredBaseService<T, D>
  implements CountSecuredBaseService<T, D>, DeleteSecuredBaseService<T, D>,
  FindSecuredBaseService<T, D>, ListSecuredBaseService<T, D>,
  SaveSecuredBaseService<T, D> {

  @Getter
  private final DefaultSecuredBaseServiceConfig<T, D> config;

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public DefaultSecuredBaseService(DefaultSecuredBaseServiceConfig<T, D> config) {
    super(config);
    this.config = config;
    registryValidators(config.getValidators());
  }

  @Override
  public Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    Set<Functionality> funcionalidades = new HashSet<>();
    funcionalidades
        .addAll(CountSecuredBaseService.super.registryFunctionalities(functionalityManager));
    funcionalidades
        .addAll(DeleteSecuredBaseService.super.registryFunctionalities(functionalityManager));
    funcionalidades
        .addAll(FindSecuredBaseService.super.registryFunctionalities(functionalityManager));
    funcionalidades
        .addAll(ListSecuredBaseService.super.registryFunctionalities(functionalityManager));
    funcionalidades
        .addAll(SaveSecuredBaseService.super.registryFunctionalities(functionalityManager));
    return funcionalidades;
  }

  public static class DefaultSecuredBaseServiceConfig<T extends BaseEntity, D extends DTO<T>>
    extends AbstractSecuredBaseServiceConfig<T, D> {

    @Getter
    private final List<IServiceValidator<D>> validators;

    /**
     * @param repository
     * @param mapper
     * @param searchRequestMapper
     * @param translator
     * @param authorizationManager
     * @param logOperationService
     * @param validators
     */
    public DefaultSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
                                           BaseMapper<T, D> mapper,
                                           SearchRequestMapper searchRequestMapper,
                                           Translator translator,
                                           CoreSecurityAuthorizationManager authorizationManager,
                                           LogOperationService logOperationService,
                                           List<IServiceValidator<D>> validators) {
      super(repository, mapper, searchRequestMapper, translator,
            authorizationManager, logOperationService);
      this.validators = validators;
    }
  }
}
