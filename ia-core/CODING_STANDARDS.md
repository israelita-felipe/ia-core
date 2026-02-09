# Padrões de Código - IA Core

Este documento estabelece os padrões de código para o projeto IA Core.

## 📑 Índice

1. [Estrutura de Pacotes](#estrutura-de-pacotes)
2. [Padrões de Entidades](#padrões-de-entidades)
3. [Padrões de DTOs](#padrões-de-dtos)
4. [Padrões de Services](#padrões-de-services)
5. [Padrões de Repositories](#padrões-de-repositories)
6. [Padrões REST](#padrões-rest)
7. [Padrões MVVM](#padrões-mvvm)
8. [Validação](#validação)
9. [Internacionalização](#internacionalização-i18n)

---

## Estrutura de Pacotes

```
com.ia.core.{modulo}.{camada}.{功能}
```

### Camadas

| Camada | Descrição | Exemplo |
|--------|-----------|---------|
| `model` | Entidades e modelos de domínio | `com.ia.core.llm.model.comando` |
| `service` | Lógica de negócio | `com.ia.core.llm.service.comando` |
| `repository` | Acesso a dados | `com.ia.core.service.repository` |
| `rest/control` | Controllers REST | `com.ia.core.rest.control` |
| `view` | Interface MVVM | `com.ia.core.llm.view.chat` |
| `mapper` | Mapeamento DTO-Entidade | `com.ia.core.llm.service.template` |

### Módulos

| Módulo | Descrição |
|--------|-----------|
| `llm` | Integração com Language Models |
| `quartz` | Agendamento de tarefas |
| `nlp` | Processamento de Linguagem Natural |
| `security` | Autenticação e Autorização |
| `report` | Relatórios |

---

## Padrões de Entidades

### Estrutura Base

```java
@Entity
@Table(name = "TABELA", schema = "SCHEMA")
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Entidade
  extends BaseEntity {
  
  // Constantes
  public static final String TABLE_NAME = "TB_ENTIDADE";
  public static final String SCHEMA_NAME = "SCHEMA";
  
  // Atributos
  @Column(name = "nome", nullable = false)
  private String nome;
  
  // Relacionamentos
  @ManyToOne
  @JoinColumn(name = "outro_id")
  private OutraEntidade outro;
  
}
```

### Relacionamentos

```java
// Um para Muitos ( Pai -> Filhos )
@OneToMany(cascade = CascadeType.ALL, 
           orphanRemoval = true,
           mappedBy = "pai",
           fetch = FetchType.LAZY)
private List<Filho> filhos = new ArrayList<>();

// Muitos para Um ( Filho -> Pai )
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "pai_id")
private Pai pai;
```

---

## Padrões de DTOs

### Estrutura

```java
public record EntidadeDTO(
  @NotNull(message = "{validation.id.obrigatorio}")
  Long id,
  
  @NotBlank(message = "{validation.nome.obrigatorio}")
  @Size(min = 3, max = 100, 
        message = "{validation.nome.tamanho}")
  String nome,
  
  @Pattern(regexp = "[A-Z]{3}", 
           message = "{validation.codigo.pattern}")
  String codigo
) {
  
  // Classes internas para tradução
  public static class VALIDATION {
    public static final String ID_OBRIGATORIO = 
      "validation.id.obrigatorio";
    public static final String NOME_OBRIGATORIO = 
      "validation.nome.obrigatorio";
    public static final String NOME_TAMANHO = 
      "validation.nome.tamanho";
    public static final String CODIGO_PATTERN = 
      "validation.codigo.pattern";
  }
}
```

### Validação

```java
// Notações de validação
@NotNull    // Não pode ser null
@NotBlank   // Não pode ser null ou vazio
@Size       // Tamanho (min/max)
@Pattern    // Expressão regular
@Min/@Max   // Valores numéricos
@Email      // Formato de email
@Digits     // Dígitos inteiros e fracionários
@Past/@Future  // Datas
@Valid      // Validação aninhada
```

---

## Padrões de Services

### Interface

```java
public interface EntidadeService {
  
  Optional<Entidade> findById(Long id);
  
  List<Entidade> findAll();
  
  Entidade save(EntidadeDTO dto);
  
  void delete(Long id);
  
  Page<Entidade> findAll(Pageable pageable);
}
```

### Implementação

```java
@Service
@Transactional
@RequiredArgsConstructor
public class EntidadeServiceImpl
  implements EntidadeService {
  
  private final EntidadeRepository repository;
  private final EntidadeMapper mapper;
  
  @Override
  public Optional<Entidade> findById(Long id) {
    return repository.findById(id);
  }
  
  @Override
  public List<Entidade> findAll() {
    return repository.findAll();
  }
  
  @Override
  public Entidade save(EntidadeDTO dto) {
    Entidade entity = mapper.toEntity(dto);
    return repository.save(entity);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Entidade> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
```

---

## Padrões de Repositories

### Interface Base

```java
public interface EntidadeRepository
  extends JpaRepository<Entidade, Long>,
          JpaSpecificationExecutor<Entidade> {
  
  // EntityGraph para evitar N+1
  @EntityGraph("Entidade.withRelacionamentos")
  Optional<Entidade> findByIdWithRelacionamentos(Long id);
  
  // Consultas customizadas
  List<Entidade> findByNomeContainingIgnoreCase(String nome);
  
  @Query("SELECT e FROM Entidade e WHERE e.ativo = true")
  List<Entidade> findAllAtivos();
  
  // Named Query
  @NamedQuery(name = "Entidade.findByStatus",
              query = "SELECT e FROM Entidade e WHERE e.status = :status")
  List<Entidade> findByStatus(@Param("status") Status status);
}
```

---

## Padrões REST

### Controller

```java
@RestController
@RequestMapping("/api/v1/entidades")
@RequiredArgsConstructor
@Tag(name = "Entidades", 
     description = "API de entidades")
public class EntidadeController {
  
  private final EntidadeService service;
  
  @GetMapping("/{id}")
  @Operation(summary = "Busca entidade por ID")
  public ResponseEntity<EntidadeDTO> findById(
    @PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
  
  @GetMapping
  @Operation(summary = "Lista todas as entidades")
  public Page<EntidadeDTO> findAll(
    @PageableDefault(size = 20) Pageable pageable) {
    return service.findAll(pageable)
      .map(EntidadeMapper::toDTO);
  }
  
  @PostMapping
  @Operation(summary = "Cria nova entidade")
  @ResponseStatus(HttpStatus.CREATED)
  public EntidadeDTO create(
    @Valid @RequestBody EntidadeDTO dto) {
    return service.save(dto);
  }
  
  @PutMapping("/{id}")
  @Operation(summary = "Atualiza entidade")
  public EntidadeDTO update(
    @PathVariable Long id,
    @Valid @RequestBody EntidadeDTO dto) {
    return service.update(id, dto);
  }
  
  @DeleteMapping("/{id}")
  @Operation(summary = "Remove entidade")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
```

---

## Padrões MVVM

### FormViewModel

```java
public class EntidadeFormViewModel
  extends FormViewModel<Entidade, EntidadeDTO> {
  
  private final EntidadeService service;
  
  @Inject
  public EntidadeFormViewModel(EntidadeService service) {
    this.service = service;
  }
  
  @Override
  protected EntidadeService getService() {
    return service;
  }
  
  @Override
  protected EntidadeDTO createEmptyDTO() {
    return new EntidadeDTO();
  }
  
  public void loadEntidade(Long id) {
    service.findById(id).ifPresent(entidade -> {
      setOriginal(toDTO(entidade));
      setEntity(entidade);
    });
  }
}
```

### FormView

```java
public class EntidadeFormView extends FormView {
  
  private final EntidadeFormViewModel viewModel;
  
  public EntidadeFormView(EntidadeFormViewModel viewModel) {
    this.viewModel = viewModel;
    initializeUI();
  }
  
  private void initializeUI() {
    // Configuração da interface
    setTitle("Cadastro de Entidade");
    setViewModel(viewModel);
  }
  
  @Override
  protected void onSave() {
    // Validação e salvamento
    viewModel.save();
  }
}
```

---

## Validação

### Hierarquia de Validação

```
DTO (Jakarta Validation)
    ↓
Translator.VALIDATION
    ↓
Properties i18n
```

### Exemplo

```java
// DTO
public record UsuarioDTO(
  @NotNull(message = "{validation.id.obrigatorio}")
  Long id,
  
  @NotBlank(message = "{validation.usuario.nome.obrigatorio}")
  @Size(min = 3, max = 50,
        message = "{validation.usuario.nome.tamanho}")
  String nome,
  
  @Email(message = "{validation.usuario.email.invalido}")
  String email
) {
  public static class VALIDATION {
    public static final String NOME_OBRIGATORIO = 
      "validation.usuario.nome.obrigatorio";
    public static final String NOME_TAMANHO = 
      "validation.usuario.nome.tamanho";
    public static final String EMAIL_INVALIDO = 
      "validation.usuario.email.invalido";
  }
}

// Translator
public class UsuarioTranslator {
  
  public static Usuario toEntity(UsuarioDTO dto) {
    validateDTO(dto, VALIDATION.class);
    // mapeamento
  }
  
  public static void validateDTO(UsuarioDTO dto, 
                                 Class<?> validationClass) {
    // validação
  }
}
```

---

## Internacionalização (i18n)

### Estrutura de Arquivos

```
src/main/resources/
└── i18n/
    ├── translations_{modulo}_{locale}.properties
    └── messages_{locale}.properties
```

### Formato de Chave

```
{componente}.{entidade}.{campo}.{tipo}

Exemplos:
validation.usuario.nome.obrigatorio
help.usuario.email
error.usuario.jaexiste
```

### Exemplo de Arquivo

```properties
# Mensagens de validação
validation.id.obrigatorio=O campo ID é obrigatório
validation.usuario.nome.obrigatorio=O nome é obrigatório
validation.usuario.nome.tamanho=O nome deve ter entre {min} e {max} caracteres
validation.usuario.email.invalido=Email inválido

# Mensagens de ajuda
help.usuario.codigo=Informe o código de identificação

# Mensagens de erro
error.usuario.jaexiste=Usuário já existe
error.usuario.naoencontrado=Usuário não encontrado
```

---

## 📚 Referências

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Spring Boot Best Practices](https://spring.io/projects/spring-boot)
- [Jakarta Validation](https://jakarta.ee/specifications/validation)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
