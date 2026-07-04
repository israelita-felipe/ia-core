# ia-core-view

## 📋 Descrição

Módulo base para a camada de apresentação usando Vaadin. Fornece componentes e padrões reutilizáveis para construir interfaces web modernas, responsivas e baseadas em MVVM (Model-View-ViewModel).

### Módulo de Help Online

Este módulo fornece um sistema de ajuda contextual para componentes Vaadin:

- **HasHelp**: Interface para componentes que possuem ajuda contextual
- **HelpOnlineComponent**: Componente Vaadin para exibição de ajuda online via diálogo
- **FlexmarkHelpRenderer**: Renderizador seguro de Markdown para HTML (com OWASP sanitizer)
- **HelpDialogViewFactory**: Fábrica de diálogos com header (sem toolbar)
- **HelpScreenshotComponent**: Componente utilitário para captura de screenshots via html2canvas

**Status:** v1.0.2 ✅ (2026-07-03)
- 66+ testes passando
- Renderização Markdown segura (OWASP sanitizer)
- Captura de screenshots recursiva
- Interação com mouse (mouseover/mouseout)
- Diálogo limpo, foco no conteúdo
- Acessibilidade WCAG 2.2 (role="dialog", aria-label, aria-expanded)

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

## 📚 Referências

### ADRs Relacionados

- [ADR-055: Help Content Pattern](ADR/055-use-help-content-pattern.md) - Padrão de ajuda online em Vaadin
- [ADR-056: Padrão de Customização de Componentes Vaadin](ADR/056-use-vaadin-component-customization-pattern.md) - CSS puro modular e responsivo
- [ADR-057: WCAG 2.2 para Acessibilidade](ADR/057-use-wcag-2-2-for-accessibility.md) - Acessibilidade em aplicações Vaadin

### CDU Relacionado

- [CDU045: Apresentar Ajuda Online](CDU/CDU045-Apresentar-Ajuda-Online/README.md) - Caso de uso completo do sistema de ajuda

## 📁 Arquivos Modificados/Criados

### Help Online (v1.0.2)

| Arquivo | Tipo | Descrição |
|---------|------|-----------|
| `src/main/java/com/ia/core/view/components/properties/HasHelp.java` | Criado | Interface para componentes com ajuda contextual |
| `src/main/java/com/ia/core/view/help/HelpOnlineComponent.java` | Criado | Componente Vaadin para exibição de ajuda via diálogo |
| `src/main/java/com/ia/core/view/help/FlexmarkHelpRenderer.java` | Criado | Renderizador seguro de Markdown para HTML |
| `src/main/java/com/ia/core/view/help/dialog/HelpDialogViewFactory.java` | Criado | Fábrica de diálogos com header (sem toolbar) |
| `src/main/java/com/ia/core/view/help/HelpScreenshotComponent.java` | Criado | Captura de screenshots via html2canvas |
| `src/main/resources/META-INF/resources/themes/core/scroll-styles.css` | Criado | Scrollbar oculta mas funcional |
| `src/main/resources/META-INF/resources/themes/core/responsive-styles.css` | Criado | Estilos responsivos mobile-first |
| `src/main/resources/META-INF/resources/themes/core/component-styles.css` | Criado | Design system de componentes |
| `src/test/java/com/ia/core/view/help/e2e/HelpOnlineAccessibilityE2eTest.java` | Criado | Testes E2E de acessibilidade WCAG 2.2 |

## 🚀 Como Usar

### Criar um Formulário Base

### Usar o Sistema de Help Online

#### 1. Helper Text em Campos (Texto Curto Sempre Visível)

```java
TextField nomeField = new TextField("Nome");
nomeField.setHelperText("Digite o nome completo do autor");
```

#### 2. Help Online em Views (Conteúdo Detalhado em Diálogo)

```java
@Route("autores")
public class AutorView extends VerticalLayout implements HasHelp {

    private TextField nomeField;
    private TextField emailField;

    public AutorView() {
        nomeField = new TextField("Nome");
        emailField = new TextField("Email");

        // Configurar helper text inline usando HasHelp
        setHelp(nomeField, "Nome completo do autor (mínimo 3 caracteres)");
        setHelp(emailField, "Email de contato do autor (opcional)");

        add(nomeField, emailField);

        // Adicionar botão de ajuda no cabeçalho
        HelpOnlineComponent helpButton = new HelpOnlineComponent(this);
        Header header = new Header("Autores", helpButton);
        add(header);
    }

    @Override
    public String getHelpTitle() {
        return "Gestão de Autores";
    }

    @Override
    public String getHelpDescription() {
        return "Tela para cadastro e gerenciamento de autores de livros. " +
               "Permite criar, editar e excluir autores do catálogo.";
    }

    @Override
    public Map<Component, Component> getHelpFields() {
        // Retorna mapa de componentes configurados com setHelp()
        return super.getHelpFields();
    }
}
```

#### 3. Help Online para Componentes Específicos

```java
TextField emailField = new TextField("Email");
emailField.setHelperText("Digite um email válido");

// Adicionar help online específico para o campo
HelpOnlineComponent fieldHelp = new HelpOnlineComponent(
    new HasHelp() {
        @Override
        public String getHelpTitle() {
            return "Email";
        }

        @Override
        public String getHelpDescription() {
            return "O email deve estar no formato usuario@dominio.com. " +
                   "Será usado para notificações e recuperação de senha.";
        }

        @Override
        public Map<Component, Component> getHelpFields() {
            return Map.of(); // Vazio pois não há campos filhos
        }
    }
);
add(emailField, fieldHelp);
```

#### 4. Help Online em FormView (Integração Automática)

```java
@Route("minha-entidade")
public class MinhaEntidadeForm extends FormView<MinhaEntidade> implements HasHelp {

    private TextField nomeField;
    private TextField emailField;

    public MinhaEntidadeForm(MinhaEntidadeService service) {
        super(new MinhaEntidadeViewModel(service));

        nomeField = createTextField("Nome", "Nome completo");
        emailField = createTextField("Email", "Email de contato");

        // Configurar helper text usando HasHelp
        setHelp(nomeField, "Nome completo (mínimo 3 caracteres)");
        setHelp(emailField, "Email no formato usuario@dominio.com");

        add(nomeField, emailField);
    }

    @Override
    public String getHelpTitle() {
        return "Formulário de Entidade";
    }

    @Override
    public String getHelpDescription() {
        return "Formulário para cadastro e edição de entidades. " +
               "Todos os campos marcados com * são obrigatórios.";
    }

    @Override
    public Map<Component, Component> getHelpFields() {
        // Retorna automaticamente os campos configurados com setHelp()
        return super.getHelpFields();
    }
}
```

**Nota**: O botão de ajuda é adicionado automaticamente pelo construtor de `FormView`, não é necessário adicioná-lo manualmente.

## 🔧 Implementação Técnica do getHelpFields()

O método `getHelpFields()` da interface `HasHelp` percorre **recursivamente** a árvore de elementos DOM do view para encontrar componentes com ajuda configurada via slot "helper".

### Como funciona:

1. **Percorrida Recursiva**: O método inicia no elemento raiz e visita todos os filhos aninhados
2. **Detecção de Slot**: Procura por elementos com atributo `slot="helper"`
3. **Mapeamento**: Cria um mapa onde a chave é o componente pai e o valor é o componente de ajuda

### Importante:

- **Componentes devem estar adicionados ao view** (via `add()`) para serem encontrados
- O método funciona com `FormView` que adiciona componentes ao `layout` interno
- Retorna `Map<Component, Component>` vazio se não houver componentes com ajuda

### Exemplo de uso:

```java
// Em um FormView, os campos são adicionados automaticamente ao layout
public class MeuForm extends FormView<MinhaEntidade> implements HasHelp {
    private TextField nomeField;

    public MeuForm() {
        super(viewModel);
        nomeField = createTextField("Nome", "Nome completo");
        setHelp(nomeField, "Digite o nome completo");
        // O campo já está adicionado ao layout via createTextField
    }

    @Override
    public Map<Component, Component> getHelpFields() {
        return super.getHelpFields(); // Retorna automaticamente o mapa
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
