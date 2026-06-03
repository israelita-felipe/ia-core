# Progresso de Implementação - Agentes Guiados por Ontologias

**Data:** 01 de Junho de 2026
**Documento de Referência:** CDU-Agentes-Guiados-por-Ontologias.md
**Status:** Em Andamento

---

## Resumo Executivo

Este documento rastreia o progresso da implementação da arquitetura de Agentes Guiados por Ontologias conforme especificado no CDU. A implementação segue uma abordagem neuro-simbólica integrando LLMs com OWL 2 DL através de tools especializadas.

**Nota:** Uma proposta de refatoração foi adicionada ao CDU (Seção 6) para integrar a arquitetura OWL com os conceitos de domínio do ia-core (FerramentaDTO, TemplateDTO, PromptDTO, SkillDTO, AgenteDTO). Esta refatoração está documentada mas ainda não foi implementada.

---

## Progresso Geral

- **Total de Construtores OWL 2 DL:** 50
- **Construtores Implementados:** 50 (100%)
- **Construtores Pendentes:** 0 (0%)
- **Fases Completas:** 6 de 6 (100%)
- **Refatoração Pendente:** Seção 6 do CDU (0% implementado)

---

## Fases de Implementação

### Fase 1: Infraestrutura Base ✅ COMPLETA

**Status:** Concluído

**Itens Implementados:**
- ✅ Pacotes criados: `tool/base`, `tool/classexpression`, `tool/objectproperty`, `tool/dataproperty`, `tool/individual`, `tool/annotation`
- ✅ Interfaces base: `OWLTool.java`, `AbstractOWLTool.java`, `ToolResult.java`, `OntologyContext.java`
- ✅ DTOs: `ConversationContext.java`, `AgentResponse.java`, `OntologyBuildRequest.java`, `OntologyBuildResult.java`, `ValidationResult.java`
- ✅ Configuração Spring: `ConfiguracaoAgentesOWL.java`

---

### Fase 2: Tools OWL 2 DL ✅ COMPLETA

**Status:** 50 de 50 construtores implementados (100%)

#### 5.1 Expressões de Classe (22 construtores)

| Construtor | Tool | Status | Observações |
|------------|------|--------|-------------|
| SubClassOf | SubClassOfTool | ✅ Implementado | Já existia |
| EquivalentClasses | EquivalentClassesTool | ✅ Implementado | Já existia |
| DisjointClasses | DisjointClassesTool | ✅ Implementado | Recém-implementado |
| DisjointUnion | DisjointUnionTool | ✅ Implementado | Recém-implementado |
| UnionOf | UnionOfTool | ✅ Implementado | Recém-implementado |
| IntersectionOf | IntersectionOfTool | ✅ Implementado | Recém-implementado |
| ComplementOf | ComplementOfTool | ✅ Implementado | Recém-implementado |
| ObjectSomeValuesFrom | SomeValuesFromTool | ✅ Implementado | Recém-implementado |
| ObjectAllValuesFrom | AllValuesFromTool | ✅ Implementado | Recém-implementado |
| ObjectHasValue | HasValueTool | ✅ Implementado | Recém-implementado |
| ObjectMinCardinality | MinCardinalityTool | ✅ Implementado | Recém-implementado |
| ObjectMaxCardinality | MaxCardinalityTool | ✅ Implementado | Recém-implementado |
| ObjectExactCardinality | ExactCardinalityTool | ✅ Implementado | Recém-implementado |
| DataSomeValuesFrom | DataSomeValuesFromTool | ✅ Implementado | Recém-implementado |
| DataAllValuesFrom | DataAllValuesFromTool | ✅ Implementado | Recém-implementado |
| DataHasValue | DataHasValueTool | ✅ Implementado | Recém-implementado |
| DataMinCardinality | DataMinCardinalityTool | ✅ Implementado | Recém-implementado |
| DataMaxCardinality | DataMaxCardinalityTool | ✅ Implementado | Recém-implementado |
| DataExactCardinality | DataExactCardinalityTool | ✅ Implementado | Recém-implementado |
| OneOf | OneOfTool | ✅ Implementado | Recém-implementado |
| HasSelf | HasSelfTool | ✅ Implementado | Recém-implementado |

**Progresso:** 22/22 (100%)

#### 5.2 Propriedades de Objeto (14 construtores)

| Construtor | Tool | Status | Observações |
|------------|------|--------|-------------|
| SubObjectPropertyOf | SubObjectPropertyOfTool | ✅ Implementado | Recém-implementado |
| EquivalentObjectProperties | EquivalentObjectPropertiesTool | ✅ Implementado | Recém-implementado |
| DisjointObjectProperties | DisjointObjectPropertiesTool | ✅ Implementado | Recém-implementado |
| InverseObjectProperties | InverseObjectPropertiesTool | ✅ Implementado | Recém-implementado |
| ObjectPropertyDomain | ObjectPropertyDomainTool | ✅ Implementado | Já existia |
| ObjectPropertyRange | ObjectPropertyRangeTool | ✅ Implementado | Já existia |
| FunctionalObjectProperty | FunctionalObjectPropertyTool | ✅ Implementado | Recém-implementado |
| InverseFunctionalObjectProperty | InverseFunctionalObjectPropertyTool | ✅ Implementado | Recém-implementado |
| ReflexiveObjectProperty | ReflexiveObjectPropertyTool | ✅ Implementado | Recém-implementado |
| IrreflexiveObjectProperty | IrreflexiveObjectPropertyTool | ✅ Implementado | Recém-implementado |
| SymmetricObjectProperty | SymmetricObjectPropertyTool | ✅ Implementado | Recém-implementado |
| AsymmetricObjectProperty | AsymmetricObjectPropertyTool | ✅ Implementado | Recém-implementado |
| TransitiveObjectProperty | TransitiveObjectPropertyTool | ✅ Implementado | Recém-implementado |
| ObjectPropertyChain | ObjectPropertyChainTool | ✅ Implementado | Recém-implementado |

**Progresso:** 14/14 (100%)

#### 5.3 Propriedades de Dado (7 construtores)

| Construtor | Tool | Status | Observações |
|------------|------|--------|-------------|
| SubDataPropertyOf | SubDataPropertyOfTool | ✅ Implementado | Recém-implementado |
| EquivalentDataProperties | EquivalentDataPropertiesTool | ✅ Implementado | Recém-implementado |
| DisjointDataProperties | DisjointDataPropertiesTool | ✅ Implementado | Recém-implementado |
| DataPropertyDomain | DataPropertyDomainTool | ✅ Implementado | Recém-implementado |
| DataPropertyRange | DataPropertyRangeTool | ✅ Implementado | Recém-implementado |
| FunctionalDataProperty | FunctionalDataPropertyTool | ✅ Implementado | Recém-implementado |

**Progresso:** 7/7 (100%)

#### 5.4 Axiomas de Indivíduo (7 construtores)

| Construtor | Tool | Status | Observações |
|------------|------|--------|-------------|
| ClassAssertion | ClassAssertionTool | ✅ Implementado | Recém-implementado |
| ObjectPropertyAssertion | ObjectPropertyAssertionTool | ✅ Implementado | Recém-implementado |
| DataPropertyAssertion | DataPropertyAssertionTool | ✅ Implementado | Recém-implementado |
| SameIndividual | SameIndividualTool | ✅ Implementado | Recém-implementado |
| DifferentIndividuals | DifferentIndividualsTool | ✅ Implementado | Recém-implementado |
| NegativeObjectPropertyAssertion | NegativeObjectPropertyAssertionTool | ✅ Implementado | Recém-implementado |
| NegativeDataPropertyAssertion | NegativeDataPropertyAssertionTool | ✅ Implementado | Recém-implementado |

**Progresso:** 7/7 (100%)

#### 5.5 Axiomas de Anotação (4 construtores)

| Construtor | Tool | Status | Observações |
|------------|------|--------|-------------|
| AnnotationAssertion | AnnotationAssertionTool | ✅ Implementado | Recém-implementado |
| SubAnnotationPropertyOf | SubAnnotationPropertyOfTool | ✅ Implementado | Recém-implementado |
| AnnotationPropertyDomain | AnnotationPropertyDomainTool | ✅ Implementado | Recém-implementado |
| AnnotationPropertyRange | AnnotationPropertyRangeTool | ✅ Implementado | Recém-implementado |

**Progresso:** 4/4 (100%)

---

### Fase 3: Camada de Validação ✅ COMPLETA

**Status:** Concluído

**Itens Implementados:**
- ✅ `ValidadorOntologia.java` - Integração com OpenlletReasonerService
- ✅ `ExplicadorInconsistencia.java` - Explicação em linguagem natural
- ✅ `LoopLLMRaciocinador.java` - Ciclo de correção de respostas
- ✅ DTOs: `ResultadoValidacao`, `FeedbackRaciocinador`, `ExplicacaoInconsistencia`

---

### Fase 4: Agentes ✅ COMPLETA

**Status:** Concluído

**Itens Implementados:**
- ✅ `AgenteConversacionalOntologiaService.java` - Serviço de agente conversacional
- ✅ `AgenteConstrutorOntologiaService.java` - Serviço de agente construtor de ontologias
- ✅ `AgentOrchestrator.java` - Orquestrador central de agentes
- ✅ `ExtratorEntidadesRelacoes.java` - Extração de entidades e relações usando LLM
- ✅ `AnaliseCorpus.java` - Análise de corpus para construção de ontologias
- ✅ Lógica de extração de axiomas de conversações
- ✅ Validação em tempo real via ValidadorOntologia
- ✅ Correção de inconsistências via LoopLLMRaciocinador
- ✅ Análise de corpus
- ✅ Extração de entidades e relações
- ✅ Geração de axiomas usando tools OWL 2 DL
- ✅ Refinamento iterativo

---

### Fase 5: Camada REST ✅ COMPLETA

**Status:** Concluído

**Itens Implementados:**
- ✅ `AgenteConversacionalController.java` - Endpoints para agente conversacional
- ✅ `AgenteConstrutorController.java` - Endpoints para agente construtor
- ✅ `FerramentasController.java` - Endpoints para ferramentas OWL 2 DL
- ✅ `ValidacaoController.java` - Endpoints para validação de ontologias
- ✅ `OntologiasController.java` - Endpoints para gerenciamento de ontologias

---

### Fase 6: Camada View ✅ COMPLETA

**Status:** Concluído

**Itens Implementados:**
- ✅ `AgenteFormView.java` - Formulário de agentes
- ✅ `AgenteListView.java` - Lista de agentes
- ✅ `AgentePageView.java` - Página de agentes
- ✅ `ValidacaoPageView.java` - Página de validação
- ✅ `ConstrutorOntologiaPageView.java` - Página de construtor de ontologia
- ✅ `OntologiaFormView.java` - Formulário de ontologias
- ✅ `OntologiaListView.java` - Lista de ontologias
- ✅ `OntologiaPageView.java` - Página de ontologias
- ✅ `OntologiaVisualizerView.java` - Visualizador de ontologias
- ✅ `PromptFormView.java` - Formulário de prompts
- ✅ `PromptListView.java` - Lista de prompts
- ✅ `PromptPageView.java` - Página de prompts
- ✅ `TemplateFormView.java` - Formulário de templates
- ✅ `TemplateListView.java` - Lista de templates
- ✅ `TemplatePageView.java` - Página de templates
- ✅ `SessaoConversacaoView.java` - View de sessões de conversação
- ✅ `ChatDialog.java` - Dialog de chat
- ✅ `FerramentasView.java` - View de ferramentas OWL 2 DL
- ✅ `InconsistenciaDialog.java` - Dialog de inconsistências
- ✅ `ChatMemoryView.java` - View de gerenciamento de memória de chat

---

## Fase 7: Refatoração para Integração com Conceitos de Domínio 🔄 EM ANDAMENTO

**Status:** Iniciado - Implementação parcial (10 de 16 itens completos, 5 de 50 tools atualizados)

**Descrição:** Integrar a arquitetura OWL com os conceitos de domínio do ia-core (FerramentaDTO, TemplateDTO, PromptDTO, SkillDTO, AgenteDTO) conforme ADR-048.

**Itens da Proposta:**

### 6.3.1 AbstractOWLTool Estende FerramentaDTO
- ✅ Refatorar `AbstractOWLTool` para estender `FerramentaDTO`
- ✅ Adicionar método `ensurePersistence()` para auto-persistência
- ✅ Adicionar dependências `TemplateUseCase` e `FerramentaUseCase`
- 🔄 Substituir `getPromptTemplate()` por `getTemplateId()` em 50 tools (5 de 50 completos)
- 🔄 Atualizar construtores de 50 implementações de tools (5 de 50 completos)
- ⏳ Eliminar `OWLToolRegistry` (FerramentaDiscoveryService já descobre)

### 6.3.2 Adicionar Metadados ao AgenteDTO
- ✅ Adicionar campo `metadados` (Map<String, Object>) ao `AgenteDTO`
- ✅ Adicionar campo `metadadosJson` (@Lob) à entidade `Agente`
- ✅ Não criar DTOs especializados (usar metadados)
- ✅ Adicionar método `initializeDefaultAgents()` ao `AgentOrchestratorService`
- ⏳ Implementar inicialização de templates, prompts, ferramentas e agentes OWL

### 6.3.3 Adicionar Conceito de Task e Eliminar Serviços
- ✅ Adicionar conceito de `AgentTask` ao `AgentOrchestratorService`
- ✅ Implementar métodos `createTask()`, `getTaskProgress()`, `cancelTask()`
- ⏳ Eliminar `AgenteConversacionalOntologiaService`
- ⏳ Eliminar `AgenteConstrutorOntologiaService`
- ⏳ Eliminar `AgentOrchestrator` específico
- ⏳ Migrar funcionalidade de Job para Task genérico

**Plano de Migração (7 semanas):**

#### Fase 1: Preparação (Semanas 1-2)
- ⏳ Adicionar campo `metadados` ao `AgenteDTO` e à entidade `Agente`
- ⏳ Criar `TemplateDTO` para cada um dos 50 prompts de tool OWL
- ⏳ Criar `PromptDTO` para prompts padrão de OWL
- ⏳ Preparar scripts de migração para dados existentes

#### Fase 2: Refatoração de Tools (Semanas 3-4)
- ⏳ Refatorar `AbstractOWLTool` para estender `FerramentaDTO`
- ⏳ Adicionar método `ensurePersistence()` para auto-persistência
- ⏳ Substituir `getPromptTemplate()` por `getTemplateId()` e usar `TemplateUseCase`
- ⏳ Atualizar todas as 50 implementações de tools
- ⏳ Testes unitários para cada tool com templates
- ⏳ Eliminar `OWLToolRegistry` (dependência removida)

#### Fase 3: Refatoração de Agentes (Semanas 3-4)
- ⏳ Adicionar método `initializeDefaultAgents()` ao `AgentOrchestratorService`
- ⏳ Implementar inicialização de templates, prompts, ferramentas e agentes OWL
- ⏳ Adicionar conceito de `AgentTask` ao `AgentOrchestratorService`
- ⏳ Implementar métodos `createTask()`, `getTaskProgress()`, `cancelTask()`
- ⏳ Eliminar `AgenteConversacionalOntologiaService`
- ⏳ Eliminar `AgenteConstrutorOntologiaService`
- ⏳ Migrar funcionalidade de Job para Task genérico
- ⏳ Excluir `AgentOrchestrator` específico

#### Fase 4: Integração e Testes (Semanas 5-6)
- ⏳ Atualizar controllers REST para usar `AgentOrchestratorService` geral
- ⏳ Atualizar views para permitir edição de templates e metadados de agentes
- ⏳ Testes de integração end-to-end
- ⏳ Verificar inicialização automática de agentes OWL

#### Fase 5: Remoção de Código Legado (Semana 7)
- ⏳ Remover `AgentOrchestrator` específico
- ⏳ Remover `AgenteConversacionalOntologiaService`
- ⏳ Remover `AgenteConstrutorOntologiaService`
- ⏳ Remover métodos de `getPromptTemplate()` hardcoded
- ⏳ Limpeza de código não utilizado
- ⏳ Atualizar documentação

**Benefícios Esperados:**
- Personalização por usuário (templates editáveis, ferramentas ativáveis)
- Descoberta dinâmica (ferramentas OWL registradas automaticamente)
- Orquestração centralizada (único ponto para todos os agentes)
- Consistência com ADR-048 (separação de domínio)
- Extensibilidade (novos agentes usando metadados)
- Simplificação (eliminação de serviços e DTOs especializados)

---

## Itens Pendentes - Prioridade Alta

- ⏳ Implementar refatoração proposta na Seção 6 do CDU
- ⏳ Integrar AbstractOWLTool com FerramentaDTO
- ⏳ Adicionar metadados ao AgenteDTO
- ⏳ Implementar conceito de Task no AgentOrchestratorService
- ⏳ Eliminar serviços especializados (AgenteConversacionalOntologiaService, AgenteConstrutorOntologiaService)

---

## Próximos Passos

1. **Imediato:** Revisar e aprovar a proposta de refatoração na Seção 6 do CDU
2. **Curto Prazo:** Iniciar Fase 1 da refatoração (Preparação)
3. **Médio Prazo:** Executar Fases 2-5 da refatoração
4. **Longo Prazo:** Validar integração completa com conceitos de domínio do ia-core

---

## Observações

- Todos os 50 construtores OWL 2 DL foram implementados como tools especializadas (100%)
- Todos os tools implementados seguem o padrão de `SubClassOfTool` e `EquivalentClassesTool`
- A configuração Spring foi atualizada para registrar todos os 50 tools
- DTOs de validação foram criados em `com.ia.core.llm.service.model.validacao`
- A camada de validação está completa com `ValidadorOntologia`, `LoopLLMRaciocinador`, e `ExplicadorInconsistencia`
- Uma proposta de refatoração foi documentada no CDU (Seção 6) para integrar a arquitetura OWL com os conceitos de domínio do ia-core
- A refatoração proposta visa eliminar duplicações e centralizar a orquestração no AgentOrchestratorService
- O conceito de Task genérico substituirá o Job específico do AgenteConstrutorOntologiaService
