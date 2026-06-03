package com.ia.core.security.service.log.operation;

import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.service.DefaultCrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para LogOperationService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class LogOperationServiceConfig
  extends DefaultCrudBaseServiceConfig<LogOperation, LogOperationDTO> {

  public LogOperationServiceConfig(BaseEntityRepository<LogOperation> repository,
                                   BaseEntityMapper<LogOperation, LogOperationDTO> mapper,
                                   SearchRequestMapper searchRequestMapper,
                                   Translator translator,
                                   List<IServiceValidator<LogOperationDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }
}
