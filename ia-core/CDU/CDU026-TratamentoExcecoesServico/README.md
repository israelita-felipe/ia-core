# CDU - Tratamento de Exceções de Serviço

## 1. Metadados
- **Nome do CDU**: Tratamento de Exceções de Serviço
- **Versão**: 1.0
- **Data**: 2026-06-18
- **Autor**: IA Core
- **Status**: Em Revisão

## 2. Descrição do Caso de Uso

### 2.1. Descrição Breve
O caso de uso "Tratamento de Exceções de Serviço" define o padrão para tratamento de erros na camada de serviço, permitindo agregação múltipla de erros, mensagens customizadas e retrocompatibilidade com código existente através da classe ServiceException.

### 2.2. Objetivos
- Fornecer exceção específica para camada de serviço
- Permitir agregação de múltiplos erros em uma única exceção
- Suportar mensagens de erro customizadas
- Manter retrocompatibilidade com código existente
- Fornecer métodos para verificar e recuperar erros agregados

### 2.3. Escopo
**Incluído**:
- Classe ServiceException estendendo DomainException
- Agregação de múltiplas exceções
- Adição de erros como String ou Exception
- Recuperação de erros como Stream de mensagens
- Verificação de existência de erros

**Excluído**:
- Validação de campos (deve usar Jakarta Validation)
- Tratamento de exceções de outras camadas (REST, View)
- Exceções específicas de domínio (cada módulo define suas próprias)

## 3. Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Desenvolvedor | Desenvolvedor que lança e trata ServiceException | Primário |
| Sistema | Sistema que gerencia exceções de serviço | Sistema |
| Usuário Final | Usuário que recebe mensagens de erro | Secundário |

## 4. Pré-condições

### 4.1. Para Lançar ServiceException
- Erro de serviço deve ter ocorrido
- Mensagem de erro ou código de erro deve estar disponível

### 4.2. Para Adicionar Erros
- Instância de ServiceException deve existir
- Erro a ser adicionado deve estar disponível

## 5. Pós-condições

### 5.1. Após Lançar ServiceException
- Exceção deve ter código de erro e mensagem
- Causa original deve ser preservada quando fornecida
- Erros agregados devem estar disponíveis

### 5.2. Após Adicionar Erros
- Erros devem ser agregados na coleção interna
- Causa deve ser atualizada no primeiro erro adicionado

## 6. Fluxo Principal

### 6.1. Lançamento de ServiceException Básico
**Given**: Erro de serviço ocorre sem detalhes específicos
**When**: ServiceException é lançada com construtor padrão
**Then**: Exceção tem código padrão "SERVICE_ERROR" e mensagem "Erro de serviço"

### 6.2. Lançamento com Mensagem de Erro
**Given**: Erro de serviço ocorre com mensagem específica
**When**: ServiceException é lançada com mensagem de erro
**Then**: Exceção tem código padrão e mensagem customizada, erro é adicionado

### 6.3. Lançamento com Código e Mensagem
**Given**: Erro de serviço ocorre com código e mensagem específicos
**When**: ServiceException é lançada com código e mensagem
**Then**: Exceção tem código e mensagem customizados

### 6.4. Lançamento com Causa
**Given**: Erro de serviço ocorre devido a outra exceção
**When**: ServiceException é lançada com código, mensagem e causa
**Then**: Exceção tem código, mensagem e causa original

### 6.5. Adição de Erros
**Given**: Instância de ServiceException existe
**When**: Método add(Exception) ou add(String) é chamado
**Then**: Erro é adicionado à coleção, causa é atualizada no primeiro erro

### 6.6. Recuperação de Erros
**Given**: Instância de ServiceException com erros agregados
**When**: Método getErrors() é chamado
**Then**: Stream de mensagens de erro é retornado

### 6.7. Verificação de Erros
**Given**: Instância de ServiceException
**When**: Método hasErros() é chamado
**Then**: true é retornado se há erros, false caso contrário

## 7. Fluxos Alternativos

### 7.1. Agregação de Múltiplos Erros
**Given**: Múltiplos erros ocorrem durante operação
**When**: Método add() é chamado múltiplas vezes
**Then**: Todos os erros são agregados na coleção

### 7.2. Recuperação de Mensagem Concatenada
**Given**: Instância de ServiceException com múltiplos erros
**When**: Método getMessage() é chamado
**Then**: Mensagens são concatenadas e retornadas (ou null se vazio)

## 8. Fluxos de Exceção

### 8.1. ServiceException sem Erros
**Given**: Instância de ServiceException criada com construtor padrão
**When**: Método hasErros() é chamado
**Then**: false é retornado

### 8.2. getMessage() sem Erros
**Given**: Instância de ServiceException sem erros agregados
**When**: Método getMessage() é chamado
**Then**: null é retornado

## 9. Regras de Negócio

| ID | Regra | Descrição |
|----|-------|-----------|
| RN001 | CodigoPadrao | Construtor padrão usa código "SERVICE_ERROR" |
| RN002 | CausaPrimeiroErro | Causa é atualizada apenas no primeiro erro adicionado |
| RN003 | AgregacaoErros | Erros são mantidos em HashSet para evitar duplicatas |
| RN004 | MensagemConcatenada | getMessage() concatena todas as mensagens de erro |
| RN005 | Retrocompatibilidade | Mantida para código existente que usa ServiceException |

## 10. Estrutura de Dados

### 10.1. ServiceException
```java
public class ServiceException extends DomainException {
    private static final long serialVersionUID = -7117586372811635337L;
    private static final String DEFAULT_ERROR_CODE = "SERVICE_ERROR";
    private Collection<Exception> erros = new HashSet<>();

    public ServiceException() {
        super(DEFAULT_ERROR_CODE, "Erro de serviço");
    }

    public ServiceException(String error) {
        this();
        add(error);
    }

    public ServiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public void add(Exception ex) {
        if (!hasErros()) {
            initCause(ex);
        }
        this.erros.add(ex);
    }

    public void add(String error) {
        this.erros.add(new Exception(error));
    }

    public Stream<String> getErrors() {
        return this.erros.parallelStream().map(Exception::getMessage);
    }

    @Override
    public String getMessage() {
        return getErrors().reduce(String::join).orElse(null);
    }

    public boolean hasErros() {
        return !erros.isEmpty();
    }
}
```

## 11. Contratos de Interface

### 11.1. Métodos de Construção
- **ServiceException()**: Construtor padrão com código e mensagem padrão
- **ServiceException(String)**: Construtor com mensagem de erro
- **ServiceException(String, String)**: Construtor com código e mensagem customizados
- **ServiceException(String, String, Throwable)**: Construtor com código, mensagem e causa

### 11.2. Métodos de Agregação
- **add(Exception)**: Adiciona exceção à coleção de erros
- **add(String)**: Adiciona mensagem de erro como Exception

### 11.3. Métodos de Recuperação
- **getErrors()**: Retorna Stream de mensagens de erro
- **getMessage()**: Retorna mensagens concatenadas (override de Throwable)
- **hasErros()**: Retorna true se há erros agregados

## 12. Requisitos Especiais

### 12.1. Thread Safety
- Coleção de erros usa HashSet (não thread-safe)
- Em ambientes concorrentes, usar sincronização externa

### 12.2. Serialização
- serialVersionUID definido para compatibilidade de serialização
- Coleção de erros é serializável

### 12.3. Performance
- Uso de parallelStream() em getErrors() para processamento paralelo
- HashSet para O(1) na verificação de existência de erros

## 13. Pontos de Extensão

### 13.1. Exceções Específicas de Serviço
- Módulos podem criar subclasses de ServiceException
- Subclasses podem adicionar campos específicos do domínio

### 13.2. Validadores Personalizados
- ServiceException pode ser usada em validadores de serviço
- Erros de validação podem ser agregados

## 14. Referências

- [ADR-011: Padrões de Tratamento de Exceções](/home/israel/git/ia-core-apps/ia-core/ADR/011-exception-handling-patterns.md)
- [ADR-012: Testing Patterns](/home/israel/git/ia-core-apps/ia-core/ADR/012-testing-patterns.md)
