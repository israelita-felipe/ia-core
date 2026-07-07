# Casos de Teste - MarkdownBuilder

## Classe: MarkdownBuilder
**Camada**: Service
**CDU**: CDU045-Apresentar-Ajuda-Online

## Cenários de Teste

### Cenário 1: Construção de Título
- **Descrição**: Verificar se título é adicionado corretamente
- **Pré-condições**: Título válido
- **Passos**:
  1. Chamar withTitle() com texto
  2. Verificar se Markdown começa com título
- **Resultado Esperado**: Título no formato `# Título`
- **Requisito Funcional**: RF001

### Cenário 2: Construção de Descrição
- **Descrição**: Verificar se descrição é adicionada corretamente
- **Pré-condições**: Descrição válida
- **Passos**:
  1. Chamar withDescription() com texto
  2. Verificar se descrição está no Markdown
- **Resultado Esperado**: Descrição adicionada após título
- **Requisito Funcional**: RF001

### Cenário 3: Construção de Imagem
- **Descrição**: Verificar se imagem é adicionada corretamente
- **Pré-condições**: Dados base64 de imagem
- **Passos**:
  1. Chamar withImage() com base64
  2. Verificar se Markdown contém imagem
- **Resultado Esperado**: Imagem no formato `![titulo](data:image/png;base64,...)`
- **Requisito Funcional**: RF002

### Cenário 4: Construção de Seção
- **Descrição**: Verificar se seção é adicionada corretamente
- **Pré-condições**: Título e conteúdo de seção
- **Passos**:
  1. Chamar withSection() com título e conteúdo
  2. Verificar se seção está no Markdown
- **Resultado Esperado**: Seção com título e conteúdo
- **Requisito Funcional**: RF001

### Cenário 5: Construção Completa
- **Descrição**: Verificar se documento completo é construído
- **Pré-condições**: Todos os campos preenchidos
- **Passos**:
  1. Chamar todos os métodos with*()
  2. Verificar se Markdown é completo
- **Resultado Esperado**: Documento Markdown válido
- **Requisitos Aplicados**: RF001, RF002

## Mapeamento para Requisitos

| Cenário | Requisito Aplicado |
|---------|-------------------|
| 1 | RF001 |
| 2 | RF001 |
| 3 | RF002 |
| 4 | RF001 |
| 5 | RF001, RF002 |
