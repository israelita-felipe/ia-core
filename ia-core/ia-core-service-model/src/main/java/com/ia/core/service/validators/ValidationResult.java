package com.ia.core.service.validators;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interface representing the result of a validation operation.
 * <p>
 * Provides methods to:
 * </p>
 * <ul>
 *   <li>Check if there are any errors</li>
 *   <li>Get all errors ordered by severity</li>
 *   <li>Get errors grouped by field</li>
 *   <li>Get errors filtered by severity</li>
 * </ul>
 * <p>
 * The errors are automatically ordered by severity (ERROR > WARNING > INFO)
 * when retrieved through the appropriate methods.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * ValidationResult result = validator.validate(myObject);
 * if (result.hasErrors()) {
 *   // Get all errors ordered by severity
 *   List<ValidationError> allErrors = result.getErrors();
 *
 *   // Get errors for a specific field
 *   List<ValidationError> fieldErrors = result.getErrorsByField("email");
 *
 *   // Get only errors (not warnings or info)
 *   List<ValidationError> errors = result.getErrorsBySeverity(Severity.ERROR);
 * }
 * </pre>
 */
public interface ValidationResult {

  /**
   * Checks if there are any validation errors.
   *
   * @return true if there are any errors (ERROR, WARNING, or INFO)
   */
  boolean hasErrors();

  /**
   * Checks if there are any errors with severity ERROR.
   *
   * @return true if there are ERROR level errors
   */
  boolean hasErrorLevelErrors();

  /**
   * Returns all validation errors ordered by severity.
   * <p>
   * Errors are sorted: ERROR first, then WARNING, then INFO.
   * </p>
   *
   * @return Unmodifiable list of errors (never null)
   */
  List<ValidationError> getErrors();

  /**
   * Returns all errors grouped by field name.
   * <p>
   * The map key is the field name, and the value is a list of errors for that field.
   * Errors within each field are ordered by severity.
   * </p>
   *
   * @return Unmodifiable map of field names to error lists (never null)
   */
  Map<String, List<ValidationError>> getErrorsByField();

  /**
   * Returns errors for a specific field.
   *
   * @param field Field name
   * @return Unmodifiable list of errors for the field (empty list if none)
   */
  List<ValidationError> getErrorsByField(String field);

  /**
   * Returns errors filtered by severity level.
   *
   * @param severity Severity level to filter by
   * @return Unmodifiable list of errors with the specified severity (empty list if none)
   */
  List<ValidationError> getErrorsBySeverity(Severity severity);

  /**
   * Creates an empty validation result.
   *
   * @return Empty ValidationResult instance
   */
  static ValidationResult empty() {
    return EmptyValidationResult.INSTANCE;
  }

  /**
   * Creates a validation result from a list of errors.
   * <p>
     The errors are automatically sorted by severity when retrieved.
     </p>
   *
   * @param errors List of validation errors
   * @return ValidationResult containing the errors
   */
  static ValidationResult of(List<ValidationError> errors) {
    return new DefaultValidationResult(errors);
  }

  /**
   * Creates a single-field validation result.
   *
   * @param field Field name
   * @param message Error message
   * @return ValidationResult with a single ERROR
   */
  static ValidationResult error(String field, String message) {
    return of(List.of(new ValidationError(field, message)));
  }

  /**
   * Creates a single-field validation result with severity.
   *
   * @param field Field name
   * @param message Error message
   * @param severity Severity level
   * @return ValidationResult with a single error
   */
  static ValidationResult of(String field, String message, Severity severity) {
    return of(List.of(new ValidationError(field, message, severity, null)));
  }

  /**
   * Empty implementation of ValidationResult.
   */
  final class EmptyValidationResult implements ValidationResult {
    private static final EmptyValidationResult INSTANCE = new EmptyValidationResult();

    @Override
    public boolean hasErrors() {
      return false;
    }

    @Override
    public boolean hasErrorLevelErrors() {
      return false;
    }

    @Override
    public List<ValidationError> getErrors() {
      return Collections.emptyList();
    }

    @Override
    public Map<String, List<ValidationError>> getErrorsByField() {
      return Collections.emptyMap();
    }

    @Override
    public List<ValidationError> getErrorsByField(String field) {
      return Collections.emptyList();
    }

    @Override
    public List<ValidationError> getErrorsBySeverity(Severity severity) {
      return Collections.emptyList();
    }
  }

  /**
   * Default implementation of ValidationResult.
   */
  final class DefaultValidationResult implements ValidationResult {
    private final List<ValidationError> errors;
    private final Map<String, List<ValidationError>> errorsByField;

    private static final Comparator<ValidationError> SEVERITY_COMPARATOR =
      Comparator.comparingInt(e -> e.getSeverity().getOrder());

    DefaultValidationResult(List<ValidationError> errors) {
      this.errors = errors != null ? List.copyOf(errors) : Collections.emptyList();
      this.errorsByField = this.errors.stream()
        .collect(Collectors.collectingAndThen(
          Collectors.groupingBy(ValidationError::getField),
          map -> Collections.unmodifiableMap(map)
        ));
    }

    @Override
    public boolean hasErrors() {
      return !errors.isEmpty();
    }

    @Override
    public boolean hasErrorLevelErrors() {
      return errors.stream().anyMatch(e -> e.getSeverity() == Severity.ERROR);
    }

    @Override
    public List<ValidationError> getErrors() {
      return errors.stream()
        .sorted(SEVERITY_COMPARATOR)
        .toList();
    }

    @Override
    public Map<String, List<ValidationError>> getErrorsByField() {
      return errorsByField;
    }

    @Override
    public List<ValidationError> getErrorsByField(String field) {
      return errorsByField.getOrDefault(field, Collections.emptyList());
    }

    @Override
    public List<ValidationError> getErrorsBySeverity(Severity severity) {
      return errors.stream()
        .filter(e -> e.getSeverity() == severity)
        .sorted(SEVERITY_COMPARATOR)
        .toList();
    }
  }
}
