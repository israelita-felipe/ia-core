package com.ia.core.model.util;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utilitários para manipulação de enums.
 *
 * <p>Fornece métodos para serialização e desserialização de enums,
 * permitindo que enums sejam armazenados e recuperados como strings.
 *
 * <p><b>Por quê usar EnumUtils?</b></p>
 * <ul>
 *   <li>Permite persistir enums como strings no banco de dados</li>
 *   <li>Facilita a transmissão de enums em APIs</li>
 *   <li>Suporta enums de qualquer tipo</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class EnumUtils {
  /**
   * Separador usado entre o nome completo da classe e o valor do enum.
   */
  private static final String SEPARATOR = "_#_";

  /**
   * Desserializa uma string para um enum.
   *
   * <p>O formato esperado é: {@code "pacote.EnumClass#ENUM_VALUE"}
   *
   * <p><b>Exemplo de uso:</b></p>
   * {@code
   * String texto = "com.example.Status#ATIVO";
   * Status status = EnumUtils.deserialize(texto);
   * }
   *
   * @param <T>  Tipo do enum
   * @param text Texto a ser desserializado no formato "pacote.Classe#VALOR"
   * @return O enum correspondente ao texto
   * @throws ClassNotFoundException se a classe do enum não for encontrada
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
   * Serializa um enum para string.
   *
   * <p>O formato gerado é: {@code "pacote.EnumClass#ENUM_VALUE"}
   *
   * <p><b>Exemplo de uso:</b></p>
   * {@code
   * Status status = Status.ATIVO;
   * String texto = EnumUtils.serialize(status);
   * // Resultado: "com.example.Status#ATIVO"
   * }
   *
   * @param <T>   Tipo do enum
   * @param value Enum a ser serializado
   * @return String representando o enum no formato "pacote.Classe#VALOR"
   */
  public static <T extends Enum> String serialize(T value) {
    return value.getDeclaringClass().getCanonicalName() + SEPARATOR
        + value.name();
  }
}
