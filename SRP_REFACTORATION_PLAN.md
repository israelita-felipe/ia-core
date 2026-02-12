# FASE 3: SRP em Services - Plano de Refatoração

## Objetivo
Separar as responsabilidades do `LLMTransformationService` em serviços especializados seguindo o **Single Responsibility Principle (SRP)**.

## Estado Atual

### LLMTransformationService (多重 responsabilidade)
```java
@Service
public class LLMTransformationService {
  // Processamento de Imagem
  public static byte[] binarizarComOtsu(InputStream input);
  public static BufferedImage binarizarImagem(BufferedImage imagemOriginal, int limiar);
  public static int calcularLimiarOtsu(BufferedImage imagem);
  public static byte[] compressAndResize(InputStream inputFile, float quality, int maxWidth, int maxHeight);
  public static byte[] compressJpeg(BufferedImage image, float quality);
  
  // Extração de Texto via LLM
  public String extractText(byte[]... images) throws IOException;
  public String extractTextPrompt();
}
```

## Serviços Propostos

### 1. ImageProcessingService (Nova responsabilidade)
**Responsabilidade**: Processamento de imagens (binarização, compressão, redimensionamento)

```java
@Service
public class ImageProcessingService {
  
  /**
   * Aplica limiar de Otsu para binarização de imagem
   */
  public byte[] binarizarComOtsu(InputStream input) throws IOException;
  
  /**
   * Binariza uma imagem usando o limiar calculado
   */
  public BufferedImage binarizarImagem(BufferedImage imagemOriginal, int limiar);
  
  /**
   * Calcula o limiar ótimo usando método de Otsu
   */
  public int calcularLimiarOtsu(BufferedImage imagem);
  
  /**
   * Comprime e redimensiona uma imagem mantendo aspect ratio
   */
  public byte[] compressAndResize(InputStream inputFile, float quality, int maxWidth, int maxHeight);
  
  /**
   * Comprime imagem JPEG
   */
  public byte[] compressJpeg(BufferedImage image, float quality) throws IOException;
}
```

### 2. TextExtractionService (Nova responsabilidade)
**Responsabilidade**: Extração de texto de imagens via LLM

```java
@Service
public class TextExtractionService {
  
  private final ChatModel chatModel;
  private final LLMCommunicator llmCommunicator;
  
  public TextExtractionService(ChatModel chatModel, LLMCommunicator llmCommunicator) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
  }
  
  /**
   * Extrai texto de imagens usando LLM
   */
  public String extractText(byte[]... images) throws IOException {
    // Pré-processamento da imagem
    ImageProcessingService imageService = new ImageProcessingService();
    List<Media> media = new ArrayList<>();
    for (var image : images) {
      byte[] binarized = imageService.binarizarComOtsu(new ByteArrayInputStream(image));
      media.add(Media.builder()
          .data(Base64.getEncoder().encodeToString(binarized))
          .mimeType(MimeTypeUtils.IMAGE_JPEG)
          .build());
    }
    
    // Extração via LLM
    return llmCommunicator.sendPrompt(chatModel, extractTextPrompt(), media);
  }
  
  private String extractTextPrompt() {
    return """
        Extraia TODO o texto desta imagem de forma precisa e completa.
        REGRAS:
        1. Transcreva APENAS texto visível
        2. Mantenha formatação original (quebras de linha, parágrafos)
        3. Preserve pontuação e caracteres especiais
        4. Não adicione interpretações ou comentários
        5. Forneça APENAS o texto extraído
        """;
  }
}
```

### 3. LLMTransformationService (Adaptador/Refatorado)
**Responsabilidade**: Delegar para os serviços especializados (compatibilidade retroativa)

```java
/**
 * @deprecated Use ImageProcessingService e TextExtractionService diretamente
 */
@Deprecated
@Service
public class LLMTransformationService {
  
  private final ImageProcessingService imageProcessingService;
  private final TextExtractionService textExtractionService;
  
  public LLMTransformationService(ImageProcessingService imageProcessingService,
                                 TextExtractionService textExtractionService) {
    this.imageProcessingService = imageProcessingService;
    this.textExtractionService = textExtractionService;
  }
  
  // Métodos delegados
  public byte[] binarizarComOtsu(InputStream input) throws IOException {
    return imageProcessingService.binarizarComOtsu(input);
  }
  
  public BufferedImage binarizarImagem(BufferedImage imagemOriginal, int limiar) {
    return imageProcessingService.binarizarImagem(imagemOriginal, limiar);
  }
  
  public int calcularLimiarOtsu(BufferedImage imagem) {
    return imageProcessingService.calcularLimiarOtsu(imagem);
  }
  
  public byte[] compressAndResize(InputStream inputFile, float quality, int maxWidth, int maxHeight) {
    return imageProcessingService.compressAndResize(inputFile, quality, maxWidth, maxHeight);
  }
  
  public byte[] compressJpeg(BufferedImage image, float quality) throws IOException {
    return imageProcessingService.compressJpeg(image, quality);
  }
  
  public String extractText(byte[]... images) throws IOException {
    return textExtractionService.extractText(images);
  }
  
  public String extractTextPrompt() {
    return textExtractionService.extractTextPrompt();
  }
}
```

## Arquivos a Criar/Modificar

### Novos Arquivos
1. `ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/ImageProcessingService.java`
2. `ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/TextExtractionService.java`

### Arquivos a Modificar
1. `ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java` (adicionar deprecation e delegação)

## Dependências a Adicionar
Nenhuma nova dependência necessária - reutilizar as existentes.

## Benefícios da Refatoração

| Benefício | Descrição |
|-----------|-----------|
| **SRP** | Cada serviço tem uma única responsabilidade |
| **Testabilidade** | Serviços menores são mais fáceis de testar |
| **Reusabilidade** | ImageProcessingService pode ser usado em outros contextos |
| **Manutenibilidade** | Alterações em processamento de imagem não afetam extração de texto |
| **Coesão** | Classes coesas com responsabilidades bem definidas |

## Ordem de Execução

1. Criar `ImageProcessingService`
2. Criar `TextExtractionService`
3. Refatorar `LLMTransformationService` para usar delegação (compatibilidade retroativa)
4. Atualizar dependências nos arquivos que usam `LLMTransformationService`
5. Adicionar `@Deprecated` em `LLMTransformationService`

## Exemplo de Uso Após Refatoração

```java
// Antes
@Autowired
private LLMTransformationService llmTransformationService;

// Depois (recomendado)
@Autowired
private ImageProcessingService imageProcessingService;

@Autowired
private TextExtractionService textExtractionService;
```

## Status
🔄 Planejado - Aguardando execução
