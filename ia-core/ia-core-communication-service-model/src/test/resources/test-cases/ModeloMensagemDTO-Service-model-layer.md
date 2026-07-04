# Casos de Teste - ModeloMensagemDTO (Service Model Layer)

## Objetivo
Testar o DTO ModeloMensagemDTO que representa os dados de transferência para modelos de mensagens.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ModeloMensagemDTO (DTO)

## Casos de Teste

### CT001 - Deve criar DTO com campos obrigatórios
**Descrição**: Verificar se é possível criar um ModeloMensagemDTO com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com nome, corpoModelo e tipoCanal preenchidos
- When: O DTO é criado
- Then: O DTO é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo nome obrigatório
**Descrição**: Verificar se o campo nome é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com nome vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar tamanho máximo do nome
**Descrição**: Verificar se o campo nome respeita o tamanho máximo de 100 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com nome com mais de 100 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT004 - Deve validar tamanho máximo da descricao
**Descrição**: Verificar se o campo descricao respeita o tamanho máximo de 500 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com descricao com mais de 500 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT005 - Deve validar campo corpoModelo obrigatório
**Descrição**: Verificar se o campo corpoModelo é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com corpoModelo vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT006 - Deve validar campo tipoCanal obrigatório
**Descrição**: Verificar se o campo tipoCanal é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com tipoCanal nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT007 - Deve criar DTO usando builder
**Descrição**: Verificar se é possível criar um ModeloMensagemDTO usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de ModeloMensagemDTO
- When: O DTO é construído usando o builder
- Then: O DTO é criado com sucesso com todos os campos especificados

### CT008 - Deve clonar DTO usando toBuilder
**Descrição**: Verificar se é possível clonar um ModeloMensagemDTO usando o método toBuilder.
**Pré-condições**: Uma instância de ModeloMensagemDTO existente
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com campos preenchidos
- When: O DTO é clonado usando toBuilder()
- Then: Um novo DTO é criado com os mesmos valores

### CT009 - Deve copiar DTO usando copyObject
**Descrição**: Verificar se é possível copiar um ModeloMensagemDTO usando o método copyObject.
**Pré-condições**: Uma instância de ModeloMensagemDTO existente
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com campos preenchidos
- When: O DTO é copiado usando copyObject()
- Then: Um novo DTO é criado sem id e version

### CT010 - Deve retornar SearchRequest correto
**Descrição**: Verificar se o método getSearchRequest retorna o SearchRequest correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método getSearchRequest é chamado
- When: O método é executado
- Then: Uma instância de ModeloMensagemSearchRequest é retornada

### CT011 - Deve retornar propertyFilters corretos
**Descrição**: Verificar se o método propertyFilters retorna os filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método propertyFilters é chamado
- When: O método é executado
- Then: Um Set de filtros é retornado

### CT012 - Deve implementar HasVariavel
**Descrição**: Verificar se ModeloMensagemDTO implementa HasVariavel.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO
- When: Verifica-se a implementação
- Then: ModeloMensagemDTO é instância de HasVariavel

### CT013 - Deve retornar contexto com variáveis
**Descrição**: Verificar se o método getContext retorna um Map com as variáveis corretas.
**Pré-condições**: Uma instância de ModeloMensagemDTO com nome, descricao, corpoModelo, tipoCanal e ativo preenchidos
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com campos preenchidos
- When: O método getContext é chamado
- Then: Um Map com VariavelTemplate.NOME_MODELO, DESCRICAO_MODELO, CORPO_MODELO, TIPO_CANAL e ATIVO_MODELO é retornado

### CT014 - Deve ter classe CAMPOS com constantes
**Descrição**: Verificar se a classe CAMPOS tem as constantes corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe CAMPOS de ModeloMensagemDTO
- When: Verifica-se as constantes
- Then: As constantes NOME, DESCRICAO, CORPO_MODELO, TIPO_CANAL e ATIVO existem

### CT015 - Deve ter método values em CAMPOS
**Descrição**: Verificar se o método values da classe CAMPOS retorna um Set com os nomes dos campos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método values da classe CAMPOS é chamado
- When: O método é executado
- Then: Um Set com NOME, DESCRICAO, CORPO_MODELO, TIPO_CANAL e ATIVO é retornado

### CT016 - Deve ter toString formatado
**Descrição**: Verificar se o método toString retorna uma string formatada.
**Pré-condições**: Uma instância de ModeloMensagemDTO com nome preenchido
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagemDTO com nome "Modelo de Boas Vindas"
- When: O método toString é chamado
- Then: A string "Modelo de Boas Vindas" é retornada
