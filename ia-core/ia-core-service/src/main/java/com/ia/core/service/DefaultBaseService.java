package com.ia.core.service;

import java.util.List;

import com.ia.core.model.BaseEntity;
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
 * @see AbstractBaseService
 * @see CountBaseService
 * @see DeleteBaseService
 * @see FindBaseService
 * @see ListBaseService
 * @see SaveBaseService
 */
public abstract class DefaultBaseService<T extends BaseEntity, D extends DTO<T>>
  extends AbstractBaseService<T, D>
  implements CountBaseService<T, D>, DeleteBaseService<T, D>,
  FindBaseService<T, D>, ListBaseService<T, D>, SaveBaseService<T, D> {

  /**
   * * Construtor do serviço base.
   *
   * @param config configuração do serviço base
   */
  public DefaultBaseService(DefaultBaseServiceConfig<T, D> config) {
    super(config);
    registryValidators(config.getValidators());
  }

  /**
   * Classe de configuração do serviço base.
   *
   * @param <T> {@link BaseEntity}
   * @param <D> {@link DTO}
   */
  public static class DefaultBaseServiceConfig<T extends BaseEntity, D extends DTO<T>>
    extends AbstractBaseServiceConfig<T, D> {
    /** Validadores */
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
     */
    public DefaultBaseServiceConfig(BaseEntityRepository<T> repository,
                                    BaseMapper<T, D> mapper,
                                    SearchRequestMapper searchRequestMapper,
                                    Translator translator,
                                    List<IServiceValidator<D>> validators) {
      super(repository, mapper, searchRequestMapper, translator);
      this.validators = validators;
    }

  }
}
