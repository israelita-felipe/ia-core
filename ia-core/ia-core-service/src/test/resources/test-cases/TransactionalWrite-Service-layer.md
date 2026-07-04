# Caso de Teste: TransactionalWrite

## Descrição
Testa a anotação TransactionalWrite que configura transações para operações de escrita.

## Classe Testada
`com.ia.core.service.annotations.TransactionalWrite`

## Fluxo do Teste
1. Testar metadados da anotação
2. Testar configuração do @Transactional
3. Testar valores padrão

## Cenários

### Cenário 1: Verificar Target METHOD
- **Dado**: A anotação TransactionalWrite
- **Quando**: Verificar @Target
- **Então**: Deve ser METHOD
- **E**: Deve ser aplicável apenas a métodos

### Cenário 2: Verificar Retention RUNTIME
- **Dado**: A anotação TransactionalWrite
- **Quando**: Verificar @Retention
- **Então**: Deve ser RUNTIME
- **E**: Deve estar disponível em tempo de execução

### Cenário 3: Verificar @Transactional readOnly false
- **Dado**: A anotação TransactionalWrite
- **Quando**: Verificar @Transactional
- **Então**: readOnly deve ser false
- **E**: Deve permitir modificações no banco

### Cenário 4: Verificar @Transactional propagation REQUIRED
- **Dado**: A anotação TransactionalWrite
- **Quando**: Verificar @Transactional
- **Então**: propagation deve ser REQUIRED
- **E**: Deve usar transação existente ou criar nova

### Cenário 5: Verificar @Transactional isolation DEFAULT
- **Dado**: A anotação TransactionalWrite
- **Quando**: Verificar @Transactional
- **Então**: isolation deve ser DEFAULT
- **E**: Deve deixar banco de dados decidir

### Cenário 6: Verificar @Transactional timeout 30
- **Dado**: A anotação TransactionalWrite
- **Quando**: Verificar @Transactional
- **Então**: timeout deve ser 30
- **E**: Deve proteger contra operações muito longas
