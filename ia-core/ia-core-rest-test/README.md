# ia-core-rest-test

## Objetivo

Módulo de testes para camada de API REST contendo classes base para testes de controllers.

## Funcionalidades Principais

- Classes base para testes de API REST (MockMvc)
- Suporte a JSON Path para validações
- Geração automática de dados usando Instancio

## Status

⏳ **Em desenvolvimento** - As classes de teste de API ainda estão sendo implementadas (conforme ADR-012, seção 6.1)

## Tipos de Testes Aplicáveis

- **Testes de Controller**: Testes de endpoints REST
- **Testes de API**: Testes de integração de API

## Dependências (a serem incluídas)

```xml
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-rest-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

## Classes Previstas

| Classe Prevista | Pacote | Descrição |
|-----------------|--------|-----------|
| `CoreAPIBase` | `com.ia.test.rest` | Base para testes de API (MockMvc) |

## Uso

```java
@WebMvcTest(MyController.class)
class MyControllerTest extends CoreAPIBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetEndpoint() throws Exception {
        mockMvc.perform(get("/api/resource"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }
}
```

## ADRs Relevantes

- ADR-012: Testing Patterns (seção 6.1 - Classes de teste de API a serem implementadas)
- ADR-014: OpenAPI/Swagger Documentation
