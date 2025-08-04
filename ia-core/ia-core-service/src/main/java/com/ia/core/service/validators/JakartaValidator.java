package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.Set;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.Translator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Validador Jakarta nativo do java
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto a ser validado
 */
public class JakartaValidator<T extends Serializable>
  extends ServiceValidator<T> {
  /** Instância */
  private static JakartaValidator<?> INSTANCE;
  /** Validador */
  private Validator validator = null;

  /**
   * Instância singleton
   *
   * @param <T>        Tipo do objeto
   * @param translator Tradutor
   * @return {@link JakartaValidator}
   */
  @SuppressWarnings("unchecked")
  public static <T extends Serializable> JakartaValidator<T> get(Translator translator) {
    if (INSTANCE == null) {
      INSTANCE = new JakartaValidator<>(translator);
    }
    return (JakartaValidator<T>) INSTANCE;
  }

  /**
   * Construtor padrão
   *
   * @param translator {@link Translator}
   */
  private JakartaValidator(Translator translator) {
    super(translator);
  }

  /**
   * @return {@link Validator}
   */
  public Validator getValidator() {
    if (validator == null) {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      validator = factory.getValidator();
    }
    return validator;
  }

  @Override
  public void validate(T object, ServiceException exception) {
    Validator validator = getValidator();
    // para cada violação adiciona a mensagem de exceção
    Set<ConstraintViolation<Serializable>> validations = validator
        .validate(object);
    validations.forEach(violation -> exception
        .add(getTranslator().getTranslation(violation.getMessage())));
  }

}
