package com.ia.core.model.filter;

import com.ia.core.model.util.DateTimeUtils;
import com.ia.core.model.util.EnumUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Enum que representa o tipo de campo para filtros de busca.
 *
 * <p>Cada tipo de campo sabe como fazer parsing (conversão) de valores
 * vindos como String para o tipo Java correspondente. Utiliza o padrão
 * Strategy para permitir diferentes estratégias de conversão por tipo.
 *
 * <p><b>Por quê usar FieldType?</b></p>
 * <ul>
 *   <li>Abstrai a conversão de tipos para filtros de busca</li>
 *   <li>Padroniza o parsing de datas, números, enums, etc.</li>
 *   <li>Facilita a integração com APIs REST que recebem Strings</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see FilterRequest
 * @see Operator
 * @since 1.0.0
 */
@Slf4j
public enum FieldType {
  /**
   * Tipo {@link Boolean}.
   *
   * <p>Parse aceito: "true", "false" (case-insensitive).
   * Qualquer outro valor resulta em {@code false}.
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
   * Tipo {@link Character}.
   *
   * <p>Parse: primeira letra da string. String vazia resulta em {@code null}.
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
   * Tipo {@link LocalDate}.
   *
   * <p>Parse: string no formato {@code dd/MM/yyyy} usando {@link DateTimeUtils#DATE_FORMATTER}.
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
   * Tipo {@link LocalTime}.
   *
   * <p>Parse: string no formato {@code HH:mm:ss} usando {@link DateTimeUtils#TIME_FORMATTER}.
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
   * Tipo {@link LocalDateTime}.
   *
   * <p>Parse: string no formato {@code dd/MM/yyyy HH:mm:ss}
   * usando {@link DateTimeUtils#DATE_TIME_FORMATTER}.
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
   * Tipo {@link String}.
   *
   * <p>Parse: retorna o {@code toString()} do valor. Valores nulos
   * resultam em string vazia.
   */
  STRING {
    @Override
    public Object parse(Object value) {
      return Optional.ofNullable(value).map(Object::toString).orElse("");
    }
  },
  /**
   * Tipo {@link Long}.
   *
   * <p>Parse: {@code Long.parseLong(value.toString())}.
   * Valores inválidos resultam em {@code 0L}.
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
   * Tipo {@link Integer}.
   *
   * <p>Parse: {@code Integer.parseInt(value.toString())}.
   * Valores inválidos resultam em {@code 0}.
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
   * Tipo {@link Double}.
   *
   * <p>Parse: {@code Double.parseDouble(value.toString())}.
   * Valores inválidos resultam em {@code 0.0}.
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
   * Tipo Enum.
   *
   * <p>Parse: deserializa enum usando {@link EnumUtils#deserialize(String)}.
   * Requer que o valor seja o nome da constante do enum.
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
   * Objeto genérico.
   *
   * <p>Não faz qualquer conversão, retorna o valor como está.
   * Útil para filtros que precisam passar objetos diretamente.
   */
  OBJECT;

  /**
   * Faz parsing do valor para o tipo de campo correspondente.
   *
   * <p>Método base que retorna o valor sem conversão.
   * Cada constante override este método para implementar sua própria lógica de parsing.
   *
   * @param value Valor a ser parseado (pode ser de qualquer tipo, incluindo {@code null})
   * @return Valor convertido para o tipo correspondente, ou o valor original se nenhuma conversão for aplicável
   */
  public Object parse(Object value) {
    return value;
  }
}
