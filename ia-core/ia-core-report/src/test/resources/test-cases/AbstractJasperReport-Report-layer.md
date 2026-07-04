# Caso de Teste: AbstractJasperReport

## Descrição
Testa a classe abstrata AbstractJasperReport que fornece funcionalidades base para relatórios JasperReports.

## Classe Testada
`com.ia.core.report.AbstractJasperReport`

## Fluxo do Teste
1. Testar enum ExportType
2. Testar métodos de exportação
3. Testar métodos de compilação de relatório
4. Testar validação de caminho do relatório

## Cenários

### Cenário 1: Verificar valores do enum ExportType
- **Dado**: O enum ExportType
- **Quando**: Listar todos os valores
- **Então**: Deve incluir PDF e XLS

### Cenário 2: Verificar contentType do PDF
- **Dado**: O valor ExportType.PDF
- **Quando**: Obter o contentType
- **Então**: Deve ser "application/pdf"

### Cenário 3: Verificar extensão do PDF
- **Dado**: O valor ExportType.PDF
- **Quando**: Obter a extensão
- **Então**: Deve ser "pdf"

### Cenário 4: Verificar contentType do XLS
- **Dado**: O valor ExportType.XLS
- **Quando**: Obter o contentType
- **Então**: Deve ser "application/vnd.ms-excel"

### Cenário 5: Verificar extensão do XLS
- **Dado**: O valor ExportType.XLS
- **Quando**: Obter a extensão
- **Então**: Deve ser "xls"

### Cenário 6: Lançar exceção para caminho null
- **Dado**: Um caminho null
- **Quando**: Chamar report(null)
- **Então**: Deve lançar NullPointerException
