# ia-core-llm-model

## 📋 Descrição

Módulo que define as entidades e DTOs específicos do sistema de integração com Large Language Models (LLMs). Contém modelos de conversa, prompts, mensagens e configurações.

## 🏗️ Estrutura

```
ia-core-llm-model/
├── src/main/java/
│   └── com/ia/core/llm/model/
│       ├── ChatMessage.java       # Entidade de mensagem
│       ├── Conversation.java      # Entidade de conversa
│       ├── Prompt.java            # Entidade de prompt
│       ├── dto/                   # DTOs
│       └── enums/                 # Enums (Role, Status)
└── pom.xml
```

## 🔑 Responsabilidades

- **ChatMessage Entity**: Representa mensagens (user, assistant)
- **Conversation Entity**: Armazena diálogos
- **Prompt Entity**: Templates de prompts reutilizáveis
- **DTOs**: ChatRequest, ChatResponse, PromptDTO
- **Enums**: Role (USER, ASSISTANT), PromptCategory
- **Value Objects**: Token counts, sentimentos

## 🛠️ Tecnologias Utilizadas

- Spring Data JPA
- Jakarta Persistence
- Lombok
- Jackson (JSON serialization)

## 📦 Dependências

- `ia-core-model` - Herda de AbstractEntity

## 🔗 Relacionamentos

Utilizado por:
- `ia-core-llm-service` - Serviços de LLM
- `ia-core-llm-service-model` - DTOs do serviço
- `ia-core-rest` - Endpoints de chat

## 💡 Padrões

- **Entity Pattern**: JPA entities com auditoria
- **Value Object**: Imutáveis (Token counts, etc.)
- **Enum Pattern**: Tipos fixos de roles/categorias

## 🚀 Ejemplo de Uso

```java
@Entity
@Table(name = "conversations")
@Data
@Builder
public class Conversation extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<ChatMessage> messages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationStatus status = ConversationStatus.ACTIVE;

    @Column(nullable = false)
    private Integer totalTokens = 0;
}

@Entity
@Table(name = "chat_messages")
@Data
@Builder
public class ChatMessage extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // USER, ASSISTANT

    @Column(columnDefinition = "LONGTEXT")
    @NotBlank
    private String content;

    @Column(nullable = false)
    private Integer tokens = 0;

    @Column
    private Double executionTime; // ms
}

public enum Role {
    USER("User"),
    ASSISTANT("Assistente");

    private String label;
}
```

## 🧪 Características

- Rastreamento de tokens para billing
- Histórico completo de conversas
- Status de conversa (ativa, arquivada, deletada)
- Timestamps de auditoria (created, updated)


