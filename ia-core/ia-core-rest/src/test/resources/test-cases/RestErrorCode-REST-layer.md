# Caso de Teste: RestErrorCode

## Descrição
Testa o enum RestErrorCode que contém códigos de erro padronizados para a API REST.

## Classe Testada
`com.ia.core.rest.error.RestErrorCode`

## Fluxo do Teste
1. Testar valores dos códigos de erro 4xx
2. Testar valores dos códigos de erro 5xx
3. Testar método getHttpStatusValue()
4. Testar getters dos campos

## Cenários

### Cenário 1: Verificar AUTHENTICATION_ERROR
- **Dado**: O enum AUTHENTICATION_ERROR
- **Quando**: Obter os valores
- **Então**: code deve ser "AUTHENTICATION_ERROR"
- **E**: httpStatus deve ser 401
- **E**: translationKey deve ser "error.authentication"

### Cenário 2: Verificar ACCESS_DENIED
- **Dado**: O enum ACCESS_DENIED
- **Quando**: Obter os valores
- **Então**: code deve ser "ACCESS_DENIED"
- **E**: httpStatus deve ser 403
- **E**: translationKey deve ser "error.access.denied"

### Cenário 3: Verificar ENTITY_NOT_FOUND
- **Dado**: O enum ENTITY_NOT_FOUND
- **Quando**: Obter os valores
- **Então**: code deve ser "ENTITY_NOT_FOUND"
- **E**: httpStatus deve ser 404
- **E**: translationKey deve ser "error.entity.not.found"

### Cenário 4: Verificar VALIDATION_ERROR
- **Dado**: O enum VALIDATION_ERROR
- **Quando**: Obter os valores
- **Então**: code deve ser "VALIDATION_ERROR"
- **E**: httpStatus deve ser 400
- **E**: translationKey deve ser "error.validation"

### Cenário 5: Verificar DATA_INTEGRITY_VIOLATION
- **Dado**: O enum DATA_INTEGRITY_VIOLATION
- **Quando**: Obter os valores
- **Então**: code deve ser "DATA_INTEGRITY_VIOLATION"
- **E**: httpStatus deve ser 409
- **E**: translationKey deve ser "error.data.integrity"

### Cenário 6: Verificar SERVICE_ERROR
- **Dado**: O enum SERVICE_ERROR
- **Quando**: Obter os valores
- **Então**: code deve ser "SERVICE_ERROR"
- **E**: httpStatus deve ser 400
- **E**: translationKey deve ser "error.service"

### Cenário 7: Verificar INTERNAL_ERROR
- **Dado**: O enum INTERNAL_ERROR
- **Quando**: Obter os valores
- **Então**: code deve ser "INTERNAL_ERROR"
- **E**: httpStatus deve ser 500
- **E**: translationKey deve ser "error.internal"

### Cenário 8: Verificar método getHttpStatusValue()
- **Dado**: Qualquer enum RestErrorCode
- **Quando**: Chamar getHttpStatusValue()
- **Então**: Deve retornar o valor do campo httpStatus
