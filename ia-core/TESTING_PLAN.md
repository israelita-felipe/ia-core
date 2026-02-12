# Plano de Testes Automatizados - ia-core-apps

## 1. Visão Geral

Este documento define a estratégia de testes automatizados para o projeto ia-core-apps, garantindo qualidade, manutenibilidade e cobertura adequada do código.

### 1.1 Objetivos
- Garantir qualidade do código através de testes automatizados
- Facilitar refatorações com segurança
- Documentar comportamento esperado do sistema
- Reduzir regressões e bugs em produção

### 1.2 Pilares da Estratégia de Testes

```
        ┌─────────────────────────────────────┐
        │         PIRÂMIDE DE TESTES          │
        │                                     │
        │              ┌───┐                   │
        │             │ E2E│      → Poucos     │
        │            ┌┴───┴┐                  │
        │           │INTEGRA│    → Alguns      │
        │          ┌┴─────┴┐                  │
        │         │ UNITÁRIOS│   → Muitos      │
        │        └─────────┘                  │
        └─────────────────────────────────────┘
```

## 2. Tipos de Testes

### 2.1 Testes Unitários
**Objetivo**: Testar unidades isoladas de código
**Localização**: `src/test/java/**/*Test.java`
**Frameworks**: JUnit 5, Mockito, AssertJ

| Componente | Foco | Tempo Exec. |
|------------|------|-------------|
| Services | Lógica de negócio | < 100ms |
| Mappers | Conversão DTO↔Entity | < 50ms |
| Validators | Regras de validação | < 50ms |
| Utilities | Funções utilitárias | < 50ms |

### 2.2 Testes de Integração
**Objetivo**: Testar interação entre componentes
**Localização**: `src/test/java/**/*IT.java`
**Frameworks**: JUnit 5, Spring Test, TestContainers

| Componente | Foco | Tempo Exec. |
|------------|------|-------------|
| Repositories | CRUD com banco | < 1s |
| Services | Injeção de dependências | < 2s |
| Controllers | Endpoints REST | < 2s |

### 2.3 Testes End-to-End (E2E)
**Objetivo**: Testar fluxos completos da aplicação
**Localização**: `src/test/java/**/*E2ETest.java`
**Frameworks**: Selenium, TestContainers

## 3. Padrões e Convenções

### 3.1 Estrutura de Pacotes de Teste

```
src/test/java/com/ia/core/
├── llm/
│   ├── service/
│   │   ├── comando/
│   │   │   ├── ComandoSistemaServiceTest.java
│   │   │   └── ComandoSistemaRepositoryIT.java
│   │   └── template/
│   │       ├── TemplateServiceTest.java
│   │       └── TemplateMapperTest.java
│   └── controller/
│       ├── ComandoSistemaControllerTest.java
│       └── TemplateControllerTest.java
├── quartz/
│   └── service/
├── nlp/
│   └── service/
└── support/
    ├── AbstractServiceTest.java
    ├── AbstractRepositoryIT.java
    ├── TestDataFactory.java
    └── TestcontainersConfiguration.java
```

### 3.2 Convenções de Nomenclatura

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| Unit Test | `{Classe}Test.java` | `ComandoSistemaServiceTest.java` |
| Integration Test | `{Classe}IT.java` | `ComandoSistemaRepositoryIT.java` |
| E2E Test | `{Funcionalidade}E2ETest.java` | `ChatE2ETest.java` |
| Test Data Factory | `TestDataFactory.java` | `TestDataFactory.java` |
| Base Test Class | `Abstract{Tipo}Test.java` | `AbstractServiceTest.java` |

### 3.3 Estrutura de um Teste Unitário

```java
@ExtendWith(MockitoExtension.class)
class ComandoSistemaServiceTest {

    @Mock
    private ComandoSistemaRepository repository;

    @InjectMocks
    private ComandoSistemaService service;

    private TestDataFactory testData;

    @BeforeEach
    void setUp() {
        testData = new TestDataFactory();
    }

    @Test
    @DisplayName("Deve retornar comando por ID")
    void deveRetornarComandoPorId() {
        // Given
        Long id = 1L;
        ComandoSistema comando = testData.criarComandoSistema(id);
        when(repository.findById(id)).thenReturn(Optional.of(comando));

        // When
        ComandoSistema result = service.buscarPorId(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando comando não encontrado")
    void deveLancarExcecaoQuandoComandoNaoEncontrado() {
        // Given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.buscarPorId(id))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Comando não encontrado");
    }
}
```

### 3.4 Estrutura de um Teste de Integração

```java
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class ComandoSistemaRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ComandoSistemaRepository repository;

    private TestDataFactory testData;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        testData = new TestDataFactory();
    }

    @Test
    @DisplayName("Deve persistir comando com todas as informações")
    void devePersistirComandoComTodasInformacoes() {
        // Given
        ComandoSistema comando = testData.criarComandoSistema(1L);

        // When
        ComandoSistema salvo = repository.save(comando);

        // Then
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getTitulo()).isEqualTo(comando.getTitulo());
        assertThat(salvo.getDataCriacao()).isNotNull();
    }

    @Test
    @DisplayName("Deve encontrar comandos por finalidade")
    void deveEncontrarComandosPorFinalidade() {
        // Given
        FinalidadeComandoEnum finalidade = FinalidadeComandoEnum.RESPONSA;
        testData.criarComandoSistema(1L, finalidade);
        testData.criarComandoSistema(2L, finalidade);
        testData.criarComandoSistema(3L, FinalidadeComandoEnum.OUTRA);

        // When
        List<ComandoSistema> result = repository.findByFinalidade(finalidade);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(c -> c.getFinalidade() == finalidade);
    }
}
```

## 4. Ferramentas e Dependências

### 4.1 Dependências Principais

```xml
<!-- JUnit 5 (já incluso no spring-boot-starter-test) -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>

<!-- AssertJ (já incluso no spring-boot-starter-test) -->
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <scope>test</scope>
</dependency>

<!-- Mockito (já incluso no spring-boot-starter-test) -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>

<!-- TestContainers -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>${testcontainers.version}</version>
    <scope>test</scope>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- JSON Path para testes de API -->
<dependency>
    <groupId>com.jayway.jsonpath</groupId>
    <artifactId>json-path</artifactId>
    <scope>test</scope>
</dependency>
```

### 4.2 Configuração de Perfis de Teste

#### `src/test/resources/application-test.yml`
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: false
  h2:
    console:
      enabled: true

logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

#### `src/test/resources/application-testcontainers.yml`
```yaml
spring:
  datasource:
    url: jdbc:tc:postgresql:15:///testdb
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: create-drop
```

## 5. Métricas de Qualidade

### 5.1 Cobertura Mínima por Tipo

| Componente | Cobertura Mínima | Cobertura Alvo |
|------------|-------------------|----------------|
| Services | 80% | 90% |
| Repositories | 70% | 85% |
| Controllers | 60% | 75% |
| Mappers | 90% | 100% |
| Utilities | 90% | 100% |

### 5.2 Regras de Qualidade

```yaml
# jacoco-maven-plugin configuration
rules:
  - name: LineCoverage
    minimum: 0.80
    message: "Cobertura de linhas deve ser >= 80%"
  - name: BranchCoverage
    minimum: 0.70
    message: "Cobertura de branches deve ser >= 70%"
  - name: ClassCoverage
    minimum: 0.90
    message: "Cobertura de classes deve ser >= 90%"
  - name: MethodCoverage
    minimum: 0.95
    message: "Cobertura de métodos deve ser >= 95%"
  - name: PublicMethodCoverage
    minimum: 1.00
    message: "Métodos públicos devem ter 100% de cobertura"
```

## 6. Boas Práticas

### 6.1 AAA Pattern (Arrange-Act-Assert)

```java
@Test
void deveCalcularValorTotalComDesconto() {
    // Arrange
    Pedido pedido = new Pedido();
    pedido.setValorTotal(BigDecimal.valueOf(100));
    pedido.setDesconto(BigDecimal.valueOf(10));
    
    // Act
    BigDecimal resultado = servico.calcularValorTotal(pedido);
    
    // Then
    assertThat(resultado).isEqualTo(BigDecimal.valueOf(90));
}
```

### 6.2 Fluent Assertions

```java
// ✅ Bom - assertions fluentes
assertThat(usuario)
    .isNotNull()
    .hasName("João")
    .hasAgeGreaterThan(18)
    .extracting(User::getEmail)
    .isNotEmpty();

// ❌ Evitar
assertNotNull(usuario);
assertEquals("João", usuario.getName());
assertTrue(usuario.getAge() > 18);
```

### 6.3 Test Data Builders

```java
class TestDataFactory {
    
    public ComandoSistema criarComandoSistema(Long id) {
        return ComandoSistema.builder()
            .id(id)
            .titulo("Test Command " + id)
            .comando("Test content")
            .finalidade(FinalidadeComandoEnum.RESPONSA)
            .exigeContexto(false)
            .build();
    }
    
    public ComandoSistema criarComandoSistema(Long id, FinalidadeComandoEnum finalidade) {
        ComandoSistema comando = criarComandoSistema(id);
        comando.setFinalidade(finalidade);
        return comando;
    }
}
```

### 6.4 Testes Determinísticos

```java
// ✅ Bom - usar Instant.fixed para datas
@Test
void deveValidarDataDeCriacao() {
    Instant fixedInstant = Instant.parse("2024-01-01T00:00:00Z");
    try (MockedStatic<Instant> mocked = mockStatic(Instant.class)) {
        mocked.when(Instant::now).thenReturn(fixedInstant);
        
        Entity entity = new Entity();
        entity.criar();
        
        assertThat(entity.getDataCriacao()).isEqualTo(fixedInstant);
    }
}
```

### 6.5 Evitar Testes Frágeis

```java
// ❌ Evitar - dependência de ordem
@Test
void deveSalvarNaOrdemCorreta() {
    // ...
}

// ✅ Bom - independentes
@Test
void deveSalvarEntidade() {
    // ...
}

@Test
void deveValidarConstraints() {
    // ...
}
```

## 7. Configuração CI/CD

### 7.1 GitHub Actions Workflow

```yaml
name: Tests

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Run Unit Tests
        run: mvn test -Dtest.groups=unit
      - name: Upload Coverage
        uses: codecov/codecov-action@v3
        with:
          files: ./target/site/jacoco/jacoco.xml

  integration-tests:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_USER: test
          POSTGRES_PASSWORD: test
          POSTGRES_DB: testdb
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Run Integration Tests
        run: mvn verify -Pintegration-tests
        env:
          SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/testdb
```

### 7.2 Maven Configuration

```xml
<profiles>
    <profile>
        <id>unit-tests</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <test.groups>unit</test.groups>
        </properties>
    </profile>
    <profile>
        <id>integration-tests</id>
        <properties>
            <test.groups>integration</test.groups>
        </properties>
        <dependencies>
            <dependency>
                <groupId>org.testcontainers</groupId>
                <artifactId>junit-jupiter</artifactId>
                <version>1.19.3</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

## 8. Checklist de Testes

### 8.1 Para Cada Novo Recurso
- [ ] Testes unitários para lógica de negócio
- [ ] Testes de integração para acesso a dados
- [ ] Testes para validações
- [ ] Testes para tratamento de exceções
- [ ] Testes paraedge cases

### 8.2 Para Cada Correção de Bug
- [ ] Criar teste que reproduza o bug
- [ ] Verificar que o teste falha antes da correção
- [ ] Aplicar correção
- [ ] Verificar que o teste passa após correção

## 9. Próximos Passos

1. [ ] Configurar TestContainers para PostgreSQL
2. [ ] Criar classes base de testes
3. [ ] Implementar TestDataFactory
4. [ ] Criar testes para serviços existentes
5. [ ] Configurar JaCoCo com regras personalizadas
6. [ ] Configurar SonarQube para análise de qualidade
7. [ ] Documentar exemplos de testes para cada tipo de componente

## Referências

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://mockito.org/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [TestContainers](https://www.testcontainers.org/)
- [Arquitetura de Testes - Martin Fowler](https://martinfowler.com/articles/practical-test-pyramid.html)
