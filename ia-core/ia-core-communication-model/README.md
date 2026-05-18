# ia-core-communication-model

## 📋 Descrição

Módulo que define entidades de comunicação (emails, SMS, notificações, webhooks). Modelos para armazenar configuração e histórico de comunicações.

## 🏗️ Estrutura

```
ia-core-communication-model/
├── src/main/java/
│   └── com/ia/core/communication/model/
│       ├── Email.java             # Entidade de email
│       ├── Notification.java      # Notificação
│       ├── Channel.java           # Canal de comunicação
│       ├── dto/                   # DTOs
│       └── enums/                 # CommunicationType, Status
└── pom.xml
```

## 🔑 Responsabilidades

- **Email Entity**: Armazena emails (templates, histórico)
- **Notification Entity**: Sistema de notificações
- **Communication Channel**: Configuração (SMTP, SMS gateway, etc.)
- **DTOs**: SendEmailRequest, NotificationDTO
- **Enums**: CommunicationType, DeliveryStatus

## 🛠️ Tecnologias

- Spring Data JPA
- Jakarta Persistence
- Jackson (JSON)

## 💡 Características

- Templates de email customizáveis
- Histórico de entregas
- Múltiplos canais (email, SMS, push)
- Retry automático para falhas


