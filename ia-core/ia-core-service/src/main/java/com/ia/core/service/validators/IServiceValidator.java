package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.Set;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.Translator;

/**
 * Serviço do validação
 *
 * @author Israel Araújo
 * @param <D> Tipo do objeto de validação
 */
public interface IServiceValidator<D extends Serializable> {
  /**
   * @return conjunto de objetos que implementam {@link HasValidation} para o
   *         tipo de objeto indicado
   */
  Set<HasValidation<D>> getHasValidation();

  /**
   * @return {@link Translator}
   */
  Translator getTranslator();

  /**
   * @param hasValidator {@link HasValidation} a ser registrado
   * @return {@link Runnable} contendo a ação de remover o validador da lista de
   *         validadores
   */
  default ServiceValidatorRegistry registry(HasValidation<D> hasValidator) {
    getHasValidation().add(hasValidator);
    return () -> getHasValidation().remove(hasValidator);
  }

  /**
   * Realiza a validação de um {@link Serializable};
   *
   * @param object {@link Serializable} a ser validado.
   * @throws ServiceException caso haja algum problema de validação.
   */
  default void validate(D object)
    throws ServiceException {
    ServiceException exception = new ServiceException();
    validate(object, exception);
    if (exception.hasErros()) {
      throw exception;
    }
  }

  /**
   * Validação de um objeto {@link Serializable}
   *
   * @param object    objeto a ser validado
   * @param exception exceção de retenção.
   */
  void validate(D object, ServiceException exception);

  /**
   * Registro de um validador
   */
  @FunctionalInterface
  public static interface ServiceValidatorRegistry {
    /**
     * Ação de remoção do validador
     */
    void remove();
  }
}
