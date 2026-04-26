package com.ia.core.service.validators;

import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.rules.BusinessRuleChain;
import com.ia.core.service.translator.Translator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Serviço de validação de um objeto
 * <p>
 * Fornece estrutura base para validação usando BusinessRuleChain e
 * HasValidation. Subclasses podem adicionar regras de negócio através do
 * atributo businessRules.
 * </p>
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
  /** Lista de regras de negócio para validação */
  @Getter
  private final List<BusinessRule<T>> businessRules = new ArrayList<>();

  /**
   * Construtor padrão
   *
   * @param translator {@link Translator}
   */
  public ServiceValidator(Translator translator) {
    super();
    this.translator = translator;
  }

  /**
   * Construtor com lista de regras de negócio
   *
   * @param translator   {@link Translator}
   * @param businessRules Lista de regras de negócio
   */
  public ServiceValidator(Translator translator,
                          List<BusinessRule<T>> businessRules) {
    super();
    this.translator = translator;
    if (businessRules != null) {
      this.businessRules.addAll(businessRules);
    }
  }

  @Override
  public ValidatorRegistration registry(HasValidation<T> hasValidator) {
    var registration = IServiceValidator.super.registry(hasValidator);
    log.info("{} registrado para  {}", getClass(), hasValidator.getClass());
    return registration;
  }

  /**
   * Valida o objeto usando BusinessRuleChain e HasValidation.
   * <p>
   * Este método pode ser sobrescrito por subclasses para lógica adicional, ou
   * usado diretamente se a validação for apenas via BusinessRuleChain.
   * </p>
   *
   * @param object Objeto a ser validado
   * @param result Resultado da validação
   */
  @Override
  public void validate(T object, ValidationResult result) {
    // Executar BusinessRuleChain se houver regras
    if (!businessRules.isEmpty()) {
      BusinessRuleChain<T> chain = BusinessRuleChain.<T>create()
          .addRules(businessRules.toArray(new BusinessRule[0]));
      chain.validate(object, result);
      log.info("DTO [{}] validado com {} regras", object, businessRules.size());
    }


  }

  /**
   * Adiciona uma regra de negócio ao validador.
   *
   * @param rule Regra de negócio a ser adicionada
   * @return this para chaining
   */
  protected ServiceValidator<T> addRule(BusinessRule<T> rule) {
    if (rule != null) {
      this.businessRules.add(rule);
    }
    return this;
  }
}
