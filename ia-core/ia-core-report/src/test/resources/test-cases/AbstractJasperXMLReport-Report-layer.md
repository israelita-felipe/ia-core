# Caso de Teste: AbstractJasperXMLReport

## Descrição
Testa a classe abstrata AbstractJasperXMLReport que extende AbstractJasperReport para XML.

## Classe Testada
`com.ia.core.report.AbstractJasperXMLReport`

## Fluxo do Teste
1. Testar criação de datasource para XML
2. Testar herança de AbstractJasperReport

## Cenários

### Cenário 1: Criar datasource a partir de XML
- **Dado**: Uma string XML
- **Quando**: Chamar dataSource()
- **Então**: Deve retornar JRXmlDataSource
- **E**: DataSource deve ser criado a partir do XML
