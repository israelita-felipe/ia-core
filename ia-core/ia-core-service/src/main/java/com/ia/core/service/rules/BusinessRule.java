package com.ia.core.service.rules;

import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;

import java.io.Serializable;

/**
 * Interface que representa uma Regra de Negócio do sistema.
 * <p>
 * Cada regra de negócio possui uma numeração única, um nome e uma descrição
 * que a identifica e documenta dentro do contexto do domínio aplicável.
 * </p>
 * <p>
 * As regras de negócio podem ser compostas utilizando o padrão Chain of Responsibility
 * através da {@link BusinessRuleChain}.
 * </p>
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto que a regra de negócio avalia
 * @since 1.0.0
 */
public interface BusinessRule<T extends Serializable> extends Serializable {

  /**
   * Retorna o código único de identificação da regra de negócio.
   * <p>
   * Este código segue o formato: [DOMÍNIO]_001, onde:
   * </p>
   * <ul>
   *   <li>[DOMÍNIO] = Sigla do domínio (ex: USR, CTE, EVT)</li>
   *   <li>001 = Número sequencial da regra</li>
   * </ul>
   *
   * @return String contendo o código da regra
   */
  String getCode();

  /**
   * Retorna o nome da regra de negócio para exibição em interfaces.
   *
   * @return String contendo o nome da regra
   */
  String getName();

  /**
   * Retorna a descrição detalhada da regra de negócio.
   *
   * @return String contendo a descrição da regra
   */
  String getDescription();

  /**
   * Retorna o tradutor para internacionalização.
   *
   * @return Translator para internacionalização
   */
  Translator getTranslator();

  /**
   * Avalia se a regra de negócio é aplicável ao objeto fornecido.
   *
   * @param object O objeto a ser avaliado
   * @return {@code true} se a regra deve ser aplicada, {@code false} caso contrário
   */
  default boolean isApplicable(T object) {
    return true;
  }

  /**
   * Executa a validação da regra de negócio.
   * <p>
   * Este método deve adicionar erros à {@link ValidationResult} caso a regra
   * não seja satisfeita pelo objeto fornecido.
   * </p>
   *
   * @param object O objeto a ser validado
   * @param result Resultado da validação para acumular erros
   */
  void validate(T object, ValidationResult result);
}
