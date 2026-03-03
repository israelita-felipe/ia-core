package com.ia.core.view.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitária para tratamento de cor.
 */
public class ColorUtils {

  private static final int TAMANHO_MARCADOR = 12;
  private static final Map<String, BufferedImage> CACHE_MARCADOR = new HashMap<>();

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

  /**
   * Converte cor hex para Color do java.awt.
   *
   * @param hex código hexadecimal da cor (ex: #FF5733)
   * @return objeto Color
   */
  public static Color hexToColor(String hex) {
    try {
      String cleanHex = hex.replace("#", "");
      if (cleanHex.length() == 6) {
        int r = Integer.parseInt(cleanHex.substring(0, 2), 16);
        int g = Integer.parseInt(cleanHex.substring(2, 4), 16);
        int b = Integer.parseInt(cleanHex.substring(4, 6), 16);
        return new Color(r, g, b);
      }
    } catch (Exception e) {
      // Ignorar e retornar cor padrão
    }
    return Color.BLUE;
  }

  /**
   * Retorna uma imagem de marcador circular com a cor especificada.
   * Usado para exibir a cor do calendário no relatório Jasper.
   *
   * @param corHex código hexadecimal da cor (ex: #FF5733)
   * @return imagem do marcador
   */
  public static BufferedImage getMarcador(String corHex) {
    if (corHex == null || corHex.isEmpty()) {
      corHex = "#007bff";
    }

    // Verificar cache
    if (CACHE_MARCADOR.containsKey(corHex)) {
      return CACHE_MARCADOR.get(corHex);
    }

    // Criar imagem
    BufferedImage imagem = new BufferedImage(TAMANHO_MARCADOR, TAMANHO_MARCADOR,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = imagem.createGraphics();

    // Configurar rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    // Converter cor hex para Color
    Color cor = hexToColor(corHex);

    // Desenhar círculo preenchido
    g2d.setColor(cor);
    g2d.fillOval(0, 0, TAMANHO_MARCADOR - 1, TAMANHO_MARCADOR - 1);

    // Desenhar borda escura
    g2d.setColor(cor.darker());
    g2d.drawOval(0, 0, TAMANHO_MARCADOR - 2, TAMANHO_MARCADOR - 2);

    g2d.dispose();

    // Armazenar em cache
    CACHE_MARCADOR.put(corHex, imagem);

    return imagem;
  }

  /**
   * Limpa o cache de marcadores.
   */
  public static void clearCache() {
    CACHE_MARCADOR.clear();
  }
}
