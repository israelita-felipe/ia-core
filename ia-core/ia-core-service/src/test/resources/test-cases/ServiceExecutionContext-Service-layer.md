# Caso de Teste: ServiceExecutionContext

## Descrição
Testa a classe ServiceExecutionContext que encapsula o estado durante a execução de operações de serviço.

## Classe Testada
`com.ia.core.service.context.ServiceExecutionContext`

## Fluxo do Teste
1. Testar criação de contexto com DTO
2. Testar métodos setters e getters
3. Testar métodos fluentes
4. Testar cancelamento de operação
5. Testar verificação de atualização

## Cenários

### Cenário 1: Criar contexto com DTO
- **Dado**: Um DTO para operação
- **Quando**: Criar ServiceExecutionContext
- **Então**: Deve inicializar com toSave
- **E**: model deve ser null
- **E**: savedEntity deve ser null
- **E**: result deve ser null
- **E**: cancelled deve ser false

### Cenário 2: Definir e obter model
- **Dado**: Um ServiceExecutionContext
- **Quando**: Chamar setModel() com uma entidade
- **Então**: getModel() deve retornar a entidade
- **E**: Deve retornar a própria instância (fluent)

### Cenário 3: Definir e obter savedEntity
- **Dado**: Um ServiceExecutionContext
- **Quando**: Chamar setSavedEntity() com uma entidade salva
- **Então**: getSavedEntity() deve retornar a entidade
- **E**: Deve retornar a própria instância (fluent)

### Cenário 4: Definir e obter result
- **Dado**: Um ServiceExecutionContext
- **Quando**: Chamar setResult() com um DTO
- **Então**: getResult() deve retornar o DTO
- **E**: Deve retornar a própria instância (fluent)

### Cenário 5: Cancelar operação com motivo
- **Dado**: Um ServiceExecutionContext
- **Quando**: Chamar cancel() com motivo
- **Então**: isCancelled() deve retornar true
- **E**: getCancelReason() deve retornar o motivo

### Cenário 6: Cancelar operação sem motivo
- **Dado**: Um ServiceExecutionContext
- **Quando**: Chamar cancel() com null
- **Então**: isCancelled() deve retornar true
- **E**: getCancelReason() deve retornar null

### Cenário 7: Verificar se é atualização com ID
- **Dado**: Um ServiceExecutionContext com model com ID
- **Quando**: Chamar isUpdate()
- **Então**: Deve retornar true

### Cenário 8: Verificar se é atualização sem ID
- **Dado**: Um ServiceExecutionContext com model sem ID
- **Quando**: Chamar isUpdate()
- **Então**: Deve retornar false

### Cenário 9: Verificar se é atualização com model null
- **Dado**: Um ServiceExecutionContext com model null
- **Quando**: Chamar isUpdate()
- **Então**: Deve retornar false
