package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

/**
 * Classe base de um serviço com todas as funcionalidades de um CRUD.
 * <p>
 * Publica automaticamente eventos de domínio nas operações CRUD através dos
 * métodos {@link #afterSave(D, D, CrudOperationType)} e
 * {@link #afterDelete(Long, D)}.
 * </p>
 *
 * @param <T> Tipo de dado {@link BaseEntity}
 * @param <D> Tipo de dado {@link DTO}
 * @author Israel Araújo
 * @see AbstractBaseService
 * @see CountBaseService
 * @see DeleteBaseService
 * @see FindBaseService
 * @see ListBaseService
 * @see SaveBaseService
 */
public abstract class CrudBaseService<T extends BaseEntity, D extends DTO<T>>
    extends AbstractBaseService<T, D>
    implements
    CrudService<T, D> {

    /**
     * * Construtor do serviço base.
     *
     * @param config configuração do serviço base
     */
    public CrudBaseService(CrudBaseServiceConfig<T, D> config) {
        super(config);
        registryValidators(config.getValidators());
    }

    @Override
    public List<IServiceValidator<D>> getValidators() {
        return getConfig().getValidators();
    }

    @Override
    public CrudBaseServiceConfig<T, D> getConfig() {
        return (CrudBaseServiceConfig<T, D>) super.getConfig();
    }

    @Override
    public void afterSave(D original, D saved,
                          CrudOperationType operationType)
        throws ServiceException {
        publishEventIfDtoNotNull(saved, operationType);
    }

    @Override
    public void afterDelete(Long id, D dto)
        throws ServiceException {
        publishEventIfDtoNotNull(dto, CrudOperationType.DELETED);
    }

    public void publishEventIfDtoNotNull(D dto,
                                         CrudOperationType operationType) {
        if (dto != null) {
            publishEvent(dto, operationType);
        }
    }


    /**
     * Classe de configuração do serviço base.
     *
     * @param <T> {@link BaseEntity}
     * @param <D> {@link DTO}
     */
    public static class CrudBaseServiceConfig<T extends BaseEntity, D extends DTO<T>>
        extends AbstractBaseServiceConfig<T, D> {
        /**
         * Validadores
         */
        @Getter
        private final List<IServiceValidator<D>> validators;

        /**
         * Construtor da configuração do serviço base.
         *
         * @param repository          repositório do serviço
         * @param mapper              mapeador do objeto do serviço
         * @param searchRequestMapper mapeador do objeto de busca
         * @param translator          tradutor
         * @param validators          validadores deste serviço
         * @param eventPublisher      publicador de eventos (opcional)
         */
        public CrudBaseServiceConfig(BaseEntityRepository<T> repository,
                                     BaseEntityMapper<T, D> mapper,
                                     SearchRequestMapper searchRequestMapper,
                                     Translator translator,
                                     List<IServiceValidator<D>> validators,
                                     ApplicationEventPublisher eventPublisher) {
            super(repository, mapper, searchRequestMapper, translator,
                eventPublisher);
            this.validators = validators;
        }

        /**
         * Construtor simplificado sem eventPublisher.
         */
        public CrudBaseServiceConfig(BaseEntityRepository<T> repository,
                                     BaseEntityMapper<T, D> mapper,
                                     SearchRequestMapper searchRequestMapper,
                                     Translator translator,
                                     List<IServiceValidator<D>> validators) {
            this(repository, mapper, searchRequestMapper, translator, validators,
                null);
        }

    }
}
