# ia-core-communication-service-model

## 📋 Descrição

DTOs para serviço de comunicação (emails, SMS, notificações). Define estruturas para envio e rastreamento de mensagens.

## 🏗️ Estrutura

```
ia-core-communication-service-model/
├── src/main/java/
│   └── com/ia/core/communication/service/dto/
│       ├── request/               # SendEmailRequest, SendSMSRequest
│       ├── response/              # SendResultDTO
│       └── util/
└── pom.xml
```

## 🔑 Responsabilidades

- **SendEmailRequest**: Parâmetros para envio de email
- **SendSMSRequest**: Parâmetros para SMS
- **SendResultDTO**: Resposta de envio
- **CommunicationHistoryDTO**: Histórico

## 💡 DTOs

```java
@Data
@Builder
public class SendEmailRequest {

    @Email
    @NotBlank
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String templateName;

    private Map<String, Object> variables;

    private List<String> attachmentPaths;
}

@Data
public class SendResultDTO {

    private Boolean success;

    private String messageId;

    private String status;

    private LocalDateTime sentAt;

    private String errorMessage;
}
```


