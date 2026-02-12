package com.ia.core.llm.service.transform;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.springframework.stereotype.Service;

/**
 * Serviço especializado para processamento de imagens.
 * Responsabilidade única: operações de binarização, compressão e redimensionamento.
 * 
 * @author Israel Araújo
 */
@Service
public class ImageProcessingService {

  /**
   * Aplica limiar de Otsu para binarização de imagem.
   *
   * @param input stream de entrada da imagem
   * @return imagem binarizada em bytes
   * @throws IOException se houver erro na leitura/escrita
   */
  public byte[] binarizarComOtsu(InputStream input) throws IOException {
    BufferedImage image = ImageIO.read(input);
    int limiar = calcularLimiarOtsu(image);
    return compressJpeg(binarizarImagem(image, limiar), 1.0f);
  }

  /**
   * Binariza uma imagem usando o limiar especificado.
   * Pixels com valor acima do limiar tornam-se brancos, abaixo tornam-se pretos.
   *
   * @param imagemOriginal imagem original
   * @param limiar limiar para binarização
   * @return imagem binarizada
   */
  public BufferedImage binarizarImagem(BufferedImage imagemOriginal, int limiar) {
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();

    BufferedImage imagemBinarizada = new BufferedImage(largura, altura,
                                                       BufferedImage.TYPE_BYTE_BINARY);

    for (int y = 0; y < altura; y++) {
      for (int x = 0; x < largura; x++) {
        int rgb = imagemOriginal.getRGB(x, y);
        int nivelCinza = calcularNivelCinza(rgb);
        int novoPixel = (nivelCinza > limiar) ? 0xFFFFFF : 0x000000;
        imagemBinarizada.setRGB(x, y, novoPixel);
      }
    }

    return imagemBinarizada;
  }

  /**
   * Calcula o limiar ótimo usando método de Otsu.
   * O método de Otsu minimiza a variância intra-classe entre pixels de fundo e primeiro plano.
   *
   * @param imagem imagem a ser analisada
   * @return limiar ótimo calculado
   */
  public int calcularLimiarOtsu(BufferedImage imagem) {
    int[] histograma = calcularHistograma(imagem);
    int totalPixels = imagem.getWidth() * imagem.getHeight();

    double somaTotal = calcularSomaTotal(histograma);
    double somaFundo = 0;
    int pesoFundo = 0;
    int pesoFrente = 0;

    double varianciaMaxima = 0;
    int limiar = 0;

    for (int i = 0; i < 256; i++) {
      pesoFundo += histograma[i];
      if (pesoFundo == 0) {
        continue;
      }

      pesoFrente = totalPixels - pesoFundo;
      if (pesoFrente == 0) {
        break;
      }

      somaFundo += i * histograma[i];

      double mediaFundo = somaFundo / pesoFundo;
      double mediaFrente = (somaTotal - somaFundo) / pesoFrente;

      double variancia = (double) pesoFundo * pesoFrente
                          * Math.pow(mediaFundo - mediaFrente, 2);

      if (variancia > varianciaMaxima) {
        varianciaMaxima = variancia;
        limiar = i;
      }
    }

    return limiar;
  }

  /**
   * Comprime e redimensiona uma imagem mantendo aspect ratio.
   *
   * @param inputFile stream de entrada da imagem
   * @param quality qualidade da compressão (0.0 a 1.0)
   * @param maxWidth largura máxima desejada
   * @param maxHeight altura máxima desejada
   * @return imagem processada em bytes
   */
  public byte[] compressAndResize(InputStream inputFile, float quality,
                                   int maxWidth, int maxHeight) throws IOException {
    BufferedImage originalImage = ImageIO.read(inputFile);

    int[] novasDimensoes = calcularNovasDimensoes(originalImage, maxWidth, maxHeight);
    int newWidth = novasDimensoes[0];
    int newHeight = novasDimensoes[1];

    BufferedImage resizedImage = redimensionarImagem(originalImage, newWidth, newHeight);

    return compressJpeg(resizedImage, quality);
  }

  /**
   * Comprime imagem no formato JPEG.
   *
   * @param image imagem a ser comprimida
   * @param quality qualidade da compressão (0.0 a 1.0)
   * @return imagem comprimida em bytes
   * @throws IOException se houver erro na compressão
   */
  public byte[] compressJpeg(BufferedImage image, float quality) throws IOException {
    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
    ImageWriter writer = writers.next();

    ImageWriteParam params = writer.getDefaultWriteParam();
    params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    params.setCompressionQuality(quality);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
         MemoryCacheImageOutputStream outputStream = new MemoryCacheImageOutputStream(out)) {
      writer.setOutput(outputStream);
      writer.write(null, new IIOImage(image, null, null), params);
      return out.toByteArray();
    } finally {
      writer.dispose();
    }
  }

  /**
   * Calcula o histograma de níveis de cinza da imagem.
   */
  private int[] calcularHistograma(BufferedImage imagem) {
    int[] histograma = new int[256];

    for (int y = 0; y < imagem.getHeight(); y++) {
      for (int x = 0; x < imagem.getWidth(); x++) {
        int rgb = imagem.getRGB(x, y);
        int nivelCinza = calcularNivelCinza(rgb);
        histograma[nivelCinza]++;
      }
    }

    return histograma;
  }

  /**
   * Calcula a soma total para o método de Otsu.
   */
  private double calcularSomaTotal(int[] histograma) {
    double soma = 0;
    for (int i = 0; i < 256; i++) {
      soma += i * histograma[i];
    }
    return soma;
  }

  /**
   * Calcula o nível de cinza a partir de um pixel RGB.
   */
  private int calcularNivelCinza(int rgb) {
    int r = (rgb >> 16) & 0xFF;
    int g = (rgb >> 8) & 0xFF;
    int b = rgb & 0xFF;
    return (r + g + b) / 3;
  }

  /**
   * Calcula novas dimensões mantendo aspect ratio.
   */
  private int[] calcularNovasDimensoes(BufferedImage imagem, int maxWidth, int maxHeight) {
    int originalWidth = imagem.getWidth();
    int originalHeight = imagem.getHeight();

    int newWidth = originalWidth;
    int newHeight = originalHeight;

    if (originalWidth > maxWidth) {
      newWidth = maxWidth;
      newHeight = (originalHeight * maxWidth) / originalWidth;
    }

    if (newHeight > maxHeight) {
      newHeight = maxHeight;
      newWidth = (originalWidth * maxHeight) / originalHeight;
    }

    return new int[] { newWidth, newHeight };
  }

  /**
   * Redimensiona uma imagem para as dimensões especificadas.
   */
  private BufferedImage redimensionarImagem(BufferedImage imagemOriginal, int newWidth,
                                            int newHeight) {
    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = resizedImage.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                       RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(imagemOriginal, 0, 0, newWidth, newHeight, null);
    g.dispose();

    return resizedImage;
  }
}
