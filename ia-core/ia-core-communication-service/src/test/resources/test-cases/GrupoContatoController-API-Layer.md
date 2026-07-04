# GrupoContatoController - Casos de Teste da Camada de API

## Visão Geral
Casos de teste para a classe `GrupoContatoController`, verificando endpoints REST para operações de grupo contato.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito, MockMvc

## Cenários de Teste

### 1. Buscar por ID
**Dado**: Um GrupoContatoController
**Quando**: GET /api/grupo-contato/{id} é chamado
**Então**: Deve retornar grupo contato

#### 1.1 Busca bem-sucedida
- **Dado**: Um ID de grupo contato válido
- **Quando**: GET /api/grupo-contato/{id} é chamado
- **Então**: Deve retornar status 200 com o grupo contato
- **Casos de Borda**: Nenhum

#### 1.2 Não encontrado
- **Dado**: Um ID de grupo contato inválido
- **Quando**: GET /api/grupo-contato/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: ID nulo, ID negativo

### 2. Listar Todos
**Dado**: Um GrupoContatoController
**Quando**: GET /api/grupo-contato é chamado
**Então**: Deve retornar lista de grupo contatos

#### 2.1 Listagem bem-sucedida
- **Dado**: Grupo contatos existem
- **Quando**: GET /api/grupo-contato é chamado
- **Então**: Deve retornar status 200 com lista de grupo contatos
- **Casos de Borda**: Lista vazia

#### 2.2 Com paginação
- **Dado**: Grupo contatos com parâmetros de paginação
- **Quando**: GET /api/grupo-contato?page=0&size=10 é chamado
- **Então**: Deve retornar status 200 com resultados paginados
- **Casos de Borda**: Parâmetros de página inválidos

### 3. Criar
**Dado**: Um GrupoContatoController
**Quando**: POST /api/grupo-contato é chamado
**Então**: Deve criar novo grupo contato

#### 3.1 Criação bem-sucedida
- **Dado**: Um grupo contato válido
- **Quando**: POST /api/grupo-contato é chamado
- **Então**: Deve retornar status 201 com o grupo contato criado
- **Casos de Borda**: Nenhum

#### 3.2 Falha de validação
- **Dado**: Um grupo contato inválido
- **Quando**: POST /api/grupo-contato é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Grupo nulo, campos obrigatórios faltando

### 4. Atualizar
**Dado**: Um GrupoContatoController
**Quando**: PUT /api/grupo-contato/{id} é chamado
**Então**: Deve atualizar grupo contato

#### 4.1 Atualização bem-sucedida
- **Dado**: Um grupo contato válido
- **Quando**: PUT /api/grupo-contato/{id} é chamado
- **Então**: Deve retornar status 200 com o grupo contato atualizado
- **Casos de Borda**: Nenhum

#### 4.2 Não encontrado
- **Dado**: Um grupo contato com ID inválido
- **Quando**: PUT /api/grupo-contato/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: Nenhum

### 5. Deletar
**Dado**: Um GrupoContatoController
**Quando**: DELETE /api/grupo-contato/{id} é chamado
**Então**: Deve deletar grupo contato

#### 5.1 Deleção bem-sucedida
- **Dado**: Um ID de grupo contato válido
- **Quando**: DELETE /api/grupo-contato/{id} é chamado
- **Então**: Deve retornar status 204
- **Casos de Borda**: Nenhum

#### 5.2 Não encontrado
- **Dado**: Um ID de grupo contato inválido
- **Quando**: DELETE /api/grupo-contato/{id} é chamado
- **Então**: Deve retornar status 404
- **Casos de Borda**: Nenhum

### 6. Adicionar Contato ao Grupo
**Dado**: Um GrupoContatoController
**Quando**: POST /api/grupo-contato/{id}/contatos é chamado
**Então**: Deve adicionar contato ao grupo

#### 6.1 Adição bem-sucedida
- **Dado**: Um grupo contato e um contato válido
- **Quando**: POST /api/grupo-contato/{id}/contatos é chamado
- **Então**: Deve retornar status 200
- **Casos de Borda**: Contato já no grupo

#### 6.2 Falha de validação
- **Dado**: Um grupo contato ou contato inválido
- **Quando**: POST /api/grupo-contato/{id}/contatos é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Grupo nulo, contato nulo

### 7. Remover Contato do Grupo
**Dado**: Um GrupoContatoController
**Quando**: DELETE /api/grupo-contato/{id}/contatos/{contatoId} é chamado
**Então**: Deve remover contato do grupo

#### 7.1 Remoção bem-sucedida
- **Dado**: Um grupo contato com um contato
- **Quando**: DELETE /api/grupo-contato/{id}/contatos/{contatoId} é chamado
- **Então**: Deve retornar status 200
- **Casos de Borda**: Contato não no grupo

#### 7.2 Falha de validação
- **Dado**: Um grupo contato ou contato inválido
- **Quando**: DELETE /api/grupo-contato/{id}/contatos/{contatoId} é chamado
- **Então**: Deve retornar status 400
- **Casos de Borda**: Grupo nulo, contato nulo

## Fluxo do Teste
1. Configurar MockMvc com GrupoContatoController
2. Testar buscar por ID (sucesso, não encontrado)
3. Testar listar todos (sucesso, paginação)
4. Testar criar (sucesso, falha de validação)
5. Testar atualizar (sucesso, não encontrado)
6. Testar deletar (sucesso, não encontrado)
7. Testar adicionar contato ao grupo (sucesso, falha de validação)
8. Testar remover contato do grupo (sucesso, falha de validação)
9. Verificar casos de borda (valores nulos, parâmetros inválidos)
