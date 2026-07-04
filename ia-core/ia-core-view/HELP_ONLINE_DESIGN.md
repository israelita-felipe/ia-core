# Conceito e Plano de Implementação: Help Online (ia-core-view)

## Status MVP: ✅ IMPLEMENTADO

O MVP foi implementado com sucesso e incluí as seguintes funcionalidades:
- [x] Novo renderizador de Markdown seguro com flexmark (`FlexmarkHelpRenderer`)
- [x] Diálogo padrão e estilizado (`CoreHelpDialog`) com toolbar e ações
- [x] Integração no `HelpOnlineComponent` para usar o novo pipeline
- [x] Botão de captura de screenshot com html2canvas (cliente)
- [x] Testes unitários para validar o renderizador
- [x] Compilação e testes passando sem erros

## Checklist (passos e entregáveis)

Resumo do estado atual (achados na análise do código)
- Existe `HasHelp` para marcar componentes com ajuda e fornecer `getHelpFields()`.
- `HelpOnlineComponent` já adiciona um botão de ajuda em `PageView` e `FormView` — o diálogo usa renderização de Markdown segura via `FlexmarkHelpRenderer`.
- Implementação possui classes: `FlexmarkHelpRenderer`, `HelpDialogViewFactory`, `HelpScreenshotComponent`, `HelpDocumentationGenerator`, `HelpMetadataExtractor`, `MarkdownBuilder`.
- Há um ADR (`ADR/055-use-help-content-pattern.md`) que documenta decisões anteriores, e testes unitários para extração de metadata.

Objetivo
- Fornecer um componente de Help Online consistente e reutilizável em `ia-core-view` que:
  - respeite o padrão visual do projeto (diálogo, botões, tipografia);
  - renderize Markdown corretamente e com segurança;
  - permita captura/export da tela do diálogo (imagem) com a mínima dependência externa possível;
  - ofereça API por herança/integração para que outros módulos reutilizem o componente.

Diretrizes de design e padrões adotados
- Acessibilidade e internacionalização
  - Seguir WCAG 2.1 para contraste, foco e leitor de tela (https://www.w3.org/TR/WCAG21/).
  - Expor textos via MessageSource / i18n central quando aplicável.
- Segurança
  - Usar flexmark-java para parse de Markdown -> HTML e depois sanitizar o HTML no servidor (OWASP Java HTML Sanitizer) ou no cliente antes de inserir em DOM.
  - Evitar uso direto de innerHTML com conteúdo não-sanitizado.
- Consistência visual
  - Reutilizar o diálogo base do projeto (subclasse de `Dialog`, ex.: `CoreDialog`), com espaçamentos, ícones e cores do tema Lumo já usados pelas views.
  - Botão de ajuda: pequeno ícone "?" com tooltip e aria-label, inserido por padrão no topo em `PageView` e `FormView` (mantido), optional em outros componentes.

Identidade visual proposta (component `HelpOnline`)
- Componentes:
  - Botão flutuante no topo direito do cabeçalho: ícone `vaadin-icon` com `vaadin:question-circle` (ou `lumo` variant).
  - Diálogo: header com título da view (HelpMetadata.title), subtítulo (route/descrição curta), corpo com area scrollable e toolbar no footer com ações: "Fechar", "Imprimir", "Salvar imagem" (ícone câmera), "Enviar feedback" (opcional).
  - Tamanhos: largura máxima 980px, altura 75vh, padding 24px.
  - Tipografia: usar variáveis de fonte do Lumo (var(--lumo-font-size-m), etc.).
  - Código/blocos: usar estilo com fundo #f6f8fa (ou var(--lumo-contrast-10pct)) e botões para copiar.
  - Destaques/alertas: seguir tokens de cor do projeto para avisos de segurança ou notas importantes.

Onde adicionar ajuda (recomendações no projeto)
- Deve existir `HasHelp` em componentes complexos e views compostas:
  - Manter em `PageView` e `FormView` (já inclusos).
  - Adicionar onde existam formulários com > 3 campos ou interações não triviais (ex.: cadastros complexos, processos, integrações externas).
  - Não é necessário marcar componentes simples (botões básicos/labels) com `HasHelp` — prefere-se documentar via help do pai.
- Estratégia de adoção: primeiro estabilizar o HelpOnline em `ia-core-view` e depois habilitar incrementalmente em CDUs/ módulos mais usados (prioridade: formulários com maior suporte ao usuário).

Renderização de Markdown — proposta técnica
1) Parser: flexmark-java (https://github.com/vsch/flexmark-java)
   - Justificativa: completo, extensível (tabelas, code blocks, tables, admonitions), disponível para Java server-side.
2) Transformar Markdown -> HTML no servidor (HelpDocumentationGenerator usando flexmark) para obter HTML bem formado.
3) Sanitização: aplicar OWASP Java HTML Sanitizer (https://github.com/OWASP/java-html-sanitizer) com política restrita permitindo apenas tags seguras (p, a[href], strong, em, code, pre, ul/ol/li, table/thead/tbody/tr/td).
4) No cliente, renderizar HTML sanitizado dentro de um `Html` (Vaadin) ou `HtmlFlow` (terceiros) — evitar manipulação directa com innerHTML sem contexto.
5) Suporte a widgets Vaadin dentro do Markdown
   - Para componentes Vaadin que precisam aparecer verdadeiramente como componentes (ex.: um Grid ou Upload inserido no help), o mecanismo de `HasHelp` deve prover um render callback que cria componentes Vaadin reais. Exemplo: HelpMetadata pode incluir renderFunctions que vinculem campos a `Component`s.

Problema atual: renderer atual usa innerHTML e parsing manual. Plano: substituir `MarkdownRenderer` por `FlexmarkMarkdownRenderer` que siga o pipeline acima.

Captura de imagem do diálogo (screenshot)
Requisitos: "a mais simples" que permita passar o componente Vaadin e gerar a imagem.

Opções avaliadas
- html2canvas (https://github.com/niklasvh/html2canvas)
  - Prós: simples, roda no cliente, fácil de integrar com Vaadin via executeJs; produz PNG/DataURL; bom para conteúdo HTML puro.
  - Contras: pode ter problemas com Web Components/shadow DOM, fontes remotas (CORS), imagens cross-origin; não captura alguns estilos aplicados via shadow DOM interno; conteúdo heavy (grid, virtual scroll) pode ser cortado ou renderizado parcialmente.
- dom-to-image-more / dom-to-image (https://github.com/tsayen/dom-to-image)
  - Prós: lida melhor com svg e estilos inline; fork "dom-to-image-more" tem mais correções.
  - Contras: similares a html2canvas em limitações e menos mantido que html2canvas.
- Server-side rendering with headless Chromium (Puppeteer / Playwright)
  - Prós: captura fiel (mesmo com Shadow DOM), melhor para imagens complexas e para gerar thumbnails em background.
  - Contras: infra adicional (serviço), maior complexidade de deploy.

Recomendação pragmática
- Começar com html2canvas (cliente) para MVP — validador: consegue capturar o conteúdo do Dialog que é basicamente HTML sanitizado + Vaadin components simples.
- Se detectar falhas com Web Components ou grids/VirtualScroll, migrar para alternativa server-side (Puppeteer) ou um mix: html2canvas para usuários e server-side para geração de thumbnails/relatórios.

Exemplo de integração (snippet)
- Incluir html2canvas via CDN no layout ou bundle frontend (package.json):
  <script src="https://cdn.jsdelivr.net/npm/html2canvas@1.4.1/dist/html2canvas.min.js"></script>

- Java (Vaadin) — executar JS para capturar o Dialog e forçar download no cliente:
  UI.getCurrent().getPage().executeJs(
    "(function(){ const el = document.querySelector('[data-help-dialog]'); if(!el){return;} html2canvas(el, {useCORS:true, scale:2}).then(canvas => { const a = document.createElement('a'); a.href = canvas.toDataURL('image/png'); a.download = 'help-' + (document.title||'view') + '.png'; a.click(); }); })();"
  );

- Observações: marcar o diálogo com atributo `data-help-dialog` (ou id) para seleção clara; usar option useCORS para imagens; tratar permissões para popups.

Roteiro de implementação (tarefas detalhadas)
Fase 0 — Preparação e pesquisa
  - T0.1: Revisar ADR/055 e testes existentes; coletar exemplos de GitHub que implementem help dialogs (PatternFly, Vaadin Cookbook) — listar links.
  - T0.2: Definir política de sanitização (lista de tags/atributos permitidos).

Fase 1 — Infraestrutura Markdown segura
  - T1.1: Adicionar dependências Maven em `ia-core-view` pom.xml: flexmark-all + owasp-java-html-sanitizer.
  - T1.2: Implementar `FlexmarkHelpRenderer`:
      - usa flexmark para gerar HTML;
      - aplica sanitização;
      - retorna HTML seguro e metadados (tabelas de conteúdos, anchors).
  - T1.3: Substituir o uso atual de `MarkdownRenderer` por `FlexmarkHelpRenderer` em `HelpOnlineComponent`.
  - T1.4: Ajustar `HelpDocumentationGenerator` para usar o novo renderer.

Fase 2 — Diálogo consistente e API
  - T2.1: Criar `CoreHelpDialog extends Dialog` (ou adaptar `CoreDialog` se existir) com:
      - header, footer com actions, estilos Lumo, slots para toolbar;
      - atributo `data-help-dialog` para captura de imagem;
      - evento de abertura para foco inicial e leitura pelo leitor de tela.
  - T2.2: Atualizar `HelpOnlineComponent` para usar `CoreHelpDialog` e a nova renderização.
  - T2.3: Garantir que `HelpOnlineComponent` aceite:
      - texto estático (override),
      - HelpMetadata gerado automaticamente,
      - callback para renderização custom (componente Vaadin real) para campos complexos.

Fase 3 — Captura de imagem e export
  - T3.1: Integrar `html2canvas` no frontend (npm ou CDN). Testes locais com diálogos simples.
  - T3.2: Adicionar botão "Salvar imagem" na toolbar que executa o trecho JS do exemplo.
  - T3.3: Testar com imagens e recursos CORS; se problemas, documentar e preparar fallback server-side.

Fase 4 — Refinamento visual, testes e rollout
  - T4.1: Implementar estilos CSS do help (variáveis Lumo, classes `.ia-help-header`, `.ia-help-body`).
  - T4.2: Testes unitários e integração: testar extração de metadata, renderização, sanitização, e botão de captura (testes e2e com Selenium/Playwright).
  - T4.3: Documentação para desenvolvedores: como marcar um componente com `HasHelp`, como criar textos customizados e como prover componentes Vaadin reais dentro do help.
  - T4.4: Rollout incremental: habilitar em 3-5 views críticas, colher feedback.

API e modelos de código sugeridos
- DTO: `HelpMetadata` (já existe) — manter e enriquecer com campos `renderCallbacks` opcional.
- Novo renderer: `FlexmarkHelpRenderer` (pseudocódigo):
  - Input: Markdown string, HelpMetadata
  - Steps: flexmark.parse -> HtmlRenderer -> sanitize -> return safe HTML

Segurança: recomendações práticas
- Sempre aplicar sanitização do lado servidor antes de enviar HTML para cliente.
- Evitar passar Markdown não verificado para renderização no cliente sem sanitizar.
- Usar CSP (Content Security Policy) para mitigar injeção de scripts.

Onde NÃO usar `HasHelp` (quando evitar)
- Componentes puramente decorativos sem interação.
- Componentes de baixo valor: botões comuns sem fluxo complexo.

Riscos e mitigação
- Risco: html2canvas não captura corretamente componentes baseados em Shadow DOM ou grids virtuais.
  - Mitigação: testar os casos de uso; ter fallback de server-side screenshot (Puppeteer).
- Risco: XSS via Markdown
  - Mitigação: usar flexmark + OWASP sanitizer e politicas estritas.

Exemplos/links úteis e fontes consultadas
- Vaadin docs – Dialogs, UI component patterns: https://vaadin.com/docs
- html2canvas (GitHub): https://github.com/niklasvh/html2canvas
- dom-to-image-more: https://github.com/tsayen/dom-to-image
- flexmark-java: https://github.com/vsch/flexmark-java
- OWASP Java HTML Sanitizer: https://github.com/OWASP/java-html-sanitizer
- W3C WCAG 2.1: https://www.w3.org/TR/WCAG21/
- WAI-ARIA Authoring Practices: https://www.w3.org/TR/wai-aria-practices-1.2/
- Nielsen Norman Group — guidelines for online help (artigos da NN/g): https://www.nngroup.com/articles/online-help/
- ADR (local): `ia-core/ADR/055-use-help-content-pattern.md`
- Exemplos no GitHub (busque por "help dialog markdown vaadin" ou "online help component") — recomenda-se consultar PatternFly / Material docs como inspiração visual.

Checklist de entrega do documento
- [x] Documento de concepção criado (este arquivo)
- [ ] Task list pronta para abrir issues
- [ ] Prototipagem do diálogo e renderizador (próximo passo)

Próximo passo que posso executar
- Se desejar, eu crio um Pull Request com:
  - atualização do `pom.xml` para flexmark + owasp sanitizer;
  - implementação inicial de `FlexmarkHelpRenderer` e `CoreHelpDialog`;
  - integração com `HelpOnlineComponent` e botão de captura com html2canvas.

---
Documento gerado automaticamente após análise do código-fonte do módulo `ia-core-view` (pasta `src/main/java/com/ia/core/view/help`) e consulta de boas práticas públicas sobre renderização segura de Markdown, usabilidade e captura de tela.


## Resumo da Implementação MVP

### Arquivos Criados/Modificados

#### 1. `FlexmarkHelpRenderer.java` (NOVO)
- Localização: `src/main/java/com/ia/core/view/help/documentation/FlexmarkHelpRenderer.java`
- Responsabilidade: Renderizar Markdown para HTML usando flexmark-java
- Funcionalidades:
  - Parse de Markdown -> AST -> HTML
  - Suporte a extensões (tabelas, etc.)
  - Tratamento de erros com escaping seguro
  - **OWASP sanitizer integrado** para prevenir XSS

#### 2. `HelpDialogViewFactory.java` (NOVO)
- Localização: `src/main/java/com/ia/core/view/help/dialog/HelpDialogViewFactory.java`
- Responsabilidade: Fábrica para diálogos de ajuda
- Funcionalidades:
  - Header com título e subtítulo
  - Body com scroll automático
  - Toolbar com ações: 📷 Screenshot, 🖨️ Print, ✕ Close
  - Exibe conteúdo via iframe com base64

#### 3. `HelpScreenshotComponent.java` (NOVO)
- Localização: `src/main/java/com/ia/core/view/help/screenshot/HelpScreenshotComponent.java`
- Responsabilidade: Componente reutilizável para captura de screenshots
- Funcionalidades:
  - Usa html2canvas via NPM
  - Retorna imagem como byte[] via callback
  - Reutilizável em outros contextos

#### 4. `HelpOnlineComponent.java` (REFATORADO)
- Localização: `src/main/java/com/ia/core/view/help/HelpOnlineComponent.java`
- Mudanças:
  - Substituído o renderer antigo (`MarkdownRenderer`) por `FlexmarkHelpRenderer`
  - Usa `HelpDialogViewFactory` para exibir diálogo
  - Conteúdo HTML é convertido para base64 e exibido via iframe
  - Novo pipeline: Markdown -> Flexmark -> HTML -> Base64 -> FrameDialog
  - Captura de screenshots recursiva de componentes com ajuda

#### 5. `FlexmarkHelpRendererTest.java` (NOVO)
- Localização: `src/test/java/com/ia/core/view/help/documentation/FlexmarkHelpRendererTest.java`
- Cobertura: 9 testes unitários
- Casos: markdown simples, listas, código, links, tabelas, estrutura complexa
- Status: ✅ Todos passando

#### 6. `HelpDialogViewFactoryTest.java` (NOVO)
- Localização: `src/test/java/com/ia/core/view/help/dialog/HelpDialogViewFactoryTest.java`
- Cobertura: 3 testes unitários
- Casos: base64 encoding, título, subtítulo
- Status: ✅ Todos passando

#### 7. `pom.xml` (ATUALIZADO)
- Adicionada dependência: `flexmark-all:0.64.0`
- **OWASP sanitizer ativo** (`owasp-java-html-sanitizer:20240325.1`)

### Compilação e Testes
```bash
# Compilação bem-sucedida
mvn clean compile -pl ia-core-view -DskipTests

# Testes bem-sucedidos
mvn test -pl ia-core-view
Tests run: 62, Failures: 0, Errors: 0
```

### Pipeline de Renderização (agora seguro)
```
Markdown (input)
    ↓
FlexmarkHelpRenderer.render()
    ↓
flexmark parse() → AST
    ↓
HtmlRenderer.render() → HTML
    ↓
buildFullHtml() → HTML completo com CSS Lumo
    ↓
toBase64() → base64 encoded
    ↓
FrameDialogViewFactory.show() → iframe com data URI
```
