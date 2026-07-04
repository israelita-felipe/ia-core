# Casos de Teste - FlywayExecution

## Classe
`com.ia.core.flyway.model.FlywayExecution`

## Tipo de Teste
Model Layer - Testes Unitários de Entidade

## Descrição
A classe `FlywayExecution` é uma entidade JPA que mapeia a tabela `flyway_schema_history` do Flyway. Esta entidade é de leitura apenas, pois os dados são gerenciados pelo Flyway.

## Casos de Teste

### CT001 - Criar entidade com builder
**Descrição**: Verificar se é possível criar uma instância de FlywayExecution usando o SuperBuilder do Lombok.
**Pré-condições**: Nenhuma
**Passos**:
1. Criar instância usando FlywayExecution.builder()
2. Preencher campos obrigatórios
3. Chamar build()
**Resultado esperado**: Instância criada com sucesso

### CT002 - Verificar campos da entidade
**Descrição**: Verificar se todos os campos da entidade estão corretamente definidos.
**Pré-condições**: Instância criada
**Passos**:
1. Criar instância com todos os campos
2. Verificar cada campo usando getters
**Resultado esperado**: Todos os campos retornam valores corretos

### CT003 - Verificar equals e hashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente (gerados pelo Lombok).
**Pré-condições**: Duas instâncias com mesmo ID
**Passos**:
1. Criar duas instâncias com mesmo ID
2. Verificar equals
3. Verificar hashCode
**Resultado esperado**: equals retorna true, hashCode são iguais

### CT004 - Verificar toBuilder
**Descrição**: Verificar se o método toBuilder funciona corretamente.
**Pré-condições**: Instância criada
**Passos**:
1. Criar instância
2. Usar toBuilder() para modificar
3. Chamar build()
**Resultado esperado**: Nova instância criada com modificações

### CT005 - Verificar toString
**Descrição**: Verificar se toString funciona corretamente.
**Pré-condições**: Instância criada
**Passos**:
1. Criar instância
2. Chamar toString()
**Resultado esperado**: String representativa da entidade
