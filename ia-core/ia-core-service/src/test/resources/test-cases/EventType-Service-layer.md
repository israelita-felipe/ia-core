# Caso de Teste: EventType

## Descrição
Testa a interface EventType que permite que serviços específicos definam seus próprios tipos de eventos.

## Interface Testada
`com.ia.core.service.event.EventType`

## Fluxo do Teste
1. Testar constantes padrão
2. Testar método name()
3. Testar implementação em CrudOperationType

## Cenários

### Cenário 1: Verificar constante CRIADA
- **Dado**: A constante CRIADA
- **Quando**: Chamar name()
- **Então**: Deve retornar "CRIADA"

### Cenário 2: Verificar constante ATUALIZADA
- **Dado**: A constante ATUALIZADA
- **Quando**: Chamar name()
- **Então**: Deve retornar "ATUALIZADA"

### Cenário 3: Verificar constante EXCLUIDA
- **Dado**: A constante EXCLUIDA
- **Quando**: Chamar name()
- **Então**: Deve retornar "EXCLUIDA"

### Cenário 4: Verificar método name() em CrudOperationType
- **Dado**: Um CrudOperationType
- **Quando**: Chamar name()
- **Então**: Deve retornar o nome do enum
