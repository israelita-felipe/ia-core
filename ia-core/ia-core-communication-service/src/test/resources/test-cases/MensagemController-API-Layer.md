# MensagemController - Casos de Teste da Camada de API

## Visão Geral
Casos de teste para a classe `MensagemController`, verificando endpoints REST para operações de mensagem.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito, MockMvc

## Cenários de Teste

### 1. Buscar por ID
**Dado**: Um MensagemController
**Quando**: GET /api/mensagem/{id} é chamado
**Então**: Deve retornar mensagem

#### 1.1 Busca bem-sucedida
- **Dado**: Um ID de mensagem válido
- **Quando**: GET /api/mensagem/{id} é chamado
- **Então**: Deve retornar status 200 com a mensagem
- **Casos de Borda**: Nenhum

#### 1.2 Não encontrado
- **Dado**: Um ID de mensagem inválido
- **Quando**: GET /api/mensagem/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: ID nulo, ID negativo

### 2. Listar Todos
**Dado**: Um MensagemController
**Quando**: GET /api/mensagem é chamado
**Então**: Deve retornar lista de mensagens

#### 2.1 Listagem bem-sucedida
- **Dado**: Mensagens existem
- **Quando**: GET /api/mensagem é chamado
- **Então**: Deve retornar status 200 com lista de mensagens
- **Casos de Borda**: Lista vazia

#### 2.2 Com paginação
- **Dado**: Mensagens com parâmetros de paginação
- **Quando**: GET /api/mensagem?page=0&size=10 é chamado
- **Então**: Deve retornar status 200 com resultados paginados
- **Casos de Borda**: Parâmetros de página inválidos

### 3. Criar
**Dado**: Um MensagemController
**Quando**: POST /api/mensagem é chamado
**Então**: Deve criar nova mensagem

#### 3.1 Criação bem-sucedida
- **Dado**: Uma mensagem válida
- **Quando**: POST /api/mensagem é chamado
- **Então**: Deve retornar status 201 com a mensagem criada
- **Casos de Borda**: Nenhum

#### 3.2 Falha de validação
- **Dado**: Uma mensagem inválida
- **Quando**: POST /api/mensagem é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Mensagem nula, campos obrigatórios faltando

### 4. Atualizar
**Dado**: Um MensagemController
**Quando**: PUT /api/mensagem/{id} é chamado
**Então**: Deve atualizar mensagem

#### 4.1 Atualização bem-sucedida
- **Dado**: Uma mensagem válida
- **Quando**: PUT /api/mensagem/{id} é chamado
- **Então**: Deve retornar status 200 com a mensagem atualizada
- **Casos de Borda**: Nenhum

#### 4.2 Não encontrado
- **Dado**: Uma mensagem com ID inválido
- **Quando**: PUT /api/mensagem/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: Nenhum

### 5. Deletar
**Dado**: Um MensagemController
**Quando**: DELETE /api/mensagem/{id} é chamado
**Então**: Deve deletar mensagem

#### 5.1 Deleção bem-sucedida
- **Dado**: Um ID de mensagem válido
- **Quando**: DELETE /api/mensagem/{id} é chamado
- **Então**: Deve retornar status 204
- **Casos de Borda**: Nenhum

#### 5.2 Não encontrado
- **Dado**: Um ID de mensagem inválido
- **Quando**: DELETE /api/mensagem/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: Nenhum

### 6. Enviar Mensagem
**Dado**: Um MensagemController
**Quando**: POST /api/mensagem/enviar é chamado
**Então**: Deve enviar mensagem

#### 6.1 Envio bem-sucedido
- **Dado**: Uma mensagem válida para envio
- **Quando**: POST /api/mensagem/enviar é chamado
- **Então**: Deve retornar status 200
- **Casos de Borda**: Nenhum

#### 6.2 Falha de validação
- **Dado**: Uma mensagem inválida para envio
- **Quando**: POST /api/mensagem/enviar é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Mensagem nula, campos obrigatórios faltando

## Fluxo do Teste
1. Configurar MockMvc com MensagemController
2. Testar buscar por ID (sucesso, não encontrado)
3. Testar listar todos (sucesso, paginação)
4. Testar criar (sucesso, falha de validação)
5. Testar atualizar (sucesso, não encontrado)
6. Testar deletar (sucesso, não encontrado)
7. Testar enviar mensagem (sucesso, falha de validação)
8. Verificar casos de borda (valores nulos, parâmetros inválidos)
