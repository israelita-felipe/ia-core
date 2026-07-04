# ModeloMensagemController - Casos de Teste da Camada de API

## Visão Geral
Casos de teste para a classe `ModeloMensagemController`, verificando endpoints REST para operações de modelo mensagem.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito, MockMvc

## Cenários de Teste

### 1. Buscar por ID
**Dado**: Um ModeloMensagemController
**Quando**: GET /api/modelo-mensagem/{id} é chamado
**Então**: Deve retornar modelo mensagem

#### 1.1 Busca bem-sucedida
- **Dado**: Um ID de modelo mensagem válido
- **Quando**: GET /api/modelo-mensagem/{id} é chamado
- **Então**: Deve retornar status 200 com o modelo mensagem
- **Casos de Borda**: Nenhum

#### 1.2 Não encontrado
- **Dado**: Um ID de modelo mensagem inválido
- **Quando**: GET /api/modelo-mensagem/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: ID nulo, ID negativo

### 2. Listar Todos
**Dado**: Um ModeloMensagemController
**Quando**: GET /api/modelo-mensagem é chamado
**Então**: Deve retornar lista de modelo mensagens

#### 2.1 Listagem bem-sucedida
- **Dado**: Modelo mensagens existem
- **Quando**: GET /api/modelo-mensagem é chamado
- **Então**: Deve retornar status 200 com lista de modelo mensagens
- **Casos de Borda**: Lista vazia

#### 2.2 Com paginação
- **Dado**: Modelo mensagens com parâmetros de paginação
- **Quando**: GET /api/modelo-mensagem?page=0&size=10 é chamado
- **Então**: Deve retornar status 200 com resultados paginados
- **Casos de Borda**: Parâmetros de página inválidos

### 3. Criar
**Dado**: Um ModeloMensagemController
**Quando**: POST /api/modelo-mensagem é chamado
**Então**: Deve criar novo modelo mensagem

#### 3.1 Criação bem-sucedida
- **Dado**: Um modelo mensagem válido
- **Quando**: POST /api/modelo-mensagem é chamado
- **Então**: Deve retornar status 201 com o modelo mensagem criado
- **Casos de Borda**: Nenhum

#### 3.2 Falha de validação
- **Dado**: Um modelo mensagem inválido
- **Quando**: POST /api/modelo-mensagem é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Modelo nulo, campos obrigatórios faltando

### 4. Atualizar
**Dado**: Um ModeloMensagemController
**Quando**: PUT /api/modelo-mensagem/{id} é chamado
**Então**: Deve atualizar modelo mensagem

#### 4.1 Atualização bem-sucedida
- **Dado**: Um modelo mensagem válido
- **Quando**: PUT /api/modelo-mensagem/{id} é chamado
- **Então**: Deve retornar status 200 com o modelo mensagem atualizado
- **Casos de Borda**: Nenhum

#### 4.2 Não encontrado
- **Dado**: Um modelo mensagem com ID inválido
- **Quando**: PUT /api/modelo-mensagem/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: Nenhum

### 5. Deletar
**Dado**: Um ModeloMensagemController
**Quando**: DELETE /api/modelo-mensagem/{id} é chamado
**Então**: Deve deletar modelo mensagem

#### 5.1 Deleção bem-sucedida
- **Dado**: Um ID de modelo mensagem válido
- **Quando**: DELETE /api/modelo-mensagem/{id} é chamado
- **Então**: Deve retornar status 204
- **Casos de Borda**: Nenhum

#### 5.2 Não encontrado
- **Dado**: Um ID de modelo mensagem inválido
- **Quando**: DELETE /api/modelo-mensagem/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: Nenhum

### 6. Processar Variáveis
**Dado**: Um ModeloMensagemController
**Quando**: POST /api/modelo-mensagem/processar é chamado
**Então**: Deve processar variáveis no template

#### 6.1 Processamento bem-sucedido
- **Dado**: Um modelo mensagem com variáveis válidas
- **Quando**: POST /api/modelo-mensagem/processar é chamado
- **Então**: Deve retornar status 200 com template processado
- **Casos de Borda**: Variáveis inexistentes

#### 6.2 Falha de validação
- **Dado**: Um modelo mensagem com variáveis inválidas
- **Quando**: POST /api/modelo-mensagem/processar é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Modelo nulo, variáveis nulas

## Fluxo do Teste
1. Configurar MockMvc com ModeloMensagemController
2. Testar buscar por ID (sucesso, não encontrado)
3. Testar listar todos (sucesso, paginação)
4. Testar criar (sucesso, falha de validação)
5. Testar atualizar (sucesso, não encontrado)
6. Testar deletar (sucesso, não encontrado)
7. Testar processar variáveis (sucesso, falha de validação)
8. Verificar casos de borda (valores nulos, parâmetros inválidos)
