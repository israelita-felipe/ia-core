# Caso de Teste: AbstractJasperCollectionReport

## Descrição
Testa a classe abstrata AbstractJasperCollectionReport que extende AbstractJasperReport para coleções.

## Classe Testada
`com.ia.core.report.AbstractJasperCollectionReport`

## Fluxo do Teste
1. Testar criação de datasource para coleção
2. Testar herança de AbstractJasperReport

## Cenários

### Cenário 1: Criar datasource a partir de coleção
- **Dado**: Uma coleção de objetos
- **Quando**: Chamar dataSource()
- **Então**: Deve retornar JRBeanCollectionDataSource
- **E**: DataSource deve conter os dados da coleção
