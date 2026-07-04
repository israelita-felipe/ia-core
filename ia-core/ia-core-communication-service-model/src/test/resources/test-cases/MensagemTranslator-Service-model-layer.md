# Casos de Teste - MensagemTranslator (Service Model Layer)

## Objetivo
Testar a classe MensagemTranslator que contém constantes de tradução para MensagemDTO.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- MensagemTranslator (classe de constantes)

## Casos de Teste

### CT001 - Deve ter classe HELP com constantes
**Descrição**: Verificar se a classe HELP tem as constantes de help text corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe HELP de MensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MENSAGEM, TELEFONE_DESTINATARIO, NOME_DESTINATARIO, CORPO_MENSAGEM, TIPO_CANAL, STATUS_MENSAGEM, ID_EXTERNO, DATA_ENVIO, DATA_ENTREGA, DATA_LEITURA e MOTIVO_FALHA existem

### CT002 - Deve ter classe VALIDATION com constantes
**Descrição**: Verificar se a classe VALIDATION tem as constantes de validação corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe VALIDATION de MensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes TELEFONE_DESTINATARIO_NOT_BLANK, TELEFONE_DESTINATARIO_SIZE, NOME_DESTINATARIO_SIZE, CORPO_MENSAGEM_NOT_BLANK, TIPO_CANAL_NOT_NULL, STATUS_MENSAGEM_NOT_NULL, ID_EXTERNO_SIZE e MOTIVO_FALHA_SIZE existem

### CT003 - Deve ter classe RULE com constantes
**Descrição**: Verificar se a classe RULE tem as constantes de regras de negócio corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe RULE de MensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes CANAL_NAO_SUPORTADO, TELEFONE_INVALIDO, MODELO_NAO_ENCONTRADO e GRUPO_NAO_ENCONTRADO existem

### CT004 - Deve ter classe MESSAGE com constantes
**Descrição**: Verificar se a classe MESSAGE tem as constantes de mensagem corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe MESSAGE de MensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes ENVIADO_SUCESSO, ENVIADO_EM_MASSA_SUCESSO, STATUS_ATUALIZADO e FALHA_ENVIO existem

### CT005 - Deve ter classe EVENT com constantes
**Descrição**: Verificar se a classe EVENT tem as constantes de evento corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe EVENT de MensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MENSAGEM_ENVIADA, MENSAGEM_ENTREGUE, MENSAGEM_LIDA e MENSAGEM_FALHOU existem

### CT006 - Deve ter constante de classe canônica
**Descrição**: Verificar se a constante MENSAGEM_CLASS existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe MensagemTranslator
- When: Verifica-se a constante
- Then: A constante MENSAGEM_CLASS existe e contém o nome canônico de MensagemDTO

### CT007 - Deve ter constantes de nomes de campos
**Descrição**: Verificar se as constantes de nomes de campos existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe MensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MENSAGEM, TELEFONE_DESTINATARIO, NOME_DESTINATARIO, CORPO_MENSAGEM, TIPO_CANAL, STATUS_MENSAGEM, ID_EXTERNO, DATA_ENVIO, DATA_ENTREGA, DATA_LEITURA e MOTIVO_FALHA existem

### CT008 - Deve ter construtor privado
**Descrição**: Verificar se a classe MensagemTranslator tem um construtor privado.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe MensagemTranslator
- When: Tenta-se instanciar a classe
- Then: Não é possível instanciar a classe (utility class)
