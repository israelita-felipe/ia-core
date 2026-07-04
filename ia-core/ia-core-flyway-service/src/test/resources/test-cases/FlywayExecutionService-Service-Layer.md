# FlywayExecutionService - Casos de Teste da Camada de Service

## Visão Geral
Casos de teste para a classe `FlywayExecutionService`, verificando execução de migrações Flyway e gerenciamento de versões.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseServiceTest
- **Referências CDU**: N/A (domínio Flyway)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Aplicar Migrações Pendentes
**Dado**: Um FlywayExecutionService
**Quando**: Migrações pendentes são aplicadas
**Então**: Deve aplicar as migrações corretamente

#### 1.1 Aplicação bem-sucedida
- **Dado**: Migrações pendentes existem
- **Quando**: applyMigrations() é chamado
- **Então**: Deve aplicar migrações e registrar execuções
- **Casos de Borda**: Nenhum

#### 1.2 Falha na aplicação
- **Dado**: Erro durante aplicação de migração
- **Quando**: applyMigrations() é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Migração inválida, erro de conexão

### 2. Verificar Versão Atual
**Dado**: Um FlywayExecutionService
**Quando**: Versão atual é verificada
**Então**: Deve retornar versão correta

#### 2.1 Verificação bem-sucedida
- **Dado**: Migrações foram aplicadas
- **Quando**: getCurrentVersion() é chamado
- **Então**: Deve retornar versão atual
- **Casos de Borda**: Nenhuma migração aplicada

#### 2.2 Falha na verificação
- **Dado**: Erro ao acessar banco de dados
- **Quando**: getCurrentVersion() é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 3. Listar Migrações Aplicadas
**Dado**: Um FlywayExecutionService
**Quando**: Migrações aplicadas são listadas
**Então**: Deve retornar lista de migrações

#### 3.1 Listagem bem-sucedida
- **Dado**: Migrações foram aplicadas
- **Quando**: listAppliedMigrations() é chamado
- **Então**: Deve retornar lista de migrações aplicadas
- **Casos de Borda**: Lista vazia

#### 3.2 Falha na listagem
- **Dado**: Erro ao acessar banco de dados
- **Quando**: listAppliedMigrations() é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 4. Listar Migrações Pendentes
**Dado**: Um FlywayExecutionService
**Quando**: Migrações pendentes são listadas
**Então**: Deve retornar lista de migrações

#### 4.1 Listagem bem-sucedida
- **Dado**: Migrações pendentes existem
- **Quando**: listPendingMigrations() é chamado
- **Então**: Deve retornar lista de migrações pendentes
- **Casos de Borda**: Lista vazia

#### 4.2 Falha na listagem
- **Dado**: Erro ao acessar banco de dados
- **Quando**: listPendingMigrations() é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 5. Rollback de Migração
**Dado**: Um FlywayExecutionService
**Quando**: Rollback de migração é executado
**Então**: Deve reverter migração

#### 5.1 Rollback bem-sucedido
- **Dado**: Migração aplicada pode ser revertida
- **Quando**: rollbackMigration(version) é chamado
- **Então**: Deve reverter migração
- **Casos de Borda**: Versão inválida

#### 5.2 Falha no rollback
- **Dado**: Erro durante rollback
- **Quando**: rollbackMigration(version) é chamado
- **Então**: Deve tratar erro adequadamente
- **Casos de Borda**: Nenhum

### 6. Validação de Migrações
**Dado**: Um FlywayExecutionService
**Quando**: Migrações são validadas
**Então**: Deve validar integridade

#### 6.1 Validação bem-sucedida
- **Dado**: Migrações são válidas
- **Quando**: validateMigrations() é chamado
- **Então**: Deve passar na validação
- **Casos de Borda**: Nenhum

#### 6.2 Validação com erro
- **Dado**: Migrações são inválidas
- **Quando**: validateMigrations() é chamado
- **Então**: Deve falhar na validação
- **Casos de Borda**: Migração duplicada, checksum inválido

## Fluxo do Teste
1. Criar instância de FlywayExecutionService
2. Testar aplicar migrações pendentes (sucesso, falha)
3. Testar verificar versão atual (sucesso, falha)
4. Testar listar migrações aplicadas (sucesso, falha)
5. Testar listar migrações pendentes (sucesso, falha)
6. Testar rollback de migração (sucesso, falha)
7. Testar validação de migrações (sucesso, erro)
8. Verificar casos de borda (lista vazia, versão inválida)
