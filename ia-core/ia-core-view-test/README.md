# ia-core-view-test

## Objetivo

Módulo de testes para interfaces Vaadin contendo classes base para testes E2E de views e componentes UI.

## Funcionalidades Principais

- `CoreVaadinViewBase` - Base para testes de Views Vaadin (mockado, sem servidor)
- `CoreBaseVaadinViewTest` - Base para testes de Views com ambiente mockado simples
- `CoreVaadinManagerBase` - Base para testes de diálogos/managers
- `CoreE2EBase` - Base para testes end-to-end (TestBench/Selenium)
- `CoreBaseE2ETest` - Base para E2E tests com SpringBootTest

## Tipos de Testes Aplicáveis

| Tipo de Teste | Classe Base | Descrição |
|--------------|-------------|-----------|
| Testes de View Vaadin | `CoreVaadinViewBase` | Testes de componentes UI (mockado) |
| Testes de View Simples | `CoreBaseVaadinViewTest` | Testes com ambiente mockado básico |
| Testes de Manager | `CoreVaadinManagerBase` | Testes de diálogos e forms |
| Testes E2E | `CoreE2EBase` | Testes com Selenium/TestBench |
| Testes E2E Spring | `CoreBaseE2ETest` | Testes E2E com contexto Spring |

## Dependências Incluídas

- Vaadin TestBench JUnit 5
- Selenium WebDriver
- WebDriverManager

## Uso

### Teste de View Vaadin

```java
// Em módulos de aplicação (biblia-view, gestor-igreja-view)
class PessoaViewTest extends CoreVaadinViewBase {

    @Test
    void testViewRendering() {
        var view = new PessoaView();
        addToUI(view);

        assertThat(view).isNotNull();
    }
}
```

### Teste E2E com TestBench

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PessoaE2ETest extends CoreBaseE2ETest {

    @Test
    void testPessoaCrud() {
        initDriver();
        driver.get("http://localhost:8080/pessoa");

        // Testes E2E com navegador real
    }
}
```

## Classes Disponíveis

| Classe | Pacote | Descrição |
|--------|--------|-----------|
| `CoreVaadinViewBase` | `com.ia.core.view` | Base para testes de Views Vaadin (mockado) |
| `CoreBaseVaadinViewTest` | `com.ia.core.view` | Base para testes de Views com ambiente mockado simples |
| `CoreVaadinManagerBase` | `com.ia.core.view` | Base para testes de diálogos/managers |
| `CoreE2EBase` | `com.ia.core.view` | Base para testes E2E com Selenium/TestBench |
| `CoreBaseE2ETest` | `com.ia.core.view` | Base para E2E tests com SpringBootTest |

## Hierarquia

```
ia-core-test
    └── ia-core-model-test
            └── ia-core-service-model-test
                    └── ia-core-view-test
```

## ADRs Relevantes

- ADR-012: Testing Patterns - Arquitetura de módulos em camadas
- ADR-039: Vaadin E2E Tests
- ADR-008: Arquitetura MVVM com ViewModel
