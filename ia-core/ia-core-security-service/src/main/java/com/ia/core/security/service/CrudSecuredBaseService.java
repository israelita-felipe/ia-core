package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.io.Serializable;
import java.util.List;

/**
 * Classe base de um serviço com todas as funcionalidades de um CRUD.
 *
 * @param <T> Tipo de dado {@link BaseEntity}
 * @param <D> Tipo de dado {@link DTO}
 * @author Israel Araújo
 * @see AbstractSecuredBaseService
 * @see CountSecuredBaseService
 * @see DeleteSecuredBaseService
 * @see FindSecuredBaseService
 * @see ListSecuredBaseService
 * @see SaveSecuredBaseService
 */
public abstract class CrudSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
    extends AbstractSecuredBaseService<T, D>
    implements
    CrudSecuredService<T, D> {

    private static final Logger log = LoggerFactory
        .getLogger(CrudSecuredBaseService.class);

    @Getter
    private final CrudSecuredBaseServiceConfig<T, D> config;

    /**
     * @param repository
     * @param mapper
     * @param searchRequestMapper
     * @param translator
     * @param authorizationManager
     * @param logOperationService
     */
    public CrudSecuredBaseService(CrudSecuredBaseServiceConfig<T, D> config) {
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
            getRepository().registerTransactionSynchronization(() -> publishEvent(saved, operationType));

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
            getRepository().registerTransactionSynchronization(() -> publishEvent(dto, CrudOperationType.DELETED));

        }
    }


    public static class CrudSecuredBaseServiceConfig<T extends Serializable, D extends DTO<?>>
        extends AbstractSecuredBaseServiceConfig<T, D> {

        @Getter
        private final List<IServiceValidator<D>> validators;

        /**
         * Construtor completo para configuração do serviço seguro.
         *
         * @param repository             repositório da entidade
         * @param mapper                 mapeador entre entidade e DTO
         * @param searchRequestMapper    mapeador de requisição de busca
         * @param translator             tradutor para internacionalização
         * @param authorizationManager   gerenciador de autorização
         * @param securityContextService serviço de contexto de segurança
         * @param logOperationService    serviço de operação de log
         * @param validators             lista de validadores
         * @param eventPublisher         publicador de eventos de aplicação
         */
        public CrudSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
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
        public CrudSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
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
