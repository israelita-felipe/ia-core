package com.ia.core.model.converter;

import com.ia.core.model.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA para {@link TSID}.
 *
 * <p>Converte automaticamente entre o tipo {@link TSID} do domínio
 * e {@link Long} para persistência no banco de dados.
 *
 * <p>Este conversor é aplicado automaticamente a todos os atributos
 * do tipo {@link TSID} (graças a {@code autoApply = true}).
 *
 * <p><b>Por quê usar TSIDConverter?</b></p>
 * <ul>
 *   <li>Permite usar TSID diretamente em entidades JPA</li>
 *   <li>Conversão automática na leitura e escrita</li>
 *   <li>Elimina a necessidade de getters/setters customizados</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see TSID
 * @see AttributeConverter
 * @since 1.0.0
 */
/**
 * Classe que representa o conversor de dados para t s i d.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TSIDConverter
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Converter(autoApply = true)
public class TSIDConverter
  implements AttributeConverter<TSID, Long> {

  /**
   * Converte o TSID para {@link Long} ao persistir no banco de dados.
   *
   * @param attribute O TSID a ser convertido (pode ser {@code null})
   * @return O valor {@link Long} para persistência, ou {@code null} se o atributo for {@code null}
   */
  @Override
  public Long convertToDatabaseColumn(TSID attribute) {
    return attribute != null ? attribute.toLong() : null;
  }

  /**
   * Converte o {@link Long} do banco de dados para {@link TSID}.
   *
   * @param dbData O valor {@link Long} vindos do banco (pode ser {@code null})
   * @return O {@link TSID} reconstruído, ou {@code null} se o dbData for {@code null}
   */
  @Override
  public TSID convertToEntityAttribute(Long dbData) {
    return dbData != null ? TSID.from(dbData) : null;
  }
}
