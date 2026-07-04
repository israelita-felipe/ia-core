# Casos de Teste - ModeloMensagemTranslator (Service Model Layer)

## Objetivo
Testar a classe ModeloMensagemTranslator que contém constantes de tradução para ModeloMensagemDTO.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ModeloMensagemTranslator (classe de constantes)

## Casos de Teste

### CT001 - Deve ter classe HELP com constantes
**Descrição**: Verificar se a classe HELP tem as constantes de help text corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe HELP de ModeloMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MODELO_MENSAGEM, NOME, DESCRICAO, CORPO_MODELO, TIPO_CANAL e ATIVO existem

### CT002 - Deve ter classe VALIDATION com constantes
**Descrição**: Verificar se a classe VALIDATION tem as constantes de validação corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe VALIDATION de ModeloMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes NOME_NOT_BLANK, NOME_SIZE, DESCRICAO_SIZE, CORPO_MODELO_NOT_BLANK e TIPO_CANAL_NOT_NULL existem

### CT003 - Deve ter classe RULE com constantes
**Descrição**: Verificar se a classe RULE tem as constantes de regras de negócio corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe RULE de ModeloMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes NOME_DUPLICADO, MODELO_INATIVO, VARIAVEIS_INVALIDAS, MODELO_NAO_ENCONTRADO e CONTATO_NAO_ENCONTRADO existem

### CT004 - Deve ter classe MESSAGE com constantes
**Descrição**: Verificar se a classe MESSAGE tem as constantes de mensagem corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe MESSAGE de ModeloMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes CRIADO_SUCESSO, ATUALIZADO_SUCESSO e DELETADO_SUCESSO existem

### CT005 - Deve ter classe EVENT com constantes
**Descrição**: Verificar se a classe EVENT tem as constantes de evento corretas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe EVENT de ModeloMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MODELO_CRIADO e MODELO_ATUALIZADO existem

### CT006 - Deve ter constante de classe canônica
**Descrição**: Verificar se a constante MODELO_MENSAGEM_CLASS existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe ModeloMensagemTranslator
- When: Verifica-se a constante
- Then: A constante MODELO_MENSAGEM_CLASS existe e contém o nome canônico de ModeloMensagemDTO

### CT007 - Deve ter constantes de nomes de campos
**Descrição**: Verificar se as constantes de nomes de campos existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe ModeloMensagemTranslator
- When: Verifica-se as constantes
- Then: As constantes MODELO_MENSAGEM, NOME, DESCRICAO, CORPO_MODELO, TIPO_CANAL e ATIVO existem

### CT008 - Deve ter construtor privado
**Descrição**: Verificar se a classe ModeloMensagemTranslator tem um construtor privado.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A classe ModeloMensagemTranslator
- When: Tenta-se instanciar a classe
- Then: Não é possível instanciar a classe (utility class)
