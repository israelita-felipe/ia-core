package com.ia.core.service.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para EnumModule.
 */
@DisplayName("EnumModule Tests")
class EnumModuleTest {

  @Test
  @DisplayName("deve retornar nome do módulo")
  void testGetModuleName() {
    EnumModule module = new EnumModule();
    assertThat(module.getModuleName()).isEqualTo(EnumModule.class.getCanonicalName());
  }

  @Test
  @DisplayName("deve retornar versão")
  void testVersion() {
    EnumModule module = new EnumModule();
    assertThat(module.version()).isNotNull();
  }

  @Test
  @DisplayName("CustomEnumSerializer deve ter tipo tratado Enum")
  void testCustomEnumSerializerHandledType() {
    EnumModule.CustomEnumSerializer serializer = new EnumModule.CustomEnumSerializer();
    assertThat(serializer.handledType()).isEqualTo(Enum.class);
  }

  @Test
  @DisplayName("CustomEnumDeserializer deve ter tipo tratado Enum")
  void testCustomEnumDeserializerHandledType() {
    EnumModule.CustomEnumDeserializer deserializer = new EnumModule.CustomEnumDeserializer();
    assertThat(deserializer.handledType()).isEqualTo(Enum.class);
  }
}
