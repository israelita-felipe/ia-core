# Checklist de Roll-out Help Online

**Status:** MVP Implementado ✅
**Próxima Fase:** Validação e Adoção Incremental
**Data:** 2026-07-01

---

## ✅ O que foi Entregue

- [x] Renderizador Markdown seguro (`FlexmarkHelpRenderer`)
- [x] Fábrica de diálogo (`HelpDialogViewFactory`) sem toolbar (diálogo limpo)
- [x] Integração no `HelpOnlineComponent`
- [x] 66 testes unitários (todos passando)
- [x] Documentação técnica (`HELP_ONLINE_DESIGN.md`)
- [x] Guia de uso (`HOW_TO_USE_HELP_ONLINE.md`)
- [x] Resumo de implementação (`MVP_HELP_ONLINE_SUMMARY.md`)
- [x] Build passando (71 testes total)
- [x] `HelpScreenshotComponent` com html2canvas integrado
- [x] Listeners de mouse em `HelpOnlineComponent` (show/hide help components)

---

## 🎯 Próximas Tarefas (por Prioridade)

### Fase Imediata (This Week)

#### Task 1: Validação em Produção
- [ ] Deploy em ambiente de teste
- [ ] Testar em mobile (responsividade)
- [ ] Verificar performance com documentos longos (>10KB)
- [x] Melhorias de estilo CSS com variáveis Lumo aplicadas
- [x] Testes unitários executados (66 testes passando)

**Estimativa:** 2h
**Responsável:** QA / Desenvolvedor

---

#### Task 2: Implementação em Primeira View
**Exemplo: CadastroUsuarioView**

```java
@Route("cadastro-usuario")
public class CadastroUsuarioView extends FormView {

    public CadastroUsuarioView() {
        super("Cadastro de Usuário");

        // Campos...
        TextField email = new TextField("Email");
        PasswordField senha = new PasswordField("Senha");

        // Ajuda
        setHelp(email, "Email válido para login");
        setHelp(senha, "Mínimo 8 caracteres");

        add(email, senha);
        // FormView já inclui HelpOnlineComponent automaticamente!
    }

    @Override
    public String getHelpTitle() {
        return "Cadastro de Novo Usuário";
    }

    @Override
    public String getHelpDescription() {
        return "Preencha os campos para criar uma nova conta.";
    }
}
```

**Estimativa:** 1h
**Responsável:** Desenvolvedor

---

#### Task 3: Coleta de Feedback
- [ ] Criar survey com usuários
- [ ] Medir acessos ao botão "?" (analytics opcional)
- [ ] Documentar issues/sugestões

**Estimativa:** 3h
**Responsável:** Product/UX

---

### Fase 1 (Next 2 Weeks)

#### Task 4: Implementação em 5 Views Críticas
- [ ] Identificar views com maior suporte ao usuário
- [ ] Implementar ajuda em cada uma
- [ ] Testar com usuários reais

**Estimativa:** 8h total
**Responsável:** Desenvolvedor(es)

**Views Propostas:**
1. Cadastro de Usuário
2. Emissão de Relatório
3. Configuração de Sistema
4. Upload de Arquivo
5. Busca Avançada

---

#### Task 5: Melhorias Visuais
- [ ] Validar estilos em diferentes temas
- [ ] Otimizar tipografia (responsive)
- [ ] Adicionar ícones customizados se necessário
- [ ] Validar contraste WCAG

**Estimativa:** 4h
**Responsável:** Designer/Frontend

---

### Fase 2 (Next Month)

#### Task 6: OWASP Sanitizer (Concluído)
- [x] Adicionar dependência no pom.xml
- [x] Atualizar `FlexmarkHelpRenderer` com sanitização
- [x] Testes de sanitização passando
- [ ] Valide segurança com OWASP tools

**Estimativa:** 6h
**Responsável:** Security/Dev Lead

---

#### Task 7: E2E Tests com Selenium/Playwright
- [ ] Criar testes automatizados para:
  - [ ] Abrir diálogo
  - [ ] Verificar renderização Markdown
  - [ ] Capturar screenshot
  - [ ] Imprimir
  - [ ] Fechar

**Estimativa:** 8h
**Responsável:** QA Automation

**Exemplo (Playwright):**
```java
@Test
void testHelpDialogOpens() {
    Page page = context.newPage();
    page.navigate("http://localhost:8080/cadastro");

    // Clica no botão de ajuda
    page.click("[aria-label='Ajuda']");

    // Verifica que o diálogo abriu
    Locator dialog = page.locator("[data-help-dialog]");
    assertEquals("visible", dialog.evaluateHandle("el => getComputedStyle(el).display"));
}
```

---

### Fase 3 (Backlog)

- [ ] Analytics (rastrear acessos)
- [ ] Busca dentro do help
- [ ] Server-side screenshot (Puppeteer)
- [ ] i18n (múltiplos idiomas)
- [ ] Editor visual para ajuda
- [ ] Versionamento de ajuda

---

## 📋 Checklist de Implementação para Cada View

Use este checklist ao implementar ajuda em uma nova view:

```markdown
### View: [Nome]

- [ ] Classe view estende `PageView` ou `FormView`
- [ ] `getHelpTitle()` implementado com título descritivo
- [ ] `getHelpDescription()` implementado com descrição (1-2 linhas)
- [ ] Campos principais têm `setHelp()` chamado
- [ ] Markdown é válido (testar em https://spec.commonmark.org/)
- [ ] Não há input de usuário na ajuda (só textos estáticos)
- [ ] Testado manualmente: botão abre diálogo
- [ ] Testado: screenshot funciona
- [ ] Testado: impressão funciona
- [ ] Testado: mobile/responsividade
- [ ] Documentação em HELP_ONLINE_DESIGN.md atualizada
- [ ] PR review concluído
```

---

## 🚀 Como Fazer um PR com Ajuda

### Passo 1: Crie um Branch
```bash
git checkout -b feature/help-view-cadastro-usuario
```

### Passo 2: Implemente a Ajuda
1. Adicione `getHelpTitle()` e `getHelpDescription()` na view
2. Chame `setHelp()` em campos importantes
3. Escreva Markdown descriptivo

### Passo 3: Teste Localmente
```bash
mvn clean install
# Abra a aplicação, navegue até a view, clique em "?"
```

### Passo 4: Commit com Mensagem Clara
```bash
git add .
git commit -m "feat(help): Adiciona ajuda online em CadastroUsuarioView

- Implementa getHelpTitle() e getHelpDescription()
- Adiciona setHelp() em campos: email, senha, nome
- Suporta screenshot e impressão via html2canvas
"
```

### Passo 5: Abra PR
- Título: `feat(help): Add help to [View Name]`
- Descrição: Mencione:
  - Qual view foi modificada
  - Quais campos têm ajuda
  - Como testar

---

## 🔍 Validação Técnica

### Para QA/Tester

1. **Funcionalidade Básica**
   - [ ] Botão "?" aparece
   - [ ] Diálogo abre ao clicar
   - [ ] Diálogo fecha (botão, ESC, click outside)

2. **Renderização Markdown**
   - [ ] Títulos aparecem em tamanho correto
   - [ ] Bold funciona
   - [ ] Links clicáveis
   - [ ] Listas formatadas
   - [ ] Código com syntax highlighting (se aplicável)

3. **Funcionalidades do Diálogo**
    - [ ] Diálogo abre com conteúdo HTML renderizado
    - [ ] Markdown é exibido corretamente
    - [ ] Screenshots são exibidos (se houver)

4. **Responsividade**
   - [ ] Desktop (1920px): UI completa
   - [ ] Tablet (768px): layout adapta
   - [ ] Mobile (375px): usável com scroll

5. **Acessibilidade**
   - [ ] Botão tem `aria-label`
   - [ ] Diálogo tem role="dialog"
   - [ ] Texto de ajuda é legível
   - [ ] Contraste WCAG AA

---

## 📊 Métricas de Sucesso

- ✅ 0 erros de build
- ✅ 66 testes passando
- ✅ 1+ view com ajuda implementada
- ✅ 100% funcionalidades básicas cobrindo (botão, diálogo)
- ✅ Listeners de mouse funcionando (show/hide help components)
- ✅ Sem regressions em testes existentes

---

## 🛠️ Troubleshooting Comum

### Problema: Diálogo não aparece
**Solução:**
1. Verifique se `setHelp()` foi chamado em pelo menos um campo
2. Confirme que a view implementa `HasHelp` (ou estende `PageView`/`FormView`)
3. Abra Console do navegador (F12) e veja se há erros

### Problema: Screenshot não funciona
**Solução:**
1. Primeira captura é mais lenta (carrega html2canvas via CDN)
2. Confirme que pop-ups não estão bloqueados
3. Teste em outro navegador (Chrome recomendado)
4. Verifique se o diálogo tem o atributo `data-help-dialog`

### Problema: Markdown não renderiza
**Solução:**
1. Valide sintaxe Markdown em https://spec.commonmark.org/
2. Confirme que caracteres especiais estão escapados
3. Teste com Markdown simples primeiro

---

## 📚 Documentação Relacionada

- **MVP Summary:** `MVP_HELP_ONLINE_SUMMARY.md` (visão geral)
- **How to Use:** `HOW_TO_USE_HELP_ONLINE.md` (guia prático)
- **Design Doc:** `HELP_ONLINE_DESIGN.md` (decisões arquiteturais)
- **ADR-055:** `ADR/055-use-help-content-pattern.md` (contexto histórico)

---

## 👥 Contatos e Escalação

**Dev Lead:** [Definir]
**QA Lead:** [Definir]
**Product Manager:** [Definir]
**Security:** [Definir]

---

## 📝 Notas Finais

- MVP é pronto para uso em produção
- Recomenda-se roll-out incremental (5 views críticas primeiro)
- Feedback de usuários é essencial para melhorias futuras
- Documentação deve ser mantida atualizada

---

**Status:** Ready for next phase ✅
**Data:** 2026-07-01
**Aprovado por:** Israel Araújo

