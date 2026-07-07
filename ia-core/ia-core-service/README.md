# ia-core-service

## 📋 Descrição

Módulo principal de serviços contendo a lógica de negócio genérica do ia-core. Implementa serviços CRUD, tratamento de transações e operações comuns reutilizáveis por aplicações baseadas no framework.

## 🏗️ Estrutura

```
ia-core-service/
├── src/main/java/
│   └── com/ia/core/service/
│       ├── AbstractCrudService.java   # Serviço base CRUD
│       ├── impl/                      # Implementações de serviços
│       └── util/                      # Utilitários de serviço
└── pom.xml
```

## 🔑 Responsabilidades

- **CRUD Genérico**: Implementa operações Create, Read, Update, Delete padrão
- **Gerenciamento de Transações**: Controla transações com `@Transactional`
- **Tratamento de Exceções**: Lógica centralizada para exceções de negócio
- **Validações de Negócio**: Validações que vão além da entidade
- **Filtros e Paginação**: Suporte a buscas dinâmicas com paginação

## 🛠️ Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- Spring AOP (Aspect-Oriented Programming)
- Transações (Spring TX)
- Lombok

## 📦 Dependências

- `ia-core-model` - Herda entidades base
- `spring-boot-starter`
- `spring-boot-starter-data-jpa`

## 🔗 Relacionamentos

Depende de:
- `ia-core-model` - Para entidades e modelos

Utilizado por:
- `ia-core-rest` - Controllers REST delegam para serviços
- `ia-core-security-service` - Estende com lógica de segurança
- `ia-core-llm-service` - Estende com integração LLM
- Todos os serviços da camada de negócio

## 💡 Padrões Implementados

- **Service Layer Pattern**: Camada de serviço reutilizável
- **Template Method Pattern**: `AbstractCrudService` define fluxo
- **Single Responsibility**: Cada serviço com responsabilidade única
- **Dependency Injection**: Via Spring `@Service` e `@Autowired`

## 🚀 Como Usar

### Estender AbstractCrudService

```java
@Service
@Transactional
public class MinhaEntidadeService extends AbstractCrudService<MinhaEntidade, Long> {

    private final MinhaEntidadeRepository repository;

    public MinhaEntidadeService(MinhaEntidadeRepository repository) {
        super(repository);
        this.repository = repository;
    }

    // Métodos CRUD herdados: save, update, delete, findById, findAll

    // Adicionar lógica específica
    public List<MinhaEntidade> buscarPorCritério(String critério) {
        return repository.findByCritério(critério);
    }
}
```

### Usar em um Controller

```java
@RestController
@RequestMapping("/api/minha-entidade")
public class MinhaEntidadeController {

    @Autowired
    private MinhaEntidadeService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MinhaEntidadeDTO dto) {
        MinhaEntidade resultado = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        MinhaEntidade resultado = service.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontrada(id));
        return ResponseEntity.ok(resultado);
    }
}
```

## 📖 Métodos Principais

### AbstractCrudService

| Método | Descrição |
|--------|-----------|
| `save(T entity)` | Cria ou atualiza uma entidade |
| `update(T entity)` | Atualiza uma entidade existente |
| `delete(Long id)` | Deleta uma entidade por ID |
| `findById(Long id)` | Busca uma entidade por ID |
| `findAll()` | Lista todas as entidades |
| `findAll(Pageable)` | Lista com paginação |
| `findAll(Specification<T>)` | Busca com filtros dinâmicos |

## 🧪 Testes

Os testes estão em `src/test/java/` e cobrem:
- Operações CRUD básicas
- Transações
- Validações de negócio
- Casos de erro

**Tipos de Testes Aplicáveis** (conforme ADR-012):
- **Testes Unitários**: Services, Validators, Rules, Business Logic
- **Testes de Integração**: Repositories, Event Publishing
- **Testes de Transação**: `@Transactional` annotations

**Cobertura Mínima**: 85% (conforme ADR-012)

**Base Classes de Teste Disponíveis** (conforme ADR-012):
- `CoreServiceBase` - Para testes unitários de serviço (Mockito)
- `CoreIntegrationBase` - Para testes de integração (TestContainers)

**Execução de Testes com Cobertura**:
```bash
# Para testar um módulo individualmente
mvn test jacoco:report -pl ia-core-service

# Para testar todos os módulos
mvn test jacoco:report

# Verificar relatório
# target/site/jacoco/index.html
```

Exemplo de teste usando as base classes:

```java
@ExtendWith(MockitoExtension.class)
class MinhaEntidadeServiceTest extends CoreServiceBase {

    @Mock
    private MinhaEntidadeRepository repository;

    @InjectMocks
    private MinhaEntidadeService service;

    @Test
    void deveSalvarEntidadeComSucesso() {
        // Given
        MinhaEntidade entidade = createFixture(MinhaEntidade.class);
        entidade.setNome("Teste");
        when(repository.save(any())).thenReturn(entidade);

        // When
        MinhaEntidade salva = service.save(entidade);

        // Then
        assertThat(salva).isNotNull();
        assertThat(salva.getNome()).isEqualTo("Teste");
    }
}
```

## 🤝 Contribuição

Ao adicionar novos serviços:
1. Estenda `AbstractCrudService`
2. Injete as dependências necessárias
3. Use `@Transactional` para operações que modificam dados
4. Documente métodos com JavaDoc
5. Adicione testes unitários

## 📝 Notas

- Todos os serviços devem estar marcados com `@Service`
- Use `@Transactional(readOnly = true)` para consultas
- Lide com exceções apropriadamente, não deixe burilar para o controller

## 🔍 Referências

- [Spring Service Layer](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-introduction)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)


