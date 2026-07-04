# EnvioMensagemRequestDTO - Casos de Teste da Camada de API

## Visão Geral
Casos de teste para a classe `EnvioMensagemRequestDTO`, verificando campos de requisição de envio de mensagem.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de EnvioMensagemRequestDTO
**Quando**: O DTO é instanciado
**Então**: Deve inicializar com valores padrão

#### 1.1 Construtor padrão
- **Dado**: Sem parâmetros
- **Quando**: new EnvioMensagemRequestDTO() é chamado
- **Então**: Deve criar instância com valores padrão dos campos
- **Casos de Borda**: Nenhum

#### 1.2 Construtor com builder
- **Dado**: Parâmetros do builder
- **Quando**: Builder é utilizado
- **Então**: Deve criar instância com valores fornecidos
- **Casos de Borda**: Parâmetros nulos

### 2. Validação de Campos Obrigatórios
**Dado**: Uma EnvioMensagemRequestDTO
**Quando**: Campos obrigatórios são validados
**Então**: Deve validar restrições dos campos

#### 2.1 Campos obrigatórios presentes
- **Dado**: Uma EnvioMensagemRequestDTO com campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve passar na validação
- **Casos de Borda**: Nenhum

#### 2.2 Campos obrigatórios ausentes
- **Dado**: Uma EnvioMensagemRequestDTO sem campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Destinatário nulo, conteúdo nulo

### 3. Verificação de Campos de Destinatário
**Dado**: Uma EnvioMensagemRequestDTO
**Quando**: Campos de destinatário são acessados
**Então**: Deve retornar valores corretos

#### 3.1 Destinatário válido
- **Dado**: Uma EnvioMensagemRequestDTO com destinatário válido
- **Quando**: Destinatário é acessado
- **Então**: Deve retornar o destinatário
- **Casos de Borda**: Nenhum

#### 3.2 Destinatário inválido
- **Dado**: Uma EnvioMensagemRequestDTO com destinatário inválido
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Destinatário nulo, formato inválido

### 4. Verificação de Campos de Conteúdo
**Dado**: Uma EnvioMensagemRequestDTO
**Quando**: Campos de conteúdo são acessados
**Então**: Deve retornar valores corretos

#### 4.1 Conteúdo válido
- **Dado**: Uma EnvioMensagemRequestDTO com conteúdo válido
- **Quando**: Conteúdo é acessado
- **Então**: Deve retornar o conteúdo
- **Casos de Borda**: Nenhum

#### 4.2 Conteúdo inválido
- **Dado**: Uma EnvioMensagemRequestDTO com conteúdo inválido
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Conteúdo nulo, conteúdo vazio

### 5. Verificação de Atributo CAMPOS
**Dado**: Uma EnvioMensagemRequestDTO
**Quando**: Atributo CAMPOS é verificado
**Então**: Deve conter todos os nomes de campos

#### 5.1 Verificação de CAMPOS
- **Dado**: Uma EnvioMensagemRequestDTO
- **Quando**: CAMPOS é acessado
- **Então**: Deve retornar array com nomes de campos
- **Casos de Borda**: Nenhum

#### 5.2 Busca de campo por nome
- **Dado**: Uma EnvioMensagemRequestDTO
- **Quando**: Campo é buscado por nome
- **Então**: Deve retornar índice do campo
- **Casos de Borda**: Campo não encontrado

## Fluxo do Teste
1. Criar instância de EnvioMensagemRequestDTO
2. Testar inicialização do construtor (padrão, builder)
3. Testar validação de campos obrigatórios (presentes, ausentes)
4. Testar verificação de campos de destinatário (válido, inválido)
5. Testar verificação de campos de conteúdo (válido, inválido)
6. Testar verificação de atributo CAMPOS
7. Verificar casos de borda (valores nulos, formatos inválidos)
