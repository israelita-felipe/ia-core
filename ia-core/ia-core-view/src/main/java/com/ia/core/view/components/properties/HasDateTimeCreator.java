package com.ia.core.view.components.properties;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.stream.Stream;

import com.ia.core.model.util.FormatUtils;
import com.ia.core.service.translator.CoreApplicationTranslator;
import com.ia.core.service.util.ShortDayOfWeek;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.timepicker.TimePicker;

/**
 * Cria data de tempo
 *
 * @author Israel Araújo
 */
public interface HasDateTimeCreator
  extends HasHelp, HasTranslator {
  /**
   * Cria campo de data
   *
   * @param label Título do campos
   * @param help  Texto de ajuda do campo
   * @return {@link DatePicker}
   */
  default DatePicker createDateField(String label, String help) {
    DatePicker field = new DatePicker(label);
    field.setI18n(getDatePickerI18n());
    setHelp(field, help);
    return field;
  }

  /**
   * Cria campo de data e tempo
   *
   * @param label Título do campos
   * @param help  Texto de ajuda do campo
   * @return {@link DateTimePicker}
   */
  default DateTimePicker createDateTimePickerField(String label,
                                                   String help) {
    DateTimePicker field = new DateTimePicker(label);
    field.setDatePickerI18n(getDatePickerI18n());
    setHelp(field, help);
    return field;
  }

  /**
   * Cria campo de tempo
   *
   * @param label Título do campos
   * @param help  Texto de ajuda do campo
   * @return {@link TimePicker}
   */
  default TimePicker createTimeField(String label, String help) {
    TimePicker field = new TimePicker(label);
    setHelp(field, help);
    return field;
  }

  /**
   * @return {@link DatePickerI18n}
   */
  default DatePickerI18n getDatePickerI18n() {
    var datePickerI18n = new DatePickerI18n();
    datePickerI18n.setCancel($(CoreApplicationTranslator.CANCELAR));
    datePickerI18n.setDateFormat(FormatUtils.DATE);
    datePickerI18n.setMonthNames(Stream.of(Month.values())
        .map(mes -> $(mes.name())).toList());
    datePickerI18n.setToday(CoreApplicationTranslator.HOJE);
    datePickerI18n.setWeekdays(Stream.of(DayOfWeek.values())
        .map(dia -> $(dia.name())).toList());
    datePickerI18n.setWeekdaysShort(Stream.of(ShortDayOfWeek.values())
        .map(dia -> $(dia.name())).toList());
    return datePickerI18n;
  }
}
