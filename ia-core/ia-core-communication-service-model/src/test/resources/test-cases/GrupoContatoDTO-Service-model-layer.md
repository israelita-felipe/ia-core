# Casos de Teste - GrupoContatoDTO (Service Model Layer)

## Objetivo
Testar o DTO GrupoContatoDTO que representa os dados de transferência para grupos de contatos.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- GrupoContatoDTO (DTO)

## Casos de Teste

### CT001 - Deve criar DTO com campos obrigatórios
**Descrição**: Verificar se é possível criar um GrupoContatoDTO com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com nome preenchido
- When: O DTO é criado
- Then: O DTO é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo nome obrigatório
**Descrição**: Verificar se o campo nome é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com nome vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar tamanho máximo do nome
**Descrição**: Verificar se o campo nome respeita o tamanho máximo de 100 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com nome com mais de 100 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT004 - Deve validar tamanho máximo da descrição
**Descrição**: Verificar se o campo descricao respeita o tamanho máximo de 500 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com descricao com mais de 500 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT005 - Deve criar DTO usando builder
**Descrição**: Verificar se é possível criar um GrupoContatoDTO usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de GrupoContatoDTO
- When: O DTO é construído usando o builder
- Then: O DTO é criado com sucesso com todos os campos especificados

### CT006 - Deve clonar DTO usando toBuilder
**Descrição**: Verificar se é possível clonar um GrupoContatoDTO usando o método toBuilder.
**Pré-condições**: Uma instância de GrupoContatoDTO existente
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com campos preenchidos
- When: O DTO é clonado usando toBuilder()
- Then: Um novo DTO é criado com os mesmos valores

### CT007 - Deve copiar DTO usando copyObject
**Descrição**: Verificar se é possível copiar um GrupoContatoDTO usando o método copyObject.
**Pré-condições**: Uma instância de GrupoContatoDTO existente
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com campos preenchidos
- When: O DTO é copiado usando copyObject()
- Then: Um novo DTO é criado sem id e version

### CT008 - Deve retornar SearchRequest correto
**Descrição**: Verificar se o método getSearchRequest retorna o SearchRequest correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método getSearchRequest é chamado
- When: O método é executado
- Then: Uma instância de GrupoContatoSearchRequest é retornada

### CT009 - Deve retornar propertyFilters corretos
**Descrição**: Verificar se o método propertyFilters retorna os filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método propertyFilters é chamado
- When: O método é executado
- Then: Um Set de filtros é retornado

### CT010 - Deve implementar HasVariavel
**Descrição**: Verificar se GrupoContatoDTO implementa HasVariavel.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO
- When: Verifica-se a implementação
- Then: GrupoContatoDTO é instância de HasVariavel

### CT011 - Deve retornar contexto com variáveis
**Descrição**: Verificar se o método getContext retorna um Map com as variáveis corretas.
**Pré-condições**: Uma instância de GrupoContatoDTO com nome, descricao e ativo preenchidos
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com nome, descricao e ativo preenchidos
- When: O método getContext é chamado
- Then: Um Map com VariavelTemplate.NOME, DESCRICAO_GRUPO e ATIVO_GRUPO é retornado

### CT012 - Deve ter classe CAMPOS com constantes
**Descrição**: Verificar se a classe CAMPOS tem as constantes corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe CAMPOS de GrupoContatoDTO
- When: Verifica-se as constantes
- Then: As constantes NOME, DESCRICAO e ATIVO existem

### CT013 - Deve ter método values em CAMPOS
**Descrição**: Verificar se o método values da classe CAMPOS retorna um Set com os nomes dos campos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método values da classe CAMPOS é chamado
- When: O método é executado
- Then: Um Set com NOME, DESCRICAO e ATIVO é retornado

### CT014 - Deve ter toString formatado
**Descrição**: Verificar se o método toString retorna uma string formatada.
**Pré-condições**: Uma instância de GrupoContatoDTO com nome preenchido
**Fluxo Principal**:
- Given: Uma instância de GrupoContatoDTO com nome "Clientes VIP"
- When: O método toString é chamado
- Then: A string "Clientes VIP" é retornada
