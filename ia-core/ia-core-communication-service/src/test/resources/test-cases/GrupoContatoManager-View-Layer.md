# GrupoContatoManager - Casos de Teste da Camada de View

## Visão Geral
Casos de teste para a classe `GrupoContatoManager`, verificando operações de gerenciamento de grupo contato na view.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseVaadinManagerTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Carregar Lista de Grupo Contato
**Dado**: Um GrupoContatoManager
**Quando**: Lista de grupo contato é carregada
**Então**: Deve atualizar a view com a lista

#### 1.1 Carregamento bem-sucedido
- **Dado**: Um GrupoContatoManager com client mockado
- **Quando**: loadList() é chamado
- **Então**: Deve carregar lista de grupo contatos
- **Casos de Borda**: Lista vazia

#### 1.2 Falha de carregamento
- **Dado**: Um GrupoContatoManager com erro no client
- **Quando**: loadList() é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 2. Carregar Detalhes de Grupo Contato
**Dado**: Um GrupoContatoManager
**Quando**: Detalhes de grupo contato são carregados
**Então**: Deve atualizar a view com os detalhes

#### 2.1 Carregamento bem-sucedido
- **Dado**: Um GrupoContatoManager com client mockado
- **Quando**: loadDetails(id) é chamado
- **Então**: Deve carregar detalhes do grupo contato
- **Casos de Borda**: ID nulo, ID inválido

#### 2.2 Falha de carregamento
- **Dado**: Um GrupoContatoManager com erro no client
- **Quando**: loadDetails(id) é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 3. Salvar Grupo Contato
**Dado**: Um GrupoContatoManager
**Quando**: Grupo contato é salvo
**Então**: Deve enviar dados para o client

#### 3.1 Salvamento bem-sucedido
- **Dado**: Um GrupoContatoManager com dados válidos
- **Quando**: save(group) é chamado
- **Então**: Deve salvar grupo contato
- **Casos de Borda**: Nenhum

#### 3.2 Falha de validação
- **Dado**: Um GrupoContatoManager com dados inválidos
- **Quando**: save(group) é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Grupo nulo, campos obrigatórios faltando

### 4. Deletar Grupo Contato
**Dado**: Um GrupoContatoManager
**Quando**: Grupo contato é deletado
**Então**: Deve enviar requisição para o client

#### 4.1 Deleção bem-sucedida
- **Dado**: Um GrupoContatoManager com ID válido
- **Quando**: delete(id) é chamado
- **Então**: Deve deletar grupo contato
- **Casos de Borda**: Nenhum

#### 4.2 Falha de deleção
- **Dado**: Um GrupoContatoManager com erro no client
- **Quando**: delete(id) é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: ID nulo, ID inválido

### 5. Adicionar Contato ao Grupo
**Dado**: Um GrupoContatoManager
**Quando**: Contato é adicionado ao grupo
**Então**: Deve atualizar a view

#### 5.1 Adição bem-sucedida
- **Dado**: Um GrupoContatoManager com contato válido
- **Quando**: addContact(groupId, contactId) é chamado
- **Então**: Deve adicionar contato ao grupo
- **Casos de Borda**: Contato já no grupo

#### 5.2 Falha de validação
- **Dado**: Um GrupoContatoManager com contato inválido
- **Quando**: addContact(groupId, contactId) é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Grupo nulo, contato nulo

### 6. Remover Contato do Grupo
**Dado**: Um GrupoContatoManager
**Quando**: Contato é removido do grupo
**Então**: Deve atualizar a view

#### 6.1 Remoção bem-sucedida
- **Dado**: Um GrupoContatoManager com contato no grupo
- **Quando**: removeContact(groupId, contactId) é chamado
- **Então**: Deve remover contato do grupo
- **Casos de Borda**: Contato não no grupo

#### 6.2 Falha de validação
- **Dado**: Um GrupoContatoManager com contato inválido
- **Quando**: removeContact(groupId, contactId) é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Grupo nulo, contato nulo

## Fluxo do Teste
1. Criar instância de GrupoContatoManager
2. Testar carregar lista de grupo contato (sucesso, falha)
3. Testar carregar detalhes de grupo contato (sucesso, falha)
4. Testar salvar grupo contato (sucesso, falha de validação)
5. Testar deletar grupo contato (sucesso, falha)
6. Testar adicionar contato ao grupo (sucesso, falha de validação)
7. Testar remover contato do grupo (sucesso, falha de validação)
8. Verificar casos de borda (valores nulos, IDs inválidos)
