package com.ia.core.quartz.model.periodicidade.converter;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Israel Araújo
 */
@Converter(autoApply = true)
public class DayOfWeekConverter
  implements AttributeConverter<DayOfWeek, Integer> {

  @Override
  public Integer convertToDatabaseColumn(DayOfWeek attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getValue();
  }

  @Override
  public DayOfWeek convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return DayOfWeek.of(dbData);
  }

}
