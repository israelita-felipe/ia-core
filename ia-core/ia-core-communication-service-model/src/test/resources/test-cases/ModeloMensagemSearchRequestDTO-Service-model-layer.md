# Casos de Teste - ModeloMensagemSearchRequest (Service Model Layer)

## Objetivo
Testar o SearchRequest ModeloMensagemSearchRequest que define os filtros disponíveis para pesquisa de modelos de mensagens.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ModeloMensagemSearchRequest (SearchRequest)

## Casos de Teste

### CT001 - Deve criar SearchRequest com filtros configurados
**Descrição**: Verificar se é possível criar um ModeloMensagemSearchRequest com os filtros configurados.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O SearchRequest é criado
- Then: Os filtros para nome, descricao, tipoCanal e ativo estão configurados

### CT002 - Deve ter filtro para nome
**Descrição**: Verificar se o filtro para nome está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para nome existe com operadores EQUAL, NOT_EQUAL e LIKE

### CT003 - Deve ter filtro para descricao
**Descrição**: Verificar se o filtro para descricao está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para descricao existe com operadores EQUAL, NOT_EQUAL e LIKE

### CT004 - Deve ter filtro para tipoCanal
**Descrição**: Verificar se o filtro para tipoCanal está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para tipoCanal existe com operadores EQUAL e NOT_EQUAL

### CT005 - Deve ter filtro para ativo
**Descrição**: Verificar se o filtro para ativo está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para ativo existe com operadores EQUAL e NOT_EQUAL

### CT006 - Deve retornar filtros disponíveis
**Descrição**: Verificar se o método getAvaliableFilters retorna o mapa de filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: Um Map com os filtros disponíveis é retornado

### CT007 - Deve ter tipo STRING para nome
**Descrição**: Verificar se o filtro para nome tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para nome tem o tipo FieldType.STRING

### CT008 - Deve ter tipo STRING para descricao
**Descrição**: Verificar se o filtro para descricao tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para descricao tem o tipo FieldType.STRING

### CT009 - Deve ter tipo ENUM para tipoCanal
**Descrição**: Verificar se o filtro para tipoCanal tem o tipo ENUM.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para tipoCanal tem o tipo FieldType.ENUM

### CT010 - Deve ter tipo BOOLEAN para ativo
**Descrição**: Verificar se o filtro para ativo tem o tipo BOOLEAN.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para ativo tem o tipo FieldType.BOOLEAN
