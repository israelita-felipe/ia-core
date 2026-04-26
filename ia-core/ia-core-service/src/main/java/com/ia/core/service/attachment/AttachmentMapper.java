package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Attachment} para {@link AttachmentDTO}
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 * @param <A> Tipo do {@link DTO} do anexo.
 */
public interface AttachmentMapper<T extends Attachment, A extends AttachmentDTO<T>>
  extends BaseEntityMapper<T, A> {

}
