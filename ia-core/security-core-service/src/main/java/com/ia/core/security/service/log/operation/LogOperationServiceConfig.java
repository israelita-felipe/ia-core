package com.ia.core.security.service.log.operation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
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
public class LogOperationServiceConfig
  extends DefaultBaseServiceConfig<LogOperation, LogOperationDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public LogOperationServiceConfig(BaseEntityRepository<LogOperation> repository,
                                   BaseMapper<LogOperation, LogOperationDTO> mapper,
                                   SearchRequestMapper searchRequestMapper,
                                   Translator translator,
                                   List<IServiceValidator<LogOperationDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }

}
