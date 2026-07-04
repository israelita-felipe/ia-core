# Casos de Teste - EnvioMensagemTranslator (Service Model Layer)

## Objetivo
Testar a classe EnvioMensagemTranslator que contém constantes de tradução para EnvioMensagemDTO.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- EnvioMensagemTranslator (classe de constantes)

## Casos de Teste

### CT001 - Deve ter classe HELP com constantes
**Descrição**: Verificar se a classe HELP tem as constantes de help text corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe HELP de EnvioMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes ENVIO_MENSAGEM, TIPO_CANAL, CORPO_MENSAGEM, MODELO_MENSAGEM_ID, PARAMETROS_TEMPLATE, TELEFONES, GRUPOS_CONTATO_IDS, AGENDADO e DATA_AGENDAMENTO existem

### CT002 - Deve ter classe VALIDATION com constantes
**Descrição**: Verificar se a classe VALIDATION tem as constantes de validação corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe VALIDATION de EnvioMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes TIPO_CANAL_NOT_NULL, CORPO_MENSAGEM_NOT_BLANK, MODELO_MENSAGEM_ID_NULL e TELEFONES_NOT_EMPTY existem

### CT003 - Deve ter classe RULE com constantes
**Descrição**: Verificar se a classe RULE tem as constantes de regras de negócio corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe RULE de EnvioMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes CANAL_NAO_SUPORTADO, MODELO_NAO_ENCONTRADO, TELEFONE_INVALIDO e AGENDAMENTO_PASSADO existem

### CT004 - Deve ter classe MESSAGE com constantes
**Descrição**: Verificar se a classe MESSAGE tem as constantes de mensagem corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe MESSAGE de EnvioMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes ENVIADO_SUCESSO, AGENDADO_SUCESSO e ERRO_ENVIO existem

### CT005 - Deve ter classe EVENT com constantes
**Descrição**: Verificar se a classe EVENT tem as constantes de evento corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe EVENT de EnvioMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MENSAGEM_ENVIADA, MENSAGEM_AGENDADA e MENSAGEM_ERRO existem

### CT006 - Deve ter constante de classe canônica
**Descrição**: Verificar se a constante ENVIO_MENSAGEM_CLASS existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe EnvioMensagemTranslator
- When: Verifica-se a constante
- Then: A constante ENVIO_MENSAGEM_CLASS existe e contém o nome canônico de EnvioMensagemRequestDTO

### CT007 - Deve ter constantes de nomes de campos
**Descrição**: Verificar se as constantes de nomes de campos existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe EnvioMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes ENVIO_MENSAGEM, TIPO_CANAL, CORPO_MENSAGEM, MODELO_MENSAGEM_ID, PARAMETROS_TEMPLATE, TELEFONES, GRUPOS_CONTATO_IDS, AGENDADO e DATA_AGENDAMENTO existem

### CT008 - Deve ter construtor privado
**Descrição**: Verificar se a classe EnvioMensagemTranslator tem um construtor privado.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe EnvioMensagemTranslator
- When: Tenta-se instanciar a classe
- Then: Não é possível instanciar a classe (utility class)
