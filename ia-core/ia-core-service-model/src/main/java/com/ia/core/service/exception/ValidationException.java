package com.ia.core.service.exception;

import com.ia.core.service.validators.ValidationResult;

/**
 * Exception thrown when validation fails.
 * <p>
 * This exception encapsulates a {@link ValidationResult} that contains
 * all validation errors in an organized structure (ordered by severity
 * and grouped by field).
 * </p>
 *
 * <h2>Usage</h2>
 * <pre>
 * try {
 *     validator.validate(myObject);
 * } catch (ValidationException e) {
 *     ValidationResult result = e.getValidationResult();
 *     // Handle errors organized by field
 *     result.getErrorsByField().forEach((field, errors) -> {
 *         System.out.println(field + ": " + errors);
 *     });
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
public class ValidationException extends ServiceException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_ERROR_CODE = "VALIDATION_ERROR";

  private final ValidationResult validationResult;

  /**
   * Constructs a new ValidationException with the given validation result.
   *
   * @param validationResult The validation result containing errors
   */
  public ValidationException(ValidationResult validationResult) {
    super(DEFAULT_ERROR_CODE, "Validation failed");
    this.validationResult = validationResult;
  }

  /**
   * Constructs a new ValidationException with a message and validation result.
   *
   * @param message Error message
   * @param validationResult The validation result containing errors
   */
  public ValidationException(String message, ValidationResult validationResult) {
    super(DEFAULT_ERROR_CODE, message);
    this.validationResult = validationResult;
  }

  /**
   * Constructs a new ValidationException with error code, message and validation result.
   *
   * @param errorCode Error code
   * @param message Error message
   * @param validationResult The validation result containing errors
   */
  public ValidationException(String errorCode, String message, ValidationResult validationResult) {
    super(errorCode, message);
    this.validationResult = validationResult;
  }

  /**
   * @return The validation result containing organized errors
   */
  public ValidationResult getValidationResult() {
    return validationResult;
  }

  /**
   * @return true if there are validation errors
   */
  @Override
  public boolean hasErros() {
    return validationResult != null && validationResult.hasErrors();
  }
}
