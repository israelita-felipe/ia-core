# GrupoContatoService - Casos de Teste da Camada de Serviço

## Visão Geral
Casos de teste para a classe `GrupoContatoService`, verificando operações de CRUD e validações de negócio.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseServiceTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito, Instancio

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de GrupoContatoService
**Quando**: O serviço é instanciado
**Então**: Deve inicializar com repository e mapper

#### 1.1 Construtor com repository e mapper
- **Dado**: Instâncias de repository e mapper
- **Quando**: new GrupoContatoService(repository, mapper) é chamado
- **Então**: Deve inicializar com dependências fornecidas
- **Casos de Borda**: Repository nulo, mapper nulo

### 2. Buscar por ID
**Dado**: Uma GrupoContatoService
**Quando**: Busca por ID é chamada
**Então**: Deve retornar grupo contato

#### 2.1 Busca bem-sucedida
- **Dado**: Um ID de grupo contato válido
- **Quando**: findById(id) é chamado
- **Então**: Deve retornar grupo contato
- **Casos de Borda**: Nenhum

#### 2.2 Não encontrado
- **Dado**: Um ID de grupo contato inválido
- **Quando**: findById(id) é chamado
- **Então**: Deve retornar nulo ou lançar exceção
- **Casos de Borda**: ID nulo, ID negativo

### 3. Listar Todos
**Dado**: Uma GrupoContatoService
**Quando**: Listar todos é chamado
**Então**: Deve retornar lista de grupo contatos

#### 3.1 Listagem bem-sucedida
- **Dado**: Grupo contatos existem
- **Quando**: findAll() é chamado
- **Então**: Deve retornar lista de grupo contatos
- **Casos de Borda**: Lista vazia

#### 3.2 Com paginação
- **Dado**: Grupo contatos com parâmetros de paginação
- **Quando**: findAll(pageable) é chamado
- **Então**: Deve retornar resultados paginados
- **Casos de Borda**: Parâmetros de página inválidos

### 4. Criar
**Dado**: Uma GrupoContatoService
**Quando**: Criar é chamado
**Então**: Deve criar novo grupo contato

#### 4.1 Criação bem-sucedida
- **Dado**: Um grupo contato válido
- **Quando**: create(group) é chamado
- **Então**: Deve salvar e retornar entidade criada
- **Casos de Borda**: Nenhum

#### 4.2 Falha de validação
- **Dado**: Um grupo contato inválido
- **Quando**: create(group) é chamado
- **Então**: Deve lançar exceção de validação
- **Casos de Borda**: Grupo nulo, campos obrigatórios faltando

### 5. Atualizar
**Dado**: Uma GrupoContatoService
**Quando**: Atualizar é chamado
**Então**: Deve atualizar grupo contato

#### 5.1 Atualização bem-sucedida
- **Dado**: Um grupo contato válido
- **Quando**: update(group) é chamado
- **Então**: Deve salvar e retornar entidade atualizada
- **Casos de Borda**: Nenhum

#### 5.2 Não encontrado
- **Dado**: Um grupo contato com ID inválido
- **Quando**: update(group) é chamado
- **Então**: Deve lançar exceção de não encontrado
- **Casos de Borda**: Nenhum

### 6. Deletar
**Dado**: Uma GrupoContatoService
**Quando**: Deletar é chamado
**Então**: Deve deletar grupo contato

#### 6.1 Deleção bem-sucedida
- **Dado**: Um ID de grupo contato válido
- **Quando**: delete(id) é chamado
- **Então**: Deve deletar grupo contato
- **Casos de Borda**: Nenhum

#### 6.2 Não encontrado
- **Dado**: Um ID de grupo contato inválido
- **Quando**: delete(id) é chamado
- **Então**: Deve lançar exceção de não encontrado
- **Casos de Borda**: Nenhum

### 7. Adicionar Contato ao Grupo
**Dado**: Uma GrupoContatoService
**Quando**: Adicionar contato ao grupo é chamado
**Então**: Deve adicionar contato ao grupo

#### 7.1 Adição bem-sucedida
- **Dado**: Um grupo contato e um contato válido
- **Quando**: addContact(groupId, contactId) é chamado
- **Então**: Deve adicionar contato ao grupo
- **Casos de Borda**: Contato já no grupo

#### 7.2 Falha de validação
- **Dado**: Um grupo contato ou contato inválido
- **Quando**: addContact(groupId, contactId) é chamado
- **Então**: Deve lançar exceção de validação
- **Casos de Borda**: Grupo nulo, contato nulo

### 8. Remover Contato do Grupo
**Dado**: Uma GrupoContatoService
**Quando**: Remover contato do grupo é chamado
**Então**: Deve remover contato do grupo

#### 8.1 Remoção bem-sucedida
- **Dado**: Um grupo contato com um contato
- **Quando**: removeContact(groupId, contactId) é chamado
- **Então**: Deve remover contato do grupo
- **Casos de Borda**: Contato não no grupo

#### 8.2 Falha de validação
- **Dado**: Um grupo contato ou contato inválido
- **Quando**: removeContact(groupId, contactId) é chamado
- **Então**: Deve lançar exceção de validação
- **Casos de Borda**: Grupo nulo, contato nulo

## Fluxo do Teste
1. Criar instância de GrupoContatoService
2. Testar inicialização do construtor
3. Testar buscar por ID (sucesso, não encontrado)
4. Testar listar todos (sucesso, paginação)
5. Testar criar (sucesso, falha de validação)
6. Testar atualizar (sucesso, não encontrado)
7. Testar deletar (sucesso, não encontrado)
8. Testar adicionar contato ao grupo (sucesso, falha de validação)
9. Testar remover contato do grupo (sucesso, falha de validação)
10. Verificar casos de borda (valores nulos, parâmetros inválidos)
