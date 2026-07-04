# Casos de Teste - ContatoMensagemDTO (Service Model Layer)

## Objetivo
Testar o DTO ContatoMensagemDTO que representa os dados de transferência para contatos de mensagens.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ContatoMensagemDTO (DTO)

## Casos de Teste

### CT001 - Deve criar DTO com campos obrigatórios
**Descrição**: Verificar se é possível criar um ContatoMensagemDTO com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com grupoContato e telefone preenchidos
- When: O DTO é criado
- Then: O DTO é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo grupoContato obrigatório
**Descrição**: Verificar se o campo grupoContato é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO sem grupoContato
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar campo telefone obrigatório
**Descrição**: Verificar se o campo telefone é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com telefone vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT004 - Deve validar tamanho máximo do telefone
**Descrição**: Verificar se o campo telefone respeita o tamanho máximo de 20 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com telefone com mais de 20 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT005 - Deve validar tamanho máximo do nome
**Descrição**: Verificar se o campo nome respeita o tamanho máximo de 100 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com nome com mais de 100 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT006 - Deve criar DTO usando builder
**Descrição**: Verificar se é possível criar um ContatoMensagemDTO usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de ContatoMensagemDTO
- When: O DTO é construído usando o builder
- Then: O DTO é criado com sucesso com todos os campos especificados

### CT007 - Deve clonar DTO usando toBuilder
**Descrição**: Verificar se é possível clonar um ContatoMensagemDTO usando o método toBuilder.
**Pré-condições**: Uma instância de ContatoMensagemDTO existente
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com campos preenchidos
- When: O DTO é clonado usando toBuilder()
- Then: Um novo DTO é criado com os mesmos valores

### CT008 - Deve copiar DTO usando copyObject
**Descrição**: Verificar se é possível copiar um ContatoMensagemDTO usando o método copyObject.
**Pré-condições**: Uma instância de ContatoMensagemDTO existente
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com campos preenchidos
- When: O DTO é copiado usando copyObject()
- Then: Um novo DTO é criado sem id e version

### CT009 - Deve retornar SearchRequest correto
**Descrição**: Verificar se o método getSearchRequest retorna o SearchRequest correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método getSearchRequest é chamado
- When: O método é executado
- Then: Uma instância de ContatoMensagemSearchRequest é retornada

### CT010 - Deve retornar propertyFilters corretos
**Descrição**: Verificar se o método propertyFilters retorna os filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método propertyFilters é chamado
- When: O método é executado
- Then: Um Set de filtros é retornado

### CT011 - Deve implementar HasVariavel
**Descrição**: Verificar se ContatoMensagemDTO implementa HasVariavel.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO
- When: Verifica-se a implementação
- Then: ContatoMensagemDTO é instância de HasVariavel

### CT012 - Deve retornar contexto com variáveis
**Descrição**: Verificar se o método getContext retorna um Map com as variáveis corretas.
**Pré-condições**: Uma instância de ContatoMensagemDTO com telefone e nome preenchidos
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com telefone e nome preenchidos
- When: O método getContext é chamado
- Then: Um Map com VariavelTemplate.TELEFONE e VariavelTemplate.NOME é retornado

### CT013 - Deve ter classe CAMPOS com constantes
**Descrição**: Verificar se a classe CAMPOS tem as constantes corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe CAMPOS de ContatoMensagemDTO
- When: Verifica-se as constantes
- Then: As constantes GRUPO_CONTATO, TELEFONE e NOME existem

### CT014 - Deve ter toString formatado
**Descrição**: Verificar se o método toString retorna uma string formatada.
**Pré-condições**: Uma instância de ContatoMensagemDTO com telefone e nome preenchidos
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagemDTO com telefone "11999999999" e nome "João"
- When: O método toString é chamado
- Then: A string "11999999999 - João" é retornada
