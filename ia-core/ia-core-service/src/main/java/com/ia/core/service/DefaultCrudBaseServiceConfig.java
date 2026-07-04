package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import java.util.List;

/**
 * Configuração padrão reutilizável para serviços {@link CrudBaseService}.
 * <p>
 * Elimina a necessidade de criar subclasses de {@link CrudBaseService.CrudBaseServiceConfig}
 * que apenas repassam parâmetros ao construtor da superclasse sem adicionar
 * campos ou lógica própria.
 *
 * @param <T> tipo da entidade
 * @param <D> tipo do DTO
 * @author Israel Araújo
 * @since 1.0.0
 */
public class DefaultCrudBaseServiceConfig<T extends BaseEntity, D extends DTO<T>>
    extends CrudBaseService.CrudBaseServiceConfig<T, D> {

  public DefaultCrudBaseServiceConfig(BaseEntityRepository<T> repository,
                                      BaseEntityMapper<T, D> mapper,
                                      SearchRequestMapper searchRequestMapper,
                                      Translator translator,
                                      List<IServiceValidator<D>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }
}
