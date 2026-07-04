# ia-core-rest

## 📋 Descrição

Módulo que fornece o servidor REST da aplicação. Implementa controllers REST que expõem os serviços da camada de negócio como APIs HTTP, seguindo padrões RESTful.

## 🏗️ Estrutura

```
ia-core-rest/
├── src/main/java/
│   └── com/ia/core/rest/
│       ├── controller/             # Controllers REST
│       ├── dto/                    # Data Transfer Objects
│       ├── config/                 # Configurações Spring
│       └── exception/              # Tratamento de exceções
├── src/main/resources/
│   └── application*.yml            # Configurações
└── pom.xml
```

## 🔑 Responsabilidades

- **REST Controllers**: Definem endpoints HTTP para CRUD e operações específicas
- **Validação de Input**: Valida dados recebidos do cliente
- **Serialização JSON**: Converte entidades para JSON e vice-versa
- **Tratamento de Erros HTTP**: Retorna códigos de status apropriados
- **Documentação OpenAPI**: Suporte a Swagger/OpenAPI 3.0
- **Configuração Spring**: Beans e configurações do servlet

## 🛠️ Tecnologias Utilizadas

- Spring Boot Web Starter
- Spring Security (para proteção de endpoints)
- Jackson (serialização JSON)
- Validation (validação de input)
- Springdoc OpenAPI (Swagger/Javadoc)
- MapStruct (mapeamento DTO ↔ Entity)

## 📦 Dependências

- `ia-core-service` - Acessa serviços de negócio
- `ia-core-security-service` - Para autenticação/autorização
- `spring-boot-starter-web`
- `spring-security-core`
- `springdoc-openapi-starter-webmvc-ui`

## 🔗 Relacionamentos

Depende de:
- `ia-core-service` - Delega operações de negócio
- `ia-core-security-service` - Autentica e autoriza requisições

Utilizado por:
- Clientes HTTP (postman, navegadores, aplicações frontend)
- Integrações externas via API

## 💡 Padrões Implementados

- **MVC Pattern**: Model-View-Controller via Spring MVC
- **REST Principles**: Resources, HTTP methods, status codes
- **DTO Pattern**: Transferência de dados sem expor entidades
- **Exception Translation**: Exceções de negócio para HTTP

## 🚀 Como Usar

### Criar um Controller REST

```java
@RestController
@RequestMapping("/api/${api.version}/minhas-entidades")
@Tag(name = "Minha Entidade", description = "Operações de Minha Entidade")
public class MinhaEntidadeController {

    @Autowired
    private MinhaEntidadeService service;

    @Autowired
    private MinhaEntidadeMapper mapper;

    @PostMapping
    @Operation(summary = "Cria uma nova entidade")
    public ResponseEntity<MinhaEntidadeDTO> criar(
            @Valid @RequestBody CriarMinhaEntidadeDTO dto) {
        MinhaEntidade entidade = mapper.toEntity(dto);
        MinhaEntidade salva = service.save(entidade);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.toDTO(salva));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma entidade por ID")
    public ResponseEntity<MinhaEntidadeDTO> buscar(@PathVariable Long id) {
        MinhaEntidade entidade = service.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontrada(id));
        return ResponseEntity.ok(mapper.toDTO(entidade));
    }

    @GetMapping
    @Operation(summary = "Lista entidades com filtros opcionais")
    public ResponseEntity<Page<MinhaEntidadeDTO>> listar(
            @ParameterObject Pageable pageable,
            @ParameterObject MinhaEntidadeFilter filter) {
        Page<MinhaEntidade> resultado = service.findAll(pageable, filter);
        return ResponseEntity.ok(
            resultado.map(mapper::toDTO)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma entidade existente")
    public ResponseEntity<MinhaEntidadeDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarMinhaEntidadeDTO dto) {
        MinhaEntidade entidade = service.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontrada(id));
        mapper.updateEntity(dto, entidade);
        MinhaEntidade atualizada = service.save(entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma entidade")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Criar um DTO

```java
@Data
@Builder
public class MinhaEntidadeDTO {

    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    private LocalDateTime criadoEm;

    private String criadoPor;
}
```

### Usar MapStruct para mapeamento

```java
@Mapper(componentModel = "spring")
public interface MinhaEntidadeMapper {

    MinhaEntidadeDTO toDTO(MinhaEntidade entity);

    MinhaEntidade toEntity(CriarMinhaEntidadeDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    void updateEntity(AtualizarMinhaEntidadeDTO dto, @MappingTarget MinhaEntidade entity);
}
```

## 📋 Padrões de Resposta

### Sucesso (200, 201, 204)

```json
{
    "id": 1,
    "nome": "Exemplo",
    "email": "exemplo@test.com",
    "criadoEm": "2025-05-15T10:30:00Z",
    "criadoPor": "usuario1"
}
```

### Erro (4xx, 5xx)

```json
{
    "status": 400,
    "message": "Validação falhou",
    "errors": [
        {
            "field": "email",
            "message": "Email inválido"
        }
    ]
}
```

## 🔐 Segurança

- Use `@PreAuthorize` para proteção em métodos
- Implemente CORS se necessário
- Valide entrada com `@Valid`
- Use HTTPS em produção

```java
@PostMapping("/admin-only")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> operacaoAdmin() {
    // ...
}
```

## 🧪 Testes

Testes em `src/test/java/`:

```java
@WebMvcTest(MinhaEntidadeController.class)
public class MinhaEntidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MinhaEntidadeService service;

    @Test
    public void testBuscar() throws Exception {
        MinhaEntidade entidade = new MinhaEntidade();
        entidade.setId(1L);
        entidade.setNome("Teste");

        when(service.findById(1L)).thenReturn(Optional.of(entidade));

        mockMvc.perform(get("/api/${api.version}/minhas-entidades/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Teste"));
    }
}
```

## 📖 Configuração

### application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

springdoc:
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
  api-docs:
    path: /v3/api-docs
```

## 🚀 Endpoints Padrão

| Método | Caminho | Descrição |
|--------|---------|-----------|
| `POST` | `/api/${api.version}/recurso` | Cria novo |
| `GET` | `/api/${api.version}/recurso/{id}` | Busca por ID |
| `GET` | `/api/${api.version}/recurso` | Lista com paginação |
| `PUT` | `/api/${api.version}/recurso/{id}` | Atualiza |
| `DELETE` | `/api/${api.version}/recurso/{id}` | Deleta |
| `GET` | `/swagger-ui.html` | Documentação Swagger |

## 🤝 Contribuição

Ao adicionar novo endpoints:
1. Use `@RestController` e `@RequestMapping`
2. Documente com `@Operation` e `@Tag`
3. Valide entrada com `@Valid`
4. Use DTOs para resposta
5. Retorne status HTTP apropriados
6. Adicione testes com MockMvc

## 🔍 Referências

- [Spring Web MVC](https://spring.io/projects/spring-framework)
- [OpenAPI/Swagger](https://springdoc.org/)
- [RESTful API Best Practices](https://restfulapi.net/)


