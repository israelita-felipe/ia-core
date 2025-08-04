package com.ia.core.service.attachment.validators;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.attachment.dto.AttachmentTranslator;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ServiceValidator;

/**
 * Validador do anexo.
 *
 * @param <T> Tipo do anexo do validador
 */
public class AttachmentValidator<T extends AttachmentDTO<?>>
  extends ServiceValidator<T> {

  /**
   * @param translator {@link Translator} do validador
   */
  public AttachmentValidator(Translator translator) {
    super(translator);
  }

  @Override
  public void validate(T object, ServiceException exception) {
    if (object.getId() == null
        && (object.getContent() == null || object.getContent().isBlank()
            || object.getContent().isEmpty())) {

      exception.add(getTranslator()
          .getTranslation(AttachmentTranslator.VALIDATION.CONTENT_NOT_NULL));
    }
  }

}
