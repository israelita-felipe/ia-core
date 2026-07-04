# Caso de Teste: ServiceException

## Descrição
Testa a classe ServiceException que é usada para retenção de erros de serviço.

## Classe Testada
`com.ia.core.service.exception.ServiceException`

## Fluxo do Teste
1. Testar construtores
2. Testar métodos de adição de erros
3. Testar método hasErros()
4. Testar método getErrors()
5. Testar método getMessage()

## Cenários

### Cenário 1: Criar ServiceException com construtor padrão
- **Dado**: Construtor padrão
- **Quando**: Criar ServiceException()
- **Então**: Deve criar exceção com código "SERVICE_ERROR"
- **E**: Deve criar exceção com mensagem "Erro de serviço"
- **E**: hasErros() deve retornar false

### Cenário 2: Criar ServiceException com erro
- **Dado**: Uma mensagem de erro
- **Quando**: Criar ServiceException(String error)
- **Então**: Deve adicionar o erro
- **E**: hasErros() deve retornar true
- **E**: getMessage() deve conter o erro

### Cenário 3: Criar ServiceException com código e mensagem
- **Dado**: Código de erro e mensagem
- **Quando**: Criar ServiceException(String errorCode, String message)
- **Então**: Deve criar exceção com código fornecido
- **E**: Deve criar exceção com mensagem fornecida

### Cenário 4: Criar ServiceException com código, mensagem e causa
- **Dado**: Código, mensagem e causa
- **Quando**: Criar ServiceException(String errorCode, String message, Throwable cause)
- **Então**: Deve criar exceção com código fornecido
- **E**: Deve criar exceção com mensagem fornecida
- **E**: Deve criar exceção com causa fornecida

### Cenário 5: Adicionar exceção
- **Dado**: Uma ServiceException
- **Quando**: Chamar add(Exception ex)
- **Então**: A exceção deve ser adicionada
- **E**: hasErros() deve retornar true
- **E**: A causa deve ser definida no primeiro erro

### Cenário 6: Adicionar mensagem de erro
- **Dado**: Uma ServiceException
- **Quando**: Chamar add(String error)
- **Então**: A mensagem deve ser adicionada como Exception
- **E**: hasErros() deve retornar true

### Cenário 7: Verificar hasErros() sem erros
- **Dado**: Uma ServiceException sem erros
- **Quando**: Chamar hasErros()
- **Então**: Deve retornar false

### Cenário 8: Verificar hasErros() com erros
- **Dado**: Uma ServiceException com erros
- **Quando**: Chamar hasErros()
- **Então**: Deve retornar true

### Cenário 9: Obter erros
- **Dado**: Uma ServiceException com múltiplos erros
- **Quando**: Chamar getErrors()
- **Então**: Deve retornar Stream de mensagens
- **E**: Deve conter todas as mensagens de erro

### Cenário 10: Obter mensagem com erros
- **Dado**: Uma ServiceException com erros
- **Quando**: Chamar getMessage()
- **Então**: Deve retornar as mensagens concatenadas
