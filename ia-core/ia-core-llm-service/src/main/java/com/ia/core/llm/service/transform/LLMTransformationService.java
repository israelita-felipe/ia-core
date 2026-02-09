package com.ia.core.llm.service.transform;

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

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.ia.core.owl.service.LLMCommunicator;

/**
 * Servico deprecated para transformacao e extracao de texto.
 * 
 * @deprecated Use {@link ImageProcessingService} para processamento de imagens
 *             e {@link TextExtractionService} para extracao de texto.
 *             Esta classe sera removida em versoes futuras.
 * 
 * @author Israel Araujo
 */
@Deprecated
@Service
public class LLMTransformationService {

  private final ChatModel chatModel;
  private final LLMCommunicator llmCommunicator;
  private final ImageProcessingService imageProcessingService;
  private final TextExtractionService textExtractionService;

  /**
   * Construtor com injecao de dependencias.
   */
  public LLMTransformationService(ChatModel chatModel,
                                   LLMCommunicator llmCommunicator,
                                   ImageProcessingService imageProcessingService,
                                   TextExtractionService textExtractionService) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
    this.imageProcessingService = imageProcessingService;
    this.textExtractionService = textExtractionService;
  }

  /**
   * Extrai texto de imagens usando LLM.
   *
   * @deprecated Use {@link TextExtractionService#extractText(byte[]...)}
   */
  @Deprecated
  public String extractText(byte[]... images) throws IOException {
    return textExtractionService.extractText(images);
  }

  /**
   * Aplica limiar de Otsu para binarizacao de imagem.
   *
   * @deprecated Use {@link ImageProcessingService#binarizarComOtsu(InputStream)}
   */
  @Deprecated
  public byte[] binarizarComOtsu(InputStream input) throws IOException {
    return imageProcessingService.binarizarComOtsu(input);
  }

  /**
   * Binariza uma imagem usando o limiar especificado.
   *
   * @deprecated Use {@link ImageProcessingService#binarizarImagem(BufferedImage, int)}
   */
  @Deprecated
  public BufferedImage binarizarImagem(BufferedImage imagemOriginal, int limiar) {
    return imageProcessingService.binarizarImagem(imagemOriginal, limiar);
  }

  /**
   * Calcula o limiar otimo usando metodo de Otsu.
   *
   * @deprecated Use {@link ImageProcessingService#calcularLimiarOtsu(BufferedImage)}
   */
  @Deprecated
  public int calcularLimiarOtsu(BufferedImage imagem) {
    return imageProcessingService.calcularLimiarOtsu(imagem);
  }

  /**
   * Compacta e redimensiona a imagem.
   *
   * @deprecated Use {@link ImageProcessingService#compressAndResize(InputStream, float, int, int)}
   */
  @Deprecated
  public byte[] compressAndResize(InputStream inputFile, float quality,
                                         int maxWidth, int maxHeight) throws IOException {
    return imageProcessingService.compressAndResize(inputFile, quality, maxWidth, maxHeight);
  }

  /**
   * Comprime imagem no formato JPEG.
   *
   * @deprecated Use {@link ImageProcessingService#compressJpeg(BufferedImage, float)}
   */
  @Deprecated
  public byte[] compressJpeg(InputStream input, float quality) throws IOException {
    BufferedImage image = ImageIO.read(input);
    return compressJpeg(image, quality);
  }

  /**
   * Comprime imagem no formato JPEG.
   *
   * @deprecated Use {@link ImageProcessingService#compressJpeg(BufferedImage, float)}
   */
  @Deprecated
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
   * Prompt para extracao de texto de imagens.
   *
   * @deprecated Use {@link TextExtractionService}
   */
  @Deprecated
  public String extractTextPrompt() {
    return "Extraia TODO o texto desta imagem de forma precisa e completa." + "\n" + "\n" +
           "REGRAS:" + "\n" +
           "1. Transcreva APENAS texto visivel" + "\n" +
           "2. Mantenha formatacao original (quebras de linha, paragrafos)" + "\n" +
           "3. Preserve pontuacao e caracteres especiais" + "\n" +
           "4. Nao adicione interpretacoes ou comentarios" + "\n" +
           "5. Forneca APENAS o texto extrado" + "\n" + "\n" +
           "Se a imagem nao contiver texto legivel, retorne NENHUM_TEXTO_ENCONTRADO." + "\n";
  }
}
