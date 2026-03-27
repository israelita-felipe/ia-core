# ADR-024: Usar Versionamento de API via URL Path

## Status

✅ Aceito

## Contexto

A API REST do projeto precisa evoluir sem quebrar clientes existentes. É necessário:
- Suporte a múltiplas versões simultâneas
- Backward compatibility
- Documentação clara de versões disponíveis
- Migração gradual de clientes

## Decisão

Usar **versionamento via URL path** (`/api/v1/...`), o método mais explícito e widely accepted.

## Detalhes

### Estrutura de URLs

```
/api/v1/pessoas
/api/v1/eventos
/api/v2/pessoas  (nova versão com campos adicionais)
```

### Configuração de Versionamento

```java
@RestController
@RequestMapping("/api/v1")
public class PessoaControllerV1 extends ListBaseController<Pessoa, PessoaDTO> {
    // Endpoints da versão 1
}

@RestController
@RequestMapping("/api/v2")
public class PersonaControllerV2 extends ListBaseController<Pessoa, PessoaDTO> {
    // Endpoints da versão 2
}
```

### Exemplo de Response com Versionamento

```json
// v1 - pessoa simples
{
  "id": 1,
  "nome": "João Silva",
  "tipo": "FISICA"
}

// v2 - pessoa com campos adicionais
{
  "id": 1,
  "nome": "João Silva",
  "tipo": "FISICA",
  "cpf": "12345678901",
  "dataCadastro": "2024-01-15T10:30:00Z",
  "endereco": {
    "rua": "Rua Principal",
    "cidade": "São Paulo"
  }
}
```

### Configuration de Versões

```java
@Configuration
public class ApiVersioningConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiVersionInterceptor());
    }
}

@Component
public class ApiVersionInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                           HttpServletResponse response, 
                           Object handler) {
        
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/api/v")) {
            String version = extractVersion(requestURI);
            request.setAttribute("apiVersion", version);
        }
        return true;
    }
    
    private String extractVersion(String uri) {
        Pattern pattern = Pattern.compile("/api/v(\\d+)");
        Matcher matcher = pattern.matcher(uri);
        return matcher.find() ? matcher.group(1) : "1";
    }
}
```

### Documentação de Versões

```yaml
openapi: 3.0.0
info:
  title: ia-core-apps API
  version: '1.0'
  description: |
    ## Versões Disponíveis
    
    | Versão | Status | Descrição |
    |--------|--------|-----------|
    | v1 | ✅ Ativa | Versão inicial |
    | v2 | 🔄 Beta | Novos campos |

paths:
  /api/v1/pessoas:
    get:
      summary: Listar pessoas (v1)
  /api/v2/pessoas:
    get:
      summary: Listar pessoas (v2)
```

### Boas Práticas

| Prática | Recomendação |
|---------|--------------|
| Manter versões antigas | Mínimo 2 versões ativas |
| Deprecation notice | Header `Warning: 299` |
| Documentar diferenças | Changelog entre versões |
| Versionamento tardio | Começar com v1, não v0 |
| Evitar mudanças no path | Manter backward compat |

## Consequências

### Positivas

- ✅ Muito explícito e visível
- ✅ Fácil de entender para clientes
- ✅ Suporte a múltiplas versões
- ✅ Cache friendly (CDN)
- ✅ Simple de implementar

### Negativas

- ❌ Polui a URL
- ❌ Duplicação de código entre versões
- ❌ Requer documentação clara

## Status de Implementação

✅ **COMPLETO**

- Estrutura `/api/v1/` implementada
- v1 ativa, v2 em desenvolvimento

## Data

2024-02-01

## Revisores

- Team Lead
- Architect

## Referências

1. **REST API Versioning Best Practices**
   - URL: https://www.baeldung.com/rest-api-versioning
   - Guia completo

2. **Microsoft - API Versioning**
   - URL: https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design#api-versioning
   - Melhores práticas

3. **Richardson Maturity Model**
   - URL: https://martinfowler.com/articles/richardsonMaturityModel.html
   - Níveis de maturidade REST

4. **Stripe API Versioning**
   - URL: https://stripe.com/blog/api-versioning
   - Exemplo de empresa real

5. **Spring REST Docs**
   - URL: https://spring.io/projects/spring-restdocs
   - Documentação