package com.ia.core.llm.model.ontologia.converters;

import com.ia.core.llm.model.ontologia.OntologyFormat;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA para {@link OntologyFormat}.
 * <p>
 * Responsável por converter entre o enum de formato de ontologia e sua
 * representação como String no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Converter(autoApply = true)
public class OntologyFormatConverter implements AttributeConverter<OntologyFormat, String> {

  @Override
  public String convertToDatabaseColumn(OntologyFormat attribute) {
    return attribute == null ? null : attribute.name();
  }

  @Override
  public OntologyFormat convertToEntityAttribute(String dbData) {
    return dbData == null ? null : OntologyFormat.valueOf(dbData);
  }
}
