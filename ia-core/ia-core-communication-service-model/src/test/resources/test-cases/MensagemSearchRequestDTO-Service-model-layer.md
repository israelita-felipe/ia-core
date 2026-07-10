# Casos de Teste - MensagemSearchRequest (Service Model Layer)

## Objetivo
Testar o SearchRequest MensagemSearchRequest que define os filtros disponíveis para pesquisa de mensagens.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- MensagemSearchRequest (SearchRequest)

## Casos de Teste

### CT001 - Deve criar SearchRequest com filtros configurados
**Descrição**: Verificar se é possível criar um MensagemSearchRequest com os filtros configurados.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O SearchRequest é criado
- Then: Os filtros para telefoneDestinatario, nomeDestinatario, tipoCanal, statusMensagem e idExterno estão configurados

### CT002 - Deve ter filtro para telefoneDestinatario
**Descrição**: Verificar se o filtro para telefoneDestinatario está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para telefoneDestinatario existe com operadores EQUAL, NOT_EQUAL e LIKE

### CT003 - Deve ter filtro para nomeDestinatario
**Descrição**: Verificar se o filtro para nomeDestinatario está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para nomeDestinatario existe com operadores EQUAL, NOT_EQUAL e LIKE

### CT004 - Deve ter filtro para tipoCanal
**Descrição**: Verificar se o filtro para tipoCanal está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para tipoCanal existe com operadores EQUAL e NOT_EQUAL

### CT005 - Deve ter filtro para statusMensagem
**Descrição**: Verificar se o filtro para statusMensagem está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para statusMensagem existe com operadores EQUAL e NOT_EQUAL

### CT006 - Deve ter filtro para idExterno
**Descrição**: Verificar se o filtro para idExterno está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para idExterno existe com operadores EQUAL e NOT_EQUAL

### CT007 - Deve retornar filtros disponíveis
**Descrição**: Verificar se o método getAvaliableFilters retorna o mapa de filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: Um Map com os filtros disponíveis é retornado

### CT008 - Deve ter tipo STRING para telefoneDestinatario
**Descrição**: Verificar se o filtro para telefoneDestinatario tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para telefoneDestinatario tem o tipo FieldType.STRING

### CT009 - Deve ter tipo STRING para nomeDestinatario
**Descrição**: Verificar se o filtro para nomeDestinatario tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para nomeDestinatario tem o tipo FieldType.STRING

### CT010 - Deve ter tipo ENUM para tipoCanal
**Descrição**: Verificar se o filtro para tipoCanal tem o tipo ENUM.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para tipoCanal tem o tipo FieldType.ENUM

### CT011 - Deve ter tipo ENUM para statusMensagem
**Descrição**: Verificar se o filtro para statusMensagem tem o tipo ENUM.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para statusMensagem tem o tipo FieldType.ENUM

### CT012 - Deve ter tipo STRING para idExterno
**Descrição**: Verificar se o filtro para idExterno tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para idExterno tem o tipo FieldType.STRING
