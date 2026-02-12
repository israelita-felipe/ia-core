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
| Repository | PascalCase + Repository | `EventoRepository`, `PessoaRepository` |
| Service | PascalCase + Service | `EventoService`, `PessoaService` |
| Mapper | PascalCase + Mapper | `EventoMapper`, `PessoaMapper` |
| Translator | PascalCase + Translator | `EventoTranslator`, `PessoaTranslator` |
| SearchRequest | PascalCase + SearchRequest | `EventoSearchRequest` |
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
