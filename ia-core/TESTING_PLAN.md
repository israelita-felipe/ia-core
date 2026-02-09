# FASE 6: Testes Unitários

## Objetivo
Implementar testes unitários com cobertura mínima de 70% para garantir qualidade e regressão.

---

## 6.1 Estratégia de Testes

### Pirâmide de Testes

```
        /\
       /  \
      /Integration\
     /--------------\
    /   Unit Tests  \
   /________________\
```

- **Unit Tests**: 70% - Testam classes isoladamente
- **Integration Tests**: 20% - Testam integração com banco
- **E2E Tests**: 10% - Testes de ponta a ponta

---

## 6.2 Dependências

```xml
<!-- JUnit 5 -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-engine</artifactId>
  <scope>test</scope>
</dependency>

<!-- Mockito -->
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-core</artifactId>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-junit-jupiter</artifactId>
  <scope>test</scope>
</dependency>

<!-- AssertJ -->
<dependency>
  <groupId>org.assertj</groupId>
  <artifactId>assertj-core</artifactId>
  <scope>test</scope>
</dependency>

<!-- Spring Test -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>

<!-- JaCoCo -->
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
</plugin>
```

---

## 6.3 Estrutura de Testes

```
src/test/java/com/ia/core/
├── {modulo}/
│   ├── service/
│   │   └── {Entidade}ServiceTest.java
│   ├── repository/
│   │   └── {Entidade}RepositoryTest.java
│   ├── rest/
│   │   └── {Entidade}ControllerTest.java
│   └── mapper/
│       └── {Entidade}MapperTest.java
└── util/
    └── AssertionUtils.java
```

---

## 6.4 Templates de Testes

### 6.4.1 Service Test

```java
@ExtendWith(MockitoExtension.class)
class {Entidade}ServiceTest {

  @Mock
  private {Entidade}Repository repository;

  @InjectMocks
  private {Entidade}Service service;

  @Test
  @DisplayName("Deve retornar entidade quando encontrada por ID")
  void deveRetornarEntidadeQuandoEncontrada() {
    // Given
    Long id = 1L;
    {Entidade} entidade = criarEntidade();
    when(repository.findById(id)).thenReturn(Optional.of(entidade));

    // When
    Optional<{Entidade}> result = service.findById(id);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
  }

  @Test
  @DisplayName("Deve retornar vazio quando entidade não encontrada")
  void deveRetornarVazioQuandoNaoEncontrada() {
    // Given
    Long id = 999L;
    when(repository.findById(id)).thenReturn(Optional.empty());

    // When
    Optional<{Entidade}> result = service.findById(id);

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Deve salvar entidade com sucesso")
  void deveSalvarEntidadeComSucesso() {
    // Given
    {Entidade}DTO dto = criarDTO();
    {Entidade} entidade = criarEntidade();
    when(repository.save(any({Entidade}.class))).thenReturn(entidade);

    // When
    {Entidade} result = service.save(dto);

    // Then
    assertThat(result).isNotNull();
    verify(repository).save(any({Entidade}.class));
  }

  @Test
  @DisplayName("Deve excluir entidade com sucesso")
  void deveExcluirEntidadeComSucesso() {
    // Given
    Long id = 1L;
    doNothing().when(repository).deleteById(id);

    // When
    service.delete(id);

    // Then
    verify(repository).deleteById(id);
  }

  @Test
  @DisplayName("Deve lançar exceção quando entidade não encontrada para atualização")
  void deveLancarExcecaoQuandoEntidadeNaoEncontrada() {
    // Given
    Long id = 999L;
    {Entidade}DTO dto = criarDTO();
    when(repository.existsById(id)).thenReturn(false);

    // When/Then
    assertThatThrownBy(() -> service.update(id, dto))
      .isInstanceOf(EntityNotFoundException.class)
      .hasMessageContaining("não encontrado");
  }

  private {Entidade} criarEntidade() {
    return {Entidade}.builder()
      .id(1L)
      .nome("Teste")
      .build();
  }

  private {Entidade}DTO criarDTO() {
    return new {Entidade}DTO(1L, "Teste");
  }
}
```

### 6.4.2 Mapper Test

```java
@ExtendWith(MockitoExtension.class)
class {Entidade}MapperTest {

  private {Entidade}Mapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new {Entidade}MapperImpl();
  }

  @Test
  @DisplayName("Deve converter DTO para entidade")
  void deveConverterDTOparaEntidade() {
    // Given
    {Entidade}DTO dto = new {Entidade}DTO(1L, "Teste");

    // When
    {Entidade} entity = mapper.toEntity(dto);

    // Then
    assertThat(entity.getId()).isEqualTo(dto.id());
    assertThat(entity.getNome()).isEqualTo(dto.nome());
  }

  @Test
  @DisplayName("Deve converter entidade para DTO")
  void deveConverterEntidadeParaDTO() {
    // Given
    {Entidade} entity = {Entidade}.builder()
      .id(1L)
      .nome("Teste")
      .build();

    // When
    {Entidade}DTO dto = mapper.toDTO(entity);

    // Then
    assertThat(dto.id()).isEqualTo(entity.getId());
    assertThat(dto.nome()).isEqualTo(entity.getNome());
  }

  @Test
  @DisplayName("Deve atualizar entidade com DTO")
  void deveAtualizarEntidadeComDTO() {
    // Given
    {Entidade} entity = {Entidade}.builder()
      .id(1L)
      .nome("Antigo")
      .build();
    {Entidade}DTO dto = new {Entidade}DTO(1L, "Novo");

    // When
    mapper.updateEntity(entity, dto);

    // Then
    assertThat(entity.getNome()).isEqualTo("Novo");
  }
}
```

### 6.4.3 Repository Test

```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class {Entidade}RepositoryTest {

  @Autowired
  private {Entidade}Repository repository;

  @Test
  @DisplayName("Deve encontrar entidade por nome")
  void deveEncontrarEntidadePorNome() {
    // Given
    String nome = "Teste";
    {Entidade} entidade = criarEntidade();
    repository.save(entidade);

    // When
    Optional<{Entidade}> result = repository.findByNome(nome);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().getNome()).isEqualTo(nome);
  }

  @Test
  @DisplayName("Deve verificar existência por ID")
  void deveVerificarExistenciaPorID() {
    // Given
    {Entidade} entidade = criarEntidade();
    {Entidade} saved = repository.save(entidade);

    // When
    boolean exists = repository.existsById(saved.getId());

    // Then
    assertThat(exists).isTrue();
  }

  private {Entidade} criarEntidade() {
    return {Entidade}.builder()
      .nome("Teste")
      .build();
  }
}
```

---

## 6.5 Configuração JaCoCo

```xml
<!-- pom.xml -->
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.10</version>
  <executions>
    <execution>
      <goals>
        <goal>prepare-agent</goal>
      </goals>
    </execution>
    <execution>
      <id>report</id>
      <phase>test</phase>
      <goals>
        <goal>report</goal>
      </goals>
    </execution>
    <execution>
      <id>check</id>
      <phase>verify</phase>
      <goals>
        <goal>check</goal>
      </goals>
      <configuration>
        <rules>
          <rule>
            <element>PACKAGE</element>
            <limits>
              <limit>
                <counter>LINE</counter>
                <value>COVEREDRATIO</value>
                <minimum>0.70</minimum>
              </limit>
            </limits>
          </rule>
        </rules>
      </configuration>
    </execution>
  </executions>
</plugin>
```

---

## 6.6 Classes de Teste Prioritárias

| Prioridade | Classe | Cobertura Mínima |
|------------|--------|------------------|
| ALTA | `ImageProcessingServiceTest` | 80% |
| ALTA | `TextExtractionServiceTest` | 80% |
| ALTA | `TemplateServiceTest` | 75% |
| ALTA | `ComandoSistemaServiceTest` | 75% |
| MÉDIA | `TemplateMapperTest` | 70% |
| MÉDIA | `ComandoSistemaMapperTest` | 70% |
| MÉDIA | `TemplateRepositoryTest` | 70% |
| BAIXA | `SchedulerConfigServiceTest` | 60% |

---

## 6.7 Execução de Testes

```bash
# Executar todos os testes
mvn test

# Executar com relatório de cobertura
mvn test jacoco:report

# Ver relatório de cobertura
open target/site/jacoco/index.html

# Executar teste específico
mvn test -Dtest=TemplateServiceTest

# Executar com Maven Surefire
mvn surefire:test
```

---

## 6.8 Métricas de Qualidade

| Métrica | Meta |
|---------|------|
| Cobertura de Linha | 70% |
| Cobertura de Branch | 60% |
| Complexidade Ciclomática | < 10 por método |
| Duplicação de Código | < 3% |
| Tests Passing | 100% |

---

## 6.9 Boas Práticas

### Nomeação de Testes

```java
// Padrão: Deve_[ação]_[resultado]
@Test
void deveSalvarEntidadeComSucesso()

@Test
void deveLancarExcecaoQuandoIdInvalido()

// Given-When-Then
@Test
void deveRetornarEntidadeQuandoEncontradaPorId()
```

### Arrange-Act-Assert

```java
@Test
void deveProcessarImagemComSucesso() {
  // Arrange
  byte[] input = criarImagemTeste();
  when(imageProcessor.process(any())).thenReturn(resultadoEsperado);

  // Act
  byte[] result = service.processImage(input);

  // Assert
  assertThat(result).isEqualTo(resultadoEsperado);
}
```

### Uso de Test Builders

```java
@Test
void deveCriarEntidadeComBuilder() {
  // Given
  Entidade entity = Entidade.builder()
    .id(1L)
    .nome("Teste")
    .build();

  // When/Then
  assertThat(entity.getNome()).isEqualTo("Teste");
}
```

---

## 6.10 Executar Cobertura

```bash
# Gerar relatório de cobertura
mvn clean test jacoco:report

# Verificar se atingiu a meta
mvn jacoco:check

# Relatório em HTML
open target/site/jacoco/index.html
```

---

## Próximos Passos

1. Criar estrutura de diretórios de testes
2. Implementar testes para `ImageProcessingService`
3. Implementar testes para `TextExtractionService`
4. Configurar JaCoCo no pom.xml
5. Executar e verificar cobertura
6. Implementar testes para outros serviços prioritários
