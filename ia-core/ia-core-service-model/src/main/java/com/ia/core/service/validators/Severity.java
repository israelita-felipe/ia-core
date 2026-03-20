package com.ia.core.service.validators;

/**
 * Enum representing the severity level of validation errors.
 * <p>
 * Errors are ordered from most severe to least severe:
 * ERROR > WARNING > INFO
 * </p>
 */
public enum Severity {
  /**
   * Critical error that prevents the operation from completing.
   */
  ERROR(0),

  /**
   * Warning that indicates a potential issue but doesn't block the operation.
   */
  WARNING(1),

  /**
   * Informational message about the validation.
   */
  INFO(2);

  private final int order;

  Severity(int order) {
    this.order = order;
  }

  /**
   * @return Order value (lower = more severe)
   */
  public int getOrder() {
    return order;
  }
}
