# Casos de Teste - EnvioMensagemResponseDTO (Service Model Layer)

## Objetivo
Testar o DTO EnvioMensagemResponseDTO que representa os dados de transferência para resposta de envio de mensagem em massa.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- EnvioMensagemResponseDTO (DTO)

## Casos de Teste

### CT001 - Deve criar DTO usando builder
**Descrição**: Verificar se é possível criar um EnvioMensagemResponseDTO usando o builder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de EnvioMensagemResponseDTO
- When: O DTO é construído usando o builder
- Then: O DTO é criado com sucesso com todos os campos especificados

### CT002 - Deve ter campo totalEnviados
**Descrição**: Verificar se o campo totalEnviados existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemResponseDTO
- When: Verifica-se o campo
- Then: O campo totalEnviados existe

### CT003 - Deve ter campo totalFalhas
**Descrição**: Verificar se o campo totalFalhas existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemResponseDTO
- When: Verifica-se o campo
- Then: O campo totalFalhas existe

### CT004 - Deve ter campo mensagensFalhas
**Descrição**: Verificar se o campo mensagensFalhas existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemResponseDTO
- When: Verifica-se o campo
- Then: O campo mensagensFalhas existe

### CT005 - Deve ter campo dataEnvio
**Descrição**: Verificar se o campo dataEnvio existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemResponseDTO
- When: Verifica-se o campo
- Then: O campo dataEnvio existe

### CT006 - Deve ter campo statusGeral
**Descrição**: Verificar se o campo statusGeral existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemResponseDTO
- When: Verifica-se o campo
- Then: O campo statusGeral existe

### CT007 - Deve criar resposta de sucesso
**Descrição**: Verificar se o método estático sucesso cria uma resposta de sucesso.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método sucesso é chamado com total=10
- When: O método é executado
- Then: Uma resposta com totalEnviados=10, totalFalhas=0, dataEnvio preenchida e statusGeral="SUCESSO" é retornada

### CT008 - Deve criar resposta parcial
**Descrição**: Verificar se o método estático parcial cria uma resposta parcial.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método parcial é chamado com enviados=8, falhas=2 e lista de falhas
- When: O método é executado
- Then: Uma resposta com totalEnviados=8, totalFalhas=2, mensagensFalhas preenchida, dataEnvio preenchida e statusGeral="PARCIAL" é retornada

### CT009 - Deve criar resposta com falhas
**Descrição**: Verificar se o método estático comFalhas cria uma resposta com falhas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método comFalhas é chamado com enviados=5 e lista de falhas
- When: O método é executado
- Then: Uma resposta com totalEnviados=5, totalFalhas=tamanho da lista, mensagensFalhas preenchida, dataEnvio preenchida e statusGeral="PARCIAL" é retornada
