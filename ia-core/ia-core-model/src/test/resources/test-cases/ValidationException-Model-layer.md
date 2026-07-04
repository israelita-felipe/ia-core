# Caso de Teste: ValidationException

## Descrição
Testa a classe ValidationException que representa erros de validação de dados de entrada.

## Classe Testada
`com.ia.core.model.exception.ValidationException`

## Fluxo do Teste
1. Criar exceção com mensagem
2. Criar exceção com campo, valor rejeitado e mensagem
3. Verificar propriedades da exceção
4. Lançar exceção e validar comportamento

## Cenários

### Cenário 1: Criar exceção com mensagem
- **Dado**: Uma mensagem de erro
- **Quando**: Criar ValidationException com a mensagem
- **Então**: Deve usar código de erro padrão "VALIDATION_ERROR"
- **E**: Deve armazenar a mensagem fornecida
- **E**: Deve ter campo como null
- **E**: Deve ter valor rejeitado como null

### Cenário 2: Criar exceção com campo, valor rejeitado e mensagem
- **Dado**: Um campo "email", um valor rejeitado "invalido" e uma mensagem
- **Quando**: Criar ValidationException com campo, valor e mensagem
- **Então**: Deve usar código de erro padrão "VALIDATION_ERROR"
- **E**: Deve armazenar a mensagem fornecida
- **E**: Deve armazenar o nome do campo
- **E**: Deve armazenar o valor rejeitado

### Cenário 3: Verificar getters de campo e valor rejeitado
- **Dado**: Uma exceção criada com campo e valor rejeitado
- **Quando**: Chamar getField() e getRejectedValue()
- **Então**: Deve retornar o nome do campo
- **E**: Deve retornar o valor rejeitado

### Cenário 4: Lançar exceção e validar comportamento
- **Dado**: Um campo "cpf", um valor rejeitado "123" e uma mensagem
- **Quando**: Lançar ValidationException
- **Então**: Deve ser instância de ValidationException
- **E**: Deve ter a mensagem fornecida
- **E**: Deve ter código de erro padrão
