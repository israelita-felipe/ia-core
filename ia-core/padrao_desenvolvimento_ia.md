# Padrão de Desenvolvimento IA - ia-core

Este documento define os padrões, convenções e melhores práticas para desenvolvimento de funcionalidades de Inteligência Artificial nos projetos ia-core e Biblia, baseado na arquitetura atual e nas referências do Spring AI.

## 📋 Índice

1. [Arquitetura de Camadas](#arquitetura-de-camadas)
2. [Padrões de Tool Calling](#padrões-de-tool-calling)
3. [Implementação de Agentes](#implementação-de-agentes)
4. [Configuração e Propriedades](#configuração-e-propriedades)
5. [Organização de Código](#organização-de-código)
6. [Testes e Validação](#testes-e-validação)
7. [Documentação](#documentação)
8. [Referências Externas](#referências-externas)

---

## Arquitetura de Camadas

### Estrutura de 4 Camadas

O projeto ia-core segue rigorosamente uma arquitetura de 4 camadas para todos os módulos de domínio:

```
ia-core-llm-model          → Camada de Modelo (Entidades, DTOs)
ia-core-llm-service       → Camada de Serviço (Lógica de Negócio)
ia-core-llm-service-model → Camada de Modelo de Serviço (DTOs de Serviço)
ia-core-llm-view          → Camada de View (Interface Vaadin)
ia-core-llm-rest          → Camada REST (API HTTP)
```

### Responsabilidades por Camada

**Diagrama de Camadas e Fluxos:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           CLIENTE                                        │
│                    (Browser / API Client)                                │
└───────────────────────────┬─────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        CAMADA VIEW (Vaadin)                               │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaManager (Componente)                                       │   │
│  │  - cadastrarPessoa(PessoaDTO) → Tool automática                  │   │
│  │  - listarPessoasMaioresDe18Anos() → Tool automática              │   │
│  │  - buscarPessoasPorNome(String) → Tool automática                │   │
│  │  - Chama API REST via WebClient/RestClient                       │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│                              │                                            │
│                              ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaFormView (UI)                                              │   │
│  │  - Formulário de cadastro/edição                                 │   │
│  │  - Grid de listagem                                              │   │
│  │  - Integração com AI Agent via ChatClient                        │   │
│  │  - Atualiza view com resultado da API REST                       │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│                              │                                            │
│                              ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  AgentOrchestratorService (IA Agent - View)                      │   │
│  │  - orchestrarAgente(Long, String)                                │   │
│  │  - Descobre tools do PessoaManager                               │   │
│  │  - Usa ChatClient para comunicação com LLM                       │   │
│  │  - Tools chamam API REST                                          │   │
│  └──────────────────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────────────────┘
                            │ HTTP Request
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                       CAMADA REST (Spring MVC)                            │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaController                                                │   │
│  │  - POST /api/v1/pessoas                                           │   │
│  │  - GET /api/v1/pessoas                                            │   │
│  │  - GET /api/v1/pessoas/{id}                                       │   │
│  │  - PUT /api/v1/pessoas/{id}                                       │   │
│  │  - DELETE /api/v1/pessoas/{id}                                    │   │
│  │  - POST /api/v1/pessoas/chat (AI Agent endpoint)                  │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│                              │                                            │
│                              ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  AgentOrchestratorService (IA Agent - REST)                       │   │
│  │  - orchestrarAgente(Long, String)                                │   │
│  │  - Descobre tools do PessoaService                               │   │
│  │  - Usa ChatClient para comunicação com LLM                       │   │
│  │  - Tools chamam PessoaService diretamente                        │   │
│  └──────────────────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                     CAMADA SERVICE (Lógica de Negócio)                    │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaService (Service)                                         │   │
│  │  - criar(PessoaDTO) → Tool automática                            │   │
│  │  - listarTodas() → Tool automática                               │   │
│  │  - buscarPorId(Long) → Tool automática                           │   │
│  │  - atualizar(Long, PessoaDTO) → Tool automática                  │   │
│  │  - deletar(Long) → Tool automática                              │   │
│  │  - buscarPorCriterios(String, Integer) → Tool automática         │   │
│  └──────────────────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                  CAMADA SERVICE-MODEL (DTOs e Mappers)                    │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaDTO                                                       │   │
│  │  - id, nome, email, dataNascimento, cpf                         │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaMapper (MapStruct)                                        │   │
│  │  - toEntity(PessoaDTO)                                           │   │
│  │  - toDTO(Pessoa)                                                  │   │
│  └──────────────────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                     CAMADA MODEL (Entidades JPA)                          │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  Pessoa (Entity)                                                 │   │
│  │  - @Entity, @Table                                              │   │
│  │  - id, nome, email, dataNascimento, cpf                         │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │  PessoaRepository (JPA)                                         │   │
│  │  - JpaRepository<Pessoa, Long>                                   │   │
│  │  - Métodos de busca customizados                                 │   │
│  └──────────────────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         BANCO DE DADOS                                    │
│                    (PostgreSQL / H2)                                      │
└─────────────────────────────────────────────────────────────────────────┘

FLUXO DE DESCOBERTA DE TOOLS:
┌─────────────────────────────────────────────────────────────────────────┐
│  FerramentaDiscoveryService                                             │
│  1. Escaneia pacotes configurados (service, view)                       │
│  2. Busca classes *Service e *Manager                                  │
│  3. Identifica métodos públicos                                        │
│  4. Usa Javadoc como descrição da tool                                  │
│  5. Usa @ToolParam para descrição de parâmetros                        │
│  6. Registra tools na tabela Ferramenta                                │
│  7. Tools ficam disponíveis para AgentOrchestratorService              │
└─────────────────────────────────────────────────────────────────────────┘

FLUXO DE EXECUÇÃO - CAMADA VIEW:
┌─────────────────────────────────────────────────────────────────────────┐
│  1. Usuário digita entrada textual na View                              │
│  2. AgentOrchestratorService (View) recebe entrada                       │
│  3. ChatClient envia prompt ao LLM com lista de tools do PessoaManager  │
│  4. LLM decide qual tool chamar e com quais parâmetros                  │
│  5. Tool do PessoaManager é executada                                   │
│  6. Tool dispara requisição HTTP para API REST                          │
│  7. API REST recebe requisição (sem intervenção de agente)              │
│  8. PessoaController executa endpoint diretamente                       │
│  9. PessoaService executa lógica de negócio                             │
│  10. PessoaRepository acessa banco de dados                              │
│  11. Resultado retorna: Banco → Service → Controller → API REST       │
│  12. PessoaManager captura resultado da API REST                        │
│  13. PessoaManager atualiza View conforme necessário                    │
│  14. Resultado é retornado ao LLM                                        │
│  15. LLM processa resultado e gera resposta final                       │
│  16. Resposta é enviada ao usuário via View                              │
└─────────────────────────────────────────────────────────────────────────┘

FLUXO DE EXECUÇÃO - CAMADA REST:
┌─────────────────────────────────────────────────────────────────────────┐
│  1. Cliente envia entrada textual via API REST                          │
│  2. AgentOrchestratorService (REST) recebe entrada                       │
│  3. ChatClient envia prompt ao LLM com lista de tools do PessoaService  │
│  4. LLM decide qual tool chamar e com quais parâmetros                  │
│  5. Tool do PessoaService é executada diretamente                      │
│  6. PessoaService executa lógica de negócio                             │
│  7. PessoaRepository acessa banco de dados                              │
│  8. Resultado retorna: Banco → Service → API REST                       │
│  9. Resultado é retornado ao LLM                                        │
│  10. LLM processa resultado e gera resposta final                       │
│  11. Resposta é enviada ao cliente via API REST                          │
└─────────────────────────────────────────────────────────────────────────┘

ARQUITETURA INDEPENDENTE:
┌─────────────────────────────────────────────────────────────────────────┐
│  • API REST pode ser hospedada com agente sem hospedar a camada View     │
│  • Camada View pode ser hospedada com seus próprios agentes              │
│  • View chama API REST via HTTP (WebClient/RestClient)                   │
│  • API REST executa lógica de negócio diretamente (sem agente)           │
│  • Cada camada tem seu próprio AgentOrchestratorService                  │
└─────────────────────────────────────────────────────────────────────────┘
```

**Camada Model (`ia-core-llm-model`)**
- Entidades JPA com anotações `@Entity`
- Enums e tipos de domínio
- Interfaces de repositório
- Mapeamentos de banco de dados

**Camada Service (`ia-core-llm-service`)**
- Lógica de negócio e orquestração
- Implementação de tools Spring AI com `@Tool`
- Serviços de descoberta automática de ferramentas
- Integração com LLMs e agentes
- Gerenciamento de transações

**Camada Service Model (`ia-core-llm-service-model`)**
- DTOs para comunicação entre camadas
- Mappers MapStruct
- Tradutores i18n
- Use Cases e Request/Response objects

**Camada View (`ia-core-llm-view`)**
- Componentes Vaadin MVVM
- Tools específicas da interface
- Managers CRUD
- Layouts e navegação

**Camada REST (`ia-core-llm-rest`)**
- Controllers REST
- Endpoints para integração externa
- Tools para acesso via API
- Implementação de protocolos (A2A, MCP)

---

## Padrões de Tool Calling

### Regra Geral: *Service e *Manager como Tools

No ia-core, **todos os métodos públicos de classes *Service e *Manager são considerados tools por padrão**. Isso significa que qualquer método público nessas classes pode ser automaticamente descoberto e exposto como tool para o modelo de IA.

**Classes *Service (Camada Service):**
- Todos os métodos públicos são automaticamente tools
- Implementam lógica de negócio e orquestração
- São descobertos pelo FerramentaDiscoveryService
- Devem ter Javadoc descrevendo o propósito para o modelo

**Classes *Manager (Camada View):**
- Todos os métodos públicos são automaticamente tools
- Implementam operações funcionais da interface
- São descobertos pelo FerramentaDiscoveryService
- Devem ter Javadoc descrevendo o propósito para o modelo

**Exemplo - PessoaService (ia-core-service):**
```java
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    /**
     * Cria uma nova pessoa no sistema.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param dto DTO com dados da pessoa a ser criada
     * @return Pessoa criada com ID gerado
     */
    public PessoaDTO criar(PessoaDTO dto) {
        Pessoa entity = mapper.toEntity(dto);
        entity = pessoaRepository.save(entity);
        return mapper.toDTO(entity);
    }

    /**
     * Lista todas as pessoas do sistema.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @return Lista de todas as pessoas
     */
    public List<PessoaDTO> listarTodas() {
        return pessoaRepository.findAll().stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Busca pessoas por critérios específicos.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param nome Nome para busca (opcional)
     * @param idadeMinima Idade mínima (opcional)
     * @return Lista de pessoas que atendem aos critérios
     */
    public List<PessoaDTO> buscarPorCriterios(
        @ToolParam(description = "Nome para busca (opcional)", required = false) String nome,
        @ToolParam(description = "Idade mínima (opcional)", required = false) Integer idadeMinima) {

        Specification<Pessoa> spec = Specification.where(null);

        if (nome != null && !nome.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }

        if (idadeMinima != null) {
            LocalDate dataNascimentoMin = LocalDate.now().minusYears(idadeMinima);
            spec = spec.and((root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("dataNascimento"), dataNascimentoMin));
        }

        return pessoaRepository.findAll(spec).stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }
}
```

**Exemplo - PessoaManager (ia-core-view):**
```java
@Component
@RequiredArgsConstructor
public class PessoaManager {

    private final PessoaService pessoaService;

    /**
     * Cadastra uma nova pessoa através da interface Vaadin.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param dto DTO com dados da pessoa
     * @return Pessoa criada
     */
    public PessoaDTO cadastrarPessoa(PessoaDTO dto) {
        return pessoaService.criar(dto);
    }

    /**
     * Lista todas as pessoas maiores de 18 anos.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @return Lista de pessoas maiores de 18 anos
     */
    public List<PessoaDTO> listarPessoasMaioresDe18Anos() {
        return pessoaService.buscarPorCriterios(null, 18);
    }

    /**
     * Busca pessoas por nome.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param nome Nome para busca
     * @return Lista de pessoas com o nome informado
     */
    public List<PessoaDTO> buscarPessoasPorNome(String nome) {
        return pessoaService.buscarPorCriterios(nome, null);
    }
}
```

### Definição de Tools

As tools devem ser definidas usando a anotação `@Tool` do Spring AI. Baseado na documentação oficial do Spring AI, a anotação `@Tool` permite transformar métodos em ferramentas que podem ser chamadas pelo modelo de IA.

**Exemplo Básico (ia-core-llm-service):**
```java
@Component
public class LlmToolCatalog {

  @Tool(name = "llm_echo",
        description = "Ferramenta de eco para validação de funcionalidade de tool calling. " +
                     "Recebe um texto como entrada e retorna o mesmo texto como saída. " +
                     "Útil para testar se o orquestrador consegue invocar ferramentas corretamente.")
  public String echo(@ToolParam(description = "Texto de entrada a ser ecoado. Pode ser qualquer string.") String text) {
    return text == null ? "" : text;
  }
}
```

**Exemplo com Múltiplos Parâmetros (ia-core-llm-service):**
```java
@Component
public class DatabaseToolCatalog {

  @Tool(name = "query_database",
        description = "Executa uma query SQL no banco de dados e retorna os resultados. " +
                     "Suporta queries SELECT básicas com filtros.")
  public List<Map<String, Object>> queryDatabase(
      @ToolParam(description = "Query SQL a ser executada (apenas SELECT)") String query,
      @ToolParam(description = "Parâmetros para a query (opcional)", required = false) Map<String, Object> params) {
    // Implementação usando JPA ou JDBC
    return jdbcTemplate.queryForList(query, params);
  }
}
```

**Exemplo de Tool de View (ia-core-llm-view):**
```java
@Component
public class ViewToolCatalog {

  @Tool(name = "show_notification",
        description = "Exibe uma notificação na interface do usuário. " +
                     "Útil para informar o usuário sobre eventos importantes.")
  public String showNotification(
      @ToolParam(description = "Mensagem da notificação") String mensagem,
      @ToolParam(description = "Tipo: success, error, warning, info", required = false) String tipo) {
    Notification notification = Notification.show(mensagem);
    if (tipo != null) {
      switch (tipo.toLowerCase()) {
        case "success":
          notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
          break;
        case "error":
          notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
          break;
        case "warning":
          notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
          break;
        default:
          notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
      }
    }
    return "Notificação exibida: " + mensagem;
  }
}
```

### Padrões de Nomenclatura

- **Nome da tool**: `snake_case`, descritivo e único
  - Exemplo: `llm_echo`, `query_database`, `show_notification`
  - Deve ser único entre todas as tools disponíveis para o modelo
  - Se não fornecido, usa o nome do método

- **Descrição**: Clara e concisa, explicando o propósito e parâmetros
  - Deve ser detalhada para o modelo entender quando e como usar a tool
  - Descrições pobres podem levar ao modelo não usar a tool ou usá-la incorretamente
  - Inclui contexto de uso e exemplos quando possível

- **Parâmetros**: Nomes descritivos com `@ToolParam`
  - Todos os parâmetros são considerados required por padrão
  - Use `required = false` para parâmetros opcionais
  - Forneça descrição clara para cada parâmetro

### Propriedades Avançadas do @Tool

Baseado na documentação do Spring AI, a anotação `@Tool` suporta as seguintes propriedades:

```java
@Tool(
    name = "custom_tool_name",           // Nome único da tool
    description = "Descrição detalhada", // Descrição para o modelo
    returnDirect = false,                // Se true, retorna direto ao cliente
    resultConverter = CustomConverter.class // Converter para resultado
)
public String myTool(@ToolParam String param) {
    // Implementação
}
```

**returnDirect**:
- `true`: O resultado é retornado diretamente ao cliente sem passar pelo modelo
- `false` (padrão): O resultado é passado de volta ao modelo para processamento adicional

**resultConverter**:
- Implementação de `ToolCallResultConverter` para converter o resultado para String
- Útil quando o método retorna tipos complexos que precisam de serialização especial

### Separação por Camada

**Tools de Serviço** (`com.ia.core.llm.service.tool`)
- Operações de negócio
- Integrações externas
- Consultas de banco de dados
- Processamento de dados

**Exemplo Prático - ia-core-llm-service:**
```java
@Component
public class BusinessToolCatalog {

  @Autowired
  private AgenteService agenteService;

  @Tool(name = "listar_agentes_ativos",
        description = "Lista todos os agentes ativos no sistema. " +
                     "Retorna uma lista com nome, descrição e modelo de cada agente.")
  public List<AgenteDTO> listarAgentesAtivos() {
    return agenteService.listarAtivos();
  }

  @Tool(name = "criar_agente",
        description = "Cria um novo agente especialista com as configurações fornecidas. " +
                     "O agente será salvo no banco de dados e estará disponível para uso.")
  public AgenteDTO criarAgente(
      @ToolParam(description = "Nome do agente") String nome,
      @ToolParam(description = "Descrição das capacidades do agente") String descricao,
      @ToolParam(description = "Modelo de IA a ser usado (ex: llama3.2-vision)") String modelo) {
    AgenteDTO dto = AgenteDTO.builder()
        .nome(nome)
        .descricao(descricao)
        .modelo(modelo)
        .ativo(true)
        .build();
    return agenteService.criar(dto);
  }
}
```

**Tools de View** (`com.ia.core.llm.view.tool`)
- Interações com UI Vaadin
- Navegação entre views
- Atualização de componentes
- Interações com usuário

**Exemplo Prático - ia-core-llm-view:**
```java
@Component
public class ViewToolCatalog {

  @Tool(name = "show_notification",
        description = "Exibe uma notificação na interface do usuário. " +
                     "Útil para informar o usuário sobre eventos importantes.")
  public String showNotification(
      @ToolParam(description = "Mensagem da notificação") String mensagem,
      @ToolParam(description = "Tipo: success, error, warning, info", required = false) String tipo) {
    Notification notification = Notification.show(mensagem);
    if (tipo != null) {
      switch (tipo.toLowerCase()) {
        case "success":
          notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
          break;
        case "error":
          notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
          break;
        case "warning":
          notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
          break;
        default:
          notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
      }
    }
    return "Notificação exibida: " + mensagem;
  }

  @Tool(name = "get_current_view",
        description = "Retorna o nome da view atualmente exibida na interface.")
  public String getCurrentView() {
    return UI.getCurrent().getClass().getSimpleName();
  }
}
```

**Tools REST** (`com.ia.core.llm.rest.web`)
- Endpoints HTTP
- Integração via API
- Protocolos externos (A2A, MCP)

**Exemplo Prático - ia-core-llm-rest:**
```java
@Service
public class RestToolCatalog {

  @Autowired
  private WebSearchTool webSearchTool;

  @Tool(name = "web_search",
        description = "Realiza uma busca na internet usando a API do Brave Search. " +
                     "Retorna os resultados mais relevantes para a query fornecida.")
  public String webSearch(
      @ToolParam(description = "Termo de busca a ser pesquisado na internet") String query) {
    return webSearchTool.searchWeb(query);
  }
}
```

### Descoberta Automática de Tools

O `FerramentaDiscoveryService` descobre automaticamente tools anotadas com `@Tool`:

```yaml
ia-core:
  llm:
    ferramenta:
      discovery:
        enabled: true
        scan-packages:
          - com.ia.core.llm.service.tool
          - com.ia.core.llm.view.tool
        refresh-on-startup: true
```

**Implementação do FerramentaDiscoveryService (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class FerramentaDiscoveryService {

  private final FerramentaRepository ferramentaRepository;
  private final LlmModuleProperties llmModuleProperties;
  private final ApplicationContext applicationContext;

  @PostConstruct
  public void onStartup() {
    if (llmModuleProperties.getFerramenta().getDiscovery().isRefreshOnStartup()) {
      syncFromDiscovery();
    }
  }

  @TransactionalWrite
  public void syncFromDiscovery() {
    if (!llmModuleProperties.getFerramenta().getDiscovery().isEnabled()) {
      return;
    }

    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      Class<?> type = applicationContext.getType(beanName);
      if (type == null || !isInScanPackages(type)) {
        continue;
      }

      for (Method method : ReflectionUtils.getAllDeclaredMethods(type)) {
        Tool tool = method.getAnnotation(Tool.class);
        if (tool != null) {
          String id = tool.name().isBlank() ? type.getSimpleName() + "." + method.getName() : tool.name();
          String desc = tool.description().isBlank() ? method.getName() : tool.description();
          upsertTool(id, desc, type.getSimpleName());
        }
      }
    }
  }

  private boolean isInScanPackages(Class<?> type) {
    String pkg = type.getPackageName();
    return llmModuleProperties.getFerramenta().getDiscovery().getScanPackages().stream()
        .anyMatch(pkg::startsWith);
  }

  private void upsertTool(String identificador, String descricao, String modulo) {
    ferramentaRepository.findByIdentificador(identificador).ifPresentOrElse(
        existing -> updateExisting(existing, identificador, descricao, modulo, TipoFerramentaEnum.TOOL_SPRING),
        () -> ferramentaRepository.save(Ferramenta.builder()
            .titulo(identificador)
            .descricao(descricao)
            .identificador(identificador)
            .moduloOrigem(modulo)
            .tipo(TipoFerramentaEnum.TOOL_SPRING)
            .ativo(true)
            .descobertaAutomatica(true)
            .build()));
  }
}
```

### ChatClient API Patterns

Baseado na documentação do Spring AI, o ChatClient API fornece uma API fluente para comunicação com modelos de chat. No ia-core, o ChatClient deve ser usado através dos serviços de orquestração.

**Uso Básico do ChatClient (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class ChatOrchestrationService {

  private final ChatClient.Builder chatClientBuilder;

  public String chatComSimples(String mensagem) {
    return chatClientBuilder.build()
        .prompt()
        .user(mensagem)
        .call()
        .content();
  }

  public String chatComTools(String mensagem, List<Tool> tools) {
    return chatClientBuilder.build()
        .prompt()
        .user(mensagem)
        .tools(tools)
        .call()
        .content();
  }
}
```

**ChatClient com Streaming (ia-core-llm-service):**
```java
public Flux<String> chatComStreaming(String mensagem) {
  return chatClientBuilder.build()
      .prompt()
      .user(mensagem)
      .stream()
      .content();
}
```

**ChatClient com Advisors (ia-core-llm-service):**
```java
public String chatComAdvisors(String mensagem, List<Advisor> advisors) {
  return chatClientBuilder.build()
      .prompt()
      .user(mensagem)
      .advisors(advisors)
      .call()
      .content();
}
```

### Advisor Patterns

Baseado na documentação do Spring AI, Advisors encapsulam padrões recorrentes de IA generativa e transformam dados enviados e recebidos dos LLMs.

**Implementação de Advisor Personalizado (ia-core-llm-service):**
```java
@Component
public class LoggingAdvisor implements CallAdvisor {

  @Override
  public String getName() {
    return "LoggingAdvisor";
  }

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest chatClientRequest,
      CallAdvisorChain callAdvisorChain) {

    log.info("ChatClientRequest: {}", chatClientRequest.getUserText());

    ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);

    log.info("ChatClientResponse: {}", response.getResult().getOutput().getContent());

    return response;
  }
}
```

**Advisor de RAG (Retrieval Augmented Generation) (ia-core-llm-service):**
```java
@Component
public class RagAdvisor implements CallAdvisor {

  private final VectorStore vectorStore;

  @Override
  public String getName() {
    return "RagAdvisor";
  }

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest chatClientRequest,
      CallAdvisorChain callAdvisorChain) {

    String userText = chatClientRequest.getUserText();

    // Busca documentos relevantes no vector store
    List<Document> documents = vectorStore.similaritySearch(
        SearchRequest.query(userText).withTopK(5));

    // Adiciona contexto ao prompt
    String context = documents.stream()
        .map(Document::getContent)
        .collect(Collectors.joining("\n\n"));

    String enhancedPrompt = "Contexto:\n" + context + "\n\nPergunta:\n" + userText;

    ChatClientRequest enhancedRequest = new ChatClientRequest(
        enhancedPrompt,
        chatClientRequest.getChatOptions(),
        chatClientRequest.getAdvisorContext()
    );

    return callAdvisorChain.nextCall(enhancedRequest);
  }
}
```

**Configuração de Advisors no ChatClient (ia-core-llm-service):**
```java
@Configuration
public class ChatClientConfig {

  @Bean
  public ChatClient.Builder chatClientBuilder(
      ChatModel chatModel,
      List<Advisor> advisors) {

    return ChatClient.builder(chatModel)
        .defaultAdvisors(advisors);
  }
}
```

### Chat Memory Patterns

Baseado na documentação do Spring AI, Chat Memory permite manter contexto entre múltiplas interações com o LLM.

**Conceito de Chat Memory:**
- LLMs são stateless (sem estado)
- Chat Memory armazena e recupera informações entre interações
- Diferença entre Chat Memory (informação contextual) e Chat History (histórico completo)
- Spring AI auto-configura ChatMemory bean por padrão

**Tipos de Memória:**

**MessageWindowChatMemory (Padrão):**
- Mantém janela de mensagens até tamanho máximo (padrão: 20 mensagens)
- Remove mensagens antigas quando excede limite
- Preserva mensagens de sistema
- Se nova mensagem de sistema é adicionada, remove anteriores

**Configuração de Chat Memory (ia-core-llm-service):**
```java
@Configuration
public class ChatMemoryConfig {

  @Bean
  public ChatMemory chatMemory() {
    return MessageWindowChatMemory.builder()
        .maxMessages(20)  // Padrão: 20 mensagens
        .build();
  }

  @Bean
  public ChatMemoryRepository chatMemoryRepository() {
    // Por padrão usa InMemoryChatMemoryRepository
    // Para produção, use JdbcChatMemoryRepository, CassandraChatMemoryRepository, etc.
    return new InMemoryChatMemoryRepository();
  }
}
```

**Uso de Chat Memory com ChatClient (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class ChatOrchestrationService {

  private final ChatClient.Builder chatClientBuilder;
  private final ChatMemory chatMemory;

  public String chatComMemoria(String mensagem, String conversationId) {
    ChatClient chatClient = chatClientBuilder
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build()
        )
        .build();

    return chatClient.prompt()
        .user(mensagem)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
  }
}
```

**Integração com AgentOrchestratorService (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class AgentOrchestratorService {

  private final ChatClient.Builder chatClientBuilder;
  private final ChatMemory chatMemory;
  private final AgenteService agenteService;
  private final AgentSessionService sessionService;

  public String orchestrarAgenteComMemoria(Long agenteId, String mensagem, String conversationId) {
    AgenteDTO agente = agenteService.buscarPorId(agenteId);

    ChatClient chatClient = chatClientBuilder
        .defaultSystem(agente.getSystemPrompt())
        .defaultInstructions(agente.getInstructions())
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build()
        )
        .build();

    List<Tool> tools = carregarToolsDoAgente(agente);

    String resposta = chatClient.prompt()
        .user(mensagem)
        .tools(tools)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();

    sessionService.adicionarInteracao(conversationId, mensagem, resposta);

    return resposta;
  }
}
```

**Repositório de Memória (JdbcChatMemoryRepository):**

**JdbcChatMemoryRepository (Produção):**
- Armazena mensagens em banco de dados relacional
- Persiste entre reinicializações
- Requer configuração de datasource
- Recomendado para produção

```java
@Bean
public ChatMemoryRepository jdbcChatMemoryRepository(DataSource dataSource) {
    return JdbcChatMemoryRepository.builder(dataSource)
        .chatMemoryTable("chat_memory")  // Nome da tabela
        .build();
}
```

**Configuração de Chat Memory (application-llm-service.yml):**
```yaml
spring:
  ai:
    chat:
      memory:
        enabled: true
        max-messages: 20
```

**Uso em AgentSessionService (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class AgentSessionService {

  private final ChatMemory chatMemory;
  private final AgentSessionRepository sessionRepository;

  public String criarSessao(Long agenteId) {
    String conversationId = UUID.randomUUID().toString();

    AgentSession sessao = AgentSession.builder()
        .agente(agenteService.buscarEntidadePorId(agenteId))
        .conversationId(conversationId)
        .createdAt(new Date())
        .build();

    sessionRepository.save(sessao);
    return conversationId;
  }

  public void adicionarInteracao(String conversationId, String mensagemUsuario, String resposta) {
    // ChatMemory gerencia automaticamente as mensagens
    // Este método é opcional para histórico completo
  }

  public List<Message> getHistoricoConversa(String conversationId) {
    return chatMemory.get(conversationId, Integer.MAX_VALUE);
  }
}
```

**Gerenciamento de Chat Memory na Camada View (ia-core-llm-view - In-Memory):**

**ViewChatMemoryManager:**
```java
package com.ia.core.llm.view.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerenciador de chat memory específico para a camada View.
 * <p>
 * Gerencia sessões de conversa na interface Vaadin usando memória local (in-memory),
 * permitindo que o usuário mantenha contexto entre interações na view sem persistência em banco.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ViewChatMemoryManager {

  private final ChatClient.Builder chatClientBuilder;
  private final Map<String, ChatMemory> sessionMemories = new ConcurrentHashMap<>();

  /**
   * Cria uma nova sessão de conversa para a view com chat memory in-memory.
   *
   * @return Conversation ID único
   */
  public String criarSessaoView() {
    String conversationId = UUID.randomUUID().toString();
    ChatMemory chatMemory = MessageWindowChatMemory.builder()
        .maxMessages(50)
        .build();
    sessionMemories.put(conversationId, chatMemory);
    return conversationId;
  }

  /**
   * Envia mensagem para o agente com contexto de memória da view (in-memory).
   *
   * @param mensagem Mensagem do usuário
   * @param conversationId ID da conversa
   * @return Resposta do agente
   */
  public String chatComMemoriaView(String mensagem, String conversationId) {
    ChatMemory chatMemory = sessionMemories.get(conversationId);
    if (chatMemory == null) {
      throw new IllegalArgumentException("Sessão não encontrada: " + conversationId);
    }

    ChatClient chatClient = chatClientBuilder
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build()
        )
        .build();

    return chatClient.prompt()
        .user(mensagem)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
  }

  /**
   * Limpa o histórico de uma conversa específica da view.
   *
   * @param conversationId ID da conversa
   */
  public void limparConversaView(String conversationId) {
    ChatMemory chatMemory = sessionMemories.get(conversationId);
    if (chatMemory != null) {
      chatMemory.clear(conversationId);
    }
  }

  /**
   * Remove completamente uma sessão de conversa da memória.
   *
   * @param conversationId ID da conversa
   */
  public void removerSessaoView(String conversationId) {
    sessionMemories.remove(conversationId);
  }

  /**
   * Obtém o histórico de uma conversa da view.
   *
   * @param conversationId ID da conversa
   * @return Lista de mensagens
   */
  public List<org.springframework.ai.chat.messages.Message> getHistoricoView(String conversationId) {
    ChatMemory chatMemory = sessionMemories.get(conversationId);
    if (chatMemory == null) {
      return List.of();
    }
    return chatMemory.get(conversationId, Integer.MAX_VALUE);
  }
}
```

**Integração com PessoaFormView:**
```java
@Component
@Route("pessoas")
@RequiredArgsConstructor
public class PessoaFormView extends VerticalLayout {

  private final PessoaManager pessoaManager;
  private final AgentOrchestratorService agentOrchestratorService;
  private final ViewChatMemoryManager viewChatMemoryManager;

  private String conversationId;
  private final Grid<PessoaDTO> grid = new Grid<>(PessoaDTO.class);
  private final Binder<PessoaDTO> binder = new Binder<>(PessoaDTO.class);

  public PessoaFormView() {
    setSizeFull();

    // Inicializa sessão de conversa
    conversationId = viewChatMemoryManager.criarSessaoView();

    H2 title = new H2("Gerenciamento de Pessoas");
    add(title);

    // Formulário
    FormLayout formLayout = new FormLayout();

    TextField nomeField = new TextField("Nome");
    EmailField emailField = new EmailField("Email");
    TextField cpfField = new TextField("CPF");

    formLayout.add(nomeField, emailField, cpfField);
    add(formLayout);

    // Botões
    Button btnSalvar = new Button("Salvar", e -> salvarPessoa());
    Button btnListar = new Button("Listar Todas", e -> listarPessoas());
    Button btnMaiores18 = new Button("Maiores de 18", e -> listarMaiores18());
    Button btnChat = new Button("Chat com IA", e -> abrirChat());
    Button btnLimparChat = new Button("Limpar Chat", e -> limparChat());

    add(btnSalvar, btnListar, btnMaiores18, btnChat, btnLimparChat);

    // Grid
    grid.setSizeFull();
    add(grid);

    // Binder
    binder.forField(nomeField).asRequired("Nome é obrigatório").bind(PessoaDTO::getNome, PessoaDTO::setNome);
    binder.forField(emailField).asRequired("Email é obrigatório").bind(PessoaDTO::getEmail, PessoaDTO::setEmail);
    binder.forField(cpfField).asRequired("CPF é obrigatório").bind(PessoaDTO::getCpf, PessoaDTO::setCpf);

    listarPessoas();
  }

  private void salvarPessoa() {
    PessoaDTO dto = new PessoaDTO();
    if (binder.writeBeanIfValid(dto)) {
      PessoaDTO salva = pessoaManager.cadastrarPessoa(dto);
      Notification.show("Pessoa salva: " + salva.getNome());
      listarPessoas();
    }
  }

  private void listarPessoas() {
    grid.setItems(pessoaManager.listarTodasPessoas());
  }

  private void listarMaiores18() {
    grid.setItems(pessoaManager.listarPessoasMaioresDe18Anos());
  }

  private void abrirChat() {
    Dialog dialog = new Dialog();
    dialog.setWidth("600px");

    VerticalLayout layout = new VerticalLayout();
    TextArea chatArea = new TextArea("Histórico da Conversa");
    chatArea.setReadOnly(true);
    chatArea.setWidthFull();
    chatArea.setHeight("200px");

    TextField mensagemField = new TextField("Mensagem");
    Button btnEnviar = new Button("Enviar", e -> enviarMensagem(mensagemField.getValue(), chatArea));

    layout.add(chatArea, mensagemField, btnEnviar);
    dialog.add(layout);
    dialog.open();

    // Carrega histórico inicial
    atualizarHistoricoChat(chatArea);
  }

  private void enviarMensagem(String mensagem, TextArea chatArea) {
    String resposta = viewChatMemoryManager.chatComMemoriaView(mensagem, conversationId);
    Notification.show("IA: " + resposta);
    atualizarHistoricoChat(chatArea);
  }

  private void limparChat() {
    viewChatMemoryManager.limparConversaView(conversationId);
    conversationId = viewChatMemoryManager.criarSessaoView();
    Notification.show("Chat limpo. Nova sessão iniciada.");
  }

  private void atualizarHistoricoChat(TextArea chatArea) {
    List<org.springframework.ai.chat.messages.Message> historico =
        viewChatMemoryManager.getHistoricoView(conversationId);

    StringBuilder sb = new StringBuilder();
    for (org.springframework.ai.chat.messages.Message msg : historico) {
      sb.append(msg.getMessageType()).append(": ").append(msg.getContent()).append("\n");
    }
    chatArea.setValue(sb.toString());
  }
}
```

**Configuração de Chat Memory na View:**

A camada view usa chat memory in-memory (MessageWindowChatMemory) sem necessidade de configuração YAML. O ViewChatMemoryManager gerencia sessões de conversa localmente usando `Map<String, ChatMemory>`.

### Tipos de Ferramentas

A entidade `Ferramenta` suporta múltiplos tipos:

- `AGENTE_ESPECIALISTA`: Sub-agentes especializados
- `TOOL_SPRING`: Tools Spring AI com `@Tool`
- `RECURSO_MCP`: Recursos do Model Context Protocol

---

## Implementação de Agentes

### Sistema de Agentes

O projeto implementa um sistema completo de agentes com:

**AgentOrchestratorService**
- Orquestração de múltiplos agentes
- Gerenciamento de sessões
- Roteamento multi-modelo
- Suporte a subagentes

**AgentSessionService**
- Gerenciamento de sessões de conversação
- Contexto por sessão
- Histórico de interações

**AgenteService**
- CRUD de agentes
- Configuração de modelos
- Definição de skills

### Configuração de Agentes

```yaml
ia-core:
  llm:
    agent:
      orchestrator-id: ia-core-orchestrator
      tool-scan-packages:
        - com.ia.core.llm.service.tool
      enabled: true
      max-sub-agent-turns: 10
      subagent-resolver:
        enabled: true
        type: database
      built-in-tools:
        web-search:
          enabled: true
      a2a:
        enabled: true
        server-url: ${A2A_SERVER_URL:http://localhost:8080}
        agent-id: ia-core-llm-agent
      multi-model:
        enabled: true
        default-model: ${OLLAMA_CHAT_MODEL:llama3.2-vision}
```

### Padrões de Implementação

**Agente Especialista (ia-core-llm-model):**
```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String modelo;
    private TipoAgenteEnum tipo;
    private List<String> skills;
    private boolean ativo;

    @Column(columnDefinition = "text")
    private String systemPrompt;

    @Column(columnDefinition = "text")
    private String instructions;

    @Builder.Default
    private Integer maxSubAgentTurns = 10;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
```

**Tool de Agente (ia-core-llm-service):**
```java
@Component
public class AgenteToolCatalog {

  @Autowired
  private AgenteService agenteService;

  @Tool(name = "executar_tarefa_agente",
        description = "Executa uma tarefa específica usando um agente especialista. " +
                     "O agente é selecionado baseado na tarefa solicitada.")
  public String executarTarefaAgente(
      @ToolParam(description = "ID do agente a ser usado") Long agenteId,
      @ToolParam(description = "Descrição da tarefa a ser executada") String tarefa) {

    AgenteDTO agente = agenteService.buscarPorId(agenteId);
    // Implementação de execução da tarefa pelo agente
    return "Tarefa executada pelo agente: " + agente.getNome();
  }

  @Tool(name = "listar_skills_agente",
        description = "Lista todas as skills disponíveis para um agente específico.")
  public List<String> listarSkillsAgente(
      @ToolParam(description = "ID do agente") Long agenteId) {

    AgenteDTO agente = agenteService.buscarPorId(agenteId);
    return agente.getSkills();
  }
}
```

### Integração AgentOrchestratorService

**Implementação do AgentOrchestratorService (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class AgentOrchestratorService {

  private final ChatClient.Builder chatClientBuilder;
  private final AgenteService agenteService;
  private final AgentSessionService sessionService;

  @Value("${ia-core.llm.agent.max-sub-agent-turns:10}")
  private int maxSubAgentTurns;

  public String orchestrarAgente(Long agenteId, String mensagemUsuario) {
    AgenteDTO agente = agenteService.buscarPorId(agenteId);

    // Cria ou recupera sessão
    AgentSessionDTO sessao = sessionService.criarOuRecuperarSessao(agenteId);

    // Configura ChatClient com system prompt do agente
    ChatClient chatClient = chatClientBuilder
        .defaultSystem(agente.getSystemPrompt())
        .defaultInstructions(agente.getInstructions())
        .build();

    // Executa orquestração com tools do agente
    List<Tool> tools = carregarToolsDoAgente(agente);

    String resposta = chatClient.prompt()
        .user(mensagemUsuario)
        .tools(tools)
        .call()
        .content();

    // Salva interação na sessão
    sessionService.adicionarInteracao(sessao.getId(), mensagemUsuario, resposta);

    return resposta;
  }

  private List<Tool> carregarToolsDoAgente(AgenteDTO agente) {
    // Carrega tools baseadas nas skills do agente
    // Implementação depende das skills configuradas
    return List.of();
  }
}
```

### Integração AgentSessionService

**Implementação do AgentSessionService (ia-core-llm-service):**
```java
@Service
@RequiredArgsConstructor
public class AgentSessionService {

  private final AgentSessionRepository sessionRepository;
  private final AgenteService agenteService;

  public AgentSessionDTO criarOuRecuperarSessao(Long agenteId) {
    // Implementação para criar ou recuperar sessão
    // Pode usar identificação de usuário ou contexto
    AgentSession sessao = AgentSession.builder()
        .agente(agenteService.buscarEntidadePorId(agenteId))
        .createdAt(new Date())
        .build();

    return sessionRepository.save(sessao).toDTO();
  }

  public void adicionarInteracao(Long sessaoId, String mensagemUsuario, String resposta) {
    AgentSession sessao = sessionRepository.findById(sessaoId)
        .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

    Interacao interacao = Interacao.builder()
        .mensagemUsuario(mensagemUsuario)
        .resposta(resposta)
        .timestamp(new Date())
        .build();

    sessao.getInteracoes().add(interacao);
    sessionRepository.save(sessao);
  }
}
```

---

## Configuração e Propriedades

### Estrutura de Configuração

As configurações devem seguir a estrutura hierárquica:

```yaml
ia-core:
  llm:
    enabled: true
    security:
      protected-paths:
        - /mcp/**
        - /.well-known/agent-card.json
    agent:
      # Configurações de agente
    ferramenta:
      # Configurações de ferramentas
    audit:
      # Configurações de auditoria
```

### Propriedades Obrigatórias

**Spring AI**
```yaml
spring:
  ai:
    model:
      chat: ollama
      embedding: ollama
    ollama:
      base-url: ${OLLAMA_BASE_URL:http://localhost:11434}
      chat:
        options:
          model: ${OLLAMA_CHAT_MODEL:llama3.2-vision}
          temperature: 0.3
```

**Brave Search** (para WebSearchTool)
```yaml
brave:
  api:
    key: ${BRAVE_API_KEY:}
```

### Versionamento de Dependências

```xml
<properties>
    <spring-ai.version>2.0.0-M1</spring-ai.version>
    <spring-ai-agent-utils.version>0.7.0</spring-ai-agent-utils.version>
</properties>
```

### Configuração de Módulos LLM

**ia-core-llm-service (pom.xml):**
```xml
<dependencies>
    <dependency>
        <groupId>com.ia</groupId>
        <artifactId>ia-core-llm-service-model</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springaicommunity</groupId>
        <artifactId>spring-ai-agent-utils</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springaicommunity</groupId>
        <artifactId>spring-ai-agent-utils-a2a</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-model-ollama</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-advisors-vector-store</artifactId>
    </dependency>
</dependencies>
```

**ia-core-llm-rest (pom.xml):**
```xml
<dependencies>
    <dependency>
        <groupId>com.ia</groupId>
        <artifactId>ia-core-llm-service</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>com.ia.core</groupId>
        <artifactId>ia-core-security-service</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>com.ia.core</groupId>
        <artifactId>ia-core-rest</artifactId>
        <version>${project.version}</version>
    </dependency>
</dependencies>
```

---

## Exemplo Fullstack: CRUD de Pessoa com IA Agent

Este exemplo demonstra a implementação completa de um CRUD de pessoa com integração de IA Agent tanto na camada REST quanto na camada View, seguindo os padrões do ia-core.

### Conceito: Use Cases e Tools

**Princípio Fundamental:** Cada tool pertence a uma classe que implementa um use case. Como o use case é implementado tanto na API quanto na View, a tool terá o mesmo comando na view ou na API e o mesmo resultado.

**Use Cases de Pessoa:**
1. **Criar Pessoa** - Cadastro de nova pessoa
2. **Listar Pessoas** - Listagem de todas as pessoas
3. **Buscar Pessoa por ID** - Busca específica por identificador
4. **Atualizar Pessoa** - Atualização de dados de pessoa existente
5. **Deletar Pessoa** - Remoção de pessoa
6. **Buscar Pessoas por Critérios** - Busca avançada com filtros

### Passo a Passo da Implementação Fullstack

#### Passo 1: Camada Model (ia-core-model)

**Entidade Pessoa:**
```java
package com.ia.core.model.pessoa;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "pessoa")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
```

**Repository:**
```java
package com.ia.core.model.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {
    Optional<Pessoa> findByEmail(String email);
    Optional<Pessoa> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
```

#### Passo 2: Camada Service-Model (ia-core-service-model)

**DTO Pessoa:**
```java
package com.ia.core.service.model.pessoa;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    private Boolean ativo = true;
}
```

**Mapper MapStruct:**
```java
package com.ia.core.service.model.pessoa;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.ia.core.model.pessoa.Pessoa;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaDTO toDTO(Pessoa entity);
    Pessoa toEntity(PessoaDTO dto);
    void updateEntityFromDTO(PessoaDTO dto, @MappingTarget Pessoa entity);
}
```

#### Passo 3: Camada Service (ia-core-service)

**PessoaService - Implementa Use Cases como Tools Automáticas:**
```java
package com.ia.core.service.pessoa;

import com.ia.core.service.model.pessoa.PessoaDTO;
import com.ia.core.service.model.pessoa.PessoaMapper;
import com.ia.core.model.pessoa.Pessoa;
import com.ia.core.model.pessoa.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de pessoas.
 * <p>
 * Todos os métodos públicos são automaticamente tools descobertas pelo FerramentaDiscoveryService.
 * Implementa os use cases de CRUD de pessoa.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    /**
     * Use Case: Criar Pessoa
     * Cria uma nova pessoa no sistema.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param dto DTO com dados da pessoa a ser criada
     * @return Pessoa criada com ID gerado
     * @throws IllegalArgumentException se email ou CPF já existirem
     */
    @Tool(description = "Cria uma nova pessoa no sistema. Recebe dados da pessoa (nome, email, data de nascimento, CPF) e retorna a pessoa criada com ID gerado.")
    @TransactionalWrite
    public PessoaDTO criar(@ToolParam(description = "DTO com dados da pessoa a ser criada (nome, email, data de nascimento, CPF)") PessoaDTO dto) {
        log.info("Criando pessoa: nome={}, email={}", dto.getNome(), dto.getEmail());

        validarEmailUnico(dto.getEmail());
        validarCpfUnico(dto.getCpf());

        Pessoa entity = pessoaMapper.toEntity(dto);
        entity.setCreatedAt(new Date());
        entity = pessoaRepository.save(entity);

        log.info("Pessoa criada com sucesso: id={}", entity.getId());
        return pessoaMapper.toDTO(entity);
    }

    /**
     * Use Case: Listar Pessoas
     * Lista todas as pessoas do sistema.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @return Lista de todas as pessoas
     */
    @Tool(description = "Lista todas as pessoas do sistema. Retorna uma lista com todas as pessoas cadastradas com seus dados completos.")
    @TransactionalRead
    public List<PessoaDTO> listarTodas() {
        log.debug("Listando todas as pessoas");
        return pessoaRepository.findAll().stream()
            .map(pessoaMapper::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Use Case: Buscar Pessoa por ID
     * Busca uma pessoa específica por seu identificador.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param id Identificador da pessoa
     * @return Pessoa encontrada
     * @throws IllegalArgumentException se pessoa não existir
     */
    @Tool(description = "Busca uma pessoa específica por seu identificador. Recebe o ID da pessoa e retorna os dados completos da pessoa encontrada.")
    @TransactionalRead
    public PessoaDTO buscarPorId(@ToolParam(description = "Identificador único da pessoa a ser buscada") Long id) {
        log.debug("Buscando pessoa por id: {}", id);
        Pessoa entity = pessoaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada: " + id));
        return pessoaMapper.toDTO(entity);
    }

    /**
     * Use Case: Atualizar Pessoa
     * Atualiza os dados de uma pessoa existente.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param id Identificador da pessoa a ser atualizada
     * @param dto DTO com novos dados
     * @return Pessoa atualizada
     * @throws IllegalArgumentException se pessoa não existir ou email/CPF já existirem
     */
    @Tool(description = "Atualiza os dados de uma pessoa existente. Recebe o ID da pessoa e os novos dados, retorna a pessoa atualizada.")
    @TransactionalWrite
    public PessoaDTO atualizar(
        @ToolParam(description = "Identificador único da pessoa a ser atualizada") Long id,
        @ToolParam(description = "DTO com os novos dados da pessoa (nome, email, data de nascimento, CPF)") PessoaDTO dto) {
        log.info("Atualizando pessoa: id={}, nome={}", id, dto.getNome());

        Pessoa entity = pessoaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada: " + id));

        if (!entity.getEmail().equals(dto.getEmail())) {
            validarEmailUnico(dto.getEmail());
        }

        if (!entity.getCpf().equals(dto.getCpf())) {
            validarCpfUnico(dto.getCpf());
        }

        pessoaMapper.updateEntityFromDTO(dto, entity);
        entity.setUpdatedAt(new Date());
        entity = pessoaRepository.save(entity);

        log.info("Pessoa atualizada com sucesso: id={}", entity.getId());
        return pessoaMapper.toDTO(entity);
    }

    /**
     * Use Case: Deletar Pessoa
     * Remove uma pessoa do sistema.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param id Identificador da pessoa a ser removida
     * @throws IllegalArgumentException se pessoa não existir
     */
    @Tool(description = "Remove uma pessoa do sistema. Recebe o ID da pessoa e remove-a permanentemente do banco de dados.")
    @TransactionalWrite
    public void deletar(@ToolParam(description = "Identificador único da pessoa a ser removida") Long id) {
        log.info("Deletando pessoa: id={}", id);

        Pessoa entity = pessoaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada: " + id));

        pessoaRepository.delete(entity);
        log.info("Pessoa deletada com sucesso: id={}", id);
    }

    /**
     * Use Case: Buscar Pessoas por Critérios
     * Busca pessoas por critérios específicos como nome e idade mínima.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     *
     * @param nome Nome para busca (opcional)
     * @param idadeMinima Idade mínima (opcional)
     * @return Lista de pessoas que atendem aos critérios
     */
    @Tool(description = "Busca pessoas por critérios específicos. Recebe nome (opcional) e idade mínima (opcional) como filtros, retorna lista de pessoas que atendem aos critérios.")
    @TransactionalRead
    public List<PessoaDTO> buscarPorCriterios(
        @ToolParam(description = "Nome para busca (opcional)", required = false) String nome,
        @ToolParam(description = "Idade mínima (opcional)", required = false) Integer idadeMinima) {

        log.debug("Buscando pessoas por critérios: nome={}, idadeMinima={}", nome, idadeMinima);

        Specification<Pessoa> spec = Specification.where(null);

        if (nome != null && !nome.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }

        if (idadeMinima != null) {
            LocalDate dataNascimentoMin = LocalDate.now().minusYears(idadeMinima);
            spec = spec.and((root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("dataNascimento"), dataNascimentoMin));
        }

        return pessoaRepository.findAll(spec).stream()
            .map(pessoaMapper::toDTO)
            .collect(Collectors.toList());
    }

    private void validarEmailUnico(String email) {
        if (pessoaRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado: " + email);
        }
    }

    private void validarCpfUnico(String cpf) {
        if (pessoaRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }
    }
}
```

#### Passo 4: Camada REST (ia-core-rest)

**PessoaController - Endpoints REST:**
```java
package com.ia.core.rest.pessoa;

import com.ia.core.service.pessoa.PessoaService;
import com.ia.core.service.model.pessoa.PessoaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller REST para gerenciamento de pessoas.
 * <p>
 * Expõe endpoints REST para os use cases de CRUD de pessoa.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@Valid @RequestBody PessoaDTO dto) {
        log.info("POST /api/v1/pessoas - Criar pessoa");
        PessoaDTO resultado = pessoaService.criar(dto);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodas() {
        log.info("GET /api/v1/pessoas - Listar todas as pessoas");
        List<PessoaDTO> resultado = pessoaService.listarTodas();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/pessoas/{} - Buscar pessoa por ID", id);
        PessoaDTO resultado = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody PessoaDTO dto) {
        log.info("PUT /api/v1/pessoas/{} - Atualizar pessoa", id);
        PessoaDTO resultado = pessoaService.atualizar(id, dto);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("DELETE /api/v1/pessoas/{} - Deletar pessoa", id);
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
```

**PessoaChatController - Endpoint AI Agent:**
```java
package com.ia.core.rest.pessoa;

import com.ia.core.service.agent.AgentOrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Controller REST para interação com IA Agent para pessoas.
 * <p>
 * Expõe endpoint para conversação com IA Agent usando tools do PessoaService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/pessoas/chat")
@RequiredArgsConstructor
public class PessoaChatController {

    private final AgentOrchestratorService agentOrchestratorService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        log.info("POST /api/v1/pessoas/chat - Chat com IA Agent");

        String mensagem = request.get("mensagem");
        Long agenteId = Long.parseLong(request.getOrDefault("agenteId", "1"));

        String resposta = agentOrchestratorService.orchestrarAgente(agenteId, mensagem);

        return ResponseEntity.ok(Map.of(
            "resposta", resposta,
            "agenteId", agenteId
        ));
    }
}
```

#### Passo 5: Camada View (ia-core-view)

**PessoaManager - Implementa Use Cases como Tools Automáticas:**
```java
package com.ia.core.view.pessoa;

import com.ia.core.service.model.pessoa.PessoaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

/**
 * Manager para gerenciamento de pessoas na camada View.
 * <p>
 * Todos os métodos públicos são automaticamente tools descobertas pelo FerramentaDiscoveryService.
 * Implementa os use cases de CRUD de pessoa adaptados para a interface Vaadin.
 * <p>
 * IMPORTANTE: Tools na camada View devem ser operações funcionais, NÃO de navegação.
 * Exemplos válidos: cadastrar pessoa, listar pessoas, buscar por critérios.
 * Exemplos inválidos: navegar para view, abrir diálogo.
 * <p>
 * IMPORTANTE: Este manager chama a API REST via HTTP, não o PessoaService diretamente.
 * Isso permite que a API REST seja hospedada independentemente da camada View.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PessoaManager {

    private final RestClient restClient;

    @Value("${api.base-url:http://localhost:8080}")
    private String apiBaseUrl;

    @Value("${api.version:v1}")
    private String apiVersion;

    /**
     * Use Case: Criar Pessoa (View)
     * Cadastra uma nova pessoa através da interface Vaadin.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     * Chama a API REST via HTTP POST.
     *
     * @param dto DTO com dados da pessoa
     * @return Pessoa criada
     */
    @Tool(description = "Cadastra uma nova pessoa através da interface Vaadin. Chama a API REST via HTTP POST para criar a pessoa.")
    public PessoaDTO cadastrarPessoa(@ToolParam(description = "DTO com dados da pessoa a ser cadastrada (nome, email, data de nascimento, CPF)") PessoaDTO dto) {
        log.info("View: Cadastrando pessoa via API REST: nome={}", dto.getNome());
        return restClient.post()
            .uri(apiBaseUrl + "/api/" + apiVersion + "/pessoas")
            .body(dto)
            .retrieve()
            .body(PessoaDTO.class);
    }

    /**
     * Use Case: Listar Pessoas (View)
     * Lista todas as pessoas do sistema para exibição na interface.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     * Chama a API REST via HTTP GET.
     *
     * @return Lista de todas as pessoas
     */
    @Tool(description = "Lista todas as pessoas do sistema para exibição na interface. Chama a API REST via HTTP GET.")
    public List<PessoaDTO> listarTodasPessoas() {
        log.debug("View: Listando todas as pessoas via API REST");
        return restClient.get()
            .uri(apiBaseUrl + "/api/" + apiVersion + "/pessoas")
            .retrieve()
            .body(List.class);
    }

    /**
     * Use Case: Listar Pessoas Maiores de 18 Anos (View)
     * Lista todas as pessoas maiores de 18 anos.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     * É uma operação funcional específica da view.
     * Chama a API REST via HTTP GET com parâmetros.
     *
     * @return Lista de pessoas maiores de 18 anos
     */
    @Tool(description = "Lista todas as pessoas maiores de 18 anos. É uma operação funcional específica da view. Chama a API REST via HTTP GET com parâmetros.")
    public List<PessoaDTO> listarPessoasMaioresDe18Anos() {
        log.debug("View: Listando pessoas maiores de 18 anos via API REST");
        return restClient.get()
            .uri(apiBaseUrl + "/api/" + apiVersion + "/pessoas/buscar?idadeMinima=18")
            .retrieve()
            .body(List.class);
    }

    /**
     * Use Case: Buscar Pessoas por Nome (View)
     * Busca pessoas por nome para exibição na interface.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     * É uma operação funcional específica da view.
     * Chama a API REST via HTTP GET com parâmetros.
     *
     * @param nome Nome para busca
     * @return Lista de pessoas com o nome informado
     */
    @Tool(description = "Busca pessoas por nome para exibição na interface. É uma operação funcional específica da view. Chama a API REST via HTTP GET com parâmetros.")
    public List<PessoaDTO> buscarPessoasPorNome(@ToolParam(description = "Nome para busca de pessoas") String nome) {
        log.debug("View: Buscando pessoas por nome via API REST: {}", nome);
        return restClient.get()
            .uri(apiBaseUrl + "/api/" + apiVersion + "/pessoas/buscar?nome=" + nome)
            .retrieve()
            .body(List.class);
    }

    /**
     * Use Case: Atualizar Pessoa (View)
     * Atualiza os dados de uma pessoa existente através da interface.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     * Chama a API REST via HTTP PUT.
     *
     * @param id Identificador da pessoa
     * @param dto DTO com novos dados
     * @return Pessoa atualizada
     */
    @Tool(description = "Atualiza os dados de uma pessoa existente através da interface. Chama a API REST via HTTP PUT.")
    public PessoaDTO atualizarPessoa(
        @ToolParam(description = "Identificador único da pessoa a ser atualizada") Long id,
        @ToolParam(description = "DTO com os novos dados da pessoa (nome, email, data de nascimento, CPF)") PessoaDTO dto) {
        log.info("View: Atualizando pessoa via API REST: id={}, nome={}", id, dto.getNome());
        return restClient.put()
            .uri(apiBaseUrl + "/api/" + apiVersion + "/pessoas/" + id)
            .body(dto)
            .retrieve()
            .body(PessoaDTO.class);
    }

    /**
     * Use Case: Deletar Pessoa (View)
     * Remove uma pessoa através da interface.
     * Esta tool é automaticamente descoberta pelo FerramentaDiscoveryService.
     * Chama a API REST via HTTP DELETE.
     *
     * @param id Identificador da pessoa
     */
    @Tool(description = "Remove uma pessoa através da interface. Chama a API REST via HTTP DELETE.")
    public void deletarPessoa(@ToolParam(description = "Identificador único da pessoa a ser removida") Long id) {
        log.info("View: Deletando pessoa via API REST: id={}", id);
        restClient.delete()
            .uri(apiBaseUrl + "/api/" + apiVersion + "/pessoas/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
```

**PessoaFormView - Interface Vaadin:**
```java
package com.ia.core.view.pessoa;

import com.ia.core.service.agent.AgentOrchestratorService;
import com.ia.core.service.model.pessoa.PessoaDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * View Vaadin para gerenciamento de pessoas.
 * <p>
 * Implementa interface de CRUD de pessoa com integração com IA Agent.
 * A View chama a API REST via PessoaManager, permitindo arquitetura independente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
@Route("pessoas")
@RequiredArgsConstructor
public class PessoaFormView extends VerticalLayout {

    private final PessoaManager pessoaManager;
    private final AgentOrchestratorService agentOrchestratorService;

    private final Grid<PessoaDTO> grid = new Grid<>(PessoaDTO.class);
    private final Binder<PessoaDTO> binder = new Binder<>(PessoaDTO.class);

    public PessoaFormView() {
        setSizeFull();

        H2 title = new H2("Gerenciamento de Pessoas");
        add(title);

        // Formulário
        FormLayout formLayout = new FormLayout();

        TextField nomeField = new TextField("Nome");
        EmailField emailField = new EmailField("Email");
        TextField cpfField = new TextField("CPF");

        formLayout.add(nomeField, emailField, cpfField);
        add(formLayout);

        // Botões
        Button btnSalvar = new Button("Salvar", e -> salvarPessoa());
        Button btnListar = new Button("Listar Todas", e -> listarPessoas());
        Button btnMaiores18 = new Button("Maiores de 18", e -> listarMaiores18());
        Button btnChat = new Button("Chat com IA", e -> abrirChat());

        add(btnSalvar, btnListar, btnMaiores18, btnChat);

        // Grid
        grid.setSizeFull();
        add(grid);

        // Binder
        binder.forField(nomeField).asRequired("Nome é obrigatório").bind(PessoaDTO::getNome, PessoaDTO::setNome);
        binder.forField(emailField).asRequired("Email é obrigatório").bind(PessoaDTO::getEmail, PessoaDTO::setEmail);
        binder.forField(cpfField).asRequired("CPF é obrigatório").bind(PessoaDTO::getCpf, PessoaDTO::setCpf);

        listarPessoas();
    }

    private void salvarPessoa() {
        PessoaDTO dto = new PessoaDTO();
        if (binder.writeBeanIfValid(dto)) {
            PessoaDTO salva = pessoaManager.cadastrarPessoa(dto);
            Notification.show("Pessoa salva: " + salva.getNome());
            listarPessoas();
        }
    }

    private void listarPessoas() {
        grid.setItems(pessoaManager.listarTodasPessoas());
    }

    private void listarMaiores18() {
        grid.setItems(pessoaManager.listarPessoasMaioresDe18Anos());
    }

    private void abrirChat() {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        VerticalLayout layout = new VerticalLayout();
        TextField mensagemField = new TextField("Mensagem");
        Button btnEnviar = new Button("Enviar", e -> enviarMensagem(mensagemField.getValue()));

        layout.add(mensagemField, btnEnviar);
        dialog.add(layout);
        dialog.open();
    }

    private void enviarMensagem(String mensagem) {
        String resposta = agentOrchestratorService.orchestrarAgente(1L, mensagem);
        Notification.show("IA: " + resposta);
    }
}
```

### Resumo da Implementação

**Hierarquia de Classes e Tools:**

```
PessoaService (*Service - Camada Service)
├── criar(PessoaDTO) → Tool automática
├── listarTodas() → Tool automática
├── buscarPorId(Long) → Tool automática
├── atualizar(Long, PessoaDTO) → Tool automática
├── deletar(Long) → Tool automática
└── buscarPorCriterios(String, Integer) → Tool automática

PessoaManager (*Manager - Camada View)
├── cadastrarPessoa(PessoaDTO) → Tool automática (chama API REST via HTTP)
├── listarTodasPessoas() → Tool automática (chama API REST via HTTP)
├── listarPessoasMaioresDe18Anos() → Tool automática (chama API REST via HTTP)
├── buscarPessoasPorNome(String) → Tool automática (chama API REST via HTTP)
├── atualizarPessoa(Long, PessoaDTO) → Tool automática (chama API REST via HTTP)
└── deletarPessoa(Long) → Tool automática (chama API REST via HTTP)
```

**Fluxo de Execução - Arquitetura Independente:**

1. **Via REST API (sem View):**
   - Cliente envia entrada textual via API REST
   - AgentOrchestratorService (REST) recebe entrada
   - ChatClient envia prompt ao LLM com lista de tools do PessoaService
   - LLM decide qual tool chamar e com quais parâmetros
   - Tool do PessoaService é executada diretamente
   - PessoaService executa lógica de negócio
   - PessoaRepository acessa banco de dados
   - Resultado retorna: Banco → Service → API REST
   - Resultado é retornado ao LLM
   - LLM processa resultado e gera resposta final
   - Resposta é enviada ao cliente via API REST

2. **Via View com IA Agent (chamando API REST):**
   - Usuário digita entrada textual na View
   - AgentOrchestratorService (View) recebe entrada
   - ChatClient envia prompt ao LLM com lista de tools do PessoaManager
   - LLM decide qual tool chamar e com quais parâmetros
   - Tool do PessoaManager é executada
   - Tool dispara requisição HTTP para API REST via RestClient
   - API REST recebe requisição (sem intervenção de agente)
   - PessoaController executa endpoint diretamente
   - PessoaService executa lógica de negócio
   - PessoaRepository acessa banco de dados
   - Resultado retorna: Banco → Service → Controller → API REST
   - PessoaManager captura resultado da API REST
   - PessoaManager atualiza View conforme necessário
   - Resultado é retornado ao LLM
   - LLM processa resultado e gera resposta final
   - Resposta é enviada ao usuário via View

3. **Via View Direta (sem IA Agent):**
   - Usuário clica em botões da PessoaFormView
   - PessoaManager executa use case chamando API REST via HTTP
   - API REST executa lógica de negócio
   - Resultado é retornado para PessoaManager
   - PessoaManager atualiza View com resultado

**Princípios Aplicados:**

- **Tools Automáticas:** Todos os métodos públicos de *Service e *Manager são tools
- **Use Cases:** Cada tool pertence a um use case implementado em ambas as camadas
- **Arquitetura Independente:** API REST pode ser hospedada sem View, e View pode ser hospedada separadamente
- **Comunicação HTTP:** View chama API REST via RestClient/WebClient, não acessa Service diretamente
- **Operações Funcionais:** Tools de view são operações funcionais, não de navegação
- **Descoberta Automática:** FerramentaDiscoveryService descobre tools automaticamente
- **Agentes Independentes:** Cada camada tem seu próprio AgentOrchestratorService

---

## Padrões de Integração REST

### Specification Pattern para Filtros Dinâmicos (ADR-002)

Baseado no ADR-002, usar Specification Pattern (JPA Criteria API) para filtros dinâmicos em queries de IA.

**Operadores Implementados:**
- `EQUAL`, `NOT_EQUAL`, `LIKE`, `IN`
- `GREATER_THAN`, `LESS_THAN`, `GREATER_THAN_OR_EQUAL_TO`, `LESS_THAN_OR_EQUAL_TO`

**FieldTypes Suportados:**
- `BOOLEAN`, `CHAR`, `DATE`, `TIME`, `DATE_TIME`, `STRING`, `LONG`, `INTEGER`, `DOUBLE`, `ENUM`, `OBJECT`

**Exemplo de Uso em Service de IA:**
```java
@Service
public class PessoaService {

  public List<PessoaDTO> buscarPorCriterios(
      @ToolParam(description = "Nome para busca (opcional)", required = false) String nome,
      @ToolParam(description = "Idade mínima (opcional)", required = false) Integer idadeMinima) {

    Specification<Pessoa> spec = Specification.where(null);

    if (nome != null && !nome.isEmpty()) {
      spec = spec.and((root, query, cb) ->
          cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
    }

    if (idadeMinima != null) {
      spec = spec.and((root, query, cb) ->
          cb.greaterThanOrEqualTo(root.get("idade"), idadeMinima));
    }

    return pessoaRepository.findAll(spec).stream()
        .map(pessoaMapper::toDTO)
        .collect(Collectors.toList());
  }
}
```

### Domain Events para Comunicação Desacoplada (ADR-005)

Baseado no ADR-005, usar Domain Events para notificar operações de IA sem criar dependências diretas.

**Tipos de Evento:**
- `CREATED` - Entidade criada
- `UPDATED` - Entidade atualizada
- `DELETED` - Entidade deletada

**Exemplo de Evento de IA:**
```java
@Component
public class LlmInteractionEventListener {

  @EventListener
  public void handleLlmInteractionEvent(BaseServiceEvent<LlmInteractionDTO> event) {
    if (event.getEventType() == CrudOperationType.CREATED) {
      log.info("Nova interação com IA registrada: {}", event.getDto().getId());
      // Notificar sistemas externos, atualizar métricas, etc.
    }
  }
}
```

### Exception Handling para IA (ADR-011)

Baseado no ADR-011, usar hierarquia de exceções baseada em domínio para erros de IA.

**Hierarquia de Exceções:**
```
DomainException (abstract)
    ├── ResourceNotFoundException
    ├── ValidationException
    └── BusinessException
```

**Exemplo de Exceção de IA:**
```java
public class LlmModelNotFoundException extends DomainException {
  public LlmModelNotFoundException(String model) {
    super(String.format("Modelo LLM não encontrado: %s", model));
  }
}

@Service
public class AgentOrchestratorService {

  public String orchestrarAgente(Long agenteId, String mensagem) {
    AgenteDTO agente = agenteService.buscarPorId(agenteId)
        .orElseThrow(() -> new LlmModelNotFoundException("Agente não encontrado"));
    // ...
  }
}
```

### Logging e Monitoramento para IA (ADR-013)

Baseado no ADR-013, usar Correlation ID para rastreamento de requisições de IA e métricas com Micrometer.

**Correlation ID em Interações de IA:**
```java
@Service
@Slf4j
public class AgentOrchestratorService {

  public String orchestrarAgente(Long agenteId, String mensagem) {
    String correlationId = MDC.get("correlationId");
    log.info("Iniciando orquestração de agente - correlationId: {}, agenteId: {}",
        correlationId, agenteId);

    try {
      String resposta = chatClient.prompt()
          .user(mensagem)
          .call()
          .content();

      log.info("Orquestração concluída com sucesso - correlationId: {}", correlationId);
      return resposta;
    } catch (Exception e) {
      log.error("Erro na orquestração de agente - correlationId: {}", correlationId, e);
      throw new LlmOrchestrationException("Erro na orquestração de agente", e);
    }
  }
}
```

**Métricas de IA com Micrometer:**
```java
@Component
public class LlmMetrics {

  private final Counter llmRequestCounter;
  private final Timer llmResponseTimer;

  public LlmMetrics(MeterRegistry registry) {
    this.llmRequestCounter = Counter.builder("llm_requests_total")
        .description("Total de requisições LLM")
        .tag("type", "agent")
        .register(registry);

    this.llmResponseTimer = Timer.builder("llm_response_time")
        .description("Tempo de resposta LLM")
        .publishPercentiles(0.5, 0.95, 0.99)
        .register(registry);
  }

  public void recordRequest(String type) {
    llmRequestCounter.increment(Tag.of("type", type));
  }

  public void recordResponseTime(long duration) {
    llmResponseTimer.record(duration, TimeUnit.MILLISECONDS);
  }
}
```

### Business Rule Chain para Validações de IA (ADR-018)

Baseado no ADR-018, usar Business Rule Chain para validações de negócio em contexto de IA.

**Exemplo de Validação de Agent:**
```java
public class AgentAtivoRule implements BusinessRule<AgenteDTO> {

  @Override
  public String getCode() {
    return "AGENTE_001";
  }

  @Override
  public String getName() {
    return "Agente deve estar ativo";
  }

  @Override
  public void validate(AgenteDTO dto, ValidationResult result) {
    if (!dto.getAtivo()) {
      result.addError("ativo", translator.get(AGENTE_INATIVO));
    }
  }
}

// Compondo cadeia de regras
BusinessRuleChain<AgenteDTO> chain = BusinessRuleChain.<AgenteDTO>create()
    .addRule(new AgentAtivoRule())
    .addRule(new AgentModelValidoRule())
    .addRule(new AgentToolsRule());

chain.validate(agenteDTO);
```

### EntityGraph para Performance de IA (ADR-006)

Baseado no ADR-006, usar EntityGraph para otimizar queries de IA e evitar N+1.

**Exemplo de EntityGraph em Service de IA:**
```java
@Service
public class AgenteService {

  @EntityGraph(attributePaths = {"ferramentas", "skills"})
  public AgenteDTO buscarComFerramentas(Long id) {
    Agente entity = agenteRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Agente", id));
    return agenteMapper.toDTO(entity);
  }
}
```

### Service Validator Pattern para IA (ADR-019)

Baseado no ADR-019, usar Service Validator para validações de IA separadas da lógica de negócio.

**Exemplo de Validator para Agent:**
```java
@Component
public class AgenteValidator implements ServiceValidator<AgenteDTO> {

  @Override
  public ValidationResult validate(AgenteDTO dto) {
    ValidationResult result = ValidationResult.create();

    if (dto.getInstrucoes() == null || dto.getInstrucoes().isBlank()) {
      result.addError("instrucoes", "Instruções do agente são obrigatórias");
    }

    if (dto.getModelo() == null || dto.getModelo().isBlank()) {
      result.addError("modelo", "Modelo LLM é obrigatório");
    }

    return result;
  }
}

@Service
public class AgenteService {

  private final AgenteValidator validator;

  public AgenteDTO criar(AgenteDTO dto) {
    ValidationResult result = validator.validate(dto);
    if (result.hasErrors()) {
      throw new ValidationException(result);
    }
    // ...
  }
}
```

### Testes para IA (ADR-012)

Baseado no ADR-012, usar padrões de testes para componentes de IA.

**Exemplo de Teste de AgentOrchestratorService:**
```java
@SpringBootTest
class AgentOrchestratorServiceTest {

  @Autowired
  private AgentOrchestratorService orchestratorService;

  @MockBean
  private ChatClient.Builder chatClientBuilder;

  @Test
  void testOrchestrarAgente() {
    // Given
    Long agenteId = 1L;
    String mensagem = "Liste todas as pessoas";

    // When
    String resposta = orchestratorService.orchestrarAgente(agenteId, mensagem);

    // Then
    assertNotNull(resposta);
    assertFalse(resposta.isEmpty());
  }
}
```

### HasVersion para Versionamento de Entidades (ADR-026)

Baseado no ADR-026, usar HasVersion para controle de versão otimista em entidades de IA.

**Exemplo de Entidade com Versionamento:**
```java
@Entity
@Table(name = "LLM_AGENTE")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Agente extends BaseEntity implements HasVersion {

  @Version
  private Long version;

  // outros campos...
}
```

### TSID para Identidade de Entidades (ADR-015)

Baseado no ADR-015, usar TSID (Time-Sorted Unique Identifier) para IDs de entidades de IA.

**Exemplo de Entidade com TSID:**
```java
@Entity
@Table(name = "LLM_AGENTE")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Agente extends BaseEntity {

  @Tsid
  @Column(name = "id")
  private Long id;

  // outros campos...
}
```

### SuperBuilder para Entidades JPA (ADR-020)

Baseado no ADR-020, usar SuperBuilder do Lombok para entidades JPA de IA.

**Exemplo de Entidade com SuperBuilder:**
```java
@Entity
@Table(name = "LLM_AGENTE")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Agente extends BaseEntity {

  private String identificador;
  private String titulo;
  private String descricao;
  @Lob
  private String instrucoes;
  private String modelo;
  private Boolean ativo;
  private String moduloOrigem;

  @ManyToMany
  @JoinTable(
    name = "LLM_AGENTE_FERRAMENTA",
    joinColumns = @JoinColumn(name = "agente_id"),
    inverseJoinColumns = @JoinColumn(name = "ferramenta_id")
  )
  private Set<Ferramenta> ferramentas = new HashSet<>();
}
```

### Flyway para Migrations de Banco de Dados (ADR-017)

Baseado no ADR-017, usar Flyway para migrations de banco de dados de IA.

**Exemplo de Migration para IA:**
```sql
-- V10022025110000__create_llm_tables.sql
CREATE TABLE llm_agente (
    id BIGSERIAL PRIMARY KEY,
    identificador VARCHAR(255) NOT NULL UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    instrucoes TEXT,
    modelo VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE,
    modulo_origem VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

CREATE TABLE llm_ferramenta (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT,
    tipo VARCHAR(50) NOT NULL,
    identificador VARCHAR(255) NOT NULL UNIQUE,
    modulo_origem VARCHAR(255),
    ativo BOOLEAN DEFAULT TRUE,
    descoberta_automatica BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE llm_agente_ferramenta (
    agente_id BIGINT NOT NULL,
    ferramenta_id BIGINT NOT NULL,
    PRIMARY KEY (agente_id, ferramenta_id),
    FOREIGN KEY (agente_id) REFERENCES llm_agente(id),
    FOREIGN KEY (ferramenta_id) REFERENCES llm_ferramenta(id)
);
```

### A2A (Agent-to-Agent) Protocol

**A2AController (ia-core-llm-rest):**
```java
@RestController
@RequestMapping("/api/${api.version}/llm/a2a")
@RequiredArgsConstructor
public class A2AController {

  @Value("${ia-core.llm.agent.a2a.enabled:false}")
  private boolean a2aEnabled;

  @Value("${ia-core.llm.agent.a2a.server-url:http://localhost:8080}")
  private String serverUrl;

  @Value("${ia-core.llm.agent.a2a.agent-id:ia-core-llm-agent}")
  private String agentId;

  private boolean connected = false;

  @PostMapping("/connect")
  public ResponseEntity<Map<String, Object>> connect(
      @RequestParam(required = false) String serverUrl,
      @RequestParam(required = false) String agentId) {

    if (!a2aEnabled) {
      return ResponseEntity.badRequest()
          .body(Map.of("success", false, "message", "A2A não está habilitado"));
    }

    // TODO: Implementar conexão real usando spring-ai-agent-utils-a2a
    this.connected = true;

    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Conexão A2A estabelecida com sucesso",
        "serverUrl", serverUrl != null ? serverUrl : this.serverUrl,
        "agentId", agentId != null ? agentId : this.agentId));
  }

  @GetMapping("/status")
  public ResponseEntity<Map<String, Object>> status() {
    Map<String, Object> status = new HashMap<>();
    status.put("enabled", a2aEnabled);
    status.put("connected", connected);
    status.put("serverUrl", serverUrl);
    status.put("agentId", agentId);
    return ResponseEntity.ok(status);
  }
}
```

### MCP (Model Context Protocol)

**Configuração MCP (application-llm-service.yml):**
```yaml
spring:
  ai:
    mcp:
      server:
        enabled: true
        name: ia-core-llm
        version: 1.0.0
        sse-endpoint: /mcp/sse
        path: /mcp
        agent-card:
          enabled: true
          path: /.well-known/agent-card.json
      tools:
        usecase-scan:
          enabled: true
          base-package: com.ia
```

**MCP Controller (ia-core-llm-rest) - Pendente de Implementação:**
```java
@RestController
@RequestMapping("/mcp")
public class McpController {

  // TODO: Implementar endpoint SSE conforme RFC 8485
  @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> mcpSse() {
    // Implementação pendente
    return Flux.empty();
  }

  // TODO: Implementar agent card
  @GetMapping(value = "/.well-known/agent-card.json", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> agentCard() {
    // Implementação pendente
    return ResponseEntity.ok(Map.of(
        "name", "ia-core-llm",
        "version", "1.0.0",
        "description", "IA Core LLM Agent"
    ));
  }
}
```

### Web Search Integration

**WebSearchTool (ia-core-llm-rest):**
```java
@Service
public class WebSearchTool {

  public static final int MAX_RESULT_COUNT = 10;
  private final BraveWebSearchTool braveWebSearchTool;

  public WebSearchTool(@Value("${brave.api.key:}") String apiKey) {
    if (apiKey == null || apiKey.isEmpty()) {
      log.warn("Brave API key não configurada. WebSearchTool não estará funcional.");
      this.braveWebSearchTool = null;
    } else {
      this.braveWebSearchTool = BraveWebSearchTool.builder(apiKey)
          .resultCount(MAX_RESULT_COUNT)
          .build();
      log.info("BraveWebSearchTool inicializado com sucesso");
    }
  }

  @Tool(description = "Realiza busca na internet usando Brave Search API")
  public String searchWeb(@ToolParam(description = "Termo de busca") String query) {
    if (braveWebSearchTool == null) {
      return "Erro: BraveWebSearchTool não inicializado. Configure brave.api.key";
    }

    try {
      return braveWebSearchTool.webSearch(query, Collections.emptyList(), Collections.emptyList());
    } catch (Exception e) {
      log.error("Erro durante busca na internet: query={}", query, e);
      return "Erro durante busca na internet: " + e.getMessage();
    }
  }
}
```

**WebSearchController (ia-core-llm-rest):**
```java
@RestController
@RequestMapping("/api/${api.version}/llm/web")
@RequiredArgsConstructor
public class WebSearchController {

  private final WebSearchTool webSearchTool;

  @PostMapping("/search")
  public ResponseEntity<String> search(@RequestParam String query) {
    try {
      String results = webSearchTool.searchWeb(query);
      return ResponseEntity.ok(results);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("Erro durante busca na internet: " + e.getMessage());
    }
  }

  @GetMapping("/status")
  public ResponseEntity<String> status() {
    return ResponseEntity.ok("Serviço de busca na internet disponível");
  }
}
```

---

## Organização de Código

### Estrutura de Pacotes

```
com.ia.core.llm
├── model
│   ├── agente
│   ├── ferramenta
│   └── sessao
├── service
│   ├── agente
│   ├── ferramenta
│   ├── tool
│   └── config
├── service-model
│   ├── agent
│   └── ferramenta
├── view
│   ├── tool
│   └── manager
└── rest
    ├── a2a
    └── web
```

### Convenções de Nomenclatura

**Classes**
- Entidades: Nome singular (`Agente`, `Ferramenta`)
- Services: `NomeService` (`AgenteService`)
- Controllers: `NomeController` (`AgenteController`)
- DTOs: `NomeDTO` (`AgenteDTO`)
- Tools: `NomeTool` (`WebSearchTool`)

**Métodos**
- Verbos no infinitivo (`criarAgente`, `listarFerramentas`)
- Métodos de tool: descritivos (`searchWeb`, `echo`)

**Variáveis**
- camelCase (`nomeAgente`, `listaFerramentas`)
- Constantes: UPPER_SNAKE_CASE (`MAX_RESULT_COUNT`)

### Anotações Spring AI

```java
@Tool                    // Marca método como tool
@ToolParam              // Descreve parâmetro de tool
@Service                // Marca classe como serviço Spring
@Component              // Marca classe como componente Spring
```

---

## Testes e Validação

### Estrutura de Testes

```java
@SpringBootTest
class AgenteServiceTest {

    @Autowired
    private AgenteService agenteService;

    @Test
    void deveCriarAgente() {
        // Implementação
    }

    @Test
    void deveDescobrirTools() {
        // Teste de descoberta automática
    }
}
```

### Testes de Tools

```java
@Test
void testEchoTool() {
    LlmToolCatalog catalog = new LlmToolCatalog();
    String resultado = catalog.echo("teste");
    assertEquals("teste", resultado);
}
```

### Validação de DTOs

```java
@Test
void deveValidarAgenteDTO() {
    AgenteDTO dto = AgenteDTO.builder()
        .nome("")
        .build();

    Set<ConstraintViolation<AgenteDTO>> violations =
        validator.validate(dto);

    assertFalse(violations.isEmpty());
}
```

### Testes de Integração

**Teste de AgentOrchestratorService (ia-core-llm-service):**
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class AgentOrchestratorServiceIntegrationTest {

    @Autowired
    private AgentOrchestratorService orchestratorService;

    @Autowired
    private AgenteService agenteService;

    @Test
    void deveOrquestrarAgente() {
        // Arrange
        AgenteDTO agente = criarAgenteTeste();
        String mensagem = "Olá, como você está?";

        // Act
        String resposta = orchestratorService.orchestrarAgente(agente.getId(), mensagem);

        // Assert
        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
    }

    private AgenteDTO criarAgenteTeste() {
        AgenteDTO dto = AgenteDTO.builder()
            .nome("Agente Teste")
            .descricao("Agente para testes")
            .modelo("llama3.2-vision")
            .systemPrompt("Você é um agente de teste")
            .instructions("Responda de forma concisa")
            .ativo(true)
            .build();
        return agenteService.criar(dto);
    }
}
```

### Testes de Controllers REST

**Teste de A2AController (ia-core-llm-rest):**
```java
@SpringBootTest
@AutoConfigureMockMvc
class A2AControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void deveRetornarStatusQuandoA2AHabilitado() throws Exception {
        mockMvc.perform(get("/api/v1/llm/a2a/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.enabled").exists())
            .andExpect(jsonPath("$.connected").exists());
    }

    @Test
    @WithMockUser
    void deveConectarQuandoA2AHabilitado() throws Exception {
        mockMvc.perform(post("/api/v1/llm/a2a/connect")
                .param("serverUrl", "http://localhost:8080")
                .param("agentId", "test-agent"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }
}
```

---

## Documentação

### Javadoc

Todas as classes públicas devem ter Javadoc:

```java
/**
 * Serviço para gerenciamento de agentes de IA.
 * <p>
 * Responsável por CRUD de agentes e orquestração de sessões.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Service
public class AgenteService {
    // Implementação
}
```

### Comentários de Código

```java
// Verifica se a API key do Brave está configurada
if (apiKey == null || apiKey.isEmpty()) {
    log.warn("Brave API key não configurada");
    this.braveWebSearchTool = null;
}
```

### Documentação de Configuração

Documentar propriedades obrigatórias e opcionais:

```yaml
# Configuração do Ollama
# OLLAMA_BASE_URL: URL do servidor Ollama (padrão: http://localhost:11434)
# OLLAMA_CHAT_MODEL: Modelo de chat (padrão: llama3.2-vision)
spring:
  ai:
    ollama:
      base-url: ${OLLAMA_BASE_URL:http://localhost:11434}
```

---

## Referências Externas

### Documentação Oficial Spring AI

- **Spring AI Reference**: https://docs.spring.io/spring-ai/reference/
  - Chat Client
  - Tool Calling
  - Advisors
  - Prompt Templates
  - Function Calling

- **Spring AI Samples**: https://github.com/spring-projects/spring-ai/tree/main/spring-ai-samples
  - Exemplos de tool-calling
  - Exemplos de agent

- **Spring Boot + Spring AI Workshop**: https://github.com/habuma/spring-ai-kitchen-assistant
  - Projeto de referência com agente e múltiplas ferramentas

### Comunidade e Suporte

- **Stack Overflow** (tag spring-ai): https://stackoverflow.com/questions/tagged/spring-ai
- **Spring Community Discord** (canal #ai): https://discord.gg/spring
- **GitHub Discussions**: https://github.com/spring-projects/spring-ai/discussions

### Artigos Acadêmicos

- **LLM agent tool use**: https://arxiv.org/search/?query=LLM+agent+tool+use
- **ReAct prompting**: https://arxiv.org/abs/2210.03629
- **Toolformer**: https://arxiv.org/abs/2302.04761
- **Generative Agents**: https://arxiv.org/abs/2304.03442
- **Plan-and-Solve Prompting**: https://arxiv.org/abs/2305.04091
- **Survey LLM agents**: https://arxiv.org/search/?query=survey+LLM+agents

---

## Melhores Práticas

### Performance

- Usar streaming para respostas longas
- Implementar cache para queries frequentes
- Limitar número de sub-agent turns
- Usar vector stores para RAG

### Segurança

- Proteger endpoints sensíveis (`/mcp/**`, `/.well-known/agent-card.json`)
- Validar todas as entradas de tools
- Usar JWT para autenticação
- Implementar rate limiting

### Manutenibilidade

- Seguir estrutura de 4 camadas
- Usar injeção de dependências
- Implementar logging adequado
- Escrever testes unitários

### Escalabilidade

- Usar configuração externa
- Implementar resiliência com Resilience4j
- Suportar múltiplos modelos
- Arquitetura modular

---

## Status Atual (29/05/2026)

### Implementado

✅ Sistema de agentes completo
✅ Tool discovery configurável
✅ LlmToolCatalog implementado
✅ Resilience4j integrado
✅ REST layer implementada
✅ A2AController (simulado)
✅ WebSearchTool implementado

### Pendente

⏳ MCP endpoint SSE
⏳ ViewToolCatalog (vazio)
⏳ A2A real (spring-ai-agent-utils-a2a)
⏳ Documentação de API key
⏳ Spring AI versão estável

---

*Documento criado em [29/05/2026] - Padrões de desenvolvimento IA para ia-core e Biblia*
