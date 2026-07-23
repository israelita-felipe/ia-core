# ADR-060: Configuração Extensível Modular

## Status

✅ **Implementado**

> **Nota**: A camada de segurança (SecuredConfiguracaoService) pode ser implementada posteriormente via Decoration Pattern conforme necessidade.

## Contexto

O ia-core precisa de um mecanismo de configuração centralizado que atenda aos seguintes requisitos:

1. **Extensibilidade por módulo** - Cada módulo (LLM, Communication, Security, Biblia, etc.) deve poder contribuir configurações específicas sem interferir umas com as outras
2. **Armazenamento no banco de dados** - Permitir configuração dinâmica sem necessidade de redploy
3. **UI genérica com abas personalizadas** - Tela de configuração única com abas distintas por módulo
4. **Consistência com ADRs existentes** - Seguir padrões como `ADR-018 Business Rule Chain`, `ADR-019 Service Validator`, e MVVM (`ADR-008`)

### Drivers da Decisão

- Configurações como e-mail, SMS, endpoints LLM variam por ambiente e cliente
- Hardcoding de valores dificulta manutenção e implantação
- Necessidade de UI administrativa centralizada para todas as configurações
- Os módulos precisam de seções de configuração independentes

## Decisão

Implementar um padrão de **Configuração Extensível Baseada em Plugins** com arquitetura em camadas seguindo o padrão `Attachment` (entidade abstrata no core, implementação concreta nos módulos).

## Detalhes

### 1. Model Layer (Entidade Abstrata)

**ia-core-model**

```java
// Arquivo: src/main/java/com/ia/core/model/configuracao/ConfiguracaoSistema.java
@MappedSuperclass
public abstract class ConfiguracaoSistema extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String chave;
    
    @Lob
    @Column(nullable = false)
    private String valor;
    
    @Column(nullable = false)
    private String modulo;
    
    @Column(nullable = false)
    private String categoria;
    
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConfiguracao tipo;
}
```

### 2. Service Layer (Classes Genéricas)

**ia-core-service**

```java
// Fornece construtos genéricos para extensão nos módulos
public class ConfiguracaoGenericService<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>> 
  extends CrudBaseService<T, D>

public interface ConfiguracaoMapper<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>>
  extends BaseEntityMapper<T, D>

public interface ConfiguracaoRepository<T extends ConfiguracaoSistema> 
  extends BaseEntityRepository<T>
```

### 3. Service Model Layer (DTO Genérico)

**ia-core-service-model**

```java
// Arquivo: src/main/java/com/ia/core/service/configuracao/dto/ConfiguracaoSistemaDTO.java
public class ConfiguracaoSistemaDTO<T extends ConfiguracaoSistema> 
  extends AbstractBaseEntityDTO<T> {
    private String chave;
    private String valor;
    private String modulo;
    private String categoria;
    private String descricao;
    private TipoConfiguracao tipo;
}
```

### 4. Providers por Módulo

**ia-core-service**

```java
// Arquivo: src/main/java/com/ia/core/service/configuracao/ConfigurationProvider.java
public interface ConfigurationProvider {
    String getModulo();
    void validar(ConfiguracaoSistemaDTO<?> config);
    void aplicar(ConfiguracaoSistemaDTO<?> config);
}
```

## Arquivos em ia-core

| Arquivo | Status |
|---------|--------|
| `ia-core-model/src/main/java/com/ia/core/model/configuracao/ConfiguracaoSistema.java` | ✅ Abstrata (`@MappedSuperclass`) |
| `ia-core-model/src/main/java/com/ia/core/model/configuracao/TipoConfiguracao.java` | ✅ Criado |
| `ia-core-model/src/main/java/com/ia/core/model/configuracao/ModuleConstants.java` | ✅ Criado |
| `ia-core-service-model/src/main/java/com/ia/core/service/configuracao/dto/ConfiguracaoSistemaDTO.java` | ✅ Genérica |
| `ia-core-service-model/src/main/java/com/ia/core/service/configuracao/dto/ConfiguracaoSistemaTranslator.java` | ✅ Criado |
| `ia-core-service-model/src/main/java/com/ia/core/service/configuracao/dto/ConfiguracaoSistemaSearchRequestDTO.java` | ✅ Criado |
| `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfigurationProvider.java` | ✅ Interface |
| `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfiguracaoMapper.java` | ✅ Genérica (sem @Mapper) |
| `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfiguracaoRepository.java` | ✅ Genérica |
| `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfiguracaoGenericService.java` | ✅ Genérica |
| `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfiguracaoSistemaValidator.java` | ✅ Criado |
| `ia-core-service/src/main/resources/db/migrations/hsqldb/VCORE2026071601__create_configuracao_sistema_table.sql` | ✅ Criado |

## Providers Implementados

| Módulo | Classe Provider | Arquivo |
|--------|-----------------|---------|
| FlyWay | `FlywayConfigurationProvider` | `ia-core-flyway-service` |
| LLM | `LlmConfigurationProvider` | `ia-core-llm-service` |
| Quartz | `QuartzConfigurationProvider` | `ia-core-quartz-service` |
| Security | `SecurityConfigurationProvider` | `ia-core-security-service` |
| Communication | `CommunicationConfigurationProvider` | `ia-core-communication-service` |
| Biblia | `BibliaConfigurationProvider` | `biblia-service` |

## Exemplo de Implementação em Módulo (Biblia)

```java
// biblia-model/src/main/java/com/ia/biblia/model/configuracao/BibliaConfiguracao.java
@Entity
@Table(schema = BibliaConfiguracao.SCHEMA_NAME, name = BibliaConfiguracao.TABLE_NAME)
public class BibliaConfiguracao extends ConfiguracaoSistema {
    public static final String TABLE_NAME = BibliaModel.TABLE_PREFIX + "CONFIGURACAO";
    public static final String SCHEMA_NAME = BibliaModel.SCHEMA;
}

// biblia-service-model/.../BibliaConfiguracaoDTO.java
public class BibliaConfiguracaoDTO extends ConfiguracaoSistemaDTO<BibliaConfiguracao> { }

// biblia-service/.../BibliaConfiguracaoMapper.java
@Mapper(componentModel = "spring")
public interface BibliaConfiguracaoMapper 
  extends ConfiguracaoMapper<BibliaConfiguracao, BibliaConfiguracaoDTO> { }

// biblia-service/.../BibliaConfiguracaoRepository.java
@Repository
public interface BibliaConfiguracaoRepository 
  extends ConfiguracaoRepository<BibliaConfiguracao> { }

// biblia-service/.../BibliaConfiguracaoService.java
@Service
public class BibliaConfiguracaoService 
  extends ConfiguracaoGenericService<BibliaConfiguracao, BibliaConfiguracaoDTO> { }

// biblia-service/.../BibliaConfiguracaoServiceConfig.java
@Component
public class BibliaConfiguracaoServiceConfig 
  extends CrudBaseServiceConfig<BibliaConfiguracao, BibliaConfiguracaoDTO> { }
```

## Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| Spring Cloud Config Server | Centralizado, versionado | Infraestrutura adicional, não integra com UI |
| Properties File + RefreshScope | Nativo do Spring Boot | Não persiste no BD, não editável em runtime |
| **Configuração Extensível Modular** | Modular, editável, UI integrada | Mais complexidade, precisa de security layer separada |

## Consequências

### Positivas

- ✅ Configuração centralizada e dinâmica
- ✅ Módulos independentes podem contribuir configurações
- ✅ UI genérica com abas/sub-abas por categoria (ordem alfabética)
- ✅ Database persistence para portabilidade entre ambientes
- ✅ Providers validam configurações específicas por módulo
- ✅ Cada módulo define sua própria tabela/schema (flexibilidade)

### Negativas

- ❌ Complexidade adicional na camada de service
- ❌ Necessidade de encryption para dados sensíveis
- ❌ Cache invalidation precisa ser bem projetado

## Data

2026-07-16

## Revisores

- Team Lead
- Architect

## Referências

1. **Externalized Configuration Pattern** - microservices.io
   - URL: https://microservices.io/patterns/externalized-configuration.html

2. **Spring Boot Externalized Configuration** - Spring Docs
   - URL: https://docs.spring.io/spring-boot/reference/features/external-config.html

3. **Plugin Architecture in Java** - BestHub
   - URL: https://www.besthub.dev/articles/plugin-architecture-in-java-implementing-modular-extensions

## ADRs Relacionados

- ADR-018: Usar Business Rule Chain
- ADR-019: Usar Service Validator
- ADR-008: Arquitetura MVVM
- ADR-005: Usar Domain Events
- ADR-040: CAMPOS nested class pattern
- ADR-011: Exception Handling Patterns