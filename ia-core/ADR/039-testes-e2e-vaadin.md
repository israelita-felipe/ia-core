# ADR 039: Testes de Interface (E2E) para Aplicações Vaadin

## Status

**Aceito**

## Contextualização

O projeto ia-core-apps precisa de uma estratégia para testes de interface gráfica (E2E) que permita:
1. Navegação entre telas Vaadin
2. Operacionalização de formulários e componentes
3. Detecção de erros de UI e regressions
4. Reutilização entre diferentes aplicações (Biblia, gestor-igreja)

O módulo `biblia-test` já possui testes unitários, mas faltam testes de interface que validem o comportamento das Views Vaadin.

**Importante**: Este ADR se aplica aos módulos do ia-core, que funcionam como bibliotecas/framework e não podem compor uma aplicação por si só. Esses módulos são base/framework para construção de outras aplicações, padronizando o desenvolvimento e abstraindo padrões de desenvolvimento. Uma aplicação real é composta pela combinação de múltiplos módulos ia-core mais código específico do domínio da aplicação.

## Decisões

### 1. Framework de Testes E2E

**Decisão**: Usar **TestBench** (oficial Vaadin) para testes de interface

**Justificativa**:
- Integração nativa com componentes Vaadin
- API fluente para interação com componentes
- Suporte a múltiplos navegadores
- Recording de testes via IDE

**Alternativas Consideradas**:
- Selenium WebDriver: Mais genérico, mas requer mais configuração
- Playwright: Moderno, mas menos integrado com Vaadin
- Cypress: Não suporta Java diretamente

### 2. Testes Reutilizáveis no Framework

**Decisão**: Usar módulo `ia-core-view-test` para classes base de teste E2E Vaadin

**Estrutura Atual (ia-core-view-test)**:
```
ia-core-view-test/
├── src/main/java/com/ia/core/view/
│   ├── CoreVaadinViewBase.java      # Base para testes de View Vaadin
│   ├── CoreVaadinManagerBase.java   # Base para testes de Manager/Dialog
│   └── CoreE2EBase.java           # Base para testes E2E genéricos
└── pom.xml                        # Dependências TestBench, Selenium, WebDriverManager
```

**Como Usar**:
```xml
<!-- Em módulos que precisam de testes E2E Vaadin -->
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-view-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

**Hierarquia de Módulos de Teste**:
| Módulo | Depende de | Para que tipo de teste |
|--------|-----------|----------------------|
| `ia-core-test` | nenhum | Classes base (Instancio, JUnit 5, AssertJ, Mockito) |
| `ia-core-view-test` | ia-core-service-model-test | Vaadin Views, Manager, E2E com TestBench/Selenium |

### 3. Abstração de Navegação

**Decisão**: Criar utilitário de navegação entre rotas Vaadin

```java
public class ViewNavigator {

    /**
     * Navega para uma rota específica
     * @param route rota Vaadin (ex: "pessoa", "evento")
     */
    public void navigateTo(String route);

    /**
     * Verifica se a view atual corresponde à rota esperada
     */
    public boolean isAt(String expectedRoute);

    /**
     * Aguarda carregamento completo da view
     */
    public void waitForViewLoaded();
}
```

### 4. Padrão Page Object para Vaadin

**Decisão**: Implementar Page Objects para cada View

```java
public class PessoaPageObject extends AbstractPageObject {

    public PessoaPageObject(VaadinRobot robot) {
        super(robot);
    }

    public void open() {
        navigateTo("pessoa");
    }

    public void clickNovo() {
        robot.click("btn-novo");
    }

    public void fillForm(PessoaDTO dto) {
        robot.fill("field-nome", dto.getNome());
        robot.fill("field-cpf", dto.getCpf());
    }

    public void save() {
        robot.click("btn-salvar");
    }

    public boolean hasError() {
        return robot.hasElement("error-message");
    }
}
```

### 5. Configuração de Ambiente

**Decisão**: Usar WebDriver com suporte a headless mode para CI/CD

```java
@Configuration
public class TestBenchConfig {

    @Bean
    public WebDriver webDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        return new ChromeDriver(options);
    }
}
```

## Implementação

### Dependências Necessárias

**As dependências já estão incluídas em `ia-core-view-test`**:
- Vaadin TestBench JUnit 5
- Selenium WebDriver
- WebDriverManager

**Para usar, adicione apenas**:
```xml
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-view-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

### Classes Base Disponíveis

| Classe | Pacote | Uso |
|--------|--------|-----|
| `CoreVaadinViewBase` | `com.ia.core.view` | Base para testes de Views Vaadin (mockado) |
| `CoreBaseVaadinViewTest` | `com.ia.core.view` | Base para testes de Views com ambiente mockado simples |
| `CoreVaadinManagerBase` | `com.ia.core.view` | Base para testes de diálogos/managers |
| `CoreE2EBase` | `com.ia.core.view` | Base para testes E2E com TestBench/Selenium |
| `CoreBaseE2ETest` | `com.ia.core.view` | Base para E2E tests com SpringBootTest |
| `CoreBaseE2ETest` | `com.ia.core.view` | Base para E2E tests com SpringBootTest |

### Estrutura de Testes

**Módulo ia-core-view-test** (base para todos os testes Vaadin E2E):
```java
// Em módulos de aplicação (biblia-view, gestor-igreja-view)
class PessoaPageTest extends CoreVaadinViewBase {
    // Testes específicos da view Pessoa
}
```

**Exemplo de estrutura em módulos de aplicação**:
```
biblia-view/
├── src/test/java/com/ia/biblia/
│   ├── view/
│   │   ├── PessoaPageTest.java
│   │   └── EventoPageTest.java
│   └── pageobjects/
│       ├── PessoaPageObject.java
│       └── EventoPageObject.java
```

## Consequências

### Positivas
- ✅ Testes automatizados de interface gráfica
- ✅ Detecção precoce de regressions em UI
- ✅ **Reutilização via ia-core-view-test**: Classes base compartilhadas entre todas as aplicações Vaadin
- ✅ Integração com pipeline CI/CD
- ✅ Integração com arquitetura de módulos de teste em camadas (ADR-012)

### Negativas
- ⚠️ Curva de aprendizado para equipes sem experiência com TestBench
- ⚠️ Manutenção de testes quando UI muda frequentemente
- ⚠️ Tempo de execução maior que testes unitários
- ⚠️ Dependência de navegador (Chrome/Chromium) para execução local

## Decisões Dependentes

Este ADR complementa e depende de:
- [ADR 012: Padrões de Teste Automatizado](./012-testing-patterns.md) - Usa JUnit 5 como base e integra-se à arquitetura de módulos de teste em camadas
- [ADR 008: Arquitetura MVVM com ViewModel](./008-mvvm-architecture-with-viewmodel.md) - Views testáveis
- [ADR 010: Padrões de Nomenclatura](./010-nomenclature-standards.md) - Nomes consistentes

**Arquitetura de Módulos de Teste**:
- [TEST_MODULES_ARCHITECTURE.md](../TEST_MODULES_ARCHITECTURE.md) - Documentação da hierarquia de módulos de teste

## Referências

- [Vaadin TestBench Documentation](https://vaadin.com/docs/testbench)
- [Vaadin TestBench JUnit 5](https://vaadin.com/docs/testbench/junit5)
- [Selenium WebDriver](https://www.selenium.dev/documentation/)
- [Page Object Model](https://martinfowler.com/bliki/PageObject.html)
- [Vaadin Best Practices - Testing](https://vaadin.com/docs/guide/testing)
