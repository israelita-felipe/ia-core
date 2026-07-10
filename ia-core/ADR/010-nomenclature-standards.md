# ADR-010: Padrões de Nomenclatura para o Projeto

## Status

✅ Aceito

## Contexto

O projeto precisa de convenções de nomenclatura consistentes para facilitar a manutenção e legibilidade do código, seguindo as melhores práticas de Clean Code.

## Decisão

Estabelecer padrões de nomenclatura para todas as camadas da aplicação.

## Detalhes

### Padrões por Tipo

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| Entidade | PascalCase (singular) | `Evento`, `Pessoa`, `Familia` |
| DTO | PascalCase + Sufixo DTO | `EventoDTO`, `PessoaDTO` |
| SearchRequest | PascalCase + SearchRequestDTO | `EventoSearchRequestDTO` |
| Repository | PascalCase + Repository | `EventoRepository`, `PessoaRepository` |
| Service | PascalCase + Service | `EventoService`, `PessoaService` |
| Mapper | PascalCase + Mapper | `EventoMapper`, `PessoaMapper` |
| Translator | PascalCase + Translator | `EventoTranslator`, `PessoaTranslator` |
| View | PascalCase + View | `EventoView`, `EventoListView` |
| ViewModel | PascalCase + ViewModel | `EventoViewModel`, `EventoPageViewModel` |
| ViewModelConfig | PascalCase + ViewModelConfig | `EventoViewModelConfig` |

### Packages por Camada

```
com.ia.biblia.model/          # Entidades JPA
com.ia.biblia.service/       # Serviços
com.ia.biblia.repository/     # Repositories
com.ia.biblia.dto/            # DTOs
com.ia.biblia.mapper/         # Mappers
com.ia.biblia.translator/     # Translators
com.ia.biblia.controller/    # REST Controllers
com.ia.biblia.view/           # Views (UI)
com.ia.biblia.viewmodel/      # ViewModels
```

### Nomenclatura de Métodos

| Contexto | Padrão | Exemplo |
|----------|--------|---------|
| Busca por ID | `findById` | `findById(Long id)` |
| Busca todos | `findAll` | `findAll()` |
| Busca com filtros | `search` | `search(EventoSearchRequest request)` |
| Criação | `save` | `save(EventoDTO dto)` |
| Atualização | `update` | `update(EventoDTO dto)` |
| Exclusão | `delete` | `delete(Long id)` |
| Busca com EntityGraph | `findByIdWith*` | `findByIdWithLocal(Long id)` |
| Contagem | `count` | `count(SearchRequest request)` |
| Existência | `exists` | `existsById(Long id)` |

### Constantes e Enums

```java
// Constantes - UPPER_SNAKE_CASE
public static final String EVENTO_STATUS_AGENDADO = "AGENDADO";
public static final int MAX_NOME_LENGTH = 100;

// Enums - PascalCase
public enum EventoStatus {
    AGENDADO,
    EM_ANDAMENTO,
    CONCLUIDO,
    CANCELADO
}

// Campos em DTOs/Entidades - camelCase
private String nomeCompleto;
private LocalDate dataNascimento;
private Boolean ativo;
```

### Tradutores (i18n)

```java
public class EventoTranslator {

    public static final String TITULO = "evento.titulo";
    public static final String DESCRICAO = "evento.descricao";
    public static final String DATA = "evento.data";
    public static final String LOCAL = "evento.local";

    public static final class VALIDATION {
        public static final String TITULO_NOT_NULL = "evento.validation.titulo.not.null";
        public static final String DATA_FUTURA = "evento.validation.data.futura";
    }

    public static final class MESSAGE {
        public static final String CRIADO = "evento.message.criado";
        public static final String ATUALIZADO = "evento.message.atualizado";
        public static final String DELETADO = "evento.message.deletado";
    }
}
```

### Arquivos de Propriedades

| Arquivo | Idioma | Exemplo |
|---------|--------|---------|
| `messages.properties` | Padrão (pt-BR) | `evento.titulo=Título` |
| `messages_en.properties` | Inglês | `evento.titulo=Title` |

## Consequências

### Positivas

- ✅ Consistência em todo o código
- ✅ Facilidade de localização de arquivos
- ✅ Legibilidade melhorada
- ✅ Facilidade de onboarding

### Negativas

- ❌ Requer disciplina da equipe
- ❌ Renomeação de arquivos existentes

## Status de Implementação

✅ **COMPLETO**

- Verificação de nomenclatura feita em todas as classes
- Padrões documentados em CODING_STANDARDS.md
- Refatoração de nomenclatura concluída

## Data

2024-04-01

## Revisores

- Team Lead
- Architect

## Padrões de Padronização (Standardization)

Este ADR adere aos seguintes padrões, RFCs e melhores práticas:

### RFCs Relevantes

| RFC | Título | Aplicação neste ADR |
|-----|--------|---------------------|
| **RFC 3629** | UTF-8, a transformation format of ISO 10646 | Codificação obrigatória para todos os arquivos de código e recursos |
| **RFC 5646** | Tags for Identifying Languages | Tags de locale para nomenclatura de arquivos (ex: `messages_en.properties`) |
| **RFC 8288** | Web Linking | Links de documentação para recursos REST e migração de referências legadas de RFC 5988 |

### Padrões de Mercado

| Padrão | Fonte | Aplicação |
|--------|-------|-----------|
| **Java Package Naming** | Oracle | Nomes de pacotes em lowercase, sem underscores |
| **Java Class Naming** | Oracle | PascalCase para classes, interfaces, enums |
| **Java Method Naming** | Oracle | camelCase para métodos, parâmetros |
| **Java Constant Naming** | Oracle | UPPER_SNAKE_CASE para constantes |
| **Spring Boot Conventions** | Spring | Nomenclatura de beans, controllers, services |
| **REST API Naming** | REST API Guidelines | Nomes de endpoints em lowercase, verbos HTTP |

### Boas Práticas Adotadas

1. **Consistência total**: Todos os módulos seguem exatamente as mesmas convenções.
2. **Prefixos de módulo**: Classes de domínio usam prefixo do módulo (ex: `Pessoa` em `biblia-model`, `Evento` em `biblia-service`).
3. **Sufixos semânticos**: Sufixos indicam claramente o propósito (DTO, Service, Repository, etc.).
4. **Nomes descritivos**: Nomes devem ser auto-explicativos e evitar abreviações não óbvias.
5. **Hierarquia clara**: Pacotes seguem hierarquia lógica (model → service → repository → controller).
6. **Evitar palavras redundantes**: Não usar `Manager` quando `Service` é mais adequado.
7. **Testes seguem padrões**: Classes de teste seguem mesmas convenções com sufixo `Test` ou `Tests`.
8. **Interfaces seguem convenções**: Interfaces usam prefixo `I` ou sufixo `able` quando apropriado.

### Compatibilidade com ADRs Relacionados

- **ADR-003**: Classes `*Translator` seguem convenções de nomenclatura e padrões de keys.
- **ADR-017**: Nomes de migrations seguem convenções de versionamento e descrição.
- **ADR-047**: UTF-8 em todos os arquivos de código e recursos.
- **ADR-048**: Classes de AI/MCP seguem convenções de nomenclatura do Spring AI.
- **ADR-050**: Diretrizes gerais de padronização do projeto.
- **ADR-052**: ADRs devem usar linguagem normativa RFC 2119/RFC 8174 e indicar referências técnicas vigentes.

## Referências

1. **Google Java Style Guide**
   - URL: https://google.github.io/styleguide/javaguide.html
   - Guia de estilo oficial do Google para Java

2. **Oracle - Code Conventions for Java**
   - URL: https://www.oracle.com/java/technologies/javase/codeconventions-contents.html
   - Convenções de código clássicas da Oracle

3. **Baeldung - Java Naming Conventions**
   - URL: https://www.baeldung.com/java-naming-conventions
   - Convenções de nomenclatura modernas

4. **Martin Fowler - Coding Conventions**
   - URL: https://martinfowler.com/bliki/CodingConvention.html
   - Importância de convenções de código

5. **Clean Code - Robert C. Martin**
   - URL: https://www.oreilly.com/library/view/clean-code/9780136083237/
   - Livro de referência sobre código limpo
