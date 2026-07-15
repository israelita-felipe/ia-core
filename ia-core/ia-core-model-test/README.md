# ia-core-model-test

## Objetivo

Módulo de testes para classes de modelo (model, DTOs) contendo classes base para testes de unidade.

## Funcionalidades Principais

- `CoreBaseUnitTest` - Classe base para testes de modelo (via delegação)
- `CoreModelUnitTest` - Classe delegadora para CoreBaseUnitTest (backward compatibility)

## Tipos de Testes Aplicáveis

- **Unitários**: Testes de classes utilitárias
- **Model**: Testes de entidades e DTOs
- **Fixture Pattern**: Geração automática de dados usando Instancio

## Dependências

```xml
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-model-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

## Uso

```java
// Para testes de modelo genéricos
class MyEntityTest extends CoreModelUnitTest {

    @Test
    void testEntityCreation() {
        var entity = Instancio.create(MyEntity.class);
        assertThat(entity).isNotNull();
    }
}
```

## Classes Disponíveis

| Classe | Pacote | Descrição |
|--------|--------|-----------|
| `CoreBaseUnitTest` | `com.ia.test` | Classe base para testes (delegada) |
| `CoreModelUnitTest` | `com.ia.test.model` | Classe delegadora para CoreBaseUnitTest |

## ADRs Relevantes

- ADR-012: Testing Patterns
- ADR-010: Nomenclature Standards (para nomenclatura de testes)
