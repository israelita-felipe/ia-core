package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ia.core.service.translator.Translator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço de validação de um objeto
 *
 * @param <T> Tipo do objeto
 */
@Slf4j
public abstract class ServiceValidator<T extends Serializable>
  implements IServiceValidator<T> {
  /** Conjunto de serviços que possuem validação */
  @Getter
  private Set<HasValidation<T>> hasValidation = new HashSet<>();
  /** Tradutor da aplicação */
  @Getter
  private final Translator translator;

  /**
   * Construtor padrão
   *
   * @param translator {@link Translator}
   */
  public ServiceValidator(Translator translator) {
    super();
    this.translator = translator;
  }

  @Override
  public ServiceValidatorRegistry registry(HasValidation<T> hasValidator) {
    var registry = IServiceValidator.super.registry(hasValidator);
    log.info("{} registrado para  {}", getClass(), hasValidator.getClass());
    return registry;
  }
}
