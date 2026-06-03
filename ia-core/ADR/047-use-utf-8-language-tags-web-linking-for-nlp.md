# ADR 047: Use UTF-8, Language Tags, and Web Linking for NLP/Linguagem Modules

## Status
Accepted

## Context
Os módulos de processamento de linguagem natural (NLP) e gramática precisam lidar corretamente com caracteres internacionais, tags de idioma e links da web. Precisamos adotar padrões estabelecidos para garantir correta manipulação de texto multilíngue e semântica web.

## Decision
Vamos utilizar os seguintes RFCs para os módulos NLP/Linguagem:
- RFC 3629: UTF-8, um formato de transformação de caracteres ISO 10646
- RFC 5646: Tags para Identificação de Idiomas
- RFC 5988: Web Linking

Isso garantirá:
- Correta manipulação de caracteres Unicode em processamento de texto
- Identificação padronizada de idiomas para recursos multilíngues
- Semântica de links para recursos web relacionados a linguística
## Standardization Patterns

Este ADR adere aos seguintes padrões, RFCs e melhores práticas:

### RFCs Relevantes

| RFC | Título | Aplicação |
|-----|--------|-----------|
| **RFC 3629** | UTF-8, a transformation format of ISO 10646 | Codificação obrigatória para todos os arquivos de código e recursos |
| **RFC 5646** | Tags for Identifying Languages | Tags de idioma para recursos multilíngues |
| **RFC 5988** | Web Linking | Semântica de links para recursos web linguísticos |

### Padrões de Mercado

| Padrão | Fonte | Aplicação |
|--------|-------|-----------|
| **UTF-8 Encoding** | Oracle JDK | Todos arquivos fonte e recursos |
| **Language Tag Format** | IETF BCP 47 | Tags como `pt-BR`, `en-US` |
| **MessageFormat** | Oracle JDK | Formatação de mensagens parametrizadas |
| **Unicode CLDR** | Unicode Consortium | Dados de localização (números, datas) |
| **REST Linking** | RFC 5988 | Cabeçalhos Link com `rel` apropriado |

### Boas Práticas Adotadas

1. **UTF-8 obrigatório**: Todos arquivos devem usar UTF-8; configurar `file.encoding=UTF-8` no IDE.
2. **Tags de idioma**: Seguir BCP 47 (ex: `pt-BR`, `en-US`); usar `Locale.forLanguageTag`.
3. **Web Linking**: Utilizar cabeçalhos `Link` com `rel` como `self`, `next`, `prev`, `first`, `last`.
4. **Mensagens parametrizadas**: Usar `MessageFormat` para pluralização e parâmetros.
5. **Fallback de locale**: Sempre fornecer `messages.properties` como padrão.
6. **Validação de tags**: Validar tags de idioma antes de uso.
7. **Consistência de chave**: Nomenclatura de chaves de tradução segue padrão `<modulo>.<categoria>.<chave>`.
8. **Teste de idioma**: Testes unitários validam mensagens em todos os locales suportados.

### Compatibilidade com ADRs Relacionados

- **ADR-003**: Classes `*Translator` e chaves de properties seguem convenções de nomenclatura.
- **ADR-010**: Nomenclatura de pacotes e classes segue padrões do projeto.
- **ADR-017**: Migrações seguem convenções de versionamento e descrição.
- **ADR-048**: Classes AI/MCP seguem convenções de nomenclatura do Spring AI.
- **ADR-050**: Diretrizes gerais de padronização do projeto.

## Consequences

### Positivos
- Suporte completo a Unicode e idiomas internacionais
- Interoperabilidade com sistemas de linguagem padrão
- Melhor tratamento de texto multilíngue em processamento NLP
- Semântica de links bem definida para recursos web

### Negatives
- Complexidade adicional no tratamento de codificação de caracteres
- Necessidade de validação de tags de idioma
- Overhead de processamento para parsing de links web

## Implementation Plan
1. Garantir que todos os módulos NLP utilizem UTF-8 como encoding padrão
2. Implementar validação e uso de tags de idioma conforme RFC 5646
3. Utilizar cabeçalhos Link e relações de link conforme RFC 5988 quando aplicável
4. Assegurar que processadores de texto lidem corretamente com sequências Unicode
5. Implementar detecção de idioma quando necessário usando bibliotecas estabelecidas
6. Suporte a internacionalização (i18n) e localização (l10n) em recursos linguísticos

## Related Documents
- REFACTORING.md (seção 8 sobre padrões RFC relevantes)
- RFC 3629: UTF-8, a transformation format of ISO 10646
- RFC 5646: Tags for Identifying Languages
- RFC 5988: Web Linking
- ADR 003: Use Translator for I18n (existente)
