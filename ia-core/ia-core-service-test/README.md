# ia-core-service-test

## Objetivo

Módulo de testes para camada de serviço contendo classes base para testes de serviços, repositórios e integração.

## Funcionalidades Principais

- `CoreServiceBase` - Base para testes unitários de serviço (Mockito)
- `CoreIntegrationBase` - Base para testes de integração (TestContainers PostgreSQL)
- `CoreAbstractRepositoryIT` - Base para testes de repositório com BD real
- Geração automática de dados usando Instancio

## Tipos de Testes Aplicáveis

| Tipo de Teste | Classe Base | Descrição |
|--------------|-------------|-----------|
| Testes Unitários de Serviço | `CoreServiceBase` | Testes com Mockito, sem contexto Spring |
| Testes de Integração | `CoreIntegrationBase` | Testes com PostgreSQL via TestContainers |
| Testes de Repository | `CoreAbstractRepositoryIT` | Testes de repositório com BD real |

## Dependências

```xml
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-service-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

## Uso

### Teste Unitário de Serviço

```java
class MyServiceTest extends CoreServiceBase {

    @Mock
    private MyRepository repository;

    @InjectMocks
    private MyService service;

    @Test
    void testServiceMethod() {
        // Teste com mock
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertThat(result).isNotNull();
        verify(repository).findById(1L);
    }
}
```

### Teste de Integração

```java
class MyRepositoryIT extends CoreIntegrationBase {

    @Autowired
    private MyRepository repository;

    @Test
    void testRepositoryMethod() {
        // Teste com BD real via TestContainers
        var entity = Instancio.create(MyEntity.class);
        var saved = repository.save(entity);

        assertThat(saved.getId()).isNotNull();
    }
}
```

## Classes Disponíveis

| Classe | Pacote | Descrição |
|--------|--------|-----------|
| `CoreServiceBase` | `com.ia.test.service` | Base para testes unitários de serviço (Mockito) |
| `CoreIntegrationBase` | `com.ia.test.service` | Base para testes de integração (TestContainers PostgreSQL) |
| `CoreAbstractRepositoryIT` | `com.ia.test.service` | Base para testes de repositório |

## ADRs Relevantes

- ADR-012: Testing Patterns
- ADR-018: Business Rule Chain Pattern
- ADR-019: Service Validator Pattern
