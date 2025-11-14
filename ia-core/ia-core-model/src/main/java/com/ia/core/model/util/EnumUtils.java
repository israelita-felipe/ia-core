package com.ia.core.model.util;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 
 */
public class EnumUtils {
  /**
   * Separador
   */
  private static final String SEPARATOR = "_#_";
  /**
   * @param text
   * @return
   * @throws ClassNotFoundException
   */
  @SuppressWarnings("unchecked")
  public static <T extends Enum> T deserialize(String text)
    throws ClassNotFoundException {
    String[] splitted = text.split(SEPARATOR);
    var enumType = Class.forName(splitted[0]);
    return (T) Stream.of(enumType.getEnumConstants())
        .filter(enumItem -> Objects.equals(splitted[1],
                                           ((Enum<?>) enumItem).name()))
        .findFirst().orElse(null);
  }

  /**
   * @param value
   * @return
   */
  public static <T extends Enum> String serialize(T value) {
    return value.getDeclaringClass().getCanonicalName() + SEPARATOR
        + value.name();
  }
}
