package com.ia.core.service.validators;

import java.io.Serializable;

/**
 * Represents a single validation error.
 * <p>
 * Contains information about a validation failure including:
 * </p>
 * <ul>
 *   <li>field - The name of the field that failed validation</li>
 *   <li>message - Human-readable error message</li>
 *   <li>severity - The severity level of the error</li>
 *   <li>rejectedValue - The value that was rejected</li>
 * </ul>
 *
 * @param field Field name that failed validation
 * @param message Error message
 * @param severity Severity level
 * @param rejectedValue The value that was rejected (can be null)
 */
public record ValidationError(
  String field,
  String message,
  Severity severity,
  Serializable rejectedValue
) {

  /**
   * Creates a validation error with ERROR severity.
   *
   * @param field Field name
   * @param message Error message
   */
  public ValidationError(String field, String message) {
    this(field, message, Severity.ERROR, null);
  }

  /**
   * Creates a validation error with a rejected value.
   *
   * @param field Field name
   * @param message Error message
   * @param rejectedValue The rejected value
   */
  public ValidationError(String field, String message, Serializable rejectedValue) {
    this(field, message, Severity.ERROR, rejectedValue);
  }

  /**
   * @return Field name in camelCase
   */
  public String getField() {
    return field;
  }

  /**
   * @return Human-readable error message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return Severity level
   */
  public Severity getSeverity() {
    return severity;
  }

  /**
   * @return Rejected value or null
   */
  public Serializable getRejectedValue() {
    return rejectedValue;
  }

  @Override
  public String toString() {
    return "ValidationError{field='%s', message='%s', severity=%s}".formatted(
      field, message, severity
    );
  }
}
