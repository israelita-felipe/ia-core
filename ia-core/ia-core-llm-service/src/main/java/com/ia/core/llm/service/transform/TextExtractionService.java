package com.ia.core.llm.service.transform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.ia.core.owl.service.LLMCommunicator;

/**
 * Serviço especializado para extração de texto de imagens usando LLM.
 * Responsabilidade única: extração e processamento de texto via modelo de linguagem.
 * 
 * @author Israel Araújo
 */
@Service
public class TextExtractionService {

  private final ChatModel chatModel;
  private final LLMCommunicator llmCommunicator;
  private final ImageProcessingService imageProcessingService;

  /**
   * Construtor com injeção de dependências.
   *
   * @param chatModel modelo de chat a ser utilizado
   * @param llmCommunicator comunicador LLM
   * @param imageProcessingService serviço de processamento de imagem
   */
  public TextExtractionService(ChatModel chatModel,
                             LLMCommunicator llmCommunicator,
                             ImageProcessingService imageProcessingService) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
    this.imageProcessingService = imageProcessingService;
  }

  /**
   * Extrai texto de imagens usando LLM.
   * O processo inclui pré-processamento da imagem e chamada ao modelo de linguagem.
   *
   * @param images imagens em bytes para extração de texto
   * @return texto extraído das imagens
   * @throws IOException se houver erro no processamento
   */
  public String extractText(byte[]... images) throws IOException {
    List<Media> media = prepareImages(images);
    return llmCommunicator.sendPrompt(chatModel, extractTextPrompt(), media);
  }

  /**
   * Prepara imagens para extração.
   * Aplica binarização para melhorar a qualidade da extração.
   */
  private List<Media> prepareImages(byte[]... images) throws IOException {
    List<Media> media = new ArrayList<>();

    for (byte[] image : images) {
      byte[] binarizedImage = imageProcessingService.binarizarComOtsu(
          new ByteArrayInputStream(image));

      Media mediaItem = Media.builder()
          .data(Base64.getEncoder().encodeToString(binarizedImage))
          .mimeType(MimeTypeUtils.IMAGE_JPEG)
          .build();

      media.add(mediaItem);
    }

    return media;
  }

  /**
   * Prompt para extração de texto de imagens.
   *
   * @return prompt formatado para extração
   */
  private String extractTextPrompt() {
    return """
        Extraia TODO o texto desta imagem de forma precisa e completa.

        REGRAS:
        1. Transcreva APENAS texto visível
        2. Mantenha formatação original (quebras de linha, parágrafos)
        3. Preserve pontuação e caracteres especiais
        4. Não adicione interpretações ou comentários
        5. Forneça APENAS o texto extraído

        Se a imagem não contiver texto legível, retorne "NENHUM_TEXTO_ENCONTRADO".

        """;
  }
}
