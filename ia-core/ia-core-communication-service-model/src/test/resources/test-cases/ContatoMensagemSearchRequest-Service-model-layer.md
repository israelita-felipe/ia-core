# Casos de Teste - ContatoMensagemSearchRequest (Service Model Layer)

## Objetivo
Testar o SearchRequest ContatoMensagemSearchRequest que define os filtros disponíveis para pesquisa de contatos de mensagens.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ContatoMensagemSearchRequest (SearchRequest)

## Casos de Teste

### CT001 - Deve criar SearchRequest com filtros configurados
**Descrição**: Verificar se é possível criar um ContatoMensagemSearchRequest com os filtros configurados.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemSearchRequest
- When: O SearchRequest é criado
- Then: Os filtros para telefone e nome estão configurados

### CT002 - Deve ter filtro para telefone
**Descrição**: Verificar se o filtro para telefone está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para telefone existe com operadores EQUAL, NOT_EQUAL e LIKE

### CT003 - Deve ter filtro para nome
**Descrição**: Verificar se o filtro para nome está configurado com os operadores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para nome existe com operadores EQUAL, NOT_EQUAL e LIKE

### CT004 - Deve retornar filtros disponíveis
**Descrição**: Verificar se o método getAvaliableFilters retorna o mapa de filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: Um Map com os filtros disponíveis é retornado

### CT005 - Deve ter tipo STRING para telefone
**Descrição**: Verificar se o filtro para telefone tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para telefone tem o tipo FieldType.STRING

### CT006 - Deve ter tipo STRING para nome
**Descrição**: Verificar se o filtro para nome tem o tipo STRING.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemSearchRequest
- When: O método getAvaliableFilters é chamado
- Then: O filtro para nome tem o tipo FieldType.STRING
