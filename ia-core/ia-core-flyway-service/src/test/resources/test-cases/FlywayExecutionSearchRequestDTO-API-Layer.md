# Caso de Teste: FlywayExecutionSearchRequest

## Descrição
Testa o DTO FlywayExecutionSearchRequest, verificando campos de busca de execuções Flyway.

## Classe Testada
`com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionSearchRequest`

## Tipo de Teste
Unitário

## Fluxo do Teste
1. Dado uma instância de FlywayExecutionSearchRequest
2. Quando os campos de busca são preenchidos
3. Então deve manter os critérios de busca corretamente

## Cenários
- Busca por versão
- Busca por status
- Busca por período
- Busca com múltiplos critérios
- Busca sem critérios (lista todos)

## Observações
- DTO para critérios de busca de execuções
