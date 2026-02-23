package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.List;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator.ServiceValidatorRegistry;

/**
 * Interface que indica que um objeto possui validação, indicado para realizar
 * polimorfismo em classes de serviço.
 *
 * @param <T> Tipo do dado a ser validado
 */
public interface HasValidation<T extends Serializable> {
  /**
   * @return {@link JakartaValidator}
   */
  default JakartaValidator<T> createJakartaValidator() {
    return JakartaValidator.get(getTranslator());
  }

  /**
   * @return {@link Translator}
   */
  Translator getTranslator();

  /**
   * @return lista de validadores
   */
  List<IServiceValidator<T>> getValidators();

  /**
   * @param validators lista de validadores
   * @return lista de regitros dos validadores
   */
  default List<ServiceValidatorRegistry> registryValidators(List<IServiceValidator<T>> validators) {
    return validators.stream().map(validator -> {
      return validator.registry(this);
    }).toList();
  }

  /**
   * Indicativo de utilização de validação via {@link JakartaValidator}
   *
   * @return <code>true</code> por padrão.
   */
  default boolean useJakartaValidation() {
    return true;
  }

  /**
   * Realiza a validação do objeto
   *
   * @param object objeto a ser validado
   * @throws ServiceException caso ocorra erros de validação
   */
  default void validate(T object)
    throws ServiceException {
    if (useJakartaValidation()) {
      createJakartaValidator().validate(object);
    }
    for (IServiceValidator<T> validator : getValidators()) {
      validator.validate(object);
    }
  }
}
