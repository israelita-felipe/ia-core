# Padrões de Documentação Javadoc

Este documento estabelece os padrões de documentação Javadoc para o projeto ia-core-apps e Biblia.

## Índice

1. [Introdução](#introdução)
2. [Estrutura Básica](#estrutura-básica)
3. [Elementos Documentados](#elementos-documentados)
4. [Tags Javadoc](#tags-javadoc)
5. [Convenções de Nomenclatura](#convenções-de-nomenclatura)
6. [Exemplos](#exemplos)
7. [Ferramentas](#ferramentas)

---

## Introdução

A documentação Javadoc é essencial para a manutenibilidade do projeto. Este documento estabelece padrões consistentes para toda a equipe.

### Objetivos

- **Legibilidade**: Código auto-explicativo
- **Manutenibilidade**: Facilitar atualizações
- **Reusabilidade**: Compreensão rápida de APIs
- **Onboarding**: Acelerar integração de novos desenvolvedores

---

## Estrutura Básica

### Documentação de Classe

```java
/**
 * Classe que representa uma entidade do domínio.
 * 
 * Esta classe fornece funcionalidades básicas para todas as entidades
 * do sistema, incluindo identificação, versionamento e auditoria.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see BaseEntity
 */
public abstract class BaseEntity implements Serializable {
}
```

### Documentação de Método

```java
/**
 * Executa uma operação de busca com paginação.
 * 
 * Este método aplica filtros dinâmicos baseados na especificação
 * fornecida e retorna uma página de resultados ordenados.
 *
 * @param request critérios de busca e ordenação
 * @param page número da página (inicia em 0)
 * @param size quantidade de elementos por página
 * @return página de resultados filtrados
 * @throws IllegalArgumentException se page < 0 ou size <= 0
 * @throws ServiceException se ocorrer erro durante a busca
 */
public Page<D> findAll(SearchRequest request, int page, int size) {
}
```

---

## Elementos Documentados

### 1. Classes e Interfaces

Todas as classes públicas devem ter documentação:

```java
/**
 * Descrição clara da responsabilidade da classe.
 * 
 * Detalhes adicionais sobre o comportamento e uso.
 *
 * @author Nome do Autor
 * @version 1.0.0
 */
public class MinhaClasse {
}
```

### 2. Métodos Públicos

Métodos públicos devem ser documentados com:

- Descrição do que o método faz
- Descrição de cada parâmetro (`@param`)
- Valor de retorno (`@return`)
- Exceções lançadas (`@throws`)
- Exemplo de uso (`@code` ou `@example`)

```java
/**
 * Converte uma entidade para DTO.
 * 
 * Este método realiza o mapeamento da entidade para seu correspondente
 * DTO, incluindo todos os relacionamentos configurados no mapper.
 *
 * @param entity a entidade a ser convertida
 * @return o DTO resultante da conversão
 * @throws IllegalArgumentException se entity for null
 * @throws MappingException se ocorrer erro no mapeamento
 */
public DTO toDTO(Entity entity) {
}
```

### 3. Parâmetros

```java
/**
 * @param id identificador único da entidade (não pode ser null)
 * @param request objeto contendo critérios de filtragem
 * @param page número da página (inicia em 0)
 * @param size quantidade de registros por página (máximo: 100)
 */
public void method(Long id, SearchRequest request, int page, int size) {
}
```

### 4. Retorno

```java
/**
 * @return lista de entidades encontradas (nunca null, pode estar vazia)
 */
public List<Entity> findAll() {
}

/**
 * @return Optional contendo a entidade se encontrada, vazio caso contrário
 */
public Optional<Entity> findById(Long id) {
}
```

### 5. Exceções

```java
/**
 * @throws ResourceNotFoundException se o recurso não for encontrado
 * @throws ValidationException se os dados forem inválidos
 * @throws ServiceException se ocorrer erro no processamento
 */
public void save(Entity entity) {
}
```

---

## Tags Javadoc

### Tags Obrigatórias

| Tag | Uso | Exemplo |
|-----|-----|---------|
| `@param` | Parâmetro | `@param id o identificador` |
| `@return` | Retorno | `@return o DTO convertido` |
| `@throws` | Exceção | `@throws Exception mensagem` |
| `@author` | Autor | `@author Israel Araújo` |
| `@since` | Versão | `@since 1.0.0` |
| `@see` | Referência | `@see BaseEntity` |

### Tags Opcionais

| Tag | Uso | Exemplo |
|-----|-----|---------|
| `@version` | Versão | `@version 1.0.0` |
| `@deprecated` | Obsoleto | `@deprecated use outro método` |
| `@example` | Exemplo | `@example code()` |
| `{@link}` | Link interno | `{@link #findById}` |
| `{@code}` | Código inline | `{@code entity.getId()}` |
| `{@value}` | Valor constante | `{@value #MAX_SIZE}` |

---

## Convenções de Nomenclatura

### 1. Descrições de Parâmetros

```java
// ✅ CORRETO
@param id o identificador único da entidade
@param request os critérios de busca
@param page o número da página (inicia em 0)

// ❌ INCORRETO
@param id o id
@param request request
@param page page number
```

### 2. Descrições de Retorno

```java
// ✅ CORRETO
@return lista de entidades encontradas (nunca null)
@return Optional contendo a entidade se encontrada

// ❌ INCORRETO
@return a lista
@return result
```

### 3. Descrições de Exceções

```java
// ✅ CORRETO
@throws IllegalArgumentException se page < 0
@throws ResourceNotFoundException se a entidade não existir

// ❌ INCORRETO
@throws Exception erro
@throws IllegalArgumentException invalid
```

---

## Exemplos

### Exemplo 1: Repository

```java
/**
 * Repositório para operações de persistência de Evento.
 * 
 * Fornece métodos para CRUD e consultas específicas de eventos,
 * incluindo suporte a EntityGraph para carregamento otimizado.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see BaseEntityRepository
 * @see Evento
 */
public interface EventoRepository extends BaseEntityRepository<Evento> {

    /**
     * Busca um evento pelo ID com todos os relacionamentos.
     * 
     * Utiliza EntityGraph para carregar local, materiais e inscrições
     * em uma única query, evitando problemas de N+1.
     *
     * @param id identificador do evento
     * @return Optional contendo o evento se encontrado
     * @throws IllegalArgumentException se id for null
     */
    @EntityGraph("Evento.withAll")
    Optional<Evento> findByIdWithAll(Long id);
}
```

### Exemplo 2: Service

```java
/**
 * Serviço para operações de negócio de Evento.
 * 
 * Gerencia o ciclo de vida de eventos, incluindo criação,
     */
public interface EventoService extends BaseService<Evento, EventoDTO> {

    /**
     * Agenda um novo evento.
     * 
     * Valida se a data é futura, se o local está disponível
     * e se o responsável tem permissões necessárias.
     *
     * @param dto dados do evento a ser criado
     * @return o evento criado com ID gerado
     * @throws ValidationException se dados forem inválidos
     * @throws BusinessException se regra de negócio for violada
     * @throws ResourceNotFoundException se local não existir
     */
    EventoDTO schedule(EventoDTO dto);
}
```

### Exemplo 3: DTO

```java
/**
 * Data Transfer Object para Evento.
 * 
 * Utilizado para comunicação entre camadas, contendo apenas
     */
public class EventoDTO {

    /**
     * Identificador único do evento.
     */
    private Long id;

    /**
     * Nome/título do evento.
     */
    @NotBlank(message = "{evento.validation.nome.not.blank}")
    @Size(max = 200, message = "{evento.validation.nome.size}")
    private String nome;

    /**
     * Data e hora de realização do evento.
     */
    @NotNull(message = "{evento.validation.data.not.null}")
    private LocalDateTime dataEvento;

    /**
     * Status atual do evento.
     */
    private EventoStatus status;
}
```

---

## Ferramentas

### Verificação de Documentação

Para verificar se classes estão documentadas:

```bash
# Gerar relatório de cobertura Javadoc
mvn javadoc:javadoc 2>/dev/null | grep -E "(warning|error)" | head -20
```

### Plugins Recomendados

1. **IDE**: Inspektions do IntelliJ/Eclipse
2. **CI**: Maven Javadoc Plugin
3. **Qualidade**: ErrorProne

---

## Checklist de Documentação

- [ ] Todas as classes públicas têm `@author` e `@since`
- [ ] Todos os métodos públicos têm `@param`, `@return` e `@throws`
- [ ] Descrições são claras e concisas
- [ ] Exemplos de uso são fornecidos para métodos complexos
- [ ] Referências cruzadas usam `@see` ou `{@link}`
- [ ] Constantes são documentadas quando não são auto-explicativas
- [ ] Classes internas/anonimas são documentadas

---

## Histórico de Revisões

| Versão | Data | Autor | Descrição |
|--------|------|-------|-----------|
| 1.0 | 2024-01-15 | Israel Araújo | Versão inicial |

---

**Nota**: Este documento complementa o `CODING_STANDARDS.md` e deve ser seguido em conjunto com os padrões de codificação estabelecidos.
