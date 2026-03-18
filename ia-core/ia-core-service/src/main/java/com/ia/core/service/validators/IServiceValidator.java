package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.Set;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.exception.ValidationException;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;

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
   * O validador será adicionado ao conjunto de validadores e removido quando
   * o método {@link ValidatorRegistration#close()} for chamado, ou quando usado
   * em try-with-resources.
   * </p>
   * <pre>
   * try (var registration = validatorRegistryRegistry(meuValidador)) {
   *     // validações aqui
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
   * Realiza a validação de um {@link Serializable};
   *
   * @param object {@link Serializable} a ser validado.
   * @throws ServiceException caso haja algum problema de validação.
   */
  // TODO [P1] LINHA 45-55: Refatorar validate() para retornar ValidationResult ordenada
  // validate() acumula erros em ServiceException mas sem ordem de processamento
  // Criar ValidationResult com lista ordenada por severity (ERROR, WARNING, INFO)
  // e agrupada por field para melhor UX na apresentação de erros
  // Status: PENDENTE - UX: usuário precisa ver erros em ordem lógica (campo, tipo)
  default void validate(D object)
    throws ServiceException {
    ServiceException exception = new ServiceException();
    validate(object, exception);
    if (exception.hasErros()) {
      throw exception;
    }
  }

  /**
   * Valida um objeto e retorna o resultado como {@link ValidationResult}.
   * <p>
   * Este método fornece uma alternativa ao {@link #validate(Object)} que retorna
   * um objeto estruturado com erros ordenados por severidade e agrupados por campo.
   * </p>
   * <p>
   * Nota: A implementação padrão faz uma conversão básica de ServiceException.
   * Subclasses podem sobrescrever para fornecer mapeamento customizado.
   * </p>
   *
   * @param object Objeto a ser validado
   * @return ValidationResult contendo erros ordenados e agrupados
   */
  default ValidationResult validateAndGetResult(D object) {
    ServiceException exception = new ServiceException();
    validate(object, exception);

    if (!exception.hasErros()) {
      return ValidationResult.empty();
    }

    // Retorna resultado básico com erros
    // Implementação completa depende de mapeamento específico de ServiceException
    return ValidationResult.error("validation", "Object validation failed");
  }

  /**
   * Validação de um objeto {@link Serializable}
   *
   * @param object    objeto a ser validado
   * @param exception exceção de retenção.
   */
  void validate(D object, ServiceException exception);

  /**
   * Registro de um validador com suporte a try-with-resources.
   * <p>
   * Esta interface estende {@link AutoCloseable} para permitir o uso em
   * try-with-resources, garantindo que o validador seja removido automaticamente
   * ao final do bloco, mesmo em caso de exceção.
   * </p>
   * <pre>
   * try (IServiceValidator.ValidatorRegistration registration =
   *          validator.registry(meuValidador)) {
   *     // validações aqui
   * } // validador automaticamente removido
   * </pre>
   */
  @FunctionalInterface
  interface ValidatorRegistration extends AutoCloseable {
    /**
     * Remove o validador do conjunto de validadores.
     * Equivalente a {@link #close()} para compatibilidade com AutoCloseable.
     */
    void remove();

    /**
     * Remove o validador do conjunto de validadores.
     * Implementação padrão que chama {@link #remove()}.
     */
    @Override
    default void close() {
      remove();
    }
  }

  /**
   * Registro de um validador
   * @deprecated Use {@link ValidatorRegistration} para compatibilidade com try-with-resources
   */
  @Deprecated
  @FunctionalInterface
  interface ServiceValidatorRegistry {
    /**
     * Ação de remoção do validador
     */
    void remove();
  }
}
