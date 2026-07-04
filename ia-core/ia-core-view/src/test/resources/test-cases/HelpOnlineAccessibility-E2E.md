# Casos de Teste - Help Online Accessibility E2E

## Classe: HelpOnlineAccessibilityE2eTest, HelpDialogAccessibilityE2eTest, HelpKeyboardNavigationE2eTest, HelpHighContrastE2eTest
**Camada**: E2E (End-to-End)
**CDU**: CDU045-Apresentar-Ajuda-Online
**ADR**: ADR-057 (WCAG 2.2 Accessibility)

## Cenários de Teste

### Cenário 1: Botão de Ajuda - aria-label
- **Descrição**: Verificar se o botão de ajuda possui aria-label="Ajuda"
- **Pré-condições**: View carregada com botão de ajuda
- **Passos**:
  1. Navegar para a view de teste
  2. Localizar o botão de ajuda pelo seletor CSS
  3. Verificar o atributo aria-label
- **Resultado Esperado**: aria-label deve ser "Ajuda"
- **Critério WCAG**: 4.1.2 Name, Role, Value

### Cenário 2: Botão de Ajuda - aria-expanded inicial
- **Descrição**: Verificar se o botão inicia com aria-expanded="false"
- **Pré-condições**: View carregada
- **Passos**:
  1. Navegar para a view de teste
  2. Verificar o atributo aria-expanded do botão
- **Resultado Esperado**: aria-expanded deve ser "false"
- **Critério WCAG**: 4.1.3 Status Messages

### Cenário 3: Botão de Ajuda - aria-expanded ao abrir
- **Descrição**: Verificar se aria-expanded muda para "true" ao abrir o diálogo
- **Pré-condições**: View carregada
- **Passos**:
  1. Clicar no botão de ajuda
  2. Verificar o atributo aria-expanded
- **Resultado Esperado**: aria-expanded deve ser "true"
- **Critério WCAG**: 4.1.3 Status Messages

### Cenário 4: Diálogo - aria-modal
- **Descrição**: Verificar se o diálogo possui aria-modal="true"
- **Pré-condições**: Diálogo aberto
- **Passos**:
  1. Clicar no botão de ajuda
  2. Localizar o elemento vaadin-dialog-overlay
  3. Verificar o atributo aria-modal
- **Resultado Esperado**: aria-modal deve ser "true"
- **Critério WCAG**: 4.1.2 Name, Role, Value

### Cenário 5: Diálogo - role="dialog"
- **Descrição**: Verificar se o diálogo possui role="dialog"
- **Pré-condições**: Diálogo aberto
- **Passos**:
  1. Clicar no botão de ajuda
  2. Verificar o atributo role do diálogo
- **Resultado Esperado**: role deve ser "dialog"
- **Critério WCAG**: 4.1.2 Name, Role, Value

### Cenário 6: Diálogo - aria-live="polite"
- **Descrição**: Verificar se o conteúdo do diálogo possui aria-live="polite"
- **Pré-condições**: Diálogo aberto
- **Passos**:
  1. Clicar no botão de ajuda
  2. Localizar o conteúdo com div[aria-live="polite"]
- **Resultado Esperado**: aria-live deve ser "polite"
- **Critério WCAG**: 4.1.3 Status Messages

### Cenário 7: Diálogo - aria-atomic="true"
- **Descrição**: Verificar se o conteúdo do diálogo possui aria-atomic="true"
- **Pré-condições**: Diálogo aberto
- **Passos**:
  1. Clicar no botão de ajuda
  2. Verificar o atributo aria-atomic do conteúdo
- **Resultado Esperado**: aria-atomic deve ser "true"
- **Critério WCAG**: 4.1.3 Status Messages

### Cenário 8: Foco - Trap no Diálogo
- **Descrição**: Verificar se o foco é travado no diálogo quando aberto
- **Pré-condições**: Diálogo aberto
- **Passos**:
  1. Clicar no botão de ajuda
  2. Verificar elemento com foco ativo
  3. Verificar se está dentro do diálogo
- **Resultado Esperado**: Foco deve estar dentro do diálogo
- **Critério WCAG**: 2.1.1 Keyboard

### Cenário 9: Navegação - Tab para o botão
- **Descrição**: Verificar se Tab navega para o botão de ajuda
- **Pré-condições**: View carregada
- **Passos**:
  1. Pressionar Tab
  2. Verificar elemento com foco
- **Resultado Esperado**: Foco deve estar no botão de ajuda
- **Critério WCAG**: 2.1.1 Keyboard

### Cenário 10: Navegação - Enter abre diálogo
- **Descrição**: Verificar se Enter abre o diálogo
- **Pré-condições**: Foco no botão de ajuda
- **Passos**:
  1. Navegar para o botão via Tab
  2. Pressionar Enter
  3. Verificar se diálogo abriu
- **Resultado Esperado**: Diálogo deve abrir
- **Critério WCAG**: 2.1.1 Keyboard

### Cenário 11: Navegação - Escape fecha diálogo
- **Descrição**: Verificar se Escape fecha o diálogo
- **Pré-condições**: Diálogo aberto
- **Passos**:
  1. Pressionar Escape
  2. Verificar se diálogo fechou
- **Resultado Esperado**: Diálogo deve fechar
- **Critério WCAG**: 2.1.1 Keyboard

### Cenário 12: High Contrast - Borda visível
- **Descrição**: Verificar se o botão tem borda visível em high contrast mode
- **Pré-condições**: View carregada
- **Passos**:
  1. Simular prefers-contrast: high
  2. Verificar estilo de borda do botão
- **Resultado Esperado**: Borda deve estar visível
- **Critério WCAG**: 1.4.11 Non-text Contrast

### Cenário 13: High Contrast - Outline de foco
- **Descrição**: Verificar se o outline de foco é visível em high contrast mode
- **Pré-condições**: View carregada
- **Passos**:
  1. Focar no botão
  2. Verificar outline via getComputedStyle
- **Resultado Esperado**: Outline deve ser visível
- **Critério WCAG**: 2.4.7 Focus Visible

### Cenário 14: Reduced Motion - Respeito
- **Descrição**: Verificar se o componente respeita prefers-reduced-motion
- **Pré-condições**: View carregada
- **Passos**:
  1. Simular prefers-reduced-motion
  2. Abrir diálogo
  3. Verificar se abre sem animações
- **Resultado Esperado**: Diálogo deve abrir normalmente
- **Critério WCAG**: 2.3.3 Animation from Interactions

## Mapeamento para Requisitos

| Cenário | Requisito Aplicado | Critério WCAG |
|---------|-------------------|---------------|
| 1-3 | RF001, RF004 | 4.1.2, 4.1.3 |
| 4-7 | RF001, RF004 | 4.1.2, 4.1.3 |
| 8-11 | RF001, RF004 | 2.1.1, 2.4.7 |
| 12-14 | RF001, RF004 | 1.4.11, 2.3.3 |

## Configuração de Teste

### Dependências Necessárias
- Selenium WebDriver
- ChromeDriver (em modo headless)
- Spring Boot Test
- Vaadin TestBench

### Execução
```bash
cd ia-core-view
mvn test -Dtest=HelpOnlineAccessibilityE2eTest
mvn test -Dtest=HelpDialogAccessibilityE2eTest
mvn test -Dtest=HelpKeyboardNavigationE2eTest
mvn test -Dtest=HelpHighContrastE2eTest
```

## Referências
- [WCAG 2.2 Guidelines](https://www.w3.org/TR/WCAG22/)
- [WAI-ARIA Authoring Practices](https://www.w3.org/WAI/ARIA/apg/)
- [Vaadin Accessibility Documentation](https://vaadin.com/docs/latest/accessibility)
