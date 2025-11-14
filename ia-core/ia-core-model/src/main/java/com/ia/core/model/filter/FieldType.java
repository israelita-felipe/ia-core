package com.ia.core.model.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.ia.core.model.util.EnumUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Enum} que representa o tipo de campo.
 *
 * @author Israel Araújo
 */
@Slf4j
public enum FieldType {
  /**
   * {@link Boolean}
   */
  BOOLEAN,
  /**
   * {@link Character}
   */
  CHAR,
  /**
   * {@link LocalDate}
   */
  DATE,
  /**
   * {@link LocalTime}
   */
  TIME,
  /**
   * {@link LocalDateTime}
   */
  DATE_TIME,
  /**
   * {@link Double}
   */
  DOUBLE,
  /**
   * {@link Integer}
   */
  INTEGER,
  /**
   * {@link Long}
   */
  LONG,
  /**
   * {@link String}
   */
  STRING,
  /**
   * {@link java.util.UUID}
   */
  UUID {
    @Override
    public Object parse(Object value) {
      try {
        if (String.class.isInstance(value)) {
          return java.util.UUID.fromString((String) value);
        }
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      }
      return super.parse(value);
    }
  },
  /** Enumeração */
  ENUM {
    @Override
    public Object parse(Object value) {
      try {
        if (String.class.isInstance(value)) {
          return EnumUtils.deserialize((String) value);
        }
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      }
      return super.parse(value);
    }
  };

  /**
   * Parseamento de objeto caso necessário
   *
   * @param value objeto a ser parseado
   * @return objeto resultante
   */
  public Object parse(Object value) {
    return value;
  }

}
