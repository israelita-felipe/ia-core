package com.ia.core.llm.service.comando;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.service.DefaultBaseService.DefaultBaseServiceConfig;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

/**
 *
 */
@Component
public class ComandoSistemaServiceConfig
  extends DefaultBaseServiceConfig<ComandoSistema, ComandoSistemaDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   */
  public ComandoSistemaServiceConfig(PlatformTransactionManager transactionManager,
                                     BaseEntityRepository<ComandoSistema> repository,
                                     BaseMapper<ComandoSistema, ComandoSistemaDTO> mapper,
                                     SearchRequestMapper searchRequestMapper,
                                     Translator translator,
                                     List<IServiceValidator<ComandoSistemaDTO>> validators) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, validators);
  }

}
