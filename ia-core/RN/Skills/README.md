# Skills - Módulos de IA

## Visão Geral
Skills são módulos especializados encapsulando funcionalidades de IA e serviços reutilizáveis entre projetos.

---

## Skills Identificadas

### ia-core-llm-service
- **Pacote**: `com.ia.core.llm.service`
- **Objetivo**: Orquestrar operações de chat comLLM, vector store e templates
- **Contrato principal**: `ChatService`

**Exemplo de uso** (código real):
```java
protected String ask(String document, String text, String systemTemplate,
    FinalidadeComandoEnum finalidade, boolean exigeContexto, Map<String, Object> params) {
  Prompt prompt = promptTemplateService.createSystemPrompt(
      document, text, systemTemplate, finalidade, params);
  CallResponseSpec response = call(prompt);
  // ...
}
```

### ia-core-nlp-service
- **Pacote**: `com.ia.core.nlp.service`
- **Objetivo**: Processamento de linguagem natural para texto bíblico

### ia-core-communication-service
- **Pacote**: `com.ia.core.communication.service`
- **Objetivo**: Envio de mensagens via múltiplos canais

### ia-core-quartz-service
- **Pacote**: `com.ia.core.quartz.service`
- **Objetivo**: Agendamento de jobs com suporte a periodicidade RFC5545

---

## Estrutura de Uma Skill

Cada skill segue:
```
ia-core-{skill}/
├── pom.xml
├── ia-core-{skill}-model/      # DTOs
├── ia-core-{skill}-service/    # Implementação
└── ia-core-{skill}-view/       # UI Vaadin
```

---

## Referência
- Código: `ia-core/ia-core-llm-service/`