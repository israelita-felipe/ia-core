# Como Usar o Help Online - Guia para Desenvolvedores

## Visão Geral

O `HelpOnlineComponent` é um sistema de ajuda online integrado ao `ia-core-view` que permite adicionar documentação contextual a views e componentes.

**Novidade:** Agora usa `FrameDialogViewFactory` (padrão do projeto) para exibir ajuda em um iframe com conteúdo HTML em base64.

## Principais Componentes

### 1. `HelpOnlineComponent` (Botão de Ajuda)
- Botão ícone "?" que abre um diálogo com ajuda
- Automaticamente adicionado em `PageView` e `FormView`
- Pode ser adicionado manualmente em outros componentes

### 2. `FlexmarkHelpRenderer` (Renderizador Markdown)
- Converte Markdown para HTML usando flexmark-java
- Suporta: headings, bold, links, listas, tabelas, blocos de código
- Tratamento de erros com escaping automático

### 3. `HelpDialogViewFactory` (Diálogo de Ajuda)
- Fábrica para diálogos de ajuda (padrão do projeto)
- Exibe conteúdo via iframe com base64
- Header com título e maximizado
- Sem toolbar (diálogo limpo, foco no conteúdo)
- Compatível com WCAG

## Como Implementar

### Opção 1: Adicionar Ajuda Automaticamente em PageView/FormView

Se sua view estender `PageView` ou `FormView`, o `HelpOnlineComponent` já está incluído automaticamente.

```java
public class MinhaView extends PageView {

    public MinhaView() {
        super();

        // HasHelp é implementado em PageView
        // Quando você chama setHelp no componente, a ajuda é coletada automaticamente
    }
}
```

### Opção 2: Adicionar Ajuda em Componentes Específicos

Implemente a interface `HasHelp` no seu componente:

```java
import com.ia.core.view.components.properties.HasHelp;
import com.ia.core.view.help.HelpOnlineComponent;

public class MeuComponente extends VerticalLayout implements HasHelp {

    private TextField nomeCampo;
    private TextArea descricaoCampo;

    public MeuComponente() {
        nomeCampo = new TextField("Nome");
        descricaoCampo = new TextArea("Descrição");

        // Adiciona ajuda ao campo
        setHelp(nomeCampo, "O nome do item");
        setHelp(descricaoCampo, "Descrição detalhada");

        add(nomeCampo, descricaoCampo);

        // Adiciona botão de ajuda ao componente
        add(new HelpOnlineComponent(this));
    }

    @Override
    public String getHelpTitle() {
        return "Ajuda do Meu Componente";
    }

    @Override
    public String getHelpDescription() {
        return "Este componente permite gerenciar itens com nome e descrição.";
    }
}
```

### Opção 3: Ajuda com Texto Customizado

```java
// Para um componente específico
new HelpOnlineComponent(componente, "Texto de ajuda customizado");

// Ou em uma view
PageView view = new MinhaView();
view.add(new HelpOnlineComponent(view, "Instruções específicas para esta página"));
```

## Sintaxe Markdown Suportada

O sistema suporta os seguintes elementos Markdown:

```markdown
# Título Principal (h1)

## Subtítulo (h2)

### Seção (h3)

Parágrafo normal com **negrito** e *itálico*.

[Link](https://exemplo.com)

- Item 1
- Item 2
- Item 3

1. Primeiro
2. Segundo
3. Terceiro

```
Bloco de código
com múltiplas linhas
```

| Header 1 | Header 2 |
|----------|----------|
| Cell 1   | Cell 2   |
```

## Exemplo Completo

```java
@Route("cadastro-usuario")
public class CadastroUsuarioView extends FormView implements HasHelp {

    private TextField emailCampo;
    private TextField nomeCampo;
    private PasswordField senhaCampo;

    public CadastroUsuarioView() {
        super("Cadastro de Usuário");

        // Criar campos
        emailCampo = new TextField("Email");
        nomeCampo = new TextField("Nome");
        senhaCampo = new PasswordField("Senha");

        // Adicionar ajuda aos campos
        setHelp(emailCampo, "Email válido para login e recuperação de senha");
        setHelp(nomeCampo, "Nome completo do usuário");
        setHelp(senhaCampo, "Mínimo 8 caracteres com letras, números e caracteres especiais");

        // Adicionar formulário
        add(emailCampo, nomeCampo, senhaCampo);

        // FormView já inclui HelpOnlineComponent automaticamente
    }

    @Override
    public String getHelpTitle() {
        return "Cadastro de Novo Usuário";
    }

    @Override
    public String getHelpDescription() {
        return "Preencha o formulário com seus dados para criar uma conta. Campos marcados com * são obrigatórios.";
    }
}
```

## Captura de Screenshot

A captura de screenshot agora ocorre automaticamente no `HelpOnlineComponent` quando o diálogo é aberto. Screenshots são capturados de todos os componentes `HasHelp` e embutidos no Markdown como imagens base64.

**Para captura de screenshots de componentes específicos:**

Use o `HelpScreenshotComponent` diretamente:

```java
HelpScreenshotComponent screenshot = new HelpScreenshotComponent();
screenshot.capture(myComponent, bytes -> {
    // Processar imagem capturada (PNG)
    saveImage(bytes);
});
```

## Interação com Mouse

O `HelpOnlineComponent` inclui listeners de mouse para interação contextual:

- **mouseover:** Exibe todos os componentes de ajuda via `hasHelp.getHelpFields()` e `setVisible(true)`
- **mouseout:** Oculta todos os componentes de ajuda via `setVisible(false)`

Isso permite que os usuários vejam visualmente quais campos têm ajuda disponível ao passar o mouse sobre o botão de ajuda.

## Testes Unitários

O renderer foi testado com:

```bash
mvn test -pl ia-core-view -Dtest=FlexmarkHelpRendererTest
```

**Casos cobertos:**
- Markdown simples (headings, bold, paragraphs)
- Listas (ordenadas e não-ordenadas)
- Blocos de código
- Links
- Tabelas
- Estruturas complexas

## Segurança

### Considerações de Segurança

1. **HTML Seguro:** O `FlexmarkHelpRenderer` processa Markdown e gera HTML bem-formado
2. **Escaping:** Erros de parsing retornam conteúdo escapado (sem tags HTML)
3. **Conteúdo Estático:** Textos de ajuda devem ser estáticos (hardcoded)
4. **Input do Usuário:** Se incluir input de usuário na ajuda, considere adicionar sanitização OWASP (comentada no pom.xml)

### OWASP Sanitizer (Ativo)

O `FlexmarkHelpRenderer` já inclui sanitização OWASP integrada para prevenir XSS. A dependência está ativa no `pom.xml`:

```xml
<dependency>
    <groupId>com.googlecode.owasp-java-html-sanitizer</groupId>
    <artifactId>owasp-java-html-sanitizer</artifactId>
    <version>20240325.1</version>
</dependency>
```

A sanitização remove automaticamente:
- Tags `<script>`, `<style>`, `<iframe>`
- Atributos `onclick`, `onload`, `javascript:`
- Qualquer conteúdo perigoso no HTML gerado

## Boas Práticas

1. **Títulos Descritivos:** Use títulos claros (`getHelpTitle()`)
2. **Descrições Breves:** Descrição da view em até 2-3 linhas
3. **Estrutura:** Organize ajuda com headings (##, ###)
4. **Exemplos:** Inclua exemplos quando apropriado
5. **Links:** Vincule a documentação externa quando necessário
6. **Concisão:** Mantenha ajuda concisa e focada

## Troubleshooting

### Ajuda não aparece

- Certifique-se de que `setHelp()` foi chamado no campo
- Verifique que o componente implementa `HasHelp`
- Confirme que `HelpOnlineComponent` foi adicionado à view

### Markdown não renderiza corretamente

- Verifique sintaxe Markdown (espaçamento, caracteres especiais)
- Use blocos de código com ` ``` ` para código
- Teste com casos simples primeiro

### Screenshot não funciona

- Primeiro uso carrega `html2canvas` via CDN (espere alguns segundos)
- Verifique se pop-ups estão bloqueados no navegador
- Teste em diferentes navegadores (Chrome, Firefox, Safari)
- Se falhar, use a impressão como alternativa

## Roadmap Futuro

- [x] Adicionar OWASP sanitizer para input dinâmico
- [ ] Implementar server-side screenshot (Puppeteer) como alternativa
- [ ] Adicionar busca/índice dentro do help
- [ ] Suporte a i18n (múltiplos idiomas)
- [ ] Editor visual para ajuda (integração com CMS opcional)
- [ ] Analytics: rastrear quais tópicos de ajuda são mais acessados
- [ ] Versioning: manter histórico de mudanças na ajuda

## Suporte e Contribuições

Para problemas ou sugestões, abra uma issue no repositório do projeto.

---

**Documento atualizado:** 2026-07-02
**Módulo:** `ia-core-view`
**Status:** MVP Implementado ✅
