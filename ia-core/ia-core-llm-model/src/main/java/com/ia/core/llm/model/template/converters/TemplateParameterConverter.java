package com.ia.core.llm.model.template.converters;

import com.ia.core.llm.model.template.TemplateParameterEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertte um {@link TemplateParameterEnum} para {@link Integer}
 *
 * @author Israel Araújo
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
