package com.ia.core.llm.model.template.converters;

import com.ia.core.llm.model.template.TemplateParameterEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converte um {@link TemplateParameterEnum} para {@link Integer}.
 * <p>
 * Responsável por gerenciar a conversão entre o enum de parâmetro de template
 * e sua representação no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Converter(autoApply = true)
public class TemplateParameterConverter
  implements AttributeConverter<TemplateParameterEnum, Integer> {

  @Override
  public Integer convertToDatabaseColumn(TemplateParameterEnum attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getCodigo();
  }

  @Override
  public TemplateParameterEnum convertToEntityAttribute(Integer dbData) {
    return TemplateParameterEnum.valueOf(dbData);
  }

}
