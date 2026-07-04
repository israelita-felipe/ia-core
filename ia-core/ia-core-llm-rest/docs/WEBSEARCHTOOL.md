# WebSearchTool

Documentação oficial da ferramenta de busca na internet do `ia-core`.

## Visão geral

O `WebSearchTool` é uma ferramenta nativa do `ia-core` para busca na internet. Ele encapsula o `BraveWebSearchTool` do `spring-ai-agent-utils` e expõe uma API simples para agentes LLM consultarem informações atualizadas da web.

## Dependência

A funcionalidade já está disponível no módulo `ia-core-llm-rest` por meio da dependência:

```xml
<dependency>
    <groupId>org.springaicommunity</groupId>
    <artifactId>spring-ai-agent-utils</artifactId>
</dependency>
```

## Configuração

Adicione a chave da API do Brave Search no `application.yml`:

```yaml
brave:
  api:
    key: ${BRAVE_API_KEY}
```

> **Importante:** sem a chave, o `WebSearchTool` é inicializado, mas retorna erro quando utilizado.

## Uso programático

```java
@Service
@RequiredArgsConstructor
public class MeuServico {

    private final WebSearchTool webSearchTool;

    public String consultar(String query) {
        return webSearchTool.searchWeb(query);
    }
}
```

## Uso como tool de agente

O `WebSearchTool` já está anotado com `@Tool` e pode ser descoberto automaticamente pelo catálogo de ferramentas do agente.

```java
@Tool(
    description = "Realiza busca na internet usando Brave Search API e retorna os resultados mais relevantes."
)
public String searchWeb(
    @ToolParam(description = "Termo de busca a ser pesquisado na internet") String query) {
    ...
}
```

## Endpoints REST

| Método | Caminho | Descrição |
|--------|---------|-----------|
| `POST` | `/api/${api.version}/llm/web/search` | Realiza busca na internet |
| `GET` | `/api/${api.version}/llm/web/status` | Verifica disponibilidade do serviço |

### Exemplo de requisição

```bash
curl -X POST "http://localhost:8080/api/${api.version}/llm/web/search?query=Spring+AI+Agent+Utils"
```

### Exemplo de resposta

```json
{
  "results": [
    {
      "title": "Spring AI Agent Utils",
      "url": "https://github.com/spring-ai/spring-ai-agent-utils",
      "description": "..."
    }
  ]
}
```

## Parâmetros

| Parâmetro | Tipo | Obrigatório | Descrição |
|-----------|------|--------------|-----------|
| `query` | `String` | Sim | Termo de busca a ser pesquisado na internet |

## Retorno

- **Sucesso:** texto com os resultados da busca.
- **Erro:** mensagem indicando falha na busca ou ausência da chave de API.

## Limitações

- Requer chave de API do Brave Search.
- O número máximo de resultados é definido por `WebSearchTool.MAX_RESULT_COUNT`.

## Referências

- `WebSearchTool.java`
- `WebSearchController.java`
- `BraveWebSearchTool` do `spring-ai-agent-utils`
