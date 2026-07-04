# Regras de Negócio (RN)

## Visão Geral

Este diretório contém as Regras de Negócio do projeto ia-core, seguindo o padrão ADR-054.

## Módulos

| Módulo | Descrição | RNs | CDU Referenciado |
|--------|-----------|-----|------------------|
| [Conversacao-Chat](Conversacao-Chat/README.md) | Conversação com agentes LLM | RN_CHT_001 a RN_CHT_006 | CDU001-Conversacao-Chat |
| [Manter-Agente](Manter-Agente/README.md) | Gerenciamento de agentes LLM | RN_AGT_001 a RN_AGT_008 | CDU002-Manter-Agente |
| [Manter-Communication](Manter-Communication/README.md) | Comunicação e notificações do sistema | RN_COM_001 a RN_COM_008 | CDU003-Manter-Communication |
| [Manter-Ferramenta](Manter-Ferramenta/README.md) | Gerenciamento de ferramentas | RN_FER_001 a RN_FER_007 | CDU004-Manter-Ferramenta |
| [Manter-Ferramenta-LLM](Manter-Ferramenta-LLM/README.md) | Ferramentas LLM específicas | RN_LLM_001 a RN_LLM_006 | CDU005-Manter-Ferramenta-LLM |
| [Manter-Grammar](Manter-Grammar/README.md) | Grammar ANTLR | RN_GRM_001 a RN_GRM_005 | CDU007-Manter-Grammar |
| [Manter-OWL](Manter-OWL/README.md) | Ontologias OWL (reasoners) | RN_OWL_001 a RN_OWL_007 | CDU009-Manter-OWL |
| [Manter-NLP](Manter-NLP/README.md) | Processamento de linguagem natural | RN_NLP_001 a RN_NLP_006 | CDU008-Manter-NLP |
| [Manter-Ontologia](Manter-Ontologia/README.md) | Gerenciamento de ontologias OWL | RN_ONT_001 a RN_ONT_008 | CDU010-Manter-Ontologia |
| [Manter-REST](Manter-REST/README.md) | Gerenciamento de APIs REST | RN_API_001 a RN_API_007 | CDU012-Manter-REST |
| [Manter-Report](Manter-Report/README.md) | Gerenciamento de relatórios | RN_RPT_001 a RN_RPT_007 | CDU013-Manter-Report |
| [Manter-Template](Manter-Template/README.md) | Gerenciamento de templates de prompt | RN_TMP_001 a RN_TMP_007 | CDU017-Manter-Template |
| [OperacoesCRUDServico](OperacoesCRUDServico/README.md) | Operações CRUD padronizadas | RN_CRU_001 a RN_CRU_007 | CDU019-OperacoesCRUDServico |
| [ContextoExecucaoServico](ContextoExecucaoServico/README.md) | Contexto de execução de serviço | RN_CTX_001 a RN_CTX_006 | CDU020-ContextoExecucaoServico |
| [AnotacoesTransacionais](AnotacoesTransacionais/README.md) | Anotações transacionais customizadas | RN_TRN_001 a RN_TRN_007 | CDU021-AnotacoesTransacionais |
| [Manter-Scheduler](Manter-Scheduler/README.md) | Agendamento e execução de tarefas | RN_SCH_001 a RN_SCH_004 | CDU014-Manter-Scheduler |
| [Manter-Security](Manter-Security/README.md) | Segurança e autenticação | RN_SEC_001 a RN_SEC_006 | CDU015-Manter-Security |
| [Periodicidade](Periodicidade/README.md) | Periodicidade de eventos | RN_PER_001 a RN_PER_021 | CDU011-Manter-Quartz |
| [Skills](Skills/README.md) | Módulos de IA reutilizáveis | RN_SKL_001 a RN_SKL_002 | CDU016-Manter-Skill |
| [Manter-Resilience](Manter-Resilience/README.md) | Resilience4j | RN_RES_001 a RN_RES_005 | CDU046-Manter-Resilience |
| [Orquestrar-Sessao-Agentes](Orquestrar-Sessao-Agentes/README.md) | Orquestração de agentes | RN_SES_001 a RN_SES_005 | CDU018-Orquestrar-Sessao-Agentes |
| [GerenciamentoViews](GerenciamentoViews/README.md) | Views Vaadin | RN_VIW_001 a RN_VIW_005 | CDU022-GerenciamentoViews |
| [TratamentoExcecoesServico](TratamentoExcecoesServico/README.md) | Exception handling | RN_EXC_001 a RN_EXC_006 | CDU026-TratamentoExcecoesServico |
| [FiltragemDinamica](FiltragemDinamica/README.md) | Filtragem dinâmica de dados | RN_FLD_001 a RN_FLD_007 | CDU027-FiltragemDinamica |
| [Internacionalizacao](Internacionalizacao/README.md) | i18n | RN_INT_001 a RN_INT_006 | CDU029-Internacionalizacao |

## Padrão de Nomenclatura

- **PREFIXO**: 3 letras maiúsculas identificando o módulo
- **Número**: 3 dígitos sequenciais por módulo (001, 002, ...)
- **Exemplo**: CHT_001, AGT_001, FER_001

## Estrutura do Documento

Cada documento RN deve conter:
- Visão Geral
- Referência (CDU, Service, Módulo)
- Regras de Negócio (Nome, Descrição, Critérios, Severidade, Referência CDU)
- Padrão de Implementação
- Referências
