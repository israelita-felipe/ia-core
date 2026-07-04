package com.ia.core.llm.model.prompt;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA para {@link FinalidadePromptEnum}.
 * <p>
 * Responsável por converter entre o enum de finalidade de prompt e sua
 * representação como String no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Converter(autoApply = true)
public class FinalidadePromptConverter
  implements AttributeConverter<FinalidadePromptEnum, String> {

  @Override
  public String convertToDatabaseColumn(FinalidadePromptEnum attribute) {
    return attribute == null ? null : attribute.name();
  }

  @Override
  public FinalidadePromptEnum convertToEntityAttribute(String dbData) {
    return dbData == null ? null : FinalidadePromptEnum.valueOf(dbData);
  }
}
