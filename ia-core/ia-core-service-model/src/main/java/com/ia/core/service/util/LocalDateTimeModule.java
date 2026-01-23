package com.ia.core.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.ia.core.model.util.DateTimeUtils;

/**
 * Módulo para conversão de data de hora
 *
 * @author Israel Araújo
 */
public class LocalDateTimeModule
  extends com.fasterxml.jackson.databind.Module {
  /**
   * Formatador de data e hora
   */
  private final DateTimeFormatter dateTimeFormatter = DateTimeUtils.DATE_TIME_FORMATTER;
  /**
   * Formatador de data
   */
  private final DateTimeFormatter dateFormatter = DateTimeUtils.DATE_FORMATTER;
  /**
   * Formatador de tempo
   */
  private final DateTimeFormatter timeFormatter = DateTimeUtils.TIME_FORMATTER;

  @Override
  public String getModuleName() {
    return getClass().getCanonicalName();
  }

  @Override
  public Version version() {
    return new Version(0, 1, 0, "", null, null);
  }

  @Override
  public void setupModule(SetupContext context) {

    // serializadores
    SimpleSerializers serializers = new SimpleSerializers();
    serializers
        .addSerializer(LocalDateTime.class,
                       new LocalDateTimeSerializer(dateTimeFormatter));
    serializers.addSerializer(LocalDate.class,
                              new LocalDateSerializer(dateFormatter));
    serializers.addSerializer(LocalTime.class,
                              new LocalTimeSerializer(timeFormatter));
    context.addSerializers(serializers);

    // deserializadores
    SimpleDeserializers deserializers = new SimpleDeserializers();

    deserializers
        .addDeserializer(LocalDateTime.class,
                         new LocalDateTimeDeserializer(dateTimeFormatter));
    deserializers.addDeserializer(LocalDate.class,
                                  new LocalDateDeserializer(dateFormatter));
    deserializers.addDeserializer(LocalTime.class,
                                  new LocalTimeDeserializer(timeFormatter));
    context.addDeserializers(deserializers);

  }

}
