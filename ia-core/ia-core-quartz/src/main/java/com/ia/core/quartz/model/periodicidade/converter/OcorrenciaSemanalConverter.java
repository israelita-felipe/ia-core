package com.ia.core.quartz.model.periodicidade.converter;

import com.ia.core.quartz.model.periodicidade.OcorrenciaSemanal;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Israel Araújo
 */
@Converter(autoApply = true)
public class OcorrenciaSemanalConverter
  implements AttributeConverter<OcorrenciaSemanal, Integer> {

  @Override
  public Integer convertToDatabaseColumn(OcorrenciaSemanal attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getCodigo();
  }

  @Override
  public OcorrenciaSemanal convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return OcorrenciaSemanal.of(dbData);
  }

}
