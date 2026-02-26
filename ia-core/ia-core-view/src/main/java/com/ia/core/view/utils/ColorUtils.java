package com.ia.core.view.utils;

/**
 * Classe utilitária para tratamento de cor.
 */
public class ColorUtils {

  /**
   * Converte uma cor hexadecimal para o formato rgba(r, g, b, a).
   *
   * @param hex   Cor no formato #RRGGBB ou RRGGBB
   * @param alpha Valor alpha de 0 a 255 (0 = transparente, 255 = opaco)
   * @return String no formato "rgba(r, g, b, a)" com alpha entre 0 e 1
   */
  public static String hexToRgba(String hex, int alpha) {
    // Valida e normaliza o alpha
    int alphaVal = Math.max(0, Math.min(255, alpha));
    double alphaDouble = alphaVal / 255.0;
    return hexToRgba(hex, alphaDouble);
  }

  /**
   * Converte uma cor hexadecimal para o formato rgba(r, g, b, a).
   *
   * @param hex   Cor no formato #RRGGBB ou RRGGBB
   * @param alpha Valor alpha de 0.0 a 1.0 (0.0 = transparente, 1.0 = opaco)
   * @return String no formato "rgba(r, g, b, a)"
   */
  public static String hexToRgba(String hex, double alpha) {
    // Remove o caractere '#' se presente
    String hexSemPrefixo = hex.replace("#", "");

    // Converte para inteiro
    int cor = Integer.parseInt(hexSemPrefixo, 16);

    // Extrai os componentes
    int r = (cor >> 16) & 0xFF;
    int g = (cor >> 8) & 0xFF;
    int b = cor & 0xFF;

    // Garante que alpha esteja entre 0.0 e 1.0
    double a = Math.max(0.0, Math.min(1.0, alpha));

    // Formata a string RGBA
    return String.format("rgba(%d, %d, %d, %s)", r, g, b, a);
  }

}
