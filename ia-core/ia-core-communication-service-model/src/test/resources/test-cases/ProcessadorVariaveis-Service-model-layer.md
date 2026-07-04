# Casos de Teste - ProcessadorVariaveis (Service Model Layer)

## Objetivo
Testar a classe ProcessadorVariaveis que processa variáveis em templates de mensagens.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ProcessadorVariaveis (classe de serviço)

## Casos de Teste

### CT001 - Deve processar template com variáveis
**Descrição**: Verificar se o método processar substitui variáveis no template pelos valores do contexto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template "Olá {{nome}}, bem-vindo!" e um contexto com nome="João"
- When: O método processar é chamado
- Then: O template processado é "Olá João, bem-vindo!"

### CT002 - Deve retornar string vazia quando template é nulo
**Descrição**: Verificar se o método processar retorna string vazia quando template é nulo.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template nulo
- When: O método processar é chamado
- Then: String vazia é retornada

### CT003 - Deve retornar string vazia quando template é vazio
**Descrição**: Verificar se o método processar retorna string vazia quando template é vazio.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template vazio
- When: O método processar é chamado
- Then: String vazia é retornada

### CT004 - Deve processar template com HasVariavel
**Descrição**: Verificar se o método processar aceita um objeto HasVariavel.
**Pré-condições**: Uma implementação de HasVariavel com contexto
**Fluxo Principal**:
- Given: Um template "Olá {{nome}}" e um objeto HasVariavel com nome="Maria"
- When: O método processar é chamado com o objeto HasVariavel
- Then: O template processado é "Olá Maria"

### CT005 - Deve manter texto sem variáveis
**Descrição**: Verificar se o método processar mantém texto que não possui variáveis.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template "Olá, bem-vindo!" sem variáveis
- When: O método processar é chamado
- Then: O texto original é retornado sem modificações

### CT006 - Deve substituir múltiplas variáveis
**Descrição**: Verificar se o método processar substitui múltiplas variáveis no mesmo template.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template "Olá {{nome}}, seu agendamento é {{evento}}" com contexto nome="João" e evento="Reunião"
- When: O método processar é chamado
- Then: O template processado é "Olá João, seu agendamento é Reunião"

### CT007 - Deve retornar string vazia para variável não encontrada
**Descrição**: Verificar se o método processar retorna string vazia quando variável não existe no contexto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template "Olá {{nome}}" com contexto vazio
- When: O método processar é chamado
- Then: O template processado é "Olá "

### CT008 - Deve listar chaves disponíveis
**Descrição**: Verificar se o método listarChavesDisponiveis retorna todas as chaves das variáveis.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O método listarChavesDisponiveis é chamado
- When: O método é executado
- Then: Uma lista com todas as chaves disponíveis é retornada

### CT009 - Deve processar variáveis com ponto
**Descrição**: Verificar se o método processar suporta variáveis com ponto (ex: evento.data).
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Um template "Data: {{evento.data}}" com contexto apropriado
- When: O método processar é chamado
- Then: A variável com ponto é substituída corretamente
