# EnvioMensagemResponseDTO - Casos de Teste da Camada de API

## Visão Geral
Casos de teste para a classe `EnvioMensagemResponseDTO`, verificando campos de resposta de envio de mensagem.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de EnvioMensagemResponseDTO
**Quando**: O DTO é instanciado
**Então**: Deve inicializar com valores padrão

#### 1.1 Construtor padrão
- **Dado**: Sem parâmetros
- **Quando**: new EnvioMensagemResponseDTO() é chamado
- **Então**: Deve criar instância com valores padrão dos campos
- **Casos de Borda**: Nenhum

#### 1.2 Construtor com builder
- **Dado**: Parâmetros do builder
- **Quando**: Builder é utilizado
- **Então**: Deve criar instância com valores fornecidos
- **Casos de Borda**: Parâmetros nulos

### 2. Validação de Campos Obrigatórios
**Dado**: Uma EnvioMensagemResponseDTO
**Quando**: Campos obrigatórios são validados
**Então**: Deve validar restrições dos campos

#### 2.1 Campos obrigatórios presentes
- **Dado**: Uma EnvioMensagemResponseDTO com campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve passar na validação
- **Casos de Borda**: Nenhum

#### 2.2 Campos obrigatórios ausentes
- **Dado**: Uma EnvioMensagemResponseDTO sem campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Status nulo, mensagem nula

### 3. Verificação de Status de Envio
**Dado**: Uma EnvioMensagemResponseDTO
**Quando**: Status de envio é acessado
**Então**: Deve retornar valor correto

#### 3.1 Status de sucesso
- **Dado**: Uma EnvioMensagemResponseDTO com status de sucesso
- **Quando**: Status é acessado
- **Então**: Deve retornar status de sucesso
- **Casos de Borda**: Nenhum

#### 3.2 Status de falha
- **Dado**: Uma EnvioMensagemResponseDTO com status de falha
- **Quando**: Status é acessado
- **Então**: Deve retornar status de falha
- **Casos de Borda**: Status inválido

### 4. Verificação de Mensagem de Erro
**Dado**: Uma EnvioMensagemResponseDTO
**Quando**: Mensagem de erro é acessada
**Então**: Deve retornar valor correto

#### 4.1 Mensagem de erro presente
- **Dado**: Uma EnvioMensagemResponseDTO com mensagem de erro
- **Quando**: Mensagem de erro é acessada
- **Então**: Deve retornar a mensagem de erro
- **Casos de Borda**: Nenhum

#### 4.2 Mensagem de erro ausente
- **Dado**: Uma EnvioMensagemResponseDTO sem mensagem de erro
- **Quando**: Mensagem de erro é acessada
- **Então**: Deve retornar nulo ou string vazia
- **Casos de Borda**: Nenhum

### 5. Verificação de Atributo CAMPOS
**Dado**: Uma EnvioMensagemResponseDTO
**Quando**: Atributo CAMPOS é verificado
**Então**: Deve conter todos os nomes de campos

#### 5.1 Verificação de CAMPOS
- **Dado**: Uma EnvioMensagemResponseDTO
- **Quando**: CAMPOS é acessado
- **Então**: Deve retornar array com nomes de campos
- **Casos de Borda**: Nenhum

#### 5.2 Busca de campo por nome
- **Dado**: Uma EnvioMensagemResponseDTO
- **Quando**: Campo é buscado por nome
- **Então**: Deve retornar índice do campo
- **Casos de Borda**: Campo não encontrado

## Fluxo do Teste
1. Criar instância de EnvioMensagemResponseDTO
2. Testar inicialização do construtor (padrão, builder)
3. Testar validação de campos obrigatórios (presentes, ausentes)
4. Testar verificação de status de envio (sucesso, falha)
5. Testar verificação de mensagem de erro (presente, ausente)
6. Testar verificação de atributo CAMPOS
7. Verificar casos de borda (valores nulos, status inválido)
