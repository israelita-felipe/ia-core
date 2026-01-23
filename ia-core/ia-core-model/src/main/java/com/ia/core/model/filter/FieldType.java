package com.ia.core.model.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.ia.core.model.util.DateTimeUtils;
import com.ia.core.model.util.EnumUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Enum que representa o tipo de campo para filtros. Cada tipo de campo sabe
 * como fazer parsing de valores (conversão de String para o tipo correto).
 * Utiliza Strategy Pattern para permitir diferentes estratégias de conversão
 * por tipo.
 *
 * @author Israel Araújo
 * @see FilterRequest
 */
@Slf4j
public enum FieldType {
  /**
   * Tipo {@link Boolean}. Parse: "true" ou "false" -> Boolean.
   */
  BOOLEAN {
    @Override
    public Object parse(Object value) {
      if (value instanceof Boolean)
        return value;
      return Optional.ofNullable(value).map(Object::toString)
          .map(Boolean::parseBoolean).orElse(false);
    }
  },
  /**
   * Tipo {@link Character}. Parse: primeira letra da string.
   */
  CHAR {
    @Override
    public Object parse(Object value) {
      if (value instanceof Character)
        return value;
      String str = Optional.ofNullable(value).map(Object::toString)
          .orElse("");
      return str.isEmpty() ? null : str.charAt(0);
    }
  },
  /**
   * Tipo {@link LocalDate}. Parse: string no formato DATE_FORMATTER.
   */
  DATE {
    @Override
    public Object parse(Object value) {
      try {
        if (String.class.isInstance(value)) {
          return LocalDate.parse((String) value,
                                 DateTimeUtils.DATE_FORMATTER);
        }
      } catch (Exception e) {
        log.error("Erro ao fazer parse de DATE: {}",
                  e.getLocalizedMessage());
      }
      return super.parse(value);
    }
  },
  /**
   * Tipo {@link LocalTime}. Parse: string no formato TIME_FORMATTER.
   */
  TIME {
    @Override
    public Object parse(Object value) {
      try {
        if (String.class.isInstance(value)) {
          return LocalTime.parse((String) value,
                                 DateTimeUtils.TIME_FORMATTER);
        }
      } catch (Exception e) {
        log.error("Erro ao fazer parse de TIME: {}",
                  e.getLocalizedMessage());
      }
      return super.parse(value);
    }
  },
  /**
   * Tipo {@link LocalDateTime}. Parse: string no formato DATE_TIME_FORMATTER.
   */
  DATE_TIME {
    @Override
    public Object parse(Object value) {
      try {
        if (String.class.isInstance(value)) {
          return LocalDateTime.parse((String) value,
                                     DateTimeUtils.DATE_TIME_FORMATTER);
        }
      } catch (Exception e) {
        log.error("Erro ao fazer parse de DATETIME: {}",
                  e.getLocalizedMessage());
      }
      return super.parse(value);
    }
  },
  /**
   * Tipo {@link String}. Parse: toString() do valor.
   */
  STRING {
    @Override
    public Object parse(Object value) {
      return Optional.ofNullable(value).map(Object::toString).orElse("");
    }
  },
  /**
   * Tipo {@link Long}. Parse: Long.parseLong(value.toString()).
   */
  LONG {
    @Override
    public Object parse(Object value) {
      if (value instanceof Long)
        return value;
      try {
        return Long.parseLong(Optional.ofNullable(value)
            .map(Object::toString).orElse("0"));
      } catch (NumberFormatException e) {
        log.error("Erro ao fazer parse de LONG: {}",
                  e.getLocalizedMessage());
        return 0L;
      }
    }
  },
  /**
   * Tipo {@link Integer}. Parse: Integer.parseInt(value.toString()).
   */
  INTEGER {
    @Override
    public Object parse(Object value) {
      if (value instanceof Integer)
        return value;
      try {
        return Integer.parseInt(Optional.ofNullable(value)
            .map(Object::toString).orElse("0"));
      } catch (NumberFormatException e) {
        log.error("Erro ao fazer parse de INTEGER: {}",
                  e.getLocalizedMessage());
        return 0;
      }
    }
  },
  /**
   * Tipo {@link Double}. Parse: Double.parseDouble(value.toString()).
   */
  DOUBLE {
    @Override
    public Object parse(Object value) {
      if (value instanceof Double)
        return value;
      try {
        return Double.parseDouble(Optional.ofNullable(value)
            .map(Object::toString).orElse("0.0"));
      } catch (NumberFormatException e) {
        log.error("Erro ao fazer parse de DOUBLE: {}",
                  e.getLocalizedMessage());
        return 0.0;
      }
    }
  },
  /**
   * Tipo Enum. Parse: deserializa enum usando EnumUtils.
   */
  ENUM {
    @Override
    public Object parse(Object value) {
      try {
        if (String.class.isInstance(value)) {
          return EnumUtils.deserialize((String) value);
        }
      } catch (ClassNotFoundException e) {
        log.error("Erro ao fazer parse de ENUM: {}",
                  e.getLocalizedMessage());
      }
      return super.parse(value);
    }
  },
  /**
   * Objeto genérico
   */
  OBJECT;

  /**
   * Faz parsing do valor para o tipo de campo correspondente.
   *
   * @param value Valor a ser parseado (pode ser de qualquer tipo)
   * @return Valor convertido para o tipo correspondente, ou null se falhar
   */
  public Object parse(Object value) {
    return value;
  }
}
