# ia-core-service-model-test

## Objetivo

Módulo de testes para DTOs contendo classes base para testes de objetos de transferência de dados.

## Funcionalidades Principais

- `CoreDTOUnitTest` - Classe base para testes de DTOs
- Geração automática de dados usando Instancio
- AssertJ para assertions fluentes

## Tipos de Testes Aplicáveis

- **Unitários**: Testes de DTOs
- **Fixture Pattern**: Geração automática de dados usando Instancio

## Dependências

```xml
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-service-model-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

## Uso

```java
// Para testes de DTOs
class MyDTOTest extends CoreDTOUnitTest {

    @Test
    void testDTOCreation() {
        var dto = Instancio.create(MyDTO.class);
        assertThat(dto).isNotNull();
    }
}
```

## Classes Disponíveis

| Classe | Pacote | Descrição |
|--------|--------|-----------|
| `CoreDTOUnitTest` | `com.ia.test.dto` | Base para testes de DTOs |

## ADRs Relevantes

- ADR-012: Testing Patterns
- ADR-010: Nomenclature Standards (para nomenclatura de testes)
