package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Israel Araújo
 * @param <T> Tipo do anexo.
 */
@NoRepositoryBean
public interface AttachmentRepository<T extends Attachment>
  extends BaseEntityRepository<T> {

}
