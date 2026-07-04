# MensagemService - Casos de Teste da Camada de Serviço

## Visão Geral
Casos de teste para a classe `MensagemService`, verificando operações de CRUD e validações de negócio.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseServiceTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito, Instancio

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de MensagemService
**Quando**: O serviço é instanciado
**Então**: Deve inicializar com repository e mapper

#### 1.1 Construtor com repository e mapper
- **Dado**: Instâncias de repository e mapper
- **Quando**: new MensagemService(repository, mapper) é chamado
- **Então**: Deve inicializar com dependências fornecidas
- **Casos de Borda**: Repository nulo, mapper nulo

### 2. Buscar por ID
**Dado**: Uma MensagemService
**Quando**: Busca por ID é chamada
**Então**: Deve retornar mensagem

#### 2.1 Busca bem-sucedida
- **Dado**: Um ID de mensagem válido
- **Quando**: findById(id) é chamado
- **Então**: Deve retornar mensagem
- **Casos de Borda**: Nenhum

#### 2.2 Não encontrado
- **Dado**: Um ID de mensagem inválido
- **Quando**: findById(id) é chamado
- **Então**: Deve retornar nulo ou lançar exceção
- **Casos de Borda**: ID nulo, ID negativo

### 3. Listar Todos
**Dado**: Uma MensagemService
**Quando**: Listar todos é chamado
**Então**: Deve retornar lista de mensagens

#### 3.1 Listagem bem-sucedida
- **Dado**: Mensagens existem
- **Quando**: findAll() é chamado
- **Então**: Deve retornar lista de mensagens
- **Casos de Borda**: Lista vazia

#### 3.2 Com paginação
- **Dado**: Mensagens com parâmetros de paginação
- **Quando**: findAll(pageable) é chamado
- **Então**: Deve retornar resultados paginados
- **Casos de Borda**: Parâmetros de página inválidos

### 4. Criar
**Dado**: Uma MensagemService
**Quando**: Criar é chamado
**Então**: Deve criar nova mensagem

#### 4.1 Criação bem-sucedida
- **Dado**: Uma mensagem válida
- **Quando**: create(message) é chamado
- **Então**: Deve salvar e retornar entidade criada
- **Casos de Borda**: Nenhum

#### 4.2 Falha de validação
- **Dado**: Uma mensagem inválida
- **Quando**: create(message) é chamado
- **Então**: Deve lançar exceção de validação
- **Casos de Borda**: Mensagem nula, campos obrigatórios faltando

### 5. Atualizar
**Dado**: Uma MensagemService
**Quando**: Atualizar é chamado
**Então**: Deve atualizar mensagem

#### 5.1 Atualização bem-sucedida
- **Dado**: Uma mensagem válida
- **Quando**: update(message) é chamado
- **Então**: Deve salvar e retornar entidade atualizada
- **Casos de Borda**: Nenhum

#### 5.2 Não encontrado
- **Dado**: Uma mensagem com ID inválido
- **Quando**: update(message) é chamado
- **Então**: Deve lançar exceção de não encontrado
- **Casos de Borda**: Nenhum

### 6. Deletar
**Dado**: Uma MensagemService
**Quando**: Deletar é chamado
**Então**: Deve deletar mensagem

#### 6.1 Deleção bem-sucedida
- **Dado**: Um ID de mensagem válido
- **Quando**: delete(id) é chamado
- **Então**: Deve deletar mensagem
- **Casos de Borda**: Nenhum

#### 6.2 Não encontrado
- **Dado**: Um ID de mensagem inválido
- **Quando**: delete(id) é chamado
- **Então**: Deve lançar exceção de não encontrado
- **Casos de Borda**: Nenhum

### 7. Atualizar Status da Mensagem
**Dado**: Uma MensagemService
**Quando**: Atualizar status é chamado
**Então**: Deve atualizar status da mensagem

#### 7.1 Atualização de status bem-sucedida
- **Dado**: Uma mensagem e um status válido
- **Quando**: updateStatus(messageId, status) é chamado
- **Então**: Deve atualizar status da mensagem
- **Casos de Borda**: Status inválido

#### 7.2 Falha de validação
- **Dado**: Uma mensagem ou status inválido
- **Quando**: updateStatus(messageId, status) é chamado
- **Então**: Deve lançar exceção de validação
- **Casos de Borda**: Mensagem nula, status nulo

## Fluxo do Teste
1. Criar instância de MensagemService
2. Testar inicialização do construtor
3. Testar buscar por ID (sucesso, não encontrado)
4. Testar listar todos (sucesso, paginação)
5. Testar criar (sucesso, falha de validação)
6. Testar atualizar (sucesso, não encontrado)
7. Testar deletar (sucesso, não encontrado)
8. Testar atualizar status da mensagem (sucesso, falha de validação)
9. Verificar casos de borda (valores nulos, parâmetros inválidos)
