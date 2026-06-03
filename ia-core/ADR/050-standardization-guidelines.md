# ADR-050: Diretrizes Gerais de Padronização do Projeto

## Status

- ✅ Aceito
- Data: 2026-06-02

## Objetivo

Estabelecer diretrizes gerais de padronização para todos os módulos do projeto, consolidando padróes, RFCs e boas práticas adotadas em ADRs anteriores.

## Padrões e RFCs Adotados

### 1. Codificação UTF-8 (RFC 3629)
- Aplicado em: ADR-003, ADR-017, ADR-047, ADR-048
- Requisitos:
  - Todos arquivos de código e recursos devem usar UTF-8
  - Configuração de `file.encoding=UTF-8` no IDE
  - Validação de codificação em builds

### 2. Tags de Idioma (RFC 5646)
- Aplicado em: ADR-003, ADR-047
- Requisitos:
  - Uso de tags BCP 47 (`pt-BR`, `en-US`)
  - Validação de tags antes de uso
  - Fallback para `messages.properties`

### 3. Padrões de Nomenclatura (ADR-010)
- Aplicado em: Todos módulos
- Requisitos:
  - PascalCase para classes, interfaces
  - camelCase para métodos e parâmetros
  - UPPER_SNAKE_CASE para constantes
  - Prefixos de módulo em pacotes

### 4. Migrações de Banco de Dados (ADR-017)
- Aplicado em: ADR-017
- Requisitos:
  - Nomenclatura `V{YYYYMMDD_HHMMSS}__Descricao.sql`
  - UTF-8 obrigatório em migrations
  - Atomicidade e idempotência
  - Documentação detalhada

### 5. Padrões NLP e Web Linking (ADR-047)
- Aplicado em: ADR-047
- Requisitos:
  - UTF-8 em processamento de texto
  - Uso de cabeçalhos `Link` com `rel` semántico
  - Validação de tags de idioma
  - Suporte a internacionalização (i18n)

### 6. Integração AI com Spring AI (ADR-048)
- Aplicado em: ADR-048
- Requisitos:
  - Spring AI 2.0.0-M1 puro
  - Descoberta dinâmica de ferramentas
  - Skills e ferramentas em banco de dados
  - Segurança via `ia-core-security-service`

## Boas Práticas Gerais

1. **Consistência total**: Todos módulos devem seguir as mesmas convenções
2. **Documentação**: Padrões devem ser documentados em ADRs
3. **Testes**: Validação de padrões em testes unitários e de integração
4. **Versionamento**: Seguir padrõo `YYYYMMDD_HHMMSS` para versões
5. **Modularidade**: Cada módulo deve ser independente e reutilizável

## Compatibilidade com ADRs Específicos

- **ADR-003**: Padrões de internacionalização
- **ADR-010**: Convenções de nomenclatura
- **ADR-017**: Padrõos de migração de banco
- **ADR-047**: Padrõos NLP e web
- **ADR-048**: Integração AI

## Referências

1. RFC 3629 - UTF-8
2. RFC 5646 - Tags de idioma
3. RFC 5988 - Web Linking
4. ADR-003 a ADR-048 (detalhados em seus respectivos arquivos)
5. Spring AI 2.0.0-M1 Documentation
6. Flyway Documentation
