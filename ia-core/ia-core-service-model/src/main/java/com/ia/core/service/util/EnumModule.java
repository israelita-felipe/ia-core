package com.ia.core.service.util;

import java.io.IOException;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.Version;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.module.SimpleDeserializers;
import tools.jackson.databind.module.SimpleSerializers;

import com.ia.core.model.util.EnumUtils;

/**
 * Módulo para conversão de enumeração
 *
 * @author Israel Araújo
 */
public class EnumModule extends JacksonModule {

  private SimpleSerializers serializers = new SimpleSerializers();
  private SimpleDeserializers deserializers = new SimpleDeserializers();

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
    addSerializers(Enum.class, new CustomEnumSerializer());
    context.addSerializers(serializers);

    // deserializadores

    addDeserializers(Enum.class, new CustomEnumDeserializer());
    context.addDeserializers(deserializers);

  }

  /**
   * @param deserializers {@link SimpleDeserializers}
   */
  @SuppressWarnings("rawtypes")
  public <T extends Enum> void addDeserializers(Class<T> type,
                                                ValueDeserializer<T> deserializer) {
    deserializers.addDeserializer(type, deserializer);
  }

  /**
   * @param serializers {@link SimpleSerializers}
   */
  @SuppressWarnings("rawtypes")
  public <T extends Enum> void addSerializers(Class<T> type,
                                              ValueSerializer<T> serializer) {
    serializers.addSerializer(type, serializer);
  }

  /**
   * Serialização
   *
   * @param <T> Tipo do enum
   */
  @SuppressWarnings("rawtypes")
  public static class CustomEnumSerializer extends ValueSerializer<Enum> {
    @Override
    public void serialize(Enum value, JsonGenerator gen, SerializationContext serializers) {
      gen.writeString(EnumUtils.serialize(value));
    }

    @Override
    public Class<Enum> handledType() {
      return Enum.class;
    }
  }

  /**
   * Deserialização
   *
   * @param <T> Tipo do enumerador
   */
  @SuppressWarnings("rawtypes")
  public static class CustomEnumDeserializer extends ValueDeserializer<Enum> {
    @Override
    public Enum deserialize(JsonParser p, DeserializationContext ctxt) {
      if (p.hasToken(JsonToken.VALUE_STRING)) {
        try {
          return EnumUtils.deserialize(p.getText());
        } catch (Exception e) {
          return null;
        }
      }
      return null;
    }

    @Override
    public Class<Enum> handledType() {
      return Enum.class;
    }
  }

}
