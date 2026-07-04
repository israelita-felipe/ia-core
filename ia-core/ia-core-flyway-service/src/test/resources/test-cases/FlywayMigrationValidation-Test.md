# Flyway Migration Validation Test

Este teste valida a correta validação de migrações Flyway antes da aplicação.

## Passos do teste
1. Iniciar o contexto Spring Boot com configuração de Flyway.
2. Verificar que as migrações inválidas (com nomes não compatíveis) não são executadas.
3. Confirmar que o validador de nomes de migrações do Flyway identifica corretamente as migrações inválidas.

## Validações Esperadas
- Não há erros de validação de nomes de migrações.
- O log deve conter mensagens sobre migrações não executadas devido a nomes inválidos.
