# Caso de Teste: ErrorResponse

## Descrição
Testa a classe ErrorResponse que fornece estrutura padronizada para respostas de erro da API REST.

## Classe Testada
`com.ia.core.rest.error.ErrorResponse`

## Fluxo do Teste
1. Testar criação de ErrorResponse básico
2. Testar criação de ErrorResponse com detalhes
3. Testar criação de ErrorResponse com erros de campo
4. Testar campos do ErrorResponse
5. Testar classe interna ErrorDetail

## Cenários

### Cenário 1: Criar ErrorResponse básico
- **Dado**: status 404, errorCode "ENTITY_NOT_FOUND", message "Resource not found", path "/api/${api.version}/resource", traceId "abc123"
- **Quando**: Chamar ErrorResponse.of()
- **Então**: Deve criar ErrorResponse com todos os campos preenchidos
- **E**: timestamp deve ser preenchido
- **E**: details deve ser null
- **E**: fieldErrors deve ser null
- **E**: exception deve ser null

### Cenário 2: Criar ErrorResponse com detalhes
- **Dado**: status 400, errorCode "VALIDATION_ERROR", message "Validation failed", path "/api/${api.version}/resource", traceId "abc123", lista de ErrorDetail
- **Quando**: Chamar ErrorResponse.withDetails()
- **Então**: Deve criar ErrorResponse com detalhes
- **E**: details deve conter a lista fornecida
- **E**: fieldErrors deve ser null

### Cenário 3: Criar ErrorResponse com erros de campo
- **Dado**: status 400, errorCode "VALIDATION_ERROR", message "Validation failed", path "/api/${api.version}/resource", traceId "abc123", mapa de fieldErrors
- **Quando**: Chamar ErrorResponse.withFieldErrors()
- **Então**: Deve criar ErrorResponse com fieldErrors
- **E**: fieldErrors deve conter o mapa fornecido
- **E**: details deve ser null

### Cenário 4: Verificar campos do ErrorResponse
- **Dado**: Um ErrorResponse criado
- **Quando**: Obter os campos
- **Então**: timestamp deve ser Instant
- **E**: status deve ser int
- **E**: errorCode deve ser String
- **E**: message deve ser String
- **E**: path deve ser String
- **E**: traceId deve ser String

### Cenário 5: Criar ErrorDetail
- **Dado**: code "FIELD_REQUIRED", message "Field is required", field "name", rejectedValue null
- **Quando**: Criar ErrorDetail via builder
- **Então**: Deve criar ErrorDetail com todos os campos
- **E**: code deve ser "FIELD_REQUIRED"
- **E**: message deve ser "Field is required"
- **E**: field deve ser "name"
- **E**: rejectedValue deve ser null
