# ADR-062: Padronização de [Módulo]Properties e [Módulo]PropertiesConstants

## Status

✅ **Implementado**

## Contexto

Os módulos do ia-core utilizam propriedades Spring Boot externas para configurar comportamento específico. A ausência de um padrão para agrupar essas propriedades gera inconsistências:
- Alguns módulos usam prefixos genéricos (`ia-core.*`), outros já usam namespaces nativos do Spring (`spring.flyway`, `spring.ai`)
- A repetição de chaves literais nos `ConfigurationProvider` dificulta refatoração
- Falta uma camada de tipagem forte entre `application.yml` e o `ConfigurationProvider`

### Drivers da Decisão

- Alinhar prefixos com namespaces nativos do Spring Boot quando existirem
- Tipar todas as chaves de configuração via constantes
- Permitir que uma única fonte YAML alimente tanto a auto-configuração nativa quanto o `ConfigurationProvider`
- Facilitar refatorações e detecção de chaves órfãs via IDE

## Decisão

Implementar o padrão **Module Properties** composto por dois arquivos obrigatórios por módulo:

1. `[Módulo]Properties` - classe `@ConfigurationProperties` tipada
2. `[Módulo]PropertiesConstants` - constantes literais para todas as chaves

## Detalhes

### 1. [Módulo]Properties

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]Properties.java`

Responsável por:
- Mapear propriedades YAML para objetos tipados via `@ConfigurationProperties`
- Oferecer defaults saneados para cada configuração
- Servir como única fonte de verdade tipada para o módulo
- Ser injetado no `[Módulo]ConfigurationProvider`

**Padrão de implementação:**

```java
@Getter
@Setter
@ConfigurationProperties(prefix = "namespace.nativo.ou.customizado")
public class [Módulo]Properties {

    // Grupos nomeados como classes estáticas internas
    private GrupoConfig grupo = new GrupoConfig();
    private OutroGrupoConfig outro = new OutroGrupoConfig();

    @Getter
    @Setter
    public static class GrupoConfig {
        private String campo = "default";
        private boolean flag = true;
        private int numero = 10;
    }

    @Getter
    @Setter
    public static class OutroGrupoConfig {
        private String url = "http://localhost";
        private List<String> itens = new ArrayList<>();
    }
}
```

**Regras:**
- Usar `@Getter` e `@Setter` (Lombok) - não usar `@Data` se a classe crescer muito
- Agrupar propriedades relacionadas em classes estáticas internas
- Sempre fornecer valores default sensatos
- O prefixo deve ser escolhido conforme a tabela abaixo

**Tabela de namespaces:**

| Módulo | Prefixo recomendado | Justificativa |
|--------|---------------------|---------------|
| Flyway | `spring.flyway` | Nativo do Spring Boot |
| Spring AI / LLM | `spring.ai` | Nativo do Spring Boot / Spring AI |
| Quartz | `ia-core.quartz` | Não há namespace nativo equivalente |
| Security | `ia-core.security` | Não há namespace nativo consolidado |
| Communication (Mail) | `spring.mail` | Nativo do Spring Boot |
| Biblia | `ia-core.biblia` | Domínio customizado |

### 2. [Módulo]PropertiesConstants

**Arquivo:** `[módulo]-service/src/main/java/com/ia/[domínio]/service/configuracao/[Módulo]PropertiesConstants.java`

Responsável por:
- Centralizar todas as chaves literais usadas no módulo
- Servir de referência para `ConfigurationProvider`, `ConfigurationRegistry` e i18n
- Evitar "magic strings" espalhadas pelo código

**Padrão de implementação:**

```java
public final class [Módulo]PropertiesConstants {

    // =========================================================================
    // Grupo Properties (namespace.grupo.*)
    // =========================================================================
    /**
     * Property key: namespace.grupo.campo
     */
    public static final String GRUPO_CAMPO = "grupo.campo";

    /**
     * Property key: namespace.grupo.flag
     */
    public static final String GRUPO_FLAG = "grupo.flag";

    private [Módulo]PropertiesConstants() {
        // Utility class
    }
}
```

**Regras:**
- Classe final com construtor privado (utility class)
- Separar grupos por comentários `// ================`
- Cada constante deve ter Javadoc com o nome completo da chave
- Nome da constante deve seguir o padrão `CAMEL_CASE` derivado do caminho da propriedade
- **Todas** as chaves usadas no `ConfigurationProvider` devem ter uma constante correspondente

### 3. Integração com ConfigurationProvider

O `ConfigurationProvider` deve:
1. Receber `[Módulo]Properties` via construtor
2. Usar apenas constantes de `[Módulo]PropertiesConstants` em:
   - `ALLOWED_CHAVES`
   - `validar()` (switch/case)
   - `aplicar()` (switch/case)
   - `buildConfigurations()` (método `add()`)
   - `createProperties()` (persistência)
3. Nunca usar strings literais diretamente

**Exemplo de uso em `aplicar()`:**

```java
case [Módulo]PropertiesConstants.GRUPO_CAMPO ->
    [modulo]Properties.getGrupo().setCampo(valor);
```

**Exemplo de uso em `buildConfigurations()`:**

```java
add(configs, [Módulo]PropertiesConstants.GRUPO_CAMPO,
    [modulo]Properties.getGrupo().getCampo(),
    TipoConfiguracao.STRING, "Grupo", "Descrição amigável");
```

### 4. Integração com i18n

Cada chave de `[Módulo]PropertiesConstants` deve ter tradução correspondente no i18n do `service-model`:

**Arquivo:** `[módulo]-service-model/src/main/resources/i18n/translations_[modulo]_service_model_pt_BR.properties`

```properties
# ============================================================
# CONFIGURAÇÕES DO MÓDULO [MÓDULO]
# ============================================================
namespace.grupo.campo=Descrição amigável do campo
namespace.grupo.flag=Habilitar funcionalidade X
```

## Exemplo Prático: Módulo Quartz

### QuartzProperties.java

```java
@ConfigurationProperties(prefix = "ia-core.quartz")
public class QuartzProperties {
    private SchedulerConfig scheduler = new SchedulerConfig();
    private ThreadPoolConfig threadPool = new ThreadPoolConfig();
    private JobStoreConfig jobStore = new JobStoreConfig();
    // ... nested configs com defaults
}
```

### QuartzPropertiesConstants.java

```java
public final class QuartzPropertiesConstants {
    public static final String SCHEDULER_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
    public static final String THREAD_POOL_THREAD_COUNT = "org.quartz.threadPool.threadCount";
    public static final String JOB_STORE_CLASS = "org.quartz.jobStore.class";
    // ... demais chaves
}
```

### Uso no QuartzConfigurationProvider

```java
@Component
@EnableConfigurationProperties(QuartzProperties.class)
public class QuartzConfigurationProvider implements ConfigurationProvider {

    private final QuartzProperties quartzProperties;

    public QuartzConfigurationProvider(QuartzProperties quartzProperties) {
        this.quartzProperties = quartzProperties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public void aplicar(ConfiguracaoSistemaDTO<?> config) {
        switch (config.getChave()) {
            case QuartzPropertiesConstants.THREAD_POOL_THREAD_COUNT ->
                quartzProperties.getThreadPool().setThreadCount(Integer.parseInt(valor));
            // ...
        }
    }
}
```

## Consequências

### Positivas

- ✅ Propriedades tipadas e com defaults saneados
- ✅ Alinhamento com namespaces nativos do Spring Boot
- ✅ Eliminação de magic strings via constantes centralizadas
- ✅ Refatoração segura (IDE detecta uso de constantes)
- ✅ i18n automático das configurações para a UI
- ✅ Uma única fonte YAML alimenta nativo + provider

### Negativas

- ❌ Duplicação controlada: `[Módulo]Properties` + `[Módulo]PropertiesConstants`
- ❌ Necessidade de manter constantes e propriedades sincronizadas

## Migração de Propriedades Existentes

Quando um módulo já possui configuração via `@Value` ou properties legadas:

1. Criar `[Módulo]Properties` com os mesmos defaults
2. Migrar os `@Value` para injeção de `[Módulo]Properties`
3. Criar `[Módulo]PropertiesConstants` com as chaves correspondentes
4. Atualizar `ConfigurationProvider` para usar as novas constantes
5. Adicionar traduções no i18n
6. Validar que o YAML existente continua funcionando

## Data

2026-07-22

## Revisores

- Team Lead
- Architect

## Referências

1. **Spring Boot Externalized Configuration** - Spring Docs
   - URL: https://docs.spring.io/spring-boot/reference/features/external-config.html

2. **ADR-060**: Configuração Extensível Modular

## ADRs Relacionados

- ADR-060: Configuração Extensível Modular
- ADR-061: Padronização de ConfigurationProvider / ConfigurationRegistry
- ADR-040: CAMPOS nested class pattern
