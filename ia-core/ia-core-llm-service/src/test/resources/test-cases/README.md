# Casos de Teste por DTO e Camada

Este diretório contém casos de teste documentados para DTOs dos módulos `ia-core-llm-*`, organizados por camada da stack:

- Model
- Repository
- Mapper
- ServiceModel
- Service
- API/REST
- View/Client

## Matriz de cobertura

| DTO | Domínio | CDU | Casos presentes | Lacunas |
|-----|---------|-----|-----------------|---------|
| AgentConfirmationDTO | Sessão de Agente | CDU/Orquestrar-Sessao-Agentes: Orquestrar Sessão de Agentes | 7/7 | Completo |
| AgentSessionRequestDTO | Sessão de Agente | CDU/Orquestrar-Sessao-Agentes: Orquestrar Sessão de Agentes | 7/7 | Completo |
| AgentSessionResponseDTO | Sessão de Agente | CDU/Orquestrar-Sessao-Agentes: Orquestrar Sessão de Agentes | 7/7 | Completo |
| AgenteDTO | Agente LLM | CDU/Manter-Agente: Manter Agente | 7/7 | Completo |
| AnaliseInferenciaDTO | OWL Inferência | CDU/Manter-OWL: Manter OWL | 7/7 | Completo |
| AxiomaDTO | Axioma OWL | CDU/Manter-OWL: Manter OWL | 7/7 | Completo |
| ChatRequestDTO | Conversação Chat | CDU/Conversacao-Chat: Conversação Chat | 7/7 | Completo |
| ChatSessionDTO | Conversação Chat | CDU/Conversacao-Chat: Conversação Chat | 7/7 | Completo |
| ContextConversacaoDTO | Contexto de Conversação | CDU/Conversacao-Chat: Conversação Chat | 7/7 | Completo |
| EstatisticasOntologiaDTO | Ontologia OWL | CDU/Manter-Ontologia: Manter Ontologia | 7/7 | Completo |
| FerramentaActivationDTO | Ferramenta LLM | CDU/Manter-Ferramenta-LLM: Manter Ferramenta LLM | 7/7 | Completo |
| FerramentaDTO | Ferramenta LLM | CDU/Manter-Ferramenta-LLM: Manter Ferramenta LLM | 7/7 | Completo |
| FerramentaMetadataDTO | Ferramenta LLM | CDU/Manter-Ferramenta-LLM: Manter Ferramenta LLM | 7/7 | Completo |
| OntologiaDTO | Ontologia OWL | CDU/Manter-Ontologia: Manter Ontologia | 7/7 | Completo |
| PromptDTO | Prompt | CDU/Manter-Template: Manter Template | 7/7 | Completo |
| SkillDTO | Skill LLM | CDU/Manter-Skill: Manter Skill | 7/7 | Completo |
| TemplateDTO | Template | CDU/Manter-Template: Manter Template | 7/7 | Completo |
| TemplateParameterDTO | Template | CDU/Manter-Template: Manter Template | 7/7 | Completo |
| TransformacaoResultDTO | Transformação OWL | CDU/Manter-OWL: Manter OWL | 7/7 | Completo |

## Padrão de nomenclatura

Os arquivos seguem o padrão:

```text
<NomeDto>-<Camada>-Layer.md
```

Exemplo: `ChatRequestDTO-ServiceModel-Layer.md`.

## Aderência a ADRs

Todos os casos devem conter a seção `## Aderência a ADRs`, com metadados, matriz de conformidade, critérios de aceitação, evidências esperadas e referências ADR.

## Referências

- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)
