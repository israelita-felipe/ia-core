# ADR 039: Testes de Interface (E2E) para Aplicações Vaadin

## Status

**Proposto** | **Aceito** | **Deprecado**

## Contextualização

O projeto ia-core-apps precisa de uma estratégia para testes de interface gráfica (E2E) que permita:
1. Navegação entre telas Vaadin
2. Operacionalização de formulários e componentes
3. Detecção de erros de UI e regressions
4. Reutilização entre diferentes aplicações (Biblia, gestor-igreja)

O módulo `biblia-test` já possui testes unitários, mas faltam testes de interface que validem o comportamento das Views Vaadin.

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

**Decisão**: Criar classes base de teste em `ia-core-apps` para serem estendidas pelas aplicações

**Estrutura Proposta**:
```
ia-core-view/
├── src/test/java/com/ia/core/view/test/
│   ├── AbstractViewTest.java        # Base para testes de View
│   ├── AbstractFormTest.java        # Base para testes de Form
│   ├── VaadinTestRunner.java        # Executor customizado
│   └── components/
│       ├── ButtonTester.java       # Wrappers para componentes comuns
│       ├── GridTester.java
│       ├── FormTester.java
│       └── NavigationTester.java
```

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

```xml
<!-- Vaadin TestBench -->
<dependency>
    <groupId>com.vaadin</groupId>
    <artifactId>vaadin-testbench-junit5</artifactId>
    <version>24.8.7</version>
    <scope>test</scope>
</dependency>

<!-- Selenium WebDriver -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.20.0</version>
    <scope>test</scope>
</dependency>
```

### Estrutura de Testes em biblia-test

```
biblia-test/
├── src/test/java/com/ia/biblia/
│   ├── view/
│   │   ├── AbstractBibliaViewTest.java
│   │   ├── pessoa/
│   │   │   ├── PessoaPageTest.java
│   │   │   └── PessoaFormTest.java
│   │   └── evento/
│   │       └── EventoPageTest.java
│   └── pageobjects/
│       ├── BibliaPageObject.java
│       ├── PessoaPageObject.java
│       └── EventoPageObject.java
```

## Consequências

### Positivas
- ✅ Testes automatizados de interface gráfica
- ✅ Detecção precoce de regressions em UI
- ✅ Reutilização de código entre Bible e gestor-igreja
- ✅ Integração com pipeline CI/CD

### Negativas
- ⚠️ Curva de aprendizado para equipes sem experiência com TestBench
- ⚠️ Manutenção de testes quando UI muda frequentemente
- ⚠️ Tempo de execução maior que testes unitários

## Decisões Dependentes

Este ADR complementa e depende de:
- [ADR 012: Padrões de Teste Automatizado](./012-testing-patterns.md) - Usa JUnit 5 como base
- [ADR 008: Arquitetura MVVM com ViewModel](./008-mvvm-architecture-with-viewmodel.md) - Views testáveis
- [ADR 010: Padrões de Nomenclatura](./010-nomenclature-standards.md) - Nomes consistentes

## Referências

- [Vaadin TestBench Documentation](https://vaadin.com/docs/testbench)
- [Vaadin TestBench JUnit 5](https://vaadin.com/docs/testbench/junit5)
- [Selenium WebDriver](https://www.selenium.dev/documentation/)
- [Page Object Model](https://martinfowler.com/bliki/PageObject.html)
- [Vaadin Best Practices - Testing](https://vaadin.com/docs/guide/testing)