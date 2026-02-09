# Plano de Refatoração Simplificado - Projetos IA-Core e Biblia

## Visão Geral

Este documento apresenta um plano de refatoração focado, considerando que várias práticas já estão implementadas nos projetos.

---

## ✅ Fases Já Implementadas

### 1. Mapeamento DTO Bidirecional (✅ CONCLUÍDO)
**Status**: Já implementado via MapStruct - **NÃO MODIFICAR**

**Estrutura existente**:
```java
@Mapper(componentModel = "spring", uses = { EnderecoPessoaMapper.class, ContatoMapper.class })
public interface PessoaMapper {
    
    @Mapping(target = "enderecos", source = "enderecos")
    @Mapping(target = "contatos", source = "contatos")
    PessoaDTO toDTO(Pessoa pessoa);
    
    @Mapping(target = "enderecos", source = "enderecos")
    @Mapping(target = "contatos", source = "contatos")
    Pessoa toEntity(PessoaDTO dto);
}
```

**Mappers existentes**:
- [`PessoaMapper.java`](../gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/pessoa/PessoaMapper.java)
- [`IntencaoMapper.java`](../gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/intencao/IntencaoMapper.java)
- [`MaterialEventoMapper.java`](../gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/evento/MaterialEventoMapper.java)
- [`PatrocinioMapper.java`](../gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/patrocinio/PatrocinioMapper.java)
- [`PatrocinadorMapper.java`](../gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/patrocinador/PatrocinadorMapper.java)
- [`TransferenciaMapper.java`](../gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/transferencia/TransferenciaMapper.java)

> **Nota**: Os mappers MapStruct NÃO DEVEM SER MODIFICADOS conforme orientação do usuário.

---

### 2. Specification Pattern para Filtros (✅ JÁ EXISTE - ATENDE BEM)

**Status**: Já implementado em [`SearchSpecification.java`](ia-core/ia-core-model/src/main/java/com/ia/core/model/specification/SearchSpecification.java) com suporte completo em [`Operator.java`](ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/Operator.java)

**Operadores disponíveis no Operator.java**:

| Operador | Descrição | Exemplo de uso |
|----------|-----------|----------------|
| `EQUAL` | Igualdade | `nome = "João"` |
| `NOT_EQUAL` | Diferente | `status != "INATIVO"` |
| `LIKE` | Semelhante (case-insensitive) | `nome LIKE "%João%"` |
| `IN` | Em lista | `status IN ("ATIVO", "PENDENTE")` |
| `GREATER_THAN` | Maior que | `idade > 18` |
| `LESS_THAN` | Menor que | `idade < 65` |
| `GREATER_THAN_OR_EQUAL_TO` | Maior ou igual | `salario >= 1000` |
| `LESS_THAN_OR_EQUAL_TO` | Menor ou igual | `idade <= 65` |

**FieldTypes suportados em [`FieldType.java`](ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/FieldType.java)**:

| FieldType | Tipo Java | Uso típico |
|-----------|-----------|------------|
| `BOOLEAN` | `Boolean` | flags, status ativo/inativo |
| `CHAR` | `Character` | códigos single-char |
| `DATE` | `LocalDate` | datas de nascimento, cadastro |
| `TIME` | `LocalTime` | horários |
| `DATE_TIME` | `LocalDateTime` | timestamps |
| `STRING` | `String` | nomes, textos |
| `LONG` | `Long` | IDs, valores grandes |
| `INTEGER` | `Integer` | contadores, códigos |
| `DOUBLE` | `Double` | valores decimais |
| `ENUM` | `Enum` | tipos enumerados |
| `OBJECT` | `Object` | objetos genéricos |

**Estrutura do SearchSpecification**:
```java
public class SearchSpecification<T> implements Specification<T> {
    
    private final SearchRequest request;
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE),
                                       request.isDisjunction());
        
        // Aplica filtros através do enum Operator
        for (FilterRequest filter : this.request.getFilters()) {
            predicate = filter.getOperator().build(root, cb, filter, predicate,
                                                   request.isDisjunction());
        }
        
        // Ordenação
        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : this.request.getSorts()) {
            orders.add(sort.getDirection().build(root, cb, sort));
        }
        
        query.orderBy(orders);
        return predicate;
    }
}
```

**Avaliação**: O Specification Pattern **JÁ ATENDE** a maioria dos casos de uso. 

**Possíveis melhorias opcionais** (não críticas):
- `IS_NULL` / `IS_NOT_NULL` - Verificação de null
- `BETWEEN` - Entre dois valores (necessitaria mudança no FilterRequest)

---

### 3. Tratamento de Exceções (✅ JÁ EXISTE)

**Status**: Já implementado em [`BibliaRestControllerAdvice.java`](../gestor-igreja/Biblia/biblia-rest/src/main/java/com/ia/biblia/rest/BibliaRestControllerAdvice.java)

**Estrutura existente**:
```java
@RestControllerAdvice(annotations = RestController.class)
public class BibliaRestControllerAdvice extends CoreRestControllerAdvice {
    // Herda tratamento do CoreRestControllerAdvice
}
```

---

### 4. Constantes e i18n (✅ JÁ EXISTE)

**Status**: Já implementado via Translators

**Estrutura existente**:
```java
public class PessoaTranslator {
    public static final class HELP {
        public static final String PESSOA = "pessoa.help";
        public static final String NOME = "pessoa.help.nome";
    }
    
    public static final String NOME = "pessoa.nome";
    public static final String TIPO = "pessoa.tipo";
    
    public static final class VALIDATION {
        public static final String TIPO_NOT_NULL = "pessoa.validation.tipo.not.null";
        public static final String NOME_NOT_NULL = "pessoa.validation.nome.not.null";
    }
}
```

**Translators existentes**:
- [`PessoaTranslator.java`](../gestor-igreja/Biblia/biblia-service-model/src/main/java/com/ia/biblia/service/pessoa/dto/PessoaTranslator.java)
- [`FamiliaTranslator.java`](../gestor-igreja/Biblia/biblia-service-model/src/main/java/com/ia/biblia/service/familia/dto/FamiliaTranslator.java)
- [`ContaTranslator.java`](../gestor-igreja/Biblia/biblia-service-model/src/main/java/com/ia/biblia/service/conta/dto/ContaTranslator.java)
- [`MovimentoFinanceiroTranslator.java`](../gestor-igreja/Biblia/biblia-service-model/src/main/java/com/ia/biblia/service/movimentofinanceiro/dto/MovimentoFinanceiroTranslator.java)
- E mais 40+ translators

---

### 5. ServiceConfig e ManagerConfig (✅ JÁ EXISTE)

**Status**: Já implementado com padrão de injeção via construtor

**Estrutura existente - ServiceConfig**:
```java
@Component
public class PessoaServiceConfig
  extends DefaultSecuredBaseServiceConfig<Pessoa, PessoaDTO> {

  @Getter
  private final EnderecoRepository enderecoRepository;

  public PessoaServiceConfig(
      PlatformTransactionManager transactionManager,
      BaseEntityRepository<Pessoa> repository,
      BaseEntityMapper<Pessoa, PessoaDTO> mapper,
      SearchRequestMapper searchRequestMapper,
      Translator translator,
      CoreSecurityAuthorizationManager authorizationManager,
      SecurityContextService securityContextService,
      LogOperationService logOperationService,
      EnderecoRepository enderecoRepository,
      List<IServiceValidator<PessoaDTO>> validators) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, authorizationManager, securityContextService,
          logOperationService, validators);
    this.enderecoRepository = enderecoRepository;
  }
}
```

**Estrutura existente - ManagerConfig**:
```java
@Component
public class PessoaServiceConfig
  extends DefaultSecuredViewBaseMangerConfig<PessoaDTO> {

  public PessoaServiceConfig(
      BaseClient<PessoaDTO> client,
      CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }
}
```

**Configs existentes**:
- 32+ ServiceConfigs em `biblia-service`
- 21+ ManagerConfigs em `biblia-view`

---

## 🎯 Fases de Refatoração Prioritárias

### FASE A: ApplicationEventPublisher via ServiceConfig/ManagerConfig

**Objetivo**: Centralizar injeção do `ApplicationEventPublisher` através dos configs

**Regra**: TODO Service e TODO Manager deve ter o `ApplicationEventPublisher` injetado no seu Config

**Exemplo de ServiceConfig com EventPublisher**:
```java
@Component
public class FamiliaServiceConfig
  extends DefaultSecuredBaseServiceConfig<Familia, FamiliaDTO> {

  @Getter
  private final ApplicationEventPublisher eventPublisher;

  public FamiliaServiceConfig(
      PlatformTransactionManager transactionManager,
      BaseEntityRepository<Familia> repository,
      BaseEntityMapper<Familia, FamiliaDTO> mapper,
      SearchRequestMapper searchRequestMapper,
      Translator translator,
      CoreSecurityAuthorizationManager authorizationManager,
      SecurityContextService securityContextService,
      LogOperationService logOperationService,
      List<IServiceValidator<FamiliaDTO>> validators,
      ApplicationEventPublisher eventPublisher) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, authorizationManager, securityContextService,
          logOperationService, validators);
    this.eventPublisher = eventPublisher;
  }
}
```

**Exemplo de uso no Service**:
```java
@Service
public class FamiliaService extends DefaultSecuredBaseService<Familia, FamiliaDTO> {

  private final ApplicationEventPublisher eventPublisher;

  public FamiliaService(FamiliaServiceConfig config) {
    super(config);
    this.eventPublisher = config.getEventPublisher();
  }

  @Override
  public FamiliaDTO save(FamiliaDTO dto) {
    Familia familia = mapper.toEntity(dto);
    Familia salva = repository.save(familia);
    
    // Publicar evento após salvar
    eventPublisher.publishEvent(new FamiliaSalvaEvent(this, salva));
    
    return mapper.toDTO(salva);
  }
}
```

**Módulos afetados**:
- `biblia-service` → 32+ ServiceConfigs
- `biblia-view` → 21+ ManagerConfigs

**Benefícios**:
- Desacoplamento entre serviços
- Facilita testes com mocks de eventos
- Centraliza dependências

---

### FASE B: Extrair Interfaces de Serviço (DIP)

**Objetivo**: Extrair interfaces de serviço para aplicar DIP

**Antes**:
```java
@Service
public class PessoaService {
    private final PessoaRepository repository;
    
    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }
}
```

**Depois**:
```java
public interface IPessoaService {
    PessoaDTO buscarPorId(Long id);
    List<PessoaDTO> listarTodos(Pageable pageable);
    PessoaDTO salvar(PessoaDTO dto);
    void excluir(Long id);
}

@Service
public class PessoaService implements IPessoaService {
    private final PessoaRepository repository;
    private final PessoaMapper mapper;
    
    public PessoaService(PessoaRepository repository, PessoaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
```

**Módulos afetados**:
- `biblia-service` → Interfaces para PessoaService, FamiliaService, ContaService

**Benefícios**:
- Facilita mock em testes
- Permite implementações alternativas
- Aplica DIP corretamente

---

### FASE C: Padronizar Nomenclatura de Métodos

**Objetivo**: Convenções consistentes em toda a codebase

**Padrão por tipo de método**:

| Tipo | Padrão | Exemplo |
|------|---------|---------|
| Busca | `buscarPor[Atributo](T valor)` | `buscarPorNome(String nome)` |
| Lista | `listarTodos(Pageable pageable)` | `listarTodos(Pageable)` |
| Contagem | `contar()` | `contar()` |
| Verificação | `existe(Long id)` | `existe(1L)` |
| Com filtros | `filtrar(SearchRequest request)` | `filtrar(request)` |
| Com EntityGraph | `buscarPorIdCom[Relacionamento](Long id)` | `buscarPorIdComEnderecos(1L)` |

**Módulos afetados**:
- Todos os serviços em `biblia-service`
- Todos os serviços em `ia-core-llm-service`

---

### FASE D: Corrigir Dependências Circulares

**Objetivo**: Eliminar ciclos de dependência

**Ciclos comuns detectados**:
- `PessoaService` ↔ `FamiliaService`
- `ContaService` ↔ `MovimentoFinanceiroService`

**Soluções**:
1. Extrair interface (`IPessoaService`)
2. Usar eventos (`ApplicationEventPublisher`) - coberto na FASE A
3. Criar serviço de agregação

**Exemplo com eventos**:
```java
@Service
public class FamiliaService extends DefaultSecuredBaseService<Familia, FamiliaDTO> {

  private final ApplicationEventPublisher eventPublisher;

  public FamiliaService(FamiliaServiceConfig config) {
    super(config);
    this.eventPublisher = config.getEventPublisher();
  }

  public void adicionarIntegrante(Familia familia, Pessoa pessoa) {
    familia.adicionarIntegrante(pessoa);
    repository.save(familia);
    
    // Notificação via evento (desacoplado)
    eventPublisher.publishEvent(new IntegranteAdicionadoEvent(this, familia, pessoa));
  }
}
```

---

### FASE E: Documentação Técnica de Arquitetura

**Objetivos**:
1. Criar README.md por módulo
2. Documentar decisões de arquitetura (ADR)
3. Criar diagrama de componentes

**Estrutura proposta**:
```
ia-core/
├── README.md
├── ARCHITECTURE.md
└── ADR/
    ├── 001-use-mapstruct-for-mapping.md
    ├── 002-use-specification-for-filtering.md
    └── 003-use-translator-for-i18n.md

biblia/
├── README.md
├── ARCHITECTURE.md
└── ADR/
    └── ...
```

---

## 📋 Resumo das Técnicas Aplicadas

| Técnica | Status | Implementação |
|---------|--------|---------------|
| SOLID | ✅ | MapStruct, interfaces de serviço |
| Clean Architecture | ✅ | Camadas service-model-service-rest-view |
| Clean Code | ⚠️ | Parcial - precisa padronização |
| DTO Mapping | ✅ | MapStruct com @Mapper - **NÃO MODIFICAR** |
| **Specification Pattern** | ✅ **ATENDE BEM** | 8 operadores + 11 field types |
| Exception Handling | ✅ | ControllerAdvice |
| i18n | ✅ | Translator + properties |
| EntityGraph | ✅ | @NamedEntityGraph |
| Builder Pattern | ✅ | Lombok @Builder |
| ServiceConfig | ✅ | Injeção via construtor |
| ManagerConfig | ✅ | Injeção via construtor |

---

## 🎯 Próximos Passos Prioritários

| Prioridade | Fase | Descrição |
|------------|------|-----------|
| **1** | FASE A | ApplicationEventPublisher via Config |
| **2** | FASE B | Extrair interfaces de serviço (DIP) |
| **3** | FASE C | Padronizar nomenclatura |
| **4** | FASE D | Corrigir dependências circulares |
| **5** | FASE E | Documentação técnica |

---

## 📊 Métricas de Sucesso

- [ ] Cobertura de testes > 80%
- [ ] Zero warnings de compilação
- [ ] Tempo de build < 5 minutos
- [ ] Documentação > 90% classes públicas
- [ ] Zero dependências circulares
- [ ] 100% dos Services/Managers com EventPublisher via Config

---

## 📁 Arquivos de Referência

| Categoria | Localização |
|-----------|-------------|
| Mappings | `/biblia-service/src/main/java/com/ia/biblia/service/*/Mapper.java` - **NÃO MODIFICAR** |
| Translators | `/biblia-service-model/src/main/java/com/ia/biblia/service/*/dto/*Translator.java` |
| ServiceConfigs | `/biblia-service/src/main/java/com/ia/biblia/service/*/ServiceConfig.java` |
| ManagerConfigs | `/biblia-view/src/main/java/com/ia/biblia/view/*/ManagerConfig.java` |
| **Specification** | `ia-core/ia-core-model/src/main/java/com/ia/core/model/specification/SearchSpecification.java` |
| **Operator** | `ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/Operator.java` - **8 operadores** |
| **FieldType** | `ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/FieldType.java` - **11 tipos** |
| Exception Handler | `/biblia-rest/src/main/java/com/ia/biblia/rest/BibliaRestControllerAdvice.java` |

---

## 📋 Análise do Specification Pattern

### Operadores Implementados (8 total)
| Operador | Implementado | Descrição |
|----------|-------------|-----------|
| `EQUAL` | ✅ | Igualdade |
| `NOT_EQUAL` | ✅ | Diferente |
| `LIKE` | ✅ | Like (case-insensitive) |
| `IN` | ✅ | Em lista |
| `GREATER_THAN` | ✅ | Maior que |
| `LESS_THAN` | ✅ | Menor que |
| `GREATER_THAN_OR_EQUAL_TO` | ✅ | Maior ou igual |
| `LESS_THAN_OR_EQUAL_TO` | ✅ | Menor ou igual |

### FieldTypes Suportados (11 total)
| FieldType | Implementado | Tipo Java |
|-----------|--------------|-----------|
| `BOOLEAN` | ✅ | Boolean |
| `CHAR` | ✅ | Character |
| `DATE` | ✅ | LocalDate |
| `TIME` | ✅ | LocalTime |
| `DATE_TIME` | ✅ | LocalDateTime |
| `STRING` | ✅ | String |
| `LONG` | ✅ | Long |
| `INTEGER` | ✅ | Integer |
| `DOUBLE` | ✅ | Double |
| `ENUM` | ✅ | Enum |
| `OBJECT` | ✅ | Object |

### Avaliação
✅ **O Specification Pattern JÁ ATENDE** a maioria dos casos de uso com 8 operadores e 11 field types.

**Possíveis extensões opcionais** (não críticas):
- `IS_NULL` / `IS_NOT_NULL` - Verificação de null
- `BETWEEN` - Entre dois valores (necessitaria mudança no FilterRequest)

---

**Nota Importante**: Os mappers MapStruct NÃO DEVEM SER MODIFICADOS conforme orientação do usuário. Eles já estão funcionando corretamente e qualquer modificação pode quebrar a funcionalidade existente.

---

**Data de atualização**: 2026-02-09
**Versão**: 2.2
**Autor**: Israel Araújo
