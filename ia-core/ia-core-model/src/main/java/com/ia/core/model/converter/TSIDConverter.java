package com.ia.core.model.converter;

import com.ia.core.model.TSID;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Classe de conversão de {@link TSID}
 */
@Converter(autoApply = true)
public class TSIDConverter
  implements AttributeConverter<TSID, Long> {

  @Override
  public Long convertToDatabaseColumn(TSID attribute) {
    return attribute != null ? attribute.toLong() : null;
  }

  @Override
  public TSID convertToEntityAttribute(Long dbData) {
    return dbData != null ? TSID.from(dbData) : null;
  }
}
