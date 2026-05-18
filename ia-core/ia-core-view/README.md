# ia-core-view

## 📋 Descrição

Módulo base para a camada de apresentação usando Vaadin. Fornece componentes e padrões reutilizáveis para construir interfaces web modernas, responsivas e baseadas em MVVM (Model-View-ViewModel).

## 🏗️ Estrutura

```
ia-core-view/
├── src/main/java/
│   └── com/ia/core/view/
│       ├── component/              # Componentes Vaadin reutilizáveis
│       ├── layout/                 # Layouts base
│       ├── form/                   # Formulários genéricos
│       ├── table/                  # Grades/tabelas
│       ├── dialog/                 # Diálogos/modals
│       ├── viewmodel/              # ViewModels base
│       └── config/                 # Configurações Vaadin
├── src/main/resources/
│   ├── templates/                  # Templates (se usar)
│   └── static/                     # Recursos estáticos (CSS, JS)
└── pom.xml
```

## 🔑 Responsabilidades

- **Componentes Vaadin**: Wrappers e extensões de componentes Vaadin
- **Layouts Base**: FormLayout, GridLayout, etc. pré-configurados
- **Formulários Genéricos**: Classe base para formulários com validação
- **Tabelas/Grids**: componentes para exibição de dados com paginação
- **Diálogos**: Modals comuns (confirmação, erro, sucesso)
- **ViewModels Base**: Padrão MVVM para lógica de apresentação
- **Temas e Estilos**: CSS e configuração visual consistente

## 🛠️ Tecnologias Utilizadas

- **Vaadin 25.x**: Framework web Java moderno
- **Spring Boot Web Starter**: Servlet container
- **Spring Data Web**: Integração com paginação
- **Maven**: Build tool
- **CSS/Responsive Design**: Estilos e responsividade
- **Lombok**: Redução de boilerplate

## 📦 Dependências

- `ia-core-model` - Entidades e modelos
- `spring-boot-starter-web`
- `com.vaadin:vaadin-spring-boot-starter`
- `org.springframework.data:spring-data-commons` (paginação)

## 🔗 Relacionamentos

Depende de:
- `ia-core-model` - Entidades compartilhadas
- `ia-core-service` - Serviços de negócio (dados)

Utilizado por:
- `ia-core-security-view` - Estende com componentes de segurança
- `ia-core-quartz-view` - Estende com UI para agendamento
- `ia-core-llm-view` - Estende com UI para LLM
- Aplicações específicas que precisam de interface web

## 💡 Padrões Implementados

- **MVVM Pattern**: Separação Model-View-ViewModel
- **Component Pattern**: Componentes reutilizáveis
- **Observer Pattern**: Data binding bidirecional via Vaadin Binder
- **Template Method**: Classe base para formulários

## 🚀 Como Usar

### Criar um Formulário Base

```java
@Path("")
@Route("minha-entidade")
public class MinhaEntidadeForm extends FormView<MinhaEntidade> {

    private final TextField nome = new TextField("Nome");
    private final TextField email = new TextField("Email");
    private final Button salvar = new Button("Salvar");
    private final Button cancelar = new Button("Cancelar");

    private final MinhaEntidadeService service;

    public MinhaEntidadeForm(MinhaEntidadeService service) {
        this.service = service;
        initLayout();
    }

    private void initLayout() {
        // Configurar campos
        nome.setRequired(true);
        email.setRequired(true);

        // Configurar listener para salvar
        salvar.addClickListener(e -> salvar());
        cancelar.addClickListener(e -> cancelar());

        // Layout
        HorizontalLayout botoes = new HorizontalLayout(salvar, cancelar);
        VerticalLayout layout = new VerticalLayout(nome, email, botoes);
        add(layout);
    }

    private void salvar() {
        MinhaEntidade entidade = binder.getBean();
        if (binder.writeBeanIfValid(entidade)) {
            service.save(entidade);
            Notification.show("Salvo com sucesso!");
            limparFormulario();
        }
    }

    private void cancelar() {
        limparFormulario();
    }

    @Override
    public void setBean(MinhaEntidade bean) {
        binder.setBean(bean);
    }
}
```

### Criar uma Grade de Dados

```java
@Route("minha-entidade/lista")
public class MinhaEntidadeGrid extends VerticalLayout {

    private final Grid<MinhaEntidade> grid = new Grid<>(MinhaEntidade.class, false);
    private final MinhaEntidadeService service;

    public MinhaEntidadeGrid(MinhaEntidadeService service) {
        this.service = service;
        initGrid();
    }

    private void initGrid() {
        // Adicionar colunas
        grid.addColumn(MinhaEntidade::getId).setHeader("ID");
        grid.addColumn(MinhaEntidade::getNome).setHeader("Nome");
        grid.addColumn(MinhaEntidade::getEmail).setHeader("Email");

        // Ações customizadas
        grid.addColumn(createComponentColumn()).setHeader("Ações");

        // Configurar fonte de dados com paginação
        GridListDataView<MinhaEntidade> dataView = grid.setItems(
            query -> service.findAll(
                PageRequest.of(
                    query.getOffset() / query.getLimit(),
                    query.getLimit()
                )
            ).stream()
        );

        grid.setHeight("100%");
        add(grid);
    }

    private static ComponentColumn<MinhaEntidade> createComponentColumn() {
        return new ComponentColumn<>(item -> {
            Button editar = new Button("Editar");
            Button deletar = new Button("Deletar");
            return new HorizontalLayout(editar, deletar);
        });
    }
}
```

### Criar um Diálogo Customizado

```java
public class ConfirmacaoDialog extends Dialog {

    public ConfirmacaoDialog(String titulo, String mensagem,
                            Runnable onConfirm) {
        this.setHeaderTitle(titulo);

        Paragraph texto = new Paragraph(mensagem);
        Button confirmar = new Button("Confirmar", event -> {
            onConfirm.run();
            this.close();
        });
        Button cancelar = new Button("Cancelar", event -> this.close());

        HorizontalLayout botoes = new HorizontalLayout(confirmar, cancelar);
        add(texto, botoes);
    }
}
```

### Usar um Componente em uma View

```java
@Route("minha-pagina")
public class MinhaPage extends VerticalLayout {

    public MinhaPage(MinhaEntidadeForm form, MinhaEntidadeGrid grid) {
        HorizontalLayout container = new HorizontalLayout(form, grid);
        container.setFlexGrow(1, form);
        container.setFlexGrow(1, grid);
        add(container);
    }
}
```

## 📚 Componentes Comuns do Vaadin

| Componente | Descrição |
|------------|-----------|
| `TextField` | Campo de texto simples |
| `TextArea` | Área de texto multilinha |
| `Select` | Seletor dropdown |
| `DatePicker` | Seletor de data |
| `Grid` | Tabela de dados |
| `Button` | Botão |
| `Dialog` | Modal/diálogo |
| `Notification` | Notificação toast |
| `VerticalLayout` | Layout vertical |
| `HorizontalLayout` | Layout horizontal |

## 🎨 Temas

Vaadin utiliza temas baseados em CSS. Coloque temas customizados em:
```
src/main/resources/META-INF/resources/frontend/styles/
```

## 🔀 Data Binding

Use Vaadin Binder para sincronizar modelo ↔ view:

```java
private Binder<MinhaEntidade> binder = new Binder<>(MinhaEntidade.class);

// Vinculação simples
binder.bind(nome, MinhaEntidade::getNome, MinhaEntidade::setNome);

// Vinculação com conversor
binder.bind(
    emailField,
    MinhaEntidade::getEmail,
    MinhaEntidade::setEmail
)
.asRequired("Email obrigatório")
.withValidator(new EmailValidator("Email inválido"));
```

## 🧪 Testes

Testes em `src/test/java/`:

```java
@SpringBootTest
public class MinhaEntidadeGridTest {

    @Autowired
    private MinhaEntidadeGrid grid;

    @MockBean
    private MinhaEntidadeService service;

    @Test
    public void testGridLoad() {
        List<MinhaEntidade> dados = Arrays.asList(/* ... */);
        when(service.findAll()).thenReturn(dados);

        // Verificar que grid está populado
        // (teste de integração com Vaadin é complexo)
    }
}
```

## 📖 Configuração

### application.yml

```yaml
vaadin:
  url-mapping: /*
  frontend:
    endpoint-port: 3000

spring:
  web:
    locale: pt_BR
```

## 🤝 Contribuição

Ao adicionar novos componentes:
1. Estenda `VerticalLayout` ou `HorizontalLayout` apropriado
2. Injete serviços necessários
3. Use tipos genéricos para reutilização
4. Adicione comentários JavaDoc
5. Implemente testes básicos

## 📝 Notas

- Vaadin renderiza componentes no servidor (não é SPA)
- Data binding é automático com Binder
- Componentes são instâncias Java, não HTML
- Lazy loading de dados para grids grandes é essencial

## 🔍 Referências

- [Vaadin Documentation](https://vaadin.com/docs/latest)
- [Vaadin Components](https://vaadin.com/components)
- [Spring Vaadin Integration](https://spring.io/projects/spring-vaadin)


