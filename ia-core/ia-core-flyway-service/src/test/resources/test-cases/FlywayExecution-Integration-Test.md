# Flyway Execution Integration Test

Este teste de integração verifica a correta aplicação das migrações Flyway no banco de dados.

## Pré-requisitos
- Banco de dados HSQLDB configurado via `application-flyway.yml`.
- Schema inicial vazio.

## Passos do teste
1. Iniciar o contexto Spring Boot com a configuração de Flyway.
2. Verificar que as migrações foram aplicadas com sucesso.
3. Consultar a tabela `flyway_schema_history` para garantir que todas as versões esperadas estão presentes.
4. Executar um método de serviço que depende das tabelas criadas pelas migrações.
5. Assegurar que a operação retorna o resultado esperado.

## Critérios de sucesso
- Todas as migrações listadas em `src/main/resources/db/migrations` são aplicadas sem erros.
- O serviço de execução do Flyway (`FlywayExecutionService`) consegue recuperar as informações de execução.
