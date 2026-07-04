package com.ia.core.service.util;

import com.ia.core.model.util.DateTimeUtils;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.Version;
import tools.jackson.databind.*;
import tools.jackson.databind.module.SimpleDeserializers;
import tools.jackson.databind.module.SimpleSerializers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Módulo para conversão de data de hora
 *
 * @author Israel Araújo
 */
public class LocalDateTimeModule extends JacksonModule {
  /**
   * Formatador de data e hora
   */
  private final DateTimeFormatter dateTimeFormatter = DateTimeUtils.DATE_TIME_FORMATTER;
  /**
   * Formatador de data
   */
  private final DateTimeFormatter dateFormatter = DateTimeUtils.DATE_FORMATTER;
  /**
   * Formatador de tempo - flexível para aceitar ISO e formatos com/sem milissegundos
   */
  private final DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
      .appendPattern("[HH:mm:ss.SSS['Z']HH:mm:ss.SSS][HH:mm:ss['Z']HH:mm:ss][HH:mm['Z']HH:mm]")
      .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
      .toFormatter();

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
    serializers.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    serializers.addSerializer(LocalDate.class, new LocalDateSerializer());
    serializers.addSerializer(LocalTime.class, new LocalTimeSerializer());
    context.addSerializers(serializers);

    // deserializadores
    SimpleDeserializers deserializers = new SimpleDeserializers();
    deserializers.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    deserializers.addDeserializer(LocalDate.class, new LocalDateDeserializer());
    deserializers.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
    context.addDeserializers(deserializers);

  }

  class LocalDateTimeSerializer extends ValueSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializationContext serializers) {
      gen.writeString(dateTimeFormatter.format(value));
    }

    @Override
    public Class<LocalDateTime> handledType() {
      return LocalDateTime.class;
    }
  }

  class LocalDateSerializer extends ValueSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializationContext serializers) {
      gen.writeString(dateFormatter.format(value));
    }

    @Override
    public Class<LocalDate> handledType() {
      return LocalDate.class;
    }
  }

  class LocalTimeSerializer extends ValueSerializer<LocalTime> {
    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializationContext serializers) {
      gen.writeString(DateTimeUtils.TIME_FORMATTER.format(value));
    }

    @Override
    public Class<LocalTime> handledType() {
      return LocalTime.class;
    }
  }

  class LocalDateTimeDeserializer extends ValueDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) {
      String text = p.getText();
      if (text == null) {
        return null;
      }
      try {
        return LocalDateTime.parse(text, dateTimeFormatter);
      } catch (Exception e) {
        // Tenta formato ISO padrão
        return LocalDateTime.parse(text);
      }
    }

    @Override
    public Class<LocalDateTime> handledType() {
      return LocalDateTime.class;
    }
  }

  class LocalDateDeserializer extends ValueDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) {
      String text = p.getText();
      if (text == null) {
        return null;
      }
      try {
        return LocalDate.parse(text, dateFormatter);
      } catch (Exception e) {
        // Tenta formato ISO padrão
        return LocalDate.parse(text);
      }
    }

    @Override
    public Class<LocalDate> handledType() {
      return LocalDate.class;
    }
  }

  class LocalTimeDeserializer extends ValueDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) {
      String text = p.getText();
      if (text == null) {
        return null;
      }
      // Remove o sufixo 'Z' se presente (não é válido para LocalTime)
      String cleanText = text.replace("'Z'", "").replace("Z", "");
      try {
        return LocalTime.parse(cleanText, timeFormatter);
      } catch (Exception e) {
        // Tenta formato ISO padrão
        return LocalTime.parse(cleanText);
      }
    }

    @Override
    public Class<LocalTime> handledType() {
      return LocalTime.class;
    }
  }

}
