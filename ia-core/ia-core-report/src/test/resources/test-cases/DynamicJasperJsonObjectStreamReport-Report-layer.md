# Caso de Teste: DynamicJasperJsonObjectStreamReport

## Descrição
Testa a classe DynamicJasperJsonObjectStreamReport que gera relatórios dinâmicos para JSON stream.

## Classe Testada
`com.ia.core.report.DynamicJasperJsonObjectStreamReport`

## Fluxo do Teste
1. Testar criação de datasource para JSON stream
2. Testar criação dinâmica de relatório
3. Testar configuração de layout

## Cenários

### Cenário 1: Criar datasource a partir de JSON stream
- **Dado**: Um stream de strings JSON e cabeçalhos
- **Quando**: Chamar dataSource()
- **Então**: Deve retornar JRMapCollectionDataSource
- **E**: Deve converter JSON para mapa de colunas

### Cenário 2: Lançar exceção ao chamar getReportPath
- **Dado**: Uma instância de DynamicJasperJsonObjectStreamReport
- **Quando**: Chamar getReportPath()
- **Então**: Deve lançar IllegalArgumentException
- **E**: Mensagem deve indicar que relatório deve ser gerado dinamicamente

### Cenário 3: Verificar configuração de página
- **Dado**: Uma instância de DynamicJasperJsonObjectStreamReport
- **Quando**: Verificar métodos de configuração
- **Então**: getPageWidth() deve retornar 595
- **E**: getPageHeight() deve retornar 842
- **E**: getColumnWidth() deve retornar 555
