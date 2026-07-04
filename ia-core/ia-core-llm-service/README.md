# ia-core-llm-service

## 📋 Descrição

Módulo de serviço para integração com modelos de linguagem grande (Large Language Models - LLMs). Fornece abstração para comunicação com diferentes provedores de IA (OpenAI, Google Gemini, Ollama, etc.) e gerenciamento de prompts.

## 🏗️ Estrutura

```
ia-core-llm-service/
├── src/main/java/
│   └── com/ia/core/llm/service/
│       ├── ChatService.java        # Comunicação com LLM
│       ├── PromptService.java      # Gerenciamento de prompts
│       ├── EmbeddingService.java   # Geração de embeddings
│       ├── provider/               # Implementações de provedores
│       │   ├── OpenAiProvider.java
│       │   ├── GeminiProvider.java
│       │   ├── OllamaProvider.java
│       │   └── LLMProvider.java    # Interface comum
│       ├── model/                  # Modelos de dados
│       └── util/                   # Utilitários
└── pom.xml
```

## 🔑 Responsabilidades

- **ChatService**: Comunicação bidirecional com LLMs
- **PromptService**: Armazenamento e reutilização de prompts
- **EmbeddingService**: Geração de representações vetoriais de texto
- **Provider Interface**: Abstração para diferentes LLM providers
- **Token Management**: Contagem e gerenciamento de tokens
- **Context Management**: Manutenção de histórico de conversa
- **Rate Limiting**: Proteção contra uso excessivo

## 🛠️ Tecnologias Utilizadas

- Spring AI
- OpenAI API (ChatGPT)
- Google Gemini API
- Ollama (local LLMs)
- Spring Boot
- Spring Data JPA

## 📦 Dependências

- `ia-core-llm-service-model` - DTOs do LLM
- `ia-core-service` - Serviços base
- `spring-ai-openai-spring-boot-starter`
- `spring-ai-google-gemini-spring-boot-starter`

## 🔗 Relacionamentos

Depende de:
- `ia-core-llm-service-model` - DTOs
- `ia-core-service` - Serviços base
- `ia-core-nlp` - Processamento de texto

Utilizado por:
- `ia-core-rest` - Endpoints para chat/completions
- `ia-core-llm-view` - Interface web para chat
- Aplicações que precisam IA generativa

## 💡 Padrões Implementados

- **Strategy Pattern**: Diferentes provedores de LLM
- **Adapter Pattern**: Adaptar APIs heterogêneas
- **Factory Pattern**: Criar providers apropriados
- **Template Method**: Pipeline comum de processamento

## 🚀 Como Usar

### Implementar ChatService

```java
@Service
@Transactional
public class ChatServiceImpl extends AbstractCrudService<ChatMessage, Long>
    implements ChatService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private PromptService promptService;

    @Autowired
    private TokenCounterService tokenCounterService;

    /**
     * Envia uma mensagem para o LLM e retorna a resposta
     */
    public ChatResponse chat(ChatRequest request) {
        // 1. Validar request
        validateRequest(request);

        // 2. Contar tokens de entrada
        int inputTokens = tokenCounterService.countTokens(request.getMessage());

        // 3. Construir prompt com contexto
        String promptText = buildPrompt(request);

        // 4. Chamar LLM
        long startTime = System.currentTimeMillis();
        String response = chatClient.call(promptText);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // 5. Contar tokens de saída
        int outputTokens = tokenCounterService.countTokens(response);

        // 6. Salvar mensagem e resposta
        ChatMessage userMessage = new ChatMessage();
        userMessage.setRole(Role.USER);
        userMessage.setContent(request.getMessage());
        userMessage.setTokens(inputTokens);
        save(userMessage);

        ChatMessage botMessage = new ChatMessage();
        botMessage.setRole(Role.ASSISTANT);
        botMessage.setContent(response);
        botMessage.setTokens(outputTokens);
        save(botMessage);

        return ChatResponse.builder()
            .message(response)
            .inputTokens(inputTokens)
            .outputTokens(outputTokens)
            .totalTokens(inputTokens + outputTokens)
            .processingTime(elapsedTime)
            .build();
    }

    /**
     * Inicia uma nova conversa
     */
    public Conversation startConversation(String title) {
        Conversation conversation = new Conversation();
        conversation.setTitle(title);
        conversation.setMessages(new ArrayList<>());
        return conversationRepository.save(conversation);
    }

    /**
     * Adiciona mensagem a uma conversa
     */
    public ChatMessage addMessageToConversation(Long conversationId, String content, Role role) {
        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new EntityNotFoundException("Conversation not found"));

        ChatMessage message = new ChatMessage();
        message.setConversation(conversation);
        message.setContent(content);
        message.setRole(role);
        message.setTimestamp(LocalDateTime.now());

        return save(message);
    }

    /**
     * Obtém histórico de uma conversa
     */
    public List<ChatMessage> getConversationHistory(Long conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
    }

    /**
     * Constrói prompt com contexto e sistema
     */
    private String buildPrompt(ChatRequest request) {
        StringBuilder prompt = new StringBuilder();

        // Sistema prompt
        if (request.getSystemPrompt() != null) {
            prompt.append("Sistema: ").append(request.getSystemPrompt()).append("\n\n");
        }

        // Mensagens anteriores (contexto)
        if (request.getConversationId() != null) {
            List<ChatMessage> history = getConversationHistory(request.getConversationId());
            for (ChatMessage msg : history.stream()
                .limit(10) // Últimas 10 mensagens
                .collect(Collectors.toList())) {
                prompt.append(msg.getRole()).append(": ").append(msg.getContent()).append("\n");
            }
        }

        // Mensagem atual
        prompt.append("Usuário: ").append(request.getMessage()).append("\nAssistente: ");

        return prompt.toString();
    }

    private void validateRequest(ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("Mensagem não pode estar vazia");
        }

        if (request.getMessage().length() > 4000) {
            throw new IllegalArgumentException("Mensagem muito longa");
        }
    }
}
```

### Implementar PromptService

```java
@Service
@Transactional
public class PromptServiceImpl extends AbstractCrudService<Prompt, Long>
    implements PromptService {

    @Autowired
    private PromptRepository promptRepository;

    /**
     * Cria um novo prompt reutilizável
     */
    public Prompt createPrompt(PromptCreateRequest request) {
        Prompt prompt = new Prompt();
        prompt.setName(request.getName());
        prompt.setDescription(request.getDescription());
        prompt.setTemplate(request.getTemplate());
        prompt.setCategory(request.getCategory());
        prompt.setVersion(1);
        prompt.setActive(true);

        return save(prompt);
    }

    /**
     * Busca um prompt por nome/categoria
     */
    public Optional<Prompt> findByNameAndCategory(String name, String category) {
        return promptRepository.findByNameAndCategory(name, category);
    }

    /**
     * Lista prompts por categoria
     */
    public List<Prompt> listByCategory(String category) {
        return promptRepository.findByCategory(category);
    }

    /**
     * Renderiza um prompt com variáveis
     */
    public String renderPrompt(Long promptId, Map<String, String> variables) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new EntityNotFoundException("Prompt not found"));

        String template = prompt.getTemplate();

        // Substituir variáveis {{var}} por valores
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return template;
    }

    /**
     * Versiona um prompt existente
     */
    public Prompt updatePromptVersion(Long promptId, String newTemplate) {
        Prompt prompt = findById(promptId)
            .orElseThrow();

        Prompt newVersion = new Prompt();
        newVersion.setName(prompt.getName());
        newVersion.setDescription(prompt.getDescription());
        newVersion.setTemplate(newTemplate);
        newVersion.setCategory(prompt.getCategory());
        newVersion.setVersion(prompt.getVersion() + 1);
        newVersion.setActive(true);

        promptRepository.deactivateOtherVersions(prompt.getName());

        return save(newVersion);
    }
}
```

### Usar ChatService em um Controller

```java
@RestController
@RequestMapping("/api/${api.version}/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.chat(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/conversation")
    public ResponseEntity<Conversation> startConversation(
            @RequestBody ConversationRequest request) {
        Conversation conversation = chatService.startConversation(request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(conversation);
    }

    @PostMapping("/conversation/{id}/message")
    public ResponseEntity<ChatMessage> addMessage(
            @PathVariable Long id,
            @RequestBody ChatMessageRequest request) {
        ChatMessage message = chatService.addMessageToConversation(
            id,
            request.getContent(),
            request.getRole()
        );
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation/{id}")
    public ResponseEntity<List<ChatMessage>> getHistory(@PathVariable Long id) {
        List<ChatMessage> history = chatService.getConversationHistory(id);
        return ResponseEntity.ok(history);
    }
}
```

### Usando diferentes Provedores

```java
@Service
public class LLMProviderFactory {

    @Value("${llm.provider}")
    private String providerType; // openai, gemini, ollama

    @Bean
    public ChatClient chatClient() {
        switch (providerType.toLowerCase()) {
            case "openai":
                return new OpenAiChatClient();
            case "gemini":
                return new GeminiChatClient();
            case "ollama":
                return new OllamaChatClient();
            default:
                throw new IllegalArgumentException("Provider não suportado: " + providerType);
        }
    }
}
```

## 📋 Configuração

### application.yml

```yaml
llm:
  provider: openai
  model: gpt-4

spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      organization-id: ${OPENAI_ORG_ID}
    google:
      gemini:
        api-key: ${GOOGLE_GEMINI_API_KEY}
```

### application-ollama.yml

```yaml
llm:
  provider: ollama
  model: llama2

spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      model: llama2
```

## 💰 Gerenciamento de Custos

```java
@Service
public class CostManagementService {

    /**
     * Calcula custo de uma requisição
     */
    public BigDecimal calculateCost(int inputTokens, int outputTokens) {
        // OpenAI pricing
        BigDecimal inputCost = BigDecimal.valueOf(inputTokens * 0.0000015); // $0.0015 por 1k tokens
        BigDecimal outputCost = BigDecimal.valueOf(outputTokens * 0.000002); // $0.002 por 1k tokens

        return inputCost.add(outputCost);
    }

    /**
     * Registra uso para billing
     */
    public void trackUsage(String userId, int inputTokens, int outputTokens,
                          BigDecimal cost) {
        UsageLog log = new UsageLog();
        log.setUserId(userId);
        log.setInputTokens(inputTokens);
        log.setOutputTokens(outputTokens);
        log.setCost(cost);
        log.setTimestamp(LocalDateTime.now());

        usageRepository.save(log);
    }
}
```

## 🧪 Testes

```java
@SpringBootTest
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @MockBean
    private ChatClient chatClient;

    @Test
    public void testSimpleChat() {
        ChatRequest request = new ChatRequest();
        request.setMessage("Olá, como você está?");

        when(chatClient.call(anyString()))
            .thenReturn("Olá! Estou bem, obrigado!");

        ChatResponse response = chatService.chat(request);

        assertNotNull(response);
        assertEquals("Olá! Estou bem, obrigado!", response.getMessage());
    }
}
```

## 🔐 Segurança

- ✅ Não exponha chaves de API em logs
- ✅ Use variáveis de ambiente para credenciais
- ✅ Implemente rate limiting por usuário
- ✅ Valide entrada do usuário
- ✅ Sanitize prompts para evitar prompt injection

## 🤝 Contribuição

Ao adicionar novos provedores:
1. Implemente a interface `LLMProvider`
2. Implemente tratamento de erros
3. Adicione configuração via properties
4. Documente com exemplos
5. Adicione testes

## 📝 Notas

- Diferentes modelos têm diferentes preços
- Rate limiting é importante em produção
- Historicamente de conversasdevem ser paginados
- Cache de embeddings melhora performance

## 🔍 Referências

- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [OpenAI API Reference](https://platform.openai.com/docs/api-reference)
- [Google Gemini API](https://ai.google.dev/)
- [Ollama Documentation](https://ollama.ai/)


