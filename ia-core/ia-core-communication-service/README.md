# ia-core-communication-service

## 📋 Descrição

Módulo de serviço para comunicações (emails, SMS, notificações push). Gerencia envio, retry e rastreamento de mensagens.

## 🏗️ Estrutura

```
ia-core-communication-service/
├── src/main/java/
│   └── com/ia/core/communication/service/
│       ├── EmailService.java      # Envio de emails
│       ├── SmsService.java        # Envio de SMS
│       ├── NotificationService.java # Notificações
│       ├── impl/                  # Implementações
│       └── util/                  # Utilitários
└── pom.xml
```

## 🔑 Responsabilidades

- **EmailService**: SMTP, templates, attachments
- **SmsService**: Gateway de SMS externo
- **NotificationService**: Push, in-app, email
- **DeliveryTracking**: Rastreamento de status
- **RetryMechanism**: Reenvio automático

## 🛠️ Tecnologias

- Spring Mail
- Spring Templates (Thymeleaf)
- Jakarta Mail
- Resilience4j (retry)

## 📦 Dependências

- `ia-core-communication-service-model`
- `ia-core-resilience4j`
- Spring Mail

## 💡 Recursos

- Templates de email customizáveis
- Retry com backoff exponencial
- Histórico de entregas
- Múltiplos gateways de SMS


