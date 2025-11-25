package com.ia.core.service.util;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
 * Utilitário para manipulação de objetos JSON com suporte a tipos temporais do
 * Java Time API. Fornece métodos para serialização, desserialização e extração
 * de propriedades de objetos JSON.
 *
 * @author Israel Araújo
 */
public class JsonUtil {

  /**
   * Instância do parser GSON configurado com adaptadores para tipos temporais.
   * Inclui suporte para LocalDateTime, LocalDate e LocalTime.
   */
  private static final Gson JSON = new GsonBuilder()
      .registerTypeAdapter(LocalDateTime.class,
                           new LocalDateTimeTypeAdapter())
      .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
      .create();

  // Construtor privado para evitar instanciação
  private JsonUtil() {
    throw new IllegalStateException("Classe utilitária - não deve ser instanciada");
  }

  /**
   * Extrai todas as propriedades de um objeto JSON, incluindo propriedades
   * aninhadas. As propriedades aninhadas são representadas usando notação de
   * ponto (ex: "pessoa.endereco.rua").
   *
   * @param jsonString            String contendo o objeto JSON
   * @param maxLevel              Nível máximo de profundidade para exploração
   *                              de objetos aninhados. Valores negativos
   *                              indicam profundidade ilimitada.
   * @param includeComplexObjects Se true, inclui objetos complexos como strings
   *                              formatadas
   * @param ignoreProperties      Lista de propriedades que devem ser ignoradas
   *                              durante a extração
   * @return Mapa contendo todas as propriedades com suas respectivas chaves e
   *         valores
   * @throws JsonParseException se a string JSON for inválida
   */
  public static Map<String, Object> getProperties(final String jsonString,
                                                  int maxLevel,
                                                  boolean includeComplexObjects,
                                                  String... ignoreProperties) {
    Objects.requireNonNull(jsonString, "A string JSON não pode ser nula");

    Map<String, Object> result = new HashMap<>();
    Map<String, Object> jsonMap = JSON.fromJson(jsonString, Map.class);

    if (jsonMap != null) {
      for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
        processValue(result, entry.getKey(), entry.getValue(), maxLevel,
                     includeComplexObjects, 0, ignoreProperties);
      }
    }

    return result;
  }

  /**
   * Extrai propriedades de um objeto JSON com configuração padrão. Não inclui
   * objetos complexos e não limita a profundidade.
   *
   * @param jsonString String contendo o objeto JSON
   * @return Mapa contendo as propriedades do JSON
   */
  public static Map<String, Object> getProperties(final String jsonString) {
    return getProperties(jsonString, -1, false);
  }

  /**
   * Verifica se uma propriedade deve ser ignorada com base na lista de
   * propriedades a ignorar. A verificação é feita tanto pelo nome exato quanto
   * por sufixo com notação de ponto.
   *
   * @param propertyKey      Chave da propriedade a verificar
   * @param ignoreProperties Array de propriedades a ignorar
   * @return true se a propriedade deve ser ignorada, false caso contrário
   */
  public static boolean shouldIgnoreProperty(String propertyKey,
                                             String... ignoreProperties) {
    if (ignoreProperties == null || ignoreProperties.length == 0) {
      return false;
    }

    return Arrays.stream(ignoreProperties).filter(Objects::nonNull)
        .anyMatch(ignored -> propertyKey.equals(ignored)
            || propertyKey.endsWith("." + ignored));
  }

  /**
   * Processa recursivamente um valor do JSON, extraindo propriedades aninhadas
   * e aplicando as regras de profundidade máxima e inclusão de objetos
   * complexos.
   *
   * @param result                Mapa de resultado onde as propriedades serão
   *                              acumuladas
   * @param key                   Chave atual sendo processada
   * @param value                 Valor associado à chave
   * @param maxLevel              Nível máximo de recursão permitido
   * @param includeComplexObjects Se true, objetos complexos são convertidos
   *                              para string
   * @param currentLevel          Nível atual de recursão
   * @param ignoreProperties      Propriedades a serem ignoradas
   */
  @SuppressWarnings("unchecked")
  private static void processValue(Map<String, Object> result, String key,
                                   Object value, int maxLevel,
                                   boolean includeComplexObjects,
                                   int currentLevel,
                                   String... ignoreProperties) {

    if (value == null || shouldIgnoreProperty(key, ignoreProperties)) {
      return;
    }

    boolean maxLevelReached = maxLevel >= 0 && currentLevel >= maxLevel;
    Class<?> valueClass = value.getClass();

    try {
      if (value instanceof Map) {
        processMapValue(result, key, (Map<String, Object>) value, maxLevel,
                        includeComplexObjects, currentLevel,
                        maxLevelReached, ignoreProperties);
      } else if (value.getClass().isArray()) {
        processArrayValue(result, key, (Object[]) value, maxLevel,
                          includeComplexObjects, currentLevel,
                          maxLevelReached, ignoreProperties);
      } else if (value instanceof List) {
        processListValue(result, key, (List<Object>) value, maxLevel,
                         includeComplexObjects, currentLevel,
                         maxLevelReached, ignoreProperties);
      } else {
        // Valor primitivo ou simples - adiciona diretamente ao resultado
        result.put(key, value);
      }
    } catch (Exception e) {
      // Em caso de erro no processamento, adiciona o valor como string
      result.put(key, safeToString(value));
    }
  }

  /**
   * Processa um valor do tipo Map (objeto JSON).
   */
  private static void processMapValue(Map<String, Object> result,
                                      String key,
                                      Map<String, Object> mapValue,
                                      int maxLevel,
                                      boolean includeComplexObjects,
                                      int currentLevel,
                                      boolean maxLevelReached,
                                      String... ignoreProperties) {

    if (maxLevelReached || includeComplexObjects) {
      result.put(key,
                 convertComplexObjectToString(mapValue, ignoreProperties));
    } else {
      // Processa recursivamente cada entrada do mapa
      for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
        String nestedKey = key + "." + entry.getKey();
        processValue(result, nestedKey, entry.getValue(), maxLevel,
                     includeComplexObjects, currentLevel + 1,
                     ignoreProperties);
      }
    }
  }

  /**
   * Processa um valor do tipo Array.
   */
  private static void processArrayValue(Map<String, Object> result,
                                        String key, Object[] arrayValue,
                                        int maxLevel,
                                        boolean includeComplexObjects,
                                        int currentLevel,
                                        boolean maxLevelReached,
                                        String... ignoreProperties) {

    if (maxLevelReached || includeComplexObjects) {
      result
          .put(key,
               convertComplexObjectToString(arrayValue, ignoreProperties));
    } else {
      // Processa recursivamente cada elemento do array
      for (int i = 0; i < arrayValue.length; i++) {
        String arrayKey = key + "[" + i + "]";
        processValue(result, arrayKey, arrayValue[i], maxLevel,
                     includeComplexObjects, currentLevel + 1,
                     ignoreProperties);
      }
    }
  }

  /**
   * Processa um valor do tipo List.
   */
  private static void processListValue(Map<String, Object> result,
                                       String key, List<Object> listValue,
                                       int maxLevel,
                                       boolean includeComplexObjects,
                                       int currentLevel,
                                       boolean maxLevelReached,
                                       String... ignoreProperties) {

    if (maxLevelReached || includeComplexObjects) {
      result.put(key,
                 convertComplexObjectToString(listValue, ignoreProperties));
    } else {
      // Processa recursivamente cada elemento da lista
      for (int i = 0; i < listValue.size(); i++) {
        String listKey = key + "[" + i + "]";
        processValue(result, listKey, listValue.get(i), maxLevel,
                     includeComplexObjects, currentLevel + 1,
                     ignoreProperties);
      }
    }
  }

  /**
   * Converte um objeto complexo (Map, Array ou List) para uma string formatada,
   * ignorando as propriedades especificadas.
   *
   * @param object           Objeto complexo a ser convertido
   * @param ignoreProperties Propriedades a serem ignoradas na conversão
   * @return String formatada representando o objeto
   */
  private static String convertComplexObjectToString(Object object,
                                                     String... ignoreProperties) {
    if (object == null) {
      return null;
    }

    try {
      if (object instanceof Map) {
        return convertMapToString((Map<String, Object>) object,
                                  ignoreProperties);
      } else if (object.getClass().isArray()) {
        return convertArrayToString((Object[]) object);
      } else if (object instanceof List) {
        return convertListToString((List<Object>) object);
      } else {
        return safeToString(object);
      }
    } catch (Exception e) {
      return "Erro na conversão: " + e.getMessage();
    }
  }

  /**
   * Converte um Map para string formatada.
   */
  private static String convertMapToString(Map<String, Object> map,
                                           String... ignoreProperties) {
    return map.entrySet().stream()
        .filter(entry -> !shouldIgnoreProperty(entry.getKey(),
                                               ignoreProperties))
        .map(Map.Entry::getValue).map(JsonUtil::safeToString)
        .filter(Objects::nonNull).collect(Collectors.joining(", "));
  }

  /**
   * Converte um array para string formatada.
   */
  private static String convertArrayToString(Object[] array) {
    return Arrays.stream(array).map(JsonUtil::safeToString)
        .filter(Objects::nonNull).collect(Collectors.joining(", "));
  }

  /**
   * Converte uma lista para string formatada.
   */
  private static String convertListToString(List<Object> list) {
    return list.stream().map(JsonUtil::safeToString)
        .filter(Objects::nonNull).collect(Collectors.joining(", "));
  }

  /**
   * Converte um objeto para string de forma segura, tratando nulls.
   *
   * @param object Objeto a ser convertido
   * @return Representação em string do objeto ou null se o objeto for null
   */
  private static String safeToString(Object object) {
    return object != null ? String.valueOf(object) : null;
  }

  /**
   * Serializa um objeto para sua representação JSON.
   *
   * @param object Objeto a ser serializado
   * @return String JSON representando o objeto
   * @throws IllegalArgumentException se ocorrer erro durante a serialização
   */
  public static String toJson(Object object) {
    try {
      return JSON.toJson(object);
    } catch (Exception e) {
      throw new IllegalArgumentException("Erro ao serializar objeto para JSON",
                                         e);
    }
  }

  /****************************************************************************
   * Adaptadores para tipos temporais do Java Time API
   ***************************************************************************/

  /**
   * Adaptador para serialização e desserialização de LocalDateTime. Utiliza o
   * formato ISO com timezone: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
   */
  public static class LocalDateTimeTypeAdapter
    implements JsonDeserializer<LocalDateTime>,
    JsonSerializer<LocalDateTime> {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(DATE_TIME_FORMAT);

    @Override
    public LocalDateTime deserialize(JsonElement json, Type type,
                                     JsonDeserializationContext context)
      throws JsonParseException {
      try {
        return ZonedDateTime.parse(json.getAsString(), formatter)
            .toLocalDateTime();
      } catch (Exception e) {
        throw new JsonParseException("Erro ao desserializar LocalDateTime: "
            + json.getAsString(), e);
      }
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      return new JsonPrimitive(formatter.format(src));
    }
  }

  /**
   * Adaptador para serialização e desserialização de LocalDate. Utiliza o
   * formato ISO: yyyy-MM-dd
   */
  public static class LocalDateTypeAdapter
    implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(DATE_FORMAT);

    @Override
    public LocalDate deserialize(JsonElement json, Type type,
                                 JsonDeserializationContext context)
      throws JsonParseException {
      try {
        return LocalDate.parse(json.getAsString(), formatter);
      } catch (Exception e) {
        throw new JsonParseException("Erro ao desserializar LocalDate: "
            + json.getAsString(), e);
      }
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      return new JsonPrimitive(formatter.format(src));
    }
  }

  /**
   * Adaptador para serialização e desserialização de LocalTime. Utiliza o
   * formato: HH:mm:ss.SSS'Z'
   */
  public static class LocalTimeTypeAdapter
    implements JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {

    private static final String TIME_FORMAT = "HH:mm:ss.SSS'Z'";
    private final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(TIME_FORMAT);

    @Override
    public LocalTime deserialize(JsonElement json, Type type,
                                 JsonDeserializationContext context)
      throws JsonParseException {
      try {
        return LocalTime.parse(json.getAsString(), formatter);
      } catch (Exception e) {
        throw new JsonParseException("Erro ao desserializar LocalTime: "
            + json.getAsString(), e);
      }
    }

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc,
                                 JsonSerializationContext context) {
      return new JsonPrimitive(formatter.format(src));
    }
  }
}
