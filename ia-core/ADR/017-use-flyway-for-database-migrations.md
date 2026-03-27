# ADR-017: Usar Flyway para Migrações de Banco de Dados

## Status

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
| `U` |Updates idx | `U1__add_index.sql` |

**Padrão**: `V{VERSION}__{DESCRIPTION}.sql`

Exemplo: `V20240315_1430__Create_pessoa_table.sql`

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
-- V1__Create_base_entity.sql
CREATE TABLE base_entity (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- V2__Create_pessoa_table.sql
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