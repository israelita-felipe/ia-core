package com.ia.core.security.service.log.operation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.service.DefaultBaseService.DefaultBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
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
  public LogOperationServiceConfig(PlatformTransactionManager transactionManager,
                                   BaseEntityRepository<LogOperation> repository,
                                   BaseEntityMapper<LogOperation, LogOperationDTO> mapper,
                                   SearchRequestMapper searchRequestMapper,
                                   Translator translator,
                                   List<IServiceValidator<LogOperationDTO>> validators) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, validators);
  }

}
