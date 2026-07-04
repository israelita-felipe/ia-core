# ContatoMensagemService - Casos de Teste da Camada de Serviço

## Visão Geral
Casos de teste para a classe `ContatoMensagemService`, verificando operações de CRUD e validações de negócio.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseServiceTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito, Instancio

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de ContatoMensagemService
**Quando**: O serviço é instanciado
**Então**: Deve inicializar com repository e mapper

#### 1.1 Construtor com repository e mapper
- **Dado**: Instâncias de repository e mapper
- **Quando**: new ContatoMensagemService(repository, mapper) é chamado
- **Então**: Deve inicializar com dependências fornecidas
- **Casos de Borda**: Repository nulo, mapper nulo

### 2. Buscar por ID
**Dado**: Uma ContatoMensagemService
**Quando**: Busca por ID é chamada
**Então**: Deve retornar contato mensagem

#### 2.1 Busca bem-sucedida
- **Dado**: Um ID de contato mensagem válido
- **Quando**: findById(id) é chamado
- **Então**: Deve retornar contato mensagem
- **Casos de Borda**: Nenhum

#### 2.2 Não encontrado
- **Dado**: Um ID de contato mensagem inválido
- **Quando**: findById(id) é chamado
- **Então**: Deve retornar nulo ou lançar exceção
- **Casos de Borda**: ID nulo, ID negativo

### 3. Listar Todos
**Dado**: Uma ContatoMensagemService
**Quando**: Listar todos é chamado
**Então**: Deve retornar lista de contato mensagens

#### 3.1 Listagem bem-sucedida
- **Dado**: Contato mensagens existem
- **Quando**: findAll() é chamado
- **Então**: Deve retornar lista de contato mensagens
- **Casos de Borda**: Lista vazia

#### 3.2 Com paginação
- **Dado**: Contato mensagens com parâmetros de paginação
- **Quando**: findAll(pageable) é chamado
- **Então**: Deve retornar resultados paginados
- **Casos de Borda**: Parâmetros de página inválidos

### 4. Criar
**Dado**: Uma ContatoMensagemService
**Quando**: Criar é chamado
**Então**: Deve criar novo contato mensagem

#### 4.1 Criação bem-sucedida
- **Dado**: Um contato mensagem válido
- **Quando**: create(message) é chamado
- **Então**: Deve salvar e retornar entidade criada
- **Casos de Borda**: Nenhum

#### 4.2 Falha de validação
- **Dado**: Um contato mensagem inválido
- **Quando**: create(message) é chamado
- **Então**: Deve lançar exceção de validação
- **Casos de Borda**: Mensagem nula, campos obrigatórios faltando

### 5. Atualizar
**Dado**: Uma ContatoMensagemService
**Quando**: Atualizar é chamado
**Então**: Deve atualizar contato mensagem

#### 5.1 Atualização bem-sucedida
- **Dado**: Um contato mensagem válido
- **Quando**: update(message) é chamado
- **Então**: Deve salvar e retornar entidade atualizada
- **Casos de Borda**: Nenhum

#### 5.2 Não encontrado
- **Dado**: Um contato mensagem com ID inválido
- **Quando**: update(message) é chamado
- **Então**: Deve lançar exceção de não encontrado
- **Casos de Borda**: Nenhum

### 6. Deletar
**Dado**: Uma ContatoMensagemService
**Quando**: Deletar é chamado
**Então**: Deve deletar contato mensagem

#### 6.1 Deleção bem-sucedida
- **Dado**: Um ID de contato mensagem válido
- **Quando**: delete(id) é chamado
- **Então**: Deve deletar contato mensagem
- **Casos de Borda**: Nenhum

#### 6.2 Não encontrado
- **Dado**: Um ID de contato mensagem inválido
- **Quando**: delete(id) é chamado
- **Então**: Deve lançar exceção de não encontrado
- **Casos de Borda**: Nenhum

### 7. Validação de Negócio
**Dado**: Uma ContatoMensagemService
**Quando**: Validação de negócio é realizada
**Então**: Deve validar regras de negócio

#### 7.1 Dados válidos
- **Dado**: Dados de contato mensagem válidos
- **Quando**: Validação é realizada
- **Então**: Deve passar na validação
- **Casos de Borda**: Nenhum

#### 7.2 Dados inválidos
- **Dado**: Dados de contato mensagem inválidos
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Contato duplicado, mensagem inválida

## Fluxo do Teste
1. Criar instância de ContatoMensagemService
2. Testar inicialização do construtor
3. Testar buscar por ID (sucesso, não encontrado)
4. Testar listar todos (sucesso, paginação)
5. Testar criar (sucesso, falha de validação)
6. Testar atualizar (sucesso, não encontrado)
7. Testar deletar (sucesso, não encontrado)
8. Testar validação de negócio (válido, inválido)
9. Verificar casos de borda (valores nulos, parâmetros inválidos)
