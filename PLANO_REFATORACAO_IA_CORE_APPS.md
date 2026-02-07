# Plano de Refatoração - ia-core-apps

## Visão Geral

Este documento define o plano de refatoração para o projeto `ia-core-apps`, seguindo as diretrizes estabelecidas em [`PLANO_REFATORACAO_COMPLETO.md`](PLANO_REFATORACAO_COMPLETO.md) na pasta raiz.

---

## Estrutura Atual

```
ia-core-apps/
├── ia-core/                    # Módulo principal
│   ├── ia-core-model/          # Camada de modelo e entidades
│   ├── ia-core-service/        # Camada de serviço (regras de negocio)
│   ├── ia-core-rest/           # Camada REST (controllers)
│   ├── ia-core-view/           # Camada de apresentacao (Vaadin)
│   ├── ia-core-llm-service/    # Servicos LLM
│   ├── ia-core-llm-view/      # View LLM
│   ├── ia-core-llm-service-model/  # DTOs LLM
│   ├── ia-core-quartz-service/ # Servicos de agendamento
│   ├── ia-core-quartz-view/    # View Quartz
│   ├── ia-core-nlp/            # Processamento de linguagem natural
│   ├── ia-core-grammar/        # Grammars ANTLR
│   ├── ia-core-flyway/         # Migracoes de banco
│   ├── ia-core-report/         # Geracao de relatorios
│   ├── ia-core-llm-model/      # Modelos LLM
│   ├── ia-core-quartz-service-model/ # DTOs Quartz
│   └── pom.xml                 # Parent POM
```

---

## Problemas Identificados

### SOLID Violations

| Classe | Problema | Solução Proposta |
|--------|----------|------------------|
| [`ChatService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/chat/ChatService.java) | SRP violado - multiplas responsabilidades | Extrair VectorStoreOperations e PromptTemplateService |
| [`LLMTransformationService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java) | SRP violado - imagens e texto misturados | Separar ImagePreprocessingService |
| [`CoreOWLService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/owl/service/CoreOWLService.java) | Multiplas responsabilidades | Criar OWLReasoningService e OWLParsingService |

### Clean Code Violations

| Localização | Problema | Correção |
|-------------|----------|----------|
| [`LLMTransformationService.java:34`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java:34) | `llmComminicator` typo | Renomear para `llmCommunicator` |
| [`VectorStoreService.java:31-33`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/vector/VectorStoreService.java) | Metodo sem documentacao | Adicionar Javadoc |
| [`BaseEntity.java:55`](ia-core/ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java) | NullPointerException potencial | Tratamento de null |

---

## Fases de Refatoração

### Fase 1: Fundamentos e Infraestrutura

#### Passo 1.1: Padronizar Validacao Jakarta em DTOs

**Prioridade:** Alta  
**Impacto:** Alto  
**Risco:** Baixo

**Ações:**
1. Revisar todos os DTOs em `ia-core-llm-service-model/`
2. Adicionar anotacoes Jakarta Validation (`@NotNull`, `@Size`, `@Pattern`)
3. Criar arquivo i18n em `ia-core-llm-service-model/src/main/resources/i18n/translations_llm_service_model_pt_BR.properties`
4. Mapear chaves de traducao em classes `Translator` internas

**Arquivos para modificar:**
- `ComandoSistemaDTO`
- `TemplateDTO`
- `TemplateParameterDTO`
- `AxiomaDTO`

#### Passo 1.2: Corrigir Typos e Nomenclatura

**Prioridade:** Media  
**Impacto:** Medio  
**Risco:** Baixo

**Ações:**
1. Corrigir `llmComminicator` -> `llmCommunicator` em LLMTransformationService
2. Padronizar nomenclatura de variaveis em `VectorStoreService`

#### Passo 1.3: Padronizar uso de Lombok

**Prioridade:** Media  
**Impacto:** Medio  
**Risco:** Baixo

**Ações:**
1. Usar `@Getter`/`@Setter` em vez de `@Data` onde necessario
2. Usar `@Value` para objetos imutaveis
3. Padronizar `@Builder` vs `@SuperBuilder`

---

### Fase 2: Separacao de Responsabilidades (SRP)

#### Passo 2.1: Separar ChatService

**Prioridade:** Media  
**Impacto:** Medio  
**Risco:** Medio

**Novo Design:**
```
ChatService (orquestracao)
├── ChatSessionService (gerenciamento de sessao)
├── VectorStoreOperations (acesso a vector store)
├── PromptTemplateService (manipulacao de templates)
└── ChatResponseHandler (tratamento de respostas)
```

**Ações:**
1. Criar `VectorStoreOperations` - extrair de ChatService
2. Criar `PromptTemplateService` - extrair de ChatService
3. Criar `ChatSessionService` - gerenciar sessoes
4. Refatorar ChatService para orquestrador

#### Passo 2.2: Separar OWL Services

**Prioridade:** Media  
**Impacto:** Medio  
**Risco:** Medio

**Novo Design:**
```
CoreOWLService (orquestracao)
├── OWLReasoningService (inferencias)
├── OWLParsingService (parsing)
└── OWLOntologyManagementService (gerenciamento)
```

**Ações:**
1. Criar interface `OWLReasoningService`
2. Criar interface `OWLParsingService`
3. Criar interface `OWLOntologyManagementService`
4. Refatorar CoreOWLService para usar abstracoes

---

### Fase 3: Camada de Apresentacao (View)

#### Passo 3.1: Padronizar MVVM com ViewModelConfig

**Prioridade:** Alta  
**Impacto:** Alto  
**Risco:** Medio

**Ações:**
1. Seguir padrao existente de `FormViewModel` e `FormViewModelConfig`
2. ViewModel recebe `*FormViewModelConfig`, `CoreViewTranslator` e `FormValidator`
3. Usar validacao Jakarta via `FormValidator`

#### Passo 3.2: Criar FormValidator generico

**Prioridade:** Alta  
**Impacto:** Alto  
**Risco:** Baixo

**Ações:**
1. Criar componente `FormValidator` usando `jakarta.validation.Validator`
2. Injetar em todos os `FormViewModel`
3. Usar traducao via `CoreViewTranslator`

---

## Padroes Ja Existentes (NAO REFATORAR)

1. **Service Configuration**: Ja existe padrao com `@Service` e injecao via construtor
2. **ViewModel**: Ja existe `FormViewModel` e `FormViewModelConfig`
3. **Translator**: Ja existe `CoreTranslator` (REST) e `CoreViewTranslator` (View)
4. **Exception Handler**: Ja existe `CoreRestControllerAdvice`
5. **Cache**: Ja existe configuracao de cache
6. **Transaction**: Ja existe `@Transactional` do Spring
7. **DTO.CAMPOS**: Ja existe padrao de classe interna com constantes

---

## O que NAO Fazer

1. **NAO criar** classes `*ServiceConfig` com `@Configuration` e `@Bean`
2. **NAO implementar** Unit of Work ou JpaUnitOfWork
3. **NAO implementar** novo Exception Handler
4. **NAO refatorar** cache existente
5. **NAO usar** ObservableList
6. **NAO implementar** novo padrao de traducao

---

## Metricas de Sucesso

| Metrica | Target |
|---------|--------|
| Cobertura de Testes | 80% |
| Complexidade Ciclomatica | < 10 |
| DTOs com Validacao Jakarta | 100% |
| Strings em i18n | 100% |

---

## Estrategia de Migracao

**Recomendado: Incremental**

1. Criar nova estrutura paralela
2. Migrar modulo por modulo
3. Manter compatibilidade com codigo antigo
4. Feature flags para codigo novo/antigo
5. Rollback possivel a qualquer momento

**Versionamento:**
- v1.0: Estado atual
- v1.1: Validacao Jakarta completa
- v1.2: Separacao de responsabilidades
- v2.0: Arquitetura completa

---

## Referências

- [`PLANO_REFATORACAO_COMPLETO.md`](PLANO_REFATORACAO_COMPLETO.md)
- SOLID Principles
- Clean Code (Robert Martin)
- Spring Boot Best Practices
- Vaadin Best Practices
