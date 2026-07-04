# Caso de Teste: FlywayExecutionClient

## Descrição
Testa o client FlywayExecutionClient, verificando comunicação com serviço REST de Flyway.

## Classe Testada
`com.ia.core.flyway.view.flywayexecution.FlywayExecutionClient`

## Tipo de Teste
Unitário

## Fluxo do Teste
1. Dado um FlywayExecutionClient com WebClient mockado
2. Quando requisições são feitas ao serviço
3. Então deve chamar os endpoints corretos
4. E deve processar as respostas adequadamente

## Cenários
- Buscar execução por ID
- Listar execuções
- Aplicar migrações
- Verificar versão
- Tratamento de erros de comunicação

## Observações
- Client REST para comunicação com serviço Flyway
