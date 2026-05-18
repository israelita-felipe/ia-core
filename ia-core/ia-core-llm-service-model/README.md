# ia-core-llm-service-model

## 📋 Descrição

Módulo contendo DTOs e modelos de transferência de dados para o serviço de LLM. Define estruturas para requisições de chat, respostas, e configurações de prompts.

## 🏗️ Estrutura

```
ia-core-llm-service-model/
├── src/main/java/
│   └── com/ia/core/llm/service/dto/
│       ├── request/               # ChatRequest, PromptRequest
│       ├── response/              # ChatResponse, PromptResponse
│       └── util/                  # Conversores, validadores
└── pom.xml
```

## 🔑 Responsabilidades

- **ChatRequest DTO**: Requisição de envio de mensagem
- **ChatResponse DTO**: Resposta do LLM
- **PromptRequest**: Criação/atualização de prompts
- **ConversationDTO**: Dados de conversa
- **TokenCountDTO**: Rastreamento de tokens

## 🛠️ Tecnologias

- Jakarta Validation
- Jackson
- Lombok

## 💡 Estrutura de DTOs

```java
@Data
@Builder
public class ChatRequest {

    @NotBlank
    private String message;

    private Long conversationId;

    private String systemPrompt;

    @Min(0)
    @Max(2)
    private Double temperature = 0.7;

    @Min(1)
    private Integer maxTokens = 2000;
}

@Data
@Builder
public class ChatResponse {

    private String message;

    private Integer inputTokens;

    private Integer outputTokens;

    private Integer totalTokens;

    private Long processingTime;
}
```


