package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.DefaultBaseService.DefaultBaseServiceConfig;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import java.util.List;

/**
 * Configuração para o serviço de anexos.
 *
 * <p>Esta classe estende {@link DefaultBaseServiceConfig} para fornecer
 * as dependências necessárias ao {@link AttachmentService}.
 *
 * <p><b>Por quê usar AttachmentServiceConfig?</b></p>
 * <ul>
 *   <li>Centraliza as dependências do AttachmentService</li>
 *   <li>Facilita testes unitários via injeção de dependências</li>
 *   <li>Segue o padrão ServiceConfig (ADR-004)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade {@link Attachment}
 * @param <D> Tipo do DTO {@link AttachmentDTO}
 * @see AttachmentService
 * @see DefaultBaseServiceConfig
 * @since 1.0.0
 */
public class AttachmentServiceConfig<T extends Attachment, D extends AttachmentDTO<T>>
  extends DefaultBaseServiceConfig<T, D> {

  /**
   * Construtor da configuração do serviço de anexos.
   *
   * @param repository          repositório da entidade
   * @param mapper              mapper entre entidade e DTO
   * @param searchRequestMapper mapper para requisições de busca
   * @param translator          tradutor para i18n
   * @param validators          lista de validadores do serviço
   */
  public AttachmentServiceConfig(BaseEntityRepository<T> repository,
                                 BaseEntityMapper<T, D> mapper,
                                 SearchRequestMapper searchRequestMapper,
                                 Translator translator,
                                 List<IServiceValidator<D>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }

}
