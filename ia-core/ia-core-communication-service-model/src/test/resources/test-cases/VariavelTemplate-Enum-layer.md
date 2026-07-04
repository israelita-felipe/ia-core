# Casos de Teste - VariavelTemplate (Enum Layer)

## Objetivo
Testar o enum VariavelTemplate que define todas as variáveis disponíveis para templates de mensagem.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- VariavelTemplate (enum)

## Casos de Teste

### CT001 - Deve ter constante CRIADO_EM
**Descrição**: Verificar se a constante CRIADO_EM existe com os valores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se a constante CRIADO_EM
- Then: A constante existe com chave "criadoEm", descrição "Data de criação", tipo "data", categoria "criacao" e obrigatoria true

### CT002 - Deve ter constante ATUALIZADO_EM
**Descrição**: Verificar se a constante ATUALIZADO_EM existe com os valores corretos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se a constante ATUALIZADO_EM
- Then: A constante existe com chave "atualizadoEm", descrição "Data de atualização", tipo "data", categoria "criacao" e obrigatoria true

### CT003 - Deve ter constantes de contato
**Descrição**: Verificar se as constantes de contato (NOME, TELEFONE, EMAIL) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de contato
- Then: As constantes NOME, TELEFONE e EMAIL existem com categoria "contato"

### CT004 - Deve ter constantes de mensagem
**Descrição**: Verificar se as constantes de mensagem (CORPO_MENSAGEM, DATA_ENVIO, STATUS) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de mensagem
- Then: As constantes CORPO_MENSAGEM, DATA_ENVIO e STATUS existem com categoria "mensagem"

### CT005 - Deve ter constantes de modelo
**Descrição**: Verificar se as constantes de modelo (NOME_MODELO, DESCRICAO_MODELO, CORPO_MODELO, TIPO_CANAL, ATIVO_MODELO) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de modelo
- Then: As constantes NOME_MODELO, DESCRICAO_MODELO, CORPO_MODELO, TIPO_CANAL e ATIVO_MODELO existem com categoria "modelo"

### CT006 - Deve ter constantes de grupo
**Descrição**: Verificar se as constantes de grupo (NOME_GRUPO, DESCRICAO_GRUPO, ATIVO_GRUPO) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de grupo
- Then: As constantes NOME_GRUPO, DESCRICAO_GRUPO e ATIVO_GRUPO existem com categoria "grupo"

### CT007 - Deve ter constantes de evento
**Descrição**: Verificar se as constantes de evento (NOME_EVENTO, DATA_EVENTO, HORA_EVENTO) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de evento
- Then: As constantes NOME_EVENTO, DATA_EVENTO e HORA_EVENTO existem com categoria "evento"

### CT008 - Deve ter constantes de sistema
**Descrição**: Verificar se as constantes de sistema (DATA_ATUAL, HORA_ATUAL) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de sistema
- Then: As constantes DATA_ATUAL e HORA_ATUAL existem com categoria "sistema"

### CT009 - Deve ter constantes de igreja
**Descrição**: Verificar se as constantes de igreja (NOME_IGREJA, ENDERECO_IGREJA, TELEFONE_IGREJA, EMAIL_IGREJA) existem.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum VariavelTemplate
- When: Verifica-se as constantes de igreja
- Then: As constantes NOME_IGREJA, ENDERECO_IGREJA, TELEFONE_IGREJA e EMAIL_IGREJA existem com categoria "igreja"

### CT010 - Deve implementar método getChave
**Descrição**: Verificar se o método getChave retorna a chave formatada com chaves duplas.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante NOME do enum
- When: O método getChave é chamado
- Then: A string "{{nome}}" é retornada

### CT011 - Deve implementar método getDescricao
**Descrição**: Verificar se o método getDescricao retorna a descrição da variável.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante NOME do enum
- When: O método getDescricao é chamado
- Then: A descrição "Nome do destinatário" é retornada

### CT012 - Deve implementar método getTipo
**Descrição**: Verificar se o método getTipo retorna o tipo da variável.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante NOME do enum
- When: O método getTipo é chamado
- Then: O tipo "texto" é retornado

### CT013 - Deve implementar método getCategoria
**Descrição**: Verificar se o método getCategoria retorna a categoria da variável.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante NOME do enum
- When: O método getCategoria é chamado
- Then: A categoria "contato" é retornada

### CT014 - Deve implementar método isObrigatoria
**Descrição**: Verificar se o método isObrigatoria retorna se a variável é obrigatória.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante CRIADO_EM do enum
- When: O método isObrigatoria é chamado
- Then: O valor true é retornado

### CT015 - Deve buscar por chave
**Descrição**: Verificar se o método buscarPorChave retorna a variável correta.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método buscarPorChave é chamado com "nome"
- When: O método é executado
- Then: A constante NOME é retornada

### CT016 - Deve retornar Optional vazio quando chave não encontrada
**Descrição**: Verificar se o método buscarPorChave retorna Optional vazio quando chave não existe.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método buscarPorChave é chamado com "chave_inexistente"
- When: O método é executado
- Then: Optional vazio é retornado

### CT017 - Deve listar todas as chaves
**Descrição**: Verificar se o método listarChaves retorna todas as chaves disponíveis.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método listarChaves é chamado
- When: O método é executado
- Then: Uma lista com todas as chaves formatadas é retornada
