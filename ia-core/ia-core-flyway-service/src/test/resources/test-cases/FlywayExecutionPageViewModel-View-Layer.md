# Caso de Teste: FlywayExecutionPageViewModel

## Descrição
Testa o view model FlywayExecutionPageViewModel, verificando binding de dados e operações na página.

## Classe Testada
`com.ia.core.flyway.view.flywayexecution.page.FlywayExecutionPageViewModel`

## Tipo de Teste
Unitário

## Fluxo do Teste
1. Dado um FlywayExecutionPageViewModel
2. Quando os dados são carregados
3. Então deve manter o binding correto
4. E deve expor métodos de navegação

## Cenários
- Carregar lista de execuções
- Navegar para detalhes
- Aplicar migrações
- Verificar status
- Filtrar execuções

## Observações
- View model para página de execução Flyway
