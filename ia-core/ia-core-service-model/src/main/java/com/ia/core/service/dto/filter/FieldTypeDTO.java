package com.ia.core.service.dto.filter;

/**
 * Enum contendo o tipo de campo a ser filtrado.
 */
public enum FieldTypeDTO {
  /** Lógico */
  BOOLEAN,
  /** Caractere */
  CHAR,
  /** Data */
  DATE,
  /** Tempo */
  TIME,
  /** Data e tempo */
  DATE_TIME,
  /** Número real com precisão dupla */
  DOUBLE,
  /** Número inteiro */
  INTEGER,
  /** Número inteiro longo */
  LONG,
  /** Texto */
  STRING,
  /** UUID padrão {@link java.util.UUID} */
  UUID,
  /** Tipo enum */
  ENUM;
}
