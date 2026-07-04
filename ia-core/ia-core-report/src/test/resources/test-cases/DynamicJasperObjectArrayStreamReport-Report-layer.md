# Caso de Teste: DynamicJasperObjectArrayStreamReport

## Descrição
Testa a classe DynamicJasperObjectArrayStreamReport que gera relatórios dinâmicos para array de objetos stream.

## Classe Testada
`com.ia.core.report.DynamicJasperObjectArrayStreamReport`

## Fluxo do Teste
1. Testar criação de datasource para array stream
2. Testar criação dinâmica de relatório
3. Testar configuração de layout

## Cenários

### Cenário 1: Criar datasource a partir de array stream
- **Dado**: Um supplier de stream de arrays e cabeçalhos
- **Quando**: Chamar dataSource()
- **Então**: Deve retornar JRMapCollectionDataSource
- **E**: Deve converter array para mapa de colunas

### Cenário 2: Lançar exceção ao chamar getReportPath
- **Dado**: Uma instância de DynamicJasperObjectArrayStreamReport
- **Quando**: Chamar getReportPath()
- **Então**: Deve lançar IllegalArgumentException
- **E**: Mensagem deve indicar que relatório deve ser gerado dinamicamente

### Cenário 3: Verificar configuração de página
- **Dado**: Uma instância de DynamicJasperObjectArrayStreamReport
- **Quando**: Verificar métodos de configuração
- **Então**: getPageWidth() deve retornar 595
- **E**: getPageHeight() deve retornar 842
- **E**: getColumnWidth() deve retornar 555
