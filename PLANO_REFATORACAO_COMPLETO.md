# Plano de Refatoracao Completo - ia-core e Biblia

## Sumario

- [Analise do Estado Atual](#analise-do-estado-atual)
- [Problemas Identificados](#problemas-identificados)
- [Plano de Refatoracao Granular](#plano-de-refatoracao-granular)
- [Ordem de Prioridade](#ordem-de-prioridade)
- [Metricas de Sucesso](#metricas-de-sucesso)
- [Notas de Implementacao](#notas-de-implementacao)
- [Estrategia de Migracao](#estrategia-de-migracao)
- [Referencias](#referencias)

---

## Analise do Estado Atual

### Estrutura do Projeto ia-core

```
ia-core/
├── ia-core-model/           # Camada de modelo e entidades
├── ia-core-service/         # Camada de servico (regras de negocio)
├── ia-core-rest/            # Camada REST (controllers)
├── ia-core-view/            # Camada de apresentacao (Vaadin)
├── ia-core-llm-service/     # Servicos LLM
├── ia-core-llm-view/        # View LLM
├── ia-core-llm-service-model/ # DTOs LLM
├── ia-core-quartz-service/  # Servicos de agendamento
├── ia-core-quartz-view/     # View Quartz
├── ia-core-nlp/             # Processamento de linguagem natural
├── ia-core-grammar/         # Grammars ANTLR
├── ia-core-flyway/          # Migrações de banco
└── ia-core-report/          # Geracao de relatorios
```

### Estrutura do Projeto Biblia

```
biblia/
├── biblia-model/            # Camada de modelo
├── biblia-service/          # Camada de servico
├── biblia-rest/             # Camada REST
├── biblia-view/             # Camada de apresentacao
├── biblia-nlp/              # Processamento de linguagem natural
└── biblia-grammar/          # Grammars ANTLR
```

### Padroes Existentes Identificados

O projeto ja possui varios padroes bem implementados:

1. **FormViewModel e FormViewModelConfig**:
   - [`FormViewModelConfig`](ia-core/ia-core-view/src/main/java/com/ia/core/view/components/form/viewModel/FormViewModelConfig.java): Classe base de configuracao com `readOnly`
   - [`SchedulerConfigFormViewModelConfig`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/form/SchedulerConfigFormViewModelConfig.java): Exemplo de extensao com managers

2. **CoreTranslator e CoreViewTranslator**:
   - [`CoreTranslator`](ia-core/ia-core-service/src/main/java/com/ia/core/service/translator/CoreTranslator.java): Para REST
   - [`CoreViewTranslator`](ia-core/ia-core-view/src/main/java/com/ia/core/view/translator/CoreViewTranslator.java): Para View

3. **ControllerAdvice e ExceptionHandler**:
   - [`CoreRestControllerAdvice`](ia-core/ia-core-rest/src/main/java/com/ia/core/rest/error/CoreRestControllerAdvice.java): Ja existe

---

## Problemas Identificados

### SOLID Violations

1. **SRP (Single Responsibility Principle)** violado em:
   - [`ChatService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/chat/ChatService.java): Responsavel por multiplas tarefas (chat, vector store, templates)
   - [`LLMTransformationService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java): Processamento de imagens e transformacao de texto misturados
   - [`CoreOWLService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/owl/service/CoreOWLService.java): Multiplas responsabilidades (parsing, reasoning, gerenciamento)

2. **OCP (Open/Closed Principle)** parcialmente seguido:
   - Classes base abstratas sao extensíveis, mas algumas concrete classes sao rigidas
   - [`DefaultBaseService`](ia-core/ia-core-service/src/main/java/com/ia/core/service/DefaultBaseService.java) pode ser mais extensível

3. **LSP (Liskov Substitution Principle)** precisa de verificacao:
   - Heranca de `DefaultBaseService` pode causar problemas de substituicao em alguns casos

4. **ISP (Interface Segregation Principle)** parcialmente seguido:
   - Interfaces como [`CountBaseController`](ia-core/ia-core-rest/src/main/java/com/ia/core/rest/control/CountBaseController.java), [`FindBaseController`](ia-core/ia-core-rest/src/main/java/com/ia/core/rest/control/FindBaseController.java) sao segregadas
   - [`BaseService`](ia-core/ia-core-service/src/main/java/com/ia/core/service/BaseService.java) pode ser muito ampla

5. **DIP (Dependency Inversion Principle)** parcialmente seguido:
   - Injeção de dependencia via construtor existe
   - Algumas classes ainda dependem de implementações concretas

### Clean Architecture Violations

1. **Acoplamento entre Service e View** atraves de Managers
2. **DTOs anemicos** em algumas partes do codigo
3. **Validacao duplicada** entre View e Service

### Clean Code Violations

1. **Nomenclatura inconsistente** (ex: `llmComminicator` typo em [`LLMTransformationService.java:34`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java:34))
2. **Metodos longos** em algumas classes
3. **Javadoc incompleto** em varios lugares
4. **Strings hardcoded** sem referencia a i18n
5. **Exceptions genericas** ao inves de especificas

---

## Plano de Refatoracao Granular

### Fase 1: Fundamentos e Infraestrutura

#### Passo 1.1: Padronizar Validacao Jakarta em DTOs

**Tipo de Refatoracao:** Code Cleanup + Consistency

**O que fazer:**
- Garantir que todos os DTOs tenham validacao Jakarta (`@NotNull`, `@Size`, `@Pattern`, etc.)
- Criar validadores customizados quando necessario
- Adicionar mensagens de erro com referencia a i18n

**O que resolve:**
- Validacao precoce: Dados invalidos nao passam para camada de servico
- Consistência: Validacao padronizada em todos os DTOs
- Internacionalizacao: Mensagens de erro traduziveis

**Exemplo:**
```java
public class ComandoSistemaDTO {
    @NotNull(message = "{validation.comando.nome.required}")
    @Size(min = 3, max = 100, message = "{validation.comando.nome.size}")
    private String nome;
    
    @NotNull(message = "{validation.comando.finalidade.required}")
    private FinalidadeComandoEnum finalidade;
}
```

**Arquivos i18n:**
```properties
# translations_llm_service_model_pt_BR.properties
validation.comando.nome.required=Nome do comando e obrigatorio
validation.comando.nome.size=Nome deve ter entre 3 e 100 caracteres
validation.comando.finalidade.required=Finalidade do comando e obrigatoria
```

#### Passo 1.2: Padronizar Validacao Jakarta em Views (CORRIGIDO - INTEGRACAO COM MANAGER)

**Tipo de Refatoracao:** Code Cleanup + Consistency + Jakarta Validation + Integracao com Manager

**Objetivo:** Integrar validacao Jakarta Validation (Bean Validation) com o sistema existente de Managers, seguindo as diretrizes:
- ViewModels recebem apenas `*ViewModelConfig` no construtor
- Binder do Vaadin permanece inalterado
- `FormValidator` renomeado para `BeanValidator` (nome mais generico)
- `BeanValidator` integrado diretamente em `SaveBaseManager.validate(dto)` (implementacao padrao)

**O que fazer:**
1. Criar componente `BeanValidator` que usa `jakarta.validation.Validator`
2. Integrar `BeanValidator` na implementacao padrao de `SaveBaseManager.validate(dto)`
3. O `SaveBaseManager.save()` ja chama `validate()` automaticamente
4. Manter Binder do Vaadin como esta (apenas para binding de dados)

**O que resolve:**
- DRY: Validacao definida uma vez no DTO, usada em toda a aplicacao
- Integracao: Usa o sistema existente de validation do Manager
- Simplicidade: `jakarta.validation.Validator` nativo
- Manutenibilidade: Validacao centralizada no Service
- Consistencia: Mesma validacao em REST e View

**Fluxo de validacao existente (JA IMPLEMENTADO):**
```
ViewModel.save()
  -> Manager.save(dto)
    -> SaveBaseManager.validate(dto) [BeanValidator integrado automaticamente]
      -> SaveBaseClient.validate(dto)
        -> REST Controller.validate(dto)
          -> Service.validate(dto)
```

**Exemplo - BeanValidator (novo componente):**
```java
@Component
@RequiredArgsConstructor
public class BeanValidator {
    private final jakarta.validation.Validator validator;
    private final Translator translator;

    /**
     * Valida um DTO e retorna lista de mensagens de erro traduzidas.
     */
    public <T> List<String> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        
        return violations.stream()
            .map(v -> translator.translate(v.getMessage()))
            .collect(Collectors.toList());
    }

    /**
     * Valida um DTO e lança exceção se houver erros.
     */
    public <T> void validateOrThrow(T dto) throws ServiceException {
        List<String> errors = validate(dto);
        if (!errors.isEmpty()) {
            ServiceException ex = new ServiceException();
            errors.forEach(ex::add);
            throw ex;
        }
    }
}
```

**Exemplo - Integracao em SaveBaseManager (IMPLEMENTACAO PADRAO):**
```java
public interface SaveBaseManager<D extends Serializable>
  extends BaseManager<D> {

  // BeanValidator injetado automaticamente via configuracao do Spring
  BeanValidator getBeanValidator();

  /**
   * Realiza a validacao de um objeto.
   */
  default Collection<String> validate(D dto) {
    List<String> errors = getBeanValidator().validate(dto);
    if (!errors.isEmpty()) {
      // Adicionar validacao do BeanValidator ao resultado
      Collection<String> result = ((SaveBaseClient<D>) getClient()).validate(dto);
      result.addAll(errors);
      return result;
    }
    return ((SaveBaseClient<D>) getClient()).validate(dto);
  }
}
```

**Exemplo - AbstractBaseManager com BeanValidator:**
```java
public abstract class AbstractBaseManager<D extends Serializable>
  implements BaseManager<D> {

  @Getter
  private final BeanValidator beanValidator;

  // Config e outros componentes...
}
```

**Exemplo - DTO com validacao Jakarta:**
```java
public class ComandoSistemaDTO {
    @NotNull(message = "{validation.comando.nome.required}")
    @Size(min = 3, max = 100, message = "{validation.comando.nome.size}")
    private String nome;

    @NotNull(message = "{validation.comando.finalidade.required}")
    private FinalidadeComandoEnum finalidade;
}
```

**Propriedades i18n:**
```properties
# translations_service_pt_BR.properties
validation.comando.nome.required=Nome do comando e obrigatorio
validation.comando.nome.size=Nome deve ter entre 3 e 100 caracteres
validation.comando.finalidade.required=Finalidade do comando e obrigatoria
```

**O que NAO fazer (conforme diretriz do usuario):**
- NAO modificar o construtor do ViewModel para receber parametros extras
- NAO alterar o Binder do Vaadin
- NAO criar validacao separada no ViewModel

#### Passo 1.3: Corrigir Typos e Nomenclatura

**Tipo de Refatoracao:** Rename Variable + Code Cleanup

**O que fazer:**
- Corrigir `llmComminicator` -> `llmCommunicator` em [`LLMTransformationService.java:34`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java:34)
- Padronizar nomenclatura de variaveis e metodos
- Usar convencoes Java (camelCase)

**O que resolve:**
- Legibilidade: Codigo mais claro
- Profissionalismo: Codigo sem erros de digitacao
- Manutenibilidade: Menos confusao

#### Passo 1.4: Padronizar uso de Lombok

**Tipo de Refatoracao:** Code Cleanup + Consistency

**O que fazer:**
- Usar `@Getter` e `@Setter` em vez de `@Data` quando necessario
- Padronizar uso de `@Builder` vs `@SuperBuilder`
- Usar `@Value` para objetos imutaveis

**O que resolve:**
- Evita bugs sutis com Lombok
- Consistência no codigo
- Codigo mais legivel

---

### Fase 2: Separacao de Responsabilidades (SRP)

#### Passo 2.1: Separar ChatService

**Tipo de Refatoracao:** Extract Class + SRP

**O que fazer:**
- Extrair `VectorStoreOperations` de [`ChatService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/chat/ChatService.java)
- Extrair `PromptTemplateService` de `ChatService`
- Criar `ChatSessionService` para gerenciar sessoes
- Manter `ChatService` como orquestrador

**O que resolve:**
- SRP: Cada classe tem uma unica responsabilidade
- Manutenibilidade: Mudancas em vector store nao afetam chat
- Testabilidade: Testes isolados para cada responsabilidade

**Novo Design:**
```
ChatService (orquestracao)
├── ChatSessionService (gerenciamento de sessao)
├── VectorStoreOperations (acesso a vector store)
├── PromptTemplateService (manipulacao de templates)
└── ChatResponseHandler (tratamento de respostas)
```

#### Passo 2.2: Separar LLMTransformationService

**Tipo de Refatoracao:** Extract Class + SRP

**O que fazer:**
- Extrair `ImagePreprocessingService` para processamento de imagens
- Extrair `TextExtractionService` para extracao de texto
- Extrair `ImageBinarizationService` para binarizacao Otsu
- Manter `LLMTransformationService` como orquestrador

**O que resolve:**
- SRP: Classes menores e focadas
- Reutilizacao: Servicos podem ser usados independentemente
- Manutenibilidade: Facilita debugging

#### Passo 2.3: Separar OWL Services

**Tipo de Refatoracao:** Extract Interface + ISP

**O que fazer:**
- Criar `OWLReasoningService` para inferencias
- Criar `OWLParsingService` para parsing
- Criar `OWLOntologyManagementService` para gerenciamento
- Refatorar [`CoreOWLService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/owl/service/CoreOWLService.java) para usar os novos servicos

**O que resolve:**
- ISP: Interfaces menores e especificas
- Flexibilidade: Permite implementar diferentes reasoners
- Manutenibilidade: Codigo mais organizado

---

### Fase 3: Camada de Servico (REST)

#### Passo 3.1: Padronizar ServiceConfig com @Service e Injecao de Dependencia

**Tipo de Refatoracao:** Simplification + Spring Best Practices

**O que fazer:**
- Manter o padrao existente de services com injecao de dependencia via construtor
- Usar `@Service` do Spring para beans de servico
- Nao criar classes de configuracao `*ServiceConfig` com `@Configuration` e `@Bean`
- Usar `@Transactional` do Spring para controle transacional

**O que resolve:**
- Simplicidade: Padrao Ja existente no projeto
- Manutenibilidade: Menos camadas de abstracao desnecessarias
- Performance: Spring gerencia ciclo de vida dos beans

**Exemplo (manter padrao existente):**
```java
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatModel chatModel;
    private final VectorStoreOperations vectorStoreOperations;
    private final PromptTemplateService promptTemplateService;
    private final CoreTranslator translator;
    
    @Transactional
    public ChatResponse ask(String message) {
        // logica do servico
    }
}
```

#### Passo 3.2: Validacao via Jakarta Validation

**Tipo de Refatoracao:** Simplification + Consistency

**O que fazer:**
- Ja existem anotacoes Jakarta nos DTOs (completar se necessario)
- Criar `FormValidator` que usa `jakarta.validation.Validator`
- Validar o DTO completo via `validator.validate(dto)`
- Usar `CoreTranslator` para traducao de mensagens
- NAO criar classes validadoras separadas

**O que resolve:**
- DRY: Validacao definida no DTO, executada via API nativa Jakarta
- Manutenibilidade: Mudancas de validacao em um unico lugar (DTO)
- Consistência: Mesma validacao em REST e View
- Simplicidade: API nativa do Jakarta Validation
- Flexibilidade: Suporta todas as anotacoes sem mapeamento manual

**DTO com validacao Jakarta:**
```java
public class ComandoSistemaDTO {
    @NotNull(message = "{validation.comando.nome.required}")
    @Size(min = 3, max = 100, message = "{validation.comando.nome.size}")
    private String nome;
    
    @NotNull(message = "{validation.comando.finalidade.required}")
    private FinalidadeComandoEnum finalidade;
}
```

**FormValidator:**
```java
@Component
@RequiredArgsConstructor
public class FormValidator {
    private final jakarta.validation.Validator validator;
    private final CoreViewTranslator translator;
    
    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        
        if (!violations.isEmpty()) {
            ConstraintViolation<T> firstViolation = violations.iterator().next();
            String message = translator.translate(firstViolation.getMessage());
            throw new FormValidationException(message, dto);
        }
    }
}
```

**Propriedades i18n:**
```properties
validation.comando.nome.required=Nome do comando e obrigatorio
validation.comando.nome.size=Nome deve ter entre 3 e 100 caracteres
validation.comando.finalidade.required=Finalidade do comando e obrigatoria
```

---

### Fase 4: Camada de Apresentacao (View) - MVVM

#### Passo 4.1: Padronizar MVVM com ViewModelConfig Existente

**Tipo de Refatoracao:** Architectural Refactoring

**O que fazer:**
- Seguir o padrao ja existente de `FormViewModel` e `FormViewModelConfig`
- ViewModel recebe no construtor um `*FormViewModelConfig`, `CoreViewTranslator` e `FormValidator`
- O `*FormViewModelConfig` contem todos os managers necessarios (injetados pelo Spring)
- Usar `FormValidator` para validacao do DTO via Jakarta Validation
- NAO usar validacao via Binder do Vaadin
- NAO usar ObservableList

**O que resolve:**
- Separacao clara de responsabilidades
- Testabilidade de apresentacao
- Flexibilidade de UI
- Desacoplamento entre View e logica de negocio
- Simplicidade: Usa API nativa do Jakarta Validation
- Segue o padrao ja estabelecido no projeto

**Exemplo:**
```java
// FormViewModelConfig com managers injetados pelo Spring
public class ChatDialogViewModelConfig extends FormViewModelConfig<ChatRequestDTO> {
    private final ChatService chatService;
    
    public ChatDialogViewModelConfig(boolean readOnly, ChatService chatService) {
        super(readOnly);
        this.chatService = chatService;
    }
}

// ViewModel usando o config com validacao via FormValidator
public class ChatDialogViewModel extends FormViewModel<ChatRequestDTO> {
    
    public ChatDialogViewModel(ChatDialogViewModelConfig config, 
                               CoreViewTranslator translator,
                               FormValidator formValidator) {
        super(config, translator, formValidator);
        
        TextField messageField = new TextField(
            getTranslation(ChatRequestTranslator.MESSAGE));
        binder.forField(messageField)
            .bind(ChatRequestDTO::getMessage, ChatRequestDTO::setMessage);
    }
    
    public void sendMessage() {
        validateDto();
        // Enviar mensagem
    }
}

// View consumindo o ViewModel
@Route("chat")
public class ChatDialogView extends VerticalLayout {
    private final ChatDialogViewModel viewModel;
    
    public ChatDialogView(ChatDialogViewModel viewModel) {
        this.viewModel = viewModel;
        
        TextField messageField = new TextField();
        Button sendButton = new Button("Enviar");
        
        sendButton.addClickListener(e -> {
            try {
                viewModel.sendMessage();
            } catch (FormValidationException ex) {
                Notification.show(ex.getMessage());
            }
        });
    }
}
```

#### Passo 4.2: Criar BaseViewModel com FormValidator

**Tipo de Refatoracao:** Extract Class + DIP + Jakarta Validation

**O que fazer:**
- Criar `FormValidator` generico que usa `jakarta.validation.Validator`
- Injetar `FormValidator` no `FormViewModel`
- Usar `validator.validate(dto)` para validar o DTO
- NAO usar validacao via Binder do Vaadin
- NAO usar ObservableList - usar listas normais

**O que resolve:**
- Reutilizacao: Funcionalidades comuns em um lugar
- Consistência: ViewModels padronizados
- Manutenibilidade: Codigo mais organizado
- Simplicidade: Usa API nativa do Jakarta Validation
- Flexibilidade: Suporta todas as anotacoes Jakarta automaticamente

**Exemplo - FormValidator:**
```java
@Component
@RequiredArgsConstructor
public class FormValidator {
    private final jakarta.validation.Validator validator;
    private final CoreViewTranslator translator;
    
    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        
        if (!violations.isEmpty()) {
            ConstraintViolation<T> firstViolation = violations.iterator().next();
            String message = firstViolation.getMessage();
            String translatedMessage = translator.translate(message);
            
            throw new FormValidationException(translatedMessage, dto);
        }
    }
    
    public <T> List<String> validateAll(T dto) {
        return validator.validate(dto).stream()
            .map(v -> translator.translate(v.getMessage()))
            .collect(Collectors.toList());
    }
}
```

**Exemplo - FormViewModel base:**
```java
public abstract class FormViewModel<T extends Serializable> {
    protected final Binder<T> binder;
    protected final FormViewModelConfig<T> config;
    protected final CoreViewTranslator translator;
    protected final FormValidator formValidator;
    
    public FormViewModel(FormViewModelConfig<T> config, 
                         CoreViewTranslator translator,
                         FormValidator formValidator) {
        this.config = config;
        this.translator = translator;
        this.formValidator = formValidator;
        this.binder = new Binder<>();
    }
    
    protected String getTranslation(String key, Object... args) {
        return translator.translate(key, args);
    }
    
    protected void validateDto() {
        formValidator.validate(getDto());
    }
    
    protected abstract T getDto();
    protected abstract Class<T> getDtoClass();
}
```

**Exemplo - ViewModel concreto:**
```java
public class ComandoSistemaFormViewModel extends FormViewModel<ComandoSistemaDTO> {
    
    public ComandoSistemaFormViewModel(ComandoSistemaFormViewModelConfig config, 
                                       CoreViewTranslator translator,
                                       FormValidator formValidator) {
        super(config, translator, formValidator);
        
        TextField nomeField = new TextField(
            getTranslation(ComandoSistemaTranslator.NOME));
        binder.forField(nomeField)
            .bind(ComandoSistemaDTO::getNome, ComandoSistemaDTO::setNome);
    }
    
    public void save() {
        validateDto();
        // Salvamento
    }
}
```

#### Passo 4.3: Melhorar Clientes de View

**Tipo de Refatoracao:** Extract Class + Resilience

**O que fazer:**
- Refatorar [`ComandoSistemaClient`](ia-core/ia-core-llm-view/src/main/java/com/ia/core/llm/view/comando/ComandoSistemaClient.java)
- Implementar tratamento de erros com `@ExceptionHandler` ja existente
- Adicionar retry logic via Spring Retry (opcional)

**O que resolve:**
- Resiliencia: Tratamento de falhas de rede
- Performance: Retry automatico (se necessario)
- UX: Feedback ao usuario

---

### Fase 5: Camada de Dominio

#### Passo 5.1: Implementar Specification Pattern

**Tipo de Refatoracao:** Introduce Specification

**O que fazer:**
- Criar interface `Specification<T>`
- Implementar `AndSpecification`, `OrSpecification`, `NotSpecification`
- Refatorar [`FilterRequest`](ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/FilterRequest.java) para usar Specifications
- Integrar com [`SearchSpecification`](ia-core/ia-core-model/src/main/java/com/ia/core/model/specification/SearchSpecification.java)

**O que resolve:**
- Composicao: Specifications podem ser combinadas
- Reutilizacao: Specifications podem ser compartilhadas
- Legibilidade: Codigo mais expressivo

**Exemplo:**
```java
public interface Specification<T> {
    boolean isSatisfiedBy(T candidate);
    Specification<T> and(Specification<T> other);
    Specification<T> or(Specification<T> other);
    Specification<T> not();
}

public class ComandoSistemaAtivoSpecification implements Specification<ComandoSistema> {
    @Override
    public boolean isSatisfiedBy(ComandoSistema comando) {
        return comando.isAtivo();
    }
}
```

#### Passo 5.2: Implementar Domain Services

**Tipo de Refatoracao:** Extract Class + SRP

**O que fazer:**
- Criar servicos de dominio para logica complexa
- Separar logica de negocio de [`BaseEntity`](ia-core/ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java)
- Implementar invariantes de dominio

**O que resolve:**
- SRP: Logica de dominio centralizada
- Manutenibilidade: Regras de negocio em um lugar
- Testabilidade: Servicos de dominio testaveis

#### Passo 5.3: Implementar Repository Pattern

**Tipo de Refatoracao:** Extract Interface + DIP

**O que fazer:**
- Criar interfaces de repositorio especificas
- Implementar [`BaseEntityRepository`](ia-core/ia-core-service/src/main/java/com/ia/core/service/repository/BaseEntityRepository.java) com metodos customizados
- Usar `@Query` para consultas complexas

**O que resolve:**
- DIP: Camada de dominio depende de abstracoes
- Testabilidade: Repositorios mockaveis
- Performance: Consultas otimizadas

---

### Fase 6: Camada de Infraestrutura

#### Passo 6.1: Criar Infrastructure Adapters

**Tipo de Refatoracao:** Adapter Pattern

**O que fazer:**
- Criar `VectorStoreAdapter` para [`VectorStoreService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/vector/VectorStoreService.java)
- Criar `LLMModelAdapter` para ChatModel
- Criar `OWLReasonerAdapter` para reasoners

**O que resolve:**
- DIP: Dominio depende de abstracoes
- Flexibilidade: Troca de implementacoes de infraestrutura
- Testabilidade: Mocks para testes

#### Passo 6.2: Usar @Transactional do Spring

**Tipo de Refatoracao:** Simplification

**O que fazer:**
- Usar `@Transactional` do Spring para controle transacional
- NAO implementar UnitOfWork nem JpaUnitOfWork
- Configurar transacoes via anotacoes

**O que resolve:**
- Simplicidade: Ja integrado ao Spring
- Manutenibilidade: Codigo mais simples
- Performance: Spring otimiza transacoes

**Exemplo:**
```java
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatModel chatModel;
    
    @Transactional
    public ChatResponse ask(String message) {
        // logica transacional
    }
}
```

#### Passo 6.3: Manter Cache Existente

**Tipo de Refatoracao:** N/A

**O que fazer:**
- NAO refatorar nada de cache
- Manter configuracao existente de `@EnableCache`

**O que resolve:**
- Estabilidade: Cache ja funciona
- Performance: Nao impactar funcionalidades existentes

---

### Fase 7: Excecoes e Tratamento de Erros

#### Passo 7.1: Criar Hierarquia de Excecoes

**Tipo de Refatoracao:** Replace Exception with Assertion

**O que fazer:**
- Criar `DomainException` base
- Criar `ValidationException`, `NotFoundException`, `ConflictException`
- Criar `InfrastructureException` para erros de infra
- Usar mensagens de i18n via `CoreTranslator`

**O que resolve:**
- Excecoes especificas: Tratamento granular
- Logging: Classificacao de erros
- API: Respostas HTTP apropriadas

**Exemplo:**
```java
public class ValidationException extends DomainException {
    public ValidationException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}

// Uso
throw new ValidationException("validation.comando.nome.invalid.prefix");
```

#### Passo 7.2: Usar Exception Handler Existente

**Tipo de Refatoracao:** N/A

**O que fazer:**
- JA EXISTE [`CoreRestControllerAdvice`](ia-core/ia-core-rest/src/main/java/com/ia/core/rest/error/CoreRestControllerAdvice.java)
- NAO implementar novo padrao
- Apenas extender o existente se necessario

**O que resolve:**
- Consistência: Usa codigo ja existente
- Manutenibilidade: Menos codigo para manter

#### Passo 7.3: Manter Tratamento de Erros Existente

**Tipo de Refatoracao:** N/A

**O que fazer:**
- NAO implementar ErrorDTO nem ValidationErrorDTO
- Manter tratamento de erros ja existente
- Usar `CoreTranslator` para mensagens de erro

**O que resolve:**
- Estabilidade: Ja funciona
- Simplicidade: Menos codigo

---

### Fase 8: Internacionalizacao (i18n)

#### Passo 8.1: Padronizar Arquivos i18n

**Tipo de Refatoracao:** Code Cleanup + Consistency

**O que fazer:**
- Criar arquivos i18n para todos os submodulos de servico
- Padronizar nomenclatura de chaves
- Usar convencao de nomenclatura hierarquica

**O que resolve:**
- Internacionalizacao: Suporte a multiplos idiomas
- Manutenibilidade: Chaves organizadas
- Consistência: Nomenclatura padronizada

**Exemplo:**
```properties
# translations_llm_service_model_pt_BR.properties
# Validacoes
validation.comando.nome.required=Nome do comando e obrigatorio
validation.comando.nome.size=Nome deve ter entre 3 e 100 caracteres

# Erros
error.comando.notfound=Comando nao encontrado
error.comando.duplicate=Comando ja existe

# Mensagens
message.comando.created=Comando criado com sucesso
message.comando.updated=Comando atualizado com sucesso
```

#### Passo 8.2: Implementar Cache de Traducoes

**Tipo de Refatoracao:** Performance Optimization

**O que fazer:**
- JA EXISTE `CoreTranslator` para REST
- JA EXISTE `CoreViewTranslator` para View
- Implementar cache de traducoes para melhorar performance
- Usar Spring Cache para cache de traducoes

**O que resolve:**
- Performance: Reducao de consultas a arquivos de traducao
- Escalabilidade: Melhor uso de recursos
- Centralizacao: Traducoes gerenciadas em um lugar

**Exemplo:**
```java
@Service
@RequiredArgsConstructor
public class CoreTranslator {
    private final MessageSource messageSource;
    private final CacheManager cacheManager;
    
    public String translate(String key, Object... args) {
        return cacheManager.getCache("translations").get(key, () -> {
            return messageSource.getMessage(key, args, Locale.getDefault());
        });
    }
}
```

#### Passo 8.3: Remover Strings Hardcoded

**Tipo de Refatoracao:** Replace Magic String with Constant + i18n

**O que fazer:**
- Substituir strings hardcoded por referencias a i18n
- Criar constantes para chaves de i18n
- Usar `CoreTranslator` ou `CoreViewTranslator` em Views

**O que resolve:**
- Internacionalizacao: Suporte a multiplos idiomas
- Manutenibilidade: Strings centralizadas
- Consistência: Mensagens padronizadas

---

### Fase 9: Testabilidade

#### Passo 9.1: Criar Test Fixtures

**Tipo de Refatoracao:** Introduce Fixture

**O que fazer:**
- Criar `EntityBuilder` para entidades de teste
- Criar `DTOBuilder` para DTOs de teste
- Criar `UseCaseTestFixture` para use cases

**O que resolve:**
- DRY: Reutilizacao de codigo de teste
- Consistência: Dados de teste padronizados
- Manutenibilidade: Mudancas em entidades refletidas em fixtures

**Exemplo:**
```java
public class ComandoSistemaDTOBuilder {
    private ComandoSistemaDTO dto = new ComandoSistemaDTO();
    
    public static ComandoSistemaDTOBuilder builder() {
        return new ComandoSistemaDTOBuilder();
    }
    
    public ComandoSistemaDTOBuilder withNome(String nome) {
        dto.setNome(nome);
        return this;
    }
    
    public ComandoSistemaDTO build() {
        return dto;
    }
}
```

#### Passo 9.2: Implementar Test Doubles

**Tipo de Refatoracao:** Introduce Stub/Mock

**O que fazer:**
- Criar `FakeRepository` para testes de integracao
- Criar `MockService` para testes de unidade
- Usar Mockito para testes complexos

**O que resolve:**
- Testes isolados: Sem dependencia de banco de dados
- Performance: Testes rapidos
- Cobertura: Maior cobertura de codigo

#### Passo 9.3: Criar Integration Tests

**Tipo de Refatoracao:** Introduce Integration Test

**O que fazer:**
- Criar testes de integracao para Services
- Criar testes de integracao para Controllers
- Criar testes de integracao para Views

**O que resolve:**
- Confianca: Testes de integracao validam comportamento
- Regressao: Detecta bugs em mudancas
- Documentacao: Testes como documentacao

---

### Fase 10: Documentacao e Padronizacao

#### Passo 10.1: Criar Coding Standards

**Tipo de Refatoracao:** Documentation

**O que fazer:**
- Criar documento de convencoes de nomenclatura
- Definir estrutura de pacotes
- Criar checklist de code review

**O que resolve:**
- Consistência: Codigo uniforme
- Onboarding: Novos devs se adaptam rapido
- Manutenibilidade: Codigo previsivel

#### Passo 10.2: Criar Architectural Decision Records

**Tipo de Refatoracao:** Documentation

**O que fazer:**
- Documentar decisoes de design
- Criar template ADR
- Manter historico de decisoes

**O que resolve:**
- Contexto: Decisoes documentadas
- Evolucao: Historico de mudancas arquiteturais
- Comunicacao: Alinhamento de equipe

#### Passo 10.3: Completar Javadoc

**Tipo de Refatoracao:** Documentation

**O que fazer:**
- Adicionar Javadoc completo para todas as classes publicas
- Documentar parametros e retornos
- Adicionar exemplos de uso

**O que resolve:**
- Documentacao: Codigo auto-documentado
- IDE: Autocomplete com documentacao
- Manutenibilidade: Codigo mais facil de entender

---

## Ordem de Prioridade

| Prioridade | Passo | Impacto | Risco | Arquitetura |
|------------|-------|---------|-------|-------------|
| 1 | 1.1 - Validacao Jakarta em DTOs | Alto | Baixo | Service |
| 2 | 1.2 - Validacao Generica no FormViewModel | Alto | Baixo | View |
| 3 | 1.3 - Corrigir Typos | Medio | Baixo | Geral |
| 4 | 2.1 - Separar ChatService | Medio | Medio | Service |
| 5 | 3.1 - Manter Padrao Service com @Service | Alto | Baixo | Service |
| 6 | 4.1 - Padronizar MVVM com ViewModelConfig | Alto | Medio | View |
| 7 | 4.2 - FormViewModel com Validacao Generica | Alto | Baixo | View |
| 8 | 5.1 - Specification Pattern | Medio | Baixo | Domain |
| 9 | 7.1 - Hierarquia de Excecoes | Medio | Baixo | Geral |
| 10 | 8.2 - Cache de Traducoes | Alto | Baixo | Geral |

---

## Metricas de Sucesso

1. **Cobertura de Testes:** Alcancar 80% de cobertura
2. **Acoplamento:** Reduzir dependencias entre modulos
3. **Complexidade:** Manter complexidade ciclomatica < 10
4. **Legibilidade:** Codigo autoexplicativo
5. **Manutenibilidade:** Facilidade de mudanca
6. **Internacionalizacao:** 100% das strings traduziveis em i18n
7. **Validacao:** 100% dos DTOs com validacao Jakarta

---

## Notas de Implementacao

### Warnings de Compilacao Identificados

1. **[`LLMTransformationService.java:34`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java:34)** - `llmComminicator` com typo (deve ser `llmCommunicator`)
2. **[`VectorStoreService.java:31-33`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/vector/VectorStoreService.java:31)** - Metodo `getQuestionAnswerAdvisor()` sem documentacao
3. **[`BaseEntity.java:55`](ia-core/ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java:55)** - `TSID.Factory.getTsid4096()` pode lancrar NullPointerException

### Arquivos para Remocao

- Classes duplicadas em `ia-core-llm-view` e `ia-core-view`
- Codigo comentado nao utilizado
- Imports nao utilizados

### Padroes ja Existentes (NAO REFATORAR)

1. **Service Configuration**: Ja existe padrao com `@Service` e injecao via construtor
2. **ViewModel**: Ja existe `FormViewModel` e `FormViewModelConfig`
3. **Translator**: Ja existe `CoreTranslator` (REST) e `CoreViewTranslator` (View)
4. **Exception Handler**: Ja existe `CoreRestControllerAdvice`
5. **Cache**: Ja existe configuracao de cache
6. **Transaction**: Ja existe `@Transactional` do Spring
7. **DTO.CAMPOS**: Ja existe padrao de classe interna com constantes para nomes dos campos
8. **Translator.VALIDATION**: Ja existe padrao de classe interna para mensagens de validacao
9. **FormValidator**: NOVO - Criar componente que usa `jakarta.validation.Validator`

### O que NAO Fazer

1. **NAO criar** classes `*ServiceConfig` com `@Configuration` e `@Bean`
2. **NAO implementar** Unit of Work ou JpaUnitOfWork
3. **NAO implementar** ErrorDTO ou ValidationErrorDTO
4. **NAO implementar** novo Exception Handler
5. **NAO refatorar** cache existente
6. **NAO usar** ObservableList
7. **NAO implementar** novo padrao de traducao

### Boas Praticas Spring Boot

- Usar `@Service` para beans de servico
- Usar `@Transactional` para controle transacional
- Usar `@ConfigurationProperties` para configuracoes
- Usar `@Profile` para ambientes diferentes
- Usar `@ConditionalOnProperty` para features opcionais
- Usar `@Async` para operacoes assincronas (se necessario)

### Boas Praticas Vaadin

- Usar `Binder` para validacao automatica
- Usar `@Route` para definicao de rotas
- Usar `@CssImport` para estilos
- Usar `@Theme` para temas
- Usar `@PreserveOnRefresh` para estado
- Usar `FormViewModel` e `FormViewModelConfig` ja existentes

---

## Estrategia de Migracao

### Big Bang vs Incremental

**Recomendado: Incremental**

1. Criar nova estrutura paralela
2. Migrar modulo por modulo
3. Manter compatibilidade com codigo antigo
4. Feature flags para codigo novo/antigo
5. Rollback possivel a qualquer momento

### Versionamento

- v1.0: Estado atual
- v1.1: Validacao Jakarta completa
- v1.2: Separacao de responsabilidades
- v1.3: Padrao Service simplificado
- v1.4: MVVM refatorado
- v1.5: i18n completo
- v2.0: Arquitetura completa

---

## Referencias

- **SOLID:** Uncle Bob - Clean Architecture
- **Clean Code:** Robert Martin - Clean Code
- **Domain-Driven Design:** Eric Evans
- **Pattern of Enterprise Application Architecture:** Martin Fowler
- **Spring Boot Best Practices:** Spring Documentation
- **Vaadin Best Practices:** Vaadin Documentation
- **MVVM Pattern:** Microsoft Documentation
