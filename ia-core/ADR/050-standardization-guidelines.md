# ADR-050: Diretrizes Gerais de Padronização do Projeto

## Status

- ✅ Aceito
- Data: 2026-06-02

## Objetivo

Estabelecer diretrizes gerais de padronização para todos os módulos do projeto, consolidando padróes, RFCs e boas práticas adotadas em ADRs anteriores.

## Padrões e RFCs Adotados

### 1. Linguagem Normativa em ADRs (RFC 2119 e RFC 8174)
- Aplicado em: ADR-016, ADR-050, ADR-052
- Requisitos:
  - Usar `MUST`, `SHOULD`, `MAY` e equivalentes conforme RFC 2119 e RFC 8174
  - Escrever palavras-chave normativas em maiúsculas
  - Evitar ambiguidade entre requisito, recomendação e opcionalidade

### 2. Formato de ADRs (MADR)
- Aplicado em: ADR-016, ADR-052
- Requisitos:
  - Novos ADRs devem considerar o template MADR
  - ADRs revisados devem incluir contexto, opções, decisão, consequências e confirmação
  - Metadados `decision-makers`, `consulted` e `informed` são recomendados

### 3. Codificação UTF-8 (RFC 3629)
- Aplicado em: ADR-003, ADR-017, ADR-047, ADR-050
- Requisitos:
  - Todos arquivos de código e recursos devem usar UTF-8
  - Configuração de `file.encoding=UTF-8` no IDE
  - Validação de codificação em builds

### 4. Tags de Idioma (RFC 5646)
- Aplicado em: ADR-003, ADR-047, ADR-050
- Requisitos:
  - Uso de tags BCP 47 (`pt-BR`, `en-US`)
  - Validação de tags antes de uso
  - Fallback para `messages.properties`

### 5. Padrões de Nomenclatura (ADR-010)
- Aplicado em: Todos módulos
- Requisitos:
  - PascalCase para classes, interfaces
  - camelCase para métodos e parâmetros
  - UPPER_SNAKE_CASE para constantes
  - Prefixos de módulo em pacotes

### 6. Migrações de Banco de Dados (ADR-017)
- Aplicado em: ADR-017
- Requisitos:
  - Nomenclatura `V{YYYYMMDD_HHMMSS}__Descricao.sql`
  - UTF-8 obrigatório em migrations
  - Atomicidade e idempotência
  - Documentação detalhada

### 7. Padrões NLP e Web Linking (ADR-047)
- Aplicado em: ADR-047, ADR-050
- Requisitos:
  - UTF-8 em processamento de texto
  - Uso de cabeçalhos `Link` com `rel` semântico conforme RFC 8288
  - Validação de tags de idioma
  - Suporte a internacionalização (i18n)

### 8. HTTP e APIs REST (RFC 9110, RFC 9111, RFC 9112, RFC 9113)
- Aplicado em: ADR-044, ADR-051, ADR-050
- Requisitos:
  - Usar RFC 9110 para semântica HTTP, métodos, status e autenticação HTTP
  - Usar RFC 9112 para sintaxe e roteamento HTTP/1.1
  - Usar RFC 9111 para cache quando aplicável
  - Usar RFC 9113 como referência para HTTP/2 quando o transporte permitir
  - RFC 7230-7235 deve ser tratada como referência histórica/obsoleta

### 9. Problem Details para APIs (RFC 9457)
- Aplicado em: ADR-011
- Requisitos:
  - Preferir RFC 9457 para respostas de erro estruturadas
  - RFC 7807 deve ser tratada como legado/obsoleta

### 10. Integração AI com Spring AI (ADR-048)
- Aplicado em: ADR-048, quando restaurado ou substituído por ADR equivalente
- Requisitos:
  - Spring AI 2.0.0-M1 puro
  - Descoberta dinâmica de ferramentas
  - Skills e ferramentas em banco de dados
  - Segurança via `ia-core-security-service`

## Boas Práticas Gerais

1. **Consistência total**: Todos módulos devem seguir as mesmas convenções
2. **Documentação**: Padrões devem ser documentados em ADRs
3. **Testes**: Validação de padrões em testes unitários e de integração
4. **Versionamento**: Seguir padrão `YYYYMMDD_HHMMSS` para versões
5. **Modularidade**: Cada módulo deve ser independente e reutilizável
6. **Rastreabilidade RFC**: Quando um ADR citar uma RFC, indicar se ela está vigente ou obsoleta

## Compatibilidade com ADRs Específicos

- **ADR-003**: Padrões de internacionalização
- **ADR-010**: Convenções de nomenclatura
- **ADR-016**: Melhores práticas de ADRs
- **ADR-017**: Padrões de migração de banco
- **ADR-044**: Comunicação HTTP/1.1
- **ADR-047**: Padrões NLP, UTF-8, tags de idioma e Web Linking
- **ADR-048**: Integração AI
- **ADR-051**: Semântica HTTP para APIs REST
- **ADR-052**: MADR e linguagem normativa RFC 2119/8174

## Referências

1. RFC 2119 - Key words for use in RFCs to Indicate Requirement Levels
2. RFC 8174 - Ambiguity of Uppercase vs Lowercase in RFC 2119 Key Words
3. RFC 3629 - UTF-8
4. RFC 5646 - Tags de idioma
5. RFC 8288 - Web Linking
6. RFC 9110 - HTTP Semantics
7. RFC 9111 - HTTP Caching
8. RFC 9112 - HTTP/1.1
9. RFC 9113 - HTTP/2
10. RFC 9457 - Problem Details for HTTP APIs
11. ADR-016, ADR-044, ADR-047, ADR-050, ADR-051 e ADR-052
12. Spring AI 2.0.0-M1 Documentation
13. Flyway Documentation
