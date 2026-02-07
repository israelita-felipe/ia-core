package com.ia.core.view.components.form;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.ia.core.service.validators.IServiceValidator;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * Componente de validação de formulários para Views Vaadin.
 * <p>
 * Utiliza Jakarta Validation para validar objetos DTO e converte os resultados
 * para formato compatí­vel com Vaadin. Este componente deve ser injetado em
 * todos os {@link com.ia.core.view.components.form.viewModel.FormViewModel}.
 * </p>
 *
 * @author Israel Araújo
 */
@Component
public class FormValidator {

  private final Validator jakartaValidator;
  private final IServiceValidator serviceValidator;

  public FormValidator(Validator jakartaValidator, IServiceValidator serviceValidator) {
    this.jakartaValidator = jakartaValidator;
    this.serviceValidator = serviceValidator;
  }

  /**
   * Valida um objeto DTO usando Jakarta Validation.
   *
   * @param object o objeto a ser validado
   * @param bindingResult o resultado da validação do Spring
   * @return conjunto de erros de campo
   */
  public BindingResult validate(Object object, String objectName) {
    BindingResult result = new BeanPropertyBindingResult(object, objectName);
    Set<ConstraintViolation<Object>> violations = jakartaValidator.validate(object);

    for (ConstraintViolation<Object> violation : violations) {
      String fieldName = violation.getPropertyPath().toString();
      result.addError(new FieldError(objectName, fieldName,
          violation.getMessage()));
    }

    return result;
  }

  /**
   * Valida um objeto e retorna erros como mapa de campo -> mensagem.
   *
   * @param object o objeto a ser validado
   * @return mapa de erros de validação
   */
  public Map<String, String> validateToMap(Object object) {
    Map<String, String> errors = new HashMap<>();
    BindingResult result = validate(object, object.getClass().getSimpleName());

    for (FieldError error : result.getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    return errors;
  }

  /**
   * Verifica se o objeto tem erros de validação.
   *
   * @param object o objeto a ser verificado
   * @return true se houver erros
   */
  public boolean hasErrors(Object object) {
    BindingResult result = validate(object, object.getClass().getSimpleName());
    return result.hasErrors();
  }

  /**
   * Cria um validator do Vaadin a partir de um objeto.
   *
   * @param <T> tipo do objeto
   * @param object o objeto a ser validado
   * @return validator do Vaadin
   */
  public <T> AbstractValidator<T> createVaadinValidator(T object) {
    return new AbstractValidator<T>("") {
      @Override
      public ValidationResult apply(T value, ValueContext context) {
        Map<String, String> errors = validateToMap(object);
        if (!errors.isEmpty()) {
          String message = errors.values().stream()
              .findFirst()
              .orElse("Validation failed");
          return ValidationResult.forError(message);
        }
        return ValidationResult.ok();
      }
    };
  }

  /**
   * Obtém o validator Jakarta subjacente.
   *
   * @return validator Jakarta
   */
  public Validator getJakartaValidator() {
    return jakartaValidator;
  }

  /**
   * Obtém o service validator subjacente.
   *
   * @return service validator
   */
  public IServiceValidator getServiceValidator() {
    return serviceValidator;
  }
}
