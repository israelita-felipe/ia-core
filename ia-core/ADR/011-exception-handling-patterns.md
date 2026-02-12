# ADR-011: Padrões de Exception Handling

## Título
Padrões de Exception Handling para API REST

## Status

✅ Aceito

## Contexto
A aplicação ia-core-apps necessita de uma estratégia padronizada para tratamento de exceções em sua API REST. O tratamento inconsistente de erros leva a:
- Respostas de erro inconsistentes para clientes
- Dificuldade no debugging
- Vazamento de informações sensíveis
- Poor developer experience

Decisões anteriores (ADR-001 a ADR-010) estabeleceram padrões para mapping, filtragem, i18n, DI, eventos de domínio, EntityGraph, BaseEntity, MVVM, paginação e nomenclatura, mas não abordaram explicitamente o tratamento de exceções.

## Decisões

### Decisão 1: Hierarquia de Exceções Baseada em Domínio
**Escolhido:** Criar hierarquia de exceções baseadas em `DomainException`

**Alternativas consideradas:**
1. Usar apenas exceções padrão do Java (Exception, RuntimeException)
2. Usar exceção única com código de erro (ServiceException atual)
3. Hierarquia completa baseada em domínio

**Justificativa:**
- Exceções específicas do domínio são mais expressivas
- Permite tratamento granular por tipo de erro
- Facilita a categorização para logging e métricas
- Código de erro é automaticamente derivado do nome da classe

**Consequências:**
- Positivas: Código mais legível, melhor rastreabilidade
- Negativas: Maior número de classes de exceção

### Decisão 2: Enum RestErrorCode para Códigos Padronizados
**Escolhido:** Criar enum `RestErrorCode` para códigos de erro reutilizáveis

**Estrutura do enum:**
```java
public enum RestErrorCode {
  AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", 401, "error.authentication"),
  ACCESS_DENIED("ACCESS_DENIED", 403, "error.access.denied"),
  ENTITY_NOT_FOUND("ENTITY_NOT_FOUND", 404, "error.entity.not.found"),
  VALIDATION_ERROR("VALIDATION_ERROR", 400, "error.validation"),
  DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION", 409, "error.data.integrity"),
  SERVICE_ERROR("SERVICE_ERROR", 400, "error.service"),
  INTERNAL_ERROR("INTERNAL_ERROR", 500, "error.internal");
}
```

**Justificativa:**
- Reaproveitamento de códigos em handlers
- Tipo seguro (type-safe)
- Documentação automática dos códigos suportados
- Facilita internacionalização

### Decisão 3: Internacionalização de Mensagens de Erro
**Escolhido:** Usar `CoreApplicationTranslator` para todas as mensagens de erro

**Chaves de tradução:**
```java
public static final String ERROR_AUTHENTICATION = "error.authentication";
public static final String ERROR_ACCESS_DENIED = "error.access.denied";
public static final String ERROR_ENTITY_NOT_FOUND = "error.entity.not.found";
public static final String ERROR_VALIDATION = "error.validation";
public static final String ERROR_DATA_INTEGRITY = "error.data.integrity";
public static final String ERROR_SERVICE = "error.service";
public static final String ERROR_INTERNAL = "error.internal";
```

**Justificativa:**
- Suporte a múltiplos idiomas
- Messages centralizadas
- Facilidade de manutenção
- Consistência na comunicação de erros

### Decisão 4: ResponseEntity Padronizada com ErrorResponse DTO
**Escolhido:** Criar DTO ErrorResponse para padronizar respostas de erro

**Estrutura do ErrorResponse:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "errorCode": "VALIDATION_ERROR",
  "message": "Erro(s) de validação",
  "path": "/api/v1/users",
  "traceId": "a1b2c3d4-e5f6-7890",
  "details": [...],
  "fieldErrors": {...}
}
```

**Justificativa:**
- Conformidade com RFC 7807 (Problem Details for HTTP APIs)
- Estrutura consistente para todos os erros
- Campo traceId para correlação de logs
- Suporte a validação detalhada de campos

**Consequências:**
- Positivas: API consistente, melhor experiência para clientes
- Negativas: Overhead de serialização adicional

### Decisão 5: Uso de @RestControllerAdvice Centralizado
**Escolhido:** Centralizar tratamento em `CoreRestControllerAdvice`

**Justificativa:**
- Single source of truth para tratamento de erros
- Anotação `@RestControllerAdvice` do Spring
- Mantém controllers limpos de lógica de tratamento de erros
- Facilita manutenção e evolução

### Decisão 6: Exceções Não Verificadas (Unchecked)
**Escolhido:** Todas as exceções de domínio estendem `RuntimeException`

**Justificativa:**
- Não obriga declaração em assinaturas de métodos
- Mais flexível para uso em diferentes camadas
- Consistente com práticas modernas de Java/Spring

### Decisão 7: Códigos de Erro Automáticos
**Escolhido:** Derivar código de erro do nome da classe

**Exemplo:**
- `ResourceNotFoundException` → `RESOURCE_NOT_FOUND`
- `ValidationException` → `VALIDATION_ERROR`
- `BusinessException` → `BUSINESS_RULE_VIOLATION`

**Justificativa:**
- Elimina duplicação de código
- Garante consistência
- Reduz chance de erros de digitação

## Detalhamento Técnico

### Hierarquia de Classes

```
Throwable
    └── Exception
        └── RuntimeException
            └── DomainException (abstract)
                ├── ResourceNotFoundException
                ├── ValidationException
                └── BusinessException
```

### Mapeamento para HTTP Status

| Exceção | HTTP Status |
|---------|-------------|
| AuthenticationException | 401 |
| AccessDeniedException | 403 |
| ResourceNotFoundException | 404 |
| ValidationException | 400 |
| BusinessException | 400 |
| DataIntegrityViolationException | 409 |
| Exception (genérica) | 500 |

### Trace ID para Correlação

- Gerado UUID em cada requisição
- Incluído em todos os logs
- Retornado no header `x-trace-id` e no corpo da resposta
- Permite rastrear uma requisição através de múltiplos serviços

## Implementação

### Enum RestErrorCode

```java
@Getter
@RequiredArgsConstructor
public enum RestErrorCode {
  AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", 401, "error.authentication"),
  ACCESS_DENIED("ACCESS_DENIED", 403, "error.access.denied"),
  ENTITY_NOT_FOUND("ENTITY_NOT_FOUND", 404, "error.entity.not.found"),
  VALIDATION_ERROR("VALIDATION_ERROR", 400, "error.validation"),
  DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION", 409, "error.data.integrity"),
  SERVICE_ERROR("SERVICE_ERROR", 400, "error.service"),
  INTERNAL_ERROR("INTERNAL_ERROR", 500, "error.internal");

  private final String code;
  private final int httpStatus;
  private final String translationKey;
}
```

### Exemplo de DomainException

```java
public abstract class DomainException extends RuntimeException {
    private final String errorCode;

    protected DomainException(String message) {
        super(message);
        this.errorCode = determineErrorCode();
    }

    private String determineErrorCode() {
        String className = getClass().getSimpleName();
        return className.replaceAll("(?<!^)(?=[A-Z])", "_").toUpperCase();
    }
}
```

### Exemplo de Handler

```java
@RestControllerAdvice
public class CoreRestControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex, WebRequest request) {
        String traceId = generateTraceId();
        
        ErrorResponse error = ErrorResponse.of(
            HttpStatus.UNAUTHORIZED.value(),
            RestErrorCode.AUTHENTICATION_ERROR.getCode(),
            getTranslatedMessage(RestErrorCode.AUTHENTICATION_ERROR),
            getPath(request),
            traceId
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .header("x-trace-id", traceId)
            .body(error);
    }
}
```

## Migração

### ServiceException → DomainException

A classe `ServiceException` existente será mantida para retrocompatibilidade, mas novas implementações devem usar as novas exceções de domínio.

```java
// Antes (ServiceException)
throw new ServiceException("Email já cadastrado");

// Depois (ValidationException)
throw new ValidationException("email", email, "Email já cadastrado");

// Depois (BusinessException)
throw new BusinessException("DUPLICATE_EMAIL", "Email já cadastrado");
```

## Referências

- [RFC 7807 - Problem Details for HTTP APIs](https://datatracker.ietf.org/doc/html/rfc7807)
- [Spring Exception Handling](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-exceptionhandlers)

## Histórico de Revisões

| Versão | Data | Autor | Descrição |
|--------|------|-------|-----------|
| 1.0 | 2024-01-15 | Israel Araújo | Versão inicial |
| 2.0 | 2024-03-20 | Israel Araújo | Adicionado RestErrorCode enum e i18n |

---

**Nota:** Este ADR complementa os padrões estabelecidos nos ADRs anteriores e deve ser considerado em conjunto com as diretrizes de codificação do projeto.
