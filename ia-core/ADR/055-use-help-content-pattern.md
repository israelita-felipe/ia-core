# ADR-055: Padrão de Help Online em Vaadin

## Status

**ACEITO** (2026-06-25)

## Contexto

Aplicações Vaadin necessitam de um sistema de ajuda contextual que permita:
1. Exibir helper text em campos de formulário (texto curto sempre visível)
2. Mostrar conteúdo de ajuda detalhado via diálogo (help online)
3. Fornecer informações explicativas sobre campos e páginas
4. Manter a implementação apenas na camada de view (ia-core-view)

Pesquisas na documentação oficial do Vaadin e em repositórios GitHub indicam que:
- **Helper text** é o padrão recomendado pela documentação Vaadin para fornecer informações adicionais sobre campos
- Helper text usa o slot "helper" do Vaadin, que é sempre visível e acessível
- **Tooltips** são menos acessíveis e não funcionam bem em dispositivos touch
- **Dialog** é o componente padrão para exibir conteúdo detalhado em modal
- O padrão ContextHelp (GitHub: zch/ContextHelp) mostra ajuda via tecla F1, mas é menos intuitivo que um botão clicável

## Decisão

Adotamos o padrão de **Help Online em Vaadin** com as seguintes características:

### Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                    Camada de View (ia-core-view)            │
├─────────────────────────────────────────────────────────────┤
│  HasHelp (interface)                                        │
│    - setHelp(Component, String) - helper text inline       │
│    - getHelpText() - texto curto para helper               │
│    - getHelpTitle() - título para diálogo                   │
│    - getHelpDescription() - descrição detalhada             │
│    - getHelpFields() - mapa Component -> Component de ajuda │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  HelpOnlineComponent (Button)                               │
│    - Botão com ícone "?"                                     │
│    - Abre Dialog com conteúdo de ajuda                      │
│    - Usa HasHelp para obter conteúdo                        │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  FormView (CustomField)                                     │
│    - Adiciona automaticamente HelpOnlineComponent           │
│    - Subclasses podem implementar HasHelp                   │
└─────────────────────────────────────────────────────────────┘
```

### Implementação

#### 1. Interface HasHelp (incrementada)

A interface `HasHelp` já existente foi incrementada com novos métodos:

```java
public interface HasHelp extends HasElement {
    // Métodos existentes (mantidos para compatibilidade)
    Component createHelpComponentFromText(String help);
    boolean isHelpVisible();
    void setHelp(Component hasHelper, Component help);
    void setHelp(Component hasHelper, String help);

    // Novos métodos para help online
    String getHelpText();        // Texto curto para helper inline
    String getHelpTitle();      // Título para diálogo
    String getHelpDescription(); // Descrição detalhada
    Map<Component, Component> getHelpFields(); // Mapa componente -> componente de ajuda
}
```

**Método `getHelpFields()`**:
- Percorre **recursivamente** todos os elementos filhos do elemento raiz e recupera componentes de ajuda mapeados através do slot "helper"
- Retorna `Map<Component, Component>` onde a chave é o componente que recebeu ajuda e o valor é o componente de ajuda
- Útil para exibir lista de campos com suas descrições em diálogo de help online
- **Importante**: Componentes devem estar adicionados ao view (via `add()`) para serem encontrados pelo método

#### 2. HelpOnlineComponent

Componente que adiciona um botão de ajuda (?) que abre um diálogo:

```java
public class HelpOnlineComponent extends Button {
    public HelpOnlineComponent(HasHelp hasHelp);
    public HelpOnlineComponent(HasHelp hasHelp, String customHelpText);
}
```

O diálogo exibe:
- Título (se definido em `getHelpTitle()`)
- Descrição detalhada (se definida em `getHelpDescription()`)
- Lista de campos com seus componentes de ajuda (se definido em `getHelpFields()`)

O método `getHelpFields()` percorre os filhos do elemento raiz e recupera os componentes de ajuda
mapeados através do slot "helper" de cada filho. As chaves são os componentes que receberam ajuda
e os valores são os componentes de ajuda associados.

#### 3. FormView Integration

A classe `FormView` foi atualizada para adicionar automaticamente um `HelpOnlineComponent` no construtor:

```java
public FormView(IFormViewModel<T> viewModel) {
    this.binder = createBinder(viewModel);
    setId(createId());
    super.add(new HelpOnlineComponent(this));  // Adiciona botão de ajuda automaticamente
    super.add(layout);
    setSizeFull();
    createLayout();
    // ...
}
```

Subclasses de `FormView` podem implementar `HasHelp` para fornecer conteúdo de ajuda:

```java
public class MyFormView extends FormView<MyDTO> implements HasHelp {
    @Override
    public String getHelpTitle() {
        return "Título do Formulário";
    }

    @Override
    public String getHelpDescription() {
        return "Descrição detalhada do formulário";
    }

    @Override
    public Map<Component, Component> getHelpFields() {
        // Retorna mapa de campos com seus componentes de ajuda
        // Os campos devem ter sido configurados com setHelp() anteriormente
        return super.getHelpFields();
    }
}
```

### Padrões de Uso

#### Para Campos (Helper Text Inline):

```java
TextField nomeField = new TextField("Nome");
nomeField.setHelperText("Digite o nome completo do autor");
```

Ou usando `HasHelp`:

```java
TextField nomeField = new TextField("Nome");
nomeField.setHelp(nomeField, "Digite o nome completo do autor");
```

#### Para Views (Help Online):

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

#### Para Componentes Específicos:

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

#### Para FormView (Integração Automática):

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

#### Padrão de Internacionalização com Translator

Para suportar internacionalização, use o padrão `Translator` (ADR-003) para as chaves de ajuda:

**1. Adicione as chaves no Translator:**

```java
public class PessoaTranslator {
    public static final class HELP {
        public static final String FORM_HELP_TEXT = "pessoa.form.help.text";
        public static final String FORM_HELP_TITLE = "pessoa.form.help.title";
        public static final String FORM_HELP_DESCRIPTION = "pessoa.form.help.description";
        public static final String PAGE_HELP_TEXT = "pessoa.page.help.text";
        public static final String PAGE_HELP_TITLE = "pessoa.page.help.title";
        public static final String PAGE_HELP_DESCRIPTION = "pessoa.page.help.description";
    }
}
```

**2. Adicione as traduções no arquivo de propriedades:**

```properties
# PESSOA FORM HELP
pessoa.form.help.text=Formulário de cadastro e edição de pessoas e empresas
pessoa.form.help.title=Formulário de Pessoa
pessoa.form.help.description=Este formulário permite cadastrar e editar informações de pessoas físicas, pessoas jurídicas e pessoas estrangeiras. O formulário é organizado em abas: Informações Gerais (contém campos como tipo, nome, documento, estado civil, datas de nascimento/óbito/abertura/encerramento, sexo e observações), Contatos (gerencia telefones, emails e outros contatos) e Endereços (gerencia endereços residenciais e comerciais).

# PESSOA PAGE HELP
pessoa.page.help.text=Página de gestão de pessoas e empresas
pessoa.page.help.title=Página de Pessoa
pessoa.page.help.description=Esta página permite gerenciar o cadastro de pessoas e empresas do sistema. A página é composta por uma lista de pessoas/empresas cadastradas com funcionalidades de busca, filtragem, paginação e ações (criar, editar, excluir, detalhar).
```

**3. Implemente os métodos na View usando o Translator:**

```java
public class PessoaFormView extends FormView<PessoaDTO> {

    @Override
    public String getHelpText() {
        return $(PessoaTranslator.HELP.FORM_HELP_TEXT);
    }

    @Override
    public String getHelpTitle() {
        return $(PessoaTranslator.HELP.FORM_HELP_TITLE);
    }

    @Override
    public String getHelpDescription() {
        return $(PessoaTranslator.HELP.FORM_HELP_DESCRIPTION);
    }
}
```

**Regra Importante**: O método `getHelpDescription()` deve descrever o componente e seus elementos:
- **Para FormView**: Descreve o formulário, seus campos, abas e funcionalidades
- **Para PageView**: Descreve a página, sua lista, ações e componentes principais

## Consequências

### Positivas
- **Simplicidade**: Implementação apenas na camada de view, sem dependências de outras camadas
- **Padrão Vaadin**: Usa o slot "helper" que é o padrão recomendado pela documentação
- **Acessibilidade**: Helper text é sempre visível e acessível, ao contrário de tooltips
- **Flexibilidade**: Pode ser usado em qualquer componente que implemente `HasHelp`
- **Não intrusivo**: Botão de ajuda é opcional e não interfere no layout
- **Compatibilidade**: Mantém métodos existentes de `HasHelp` para não quebrar código

### Negativas
- **Manual**: Não gera manual do usuário automaticamente (isso pode ser implementado separadamente se necessário)
- **Acoplamento**: Views precisam implementar `HasHelp` para ter help online
- **Sem i18n automático**: Textos de ajuda são estáticos (pode ser integrado com Translator se necessário)

### Considerações de Segurança

**OWASP Sanitizer Integrado**:
- O `FlexmarkHelpRenderer` (implementado no MVP) inclui sanitização OWASP integrada
- Remove automaticamente tags `<script>`, `<style>`, `<iframe>` e atributos perigosos
- O HTML gerado pelo flexmark é sanitizado antes de ser exibido no diálogo
- **Status**: ✅ Implementado e ativo (2026-07-01)

**Recomendações**:
- Textos de ajuda devem ser estáticos (hardcoded)
- Se usar input de usuário, o sanitizer OWASP já está ativo
- CSP headers podem ser adicionados como camada adicional de segurança

## Referências

F### RFCs Relevantes
- **RFC 3629** - UTF-8, a transformation format of ISO 10646 (codificação obrigatória)
- **RFC 5646** - Tags for Identifying Languages (tags de idioma: pt-BR, en-US)
- **RFC 8288** - Web Linking (links de navegação na documentação)

### WCAG 2.1
- Acessibilidade do diálogo
- Contraste de cores
- Navegação via teclado
- Compatibilidade com leitores de tela

### Outras Referências
- [ADR-003](./003-use-translator-for-i18n.md) - Padrão de internacionalização
- [ADR-012](./012-testing-patterns.md) - Padrões de teste
- Vaadin Dialog Documentation: https://vaadin.com/docs/latest/components/dialog
- flexmark-java: https://github.com/vsch/flexmark-java
- OWASP Java HTML Sanitizer: https://github.com/OWASP/java-html-sanitizer
- html2canvas: https://github.com/niklasvh/html2canvas
