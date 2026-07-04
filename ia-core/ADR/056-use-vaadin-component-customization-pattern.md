# ADR-056: Padrão de Customização de Componentes Vaadin com CSS

## Status

**ACEITO** (2026-07-03)

## Contexto e Problema

Aplicações Vaadin necessitam de uma identidade visual consistente e responsiva que:
1. Seja limpa e profissional, com componentes bem definidos
2. Seja acessível conforme WCAG 2.2
3. Suporte design responsivo mobile-first
4. Mantenha a funcionalidade de scroll sem barras visíveis
5. Use CSS puro sem bibliotecas de terceiros
6. Seja fácil de manter e estender

Pesquisas realizadas:
- **Vaadin Documentation**: Theming Vaadin applications, Styling components, Responsiveness
- **WCAG 2.2**: Web Content Accessibility Guidelines para acessibilidade
- **arXiv**: HTML papers for accessibility research (arXiv:2402.08954, arXiv:2212.07286)
- **RFCs**: RFC 3629 (UTF-8), RFC 5646 (Language Tags), RFC 8288 (Web Linking)

## Drivers da Decisão

- **Acessibilidade**: Conformidade com WCAG 2.2 para usuários com deficiências
- **Responsividade**: Suporte a dispositivos móveis, tablets e desktops
- **Manutenibilidade**: CSS organizado e modular para fácil manutenção
- **Performance**: CSS puro sem dependências de bibliotecas externas
- **Identidade Visual**: Design limpo e consistente em toda aplicação

## Opções Consideradas

### Opção 1: Usar Tailwind CSS
- **Vantagens**: Utility classes populares, desenvolvimento rápido
- **Desvantagens**: Dependência externa, tamanho de bundle aumentado, curva de aprendizado
- **Decisão**: ❌ Rejeitado - viola requisito de CSS puro

### Opção 2: Usar Bootstrap
- **Vantagens**: Framework popular, componentes prontos
- **Desvantagens**: Conflitos com Vaadin, dependência externa, customização complexa
- **Decisão**: ❌ Rejeitado - viola requisito de CSS puro

### Opção 3: CSS Custom Properties do Vaadin (Lumo/Aura)
- **Vantagens**: Nativo do Vaadin, fácil customização, mantido pelo framework
- **Desvantagens**: Limitado às propriedades expostas pelo tema
- **Decisão**: ⚠️ Parcialmente aceito - usado como base, mas complementado com CSS customizado

### Opção 4: CSS Puro Modular com Custom Properties
- **Vantagens**: Sem dependências, total controle, performance otimizada, fácil manutenção
- **Desvantagens**: Requer conhecimento de CSS, desenvolvimento inicial mais trabalhoso
- **Decisão**: ✅ Aceito - atende todos os requisitos

## Decisão

Adotamos o padrão de **Customização de Componentes Vaadin com CSS Puro Modular**:

### Estrutura de Arquivos CSS

```
themes/core/
├── styles.css                    # Arquivo principal (importa todos)
├── scroll-styles.css             # Estilos de scrollbar (oculta mas funcional)
├── tab-sheet-styles.css          # Estilos específicos de TabSheet
├── responsive-styles.css         # Estilos responsivos e acessibilidade
└── component-styles.css          # Estilos de componentes (design system)
```

### Princípios de Design

1. **Mobile-First**: Estilos base para mobile, media queries para telas maiores
2. **Acessibilidade**: Suporte a WCAG 2.2, foco visível, contraste adequado
3. **CSS Custom Properties**: Uso intensivo de variáveis CSS do Vaadin
4. **Modularidade**: Separação por responsabilidade (scroll, responsive, components)
5. **Clean Design**: Visual limpo com bordas sutis, sombras leves, tipografia clara

### Detalhes de Implementação

#### 1. Scrollbar Oculta mas Funcional

**Arquivo**: `scroll-styles.css`

```css
/* Hide scrollbar for WebKit browsers (Chrome, Safari, Edge) */
::-webkit-scrollbar {
    width: 0px;
    height: 0px;
    background: transparent;
}

/* Hide scrollbar for Firefox */
* {
    scrollbar-width: none;
    -ms-overflow-style: none;
}

/* Hide scrollbar for IE/Edge */
::-webkit-scrollbar {
    display: none;
}

/* Scroll functionality remains active via mouse wheel/touch */
```

**Justificativa**: Remove a barra visual mas mantém a funcionalidade de scroll via mouse wheel e touch, proporcionando uma interface mais limpa.

#### 2. Design Responsivo Mobile-First

**Arquivo**: `responsive-styles.css`

**Breakpoints (Padrão Vaadin Lumo)**:
- XS: < 640px (mobile default)
- S: >= 640px (small tablets)
- M: >= 768px (tablets)
- L: >= 1024px (small desktops)
- XL: >= 1440px (large desktops)

**Exemplos de Implementação**:

```css
/* Fluid typography */
html {
    font-size: 16px;
}

@media (min-width: 768px) {
    html {
        font-size: 16px;
    }
}

@media (min-width: 1024px) {
    html {
        font-size: 17px;
    }
}

/* Container queries */
@container (min-width: 400px) {
    .responsive-container {
        padding: var(--lumo-space-m);
    }
}

/* Form Layout responsive */
vaadin-form-layout {
    --vaadin-form-layout-column-spacing: var(--lumo-space-s);
}

@media (min-width: 640px) {
    vaadin-form-layout[responsive-step="1"] {
        --vaadin-form-layout-columns: 2;
    }
}
```

#### 3. Design System de Componentes

**Arquivo**: `component-styles.css`

**Princípios**:
- Bordas sutis com `var(--lumo-contrast-20pct)`
- Sombras leves para profundidade
- Transições suaves (0.2s ease)
- Estados de foco visíveis para acessibilidade
- Variantes de tamanho (small, large)

**Exemplos de Implementação**:

```css
/* Cards */
.card {
    background-color: var(--lumo-base-color);
    border: 1px solid var(--lumo-contrast-20pct);
    border-radius: var(--lumo-border-radius-m);
    padding: var(--lumo-space-m);
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
    transition: box-shadow 0.2s ease;
}

.card:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

/* Input fields */
vaadin-text-field:focus-within {
    --vaadin-input-field-border-color: var(--lumo-primary-color);
    --vaadin-input-field-focus-border-width: 2px;
    box-shadow: 0 0 0 3px rgba(var(--lumo-primary-color-rgb), 0.1);
}

/* Utility classes */
.text-center { text-align: center; }
.text-muted { color: var(--lumo-secondary-text-color); }
.mt-m { margin-top: var(--lumo-space-m); }
.p-l { padding: var(--lumo-space-l); }
```

#### 4. Acessibilidade WCAG 2.2

**Recursos Implementados**:

```css
/* Focus indicators for keyboard navigation */
vaadin-button:focus,
vaadin-text-field:focus-within {
    outline: 2px solid var(--lumo-primary-color);
    outline-offset: 2px;
}

/* High contrast mode support */
@media (prefers-contrast: high) {
    vaadin-button,
    vaadin-text-field {
        border-width: 2px;
    }
}

/* Reduced motion for accessibility */
@media (prefers-reduced-motion: reduce) {
    *,
    *::before,
    *::after {
        animation-duration: 0.01ms !important;
        animation-iteration-count: 1 !important;
        transition-duration: 0.01ms !important;
    }
}

/* Print styles */
@media print {
    vaadin-app-layout,
    vaadin-menu-bar,
    .help-button {
        display: none !important;
    }
}
```

#### 5. Integração com Vaadin

**Importação Automática**: O CSS é carregado automaticamente pelo Vaadin através do arquivo `styles.css` em `themes/core/`. Não é necessário configurar no `AppShellInitializer.java`.

**Uso de CSS Custom Properties**: Os estilos usam as variáveis CSS do Vaadin (Lumo/Aura) para garantir consistência:

```css
background-color: var(--lumo-base-color);
border-color: var(--lumo-contrast-20pct);
padding: var(--lumo-space-m);
border-radius: var(--lumo-border-radius-m);
```

### Padrões de Uso

#### Adicionar Classe CSS a Componente Vaadin

```java
TextField field = new TextField("Nome");
field.addClassName("card");
field.addClassName("mt-m");
```

#### Criar Container Responsivo

```java
Div container = new Div();
container.addClassName("responsive-container");
container.addClassName("card");
```

#### Usar Utility Classes

```java
Div content = new Div();
content.addClassName("text-center");
content.addClassName("p-l");
```

#### Customizar Componente Específico

```java
Button button = new Button("Salvar");
button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
button.addClassName("font-bold");
```

## Consequências

### Positivas

- **Performance**: CSS puro sem dependências externas, carregamento rápido
- **Manutenibilidade**: Estrutura modular facilita manutenção e extensão
- **Acessibilidade**: Conformidade com WCAG 2.2, suporte a leitores de tela
- **Responsividade**: Mobile-first, funciona em todos os dispositivos
- **Identidade Visual**: Design limpo e consistente em toda aplicação
- **Integração**: Usa CSS custom properties do Vaadin, mantendo compatibilidade
- **Flexibilidade**: Fácil de customizar e estender com novos estilos

### Negativas

- **Curva de Aprendizado**: Desenvolvedores precisam conhecer CSS e Vaadin
- **Desenvolvimento Inicial**: Requer mais trabalho inicial configurando o design system
- **Limitação**: Não tem componentes pré-estilizados como frameworks CSS

### Mitigações

- Documentação completa com exemplos de uso
- Utility classes para casos comuns
- Comentários explicativos nos arquivos CSS
- Padrões bem definidos para facilitar extensão

## Status de Implementação

✅ **COMPLETO** (2026-07-03)

- ✅ `scroll-styles.css` - Scrollbar oculta mas funcional
- ✅ `responsive-styles.css` - Design responsivo mobile-first
- ✅ `component-styles.css` - Design system de componentes
- ✅ Integração via `styles.css`
- ✅ Acessibilidade WCAG 2.2
- ✅ Sem dependências de terceiros

## Referências

### RFCs Relevantes
- **RFC 3629** - UTF-8, a transformation format of ISO 10646
- **RFC 5646** - Tags for Identifying Languages
- **RFC 8288** - Web Linking

### WCAG 2.2
- Web Content Accessibility Guidelines 2.2: https://www.w3.org/TR/WCAG22/
- Responsiveness and accessibility: https://www.boia.org/blog/does-wcag-2-1-require-responsive-design

### Vaadin Documentation
- Styling Vaadin applications: https://vaadin.com/docs/latest/styling
- Styling components: https://vaadin.com/docs/latest/styling/styling-components
- Responsiveness: https://vaadin.com/docs/latest/designing-apps/responsiveness
- Theming guide: https://vaadin.com/blog/theming-vaadin-applications-a-practical-guide

### arXiv Research
- HTML papers on arXiv -- why it's important: https://doi.org/10.48550/arxiv.2402.08954
- A framework for improving accessibility: https://ar5iv.labs.arxiv.org/html/2212.07286

### ADRs Relacionados
- **ADR-055**: Padrão de Help Online em Vaadin
- **ADR-012**: Testing Patterns
- **ADR-050**: Diretrizes Gerais de Padronização do Projeto

## Data

2026-07-03

## Revisores

- Israel Araújo
