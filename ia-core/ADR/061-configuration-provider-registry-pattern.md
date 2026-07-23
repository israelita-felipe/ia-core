# ADR-061: Padronização de ConfigurationProvider, ConfigurationRegistry e ConfiguracaoSistema

## Status

✅ **Implementado**

## Contexto

O ia-core utiliza um mecanismo de configuração centralizado e extensível por módulos, conforme definido no ADR-060. Entretanto, a ausência de uma padronização explícita para os contratos de `ConfigurationProvider`, `ConfigurationRegistry` e `ConfiguracaoSistema` pode levar a implementações inconsistentes nos módulos (Flyway, LLM, Quartz, Security, Communication, Biblia).

### Drivers da Decisão

- Garantir que todos os módulos implementem configurações seguindo a mesma estrutura
- Facilitar a adição de novos módulos sem dúvidas sobre o contrato
- Centralizar a documentação do padrão para reduzir retrabalho
- Garantir que a UI de configuração (ConfigurationTabView) e o registry funcionem de forma previsível

## Decisão

Documentar e padronizar a implementação de configurações modulares através de três componentes obrigatórios:

1. **Entidade de domínio** (`[Módulo]ConfiguracaoSistema`) - herda `ConfiguracaoSistema`
2. **Provider** (`[Módulo]ConfigurationProvider`) - implementa `ConfigurationProvider`
3. **Registry** (`[Módulo]ConfigurationRegistry`) - estende `ConfigurationRegistry`

Além disso, cada módulo deve possuir DTO, Mapper, Repository e Service genéricos seguindo o padrão CRUD já estabelecido.

## Detalhes

### 1. Entidade de Domínio

Cada módulo define sua própria entidade concreta herdando de `ConfiguracaoSistema`, permitindo que cada módulo tenha sua tabela/schema isolada.

**Arquivo:** `[módulo]-model/src/main/java/com/ia/[domínio]/model/configuracao/[Módulo]Configuracao.java`

```java
@Entity
@Table(schema = [Módulo]Configuracao.SCHEMA_NAME, name = [Módulo]Configuracao.TABLE_NAME)
public class [Módulo]Configuracao extends ConfiguracaoSistema {

    public static final String TABLE_NAME = [Módulo]Model.TABLE_PREFIX + "CONFIGURACAO";
    public static final String SCHEMA_NAME = [Módulo]Model.SCHEMA;
    private static final long serialVersionUID = 1L;
}
```

**Campos herdados de `ConfiguracaoSistema`:**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `chave` | `String` | Chave única da configuração |
| `valor` | `String` | Valor da configuração (armazenado como texto) |
| `modulo` | `String` | Nome do módulo proprietário |
| `categoria` | `String` | Categoria da configuração (ex: "Flyway", "Security") |
| `descricao` | `String` | Descrição amigável para exibição na UI |
| `tipo` | `TipoConfiguracao` | Tipo: STRING, BOOLEAN, INTEGER |

### 2. Camada Service Model (DTO)

**Arquivo:** `[módulo]-service-model/src/main/java/com/ia/[domínio]/service/configuracao/dto/[Módulo]ConfiguracaoDTO.java`

```java
@SuperBuilder(toBuilder = true)
public class [Módulo]ConfiguracaoDTO extends ConfiguracaoSistemaDTO<[Módulo]Configuracao> {
    private static final long serialVersionUID = <serial>;

    public [Módulo]ConfiguracaoDTO() { super(); }

    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    @Override
    public [Módulo]ConfiguracaoDTO cloneObject() {
        return toBuilder().build();
    }
}
```

### 3. Camada Service (Provider + Genéricos)

#### 3.1 Mapper, Repository e Service Genéricos

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]ConfiguracaoMapper.java`

```java
@Mapper(componentModel = "spring")
public interface [Módulo]ConfiguracaoMapper
    extends ConfiguracaoMapper<[Módulo]Configuracao, [Módulo]ConfiguracaoDTO> { }
```

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]ConfiguracaoRepository.java`

```java
@Repository
public interface [Módulo]ConfiguracaoRepository
    extends ConfiguracaoRepository<[Módulo]Configuracao> { }
```

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]ConfiguracaoService.java`

```java
@Service
public class [Módulo]ConfiguracaoService
    extends ConfiguracaoGenericService<[Módulo]Configuracao, [Módulo]ConfiguracaoDTO> { }
```

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]ConfiguracaoServiceConfig.java`

```java
@Component
public class [Módulo]ConfiguracaoServiceConfig
    extends CrudBaseServiceConfig<[Módulo]Configuracao, [Módulo]ConfiguracaoDTO> { }
```

#### 3.2 ConfigurationProvider (Obrigatório)

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]ConfigurationProvider.java`

```java
@Slf4j
@Component
@EnableConfigurationProperties({Módulo}Properties.class)
public class [Módulo]ConfigurationProvider implements ConfigurationProvider {

    @Getter private final [Módulo]Properties [modulo]Properties;
    @Getter private final Properties properties;
    @Getter private List<ConfiguracaoSistemaDTO<?>> configurations;

    public [Módulo]ConfigurationProvider([Módulo]Properties [modulo]Properties) {
        this.[modulo]Properties = [modulo]Properties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public String getModulo() { return [Módulo]Model.NAME; }

    @Override
    public void validar(ConfiguracaoSistemaDTO<?> config) {
        if (!ALLOWED_CHAVES.contains(config.getChave())) {
            log.debug("Chave ignorada: {}", config.getChave());
            return;
        }
        // validações específicas (boolean, range, etc)
    }

    @Override
    public void aplicar(ConfiguracaoSistemaDTO<?> config) {
        validar(config);
        String chave = config.getChave();
        String valor = config.getValor();

        // Atualiza lista de configurações
        int index = configurations.indexOf(config);
        if (index != -1) configurations.set(index, config);
        else configurations.add(config);

        // Aplica no properties
        switch (chave) {
            case [Módulo]PropertiesConstants.ALGUMA_CHAVE ->
                [modulo]Properties.setAlgumCampo(convert(valor));
            // ... demais casos
        }

        this.properties.clear();
        this.properties.putAll(createProperties());
        log.info("Configuração aplicada: chave={}, valor={}", chave, valor);
    }

    private List<ConfiguracaoSistemaDTO<?>> buildConfigurations() {
        List<ConfiguracaoSistemaDTO<?>> configs = new ArrayList<>();
        // Registra todas as configurações com valores defaults
        add(configs, [Módulo]PropertiesConstants.ALGUMA_CHAVE,
            [modulo]Properties.getAlgumCampo(), TipoConfiguracao.STRING,
            "Categoria", "Descrição amigável");
        return configs;
    }

    private void add(List<ConfiguracaoSistemaDTO<?>> configs, String chave,
                     Object valor, TipoConfiguracao tipo,
                     String categoria, String descricao) {
        configs.add([Módulo]ConfiguracaoDTO.builder()
            .modulo(getModulo()).chave(chave).valor(convertValue(valor))
            .tipo(tipo).categoria(categoria).descricao(descricao).build());
    }

    protected Properties createProperties() {
        Properties properties = new Properties();
        properties.setProperty([Módulo]PropertiesConstants.ALGUMA_CHAVE,
            String.valueOf([modulo]Properties.getAlgumCampo()));
        return properties;
    }
}
```

**Contratos obrigatórios:**
| Método | Descrição |
|--------|-----------|
| `getModulo()` | Retorna o nome do módulo (ex: `QuartzModel.NAME`) |
| `getConfigurations()` | Lista de DTOs com todas as configurações default |
| `validar(config)` | Valida valores antes de aplicar (tipos, ranges, etc) |
| `aplicar(config)` | Aplica a configuração no `Properties` correspondente e atualiza `configurations` |

### 4. ConfigurationRegistry

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]ConfigurationRegistry.java`

```java
@Component
public class [Módulo]ConfigurationRegistry
    extends ConfigurationRegistry<[Módulo]Configuracao, [Módulo]ConfiguracaoDTO> {

    public [Módulo]ConfigurationRegistry(List<ConfigurationProvider> providers,
                                          [Módulo]ConfiguracaoRepository repository,
                                          [Módulo]ConfiguracaoMapper mapper) {
        super(providers, repository, mapper);
    }

    @Override
    protected [Módulo]ConfiguracaoDTO build(ConfiguracaoSistemaDTO<?> configDto) {
        return ([Módulo]ConfiguracaoDTO) configDto;
    }
}
```

**Responsabilidades do `ConfigurationRegistry`:**
- Executado via `@PostConstruct` após o Spring Context iniciar
- Itera sobre todos os `ConfigurationProvider` registrados
- Faz `upsert` das configurações padrão no banco
- Aplica as configurações via `provider.aplicar(dto)`
- Remove configurações órfãs (existente no BD mas sem provider)

## Exemplo Prático: Módulo Quartz

| Componente | Arquivo |
|------------|---------|
| Entidade | `QuartzConfiguracao.java` |
| DTO | `QuartzConfiguracaoDTO.java` |
| Mapper | `QuartzConfiguracaoMapper.java` |
| Repository | `QuartzConfiguracaoRepository.java` |
| Service | `QuartzConfiguracaoService.java` |
| ServiceConfig | `QuartzConfiguracaoServiceConfig.java` |
| **Properties** | `QuartzProperties.java` (`ia-core.quartz`) |
| **PropertiesConstants** | `QuartzPropertiesConstants.java` |
| **Provider** | `QuartzConfigurationProvider.java` |

**Estrutura de configuração YAML:**

```yaml
ia-core:
  quartz:
    scheduler:
      instance-name: "CoreQuartzScheduler"
      instance-id: "AUTO"
    thread-pool:
      thread-count: 5
    job-store:
      class: "org.quartz.impl.jdbcjobstore.JobStoreTX"
```

## Checklist para Novo Módulo

- [ ] `[Módulo]Configuracao` extends `ConfiguracaoSistema`
- [ ] `[Módulo]ConfiguracaoDTO` extends `ConfiguracaoSistemaDTO`
- [ ] `[Módulo]ConfiguracaoMapper` extends `ConfiguracaoMapper`
- [ ] `[Módulo]ConfiguracaoRepository` extends `ConfiguracaoRepository`
- [ ] `[Módulo]ConfiguracaoService` extends `ConfiguracaoGenericService`
- [ ] `[Módulo]ConfiguracaoServiceConfig` extends `CrudBaseServiceConfig`
- [ ] `[Módulo]ConfigurationProvider` implements `ConfigurationProvider`
- [ ] `[Módulo]ConfigurationRegistry` extends `ConfigurationRegistry`
- [ ] `[Módulo]Properties` com `@ConfigurationProperties`
- [ ] `[Módulo]PropertiesConstants` com chaves constantes
- [ ] Traduções no i18n do `service-model`

## Consequências

### Positivas

- ✅ Contrato explícito para extensibilidade modular
- ✅ Implementação previsível e consistente
- ✅ Registry automático com cleanup de órfãos
- ✅ UI genérica funciona sem customização por módulo
- ✅ Propriedades Spring nativas integradas ao sistema de configuração

### Negativas

- ❌ Curva de aprendizado para novos módulos
- ❌ Boilerplate inicial (DTO, Mapper, Repository, Service, Provider, Registry)
- ❌ Cada módulo precisa de sua própria tabela/schema

## Data

2026-07-22

## Revisores

- Team Lead
- Architect

## Referências

1. **ADR-060**: Configuração Extensível Modular
2. **ConfigurationRegistry.java**: `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfigurationRegistry.java`
3. **ConfigurationProvider.java**: `ia-core-service/src/main/java/com/ia/core/service/configuracao/ConfigurationProvider.java`

## ADRs Relacionados

- ADR-060: Configuração Extensível Modular
- ADR-040: CAMPOS nested class pattern
- ADR-008: Arquitetura MVVM
