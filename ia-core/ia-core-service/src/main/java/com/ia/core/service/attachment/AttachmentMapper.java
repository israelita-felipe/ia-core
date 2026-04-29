package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * Mapper para conversão entre {@link Attachment} e {@link AttachmentDTO}.
 *
 * <p>Este mapper usa MapStruct para gerar automaticamente a implementação
 * de conversão entre entidade e DTO, seguindo o padrão ADR-001.
 *
 * <p><b>Por quê usar AttachmentMapper?</b></p>
 * <ul>
 *   <li>Geração automática de código de mapeamento</li>
 *   <li>Performance superior a mapeamento manual</li>
 *   <li>Integração com Spring via componentModel = "spring"</li>
 * </ul>
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade {@link Attachment}
 * @param <A> Tipo do DTO {@link AttachmentDTO}
 * @see BaseEntityMapper
 * @since 1.0.0
 */
public interface AttachmentMapper<T extends Attachment, A extends AttachmentDTO<T>>
  extends BaseEntityMapper<T, A> {

}
