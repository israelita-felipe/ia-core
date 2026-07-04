# ADR 012: Padrões de Teste Automatizado

## Status

**Aceito**

## Contextualização

O projeto ia-core-apps precisa de uma estratégia consistente para testes automatizados que garanta qualidade, manutenibilidade e cobertura adequada do código. Atualmente, existem alguns testes básicos, mas falta uma abordagem padronizada.

## Decisões

### 1. Framework de Testes Principal

**Decisão**: Usar JUnit 5 como framework de assertions principal

**Justificativa**:
- JUnit 5 é a versão mais recente e moderna do JUnit
- Integração nativa com Spring Boot 4.x
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

**Decisão**: Testes devem seguir a convenção Java padrão, localizados em `src/test/java` dentro de cada módulo de produção

**Estrutura de Diretórios**:
- Cada módulo de produção deve ter seu diretório `src/test/java` para testes
- Testes são organizados seguindo a estrutura de pacotes da classe testada
- Exemplo: `ia-core-security-service/src/test/java/com/ia/core/security/`

| Tipo | Sufixo | Localização | Execução |
|------|--------|-------------|----------|
| Unitário | `Test.java` | `src/test/java` | default |
| Integração | `IT.java` | `src/test/java` | `-Pintegration-tests` |
| E2E | `E2ETest.java` | `src/test/java` | manual |

**Módulo ia-core-test**:
- Contém apenas classes base para testes (BaseUnitTest, BaseIntegrationTest, etc.)
- Não contém testes de domínio específicos
- Fornece infraestrutura reutilizável para todos os projetos

### 6.1. Classes Base de Teste (Abstract Test Base Classes)

**Decisão**: Classes base de teste devem seguir nomenclatura específica e serem sinalizadas para não serem executadas como testes

**Justificativa**:
- Classes base de teste são classes abstratas que fornecem infraestrutura comum para testes concretos
- Elas não contêm testes executáveis, apenas configuração e métodos utilitários
- Maven Surefire tenta executar qualquer classe que termine com "Test.java" por padrão
- Isso causa confusão nos relatórios de execução e pode indicar falsos positivos/negativos
- Nomenclatura clara diferencia classes base de testes reais

**Nomenclatura de Classes Base**:

**Padrão Recomendado**: Usar sufixo `Base` ou `Abstract` em vez de `Test`

| Tipo Atual | Nomenclatura Recomendada | Exemplo |
|------------|-------------------------|---------|
| CoreBaseServiceTest | CoreServiceBase | CoreServiceBase |
| CoreBaseAPITest | CoreAPIBase | CoreAPIBase |
| CoreBaseIntegrationTest | CoreIntegrationBase | CoreIntegrationBase |
| CoreBaseE2ETest | CoreE2EBase | CoreE2EBase |
| CoreBaseVaadinViewTest | CoreVaadinViewBase | CoreVaadinViewBase |
| CoreBaseVaadinManagerTest | CoreVaadinManagerBase | CoreVaadinManagerBase |
| AbstractBaseServiceTest | AbstractServiceBase | AbstractServiceBase |
| BaseServiceTest | ServiceBase | ServiceBase |

**Regras de Nomenclatura**:
1. **Sufixo `Base`**: Para classes base concretas que podem ser instanciadas
2. **Sufixo `Abstract`**: Para classes abstratas que não podem ser instanciadas
3. **Prefixo `Core`**: Para classes base compartilhadas entre múltiplos módulos (em ia-core-test)
4. **Sem sufixo `Test`**: Para evitar que Maven Surefire tente executá-las

**Sinalização de Não-Execução**:

**Opção 1: Anotação @Disabled (Recomendado)**
```java
@Disabled("Abstract base class - not a test")
@DisplayName("Service Base")
public abstract class CoreServiceBase extends CoreBaseUnitTest {
    // Infrastructure and common test setup
}
```

**Opção 2: Nomenclatura que não corresponde ao padrão do Maven Surefire**
- Usar sufixos como `Base`, `Abstract`, `Fixture`, `Template`
- Maven Surefire por padrão busca: `**/Test*.java`, `**/*Test.java`, `**/*Tests.java`, `**/*TestCase.java`
- Classes com sufixo `Base.java` não serão executadas automaticamente

**Opção 3: Configuração do Maven Surefire (Exclusão explícita)**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <excludes>
            <exclude>**/*Base.java</exclude>
            <exclude>**/Abstract*.java</exclude>
        </excludes>
    </configuration>
</plugin>
```

**Recomendação Combinada**:
1. Usar nomenclatura com sufixo `Base` ou `Abstract`
2. Adicionar anotação `@Disabled` com mensagem explicativa
3. Documentar claramente no Javadoc que é uma classe base

**Exemplo Completo**:
```java
/**
 * Base class for service layer tests.
 * Provides common functionality and configuration for service tests.
 * Uses Mockito for mocking dependencies and isolating the service layer.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * <p>Characteristics:
 * - Mockito extension for mocking
 * - No Spring context (pure unit tests)
 * - Service layer isolation
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @ExtendWith(MockitoExtension.class)
 * class MyServiceTest extends CoreServiceBase {
 *     @Mock
 *     private MyRepository repository;
 *
 *     @InjectMocks
 *     private MyService service;
 *
 *     @Test
 *     void testServiceMethod() {
 *         // Your test logic
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@Disabled("Abstract base class - not a test")
@ExtendWith(MockitoExtension.class)
@DisplayName("Service Base")
public abstract class CoreServiceBase extends CoreBaseUnitTest {
    // Infrastructure and common test setup
}
```

**Benefícios da Nomenclatura Correta**:
- **Clareza**: Diferencia imediatamente classes base de testes reais
- **Evita Execução Indevida**: Maven não tenta executar classes base
- **Relatórios Limpos**: Relatórios de teste mostram apenas testes reais
- **Manutenibilidade**: Fácil identificar onde adicionar novos testes
- **Consistência**: Padrão uniforme em todos os módulos

### 6.1. Organização de Test-Cases

**Decisão**: Documentos de casos de teste (test-cases) devem ficar em `src/test/resources/test-cases` dentro de cada módulo de produção

**Estrutura de Diretórios**:
- Cada módulo de produção deve ter seu diretório `src/test/resources/test-cases` para documentos de casos de teste
- Cada módulo é responsável por manter seus próprios test-cases
- Exemplo: `ia-core-security-service/src/test/resources/test-cases/`

**Responsabilidade**:
- Cada módulo de produção deve manter seus test-cases junto com seus testes
- Isso facilita a manutenção e rastreabilidade entre testes e casos de teste
- Segue o princípio de coesão: testes e documentação de testes ficam próximos do código testado

**Padronização de Nomes**:
- Use o padrão: `<NomeDaClasse>-<Camada>-<Cenário>.md`
- Exemplo: `FlywayExecution-Service-Layer-migracao-sucesso.md`
- Caso o teste cubra múltiplos cenários da mesma classe e camada, use `<NomeDaClasse>-<Camada>.md`

### 7. Perfis de Teste

**Decisão**: Usar `@ActiveProfiles("test")` com configuração H2 para testes rápidos

**Perfil `test`**: H2 em memória (testes unitários e integração rápida)
**Perfil `testcontainers`**: PostgreSQL via container (testes de integração completos)

### 8. Testes para Componentes LLM e Spring AI

**Decisão**: Adotar estratégia em três níveis para testes de componentes LLM

**Nível 1: Testes Unitários com Mocking**
- Mock do `ChatModel` para testar engenharia de prompt e parsers de saída
- Verificar construção correta de prompts
- Testar lógica de negócio sem chamadas reais à API
- Usar `@MockBean` para mock do ChatModel em contexto Spring

**Nível 2: Testes de Integração Local com Testcontainers**
- Usar Testcontainers com Ollama para rodar LLM real (mas pequeno) localmente
- Testar fluxo completo com modelo real
- Evitar dependência de APIs externas (OpenAI, Anthropic)
- Configurar via `@Testcontainers` e `@DynamicPropertySource`

**Nível 3: Testes de Integração RAG (Retrieval Augmented Generation)**
- Spin up de bancos de dados vetoriais (PgVector) em containers
- Verificar precisão de recuperação
- Testar fluxo completo de RAG com dados reais

**Desafios Específicos de Testes LLM**
- **Não-determinismo**: O mesmo prompt pode gerar resultados diferentes
- **Latência**: Chamadas reais podem levar 5-10 segundos
- **Custo**: APIs externas consomem recursos financeiros
- **Rate Limits**: APIs externas podem limitar execução paralela

**Estratégia de Asserção Semântica**
1. **Presença de Palavras-chave** (Fraco): Verificar termos específicos na resposta
2. **Similaridade Semântica** (Melhor): Usar embeddings para calcular similaridade
3. **LLM-as-a-Judge** (Avançado): Usar outro LLM para avaliar a qualidade da resposta

**Exemplo de Teste Unitário LLM**:
```java
@SpringBootTest
class SentimentServiceUnitTests {
    @MockBean
    private ChatModel chatModel;

    @Autowired
    private SentimentService sentimentService;

    @Test
    void testSentimentAnalysisReturnsPositive() {
        // 1. Preparar resposta mock
        String expectedResponse = "POSITIVE";
        ChatResponse mockResponse = new ChatResponse(
            List.of(new Generation(new AssistantMessage(expectedResponse)))
        );

        // 2. Definir comportamento
        given(chatModel.call(any(Prompt.class))).willReturn(mockResponse);

        // 3. Executar
        String result = sentimentService.analyze("I love using Spring Boot!");

        // 4. Assert
        assertThat(result).isEqualTo("POSITIVE");

        // 5. Verificar construção do prompt
        ArgumentCaptor<Prompt> promptCaptor = ArgumentCaptor.forClass(Prompt.class);
        verify(chatModel).call(promptCaptor.capture());
        String actualPrompt = promptCaptor.getValue().getContents();
        assertThat(actualPrompt).contains("Classify sentiment");
    }
}
```

**Exemplo de Teste de Integração com Ollama**:
```java
@SpringBootTest
@Testcontainers
class LLMIntegrationTest {

    @Container
    static OllamaContainer ollama = new OllamaContainer("ollama/ollama:latest")
        .withModel("llama2");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.ai.ollama.base-url", ollama::getEndpoint);
    }

    @Test
    void testLLMIntegrationWithRealModel() {
        // Testa com modelo real local
    }
}
```

### 9. Padrões avançados para documentos de casos de teste

**Tipos de testes que podem ser aplicados**:
- **Testes Unitários**: Testam unidades isoladas de código (métodos, classes) com mocks de dependências.
- **Testes de Integração**: Verificam a interação entre múltiplos componentes ou com recursos externos (banco, mensagem, APIs).
- **Testes de Sistema (E2E)**: Validam fluxos completos de ponta a ponta, simulando uso real do sistema.
- **Testes de Property-Based**: Utilizam bibliotecas como jqwik para gerar entradas aleatórias e validar invariáveis.
- **Testes de Mutação**: Avaliam a qualidade do suite de testes introduzindo falhas intencionais (mutantes) e verificando se são detectados.
- **Testes de Performance**: Medem latência, throughput e uso de recursos sob carga.
- **Testes de Segurança**: Verificam vulnerabilidades como injeção, quebra de autenticação, exposição de dados sensíveis.
- **Testes Funcionais**: Validam se o sistema funciona conforme os requisitos funcionais especificados.
- **Testes de Aceitação (UAT)**: Validam se o sistema atende às expectativas do usuário final.
- **Testes de Regressão**: Garantem que mudanças no código não quebraram funcionalidades existentes.
- **Testes de Carga**: Avaliam o comportamento do sistema sob carga esperada.
- **Testes de Stress**: Avaliam os limites do sistema sob carga extrema.
- **Testes de Usabilidade**: Avaliam a facilidade de uso e experiência do usuário.
- **Testes de Compatibilidade**: Verificam o funcionamento em diferentes plataformas/browsers.
- **Smoke Testing**: Testes rápidos para validar que os componentes principais funcionam.

**Padronização dos nomes de arquivos para casos de teste**:
- Use o padrão: `<NomeDaClasse>-<Camada>-<Cenário>.md`
  - `<NomeDaClasse>`: Nome da classe sendo testada (sem sufixos como Service, Controller, etc., opcional).
  - `<Camada>`: Camada ou responsabilidade testada (ex: Service-Layer, Controller-API, Repository-Persistence).
  - `<Cenário>`: Descrição curta do cenário de teste em kebab-case (ex: validacao-campos-obrigatorios, fluxo-sucesso, tratamento-excecao).
- Exemplo: `FlywayExecution-Service-Layer-migracao-sucesso.md`
- Caso o teste cubra múltiplos cenários da mesma classe e camada, use `<NomeDaClasse>-<Camada>.md` e descreva múltiplos cenários dentro do documento.

**Como achar classes e métodos para testar**:
1. **Identificar pontos de entrada públicos**: Métodos públicos de classes são candidatos naturais para teste.
2. **Analisar complexidade ciclomática**: Métodos com alta complexidade (muitos ramificações) requerem mais casos de teste.
3. **Buscar condições de fronteira**: Valores mínimos, máximos, vazios, nulos, valores especiais.
4. **Caminhos de exceção**: Identificar onde exceções podem ser lançadas e testar seu tratamento.
5. **Requisitos de negócio**: Mapear histórias de uso ou requisitos funcionais para fluxos que devem ser testados.
6. **Cobertura de código**: Use ferramentas de cobertura (JaCoCo) para identificar linhas não testadas e complementar casos.
7. **Revisão de código**: Durante code review, peça ao autor para indicar pontos críticos que necessitam de teste.
8. **Padrões de projeto**: Em classes que implementam padrões (Strategy, Factory, Observer), teste as variações e interações esperadas.

### 12. Bibliotecas de Teste Adicionais e Recomendadas

**AssertJ - Recursos Avançados**:
- **Comparação Recursiva**: Compara objetos campo a campo, útil para comparar entidades com DTOs
  ```java
  assertThat(entity).usingRecursiveComparison().isEqualTo(dto);
  ```
- **Soft Assertions**: Coleta múltiplas falhas em vez de parar na primeira
  ```java
  SoftAssertions.assertSoftly(soft -> {
      soft.assertThat(name).isEqualTo("Expected");
      soft.assertThat(age).isGreaterThan(18);
  });
  ```
- **Custom Assertions**: Crie suas próprias validações específicas de domínio
- **Suporte a Tipos Modernos**: Optional, Streams, Predicates, tipos temporais Java 8, tipos atômicos

**Rest-Assured - Testes de API REST**:
- DSL fluente baseado em given-when-then para testes de API
- Integração com AssertJ para assertions
- Suporte a extração de resposta para validações adicionais
- Pode ser combinado com openapi-generator para gerar clients automaticamente

**Awaitility - Testes Assíncronos**:
- Simplifica testes de código assíncrono
- Espera por condições com timeout configurável
- Sintaxe fluente e legível

**WireMock - Mock de Serviços Externos**:
- Simula serviços HTTP externos
- Configuração via código ou arquivos stub
- Útil para testes de integração sem dependências externas

**PiTest (PIT) - Testes de Mutação**:
- Introduz mutações no código para avaliar qualidade dos testes
- Identifica testes que não detectam falhas introduzidas
- Gera relatórios de覆盖率 de mutação

### 13. Melhores Práticas para Spring Boot Testing

**Não Reinventar a Roda**:
- Use ferramentas pré-construídas do Spring Boot (TestRestTemplate, TestEntityManager)
- Aproveite o ecossistema Java de testes maduro
- Pesquise soluções existentes antes de criar utilitários customizados

**Use Test Slices para Isolar Testes**:
- `@WebMvcTest`: Testa apenas camada MVC (controllers, filters, views)
- `@DataJpaTest`: Testa apenas camada de persistência JPA
- `@JsonTest`: Testa serialização/deserialização JSON
- `@WebFluxTest`: Testa controllers WebFlux
- `@ServiceTest`: Testa apenas serviços (Spring Boot 4.0+)
- Reduz tempo de execução carregando apenas componentes necessários

**Use Spring TestContext Caching**:
- Contexto Spring é criado uma vez e reutilizado entre testes
- Evite `@DirtiesContext` desnecessário que invalida o cache
- Configurações idênticas compartilham o mesmo contexto
- Pode reduzir tempo de build em até 50%

**Organização de Testes Spring Boot**:
- Testes unitários: use mocks, sem contexto Spring completo
- Testes de integração: use test slices ou `@SpringBootTest` com perfil test
- Testes E2E: use `@SpringBootTest` com contexto completo ou TestContainers

### 14. Testes para Aplicações Vaadin

**Distinção entre Componentes Vaadin**:
- **Vaadin Views (@Route)**: Componentes de UI com anotação `@Route` que contêm elementos visuais (Grid, FormLayout, Button, etc.)
- **Vaadin Managers (@Service)**: Beans Spring que gerenciam lógica de negócio na camada de view, sem componentes UI

**Abordagens de Teste**:
- **Browserless Testing**: Testa componentes server-side sem navegador real
  - Gratuito para todos os usuários
  - Execução rápida
  - Testa lógica de componentes e interações
  - Usa Karibu-Testing ou framework similar

- **End-to-End Testing**: Testa aplicação completa com navegador real
  - Requer assinatura comercial TestBench
  - Testa JavaScript, Polymer/Lit, client-side
  - Simula interações reais do usuário
  - Pode usar Selenium ou Playwright como alternativas

**Diferenças entre Ferramentas**:
- **TestBench**: Solução oficial Vaadin, recursos específicos para componentes Vaadin
- **Selenium**: Framework genérico, limitações com componentes Vaadin específicos
- **Playwright**: Alternativa moderna, suporte a múltiplos navegadores

**Recomendação**:
- Priorize browserless testing para testes unitários e de integração de componentes
- Use E2E testing para fluxos críticos de usuário que requerem navegador real
- Combine ambas abordagens para cobertura completa

### 14.1. Testes de Vaadin Views (@Route Components)

**Framework**: SpringUIUnitTest (JUnit 5)

**Justificativa**:
- Integração nativa com Spring Testing Framework
- Suporta injeção de dependências via Spring
- Gerencia ciclo de vida de UI e navegação automaticamente
- Não requer navegador real (browserless testing)

**Configuração**:
```java
@ExtendWith(SpringExtension.class)
class MyViewTest extends SpringUIUnitTest {

    @Override
    protected SpringUIUnitTestRouteRegistry getRouteRegistry() {
        return new SpringUIUnitTestRouteRegistry();
    }

    @Test
    void testViewInitialization() {
        // Arrange
        MyView view = navigate(MyView.class);

        // Act & Assert
        assertThat(view).isNotNull();
    }
}
```

**Anotações Spring**:
- `@ContextConfiguration`: Para ApplicationContext reduzido (startup mais rápido)
- `@SpringBootTest`: Para cenários complexos com contexto completo

**Exemplo com @ContextConfiguration**:
```java
@ContextConfiguration(classes = ViewTestConfig.class)
class ViewTest extends SpringUIUnitTest {

    @Configuration
    static class ViewTestConfig {
        @Bean
        MyService myService() {
            return new TestingMyService();
        }
    }
}
```

**Exemplo com @SpringBootTest**:
```java
@SpringBootTest
class ViewTest extends SpringUIUnitTest {
    // Usa contexto completo da aplicação
}
```

### 14.2. Testes de Vaadin Managers (@Service Beans)

**Framework**: Mockito com JUnit 5

**Justificativa**:
- Managers são Spring @Service beans, não componentes UI
- Não possuem @Route annotation ou componentes visuais
- São lógica de negócio que delega para service clients
- Testes unitários com Mockito são mais rápidos e focados

**Configuração**:
```java
@ExtendWith(MockitoExtension.class)
class AgenciaManagerTest {

    @Mock
    private AgenciaServiceConfig serviceConfig;

    @InjectMocks
    private AgenciaManager manager;

    @Test
    void deveRetornarNomeDoTipoDeFuncionalidade() {
        // Act
        String result = manager.getFunctionalityTypeName();

        // Assert
        assertThat(result).isEqualTo(AgenciaTranslator.AGENCIA);
    }
}
```

**Quando usar SpringUIUnitTest vs Mockito**:
- **SpringUIUnitTest**: Para Views com @Route annotation que contêm componentes UI
- **Mockito**: Para Managers @Service que são lógica de negócio sem componentes UI

### 14.3. Testes de DTOs - Verificação de CAMPOS

**Decisão**: Toda classe que implementa DTO<?> deve possuir uma inner class estática CAMPOS que lista todos os campos da classe DTO como constantes estáticas públicas, onde o valor de cada constante é o nome do campo em camelCase.

**Regra de Implementação de DTO**:
- Toda classe com sufixo *DTO deve implementar diretamente ou indiretamente a interface DTO<?>
- Quando o *DTO é relativo a uma entidade do banco de dados (estende AbstractBaseEntityDTO), deve implementar DTO<EntityType> onde EntityType é a entidade JPA correspondente
- Quando o *DTO não é relativo a uma entidade do banco de dados (DTOs de requisição, resposta, ou utilitários), deve implementar DTO<Serializable>

**Justificativa**:
- A inner class CAMPOS fornece acesso type-safe aos nomes dos campos da DTO
- Garante que todos os campos da DTO tenham uma constante correspondente
- Evita erros de digitação ou desincronização entre constantes e campos
- Facilita refatorações mantendo a consistência
- Permite uso em reflection, filtros dinâmicos, e binding de componentes UI
- O valor da constante (String) corresponde ao nome do campo em camelCase, facilitando uso em reflection
- Quando o DTO estende AbstractBaseEntityDTO, a inner class CAMPOS deve estender AbstractBaseEntityDTO.CAMPOS para herdar os campos base (id, version, etc.)
- O método values() permite obter todos os nomes de campos de forma programática

**Padrão da Inner Class CAMPOS**:
```java
public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String NOME_CAMPO_1 = "nomeCampo1";
    public static final String NOME_CAMPO_2 = "nomeCampo2";

    public static Set<String> values() {
        return Set.of(NOME_CAMPO_1, NOME_CAMPO_2);
    }
}
```

**Toda innerclass CAMPOS deve estender AbstractBaseEntityDTO**:

**Uso em Setters com firePropertyChange**:
```java
public void setNome(String nome) {
    firePropertyChange(CAMPOS.NOME, this.nome, nome);
    this.nome = nome;
}
```

**Implementação do Teste**:
```java
@Test
@DisplayName("Deve verificar que CAMPOS corresponde aos campos da classe")
void deveVerificarQueCAMPOSCorrespondeAosCamposDaClasse() {
    // Arrange - obtém todos os campos da classe DTO, incluindo campos herdados
    Set<String> allFieldNames = getAllFieldNames(MinhaDTO.class);

    // Act - obtém todos os valores das constantes estáticas em CAMPOS, incluindo herdados
    Set<String> camposFieldValues = getAllCamposValues(MinhaDTO.CAMPOS.class);

    // Assert
    assertThat(camposFieldValues).containsAll(allFieldNames);
}

/**
 * Obtém todos os nomes de campos da classe DTO, incluindo campos herdados.
 */
private Set<String> getAllFieldNames(Class<?> clazz) {
    Set<String> fieldNames = new HashSet<>();
    Class<?> currentClass = clazz;

    while (currentClass != null && currentClass != Object.class) {
        for (Field field : currentClass.getDeclaredFields()) {
            // Ignorar campos estáticos
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldNames.add(field.getName());
            }
        }
        currentClass = currentClass.getSuperclass();
    }

    return fieldNames;
}

/**
 * Obtém todos os valores das constantes estáticas em CAMPOS, incluindo herdados.
 */
private Set<String> getAllCamposValues(Class<?> camposClass) {
    Set<String> values = new HashSet<>();
    Class<?> currentClass = camposClass;

    while (currentClass != null && currentClass != Object.class) {
        for (Field field : currentClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) &&
                Modifier.isFinal(field.getModifiers())) {
                try {
                    Object value = field.get(null);
                    if (value instanceof String) {
                        values.add((String) value);
                    }
                } catch (IllegalAccessException e) {
                    // Ignorar campos não acessíveis
                }
            }
        }
        currentClass = currentClass.getSuperclass();
    }

    return values;
}
```

**Nota**: O BaseDTOUnitTest já implementa essa lógica automaticamente, então testes de DTO que estendem BaseDTOUnitTest não precisam implementar este teste manualmente.

### 14.5. Testes de DTOs - BaseDTOUnitTest

**Decisão**: Toda classe de teste de DTO deve extender BaseDTOUnitTest passando como parâmetro o tipo do DTO testado

**Justificativa**:
- BaseDTOUnitTest fornece testes automáticos para verificação da innerclass CAMPOS
- Elimina código boilerplate repetitivo em testes de DTO
- Garante consistência na verificação de CAMPOS em todos os DTOs
- Facilita criação de fixtures usando Instancio através do método createFixture()

**Implementação**:
```java
@DisplayName("Testes de MinhaDTO")
class MinhaDTOTest extends BaseDTOUnitTest<MinhaDTO> {

    @Test
    @DisplayName("Deve criar DTO com campos obrigatórios")
    void deveCriarDTOComCamposObrigatorios() {
        MinhaDTO dto = MinhaDTO.builder()
            .campoObrigatorio("valor")
            .build();

        assertThat(dto).isNotNull();
        assertThat(dto.getCampoObrigatorio()).isEqualTo("valor");
    }

    // Outros testes específicos do DTO...
}
```

**Benefícios do BaseDTOUnitTest**:
- Teste automático de verificação da innerclass CAMPOS
- Método createDTO() para criar instâncias do DTO usando fixtures
- Método getDtoClass() para obter a classe do DTO via reflexão
- Integração com BaseUnitTest para funcionalidades adicionais

**Quando Não Usar BaseDTOUnitTest**:
- Para DTOs que não possuem innerclass CAMPOS (DTOs simples ou de resposta)
- Para testes de integração que não seguem o padrão unitário
- Para DTOs que requerem configuração específica não coberta pelo BaseDTOUnitTest

### 14.4. Testes de DTOs - Getters e Setters

**Decisão**: Não testar getters e setters gerados automaticamente por Lombok ou frameworks similares, a menos que contenham processamento extra além da simples atribuição/retorno de valor

**Justificativa**:
- Getters e setters gerados automaticamente (Lombok @Data, @Getter, @Setter) não têm lógica de negócio
- Testar métodos triviais de atribuição/retorno de valor não agrega valor à qualidade do código
- Aumenta manutenção desnecessária da suíte de testes
- Distorce a métrica de cobertura de código sem benefício real
- Foca esforço em testar comportamento de negócio, não infraestrutura de dados

**Quando Testar Getters/Setters**:
- Quando o getter contém lógica de negócio (ex: formatação, cálculo, validação)
- Quando o setter contém validação ou efeitos colaterais
- Quando há lógica condicional no getter/setter
- Quando o método não é um simples getter/setter (ex: `getFormattedName()`, `setValidatedValue()`)

**Referências de Apoio**:
- **Referência 69 - On testing in DDD (Towards Dev)**: Aborda os desafios de testar código rico em DDD e oferece padrões como Test-Arranger para focar em lógica de negócio relevante
- **Referência 108 - Java Spring Boot Best Practice (Continue.dev)**: Recomenda sempre escrever testes unitários para métodos públicos com JUnit 5, focando em comportamento de negócio
- **Referência 135 - LoBREST: Log-based Business-aware REST API Testing (arXiv 2026)**: Framework de teste consciente de regras de negócio, utilizando logs para criar casos que testam cenários complexos de negócio
- **Referência 150 - Automating System Test Case Classification and Prioritization (arXiv)**: Abordagem automatizada para classificar e priorizar casos de teste no contexto de desenvolvimento orientado a casos de uso
- **Referência 151 - Test-First Protocol from Use Cases (UGM Journal)**: Protocolo estruturado e test-first para transformar especificações de casos de uso em cenários de teste orientados por cobertura de negócio

**Exemplo de Getter com Lógica (Deve Ser Testado)**:
```java
public String getFormattedTelefone() {
    if (telefone == null) {
        return "";
    }
    return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
}
```

**Exemplo de Setter com Validação (Deve Ser Testado)**:
```java
public void setEmail(String email) {
    if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        throw new IllegalArgumentException("Email inválido");
    }
    this.email = email;
}
```

**Exemplo de Getter/Setter Trivial (Não Deve Ser Testado)**:
```java
@Data
public class UsuarioDTO {
    private String nome;
    private Integer idade;
    // Lombok gera getters/setters triviais automaticamente
    // Não é necessário testar getNome(), setNome(), getIdade(), setIdade()
}
```

### 15. Refatoração do Módulo de Testes

**Decisão**: Renomear `ia-core-integration-test` para `ia-core-test` e atualizar todas as referências (COMPLETADO)

**Justificativa**:
- O nome atual sugere apenas testes de integração, mas o módulo contém todos os tipos de testes
- Nome mais genérico reflete melhor o propósito do módulo
- Facilita compreensão da estrutura do projeto

**Ações Necessárias**:
1. Renomear o diretório do módulo
2. Atualizar referências no `pom.xml` do projeto pai
3. Atualizar referências em outros módulos que dependem deste
4. Atualizar documentação e README

### 16. Implementações Padrão CRUD

**Decisão**: Criar implementações padrão de interface para operações CRUD seguindo o padrão *Service já implementado

**Justificativa**:
- Reduz código duplicado em serviços e controllers
- Padroniza operações CRUD em todo o projeto
- Facilita manutenção e consistência

**Operações CRUD Padrão**:
- `find`: Buscar um registro por ID
- `findAll`: Listar todos os registros com paginação
- `save`: Salvar (criar ou atualizar) um registro
- `delete`: Deletar um registro por ID
- `count`: Contar os registros

**Assinaturas de Métodos**:
```java
// Para objetos que podem ser interpretados genericamente
default Page<T> findAll(SearchRequest request) { }
default T find(ID id) { }
default T save(T entity) { }
default void delete(ID id) { }
default int count(SearchRequest request){}

// Para objetos que requerem criação customizada
T createObject();
```

### 17. Implementações CRUD para Controllers

**Decisão**: Criar implementações padrão CRUD para camada de controller

**Justificativa**:
- Consistência em endpoints REST
- Reduz código boilerplate
- Facilita criação de novos controllers

**Endpoints Padrão**:
- `GET /api/{resource}/{id}`: Buscar por ID
- `GET /api/{resource}`: Listar com paginação
- `POST /api/{resource}`: Criar
- `PUT /api/{resource}/{id}`: Atualizar
- `DELETE /api/{resource}/{id}`: Deletar

### 18. Implementações Base para Tipos de Teste

**Decisão**: Criar implementações base para todos os tipos de teste listados neste ADR

**Tipos de Teste com Classes Base**:
- **Unit Test**: `BaseUnitTest` com configuração Mockito comum
- **Integration Test**: `BaseIntegrationTest` com @SpringBootTest e TestContainers
- **E2E Test**: `BaseE2ETest` com configuração de navegador
- **API Test**: `BaseAPITest` com MockMvc configurado
- **Service Test**: `BaseServiceTest` com @ServiceTest
- **Repository Test**: `BaseRepositoryTest` com @DataJpaTest
- **LLM Test**: `BaseLLMTest` com mock de ChatModel
- **Vaadin View Test**: `BaseVaadinViewTest` extendendo SpringUIUnitTest
- **Vaadin Manager Test**: `BaseVaadinManagerTest` com Mockito

### 19. Uso de Cucumber para Testes Descritivos (BDD)

**Decisão**: Usar Cucumber para testes descritivos integrados com CDUs - ADOPTADO

**Justificativa**:
- Testes em linguagem natural facilitam comunicação com stakeholders
- Documentação viva dos requisitos
- Melhor rastreabilidade entre requisitos e testes
- Integração direta com CDUs (ADR-053) para documentação de casos de uso
- Feature files podem ser gerados a partir dos fluxos do CDU

**Dependências Maven**:
```xml
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-spring</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
```

**Configuração**:
- Usar `@CucumberContextConfiguration` com `@SpringBootTest` para integração Spring
- Criar classe base `BaseCucumberTest` em `ia-core-test`
- Feature files em `src/test/resources/features` com extensão `.feature`
- Step definitions em classes que extendem `BaseCucumberTest`

**Integração com CDUs (ADR-053)**:
- Feature files devem ser criados a partir dos fluxos documentados nos CDUs
- Cada CDU pode ter um ou mais feature files correspondentes
- Fluxos do CDU (Given-When-Then) são mapeados diretamente para scenarios do Cucumber
- Regras de negócio do CDU (RN001, RN002, etc.) devem ser validadas nos cenários

**Estrutura de Feature Files**:
```gherkin
Feature: Gerenciamento de Agências
  CDU: CDU001-GerenciamentoAgencias

  Scenario: Criar nova agência
    Given estou na tela de agências
    When preencho os dados da agência com nome "Agência Central"
    And clico em salvar
    Then a agência deve ser criada com sucesso
    And devo ver a agência na lista
    And a agência deve ter status "ATIVO"

  Scenario: Validar agência com nome duplicado
    Given existe uma agência com nome "Agência Central"
    When tento criar uma nova agência com nome "Agência Central"
    Then o sistema deve exibir mensagem de erro "Nome de agência já existe"
    And a agência não deve ser criada
```

**Implementação de Step Definitions**:
```java
@CucumberContextConfiguration
@SpringBootTest
public class GerenciamentoAgenciasStepDefinitions extends BaseCucumberTest {

    @Autowired
    private AgenciaService agenciaService;

    @Given("estou na tela de agências")
    public void estouNaTelaDeAgencias() {
        // Implementação do step
    }

    @When("preencho os dados da agência com nome {string}")
    public void preenchoOsDadosDaAgencia(String nome) {
        // Implementação do step
    }

    @Then("a agência deve ser criada com sucesso")
    public void aAgenciaDeveSerCriadaComSucesso() {
        // Implementação do step
    }
}
```

**Implementação**:
- Classe base criada: `com.ia.test.BaseCucumberTest`
- Suporte a hooks `@Before` e `@After` para setup/cleanup
- Integração com Spring Boot Test context
- Perfil de teste ativo por padrão

**Quando Usar Cucumber**:
- Para casos de uso complexos que exigem documentação em linguagem natural
- Quando há necessidade de comunicação com stakeholders não técnicos
- Para fluxos de negócio que precisam ser documentados como requisitos
- Para testes de aceitação que validam requisitos funcionais

**Quando Não Usar Cucumber**:
- Para testes unitários de classes simples
- Para testes de validação de DTOs e Entities
- Para testes que não representam fluxos de negócio
- Quando a equipe é puramente técnica e não precisa de documentação em linguagem natural

### 20. Consideração de CDU e Comentários de Método

**Decisão**: Considerar domínio CDU e comentários de método ao implementar casos de teste e classes de teste - COMPLETADO

**Justificativa**:
- CDUs definem o domínio e contexto de negócio
- Comentários de método documentam comportamento esperado
- Testes devem refletir o domínio de negócio documentado

**Implementação**:
- Ler CDU correspondente antes de implementar testes
- Usar terminologia do CDU nos nomes de teste
- Validar que testes cobrem cenários descritos no CDU
- Documentar em testes referências a métodos específicos do CDU

**Práticas Recomendadas**:
1. **Nomenclatura de Testes**: Usar terminologia de negócio do CDU nos nomes dos métodos de teste
   - Exemplo: `testCriarAgenciaComSucesso()` em vez de `testSave()`
   - Exemplo: `testValidarCPFInvalido()` em vez de `testValidation()`

2. **Documentação em Testes**: Adicionar comentários Javadoc nos testes referenciando o CDU
   ```java
   /**
    * Testa criação de agência conforme CDU-001: Gestão de Agências
    * Referência: método criarAgencia() do CDU
    */
   @Test
   void testCriarAgenciaComSucesso() { }
   ```

3. **Cobertura de Cenários**: Validar que todos os cenários do CDU estão cobertos por testes
   - Criar checklist de cenários do CDU
   - Mapear cada cenário para um ou mais testes
   - Documentar gaps de cobertura

4. **Terminologia Consistente**: Usar os mesmos termos do CDU em:
   - Nomes de variáveis de teste
   - Mensagens de assertion
   - Comentários explicativos

### 15. Estratégia de Pirâmide de Testes

**Princípios Gerais**:
- **Base da Pirâmide (70%)**: Testes unitários rápidos e isolados
- **Camada do Meio (20%)**: Testes de integração de componentes
- **Topo da Pirâmide (10%)**: Testes E2E lentos e caros

**Benefícios da Estratégia**:
- Feedback rápido com testes unitários
- Detecção precoce de bugs
- Menor custo de manutenção
- Testes E2E focados em caminhos críticos

**Métricas Recomendadas**:
- Tempo de execução de testes unitários: < 5 minutos
- Tempo de execução de testes de integração: < 15 minutos
- Tempo de execução de testes E2E: < 30 minutos
- Cobertura de código: **mínima de 85%**

### 16. Cobertura de Código por Tipo de Módulo e Camada

**Decisão**: Adotar metas de cobertura de código diferenciadas por tipo de módulo e camada da aplicação, baseadas em melhores práticas da indústria e criticidade do código.

**Justificativa**:
- Diferentes camadas e tipos de código têm diferentes níveis de criticidade e complexidade
- DTOs simples, enums sem lógica, e translators de constantes não beneficiam de alta cobertura
- Lógica de negócio (domain layer) requer cobertura máxima para prevenir bugs críticos
- Código de infraestrutura e framework pode ter cobertura menor sem comprometer qualidade
- Metas realistas por tipo reduzem esforço desperdiçado sem sacrificar qualidade
- Baseado em pesquisas de melhores práticas: bITdevKit, Learnixo, Spring Boot Testing Guidelines

### 16.1. Metas de Cobertura por Tipo de Módulo

**Módulos Model (ia-core-*-model)**:
- **DTOs com lógica de negócio**: 70-80% (validações, conversões, transformações)
- **DTOs simples (apenas getters/setters Lombok)**: 0-50% (testar apenas se houver lógica extra)
- **Entities JPA**: 70-80% (métodos de negócio, validações)
- **Enums com lógica**: 80-90% (métodos de negócio)
- **Enums simples (constantes apenas)**: 0-50% (testar apenas se houver comportamento)
- **Translators (constantes)**: 0-50% (testar apenas construtor privado e valores críticos)
- **Interfaces**: 0% (não requerem teste)
- **Cobertura Global do Módulo**: 60-70%

**Módulos Service (ia-core-*-service)**:
- **Services com lógica de negócio**: 90-95% (crítico para regras de negócio)
- **Validators**: 85-90% (validações são críticas)
- **Business Rules**: 90-95% (regras de negócio são críticas)
- **Specifications**: 80-90% (lógica de query)
- **Repositories**: 70-80% (foco em comportamentos customizados, não JPA padrão)
- **Event Handlers**: 80-90% (lógica de eventos)
- **Cobertura Global do Módulo**: 80-85%

**Módulos REST (ia-core-*-rest)**:
- **Controllers**: 70-80% (endpoint mapping, validações de entrada)
- **Filters**: 80-90% (lógica de filtro é crítica)
- **Exception Handlers**: 85-90% (tratamento de erros é crítico)
- **DTOs de Request/Response**: 50-70% (validações Jakarta Validation)
- **Cobertura Global do Módulo**: 70-75%

**Módulos View (ia-core-*-view)**:
- **Managers (@Service)**: 80-90% (lógica de negócio de view)
- **Views (@Route)**: 70-80% (componentes UI, interações)
- **Converters**: 80-90% (lógica de conversão)
- **Validators de UI**: 85-90% (validações de formulário)
- **Cobertura Global do Módulo**: 75-80%

**Módulos Test (ia-core-test)**:
- **Classes base de teste**: 80-90% (infraestrutura de teste é crítica)
- **Cobertura Global do Módulo**: 80-85%

### 16.2. O Que Não Testar (Exceções à Cobertura)

**Não requerem teste unitário**:
- Getters e setters gerados automaticamente por Lombok (@Data, @Getter, @Setter)
- DTOs simples sem lógica de negócio (aplos campos e getters/setters)
- Enums que são apenas constantes (sem métodos de comportamento)
- Translators que contêm apenas constantes estáticas
- Interfaces sem implementação padrão
- Classes anônimas
- Código gerado automaticamente (Lombok, MapStruct, etc.)
- Framework code (configurações Spring, JPA, etc.)
- Delegação trivial (método A chama método B sem lógica adicional)

**Quando testar mesmo assim**:
- Getters/setters com lógica extra (formatação, validação, cálculo)
- DTOs com validações Jakarta Validation
- DTOs com métodos de negócio (cloneObject, toBuilder, etc.)
- Enums com métodos de comportamento
- Translators com lógica dinâmica

### 16.3. Metas de Cobertura por Camada (Arquitetura DDD)

**Domain Layer (Entidades, Value Objects, Domain Services)**:
- **Cobertura Alvo**: 95%+
- **Justificativa**: Contém lógica de negócio crítica, onde bugs são mais caros
- **Foco**: Regras de negócio, invariantes, validações de domínio

**Application Layer (Use Cases, Application Services)**:
- **Cobertura Alvo**: 90%+
- **Justificativa**: Orquestra casos de uso, coordena componentes
- **Foco**: Fluxos de uso, orquestração, validações de aplicação

**Presentation Layer (Controllers, Views, DTOs)**:
- **Cobertura Alvo**: 70-80%
- **Justificativa**: Mapeamento de HTTP/UI, menos lógica crítica
- **Foco**: Validações de entrada, mapeamento, tratamento de erros

**Infrastructure Layer (Repositories, External Services)**:
- **Cobertura Alvo**: 70-75%
- **Justificativa**: Código de infraestrutura, configurações
- **Foco**: Comportamentos customizados, adapters, não código gerado

### 16.4. Cobertura Global do Projeto

**Métricas de Cobertura**:
- **Cobertura Mínima Global**: 75-80%
- **Cobertura Alvo Global**: 80-85%
- **Cobertura de Mutação (PIT)**: 70-80% (quando aplicável)

**Justificativa**:
- 75-80% global é suficiente para qualidade sem esforço excessivo
- Foco em cobertura significativa, não apenas números
- Camadas críticas (domain, application) têm metas mais altas
- Camadas de infraestrutura têm metas mais realistas

**Referências de Apoio**:
- **bITdevKit**: Domain 95%+, Application 90%+, Presentation 85%+, Infrastructure 70%+
- **Learnixo**: 70-80% cobertura significativa, não 100% a qualquer custo
- **Spring Boot Testing Guidelines**: Testing Honeycomb com foco em testes significativos
- **Milan Jovanovic**: Foco em lógica de domínio, não em seams (controllers, repositories)

### 16.5. Geração de Classes de Teste

**Decisão**: Gerar classes de teste para maximizar a cobertura de código significativo e executar sempre os testes com medição de cobertura.

**Justificativa**:
- A cobertura mínima diferenciada por tipo cria critérios objetivos de qualidade realistas
- A geração de classes de teste a partir dos documentos de casos de teste mantém rastreabilidade entre requisito, cenário e implementação automatizada.
- A execução dos testes com cobertura em todas as validações evita que a suíte seja executada sem evidências mensuráveis de abrangência.
- O ciclo de feedback baseado em cobertura direciona a criação de novos cenários para linhas, branches e condições ainda não exercitadas.
- Testes por módulo permitem validação rápida e isolada de componentes.

**Processo obrigatório**:
1. Para cada classe ou comportamento testável implementado, avaliar o tipo de módulo e camada para definir meta de cobertura apropriada.
2. Gerar uma classe de teste correspondente, preferencialmente no mesmo caminho relativo de pacote sob `src/test/java`.
3. Mapear cada cenário descrito nos documentos de casos de teste para um ou mais métodos de teste na respectiva classe de teste.
4. Executar a suíte de testes sempre com cobertura habilitada, usando JaCoCo ou ferramenta equivalente configurada no build.
5. Enquanto a cobertura total ficar abaixo da meta definida para o tipo de módulo, novos cenários de teste devem ser implementados.
6. Priorizar cenários que cubram linhas, branches, condições de fronteira, caminhos de exceção e regras de negócio ainda não cobertas.
7. Para DTOs simples, enums sem lógica, e translators de constantes, considerar cobertura reduzida ou dispensar testes se não houver comportamento significativo.

**Execução de Testes com Cobertura por Módulo**:
- Para testar um módulo individualmente: `mvn test jacoco:report -pl <nome-do-modulo>`
- Para testar todos os módulos: `mvn test jacoco:report`
- Verificar relatório de cobertura em `target/site/jacoco/index.html`
- Adicionar testes adicionais se cobertura abaixo da meta do tipo de módulo
- Para DTOs simples, translators e enums sem lógica, aceitar cobertura menor

**Configuração do JaCoCo**:
- O plugin jacoco-maven-plugin já está configurado no pom.xml pai
- Execução automática na fase `verify` do Maven
- Relatório gerado em `target/site/jacoco/index.html`
- Cobertura mínima global: 75%
- Configuração atual no pom.xml pai:
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>verify</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Critério de aceite**:
- A cobertura total do módulo afetado deve ser maior ou igual à meta definida para o tipo de módulo.
- Qualquer cenário identificado como necessário para atingir a cobertura mínima deve estar documentado e implementado na classe de teste correspondente.
- O módulo não deve ser considerado completo até que a cobertura mínima seja atingida, exceto para tipos onde cobertura reduzida é aceitável (DTOs simples, translators, enums sem lógica).

### 21. Padrão Fixture para Geração de Dados de Teste

**Decisão**: Usar Instancio para geração automática de dados de teste (fixture pattern)

**Justificativa**:
- Elimina código boilerplate na criação de objetos de teste
- Gera objetos completamente populados com dados aleatórios e reprodutíveis
- Suporta customização via selectors, set(), supply(), generate()
- Integração nativa com JUnit 5 via `@InstancioSource` para testes parametrizados
- Model permite criar templates reutilizáveis para objetos de teste
- Reduz manutenção de fixtures manuais

**Instalação**:
```xml
<dependency>
    <groupId>org.instancio</groupId>
    <artifactId>instancio-junit</artifactId>
    <version>4.0.0</version>
    <scope>test</scope>
</dependency>
```

**Uso Básico**:
```java
// Criar objeto simples
Student student = Instancio.create(Student.class);

// Criar lista
List<Student> students = Instancio.ofList(Student.class).size(10).create();

// Criar stream
Stream<Student> stream = Instancio.of(Student.class).stream().limit(10);
```

**Customização**:
```java
// Usando set() para valores específicos
Student student = Instancio.of(Student.class)
    .set(field(Student::getName), "João")
    .set(field(Student::getAge), 25)
    .create();

// Usando supply() para valores gerados
Student student = Instancio.of(Student.class)
    .supply(field(Student::getDateOfBirth), random -> LocalDate.now().minusYears(18 + random.intRange(0, 60)))
    .create();

// Usando generate() para valores complexos
Student student = Instancio.of(Student.class)
    .generate(field(ContactInfo::getEmail), gen -> gen.text().pattern("#a#a#a#a#a#[[email protected]]"))
    .create();
```

**Model (Template Reutilizável)**:
```java
Model<Student> studentModel = Instancio.of(Student.class)
    .generate(field(Student::getDateOfBirth), gen -> gen.temporal().localDate().past())
    .generate(field(Student::getEmail), gen -> gen.text().pattern("#a#a#a#a#a#[[email protected]]"))
    .toModel();

// Usar o modelo em múltiplos testes
Student student = Instancio.of(studentModel)
    .set(field(Student::getName), "Maria")
    .create();
```

**Testes Parametrizados**:
```java
@InstancioSource
@ParameterizedTest
void testWithGeneratedData(Student student) {
    assertThat(student).isNotNull();
    assertThat(student.getName()).isNotNull();
    assertThat(student.getAge()).isPositive();
}
```

**Integração com Classes Base**:
Adicionar métodos utilitários nas classes base de teste:
```java
public abstract class BaseUnitTest {
    protected <T> T createFixture(Class<T> type) {
        return Instancio.create(type);
    }

    protected <T> List<T> createFixtures(Class<T> type, int size) {
        return Instancio.ofList(type).size(size).create();
    }
}
```

**Referência**: [Instancio Documentation](https://www.instancio.org/user-guide/)

### 22. Testes para Filtros e SearchRequest

**Decisão**: Criar testes específicos para filtros e SearchRequestDTO

**Justificativa**:
- Filtros são componentes críticos para consultas dinâmicas
- SearchRequestDTO é usado em todos os endpoints de listagem
- Erros em filtros podem causar problemas de performance e segurança
- Validação de filtros previne injeção de SQL e outros ataques

**Tipos de Testes para Filtros**:

**1. Teste de Construção de Filtro**:
```java
@Test
void deveConstruirFiltroComSucesso() {
    // Arrange
    FilterDTO filter = new FilterDTO();
    filter.setKey("nome");
    filter.setOperator(Operator.EQUALS);
    filter.setValue("João");

    // Assert
    assertThat(filter.getKey()).isEqualTo("nome");
    assertThat(filter.getOperator()).isEqualTo(Operator.EQUALS);
    assertThat(filter.getValue()).isEqualTo("João");
}
```

**2. Teste de Validação de Filtro**:
```java
@Test
void deveValidarFiltroComChaveNula() {
    // Arrange
    FilterDTO filter = new FilterDTO();
    filter.setOperator(Operator.EQUALS);
    filter.setValue("valor");

    // Act & Assert
    assertThatThrownBy(() -> searchRequestMapper.toModel(filter))
        .isInstanceOf(ValidationException.class);
}
```

**3. Teste de SearchRequest Completo**:
```java
@Test
void deveConstruirSearchRequestComFiltros() {
    // Arrange
    SearchRequestDTO requestDTO = new SearchRequestDTO();
    requestDTO.setPage(0);
    requestDTO.setSize(10);
    requestDTO.setSort("nome,asc");

    List<FilterDTO> filters = List.of(
        new FilterDTO("nome", Operator.EQUALS, "João"),
        new FilterDTO("idade", Operator.GREATER_THAN, "18")
    );
    requestDTO.setFilters(filters);

    // Act
    SearchRequest request = searchRequestMapper.toModel(requestDTO);

    // Assert
    assertThat(request.getPage()).isEqualTo(0);
    assertThat(request.getSize()).isEqualTo(10);
    assertThat(request.getFilters()).hasSize(2);
}
```

**4. Teste de Especificação JPA**:
```java
@Test
void deveAplicarFiltroEmSpecification() {
    // Arrange
    SearchRequest request = new SearchRequest();
    request.addFilter(new Filter("nome", Operator.EQUALS, "João"));

    SearchSpecification<Entity> spec = new SearchSpecification<>(request);

    // Act
    Specification<Entity> jpaSpec = spec.toSpecification();

    // Assert
    assertThat(jpaSpec).isNotNull();
}
```

**5. Teste de Operadores Suportados**:
```java
@ParameterizedTest
@EnumSource(Operator.class)
void deveSuportarTodosOsOperadores(Operator operator) {
    // Arrange
    FilterDTO filter = new FilterDTO("campo", operator, "valor");

    // Act & Assert
    assertThat(filter.getOperator()).isEqualTo(operator);
}
```

**6. Teste de Paginação**:
```java
@Test
void deveAplicarPaginacaoCorretamente() {
    // Arrange
    SearchRequestDTO requestDTO = new SearchRequestDTO();
    requestDTO.setPage(2);
    requestDTO.setSize(20);

    // Act
    Pageable pageable = SearchSpecification.getPageable(requestDTO.getPage(), requestDTO.getSize());

    // Assert
    assertThat(pageable.getPageNumber()).isEqualTo(2);
    assertThat(pageable.getPageSize()).isEqualTo(20);
}
```

**7. Teste de Ordenação**:
```java
@Test
void deveAplicarOrdenacaoCorretamente() {
    // Arrange
    SearchRequestDTO requestDTO = new SearchRequestDTO();
    requestDTO.setSort("nome,asc");

    // Act
    Sort sort = SearchSpecification.getSort(requestDTO.getSort());

    // Assert
    assertThat(sort).isNotNull();
    assertThat(sort.getOrderFor("nome")).isNotNull();
    assertThat(sort.getOrderFor("nome").getDirection()).isEqualTo(Sort.Direction.ASC);
}
```

### 23. Padrão de Criação de Testes Junto com Classes

**Decisão**: Toda nova classe criada deve ter seu teste correspondente criado simultaneamente

**Justificativa**:
- Garante que testes não sejam esquecidos ou adiados
- Promove TDD (Test-Driven Development)
- Facilita manutenção e refatoração
- Aumenta cobertura de código desde o início
- Documenta comportamento esperado imediatamente

**Tipos de Classes e Seus Testes Correspondentes**:

| Tipo de Classe | Sufixo de Teste | Classe Base | Localização |
|---------------|-----------------|-------------|-------------|
| *Service.java | *ServiceTest.java | BaseServiceTest | src/test/java/.../service/ |
| *Controller.java | *ControllerTest.java | BaseAPITest | src/test/java/.../rest/ |
| *Repository.java | *RepositoryTest.java | BaseRepositoryTest | src/test/java/.../repository/ |
| *DTO.java | *DTOTest.java | BaseUnitTest | src/test/java/.../dto/ |
| *Entity.java | *EntityTest.java | BaseUnitTest | src/test/java/.../model/ |
| *Mapper.java | *MapperTest.java | BaseUnitTest | src/test/java/.../mapper/ |
| *Translator.java | *TranslatorTest.java | BaseUnitTest | src/test/java/.../translator/ |
| *Validator.java | *ValidatorTest.java | BaseUnitTest | src/test/java/.../validators/ |
| *Rule.java | *RuleTest.java | BaseUnitTest | src/test/java/.../rules/ |
| *Manager.java | *ManagerTest.java | BaseVaadinManagerTest | src/test/java/.../view/ |
| *Config.java | *ConfigTest.java | BaseUnitTest | src/test/java/.../config/ |
| *Filter.java | *FilterTest.java | BaseUnitTest | src/test/java/.../filter/ |
| *Adapter.java | *AdapterTest.java | BaseUnitTest | src/test/java/.../adapter/ |
| *Component.java | *ComponentTest.java | BaseVaadinViewTest | src/test/java/.../component/ |

**Processo de Criação**:

**1. Criar Classe de Produção**:
```java
// src/main/java/com/ia/core/service/ExampleService.java
@Service
public class ExampleService {
    public String exampleMethod(String input) {
        return input.toUpperCase();
    }
}
```

**2. Criar Teste Correspondente Imediatamente**:
```java
// src/test/java/com/ia/core/service/ExampleServiceTest.java
@ExtendWith(MockitoExtension.class)
class ExampleServiceTest extends BaseServiceTest {

    @InjectMocks
    private ExampleService service;

    @Test
    void deveConverterParaMaiusculo() {
        // Arrange
        String input = "teste";

        // Act
        String result = service.exampleMethod(input);

        // Assert
        assertThat(result).isEqualTo("TESTE");
    }
}
```

**3. Criar Documento de Caso de Teste**:
```markdown
# ExampleService-Service-Layer.md

## Descrição
Testa o serviço ExampleService para conversão de strings para maiúsculas.

## Classe Testada
`com.ia.core.service.ExampleService`

## Cenários
- Deve converter string para maiúsculas
- Deve retornar string vazia se input for vazio
- Deve lançar exceção se input for nulo
```

**4. Executar Testes com Cobertura**:
```bash
mvn test jacoco:report
```

**5. Verificar Cobertura Mínima de 85%**:
- Se cobertura < 85%, adicionar mais cenários de teste
- Se cobertura >= 85%, prosseguir

**Checklist para Nova Classe**:
- [ ] Classe de produção criada
- [ ] Teste correspondente criado
- [ ] Documento de caso de teste criado
- [ ] Testes passando
- [ ] Cobertura >= 85%
- [ ] Commit com ambos (classe e teste)

**Exceções**:
- Interfaces sem implementação não requerem teste
- Classes anônimas não requerem teste separado
- Enums simples podem ter teste básico ou serem ignorados se não tiverem lógica

## Consequências

### Positivas
- ✅ Padronização de testes em todo o projeto
- ✅ Maior cobertura e qualidade de código
- ✅ Facilita refatorações com confiança
- ✅ Documentação viva do comportamento esperado
- ✅ Redução de bugs em produção
- ✅ Critério objetivo de qualidade com cobertura mínima de **85%**
- ✅ Geração automática de dados de teste reduz boilerplate
- ✅ Testes criados simultaneamente com classes previnem esquecimentos
- ✅ Cobertura de filtros e SearchRequest previne problemas de segurança e performance

### Negativas
- ⚠️ Curva de aprendizado inicial
- ⚠️ Tempo adicional para escrever testes
- ⚠️ Manutenção dos testes junto com código
- ⚠️ Dependência adicional (Instancio) no projeto

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

## 24. Fluxo de Desenvolvimento Orientado a Casos de Uso e Testes

**Decisão**: Adotar fluxo de desenvolvimento consistente onde casos de uso e testes guiam a implementação de forma iterativa.

**Justificativa**:
- Garante que casos de uso sejam documentados antes da implementação
- Testes básicos guiam a implementação inicial, reduzindo retrabalho
- Refinamento iterativo melhora qualidade e cobertura
- CDUs centralizados facilitam reutilização e manutenção
- Avaliação de tipos de testes por módulo garante abordagem adequada

### Fluxo de Desenvolvimento

**Fase 1: Identificação e Criação de Casos de Uso**
1. Analisar requisitos do módulo
2. Identificar casos de uso principais
3. Criar CDUs seguindo ADR-053 em `/home/israel/git/ia-core-apps/ia-core/CDU`
4. Cada CDU deve ter pasta com nome e arquivo README.md
5. Documentar fluxos, regras de negócio e requisitos especiais

**Fase 2: Varredura de Classes Testáveis**
1. Analisar estrutura do módulo
2. Identificar classes testáveis (métodos públicos, complexidade ciclomática)
3. Avaliar tipos de testes aplicáveis conforme critérios do módulo
4. Ler README.md do módulo para entender objetivo e contexto

**Fase 3: Criação de Casos de Teste Básicos**
1. Criar documentos de casos de teste em `src/test/resources/test-cases`
2. Casos de teste devem ser básicos e incompletos inicialmente
3. Focar em cenários principais e happy paths
4. Documentar cenários que guiarão implementação

**Fase 4: Implementação do Caso de Uso**
1. Implementar classes e métodos baseados em casos de teste
2. Seguir ADRs aplicáveis ao módulo
3. Implementar stack completa (entidades, DTOs, services, controllers, etc.)
4. Usar padrões definidos nos ADRs relevantes

**Fase 5: Refinamento dos Casos de Teste**
1. Expandir casos de teste com cenários adicionais
2. Incluir casos de borda, exceções e fluxos alternativos
3. Adicionar testes de integração quando necessário
4. Documentar todos os cenários cobertos

**Fase 6: Refinamento dos Testes**
1. Implementar testes completos baseados em casos de teste refinados
2. Garantir cobertura mínima de 85%
3. Executar testes com JaCoCo para verificar cobertura
4. Adicionar testes adicionais até atingir cobertura mínima

### Critérios de Tipos de Testes por Módulo

**Módulos Model (ia-core-*-model)**:
- **Testes Unitários**: DTOs, Entities, Enums, Interfaces
- **Testes de Validação**: Jakarta Validation annotations
- **Testes de Conversão**: Mappers e Converters
- **Cobertura Esperada**: 85%+

**Módulos Service (ia-core-*-service)**:
- **Testes Unitários**: Services, Validators, Rules, Business Logic
- **Testes de Integração**: Repositories, Event Publishing
- **Testes de Transação**: @Transactional annotations
- **Cobertura Esperada**: 85%+

**Módulos REST (ia-core-*-rest)**:
- **Testes Unitários**: Controllers, Filters, Exception Handlers
- **Testes de Integração**: Endpoints REST com MockMvc
- **Testes de Segurança**: JWT, Authentication, Authorization
- **Cobertura Esperada**: 85%+

**Módulos View (ia-core-*-view)**:
- **Testes Unitários**: Managers, Converters, Validators
- **Testes de Integração**: Views com SpringUIUnitTest
- **Testes E2E**: Fluxos críticos com TestBench (ADR-039)
- **Cobertura Esperada**: 85%+

**Módulos Test (ia-core-test)**:
- **Testes Unitários**: Classes base de teste
- **Testes de Integração**: Infraestrutura de teste
- **Cobertura Esperada**: 85%+

### Avaliação de Tipos de Testes

**Critérios para Testes Unitários**:
- Classes com lógica de negócio
- Métodos com complexidade ciclomática > 3
- Validações e regras de negócio
- Conversões e transformações

**Critérios para Testes de Integração**:
- Interação com banco de dados
- Publicação de eventos
- Chamadas a serviços externos
- Configurações Spring

**Critérios para Testes E2E**:
- Fluxos críticos de usuário
- Integração entre múltiplos componentes
- Testes de UI com navegador real
- Cenários que não podem ser testados unitariamente

**Critérios para Testes de Aceitação**:
- Requisitos funcionais do usuário
- Cenários de uso reais
- Validação de requisitos de negócio

### Estrutura Centralizada de CDUs

**Localização**: `/home/israel/git/ia-core-apps/ia-core/CDU`

**Estrutura**:
```
CDU/
├── CDU001-NomeDoCasoDeUso/
│   └── README.md
├── CDU002-OutroCasoDeUso/
│   └── README.md
└── ...
```

**Padrão de Nomenclatura**:
- CDU seguido de número sequencial de 3 dígitos
- Nome descritivo em kebab-case
- Exemplo: CDU001-GerenciamentoUsuarios

**Conteúdo do README.md**:
- Seguir estrutura completa definida no ADR-053
- Incluir metadados, descrição, atores, fluxos, regras
- Documentar requisitos especiais e pontos de extensão
- Referenciar ADRs relacionados

### Integração com README.md dos Módulos

**Cada módulo deve ter README.md na raiz explicando**:
- Objetivo do módulo
- Principais funcionalidades
- Tipos de testes aplicáveis
- Dependências principais
- ADRs relevantes

**Exemplo de README.md**:
```markdown
# ia-core-service

## Objetivo
Módulo de serviço contendo lógica de negócio e operações CRUD padrão.

## Funcionalidades Principais
- BaseService e CrudBaseService
- ServiceExecutionContext
- TransactionalWrite annotation
- Domain events

## Tipos de Testes Aplicáveis
- Unitários: Services, Validators, Rules
- Integração: Repositories, Event Publishing
- Transação: @Transactional annotations

## ADRs Relevantes
- ADR-012: Testing Patterns
- ADR-018: Business Rule Chain Pattern
- ADR-019: Service Validator Pattern
```

## Revisões

| Versão | Data | Descrição |
|--------|------|-----------|
| 1.0 | 2024-01-15 | Versão inicial |
| 2.0 | 2026-06-11 | Adição de padrões específicos para testes de LLM e Spring AI |
| 3.0 | 2026-06-15 | Inclusão de geração de classes de teste, execução obrigatória com cobertura e criação de novos cenários enquanto a cobertura estiver abaixo de 85%. |
| 4.0 | 2026-06-15 | Adição de referências adicionais sobre testes de software e expansão da lista de tipos de testes com base em literatura atualizada (IBM, Vericode, Sofist, DevMedia, Testomat.io, TestGrid.io, Medium, PractiTest, DEV.to, Parasoft, Alura). |
| 5.0 | 2026-06-15 | Adição de padrão Fixture (Instancio) para geração de dados de teste, orientações para testes de filtros e SearchRequest, e padrão de criação de testes simultânea com classes. |
| 6.0 | 2026-06-17 | Remoção de instruções sobre criação de módulos de testes separados. Testes agora seguem convenção Java padrão (src/test/java) dentro de cada módulo de produção. O módulo ia-core-test contém apenas classes base para testes. |
| 7.0 | 2026-06-17 | Adição de seção sobre organização de test-cases. Documentos de casos de teste devem ficar em src/test/resources/test-cases dentro de cada módulo de produção. Cada módulo é responsável por seus próprios test-cases. |
| 8.0 | 2026-06-18 | Adição de fluxo de desenvolvimento orientado a casos de uso e testes, critérios de tipos de testes por módulo, estrutura centralizada de CDUs e integração com README.md dos módulos. |
| 9.0 | 2026-06-18 | Atualização da seção 19 sobre Cucumber para incluir integração com CDUs (ADR-053), exemplos de feature files e step definitions, e critérios de quando usar/não usar Cucumber. |
| 10.0 | 2026-06-18 | Atualização da seção 16 sobre JaCoCo para incluir instruções de execução de testes por módulo individualmente, configuração detalhada do plugin e critério de aceite de que o módulo não deve ser considerado completo até atingir 85% de cobertura. |

## Referências
- Tomar como base o documento /referencias/testes.txt
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://mockito.org/)
- [AssertJ Core](https://assertj.github.io/doc/)
- [TestContainers](https://www.testcontainers.org/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html)

## Referências Adicionais

1. **Baeldung - JUnit 5 Tutorial**
   - URL: https://www.baeldung.com/junit-5-guide
   - Guia completo sobre JUnit 5

2. **Baeldung - Mockito Tutorial**
   - URL: https://www.baeldung.com/mockito-series
   - Série de tutoriais sobre Mockito

3. **Baeldung - AssertJ**
   - URL: https://www.baeldung.com/assertj
   - Guia sobre assertions com AssertJ

4. **TestContainers - Best Practices**
   - URL: https://www.testcontainers.org/best_practices/
   - Boas práticas para uso de containers em testes

5. **Martin Fowler - Test Pyramid**
   - URL: https://martinfowler.com/articles/practical-test-pyramid.html
   - Artigo fundamental sobre pirâmide de testes

6. **IBM - Software Testing**
   - URL: https://www.ibm.com/br-pt/think/topics/software-testing
   - Definição e importância de testes de software, níveis e tipos

7. **Vericode - 13 Tipos de Teste de Software**
   - URL: https://blog.vericode.com.br/tipos-de-teste-de-software/
   - Guia completo com 13 tipos de testes para melhorar aplicações

8. **Sofist - Teste de Software**
   - URL: https://www.sofist.co/blog/teste-de-software
   - Introdução, conceitos básicos e tipos de testes

9. **DevMedia - Técnicas e Fundamentos de Testes de Software**
   - URL: https://www.devmedia.com.br/guia/tecnicas-e-fundamentos-de-testes-de-software/34403
   - Guia completo sobre técnicas e fundamentos de testes

10. **Testomat.io - Software Testing Trends 2026**
    - URL: https://testomat.io/blog/software-testing-trends/
    - Tendências de teste para 2026: IA, shift-left, continuous testing, segurança

11. **TestGrid.io - Software Testing Tools**
    - URL: https://testgrid.io/blog/software-testing-tools/
    - Ferramentas de teste de software (estáticas e dinâmicas)

12. **TestGrid.io - Java Testing Frameworks**
    - URL: https://testgrid.io/blog/java-testing-frameworks/
    - Top 7 frameworks de teste para Java (JUnit, TestNG, Mockito, Spock, etc.)

13. **Medium - QA para Iniciantes em Java**
    - URL: https://medium.com/@lilianborbadeoliveira/qa-para-iniciantes-em-java-o-guia-definitivo-e-aprofundado-de-qualidade-de-software-c9c70f90950f
    - Guia definitivo de qualidade de software em Java, padrão AAA, Mockito, AssertJ

14. **PractiTest - Best Unit Testing Tools**
    - URL: https://www.practitest.com/resource-center/blog/best-unit-testing-tools/
    - 16 melhores ferramentas de teste unitário para 2026

15. **DevMedia - Teste Unitário**
    - URL: https://www.devmedia.com.br/teste-unitario/
    - Guia completo sobre teste unitário

16. **DEV.to - Testes 101 - Testando Aplicações Java**
    - URL: https://dev.to/vepo/testes-101-testando-aplicacoes-java-1m8i
    - Guia rápido para testes em aplicações Java (TDD, JUnit 5, AssertJ, Mockito)

17. **Parasoft - JUnit Tutorial**
    - URL: https://www.parasoft.com/blog/junit-tutorial-setting-up-writing-and-running-java-unit-tests/
    - Tutorial completo de JUnit com exemplos práticos

18. **Alura - Quais classes e métodos devo testar e criar mocks**
    - URL: https://cursos.alura.com.br/forum/topico-duvida-quais-classes-e-metodos-devo-testar-e-criar-mocks-328459
    - Discussão sobre estratégias de teste e uso de mocks

19. **Instancio - User Guide**
    - URL: https://www.instancio.org/user-guide/
    - Documentação oficial do Instancio para geração de dados de teste

20. **Baeldung - Generate Unit Test Data in Java Using Instancio**
    - URL: https://www.baeldung.com/java-test-data-instancio
    - Tutorial completo sobre Instancio para geração de dados de teste

21. **GitHub - Fixture Factory**
    - URL: https://github.com/six2six/fixture-factory
    - Biblioteca alternativa para geração de objetos de teste via templates
