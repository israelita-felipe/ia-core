package com.ia.core.service.attachment.validators;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.attachment.dto.AttachmentTranslator;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ServiceValidator;
import com.ia.core.service.validators.Severity;
import com.ia.core.service.validators.ValidationError;
import com.ia.core.service.validators.ValidationResult;

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
  public void validate(T object, ValidationResult result) {
    if (object.getId() == null
        && (object.getContent() == null || object.getContent().isBlank()
            || object.getContent().isEmpty())) {

      result.addError(new ValidationError(
          "content",
          getTranslator().getTranslation(AttachmentTranslator.VALIDATION.CONTENT_NOT_NULL),
          Severity.ERROR,
          null));
    }
  }

}

