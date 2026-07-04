# Casos de Teste - HelpDialogViewFactory

## Classe: HelpDialogViewFactory
**Camada**: View
**CDU**: CDU045-Apresentar-Ajuda-Online

## Cenários de Teste

### Cenário 1: Criação de Diálogo
- **Descrição**: Verificar se o diálogo é criado corretamente
- **Pré-condições**: Conteúdo HTML válido
- **Passos**:
  1. Chamar createDialog() com conteúdo HTML
  2. Verificar se Dialog é retornado
  3. Verificar se iframe contém o conteúdo
- **Resultado Esperado**: Diálogo criado com iframe contendo conteúdo

### Cenário 2: Codificação Base64
- **Descrição**: Verificar se o conteúdo é codificado em base64
- **Pré-condições**: Conteúdo HTML
- **Passos**:
  1. Chamar encodeBase64() com conteúdo
  2. Verificar se string base64 é retornada
  3. Verificar se decodificação retorna conteúdo original
- **Resultado Esperado**: Conteúdo codificado/decodificado corretamente

### Cenário 3: Tamanho do Diálogo
- **Descrição**: Verificar se o tamanho padrão é aplicado
- **Pré-condições**: Diálogo criado
- **Passos**:
  1. Criar diálogo
  2. Verificar largura (980px)
  3. Verificar altura (75vh)
- **Resultado Esperado**: Tamanhos corretos aplicados
- **Requisito Não Funcional**: RNF002

### Cenário 4: Diálogo Maximizável
- **Descrição**: Verificar se o diálogo pode ser maximizado
- **Pré-condições**: Diálogo criado
- **Passos**:
  1. Criar diálogo
  2. Verificar se maximize é habilitado
- **Resultado Esperado**: Diálogo maximizável
- **Requisito Não Funcional**: RNF002

## Mapeamento para Requisitos

| Cenário | Requisito Aplicado |
|---------|-------------------|
| 1 | RNF001 (Sanitização XSS) |
| 2 | RF002 (Screenshots base64) |
| 3 | RNF002 (Diálogo maximizável) |
| 4 | RNF002 (Diálogo maximizável) |
