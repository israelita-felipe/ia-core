# ia-core-communication-rest

## 📋 Descrição

Endpoints REST para o serviço de comunicação. Fornece APIs para envio de emails, SMS e gerenciamento de notificações.

## 🏗️ Estrutura

```
ia-core-communication-rest/
├── src/main/java/
│   └── com/ia/core/communication/rest/
│       ├── EmailController.java   # POST /api/emails
│       ├── SmsController.java     # POST /api/sms
│       └── NotificationController.java
└── pom.xml
```

## 🔑 Endpoints

- `POST /api/v1/email/send` - Enviar email
- `GET /api/v1/email/{id}` - Status de email
- `POST /api/v1/sms/send` - Enviar SMS
- `POST /api/v1/notifications` - Enviar notificação

## 🛠️ Tecnologias

- Spring Web MVC
- Spring Validation
- RestTemplate

## 💡 Featu

- Validação de email/phone
- Autenticação requerida
- Rate limiting


