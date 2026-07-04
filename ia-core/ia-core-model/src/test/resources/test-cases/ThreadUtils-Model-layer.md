# Caso de Teste: ThreadUtils

## Descrição
Testa a classe ThreadUtils que fornece utilitários para manipulação de threads.

## Classe Testada
`com.ia.core.model.util.ThreadUtils`

## Fluxo do Teste
1. Testar obtenção de nome da thread
2. Testar sleep da thread
3. Testar verificação de thread atual
4. Testar manipulação de thread pool

## Cenários

### Cenário 1: Obter nome da thread atual
- **Dado**: A thread atual
- **Quando**: Chamar método para obter nome
- **Então**: Deve retornar nome da thread
- **E**: Nome não deve ser nulo

### Cenário 2: Obter ID da thread atual
- **Dado**: A thread atual
- **Quando**: Chamar método para obter ID
- **Então**: Deve retornar ID da thread
- **E**: ID deve ser positivo

### Cenário 3: Dormir thread por tempo especificado
- **Dado**: Um tempo em milissegundos
- **Quando**: Chamar método de sleep
- **Então**: Thread deve dormir pelo tempo especificado
- **E**: Não deve lançar InterruptedException

### Cenário 4: Verificar se thread é daemon
- **Dado**: A thread atual
- **Quando**: Chamar método para verificar se é daemon
- **Então**: Deve retornar boolean correto

### Cenário 5: Obter stack trace da thread atual
- **Dado**: A thread atual
- **Quando**: Chamar método para obter stack trace
- **Então**: Deve retornar array de StackTraceElement
- **E**: Array não deve ser vazio
