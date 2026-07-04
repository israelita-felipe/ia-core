# Casos de Teste - HelpScreenshotComponent

## Classe: HelpScreenshotComponent
**Camada**: Service
**CDU**: CDU045-Apresentar-Ajuda-Online

## Cenários de Teste

### Cenário 1: Captura de Screenshot
- **Descrição**: Verificar se screenshot é capturado corretamente
- **Pré-condições**: Componente Vaadin visível
- **Passos**:
  1. Chamar capture() com componente
  2. Verificar se base64 é retornado
  3. Verificar se formato é data URI válido
- **Resultado Esperado**: Screenshot em base64 retornado
- **Requisito Funcional**: RF002

### Cenário 2: Captura Múltipla Concorrente
- **Descrição**: Verificar se múltiplas capturas funcionam com requestId
- **Pré-condições**: Múltiplos componentes
- **Passos**:
  1. Iniciar captura no componente A com requestId=1
  2. Iniciar captura no componente B com requestId=2
  3. Verificar se resposta do requestId=1 corresponde ao componente A
- **Resultado Esperado**: Capturas concorrentes isoladas
- **Requisito Funcional**: RF002

### Cenário 3: Tratamento de Erro
- **Descrição**: Verificar se erros são tratados corretamente
- **Pré-condições**: Componente que não pode ser capturado
- **Passos**:
  1. Chamar capture() com componente inválido
  2. Verificar se exceção é lançada ou retorna null
- **Resultado Esperado**: Erro tratado sem crash
- **Requisito Funcional**: RF002

### Cenário 4: Formato de Imagem
- **Descrição**: Verificar se formato PNG é usado
- **Pré-condições**: Screenshot capturado
- **Passos**:
  1. Capturar screenshot
  2. Verificar se inicia com `data:image/png;base64,`
- **Resultado Esperado**: Formato PNG correto
- **Requisito Funcional**: RF002

## Mapeamento para Requisitos

| Cenário | Requisito Aplicado |
|---------|-------------------|
| 1 | RF002 |
| 2 | RF002 |
| 3 | RF002 |
| 4 | RF002 |
