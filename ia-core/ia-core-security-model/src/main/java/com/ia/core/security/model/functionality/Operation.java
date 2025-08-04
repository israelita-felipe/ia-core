package com.ia.core.security.model.functionality;

/**
 * @author Israel Araújo
 */
public interface Operation {
  /** Separador */
  String SEPARATOR = "#";

  default String create(String name) {
    return String.format("%s%s%s", name, SEPARATOR, value());
  }

  String value();
}
