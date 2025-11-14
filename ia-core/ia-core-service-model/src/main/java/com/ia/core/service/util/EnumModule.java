package com.ia.core.service.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ia.core.model.util.EnumUtils;

/**
 * Módulo para conversão de enumeração
 *
 * @author Israel Araújo
 */
public class EnumModule
  extends com.fasterxml.jackson.databind.Module {

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
    addSerializers(Enum.class, new CustomEnumSerializer<Enum>(Enum.class));
    context.addSerializers(serializers);

    // deserializadores

    addDeserializers(Enum.class,
                     new CustomEnumDeserializer<Enum>(Enum.class));
    context.addDeserializers(deserializers);

  }

  /**
   * @param deserializers {@link SimpleDeserializers}
   */
  @SuppressWarnings("rawtypes")
  public <T extends Enum> void addDeserializers(Class<T> type,
                                                JsonDeserializer<T> deserializer) {
    deserializers.addDeserializer(type, deserializer);
  }

  /**
   * @param serializers {@link SimpleSerializers}
   */
  @SuppressWarnings("rawtypes")
  public <T extends Enum> void addSerializers(Class<T> type,
                                              JsonSerializer<T> serializer) {
    serializers.addSerializer(type, serializer);
  }

  /**
   * Serialização
   *
   * @param <T> Tipo do enum
   */
  @SuppressWarnings("rawtypes")
  public static class CustomEnumSerializer<T extends Enum>
    extends StdSerializer<T> {
    /** Serial UID */
    private static final long serialVersionUID = 4101055497571531181L;

    /**
     * @param t Tipo do {@link Enum}
     */
    public CustomEnumSerializer(Class<T> t) {
      super(t);
    }

    @Override
    public void serialize(T value, JsonGenerator gen,
                          SerializerProvider provider)
      throws IOException {
      gen.writeString(EnumUtils.serialize(value));
    }

  }

  /**
   * Deserialização
   *
   * @param <T> Tipo do enumerador
   */
  @SuppressWarnings("rawtypes")
  public static class CustomEnumDeserializer<T extends Enum>
    extends StdDeserializer<T> {
    /** Serial UID */
    private static final long serialVersionUID = 446973271420100633L;

    /**
     * @param t Tipo do {@link Enum}
     */
    public CustomEnumDeserializer(Class<T> t) {
      super(t);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
      if (p.hasToken(JsonToken.VALUE_STRING)) {
        try {
          return EnumUtils.deserialize(p.getText());
        } catch (Exception e) {
          return null;
        }
      }
      return null;
    }

  }

}
