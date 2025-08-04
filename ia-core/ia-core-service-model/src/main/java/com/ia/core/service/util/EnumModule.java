package com.ia.core.service.util;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Módulo para conversão de enumeração
 *
 * @author Israel Araújo
 */
public class EnumModule
  extends com.fasterxml.jackson.databind.Module {
  /**
   * Separador
   */
  private static final String SEPARATOR = "_#_";

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
    serializers.addSerializer(Enum.class,
                              new CustomEnumSerializer(Enum.class));

    context.addSerializers(serializers);

    // deserializadores
    SimpleDeserializers deserializers = new SimpleDeserializers();

    deserializers.addDeserializer(Enum.class,
                                  new CustomEnumDeserializer(Enum.class));

    context.addDeserializers(deserializers);

  }

  /**
   * Serialização
   */
  @SuppressWarnings("rawtypes")
  private static class CustomEnumSerializer
    extends StdSerializer<Enum> {
    /** Serial UID */
    private static final long serialVersionUID = 4101055497571531181L;

    /**
     * @param t Tipo do {@link Enum}
     */
    protected CustomEnumSerializer(Class<Enum> t) {
      super(t);
    }

    @Override
    public void serialize(Enum value, JsonGenerator gen,
                          SerializerProvider provider)
      throws IOException {
      gen.writeString(value.getDeclaringClass().getCanonicalName()
          + SEPARATOR + value.name());
    }

  }

  /**
   * Deserialização
   */
  @SuppressWarnings("rawtypes")
  private static class CustomEnumDeserializer
    extends StdDeserializer<Enum> {
    /** Serial UID */
    private static final long serialVersionUID = 446973271420100633L;

    /**
     * @param t Tipo do {@link Enum}
     */
    protected CustomEnumDeserializer(Class<Enum> t) {
      super(t);
    }

    @Override
    public Enum deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
      if (p.hasToken(JsonToken.VALUE_STRING)) {
        try {
          String[] splitted = p.getText().split(SEPARATOR);
          var enumType = Class.forName(splitted[0]);
          return (Enum) Stream.of(enumType.getEnumConstants())
              .filter(enumItem -> Objects
                  .equals(splitted[1], ((Enum<?>) enumItem).name()))
              .findFirst().orElse(null);
        } catch (Exception e) {
          return null;
        }
      }
      return null;
    }

  }
}
