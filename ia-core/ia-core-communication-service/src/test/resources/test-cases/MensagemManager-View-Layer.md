# MensagemManager - Casos de Teste da Camada de View

## Visão Geral
Casos de teste para a classe `MensagemManager`, verificando operações de gerenciamento de mensagem na view.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseVaadinManagerTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Carregar Lista de Mensagem
**Dado**: Um MensagemManager
**Quando**: Lista de mensagem é carregada
**Então**: Deve atualizar a view com a lista

#### 1.1 Carregamento bem-sucedido
- **Dado**: Um MensagemManager com client mockado
- **Quando**: loadList() é chamado
- **Então**: Deve carregar lista de mensagens
- **Casos de Borda**: Lista vazia

#### 1.2 Falha de carregamento
- **Dado**: Um MensagemManager com erro no client
- **Quando**: loadList() é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 2. Carregar Detalhes de Mensagem
**Dado**: Um MensagemManager
**Quando**: Detalhes de mensagem são carregados
**Então**: Deve atualizar a view com os detalhes

#### 2.1 Carregamento bem-sucedido
- **Dado**: Um MensagemManager com client mockado
- **Quando**: loadDetails(id) é chamado
- **Então**: Deve carregar detalhes da mensagem
- **Casos de Borda**: ID nulo, ID inválido

#### 2.2 Falha de carregamento
- **Dado**: Um MensagemManager com erro no client
- **Quando**: loadDetails(id) é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 3. Salvar Mensagem
**Dado**: Um MensagemManager
**Quando**: Mensagem é salva
**Então**: Deve enviar dados para o client

#### 3.1 Salvamento bem-sucedido
- **Dado**: Um MensagemManager com dados válidos
- **Quando**: save(message) é chamado
- **Então**: Deve salvar mensagem
- **Casos de Borda**: Nenhum

#### 3.2 Falha de validação
- **Dado**: Um MensagemManager com dados inválidos
- **Quando**: save(message) é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Mensagem nula, campos obrigatórios faltando

### 4. Deletar Mensagem
**Dado**: Um MensagemManager
**Quando**: Mensagem é deletada
**Então**: Deve enviar requisição para o client

#### 4.1 Deleção bem-sucedida
- **Dado**: Um MensagemManager com ID válido
- **Quando**: delete(id) é chamado
- **Então**: Deve deletar mensagem
- **Casos de Borda**: Nenhum

#### 4.2 Falha de deleção
- **Dado**: Um MensagemManager com erro no client
- **Quando**: delete(id) é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: ID nulo, ID inválido

### 5. Enviar Mensagem
**Dado**: Um MensagemManager
**Quando**: Mensagem é enviada
**Então**: Deve atualizar a view

#### 5.1 Envio bem-sucedido
- **Dado**: Um MensagemManager com mensagem válida
- **Quando**: send(message) é chamado
- **Então**: Deve enviar mensagem
- **Casos de Borda**: Nenhum

#### 5.2 Falha de validação
- **Dado**: Um MensagemManager com mensagem inválida
- **Quando**: send(message) é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Mensagem nula, campos obrigatórios faltando

### 6. Atualizar Status da Mensagem
**Dado**: Um MensagemManager
**Quando**: Status da mensagem é atualizado
**Então**: Deve atualizar a view

#### 6.1 Atualização bem-sucedida
- **Dado**: Um MensagemManager com status válido
- **Quando**: updateStatus(messageId, status) é chamado
- **Então**: Deve atualizar status da mensagem
- **Casos de Borda**: Status inválido

#### 6.2 Falha de validação
- **Dado**: Um MensagemManager com status inválido
- **Quando**: updateStatus(messageId, status) é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Mensagem nula, status nulo

## Fluxo do Teste
1. Criar instância de MensagemManager
2. Testar carregar lista de mensagem (sucesso, falha)
3. Testar carregar detalhes de mensagem (sucesso, falha)
4. Testar salvar mensagem (sucesso, falha de validação)
5. Testar deletar mensagem (sucesso, falha)
6. Testar enviar mensagem (sucesso, falha de validação)
7. Testar atualizar status da mensagem (sucesso, falha de validação)
8. Verificar casos de borda (valores nulos, IDs inválidos)
