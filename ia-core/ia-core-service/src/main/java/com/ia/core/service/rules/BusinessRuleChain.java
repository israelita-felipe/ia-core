package com.ia.core.service.rules;

import com.ia.core.service.exception.ValidationException;
import com.ia.core.service.validators.ValidationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Implementação do padrão Chain of Responsibility para regras de negócio.
 * <p>
 * Esta classe permite compor múltiplas {@link BusinessRule} em uma corrente,
 * onde cada regra pode:
 * </p>
 * <ul>
 * <li>Processar o objeto e passar para a próxima regra</li>
 * <li>Processar o objeto e parar a corrente</li>
 * <li>Encerrar a corrente com erro</li>
 * </ul>
 * <p>
 * A cadeia mantém a ordem de inserção das regras e as executa sequencialmente.
 * </p>
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto que a cadeia de regras avalia
 * @since 1.0.0
 */
public class BusinessRuleChain<T extends Serializable> {

  private final List<BusinessRule<? super T>> rules = new ArrayList<>();
  private Function<T, Boolean> stopCondition;

  /**
   * Adiciona uma regra ao final da cadeia.
   *
   * @param rule Regra a ser adicionada
   * @return Esta cadeia para encadeamento
   */
  public BusinessRuleChain<T> addRule(BusinessRule<? super T> rule) {
    rules.add(rule);
    return this;
  }

  /**
   * Adiciona múltiplas regras ao final da cadeia.
   *
   * @param rules Regras a serem adicionadas
   * @return Esta cadeia para encadeamento
   */
  @SafeVarargs
  public final BusinessRuleChain<T> addRules(BusinessRule<? super T>... rules) {
    Collections.addAll(this.rules, rules);
    return this;
  }

  /**
   * Adiciona uma lista de regras ao final da cadeia.
   *
   * @param rules Lista de regras a serem adicionadas
   * @return Esta cadeia para encadeamento
   */
  public BusinessRuleChain<T> addRules(List<BusinessRule<? super T>> rules) {
    this.rules.addAll(rules);
    return this;
  }

  /**
   * Define uma condição de parada para a cadeia.
   * <p>
   * Se a condição retornar {@code true}, a execução será interrompida.
   * </p>
   *
   * @param stopCondition Função que determina quando parar
   * @return Esta cadeia para encadeamento
   */
  public BusinessRuleChain<T> stopWhen(Function<T, Boolean> stopCondition) {
    this.stopCondition = stopCondition;
    return this;
  }

  /**
   * Executa todas as regras da corrente.
   *
   * @param object O objeto a ser avaliado
   * @throws ValidationException se alguma regra falhar
   */
  public void validate(T object)
    throws ValidationException {
    ValidationResult result = ValidationResult.create();
    validate(object, result);
    if (result.hasErrors()) {
      throw new ValidationException(result);
    }
  }

  /**
   * Executa todas as regras da cadeia sem lançar exceção.
   * <p>
   * Este método executa cada regra sequencialmente e acumula os erros. Ao
   * final, retorna uma exceção contendo todos os erros encontrados, ou
   * {@code null} se nenhuma falha ocorrer.
   * </p>
   *
   * @param object O objeto a ser avaliado
   * @return Exceção contendo todos os erros, ou {@code null} se válido
   */
  public ValidationException validateAndCollect(T object) {
    ValidationResult result = ValidationResult.create();
    validate(object, result);
    return result.hasErrors() ? new ValidationException(result) : null;
  }

  /**
   * Executa todas as regras da cadeia acumulando erros.
   *
   * @param object O objeto a ser avaliado
   * @param result Resultado da validação para acumular erros
   */
  public void validate(T object, ValidationResult result) {
    for (BusinessRule<? super T> rule : rules) {
      // Verifica condição de parada
      if (stopCondition != null && stopCondition.apply(object)) {
        break;
      }

      // Verifica se a regra é aplicável
      if (!rule.isApplicable(object)) {
        continue;
      }

      // Executa a validação
      rule.validate(object, result);
    }
  }

  /**
   * Retorna o número de regras na cadeia.
   *
   * @return Número de regras
   */
  public int size() {
    return rules.size();
  }

  /**
   * Verifica se a cadeia está vazia.
   *
   * @return {@code true} se a cadeia não contém regras
   */
  public boolean isEmpty() {
    return rules.isEmpty();
  }

  /**
   * Cria uma nova instância vazia de {@link BusinessRuleChain}.
   *
   * @param <T> Tipo do objeto que a cadeia avaliará
   * @return Nova instância da cadeia
   */
  public static <T extends Serializable> BusinessRuleChain<T> create() {
    return new BusinessRuleChain<>();
  }

  /**
   * Cria uma nova instância de {@link BusinessRuleChain} com uma regra inicial.
   *
   * @param rule Primeira regra da cadeia
   * @param <T>  Tipo do objeto que a cadeia avaliará
   * @return Nova instância da cadeia com a regra
   */
  public static <T extends Serializable> BusinessRuleChain<T> create(BusinessRule<? super T> rule) {
    return new BusinessRuleChain<T>().addRule(rule);
  }

}
