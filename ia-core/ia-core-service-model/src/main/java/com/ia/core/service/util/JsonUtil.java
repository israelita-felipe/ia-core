package com.ia.core.service.util;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Israel Araújo
 */
public class JsonUtil {
  /**
   * Cria o parser GSON que será utilizado
   */
  private static final Gson JSON = new GsonBuilder()
      .registerTypeAdapter(LocalDateTime.class,
                           new LocalDateTimeTypeAdapter())
      .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
      .create();

  /**
   * Captura as prorpriedades de um determinado objeto em JSON.
   *
   * @param object objeto em JSON
   * @return Mapa de propriedades e objetos
   */
  public static Map<String, Object> getProperties(final String object) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> fromJson = JSON
        .<Map<String, Object>> fromJson(object, Map.class);
    for (String key : fromJson.keySet()) {
      Object value = fromJson.get(key);
      putValue(result, key, value);
    }
    return result;
  }

  /**
   * Atribui um valor em um mapa de objeto dado uma chave
   *
   * @param result mapa de objetos e propriedades
   * @param key    chave da propriedade
   * @param value  valor da propriedade
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static void putValue(Map<String, Object> result, String key,
                               Object value) {
    if (Map.class.isAssignableFrom(value.getClass())) {
      Map<String, Object> valueAsMap = (Map<String, Object>) value;
      for (String currentKey : valueAsMap.keySet()) {
        putValue(result, key + "." + currentKey,
                 valueAsMap.get(currentKey));
      }
    } else if (value.getClass().isArray()) {
      int i = 0;
      for (Object item : (Object[]) value) {
        putValue(result, key + "[" + (i++) + "]", item);
      }
    } else if (List.class.isAssignableFrom(value.getClass())) {
      int i = 0;
      for (Object item : (List) value) {
        putValue(result, key + "[" + (i++) + "]", item);
      }
    } else {
      result.put(key, value);
    }

  }

  /**
   * Transforma um objeto em JSON
   *
   * @param object Objeto a ser transformado
   * @return representação JSON do objeto fornecdo
   */
  public static String toJson(Object object) {
    return JSON.toJson(object);
  }

  /****************************************************************************
   * Adaptadores temporais
   ***************************************************************************/
  /**
   * Adapturad de data e tempo
   */
  public static class LocalDateTimeTypeAdapter
    implements JsonDeserializer<LocalDateTime>,
    JsonSerializer<LocalDateTime> {
    /** Formato */
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /** Formatador */
    private DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(dateFormat);

    @Override
    public LocalDateTime deserialize(JsonElement json, Type type,
                                     JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
      return ZonedDateTime
          .parse(json.getAsJsonPrimitive().getAsString(), formatter)
          .toLocalDateTime();
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      return new JsonPrimitive(formatter.format(src));
    }
  }

  /**
   * Adaptador para data
   */
  public static class LocalDateTypeAdapter
    implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    /** Formato */
    private String dateFormat = "yyyy-MM-dd";
    /** Formatador */
    private DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(dateFormat);

    @Override
    public LocalDate deserialize(JsonElement json, Type type,
                                 JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
      return LocalDate.parse(json.getAsJsonPrimitive().getAsString(),
                             formatter);
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      return new JsonPrimitive(formatter.format(src));
    }
  }

  /**
   * Adaptador para tempo
   */
  public static class LocalTimeTypeAdapter
    implements JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {
    /** Formato */
    private String dateFormat = "HH:mm:ss.SSS'Z'";
    /** Formatador */
    private DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(dateFormat);

    @Override
    public LocalTime deserialize(JsonElement json, Type type,
                                 JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
      return LocalTime.parse(json.getAsJsonPrimitive().getAsString(),
                             formatter);
    }

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      return new JsonPrimitive(formatter.format(src));
    }
  }

}
