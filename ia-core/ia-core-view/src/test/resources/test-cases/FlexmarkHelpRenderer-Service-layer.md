# Casos de Teste - FlexmarkHelpRenderer

## Classe: FlexmarkHelpRenderer
**Camada**: Service
**CDU**: CDU045-Apresentar-Ajuda-Online

## Cenários de Teste

### Cenário 1: Renderização Markdown Básica
- **Descrição**: Verificar se Markdown é renderizado corretamente
- **Pré-condições**: Conteúdo Markdown válido
- **Passos**:
  1. Chamar render() com texto Markdown
  2. Verificar se HTML é retornado
- **Resultado Esperado**: HTML válido é retornado
- **Requisito Não Funcional**: RNF001

### Cenário 2: Sanitização de Script
- **Descrição**: Verificar se scripts são removidos
- **Pré-condições**: Conteúdo com script malicioso
- **Passos**:
  1. Chamar render() com `<script>alert('xss')</script>`
  2. Verificar se script é removido
- **Resultado Esperado**: Script removido, apenas texto escapado
- **Requisito Não Funcional**: RNF001

### Cenário 3: Sanitização de Style
- **Descrição**: Verificar se estilos são removidos
- **Pré-condições**: Conteúdo com style malicioso
- **Passos**:
  1. Chamar render() com `<style>body{background:red}</style>`
  2. Verificar se style é removido
- **Resultado Esperado**: Style removido, apenas texto escapado
- **Requisito Não Funcional**: RNF001

### Cenário 4: Sanitização de Iframe
- **Descrição**: Verificar se iframes são removidos
- **Pré-condições**: Conteúdo com iframe malicioso
- **Passos**:
  1. Chamar render() com `<iframe src="evil.com"></iframe>`
  2. Verificar se iframe é removido
- **Resultado Esperado**: Iframe removido, apenas texto escapado
- **Requisito Não Funcional**: RNF001

### Cenário 5: Escape de HTML
- **Descrição**: Verificar se HTML não permitido é escapado
- **Pré-condições**: Conteúdo com tags não permitidas
- **Passos**:
  1. Chamar render() com `<custom>tag</custom>`
  2. Verificar se tags são escapadas
- **Resultado Esperado**: Tags escapadas como `<custom>tag</custom>`
- **Requisito Não Funcional**: RNF001

## Mapeamento para Requisitos

| Cenário | Requisito Aplicado |
|---------|-------------------|
| 1 | RNF001 |
| 2 | RNF001 |
| 3 | RNF001 |
| 4 | RNF001 |
| 5 | RNF001 |
