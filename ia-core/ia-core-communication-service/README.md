# ia-core-communication-service

## 📋 Descrição

Módulo de serviço para comunicações (emails, SMS, notificações push). Gerencia envio, retry e rastreamento de mensagens. Implementa validações de negócio usando Service Validator Pattern e Business Rule Chain Pattern (ADR-018, ADR-019).

## 🏗️ Estrutura

```
ia-core-communication-service/
├── src/main/java/
│   └── com/ia/core/communication/service/
│       ├── contatomensagem/         # Contatos e mensagens
│       │   ├── ContatoMensagemService.java
│       │   ├── ContatoMensagemRepository.java
│       │   ├── ContatoMensagemMapper.java
│       │   ├── validators/          # Validadores (Service Validator Pattern)
│       │   └── rules/               # Regras de negócio (Business Rule Chain)
│       ├── grupocontato/            # Grupos de contatos
│       │   ├── GrupoContatoService.java
│       │   ├── GrupoContatoRepository.java
│       │   ├── GrupoContatoMapper.java
│       │   └── validators/
│       ├── mensagem/                # Mensagens
│       │   ├── MensagemService.java
│       │   ├── MensagemRepository.java
│       │   ├── MensagemMapper.java
│       │   ├── MensagemProvider.java
│       │   ├── ResultadoEnvio.java
│       │   ├── WebhookEventListener.java
│       │   ├── validators/
│       │   └── rules/
│       ├── modelomensagem/          # Modelos de mensagem
│       │   ├── ModeloMensagemService.java
│       │   ├── ModeloMensagemRepository.java
│       │   ├── ModeloMensagemMapper.java
│       │   └── validators/
│       ├── email/                   # Serviço de email
│       │   ├── EmailService.java
│       │   ├── EmailConfig.java
│       │   └── EstrategiaEmail.java
│       └── estrategia/              # Estratégias de envio
│           ├── EstrategiaEnvio.java
│           └── EstrategiaEnvioFactory.java
└── pom.xml
```

## 🔑 Responsabilidades

- **ContatoMensagemService**: Gerenciamento de contatos e suas mensagens
- **GrupoContatoService**: Gerenciamento de grupos de contatos
- **MensagemService**: Gerenciamento de mensagens e envio
- **ModeloMensagemService**: Gerenciamento de modelos de mensagem
- **EmailService**: Envio de emails via SMTP
- **EstrategiaEnvio**: Estratégias de envio por canal (email, SMS, WhatsApp, etc.)
- **Validators**: Validação de DTOs usando Service Validator Pattern
- **Business Rules**: Regras de negócio usando Business Rule Chain Pattern

## 🛠️ Tecnologias

- Spring Data JPA
- Spring Mail
- Resilience4j (retry)
- Service Validator Pattern (ADR-019)
- Business Rule Chain Pattern (ADR-018)

## 📦 Dependências

- `ia-core-communication-service-model`
- `ia-core-communication-model`
- `ia-core-service`
- `ia-core-resilience4j`
- Spring Mail

## 💡 Recursos

- Validação dinâmica de DTOs
- Regras de negócio compostas
- Estratégias de envio por canal
- Retry com backoff exponencial
- Eventos de webhook para notificações

## 🧪 Testes

- **Tipos de Testes**: Unitários, Integração
- **Cobertura Alvo**: 80-85% (ADR-012 para módulos Service)
- **Frameworks**: JUnit 5, Mockito, AssertJ

## 📐 ADRs Aplicados

- **ADR-018**: Business Rule Chain Pattern - Validação de regras de negócio
- **ADR-019**: Service Validator Pattern - Validação dinâmica de DTOs
- **ADR-012**: Testing Patterns - Estrutura e cobertura de testes


