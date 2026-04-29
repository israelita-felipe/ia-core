package com.ia.core.quartz.model.periodicidade.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;

/**
 * Conversor JPA para mapeamento de {@link java.time.DayOfWeek} para {@link Integer} e vice-versa.
 * <p>
 * Este conversor é automaticamente aplicado a todos os atributos do tipo {@link java.time.DayOfWeek}
 * em entidades JPA, convertendo-os para inteiros (1-7) no banco de dados e restaurando
 * para objetos {@link java.time.DayOfWeek} ao carregar do banco.
 * <p>
 * A conversão utiliza o valor numérico padrão do Java para dias da semana:
 * <ul>
 * <li>1 = Segunda-feira (MONDAY)</li>
 * <li>2 = Terça-feira (TUESDAY)</li>
 * <li>3 = Quarta-feira (WEDNESDAY)</li>
 * <li>4 = Quinta-feira (THURSDAY)</li>
 * <li>5 = Sexta-feira (FRIDAY)</li>
 * <li>6 = Sábado (SATURDAY)</li>
 * <li>7 = Domingo (SUNDAY)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see java.time.DayOfWeek
 * @see jakarta.persistence.AttributeConverter
 */
/**
 * Enumeração que representa o conversor de dados para day of week.
 * <p>
 * Define os valores possíveis para DayOfWeekConverter no sistema.
 *
 * @author IA
 * @since 1.0
 */
@Converter(autoApply = true)
public class DayOfWeekConverter
  implements AttributeConverter<DayOfWeek, Integer> {

  /**
   * Converte o valor do enum {@link DayOfWeek} para seu valor numérico no banco de dados.
   *
   * @param attribute O atributo {@link DayOfWeek} a ser convertido. Pode ser {@code null}
   * @return O valor numérico correspondente (1-7), ou {@code null} se o atributo for {@code null}
   * @since 1.0.0
   */
  @Override
  public Integer convertToDatabaseColumn(DayOfWeek attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getValue();
  }

  /**
   * Converte o valor numérico do banco de dados de volta para o enum {@link DayOfWeek}.
   *
   * @param dbData O valor numérico armazenado no banco de dados. Pode ser {@code null}
   * @return O enum {@link DayOfWeek} correspondente, ou {@code null} se o dbData for {@code null}
   * @throws java.time.DateTimeException se o valor não for válido (menor que 1 ou maior que 7)
   * @since 1.0.0
   */
  @Override
  public DayOfWeek convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return DayOfWeek.of(dbData);
  }

}
