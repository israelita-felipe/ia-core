# ContatoMensagem - Casos de Teste da Camada de Modelo

## Visão Geral
Casos de teste para a entidade `ContatoMensagem`, verificando relacionamentos e campos de contato/mensagem.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseUnitTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de ContatoMensagem
**Quando**: A entidade é instanciada
**Então**: Deve inicializar com valores padrão

#### 1.1 Construtor padrão
- **Dado**: Sem parâmetros
- **Quando**: new ContatoMensagem() é chamado
- **Então**: Deve criar instância com valores padrão dos campos
- **Casos de Borda**: Nenhum

#### 1.2 Construtor com contato e mensagem
- **Dado**: Entidades Contact e Message
- **Quando**: new ContatoMensagem(contact, message) é chamado
- **Então**: Deve inicializar com entidades fornecidas
- **Casos de Borda**: Contact ou Message nulos

### 2. Verificação de Relacionamentos
**Dado**: Uma instância de ContatoMensagem
**Quando**: Relacionamentos são definidos
**Então**: Deve manter relacionamentos corretos

#### 2.1 Relacionamento com Contact
- **Dado**: Uma ContatoMensagem com um Contact válido
- **Quando**: Contact é acessado
- **Então**: Deve retornar o Contact associado
- **Casos de Borda**: Contact nulo

#### 2.2 Relacionamento com Message
- **Dado**: Uma ContatoMensagem com uma Message válida
- **Quando**: Message é acessada
- **Então**: Deve retornar a Message associada
- **Casos de Borda**: Message nula

#### 2.3 Relacionamentos bidirecionais
- **Dado**: Uma ContatoMensagem vinculada a Contact e Message
- **Quando**: Relacionamentos são verificados
- **Então**: Deve manter consistência bidirecional
- **Casos de Borda**: Referências circulares

### 3. Validação de Campos
**Dado**: Uma instância de ContatoMensagem
**Quando**: Campos obrigatórios são definidos
**Então**: Deve validar restrições dos campos

#### 3.1 Campos obrigatórios
- **Dado**: Uma ContatoMensagem sem campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Nenhum

#### 3.2 Campos opcionais
- **Dado**: Uma ContatoMensagem sem campos opcionais
- **Quando**: Campos opcionais são acessados
- **Então**: Deve retornar nulo ou valores padrão
- **Casos de Borda**: Nenhum

## Fluxo do Teste
1. Criar instância de ContatoMensagem
2. Testar inicialização do construtor
3. Testar verificação de relacionamentos (contact, message, bidirecional)
4. Testar validação de campos
5. Verificar casos de borda (valores nulos, referências circulares)
