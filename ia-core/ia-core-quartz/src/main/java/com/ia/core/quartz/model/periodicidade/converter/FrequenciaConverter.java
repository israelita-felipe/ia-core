package com.ia.core.quartz.model.periodicidade.converter;

import com.ia.core.quartz.model.periodicidade.Frequencia;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Israel Araújo
 */
@Converter(autoApply = true)
public class FrequenciaConverter
  implements AttributeConverter<Frequencia, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Frequencia attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getCodigo();
  }

  @Override
  public Frequencia convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return Frequencia.of(dbData);
  }

}
