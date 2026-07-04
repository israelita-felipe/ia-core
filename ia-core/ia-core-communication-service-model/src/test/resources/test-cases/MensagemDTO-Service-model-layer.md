# Casos de Teste - MensagemDTO (Service Model Layer)

## Objetivo
Testar o DTO MensagemDTO que representa os dados de transferência para mensagens de comunicação.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- MensagemDTO (DTO)

## Casos de Teste

### CT001 - Deve criar DTO com campos obrigatórios
**Descrição**: Verificar se é possível criar um MensagemDTO com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com telefoneDestinatario, corpoMensagem, tipoCanal e statusMensagem preenchidos
- When: O DTO é criado
- Then: O DTO é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo telefoneDestinatario obrigatório
**Descrição**: Verificar se o campo telefoneDestinatario é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com telefoneDestinatario vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar tamanho máximo do telefoneDestinatario
**Descrição**: Verificar se o campo telefoneDestinatario respeita o tamanho máximo de 20 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com telefoneDestinatario com mais de 20 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT004 - Deve validar tamanho máximo do nomeDestinatario
**Descrição**: Verificar se o campo nomeDestinatario respeita o tamanho máximo de 100 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com nomeDestinatario com mais de 100 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT005 - Deve validar campo corpoMensagem obrigatório
**Descrição**: Verificar se o campo corpoMensagem é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com corpoMensagem vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT006 - Deve validar campo tipoCanal obrigatório
**Descrição**: Verificar se o campo tipoCanal é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com tipoCanal nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT007 - Deve validar campo statusMensagem obrigatório
**Descrição**: Verificar se o campo statusMensagem é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com statusMensagem nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT008 - Deve validar tamanho máximo do idExterno
**Descrição**: Verificar se o campo idExterno respeita o tamanho máximo de 100 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com idExterno com mais de 100 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT009 - Deve validar tamanho máximo do motivoFalha
**Descrição**: Verificar se o campo motivoFalha respeita o tamanho máximo de 500 caracteres.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com motivoFalha com mais de 500 caracteres
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT010 - Deve criar DTO usando builder
**Descrição**: Verificar se é possível criar um MensagemDTO usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de MensagemDTO
- When: O DTO é construído usando o builder
- Then: O DTO é criado com sucesso com todos os campos especificados

### CT011 - Deve clonar DTO usando toBuilder
**Descrição**: Verificar se é possível clonar um MensagemDTO usando o método toBuilder.
**Pré-condições**: Uma instância de MensagemDTO existente
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com campos preenchidos
- When: O DTO é clonado usando toBuilder()
- Then: Um novo DTO é criado com os mesmos valores

### CT012 - Deve copiar DTO usando copyObject
**Descrição**: Verificar se é possível copiar um MensagemDTO usando o método copyObject.
**Pré-condições**: Uma instância de MensagemDTO existente
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com campos preenchidos
- When: O DTO é copiado usando copyObject()
- Then: Um novo DTO é criado sem id e version

### CT013 - Deve retornar SearchRequest correto
**Descrição**: Verificar se o método getSearchRequest retorna o SearchRequest correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método getSearchRequest é chamado
- When: O método é executado
- Then: Uma instância de MensagemSearchRequest é retornada

### CT014 - Deve retornar propertyFilters corretos
**Descrição**: Verificar se o método propertyFilters retorna os filtros corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método propertyFilters é chamado
- When: O método é executado
- Then: Um Set de filtros é retornado

### CT015 - Deve implementar HasVariavel
**Descrição**: Verificar se MensagemDTO implementa HasVariavel.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO
- When: Verifica-se a implementação
- Then: MensagemDTO é instância de HasVariavel

### CT016 - Deve retornar contexto com variáveis
**Descrição**: Verificar se o método getContext retorna um Map com as variáveis corretas.
**Pré-condições**: Uma instância de MensagemDTO com campos preenchidos
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com telefoneDestinatario, nomeDestinatario, corpoMensagem, dataEnvio e statusMensagem preenchidos
- When: O método getContext é chamado
- Then: Um Map com VariavelTemplate.TELEFONE, NOME, CORPO_MENSAGEM, DATA_ENVIO e STATUS é retornado

### CT017 - Deve ter classe CAMPOS com constantes
**Descrição**: Verificar se a classe CAMPOS tem as constantes corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe CAMPOS de MensagemDTO
- When: Verifica-se as constantes
- Then: As constantes TELEFONE_DESTINATARIO, NOME_DESTINATARIO, CORPO_MENSAGEM, TIPO_CANAL, STATUS_MENSAGEM, ID_EXTERNO, DATA_ENVIO, DATA_ENTREGA, DATA_LEITURA e MOTIVO_FALHA existem

### CT018 - Deve ter método values em CAMPOS
**Descrição**: Verificar se o método values da classe CAMPOS retorna um Set com os nomes dos campos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método values da classe CAMPOS é chamado
- When: O método é executado
- Then: Um Set com todos os nomes dos campos é retornado

### CT019 - Deve ter toString formatado
**Descrição**: Verificar se o método toString retorna uma string formatada.
**Pré-condições**: Uma instância de MensagemDTO com telefoneDestinatario e tipoCanal preenchidos
**Fluxo Principal**:
- Given: Uma instância de MensagemDTO com telefoneDestinatario "+5511999999999" e tipoCanal WHATSAPP
- When: O método toString é chamado
- Then: A string "+5511999999999 -> WHATSAPP" é retornada
