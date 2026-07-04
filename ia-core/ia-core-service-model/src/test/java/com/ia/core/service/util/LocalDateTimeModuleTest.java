package com.ia.core.service.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para LocalDateTimeModule.
 */
@DisplayName("LocalDateTimeModule Tests")
class LocalDateTimeModuleTest {

  @Test
  @DisplayName("deve retornar nome do módulo")
  void testGetModuleName() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    assertThat(module.getModuleName()).isEqualTo(LocalDateTimeModule.class.getCanonicalName());
  }

  @Test
  @DisplayName("deve retornar versão")
  void testVersion() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    assertThat(module.version()).isNotNull();
  }

  @Test
  @DisplayName("LocalDateTimeSerializer deve ter tipo tratado LocalDateTime")
  void testLocalDateTimeSerializerHandledType() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    LocalDateTimeModule.LocalDateTimeSerializer serializer = module.new LocalDateTimeSerializer();
    assertThat(serializer.handledType()).isEqualTo(java.time.LocalDateTime.class);
  }

  @Test
  @DisplayName("LocalDateSerializer deve ter tipo tratado LocalDate")
  void testLocalDateSerializerHandledType() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    LocalDateTimeModule.LocalDateSerializer serializer = module.new LocalDateSerializer();
    assertThat(serializer.handledType()).isEqualTo(java.time.LocalDate.class);
  }

  @Test
  @DisplayName("LocalTimeSerializer deve ter tipo tratado LocalTime")
  void testLocalTimeSerializerHandledType() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    LocalDateTimeModule.LocalTimeSerializer serializer = module.new LocalTimeSerializer();
    assertThat(serializer.handledType()).isEqualTo(java.time.LocalTime.class);
  }

  @Test
  @DisplayName("LocalDateTimeDeserializer deve ter tipo tratado LocalDateTime")
  void testLocalDateTimeDeserializerHandledType() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    LocalDateTimeModule.LocalDateTimeDeserializer deserializer = module.new LocalDateTimeDeserializer();
    assertThat(deserializer.handledType()).isEqualTo(java.time.LocalDateTime.class);
  }

  @Test
  @DisplayName("LocalDateDeserializer deve ter tipo tratado LocalDate")
  void testLocalDateDeserializerHandledType() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    LocalDateTimeModule.LocalDateDeserializer deserializer = module.new LocalDateDeserializer();
    assertThat(deserializer.handledType()).isEqualTo(java.time.LocalDate.class);
  }

  @Test
  @DisplayName("LocalTimeDeserializer deve ter tipo tratado LocalTime")
  void testLocalTimeDeserializerHandledType() {
    LocalDateTimeModule module = new LocalDateTimeModule();
    LocalDateTimeModule.LocalTimeDeserializer deserializer = module.new LocalTimeDeserializer();
    assertThat(deserializer.handledType()).isEqualTo(java.time.LocalTime.class);
  }
}
