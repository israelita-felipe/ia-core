# ia-core-report

## 📋 Descrição

Módulo de geração de relatórios usando JasperReports. Fornece funcionalidades para criar, renderizar e exportar relatórios em diversos formatos (PDF, Excel, Word, HTML, etc.).

## 🏗️ Estrutura

```
ia-core-report/
├── src/main/java/
│   └── com/ia/core/report/
│       ├── service/                # Serviço de relatórios
│       ├── builder/                # Builders para relatórios
│       ├── template/               # Templates (JRXML)
│       └── util/                   # Utilitários
├── src/main/resources/
│   └── reports/                    # Arquivos .jrxml
│       ├── user_report.jrxml
│       ├── financial_report.jrxml
│       └── templates/
└── pom.xml
```

## 🔑 Responsabilidades

- **ReportService**: Gerencia geração de relatórios
- **Report Builder**: Constrói relatórios programaticamente
- **Template Management**: Carrega e gerencia templates JRXML
- **Data Binding**: Liga dados a relatórios
- **Export**: Converte para PDF, Excel, HTML, etc.
- **Print Support**: Suporte a impressão

## 🛠️ Tecnologias Utilizadas

- **JasperReports**: Engine de relatórios
- **Jasper Studio**: Designer de templates (externo)
- **iText**: Geração de PDF
- **Apache POI**: Geração de Excel
- **Spring Boot**

## 📦 Dependências

- `ia-core-service` - Serviços base
- `net.sf.jasperreports:jasperreports`
- `org.apache.poi:poi-ooxml` (Excel)
- `com.itextpdf:itextpdf` (PDF)

## 🔗 Relacionamentos

Depende de:
- `ia-core-service` - Dados dos relatórios

Utilizado por:
- `ia-core-rest` - Endpoints para download de relatórios
- Qualquer módulo que precise gerar relatórios

## 💡 Padrões Implementados

- **Builder Pattern**: Construção de relatórios complexos
- **Strategy Pattern**: Diferentes formatos de export
- **Template Method**: Geração base de relatórios

## 🚀 Como Usar

### Implementar ReportService

```java
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private JasperReportsContext jasperContext;

    /**
     * Gera um relatório em PDF
     */
    public byte[] generateUserReport(ReportParameters params) throws JRException {
        // 1. Carregar template
        JasperReport jasperReport = JasperCompileManager
            .compileReport(getTemplateStream("user_report.jrxml"));

        // 2. Preparar dados
        List<UserReport> dados = getUserReportData(params);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

        // 3. Preparar parâmetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportTitle", "Relatório de Usuários");
        parameters.put("generatedDate", LocalDateTime.now());
        parameters.put("userName", params.getUserName());

        // 4. Fill report
        JasperPrint jasperPrint = JasperFillManager
            .fillReport(jasperReport, parameters, dataSource);

        // 5. Exportar para PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Gera relatório em Excel
     */
    public byte[] generateExcelReport(ReportParameters params) throws JRException {
        // Similar ao PDF mas usando SimpleXlsxExporter
        JasperReport jasperReport = JasperCompileManager
            .compileReport(getTemplateStream("user_report.jrxml"));

        List<UserReport> dados = getUserReportData(params);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportTitle", "Relatório de Usuários");

        JasperPrint jasperPrint = JasperFillManager
            .fillReport(jasperReport, parameters, dataSource);

        // Exportar para Excel
        SimpleXlsxExporter exporter = new SimpleXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
            new SimpleOutputStreamExporterOutput(new ByteArrayOutputStream())
        );
        exporter.exportReport();

        return /* bytes */;
    }

    /**
     * Gera relatório em HTML
     */
    public String generateHtmlReport(ReportParameters params) throws JRException {
        JasperReport jasperReport = JasperCompileManager
            .compileReport(getTemplateStream("user_report.jrxml"));

        List<UserReport> dados = getUserReportData(params);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

        JasperPrint jasperPrint = JasperFillManager
            .fillReport(jasperReport, new HashMap<>(), dataSource);

        ByteArrayOutputStream htmlOutput = new ByteArrayOutputStream();
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(htmlOutput));
        exporter.exportReport();

        return htmlOutput.toString();
    }

    private List<UserReport> getUserReportData(ReportParameters params) {
        // Buscar dados do BD e mapear para DTO
        return /* dados formatados */;
    }

    private InputStream getTemplateStream(String templateName) {
        return getClass().getClassLoader()
            .getResourceAsStream("reports/" + templateName);
    }
}
```

### Usar ReportService em Controller

```java
@RestController
@RequestMapping("/api/${api.version}/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/users/pdf")
    public ResponseEntity<byte[]> getUserReportPdf(
            @RequestParam(required = false) String userName)
            throws JRException {

        ReportParameters params = new ReportParameters();
        params.setUserName(userName);

        byte[] report = reportService.generateUserReport(params);

        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users_report.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(report);
    }

    @GetMapping("/users/excel")
    public ResponseEntity<byte[]> getUserReportExcel(
            @RequestParam(required = false) String userName)
            throws JRException {

        ReportParameters params = new ReportParameters();
        params.setUserName(userName);

        byte[] report = reportService.generateExcelReport(params);

        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users_report.xlsx")
            .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(report);
    }

    @GetMapping("/users/html")
    public String getUserReportHtml(
            @RequestParam(required = false) String userName)
            throws JRException {

        ReportParameters params = new ReportParameters();
        params.setUserName(userName);

        return reportService.generateHtmlReport(params);
    }
}
```

### Estrutura de Template JRXML (Conceitual)

```xml
<jasperReport ...>
    <property name="com.jaspersoft.studio.data.defaultdataadapter" value="TableFields"/>

    <!-- Variáveis de Relatório -->
    <parameter name="reportTitle" class="java.lang.String"/>
    <parameter name="generatedDate" class="java.time.LocalDateTime"/>

    <!-- Campos de Dados -->
    <field name="username" class="java.lang.String"/>
    <field name="email" class="java.lang.String"/>
    <field name="createdAt" class="java.time.LocalDateTime"/>

    <!-- Título -->
    <title>
        <band height="50">
            <element xsi:type="textField">
                <text>Relatório de Usuários</text>
            </element>
        </band>
    </title>

    <!-- Detalhe (linha por linha)-->
    <detail>
        <band height="20">
            <textField>
                <textFieldExpression class="java.lang.String">
                    <![CDATA[$F{username}]]>
                </textFieldExpression>
            </textField>
            <textField>
                <textFieldExpression class="java.lang.String">
                    <![CDATA[$F{email}]]>
                </textFieldExpression>
            </textField>
        </band>
    </detail>
</jasperReport>
```

## 🎨 Formatos de Exportação

| Formato | Descrição | Use Case |
|---------|-----------|----------|
| **PDF** | Portável, impressão, seguro | Relatórios finais |
| **Excel** | Dados tabulares, análise | Dados para análise |
| **HTML** | Web, visualização | Dashboard |
| **CSV** | Simples, texto | Integração |
| **Docx** | Word, editável | Documentos finais |

## 🧪 Testes

```java
@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void testGeneratePdfReport() throws JRException {
        ReportParameters params = new ReportParameters();

        byte[] report = reportService.generateUserReport(params);

        assertNotNull(report);
        assertTrue(report.length > 0);
        // Verificar assinatura PDF
        assertTrue(report[0] == 0x25); // %
        assertTrue(report[1] == 0x50); // P
        assertTrue(report[2] == 0x44); // D
        assertTrue(report[3] == 0x46); // F
    }
}
```

## ⚙️ Configuração

### application.yml

```yaml
jasper:
  reporting:
    temp-directory: /tmp/jasper
    cache-templates: true
    export-formats:
      - pdf
      - excel
      - html
```

## 🤝 Contribuição

Ao adicionar novos relatórios:
1. Crie template `.jrxml` com design tool
2. Implemente método em `ReportService`
3. Crie DTO de parâmetros
4. Adicione endpoint REST
5. Mantenha templates em `src/main/resources/reports/`

## 📝 Notas

- Templates devem ser versionados
- Cache templates para melhor performance
- Exports grandes devem ser async
- Validar dados antes de passar ao relatório

## 🔍 Referências

- [JasperReports Documentation](https://community.jaspersoft.com/project/jasperreports-library)
- [Jasper Studio](https://www.jaspersoft.com/products/jaspersoft-studio)


