package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.usecase.CrudUseCase;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  SaveSecuredBaseService<T, D>, CrudUseCase<D> {

  private static final Logger log = LoggerFactory
      .getLogger(DefaultSecuredBaseService.class);

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
  @Override
  public List<IServiceValidator<D>> getValidators() {
    return getConfig().getValidators();
  }

  /**
   * Publica evento automaticamente após salvar. Este método é chamado pelo
   * callback do SaveBaseService após cada operação de save. Usa
   * TransactionSynchronizationManager para garantir que o evento é publicado
   * apenas após a transação ser confirmada com sucesso.
   */
  @Override
  public void afterSave(D original, D saved,
                        CrudOperationType operationType)
    throws ServiceException {
    if (saved != null && config.getEventPublisher() != null) {
      // Registra sincronização para publicar evento apenas após commit
      if (TransactionSynchronizationManager.isSynchronizationActive()) {
        TransactionSynchronizationManager
            .registerSynchronization(new TransactionSynchronization() {
              @Override
              public void afterCommit() {
                publishEvent(saved, operationType);
                log.debug("Evento de {} publicado para {} (pós-commit)",
                          operationType, saved.getClass().getSimpleName());
              }
            });
      } else {
        // Fallback: contexto transacional não ativo, publica diretamente
        // (pode ser perigoso em caso de rollback)
        log.warn("Contexto transacional não ativo para {},publicando evento diretamente sem garantia de commit",
                 operationType);
        publishEvent(saved, operationType);
      }
    }
  }

  /**
   * Publica evento automaticamente após deletar. Este método é chamado pelo
   * callback do DeleteBaseService após cada operação de delete. Usa
   * TransactionSynchronizationManager para garantir que o evento é publicado
   * apenas após a transação ser confirmada com sucesso.
   */
  @Override
  public void afterDelete(Long id, D dto)
    throws ServiceException {
    if (dto != null && config.getEventPublisher() != null) {
      // Registra sincronização para publicar evento apenas após commit
      if (TransactionSynchronizationManager.isSynchronizationActive()) {
        TransactionSynchronizationManager
            .registerSynchronization(new TransactionSynchronization() {
              @Override
              public void afterCommit() {
                publishEvent(dto, CrudOperationType.DELETED);
                log.debug("Evento de DELETED publicado para {} com id {} (pós-commit)",
                          dto.getClass().getSimpleName(), id);
              }
            });
      } else {
        // Fallback: contexto transacional não ativo, publica diretamente
        // (pode ser perigoso em caso de rollback)
        log.warn("Contexto transacional não ativo para DELETE,publicando evento diretamente sem garantia de commit");
        publishEvent(dto, CrudOperationType.DELETED);
      }
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
    // Auditoria de registro de funcionalidades - Compliance e rastreabilidade
    LogOperationService logService = getLogOperationService();
    if (logService != null) {
      try {
        String functionalityName = getFunctionalityTypeName();
        log.info("Registrando funcionalidades para: {}", functionalityName);
        // Registra auditoria usando contexto do LogOperationService
        Map<String, Object> auditContext = LogOperationService.getContext();
        auditContext.put("FUNCTIONALITY_REGISTRY", functionalityName);
        auditContext.put("TIMESTAMP", java.time.LocalDateTime.now());
        auditContext.put("ACTION", "REGISTRY_FUNCTIONALITIES");
      } catch (Exception e) {
        log.error("Falha ao registrar auditoria de funcionalidades: {}",
                  e.getMessage());
      }
    }

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

  @TransactionalWrite
  @Override
  public void delete(Long id)
    throws ServiceException {
    DeleteSecuredBaseService.super.delete(id);
  }

  @TransactionalWrite
  @Override
  public D save(D toSave)
    throws ServiceException {
    return SaveSecuredBaseService.super.save(toSave);
  }

  @TransactionalReadOnly
  @Override
  public int count(SearchRequestDTO requestDTO) {
    return CountSecuredBaseService.super.count(requestDTO);
  }

  @TransactionalReadOnly
  @Override
  public D find(Long id) {
    return FindSecuredBaseService.super.find(id);
  }

  @TransactionalReadOnly
  @Override
  public Page<D> findAll(SearchRequestDTO requestDTO) {
    return ListSecuredBaseService.super.findAll(requestDTO);
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
    public DefaultSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
                                           Mapper<T, D> mapper,
                                           SearchRequestMapper searchRequestMapper,
                                           Translator translator,
                                           CoreSecurityAuthorizationManager authorizationManager,
                                           SecurityContextService securityContextService,
                                           LogOperationService logOperationService,
                                           List<IServiceValidator<D>> validators,
                                           ApplicationEventPublisher eventPublisher) {
      super(repository, mapper, searchRequestMapper, translator,
            authorizationManager, securityContextService,
            logOperationService, eventPublisher);
      this.validators = validators;
    }

    /**
     * Construtor simplificado sem eventPublisher.
     */
    public DefaultSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
                                           Mapper<T, D> mapper,
                                           SearchRequestMapper searchRequestMapper,
                                           Translator translator,
                                           CoreSecurityAuthorizationManager authorizationManager,
                                           SecurityContextService securityContextService,
                                           LogOperationService logOperationService,
                                           List<IServiceValidator<D>> validators) {
      this(repository, mapper, searchRequestMapper, translator,
           authorizationManager, securityContextService,
           logOperationService, validators, null);
    }
  }
}
