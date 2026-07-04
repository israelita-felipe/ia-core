# Caso de Teste: CrudOperationType

## Descrição
Testa o enum CrudOperationType que representa os tipos de operação CRUD padrão para eventos de domínio.

## Classe Testada
`com.ia.core.service.event.CrudOperationType`

## Fluxo do Teste
1. Testar valores do enum
2. Testar métodos de verificação
3. Testar implementação de EventType

## Cenários

### Cenário 1: Verificar CREATED
- **Dado**: O enum CREATED
- **Quando**: Obter os valores
- **Então**: name deve ser "CREATED"
- **E**: isCreated() deve retornar true
- **E**: isUpdated() deve retornar false
- **E**: isDeleted() deve retornar false

### Cenário 2: Verificar UPDATED
- **Dado**: O enum UPDATED
- **Quando**: Obter os valores
- **Então**: name deve ser "UPDATED"
- **E**: isCreated() deve retornar false
- **E**: isUpdated() deve retornar true
- **E**: isDeleted() deve retornar false

### Cenário 3: Verificar DELETED
- **Dado**: O enum DELETED
- **Quando**: Obter os valores
- **Então**: name deve ser "DELETED"
- **E**: isCreated() deve retornar false
- **E**: isUpdated() deve retornar false
- **E**: isDeleted() deve retornar true

### Cenário 4: Verificar implementação de EventType
- **Dado**: Qualquer enum CrudOperationType
- **Quando**: Verificar a interface implementada
- **Então**: Deve implementar EventType
- **E**: Deve ter método name()
