package com.ia.core.llm.model.comando.converters;

import com.ia.core.llm.model.comando.FinalidadeComandoEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertte um {@link FinalidadeComandoEnum} para {@link String}
 *
 * @author Israel Araújo
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
