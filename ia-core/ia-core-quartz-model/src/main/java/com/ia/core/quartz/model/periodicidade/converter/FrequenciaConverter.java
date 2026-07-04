package com.ia.core.quartz.model.periodicidade.converter;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA para mapeamento de {@link Frequencia} para {@link Integer} e vice-versa.
 * <p>
 * Este conversor é automaticamente aplicado a todos os atributos do tipo {@link Frequencia}
 * em entidades JPA, convertendo-os para inteiros no banco de dados e restaurando
 * para objetos {@link Frequencia} ao carregar do banco.
 * <p>
 * A conversão utiliza o código numérico da frequência:
 * <ul>
 * <li>1 = DIARIAMENTE (DAILY)</li>
 * <li>2 = SEMANALMENTE (WEEKLY)</li>
 * <li>3 = MENSALMENTE (MONTHLY)</li>
 * <li>4 = ANUALMENTE (YEARLY)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Frequencia
 * @see jakarta.persistence.AttributeConverter
 */
/**
 * Enumeração que representa o conversor de dados para frequencia.
 * <p>
 * Define os valores possíveis para FrequenciaConverter no sistema.
 *
 * @author IA
 * @since 1.0
 */
@Converter(autoApply = true)
public class FrequenciaConverter
  implements AttributeConverter<Frequencia, Integer> {

  /**
   * Converte o valor do enum {@link Frequencia} para seu código numérico no banco de dados.
   *
   * @param attribute O atributo {@link Frequencia} a ser convertido. Pode ser {@code null}
   * @return O código numérico correspondente (1-4), ou {@code null} se o atributo for {@code null}
   * @since 1.0.0
   * @see Frequencia#getCodigo()
   */
  @Override
  public Integer convertToDatabaseColumn(Frequencia attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getCodigo();
  }

  /**
   * Converte o valor numérico do banco de dados de volta para o enum {@link Frequencia}.
   *
   * @param dbData O valor numérico armazenado no banco de dados. Pode ser {@code null}
   * @return O enum {@link Frequencia} correspondente, ou {@code null} se o dbData for {@code null}
   * @since 1.0.0
   * @see Frequencia#of(int)
   */
  @Override
  public Frequencia convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return Frequencia.of(dbData);
  }

}
