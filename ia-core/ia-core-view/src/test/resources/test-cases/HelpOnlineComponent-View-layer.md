# Casos de Teste - HelpOnlineComponent

## Classe: HelpOnlineComponent
**Camada**: View
**CDU**: CDU045-Apresentar-Ajuda-Online

## Cenários de Teste

### Cenário 1: Inicialização do Componente
- **Descrição**: Verificar se o componente é inicializado corretamente
- **Pré-condições**: Componente com HasHelp implementado
- **Passos**:
  1. Criar instância de HelpOnlineComponent
  2. Verificar se o botão de ajuda (?) é criado
  3. Verificar se os listeners de mouse são registrados
- **Resultado Esperado**: Componente inicializado com botão visível
- **Requisito Funcional**: RF004

### Cenário 2: Mouse Over Exibe Ajuda
- **Descrição**: Verificar se mouseover exibe componentes de ajuda
- **Pré-condições**: HelpOnlineComponent com helpFields configurado
- **Passos**:
  1. Simular evento mouseover no botão de ajuda
  2. Verificar se todos os componentes de ajuda têm setVisible(true)
- **Resultado Esperado**: Componentes de ajuda tornam-se visíveis
- **Requisito Funcional**: RF004

### Cenário 3: Mouse Out Oculta Ajuda
- **Descrição**: Verificar se mouseout oculta componentes de ajuda
- **Pré-condições**: HelpOnlineComponent com ajuda visível
- **Passos**:
  1. Simular evento mouseout no botão de ajuda
  2. Verificar se todos os componentes de ajuda têm setVisible(false)
- **Resultado Esperado**: Componentes de ajuda tornam-se invisíveis
- **Requisito Funcional**: RF004

### Cenário 4: Click Abre Diálogo
- **Descrição**: Verificar se click abre o diálogo de ajuda
- **Pré-condições**: HelpOnlineComponent configurado
- **Passos**:
  1. Clicar no botão de ajuda
  2. Verificar se HelpDialogViewFactory é chamado
  3. Verificar se o diálogo é exibido
- **Resultado Esperado**: Diálogo de ajuda é aberto com conteúdo
- **Requisitos Aplicados**: RF001, RF002, RNF001

### Cenário 5: Coleta Recursiva de Componentes
- **Descrição**: Verificar se componentes HasHelp são coletados recursivamente
- **Pré-condições**: Componente com aninhamento de HasHelp
- **Passos**:
  1. Criar estrutura de componentes aninhados
  2. Chamar coleta recursiva
  3. Verificar se todos os componentes são encontrados
- **Resultado Esperado**: Todos os componentes HasHelp são coletados
- **Requisito Funcional**: RF003

## Mapeamento para Requisitos

| Cenário | Requisito Aplicado |
|---------|-------------------|
| 1 | RF004 |
| 2 | RF004 |
| 3 | RF004 |
| 4 | RF001, RF002, RNF001 |
| 5 | RF003 |
