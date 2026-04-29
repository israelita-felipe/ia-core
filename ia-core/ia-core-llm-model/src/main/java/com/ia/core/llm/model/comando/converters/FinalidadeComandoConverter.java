package com.ia.core.llm.model.comando.converters;

import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converte um {@link FinalidadeComandoEnum} para {@link String}.
 * <p>
 * Responsável por gerenciar a conversão entre o enum de finalidade de comando
 * e sua representação no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Converter(autoApply = true)
public class FinalidadeComandoConverter
  implements AttributeConverter<FinalidadeComandoEnum, String> {

  @Override
  public String convertToDatabaseColumn(FinalidadeComandoEnum attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.name();
  }

  @Override
  public FinalidadeComandoEnum convertToEntityAttribute(String dbData) {
    return FinalidadeComandoEnum.valueOf(dbData);
  }

}
