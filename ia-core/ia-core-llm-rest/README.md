# ia-core-llm-rest

## Objetivo

Módulo REST que expõe endpoints para gerenciamento de funcionalidades relacionadas a LLM (Large Language Models), incluindo agentes, sessões de chat, ferramentas, templates e ontologias.

## Principais Funcionalidades

- **Gerenciamento de Agentes**: Endpoints para CRUD de agentes especialistas
- **Sessões de Chat**: Gerenciamento de sessões de conversação com agentes
- **Ferramentas**: Gerenciamento de ferramentas disponíveis para agentes
- **Templates**: Gerenciamento de templates de prompts
- **Ontologias**: Gerenciamento de ontologias e contextos
- **Busca Web**: Integração com ferramentas de busca web

## Tipos de Testes Aplicáveis

- Testes Unitários: Controllers, DTOs, filtros
- Testes de Integração: Endpoints REST com MockMvc
- Testes de Segurança: Autenticação e autorização

## Dependências Principais

- ia-core-llm-service
- ia-core-llm-service-model
- ia-core-rest
- Spring Boot Web
- SpringDoc OpenAPI

## ADRs Relevantes

- ADR-010: Padrões de Nomenclatura
- ADR-011: Padrões de Tratamento de Exceções
- ADR-012: Testing Patterns
