package com.ia.core.quartz.model.periodicidade.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Month;

/**
 * Conversor JPA para mapeamento de {@link java.time.Month} para {@link Integer} e vice-versa.
 * <p>
 * Este conversor é automaticamente aplicado a todos os atributos do tipo {@link java.time.Month}
 * em entidades JPA, convertendo-os para inteiros (1-12) no banco de dados e restaurando
 * para objetos {@link java.time.Month} ao carregar do banco.
 * <p>
 * A conversão utiliza o valor numérico padrão do Java para meses:
 * <ul>
 * <li>1 = Janeiro (JANUARY)</li>
 * <li>2 = Fevereiro (FEBRUARY)</li>
 * <li>3 = Março (MARCH)</li>
 * <li>4 = Abril (APRIL)</li>
 * <li>5 = Maio (MAY)</li>
 * <li>6 = Junho (JUNE)</li>
 * <li>7 = Julho (JULY)</li>
 * <li>8 = Agosto (AUGUST)</li>
 * <li>9 = Setembro (SEPTEMBER)</li>
 * <li>10 = Outubro (OCTOBER)</li>
 * <li>11 = Novembro (NOVEMBER)</li>
 * <li>12 = Dezembro (DECEMBER)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see java.time.Month
 * @see jakarta.persistence.AttributeConverter
 */
/**
 * Enumeração que representa o conversor de dados para month.
 * <p>
 * Define os valores possíveis para MonthConverter no sistema.
 *
 * @author IA
 * @since 1.0
 */
@Converter(autoApply = true)
public class MonthConverter
  implements AttributeConverter<Month, Integer> {

  /**
   * Converte o valor do enum {@link Month} para seu valor numérico no banco de dados.
   *
   * @param attribute O atributo {@link Month} a ser convertido. Pode ser {@code null}
   * @return O valor numérico correspondente (1-12), ou {@code null} se o atributo for {@code null}
   * @since 1.0.0
   */
  @Override
  public Integer convertToDatabaseColumn(Month attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getValue();
  }

  /**
   * Converte o valor numérico do banco de dados de volta para o enum {@link Month}.
   *
   * @param dbData O valor numérico armazenado no banco de dados. Pode ser {@code null}
   * @return O enum {@link Month} correspondente, ou {@code null} se o dbData for {@code null}
   * @throws java.time.DateTimeException se o valor não for válido (menor que 1 ou maior que 12)
   * @since 1.0.0
   */
  @Override
  public Month convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return Month.of(dbData);
  }

}
