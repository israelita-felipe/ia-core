# ADR-017: Usar Flyway para Migrações de Banco de Dados

✅ Aceito

## Contexto

O projeto precisa de uma forma controlada e versionada de gerenciar alterações no schema do banco de dados. O uso de scripts SQL manuais ou alterações diretas no banco leading a problemas como:

- Perda de histórico de alterações
- Dificuldade em replicar ambiente (dev → homolog → prod)
- Conflitos em ambiente colaborativo (múltiplos desenvolvedores)
- Falta de rastreabilidade das mudanças
- Dificuldade em rollback

## Decisão

Usar **Flyway** como ferramenta de migrations do banco de dados, integrada ao ciclo de build da aplicação.

## Detalhes

### Estrutura de Diretórios

```
src/main/resources/
├── db/
│   └── migration/
│       ├── V1__Initial_schema.sql
│       ├── V2__Add_pessoa_table.sql
│       ├── V3__Add_familia_table.sql
│       └── V4__Add_audit_columns.sql
```

### Convenções de Nomenclatura

| Prefixo | Descrição | Exemplo |
|---------|-----------|---------|
| `V` | Versionamento | `V1__`, `V2__` |
| `R` | Reversões (undo) | `R__1_0_0__undo.sql` |
| `U` | Updates idx | `U1__add_index.sql` |

**Padrão Global**: `V{MODULE_PREFIX}{VERSION}__{DESCRIPTION}.sql`

Cada módulo DEVE utilizar um prefixo específico para evitar conflitos de versão entre módulos diferentes:

| Módulo | Prefixo | Exemplo |
|--------|---------|---------|
| biblia-service | BBL | `VBBL20260101__Create_livro.sql` |
| ia-core-quartz-service | QTZ | `VQTZ20260101__Create_job_table.sql` |
| ia-core-security-service | SEC | `VSEC20260101__Create_user_table.sql` |
| ia-core-llm-service | LLM | `VLLM20260101__Create_template.sql` |
| ia-core-communication-service | COM | `VCOM20260101__Create_message_table.sql` |
| ia-core-flyway-service | FLY | `VFLY20260101__Create_migration_log.sql` |

**Formato da Versão {VERSION}**: `YYYYMMDDHHMMSS` para ordenação cronológica

Exemplos:
- `VBBL20260101143000__Create_livro.sql` (biblia-service)
- `VQTZ20260101143000__Create_job_table.sql` (quartz-service)
- `VSEC20260101143000__Create_user_table.sql` (security-service)

### Configuração no pom.xml

```xml
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <version>10.4.1</version>
    <configuration>
        <locations>
            <location>classpath:db/migration</location>
        </locations>
        <baselineOnMigrate>true</baselineOnMigrate>
    </configuration>
</plugin>
```

### Configuração no application.yml

```yaml
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
    sql-migration-prefix: V
    sql-migration-separator: __
    sql-migration-suffixes: .sql
    validate-on-migrate: true
    baseline-version: 0
    table: flyway_schema_history
```

### Exemplo de Migration

```sql
-- VBBL20240315143000__Create_base_entity.sql (biblia-service)
CREATE TABLE base_entity (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- VBBL20240315144500__Create_pessoa_table.sql (biblia-service)
CREATE TABLE pessoa (
    id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    cpf VARCHAR(14),
    data_nascimento DATE,
    CONSTRAINT pk_pessoa PRIMARY KEY (id),
    CONSTRAINT fk_pessoa_base FOREIGN KEY (id) REFERENCES base_entity(id)
);

CREATE INDEX idx_pessoa_nome ON pessoa(nome);
CREATE INDEX idx_pessoa_cpf ON pessoa(cpf);
```

### Commands Maven

```bash
# Executar migrations
mvn flyway:migrate

# Verificar status
mvn flyway:info

# Limpar banco (cuidado!)
mvn flyway:clean

# Gerar baseline
mvn flyway:baseline

# Validar migrations
mvn flyway:validate
```

## Consequências

### Positivas

- ✅ Versionamento do schema via código
- ✅ Histórico completo de alterações
- ✅ Rollback controlado
- ✅ Repeable deployments
- ✅ Integração com CI/CD
- ✅ Suporte a múltiplos bancos (PostgreSQL, MySQL, etc.)
- ✅ Detecção de conflitos de merge

### Negativas

- ❌ Curva de aprendizado para equipe
- ❌ Requer disciplina na criação de migrations
- ❌ Não pode modificar migrations já aplicadas
- ❌ Arquivos grandes podem ser difíceis de revisar

## Status de Implementação

✅ **COMPLETO**

- Flyway configurado em todos os módulos do projeto
- Migrations organizadas em `src/main/resources/db/migration`
- Configuração via `application.yml`

## Data

2024-03-15

## Revisores

- Team Lead
- Architect

## Padrões de Padronização (Standardization)

Este ADR adere aos seguintes padrões, RFCs e melhores práticas:

### RFCs Relevantes

| RFC | Título | Aplicação neste ADR |
|-----|--------|---------------------|
| **RFC 3629** | UTF-8, a transformation format of ISO 10646 | Codificação obrigatória para todos os arquivos SQL |
| **RFC 5646** | Tags for Identifying Languages | Tags de locale para migrações multilíngue quando aplicável |
| **RFC 8288** | Web Linking | Links de documentação para migrações complexas; substitui RFC 5988 |

### Padrões de Mercado

| Padrão | Fonte | Aplicação |
|--------|-------|-----------|
| **Flyway Conventions** | Flyway | Nomenclatura, estrutura e organização de migrations |
| **SQL Best Practices** | ANSI SQL | Padrões de escrita de SQL para portabilidade |
| **Database Migration Patterns** | Martin Fowler | Padrões de migração segura e reversível |
| **Version Control Best Practices** | Git | Controle de versão para migrations |

### Boas Práticas Adotadas

1. **UTF-8 obrigatório**: Todos arquivos `.sql` devem usar UTF-8 com BOM quando necessário.
2. **Nomes descritivos**: Descrições devem ser claras e no imperativo (ex: "Add_user_table").
3. **Atomicidade**: Cada migration deve ser atômica - ou completa ou falha completamente.
4. **Idempotência**: Migrações devem ser idempotentes - podem ser executadas múltiplas vezes sem efeitos colaterais adversos. Deve usar `CREATE TABLE IF NOT EXISTS` e verificar existência antes de criar objetos.
5. **Documentação**: Comentários explicam o propósito e mudanças significativas.
6. **Rollback planejado**: Migrações críticas devem ter reversão documentada.
7. **Performance**: Índices criados após dados populados para evitar locks.
8. **Segurança**: Sem `DROP TABLE` sem backup prévio; usar `soft deletes` quando possível.
9. **Testes**: Migrações devem ser testadas em ambiente de staging antes de produção.
10. **Versionamento**: Versões seguem formato `YYYYMMDDHHMMSS` para ordenação cronológica, com prefixo do módulo (ex: `VBBL20240315143000`).

### Idempotência em Migrations

#### Definição

Idempotência é uma propriedade matemática e de sistemas distribuídos onde uma operação pode ser executada múltiplas vezes sem alterar o resultado além da primeira execução. Em contexto de migrations de banco de dados, significa que scripts SQL devem produzir o mesmo estado final independentemente de quantas vezes sejam executados.

#### Importância

- **CI/CD**: Ambientes podem executar migrations várias vezes durante pipelines
- **Hotfix**: Correções podem ser aplicadas após migrations já executadas
- **Re-deploy**: Reimplantação em ambientes limpos não deve causar falhas
- **Concorrência**: Múltiplos desenvolvedores podem aplicar migrations simultaneamente

#### Estratégias para Idempotência

1. **CREATE IF NOT EXISTS**:
   ```sql
   CREATE TABLE IF NOT EXISTS SECURITY.SEC_USER (...);
   CREATE INDEX IF NOT EXISTS idx_sec_user_name ON SECURITY.SEC_USER(name);
   ```

2. **Verificação de Constraints**:
   ```sql
   -- PostgreSQL/H2
   DO $$ BEGIN
       IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'uk_sec_user_code') THEN
           ALTER TABLE SECURITY.SEC_USER ADD CONSTRAINT uk_sec_user_code UNIQUE (user_code);
       END IF;
   END $$;
   ```

3. **MERGE/INSERT ... ON CONFLICT** (PostgreSQL):
   ```sql
   INSERT INTO SECURITY.SEC_ROLE (id, name) 
   VALUES (1, 'ADMIN') 
   ON CONFLICT (id) DO NOTHING;
   ```

4. **Checagem de existência de tabelas**:
   ```sql
   -- H2
   CREATE TABLE IF NOT EXISTS SECURITY.SEC_PRIVILEGE (
       id BIGINT NOT NULL,
       name VARCHAR(500) NOT NULL UNIQUE,
       ...
   );
   ```

#### Referências

- Redgate: [Creating Idempotent DDL Scripts for Database Migrations](https://www.red-gate.com/hub/product-learning/flyway/creating-idempotent-ddl-scripts-for-database-migrations/)
- Flyway Documentation: Migrations são idempotentes por natureza via tabela `flyway_schema_history`

### Padrões de Código SQL

```sql
-- Comentários no início explicando o propósito
-- VBBL20240315143000__Create_pessoa_table.sql (biblia-service)
-- Cria tabela de pessoas com campos básicos e constraints
-- Autor: Team Lead
-- Dependências: VBBL20240315140000__Create_base_entity.sql

-- Uso de schema qualificado para evitar ambiguidade
CREATE TABLE biblia.pessoa (
    id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    data_nascimento DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_pessoa PRIMARY KEY (id),
    CONSTRAINT fk_pessoa_base FOREIGN KEY (id) REFERENCES biblia.base_entity(id)
);

-- Índices criados após dados populados
CREATE INDEX idx_pessoa_nome ON biblia.pessoa(nome);
CREATE INDEX idx_pessoa_cpf ON biblia.pessoa(cpf);

-- Comentários explicando constraints
-- Constraint para evitar CPFs duplicados
-- Constraint para garantir que nome não seja nulo
```

### Compatibilidade com ADRs Relacionados

- **ADR-003**: UTF-8 em todos os arquivos SQL e properties.
- **ADR-010**: Nomes de tabelas e colunas seguem convenções de nomenclatura.
- **ADR-047**: UTF-8 e tags de idioma para migrações multilíngue.
- **ADR-048**: Migrações para tabelas de AI/MCP seguem padrões do projeto.
- **ADR-050**: Diretrizes gerais de padronização do projeto.
- **ADR-052**: ADRs devem usar linguagem normativa RFC 2119/RFC 8174 e indicar referências técnicas vigentes.

## Referências

1. **Flyway Documentation**
   - URL: https://flywaydb.org/documentation/
   - Documentação oficial do Flyway

2. **Flyway Maven Plugin**
   - URL: https://flywaydb.org/documentation/usage/maven/
   - Uso do plugin Maven

3. **Baeldung - Flyway Tutorial**
   - URL: https://www.baeldung.com/flyway-migrations
   - Tutorial completo sobre Flyway

4. **Vlad Mihalcea - Flyway Best Practices**
   - URL: https://vladmihalcea.com/flyway-best-practices/
   - Boas práticas para migrations

5. **Spring Boot - Flyway Auto-configuration**
   - URL: https://docs.spring.io/spring-boot/reference/data/sql.html#data.sql.migration.flyway
   - Configuração automática com Spring Boot
