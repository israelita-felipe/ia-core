package com.ia.core.llm.service.transform;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.ia.core.owl.service.LLMCommunicator;

/**
 *
 */
@Service
public class LLMTransformationService {
  private ChatModel chatModel;
  private LLMCommunicator llmCommunicator;

  /**
   *
   */
  public LLMTransformationService(ChatModel chatModel,
                                  LLMCommunicator llmCommunicator) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
  }

  public String extractText(byte[]... images)
    throws IOException {
    List<Media> media = new ArrayList<>();
    for (var image : images) {
      media.add(Media.builder().data(Base64.getEncoder()
          .encodeToString(binarizarComOtsu(new ByteArrayInputStream(image))))
          .mimeType(MimeTypeUtils.IMAGE_JPEG).build());
    }
    return llmCommunicator.sendPrompt(chatModel, extractTextPrompt(),
                                      media);
  }

  public static byte[] binarizarComOtsu(InputStream input)
    throws IOException {
    BufferedImage image = ImageIO.read(input);
    int limiar = calcularLimiarOtsu(image);
    return compressJpeg(binarizarImagem(image, limiar), 1);
  }

  public static BufferedImage binarizarImagem(BufferedImage imagemOriginal,
                                              int limiar) {
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();

    // Criar nova imagem em escala de cinza
    BufferedImage imagemBinarizada = new BufferedImage(largura, altura,
                                                       BufferedImage.TYPE_BYTE_BINARY);

    for (int y = 0; y < altura; y++) {
      for (int x = 0; x < largura; x++) {
        // Obter cor do pixel
        int rgb = imagemOriginal.getRGB(x, y);

        // Converter para escala de cinza
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int cinza = (r + g + b) / 3;

        // Aplicar limiar
        int novoPixel = (cinza > limiar) ? 0xFFFFFF : 0x000000;
        imagemBinarizada.setRGB(x, y, novoPixel);
      }
    }

    return imagemBinarizada;
  }

  public static int calcularLimiarOtsu(BufferedImage imagem) {
    // Calcular histograma
    int[] histograma = new int[256];
    int totalPixels = imagem.getWidth() * imagem.getHeight();

    for (int y = 0; y < imagem.getHeight(); y++) {
      for (int x = 0; x < imagem.getWidth(); x++) {
        int rgb = imagem.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int cinza = (r + g + b) / 3;
        histograma[cinza]++;
      }
    }

    // Calcular limiar ótimo usando método de Otsu
    double somaTotal = 0;
    for (int i = 0; i < 256; i++) {
      somaTotal += i * histograma[i];
    }

    double somaFundo = 0;
    int pesoFundo = 0;
    int pesoFrente = 0;

    double varianciaMaxima = 0;
    int limiar = 0;

    for (int i = 0; i < 256; i++) {
      pesoFundo += histograma[i];
      if (pesoFundo == 0)
        continue;

      pesoFrente = totalPixels - pesoFundo;
      if (pesoFrente == 0)
        break;

      somaFundo += i * histograma[i];

      double mediaFundo = somaFundo / pesoFundo;
      double mediaFrente = (somaTotal - somaFundo) / pesoFrente;

      // Calcular variância entre classes
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
   * Compacta e redimensiona a imagem
   */
  public static byte[] compressAndResize(InputStream inputFile,
                                         float quality, int maxWidth,
                                         int maxHeight) {
    try {
      BufferedImage originalImage = ImageIO.read(inputFile);

      // Calcular novas dimensões mantendo aspect ratio
      int originalWidth = originalImage.getWidth();
      int originalHeight = originalImage.getHeight();
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

      // Redimensionar
      BufferedImage resizedImage = new BufferedImage(newWidth, newHeight,
                                                     BufferedImage.TYPE_INT_RGB);
      Graphics2D g = resizedImage.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                         RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
      g.dispose();

      // Compactar
      return compressJpeg(resizedImage, quality);

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static byte[] compressJpeg(InputStream input, float quality)
    throws IOException {
    BufferedImage image = ImageIO.read(input);
    return compressJpeg(image, quality);
  }

  /**
   * @param image
   * @param quality
   * @return
   * @throws IOException
   */
  public static byte[] compressJpeg(BufferedImage image, float quality)
    throws IOException {
    // Obter o writer para JPEG
    Iterator<ImageWriter> writers = ImageIO
        .getImageWritersByFormatName("jpeg");
    ImageWriter writer = writers.next();

    // Configurar parâmetros de compressão
    ImageWriteParam params = writer.getDefaultWriteParam();
    params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    params.setCompressionQuality(quality); // 0.0 a 1.0

    // Escrever imagem compactada
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        MemoryCacheImageOutputStream outputStream = new MemoryCacheImageOutputStream(out)) {
      writer.setOutput(outputStream);
      writer.write(null, new IIOImage(image, null, null), params);
      return out.toByteArray();
    } finally {
      writer.dispose();
    }
  }

  public String extractTextPrompt() {
    return """
            Extraia TODO o texto desta imagem de forma precisa e completa.

            REGRAS:
            1. Transcreva APENAS texto visível
            2. Mantenha formatação original (quebras de linha, parágrafos)
            3. Preserve pontuação e caracteres especiais
            4. Não adicione interpretações ou comentários
            6. Forneça APENAS o texto extraído

        """;
  }
}
