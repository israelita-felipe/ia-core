# ADR-057: Implementar WCAG 2.2 para Acessibilidade em Aplicações Vaadin

## Status

**ACEITO** (2026-07-03)

## Contexto e Problema

Aplicações web modernas devem ser acessíveis a todos os usuários, incluindo aqueles com deficiências visuais, auditivas, motoras e cognitivas. O projeto ia-core, sendo um framework/base para construção de aplicações empresariais, precisa garantir que todas as aplicações construídas sobre ele sejam acessíveis por padrão.

**Drivers da Decisão**:
- **Requisitos Legais**: ADA (EUA), EAA (Europa), AODA (Ontário) exigem conformidade WCAG 2.2 AA
- **Inclusão Digital**: Acessibilidade é um direito fundamental, não um recurso opcional
- **Qualidade de Software**: Acessibilidade melhora a UX para todos os usuários
- **SEO**: Conteúdo acessível tem melhor ranqueamento em motores de busca
- **Manutenibilidade**: Código acessível é mais limpo e semântico

**Pesquisas Realizadas**:
- **W3C WCAG 2.2**: Documentação oficial e quick reference
- **arXiv**: HTML papers for accessibility (arXiv:2402.08954, arXiv:2212.07286)
- **GitHub**: Repositórios de implementação WCAG (act-rules, accessibility-agents, web-accessibility-guide)
- **WebAIM**: Checklist prático de implementação WCAG 2.2
- **Deque**: Guia de implementação WCAG 2.2 AA

## Decisão

Adotamos **WCAG 2.2 Level AA** como padrão de acessibilidade para o projeto ia-core, com implementação específica para aplicações Vaadin.

### Nível de Conformidade

**WCAG 2.2 Level AA** (nível alvo):
- **Level A**: Mínimo obrigatório - conteúdo deve ser acessível
- **Level AA**: Padrão legal - maioria das leis exigem este nível
- **Level AAA**: Aspiracional - não é viável para todos os conteúdos

**Justificativa**: Level AA é o padrão exigido pela maioria das legislações (ADA, EAA, AODA) e oferece o melhor equilíbrio entre acessibilidade e viabilidade técnica.

### Princípios POUR

WCAG 2.2 é organizado em 4 princípios (POUR):

1. **Perceivable (Percebível)**: Informação e componentes de UI devem ser apresentados de forma que usuários possam percebê-los
2. **Operable (Operável)**: Componentes de UI e navegação devem ser operáveis
3. **Understandable (Compreensível)**: Informação e operação de UI devem ser compreensíveis
4. **Robust (Robusto)**: Conteúdo deve ser robusto o suficiente para ser interpretado por tecnologias assistivas

### Implementação no ia-core

#### 1. Estrutura Semântica (Perceivable)

**HTML Semântico**:
- Vaadin componentes já fornecem estrutura semântica
- Usar landmarks HTML5 quando necessário (`<header>`, `<nav>`, `<main>`, `<footer>`)
- Hierarquia de cabeçalhos correta (H1-H6)

**Contraste de Cores**:
- Texto normal: contraste mínimo 4.5:1
- Texto grande (18pt+): contraste mínimo 3:1
- Componentes UI: contraste mínimo 3:1
- Usar variáveis CSS do Vaadin Lumo/Aura que garantem contraste

**Texto Alternativo**:
- Imagens decorativas: `alt=""` (vazio)
- Imagens informativas: `alt` descritivo
- Complex images: descrição detalhada
- Ícones: texto oculto para screen readers

**Captions e Transcripts**:
- Vídeos: captions obrigatórios
- Áudio: transcripts obrigatórios
- Conteúdo dinâmico: descrições alternativas

#### 2. Operabilidade (Operable)

**Navegação por Teclado**:
- Todos os componentes Vaadin são navegáveis por teclado por padrão
- Ordem lógico de tab (esquerda-direita, cima-baixo)
- Skip links para conteúdo principal
- Focus visível (outline 2px solid)

**Controles de Tempo**:
- Conteúdo em movimento > 5s: pausável
- Atualizações automáticas: controláveis pelo usuário
- Timeouts: aviso prévio com opção de extensão

**Navegação e Orientação**:
- Títulos de página descritivos
- Navegação consistente entre páginas
- Breadcrumbs para hierarquia
- Sitemap para grandes sites

**Prevenção de Redirecionamentos**:
- Não redirecionar automaticamente
- Avisar antes de mudanças de contexto
- Permitir cancelar ações

#### 3. Compreensibilidade (Understandable)

**Linguagem**:
- Lang attribute em `<html>` (pt-BR, en-US)
- Mudanças de idioma identificadas
- Abreviações definidas no primeiro uso
- Texto claro e simples

**Previsibilidade**:
- Navegação em local consistente
- Inputs não mudam contexto inesperadamente
- Componentes com mesma função: mesma aparência

**Assistência de Input**:
- Labels em todos os campos de formulário
- Instruções claras
- Mensagens de erro descritivas
- Sugestões de correção

**Prevenção de Erros**:
- Validação antes de submissão
- Confirmação para ações irreversíveis
- Recuperação de dados após erros
- Sem testes cognitivos para login

#### 4. Robustez (Robust)

**Compatibilidade**:
- HTML5 válido e semântico
- ARIA attributes quando necessário
- Tecnologias assistivas suportadas
- Testado com NVDA, JAWS, VoiceOver

**Name, Role, Value**:
- Componentes customizados: ARIA roles
- Estados atualizados dinamicamente
- Valores programmaticamente determináveis
- Eventos anunciados

### Implementação Específica Vaadin

#### Componentes Vaadin e Acessibilidade

**FormLayout**:
- Labels associados automaticamente
- Helper text para instruções
- Validação com mensagens de erro
- Responsive columns

**Grid**:
- Cabeçalhos semânticos
- Sort indicadores acessíveis
- Keyboard navigation
- Screen reader announcements

**Dialog**:
- Focus trap automático
- Escape para fechar
- ARIA attributes
- Backdrop click para fechar

**Menu Bar**:
- Keyboard navigation
- ARIA expanded states
- Submenus acessíveis
- Focus management

**Tabs**:
- ARIA roles (tablist, tab, tabpanel)
- Keyboard navigation (arrows)
- Active tab indicado
- Screen reader announcements

#### CSS para Acessibilidade

**Focus Indicators**:
```css
vaadin-button:focus,
vaadin-text-field:focus-within {
    outline: 2px solid var(--lumo-primary-color);
    outline-offset: 2px;
}
```

**High Contrast Mode**:
```css
@media (prefers-contrast: high) {
    vaadin-button,
    vaadin-text-field {
        border-width: 2px;
    }
}
```

**Reduced Motion**:
```css
@media (prefers-reduced-motion: reduce) {
    *,
    *::before,
    *::after {
        animation-duration: 0.01ms !important;
        transition-duration: 0.01ms !important;
    }
}
```

**Skip Links**:
```css
.skip-link {
    position: absolute;
    top: -40px;
    left: 0;
    background: var(--lumo-primary-color);
    color: white;
    padding: 8px;
    z-index: 100;
}

.skip-link:focus {
    top: 0;
}
```

#### Padrões de Código Java

**ARIA Attributes**:
```java
button.getElement().setAttribute("aria-label", "Fechar diálogo");
button.getElement().setAttribute("aria-expanded", "false");
```

**Live Regions**:
```java
Div statusRegion = new Div();
statusRegion.getElement().setAttribute("role", "status");
statusRegion.getElement().setAttribute("aria-live", "polite");
```

**Keyboard Shortcuts**:
```java
Shortcut.addShortcutListener(view, Key.ESCAPE, event -> {
    closeDialog();
});
```

### Ferramentas de Validação

**Automated Testing**:
- **axe-core**: Validação automatizada em CI/CD
- **HTML_CodeSniffer**: Validação de código
- **Pa11y**: Testes de acessibilidade automatizados

**Manual Testing**:
- **NVDA** (Windows): Screen reader gratuito
- **JAWS** (Windows): Screen reader comercial
- **VoiceOver** (macOS/iOS): Screen reader nativo
- **TalkBack** (Android): Screen reader Android

**Color Contrast**:
- **WebAIM Contrast Checker**: Validação de contraste
- **axe DevTools**: Extensão Chrome/Firefox
- **Colour Contrast Analyser**: Ferramenta desktop

### Checklist de Implementação

#### Perceivable (Percebível)
- [ ] Contraste de cores 4.5:1 (texto normal) / 3:1 (texto grande)
- [ ] Alt text em todas as imagens
- [ ] Captions em vídeos
- [ ] Texto redimensionável até 200%
- [ ] Separação de conteúdo e apresentação

#### Operable (Operável)
- [ ] Navegação por teclado completa
- [ ] Focus visível em todos os elementos interativos
- [ ] Skip links implementados
- [ ] Conteúdo em movimento pausável
- [ ] Títulos de página descritivos

#### Understandable (Compreensível)
- [ ] Lang attribute declarado
- [ ] Labels em todos os campos de formulário
- [ ] Mensagens de erro descritivas
- [ ] Navegação consistente
- [ ] Instruções claras

#### Robust (Robust)
- [ ] HTML5 válido e semântico
- [ ] ARIA attributes quando necessário
- [ ] Testado com screen readers
- [ ] Name, Role, Value implementados

### Integração com ADRs Existentes

**ADR-056**: Padrão de Customização de Componentes Vaadin
- CSS já inclui foco visível, high contrast, reduced motion
- Design system segue princípios de acessibilidade

**ADR-055**: Padrão de Help Online em Vaadin
- Helper text para instruções
- Diálogos acessíveis com ARIA
- OWASP sanitizer para segurança

**ADR-012**: Testing Patterns
- Testes de acessibilidade automatizados
- Testes manuais com screen readers
- Cobertura de critérios WCAG

## Consequências

### Positivas

- **Conformidade Legal**: Atende ADA, EAA, AODA e outras legislações
- **Inclusão**: Aplicação acessível a todos os usuários
- **Qualidade**: Código mais limpo e semântico
- **SEO**: Melhor ranqueamento em motores de busca
- **UX**: Melhor experiência para todos os usuários
- **Manutenibilidade**: Padrões claros e documentados

### Negativas

- **Curva de Aprendizado**: Desenvolvedores precisam aprender WCAG
- **Tempo de Desenvolvimento**: Implementação inicial mais demorada
- **Testes**: Requer testes manuais com screen readers
- **Design**: Algumas decisões de design podem ser limitadas

### Mitigações

- Documentação completa com exemplos
- Ferramentas automatizadas em CI/CD
- Treinamento da equipe
- Padrões reutilizáveis
- Checklist de implementação

## Status de Implementação

✅ **EM ANDAMENTO** (2026-07-03)

- ✅ CSS com foco visível, high contrast, reduced motion
- ✅ Design system seguindo princípios WCAG
- ⏳ Validação automatizada em CI/CD
- ⏳ Testes manuais com screen readers
- ⏳ Documentação completa para desenvolvedores

## Referências

### W3C Oficial
- **WCAG 2.2**: https://www.w3.org/TR/WCAG22/
- **WCAG 2.2 Quick Reference**: https://www.w3.org/WAI/WCAG22/quickref/
- **WCAG 2 Overview**: https://www.w3.org/WAI/standards-guidelines/wcag/
- **WAI-ARIA Authoring Practices Guide**: https://www.w3.org/WAI/ARIA/apg/
- **Guidance on Applying WCAG 2.2 to Mobile Applications**: https://w3c.github.io/matf/

### arXiv Research
- **HTML papers on arXiv**: https://doi.org/10.48550/arxiv.2402.08954
- **Framework for improving accessibility**: https://ar5iv.labs.arxiv.org/html/2212.07286

### GitHub Repositories
- **act-rules/act-rules.github.io**: https://github.com/act-rules/act-rules.github.io (Accessibility conformance testing rules)
- **Community-Access/accessibility-agents**: https://github.com/Community-Access/accessibility-agents (AI agents for WCAG 2.2 AA)
- **arzucaner/web-accessibility-guide**: https://github.com/arzucaner/web-accessibility-guide (WCAG 2.2 AA patterns)
- **yamanoku/apg-patterns-examples**: https://github.com/yamanoku/apg-patterns-examples (WAI-ARIA APG patterns)
- **deandreperry/508Dev**: https://github.com/deandreperry/508Dev (Interactive accessibility training)
- **squizlabs/HTML_CodeSniffer**: https://github.com/squizlabs/HTML_CodeSniffer (WCAG validation)
- **liip/TheA11yMachine**: https://github.com/liip/TheA11yMachine (Automated accessibility testing)

### Checklists e Guias
- **WebAIM WCAG 2 Checklist**: https://webaim.org/standards/wcag/checklist
- **Deque WCAG 2.2 Checklist**: https://media.dequeuniversity.com/en/docs/web-accessibility-checklist-wcag-2-2.pdf
- **TestKase WCAG 2.2 Guide**: https://www.testkase.com/blog/wcag-2-2-aa-compliance-checklist-2026
- **A11yPath WCAG 2.2 Checklist**: https://a11ypath.com/checklists/wcag-2-2/

### Ferramentas
- **axe-core**: https://github.com/dequelabs/axe-core
- **Pa11y**: https://github.com/pa11y/pa11y
- **WebAIM Contrast Checker**: https://webaim.org/resources/contrastchecker/

### ADRs Relacionados
- **ADR-056**: Padrão de Customização de Componentes Vaadin
- **ADR-055**: Padrão de Help Online em Vaadin
- **ADR-012**: Testing Patterns
- **ADR-050**: Diretrizes Gerais de Padronização do Projeto

## Data

2026-07-03

## Revisores

- Israel Araújo
