package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Israel Araújo
 * @param <T> Tipo do anexo.
 */
/**
 * Classe que representa o acesso a dados de attachment.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AttachmentRepository
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@NoRepositoryBean
public interface AttachmentRepository<T extends Attachment>
  extends BaseEntityRepository<T> {

}
