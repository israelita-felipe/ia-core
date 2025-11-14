package com.ia.core.service.attachment;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.DefaultBaseService.DefaultBaseServiceConfig;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

/**
 *
 */
public class AttachmentServiceConfig<T extends Attachment, D extends AttachmentDTO<T>>
  extends DefaultBaseServiceConfig<T, D> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param validators
   */
  public AttachmentServiceConfig(PlatformTransactionManager transactionManager,
                                 BaseEntityRepository<T> repository,
                                 BaseEntityMapper<T, D> mapper,
                                 SearchRequestMapper searchRequestMapper,
                                 Translator translator,
                                 List<IServiceValidator<D>> validators) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, validators);
  }

}
