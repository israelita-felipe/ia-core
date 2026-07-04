# ia-core-flyway-service

## 📋 Descrição

Módulo de serviço para gerenciamento de migrações de banco de dados usando Flyway. Fornece versionamento e controle de evolução do esquema de BD de forma rastreável e reversível.

## 🏗️ Estrutura

```
ia-core-flyway-service/
├── src/main/java/
│   └── com/ia/core/flyway/service/
│       ├── FlywayService.java      # Serviço de migrações
│       ├── MigrationService.java   # Gerenciamento de versões
│       ├── ValidationService.java  # Validação de BD
│       └── util/                   # Utilitários
├── src/main/resources/
│   └── db/migration/               # Scripts SQL
│       ├── V1__initial_schema.sql
│       ├── V2__add_tables.sql
│       └── V3__add_constraints.sql
└── pom.xml
```

## 🔑 Responsabilidades

- **FlywayService**: Gerencia migrações (criação, limpeza, validação)
- **MigrationService**: Rastreia e documenta versões do BD
- **ValidationService**: Valida integridade do BD
- **Migration History**: Mantém registro de todas as mudanças
- **Schema Evolution**: Suporta alterações incrementais seguras
- **Rollback Support**: (com cuidado) reverter migrações

## 🛠️ Tecnologias Utilizadas

- **Flyway**: Migração de BD versionada
- **Spring Boot Flyway Starter**: Integração Spring
- **Spring Data JPA**: Acesso a dados
- **Jakarta Persistence**: Entidades JPA
- **SQL**: Scripts de migração

## 📦 Dependências

- `ia-core-flyway-model` - Entidades
- `org.flywaydb:flyway-core`
- `org.flywaydb:flyway-mysql` (para MySQL)
- Spring Boot Starter Data JPA

## 🔗 Relacionamentos

Depende de:
- `ia-core-flyway-model` - Entidades
- Banco de dados (MySQL, PostgreSQL, etc.)

Utilizado por:
- `ia-core-rest` - Endpoints para monitoramento
- `ia-core-flyway-view` - Interface web
- Aplicação principal na inicialização

## 💡 Padrões Implementados

- **Version Control Pattern**: Cada migração é numerada
- **Schema Evolution Pattern**: Mudanças incrementais seguras
- **Audit Trail Pattern**: Rastreamento de todas as alterações
- **Dependency Injection**: Via Spring

## 🚀 Como Usar

### Estrutura de Migrações

```markdown
src/main/resources/db/migration/
├── V1__Create_initial_schema.sql
├── V2__Add_user_table.sql
├── V3__Add_indexes.sql
└── V4__Add_constraints.sql
```

### Exemplo: V1__Create_initial_schema.sql

```sql
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
```

### Exemplo: V2__Add_conversations_table.sql

```sql
CREATE TABLE IF NOT EXISTS conversations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS chat_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL, -- USER, ASSISTANT
    content LONGTEXT NOT NULL,
    tokens INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT DEFAULT 0,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
);

CREATE INDEX idx_conversations_user_id ON conversations(user_id);
CREATE INDEX idx_chat_messages_conversation_id ON chat_messages(conversation_id);
```

### Implementar FlywayService

```java
@Service
@Transactional
public class FlywayServiceImpl implements FlywayService {

    @Autowired
    private Flyway flyway;

    @Autowired
    private MigrationHistoryRepository historyRepository;

    /**
     * Executa todas as migrações pendentes
     */
    public MigrationResult migrate() {
        try {
            MigrationInfo[] infos = flyway.migrate();

            int successCount = 0;
            for (MigrationInfo info : infos) {
                if (info.getState() == MigrationState.SUCCESS) {
                    successCount++;

                    // Registrar no histórico
                    MigrationHistory history = new MigrationHistory();
                    history.setVersion(info.getVersion().getVersion());
                    history.setDescription(info.getDescription());
                    history.setType(info.getType().toString());
                    history.setScript(info.getScript());
                    history.setChecksum(info.getChecksum());
                    history.setExecutionTime(info.getExecutionTime());
                    history.setSuccess(true);
                    history.setExecutedAt(LocalDateTime.now());
                    history.setExecutedBy(getCurrentUser());

                    historyRepository.save(history);
                }
            }

            return MigrationResult.builder()
                .success(true)
                .migrationsApplied(successCount)
                .message("Migração executada com sucesso")
                .build();

        } catch (FlywayException e) {
            return MigrationResult.builder()
                .success(false)
                .message("Erro na migração: " + e.getMessage())
                .build();
        }
    }

    /**
     * Valida o estado do BD
     */
    public ValidationResult validate() {
        try {
            flyway.validate();

            return ValidationResult.builder()
                .valid(true)
                .message("BD válido e consistente")
                .build();

        } catch (FlywayException e) {
            return ValidationResult.builder()
                .valid(false)
                .message("Erro de validação: " + e.getMessage())
                .issues(Arrays.asList(e.getMessage()))
                .build();
        }
    }

    /**
     * Obtém informações de migrações
     */
    public List<MigrationInfo> getMigrationsInfo() {
        return Arrays.asList(flyway.info().all());
    }

    /**
     * Obtém histórico completo de migrações
     */
    public Page<MigrationHistory> getMigrationHistory(Pageable pageable) {
        return historyRepository.findAllByOrderByExecutedAtDesc(pageable);
    }

    /**
     * Limpa toda a BD (apenas em desenvolvimento!)
     */
    public CleanResult clean() {
        if (isProduction()) {
            throw new IllegalStateException("Não é possível limpar BD em produção");
        }

        try {
            flyway.clean();
            historyRepository.deleteAll();

            return CleanResult.builder()
                .success(true)
                .message("BD limpa com sucesso")
                .build();

        } catch (FlywayException e) {
            return CleanResult.builder()
                .success(false)
                .message("Erro ao limpar BD: " + e.getMessage())
                .build();
        }
    }

    /**
     * Repara migrações com erro (usar com cuidado)
     */
    public RepairResult repair() {
        try {
            flyway.repair();

            return RepairResult.builder()
                .success(true)
                .message("BD reparada com sucesso")
                .build();

        } catch (FlywayException e) {
            return RepairResult.builder()
                .success(false)
                .message("Erro ao reparar BD: " + e.getMessage())
                .build();
        }
    }

    private boolean isProduction() {
        String profile = System.getProperty("spring.profiles.active", "");
        return profile.contains("prod");
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "SYSTEM";
    }
}
```

### REST Controller para Migrações

```java
@RestController
@RequestMapping("/api/${api.version}/flyway")
@PreAuthorize("hasRole('ADMIN')")
public class FlywayController {

    @Autowired
    private FlywayService flywayService;

    @PostMapping("/migrate")
    public ResponseEntity<MigrationResult> migrate() {
        MigrationResult result = flywayService.migrate();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidationResult> validate() {
        ValidationResult result = flywayService.validate();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/info")
    public ResponseEntity<List<MigrationInfo>> getMigrationsInfo() {
        List<MigrationInfo> infos = flywayService.getMigrationsInfo();
        return ResponseEntity.ok(infos);
    }

    @GetMapping("/history")
    public ResponseEntity<Page<MigrationHistory>> getMigrationHistory(
            @ParameterObject Pageable pageable) {
        Page<MigrationHistory> history = flywayService.getMigrationHistory(pageable);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/repair")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<RepairResult> repair() {
        RepairResult result = flywayService.repair();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clean")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CleanResult> clean() {
        CleanResult result = flywayService.clean();
        return ResponseEntity.ok(result);
    }
}
```

## ⚙️ Configuração

### application.yml

```yaml
spring:
  flyway:
    enabled: true
    baselineOnMigrate: true
    baselineVersion: 0
    locations: classpath:db/migration
    sqlMigrationPrefix: V
    sqlMigrationSeparator: __
    sqlMigrationSuffixes: .sql
    placeholderReplacement: true
    placeholders:
      flyway_placeholder_replacement: true

  datasource:
    url: jdbc:mysql://localhost:3306/aplicacao
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: validate  # NÃO usar create, update em produção!
    show-sql: false
```

## 📋 Convenções de Nomenclatura

| Padrão | Descrição |
|--------|-----------|
| `V1__Initial_schema.sql` | Migração versionada (versão 1) |
| `V2__Add_users_table.sql` | Versão 2 (números sequenciais) |
| `V1.1__Fix_constraints.sql` | Submigração (mesmo major, incremento minor) |
| `U1__Undo_initial_schema.sql` | Undo migration (reverter) |

## 🧪 Testes

```java
@SpringBootTest
public class FlywayServiceTest {

    @Autowired
    private FlywayService flywayService;

    @Test
    public void testMigration() {
        MigrationResult result = flywayService.migrate();

        assertTrue(result.isSuccess());
        assertTrue(result.getMigrationsApplied() >= 0);
    }

    @Test
    public void testValidation() {
        ValidationResult result = flywayService.validate();

        assertTrue(result.isValid());
    }
}
```

## 🔐 Segurança

- ✅ Nunca execute `clean()` em produção
- ✅ Use `ddl-auto: validate` em produção
- ✅ Revise SQL antes de executar em produção
- ✅ Faça backup antes de migrações críticas
- ✅ Teste migrações em staging primeiro

## 📝 Melhores Práticas

1. **Migrations increp**: Nunca modifique migrations antigas
2. **Script testado**: Teste SQL localmente antes
3. **Nomes descritivos**: Use nomes claros de migração
4. **BD/App sincronizados**: Atualize ambos juntos
5. **Documentação**: Documente mudanças complexas

## 🔍 Referências

- [Flyway Documentation](https://flywaydb.org/)
- [Spring Flyway Integration](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-execute-flyway-database-migrations-on-startup)


