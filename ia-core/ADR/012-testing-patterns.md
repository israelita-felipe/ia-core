# ADR 012: Padrões de Teste Automatizado

## Status

**Proposto** | **Aceito** | **Deprecado**

## Contextualização

O projeto ia-core-apps precisa de uma estratégia consistente para testes automatizados que garanta qualidade, manutenibilidade e cobertura adequada do código. Atualmente, existem alguns testes básicos, mas falta uma abordagem padronizada.

## Decisões

### 1. Framework de Testes Principal

**Decisão**: Usar JUnit 5 como framework de assertions principal

**Justificativa**:
- JUnit 5 é a versão mais recente e moderna do JUnit
- Integração nativa com Spring Boot 3.x
- Suporte a testes parametrizados, dinâmicos e aninhados
- Extensibilidade através do modelo de extensões

**Alternativas Consideradas**:
- TestNG: Mais Features que JUnit 4, mas JUnit 5 é mais adotado no ecossistema Spring
- Spock: Excelente para BDD, mas curva de aprendizado maior

### 2. Framework de Asserções

**Decisão**: Usar AssertJ para assertions fluentes

**Justificativa**:
- API fluente e legível
- Mensagens de erro descritivas
- Grande variedade de assertions
- Integração com IDEs para autocompletar

**Exemplo**:
```java
assertThat(usuario)
    .isNotNull()
    .hasName("João")
    .hasAgeGreaterThan(18)
    .extracting(User::getEmail)
    .contains("joao@email.com");
```

### 3. Framework de Mocking

**Decisão**: Usar Mockito para criação de mocks e stubs

**Justificativa**:
- Padrão de mercado para Java
- Integração com JUnit 5 via `@ExtendWith(MockitoExtension.class)`
- API intuitiva e bem documentada
- Suporte a mockers sofisticados (inline mock maker)

**Exemplo**:
```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    
    @Mock
    private UsuarioRepository repository;
    
    @InjectMocks
    private UsuarioService service;
    
    @Test
    void deveBuscarUsuarioPorId() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        
        // When
        Usuario result = service.buscarPorId(1L);
        
        // Then
        assertThat(result).isNotNull();
        verify(repository).findById(1L);
    }
}
```

### 4. Testes de Integração com Banco de Dados

**Decisão**: Usar TestContainers para testes de integração com banco real

**Justificativa**:
- Banco de dados real durante testes
- Suporte a múltiplos bancos (PostgreSQL, MySQL, etc.)
- Isolamento entre testes
- Configuração via código

**Configuração**:
```java
@SpringBootTest
@Testcontainers
class UsuarioRepositoryIT {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }
}
```

### 5. Testes de API REST

**Decisão**: Usar MockMvc com JsonPath para testes de controllers

**Justificativa**:
- Não requer servidor HTTP real
- Configuração simples com `@AutoConfigureMockMvc`
- Verificações precisas de response
- Suporte a JSON Path para validações

**Exemplo**:
```java
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void deveRetornarUsuarioPorId() throws Exception {
        mockMvc.perform(get("/api/usuarios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nome").value("João"));
    }
}
```

### 6. Organização de Testes

**Decisão**: Separar testes unitários e de integração por sufixo e pacote

| Tipo | Sufixo | Pacote | Execução |
|------|--------|--------|----------|
| Unitário | `Test.java` | `src/test/java` | default |
| Integração | `IT.java` | `src/test/java` | `-Pintegration-tests` |
| E2E | `E2ETest.java` | `src/test/java` | manual |

### 7. Perfis de Teste

**Decisão**: Usar `@ActiveProfiles("test")` com configuração H2 para testes rápidos

**Perfil `test`**: H2 em memória (testes unitários e integração rápida)
**Perfil `testcontainers`**: PostgreSQL via container (testes de integração completos)

## Consequências

### Positivas
- ✅ Padronização de testes em todo o projeto
- ✅ Maior cobertura e qualidade de código
- ✅ Facilita refatorações com confiança
- ✅ Documentação viva do comportamento esperado
- ✅ Redução de bugs em produção

### Negativas
- ⚠️ Curva de aprendizado inicial
- ⚠️ Tempo adicional para escrever testes
- ⚠️ Manutenção dos testes junto com código

## Implementação

### Dependências Necessárias (ja incluso via spring-boot-starter-test)
- JUnit 5 (junit-jupiter)
- AssertJ (assertj-core)
- Mockito (mockito-core)
- JSON Path (json-path)

### Dependências Adicionais para Integração
```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.19.3</version>
    <scope>test</scope>
</dependency>
```

## Decisões Dependentes

Este ADR complementa e depende de:
- [ADR 010: Padrões de Nomenclatura](./010-nomenclature-standards.md) - Usa convenções de nomenclatura para testes
- [ADR 011: Padrões de Tratamento de Exceções](./011-exception-handling-patterns.md) - Testes de exceções seguem padrões definidos

## Revisões

| Versão | Data | Descrição |
|--------|------|-----------|
| 1.0 | 2024-01-15 | Versão inicial |

## Referências

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://mockito.org/)
- [AssertJ Core](https://assertj.github.io/doc/)
- [TestContainers](https://www.testcontainers.org/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html)
