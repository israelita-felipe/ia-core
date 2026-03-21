package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.Set;

import com.ia.core.service.exception.ValidationException;
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
   * Registra um validador temporariamente.
   * <p>
   * O validador será adicionado ao conjunto de validadores e removido quando o
   * método {@link ValidatorRegistration#close()} for chamado, ou quando usado
   * em try-with-resources.
   * </p>
   *
   * <pre>
   * try (var registration = validatorRegistryRegistry(meuValidador)) {
   *   // validações aqui
   * } // validador automaticamente removido
   * </pre>
   *
   * @param hasValidator {@link HasValidation} a ser registrado
   * @return {@link ValidatorRegistration} para uso em try-with-resources
   */
  default ValidatorRegistration registry(HasValidation<D> hasValidator) {
    getHasValidation().add(hasValidator);
    return () -> getHasValidation().remove(hasValidator);
  }

  /**
   * Realiza a validação de um {@link Serializable}.
   * <p>
   * Este método agora internamente usa
   * {@link #validate(Serializable, ValidationResult)} para obter erros
   * estruturados e os lança como {@link ValidationException} se houver erros.
   * </p>
   *
   * @param object {@link Serializable} a ser validado.
   * @throws ValidationException caso haja algum problema de validação.
   */
  default void validate(D object)
    throws ValidationException {
    ValidationResult result = ValidationResult.create();
    validate(object, result);
    if (result.hasErrors()) {
      throw new ValidationException(result);
    }
  }

  /**
   * Validação de um objeto {@link Serializable}
   *
   * @param object objeto a ser validado
   * @param result exceção de retenção.
   */
  void validate(D object, ValidationResult result);

  /**
   * Registro de um validador com suporte a try-with-resources.
   * <p>
   * Esta interface estende {@link AutoCloseable} para permitir o uso em
   * try-with-resources, garantindo que o validador seja removido
   * automaticamente ao final do bloco, mesmo em caso de exceção.
   * </p>
   *
   * <pre>
   * try (IServiceValidator.ValidatorRegistration registration = validator
   *     .registry(meuValidador)) {
   *   // validações aqui
   * } // validador automaticamente removido
   * </pre>
   */
  @FunctionalInterface
  interface ValidatorRegistration
    extends AutoCloseable {
    /**
     * Remove o validador do conjunto de validadores. Equivalente a
     * {@link #close()} para compatibilidade com AutoCloseable.
     */
    void remove();

    /**
     * Remove o validador do conjunto de validadores. Implementação padrão que
     * chama {@link #remove()}.
     */
    @Override
    default void close() {
      remove();
    }
  }
}
