# Casos de Teste - EnvioMensagemRequestDTO (Service Model Layer)

## Objetivo
Testar o DTO EnvioMensagemRequestDTO que representa os dados de transferência para requisição de envio de mensagem em massa.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- EnvioMensagemRequestDTO (DTO)

## Casos de Teste

### CT001 - Deve criar DTO com campos obrigatórios
**Descrição**: Verificar se é possível criar um EnvioMensagemRequestDTO com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO com tipoCanal e corpoMensagem preenchidos
- When: O DTO é criado
- Then: O DTO é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo tipoCanal obrigatório
**Descrição**: Verificar se o campo tipoCanal é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO com tipoCanal nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar campo corpoMensagem obrigatório
**Descrição**: Verificar se o campo corpoMensagem é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO com corpoMensagem vazio ou nulo
- When: A validação é executada
- Then: Uma exceção de validação é lançada

### CT004 - Deve criar DTO usando builder
**Descrição**: Verificar se é possível criar um EnvioMensagemRequestDTO usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de EnvioMensagemRequestDTO
- When: O DTO é construído usando o builder
- Then: O DTO é criado com sucesso com todos os campos especificados

### CT005 - Deve ter valor padrão para agendado
**Descrição**: Verificar se o campo agendado tem valor padrão false.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO criada sem especificar agendado
- When: O DTO é criado
- Then: O campo agendado tem valor false

### CT006 - Deve implementar HasVariavel
**Descrição**: Verificar se EnvioMensagemRequestDTO implementa HasVariavel.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO
- When: Verifica-se a implementação
- Then: EnvioMensagemRequestDTO é instância de HasVariavel

### CT007 - Deve retornar contexto com variáveis
**Descrição**: Verificar se o método getContext retorna um Map com as variáveis corretas.
**Pré-condições**: Uma instância de EnvioMensagemRequestDTO com tipoCanal e corpoMensagem preenchidos
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO com tipoCanal e corpoMensagem preenchidos
- When: O método getContext é chamado
- Then: Um Map com VariavelTemplate.TIPO_CANAL e CORPO_MENSAGEM é retornado

### CT008 - Deve ter campo modeloMensagemId
**Descrição**: Verificar se o campo modeloMensagemId existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO
- When: Verifica-se o campo
- Then: O campo modeloMensagemId existe

### CT009 - Deve ter campo parametrosTemplate
**Descrição**: Verificar se o campo parametrosTemplate existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO
- When: Verifica-se o campo
- Then: O campo parametrosTemplate existe

### CT010 - Deve ter campo telefones
**Descrição**: Verificar se o campo telefones existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO
- When: Verifica-se o campo
- Then: O campo telefones existe

### CT011 - Deve ter campo gruposContatoIds
**Descrição**: Verificar se o campo gruposContatoIds existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO
- When: Verifica-se o campo
- Then: O campo gruposContatoIds existe

### CT012 - Deve ter campo dataAgendamento
**Descrição**: Verificar se o campo dataAgendamento existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de EnvioMensagemRequestDTO
- When: Verifica-se o campo
- Then: O campo dataAgendamento existe
