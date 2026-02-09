package com.ia.core.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.BaseServiceEvent;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
public abstract class DefaultSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends AbstractSecuredBaseService<T, D>
  implements CountSecuredBaseService<T, D>, DeleteSecuredBaseService<T, D>,
  FindSecuredBaseService<T, D>, ListSecuredBaseService<T, D>,
  SaveSecuredBaseService<T, D> {

  private static final Logger log = LoggerFactory.getLogger(DefaultSecuredBaseService.class);

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

  // ========== IMPLEMENTAÇÃO DE PUBLICAÇÃO AUTOMÁTICA DE EVENTOS ==========

  /**
   * Publica evento automaticamente após salvar.
   * Este método é chamado pelo callback do SaveBaseService após cada operação de save.
   */
  @Override
  public void afterSave(D original, D saved, CrudOperationType operationType)
    throws ServiceException {
    if (saved != null && config.getEventPublisher() != null) {
      publishEvent(saved, operationType);
      log.debug("Evento de {} publicado para {}", operationType,
          saved.getClass().getSimpleName());
    }
  }

  /**
   * Publica evento automaticamente após deletar.
   * Este método é chamado pelo callback do DeleteBaseService após cada operação de delete.
   */
  @Override
  public void afterDelete(Long id, D dto) throws ServiceException {
    if (dto != null && config.getEventPublisher() != null) {
      publishEvent(dto, CrudOperationType.DELETED);
      log.debug("Evento de DELETED publicado para {} com id {}",
          dto.getClass().getSimpleName(), id);
    }
  }

  @Override
  public Map<String, String> getContextValue(Object object) {
    Map<String, String> contextValue = super.getContextValue(object);
    contextValue
        .putAll(CountSecuredBaseService.super.getContextValue(object));
    contextValue
        .putAll(DeleteSecuredBaseService.super.getContextValue(object));
    contextValue
        .putAll(FindSecuredBaseService.super.getContextValue(object));
    contextValue
        .putAll(ListSecuredBaseService.super.getContextValue(object));
    return contextValue;
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

  public static class DefaultSecuredBaseServiceConfig<T extends BaseEntity, D extends DTO<?>>
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
     * @param eventPublisher
     */
    public DefaultSecuredBaseServiceConfig(PlatformTransactionManager transactionManager,
                                           BaseEntityRepository<T> repository,
                                           Mapper<T, D> mapper,
                                           SearchRequestMapper searchRequestMapper,
                                           Translator translator,
                                           CoreSecurityAuthorizationManager authorizationManager,
                                           SecurityContextService securityContextService,
                                           LogOperationService logOperationService,
                                           List<IServiceValidator<D>> validators,
                                           ApplicationEventPublisher eventPublisher) {
      super(transactionManager, repository, mapper, searchRequestMapper,
            translator, authorizationManager, securityContextService,
            logOperationService, eventPublisher);
      this.validators = validators;
    }

    /**
     * Construtor simplificado sem eventPublisher.
     */
    public DefaultSecuredBaseServiceConfig(PlatformTransactionManager transactionManager,
                                           BaseEntityRepository<T> repository,
                                           Mapper<T, D> mapper,
                                           SearchRequestMapper searchRequestMapper,
                                           Translator translator,
                                           CoreSecurityAuthorizationManager authorizationManager,
                                           SecurityContextService securityContextService,
                                           LogOperationService logOperationService,
                                           List<IServiceValidator<D>> validators) {
      this(transactionManager, repository, mapper, searchRequestMapper,
           translator, authorizationManager, securityContextService,
           logOperationService, validators, null);
    }
  }
}
