# ia-core-llm-view

## 📋 Descrição

Interface web com Vaadin para o subsistema de LLM. Fornece chat interativo, histórico de conversas e gerenciamento de prompts.

## 🏗️ Estrutura

```
ia-core-llm-view/
├── src/main/java/
│   └── com/ia/core/llm/view/
│       ├── chat/                  # Componentes de chat
│       ├── conversation/          # Histórico de conversas
│       ├── prompt/                # Gerenciamento de prompts
│       └── component/             # Componentes reutilizáveis
└── pom.xml
```

## 🔑 Responsabilidades

- **ChatView**: Interface de chat interativa
- **ConversationListView**: Lista de conversas
- **PromptManagementView**: CRUD de prompts
- **MessageDisplay**: Renderização markdown/HTML de mensagens
- **TokenCountDisplay**: Exibição de uso de tokens

## 🛠️ Tecnologias

- Vaadin
- Spring Boot Web
- Jackson (JSON parsing)

## 💡 Componentes

- `ChatForm`: Input de mensagem com envio
- `MessageList`: Histórico de mensagens
- `ConversationSelector`: Seleção de conversa
- `PromptTemplate`: Editor de templates


