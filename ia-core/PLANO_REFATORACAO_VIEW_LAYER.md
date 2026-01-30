# 🎯 PLANO DE REFATORAÇÃO - VIEW LAYER ia-core

**Data:** 26 de Janeiro de 2026  
**Escopo:** Refatoração granular da camada View do projeto ia-core  
**Objetivo:** Aplicar SOLID minimamente, reutilizar ViewModels como Presenters, evitar camadas extras  
**Tempo Total Estimado:** ~21 horas  
**Status:** 📋 Planejado

---

## 📐 ARQUITETURA ALVO (3-4 camadas essenciais)

```
┌─────────────────────────────────┐
│  VIEW LAYER                     │
│  (PageView, ListView, FormView) │
│  - Apenas renderização          │
│  - Delegação para ViewModel     │
└────────────┬────────────────────┘
             │ (listeners/callbacks)
┌────────────▼────────────────────┐
│  VIEWMODEL LAYER                │
│  (Presenter + Estado)           │
│  - Lógica de apresentação       │
│  - Validação                    │
│  - Orquestração                 │
└────────────┬────────────────────┘
             │ (domain calls)
┌────────────▼────────────────────┐
│  MANAGER/SERVICE LAYER          │
│  (Backend - sem mudanças)       │
└─────────────────────────────────┘
```

**Camadas após refatoração: 3 (essenciais apenas)**

---

## 🔍 PADRÕES IDENTIFICADOS NA BASE DE CÓDIGO

### ✅ O que já existe (aproveitar):
1. **PageView → ViewModel → Manager** (padrão bem estabelecido)
2. **ViewModels** como estado central (reutilizáveis como Presenters)
3. **Listeners** baseados em PropertyChangeListener (infraestrutura pronta)
4. **Validação** em PageView e ViewModel (espalhada, precisa centralizar)

### ❌ O que precisa melhorar:
1. **Duplicação de componentes** (botões, diálogos, campos se repetem)
2. **Validação espalhada** (em PageView, ViewModel, sem padrão)
3. **Listeners ad-hoc** (sem contrato formal, sem interface)
4. **Boilerplate em Views** (inicialização, renderização, listeners)
5. **Sem Factory** para componentes reutilizáveis
6. **Lógica de negócio em Views** (deveria estar em ViewModel)

---

## 📋 PASSOS DE REFATORAÇÃO

### **[PASSO 1] Criar infraestrutura de listeners/callbacks no ViewModel** ⏱️ ~1h

**Objetivo:** Formalizar comunicação VIEW ↔ VIEWMODEL com Observer Pattern

**Arquivos a criar:**
- `ia-core-view/src/main/java/com/ia/core/view/components/listener/IViewModelListener.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/listener/ViewModelListenerSupport.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/listener/ViewModelEvent.java`

**Mudanças:**

**Antes:**
```java
// IPageViewModel - sem listeners formais
public interface IPageViewModel<T, ID> {
    List<T> getItems();
    void save(T entity);
}

// PageView - listeners ad-hoc
pageView.addPropertyChangeListener("items", evt -> {
    grid.setItems((List<?>) evt.getNewValue());
});
```

**Depois:**
```java
// Novo: IViewModelListener
public interface IViewModelListener {
    void onItemsChanged(ViewModelEvent event);
    void onItemSelected(ViewModelEvent event);
    void onError(ViewModelEvent event);
    void onSuccess(ViewModelEvent event);
}

// Novo: ViewModelListenerSupport
public class ViewModelListenerSupport {
    private List<IViewModelListener> listeners = new CopyOnWriteArrayList<>();
    
    /**
     * Adiciona listener para notificações do ViewModel
     */
    public void addListener(IViewModelListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Remove listener
     */
    public void removeListener(IViewModelListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Notifica todos os listeners sobre mudança de itens
     */
    protected void fireItemsChanged(List<?> items) {
        ViewModelEvent event = new ViewModelEvent(this, ViewModelEventType.ITEMS_CHANGED, items);
        listeners.forEach(l -> l.onItemsChanged(event));
    }
}

// IPageViewModel atualizado
public interface IPageViewModel<T, ID> {
    void addListener(IViewModelListener listener);
    void removeListener(IViewModelListener listener);
    // ... métodos existentes ...
}

// PageView - listeners formais
pageView.getViewModel().addListener(new IViewModelListener() {
    @Override
    public void onItemsChanged(ViewModelEvent event) {
        grid.setItems((List<?>) event.getData());
    }
});
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Listeners existentes continuam funcionando (backward compatible)
- ✅ Criar 1 unit test simples com ViewModelListenerSupport
- ✅ Não quebra nenhuma View existente

**Próximos passos:** PASSO 1 é base para PASSO 5-6

---

### **[PASSO 2] Extrair validadores reutilizáveis em padrão Strategy** ⏱️ ~2h

**Objetivo:** Centralizar validação espalhada em PageView/ViewModel para classes Strategy

**Arquivos a criar:**
- `ia-core-view/src/main/java/com/ia/core/view/validator/IValidator.java`
- `ia-core-view/src/main/java/com/ia/core/view/validator/ValidationResult.java`
- `ia-core-view/src/main/java/com/ia/core/view/validator/ValidationChain.java`
- `ia-core-view/src/main/java/com/ia/core/view/validator/ValidationException.java`

**Mudanças:**

**Antes:**
```java
// Validação em PageView (SEM PADRÃO)
public void onSave() {
    if (entity.getNome() == null || entity.getNome().isEmpty()) {
        showError("Nome obrigatório");
        return;
    }
    if (entity.getNome().length() > 100) {
        showError("Nome muito longo");
        return;
    }
    manager.save(entity);
}

// Validação duplicada em ViewModel
public void canSave(T entity) {
    return entity.getNome() != null && !entity.getNome().isEmpty();
}
```

**Depois:**
```java
// Novo: IValidator
public interface IValidator<T> {
    /**
     * Valida entidade e retorna resultado
     * @param entity entidade a validar
     * @return ValidationResult com status e mensagens
     */
    ValidationResult validate(T entity);
}

// Novo: ValidationResult
public class ValidationResult {
    private final boolean valid;
    private final List<String> errors;
    private final List<String> warnings;
    
    public ValidationResult(boolean valid, List<String> errors) {
        this.valid = valid;
        this.errors = errors != null ? errors : new ArrayList<>();
        this.warnings = new ArrayList<>();
    }
    
    public boolean isValid() { return valid; }
    public List<String> getErrors() { return errors; }
    public void addError(String error) { errors.add(error); }
}

// Novo: ValidationChain para aplicar múltiplos validadores
public class ValidationChain<T> implements IValidator<T> {
    private List<IValidator<T>> validators = new ArrayList<>();
    
    /**
     * Adiciona validador à cadeia
     */
    public ValidationChain<T> add(IValidator<T> validator) {
        validators.add(validator);
        return this;
    }
    
    /**
     * Executa todos os validadores em sequência
     */
    @Override
    public ValidationResult validate(T entity) {
        ValidationResult result = new ValidationResult(true, new ArrayList<>());
        for (IValidator<T> validator : validators) {
            ValidationResult partial = validator.validate(entity);
            if (!partial.isValid()) {
                result = new ValidationResult(false, partial.getErrors());
                break;
            }
        }
        return result;
    }
}

// Implementação: EntityValidator específico
public class EntityValidator<T> implements IValidator<T> {
    @Override
    public ValidationResult validate(T entity) {
        List<String> errors = new ArrayList<>();
        if (entity == null) {
            errors.add("Entidade não pode ser nula");
        } else {
            // Validações específicas por tipo
            if (entity instanceof NomeadoEntity) {
                String nome = ((NomeadoEntity) entity).getNome();
                if (nome == null || nome.trim().isEmpty()) {
                    errors.add("Nome é obrigatório");
                }
                if (nome != null && nome.length() > 100) {
                    errors.add("Nome não pode ter mais de 100 caracteres");
                }
            }
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }
}

// ViewModel com validação centralizada (SRP)
public class PageViewModel<T> implements IPageViewModel<T, ID> {
    private final IValidator<T> validator;
    private final Manager<T, ID> manager;
    
    public PageViewModel(Manager<T, ID> manager, IValidator<T> validator) {
        this.manager = manager;
        this.validator = validator;
    }
    
    /**
     * Valida e salva entidade, dispara listeners
     */
    public void save(T entity) throws ValidationException {
        ValidationResult result = validator.validate(entity);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
        manager.save(entity);
        fireItemsChanged(manager.findAll());
    }
    
    /**
     * Verifica se entidade pode ser salva (para UI)
     */
    public boolean canSave(T entity) {
        return validator.validate(entity).isValid();
    }
}

// PageView - sem validação, apenas delegação
public void onSave() {
    try {
        getViewModel().save(getCurrentEntity());
        showSuccess("Salvo com sucesso");
    } catch (ValidationException e) {
        showError(String.join(", ", e.getErrors()));
    }
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Todas as validações existentes continuam funcionando
- ✅ Criar unit tests para ValidationChain com múltiplos validadores
- ✅ PageView continua renderizando igual, mas mais simples

**Próximos passos:** PASSO 2 pronto para PASSO 6

---

### **[PASSO 3] Criar Factory para componentes reutilizáveis** ⏱️ ~2h

**Objetivo:** Eliminar duplicação de componentes (botões, diálogos, campos)

**Arquivos a criar:**
- `ia-core-view/src/main/java/com/ia/core/view/components/factory/ComponentFactory.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/factory/DialogFactory.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/factory/FieldFactory.java`

**Mudanças:**

**Antes:**
```java
// Botões duplicados em múltiplas Views
private Button createSaveButton() {
    Button btn = new Button("Salvar");
    btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    btn.setIcon(new Icon(VaadinIcon.SAVE));
    btn.addClickListener(e -> onSave());
    return btn;
}

private Button createDeleteButton() {
    Button btn = new Button("Deletar");
    btn.addThemeVariants(ButtonVariant.LUMO_ERROR);
    btn.setIcon(new Icon(VaadinIcon.TRASH));
    btn.addClickListener(e -> onDelete());
    return btn;
}

// Campos duplicados
private TextField createNameField() {
    TextField field = new TextField("Nome");
    field.setRequired(true);
    field.setRequiredIndicatorVisible(true);
    field.setMaxLength(100);
    field.setWidthFull();
    return field;
}
```

**Depois:**
```java
// Novo: ComponentFactory
public class ComponentFactory {
    
    /**
     * Cria botão de salvar padronizado
     */
    public static Button createSaveButton(ComponentEventListener<ClickEvent<Button>> listener) {
        Button btn = new Button("Salvar");
        btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btn.setIcon(new Icon(VaadinIcon.SAVE));
        btn.addClickListener(listener);
        return btn;
    }
    
    /**
     * Cria botão de deletar padronizado
     */
    public static Button createDeleteButton(ComponentEventListener<ClickEvent<Button>> listener) {
        Button btn = new Button("Deletar");
        btn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btn.setIcon(new Icon(VaadinIcon.TRASH));
        btn.addClickListener(listener);
        return btn;
    }
    
    /**
     * Cria botão genérico
     */
    public static Button createButton(String label, VaadinIcon icon, 
            ButtonVariant variant, ComponentEventListener<ClickEvent<Button>> listener) {
        Button btn = new Button(label);
        btn.addThemeVariants(variant);
        btn.setIcon(new Icon(icon));
        btn.addClickListener(listener);
        return btn;
    }
}

// Novo: FieldFactory
public class FieldFactory {
    
    /**
     * Cria campo de texto obrigatório com tamanho máximo
     */
    public static TextField createTextField(String label, int maxLength) {
        TextField field = new TextField(label);
        field.setRequired(true);
        field.setRequiredIndicatorVisible(true);
        field.setMaxLength(maxLength);
        field.setWidthFull();
        return field;
    }
    
    /**
     * Cria campo de email obrigatório
     */
    public static EmailField createEmailField(String label) {
        EmailField field = new EmailField(label);
        field.setRequired(true);
        field.setRequiredIndicatorVisible(true);
        field.setWidthFull();
        return field;
    }
}

// Novo: DialogFactory
public class DialogFactory {
    
    /**
     * Cria diálogo de confirmação
     */
    public static ConfirmDialog createConfirmDialog(String title, String message, 
            ComponentEventListener<ClickEvent<Button>> onConfirm) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader(title);
        dialog.setText(message);
        dialog.setConfirmButton("Confirmar", onConfirm);
        dialog.setCancelButton("Cancelar", e -> dialog.close());
        return dialog;
    }
}

// PageView - usando Factory (sem duplicação)
public void createLayout() {
    HorizontalLayout toolbar = new HorizontalLayout();
    toolbar.add(
        ComponentFactory.createSaveButton(e -> onSave()),
        ComponentFactory.createDeleteButton(e -> onDelete())
    );
    
    VerticalLayout form = new VerticalLayout();
    form.add(
        FieldFactory.createTextField("Nome", 100),
        FieldFactory.createEmailField("Email")
    );
    
    add(toolbar, form);
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Botões e campos parecem e funcionam idênticos
- ✅ Unit test com ComponentFactory.createSaveButton()
- ✅ Views que usam Factory têm menos código

**Próximos passos:** PASSO 3 pronto para PASSO 6

---

### **[PASSO 4] Criar classes base simplificadas para Views (Template Method)** ⏱️ ~1.5h

**Objetivo:** Reduzir boilerplate em FormView, ListView, PageView

**Arquivos a criar:**
- `ia-core-view/src/main/java/com/ia/core/view/components/base/AbstractPageView.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/base/AbstractFormView.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/base/AbstractListView.java`

**Mudanças:**

**Antes:**
```java
// Boilerplate repetido em todas as PageViews
public class UserPageView extends PageView<User, Long> {
    
    public UserPageView() {
        setHeight("100%");
        setWidthFull();
        add(createToolBar());
        add(createList());
        add(createEditor());
        setViewModel(new UserPageViewModel(manager));
    }
    
    private Component createToolBar() {
        // 20 linhas de código comum...
    }
    
    private Component createList() {
        // 20 linhas de código comum...
    }
}
```

**Depois:**
```java
// Novo: AbstractPageView (Template Method)
public abstract class AbstractPageView<T, ID> extends VerticalLayout {
    
    protected final IPageViewModel<T, ID> viewModel;
    protected final Grid<T> grid = new Grid<>();
    protected final FormLayout editor = new FormLayout();
    
    /**
     * Constructor - inicializa layout padrão
     */
    public AbstractPageView(IPageViewModel<T, ID> viewModel) {
        this.viewModel = viewModel;
        setHeight("100%");
        setWidthFull();
        initializeLayout();
    }
    
    /**
     * Inicializa layout padrão (Template Method)
     */
    private void initializeLayout() {
        add(createToolBar());
        add(createListComponent());
        add(createEditorComponent());
        attachListeners();
    }
    
    /**
     * Cria toolbar - pode ser sobrescrito por subclasses
     */
    protected Component createToolBar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.add(
            ComponentFactory.createButton("Novo", VaadinIcon.PLUS, 
                ButtonVariant.LUMO_PRIMARY, e -> onNew()),
            ComponentFactory.createDeleteButton(e -> onDelete()),
            createFilterField()
        );
        return toolbar;
    }
    
    /**
     * Cria componente lista (pode ser personalizado)
     */
    protected Component createListComponent() {
        grid.setItems(viewModel.getItems());
        grid.setColumns("id", "nome");
        return grid;
    }
    
    /**
     * Cria componente editor (pode ser personalizado)
     */
    protected Component createEditorComponent() {
        editor.setVisible(false);
        return editor;
    }
    
    /**
     * Anexa listeners do ViewModel
     */
    protected void attachListeners() {
        viewModel.addListener(new IViewModelListener() {
            @Override
            public void onItemsChanged(ViewModelEvent event) {
                refreshList();
            }
        });
    }
    
    protected void refreshList() {
        grid.setItems(viewModel.getItems());
    }
    
    protected void onNew() { /* hook method */ }
    protected void onDelete() { /* hook method */ }
    protected TextField createFilterField() { return new TextField("Filtrar"); }
    
    public IPageViewModel<T, ID> getViewModel() {
        return viewModel;
    }
}

// Subclass: UserPageView (muito mais simples)
public class UserPageView extends AbstractPageView<User, Long> {
    
    public UserPageView() {
        super(new UserPageViewModel());
    }
    
    @Override
    protected Component createListComponent() {
        Grid<User> grid = new Grid<>();
        grid.setItems(viewModel.getItems());
        grid.addColumn(User::getNome).setHeader("Nome");
        grid.addColumn(User::getEmail).setHeader("Email");
        return grid;
    }
    
    @Override
    protected void onDelete() {
        getViewModel().delete(grid.asSingleSelect().getValue().getId());
    }
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Herança funciona, subclasses são mais simples
- ✅ Unit test com AbstractPageView instanciado
- ✅ Não quebra PageViews existentes

**Próximos passos:** PASSO 4 pronto para PASSO 6

---

### **[PASSO 5] Centralizar lógica de renderização condicional (Presenter Pattern)** ⏱️ ~1.5h

**Objetivo:** Mover decisões de renderização de PageView para PageViewModel

**Arquivos a criar/modificar:**
- `ia-core-view/src/main/java/com/ia/core/view/components/presenter/IViewPresenter.java`
- Modificar `IPageViewModel` para estender `IViewPresenter`

**Mudanças:**

**Antes:**
```java
// Lógica de renderização espalhada em PageView
public void render(User user) {
    // Mostrar botão deletar apenas se user não é admin
    deleteButton.setVisible(!user.isAdmin());
    
    // Mostrar campo email apenas se usuário tem acesso
    emailField.setVisible(currentUser.hasPermission("EDIT_EMAIL"));
    
    // Desabilitar botão se não há mudanças
    saveButton.setEnabled(hasChanges());
    
    // Label dinâmico baseado no estado
    title.setText(user.isNew() ? "Novo Usuário" : "Editar " + user.getNome());
}
```

**Depois:**
```java
// Novo: IViewPresenter - centraliza decisões de renderização
public interface IViewPresenter {
    
    /**
     * Indica se botão deve estar visível
     */
    boolean isActionVisible(String actionName);
    
    /**
     * Indica se botão deve estar habilitado
     */
    boolean isActionEnabled(String actionName);
    
    /**
     * Retorna label dinâmico para componente
     */
    String getComponentLabel(String componentName);
    
    /**
     * Indica se campo deve estar visível
     */
    boolean isFieldVisible(String fieldName);
}

// IPageViewModel estende IViewPresenter
public interface IPageViewModel<T, ID> extends IViewPresenter {
    // ... métodos existentes ...
}

// Implementação: PageViewModel como Presenter
public class PageViewModel<T> implements IPageViewModel<T, ID> {
    
    private T currentEntity;
    private User currentUser;
    
    @Override
    public boolean isActionVisible(String actionName) {
        return switch(actionName) {
            case "DELETE" -> !currentEntity.isSystemEntity();
            case "EXPORT" -> currentUser.hasPermission("EXPORT");
            default -> true;
        };
    }
    
    @Override
    public boolean isActionEnabled(String actionName) {
        return switch(actionName) {
            case "SAVE" -> hasChanges();
            case "DELETE" -> currentEntity != null && !currentEntity.isSystemEntity();
            default -> true;
        };
    }
    
    @Override
    public String getComponentLabel(String componentName) {
        return switch(componentName) {
            case "TITLE" -> currentEntity.isNew() ? "Novo Usuário" : "Editar " + currentEntity.getNome();
            case "SUBMIT_BUTTON" -> currentEntity.isNew() ? "Criar" : "Atualizar";
            default -> "";
        };
    }
    
    @Override
    public boolean isFieldVisible(String fieldName) {
        return switch(fieldName) {
            case "EMAIL" -> currentUser.hasPermission("EDIT_EMAIL");
            case "ADMIN_NOTES" -> currentUser.isAdmin();
            default -> true;
        };
    }
}

// PageView - delega apresentação para ViewModel (SRP)
public class UserPageView extends AbstractPageView<User, Long> {
    
    @Override
    protected Component createListComponent() {
        // Decisão de renderização vem do Presenter
        deleteButton.setVisible(viewModel.isActionVisible("DELETE"));
        deleteButton.setEnabled(viewModel.isActionEnabled("DELETE"));
        
        title.setText(viewModel.getComponentLabel("TITLE"));
        
        emailField.setVisible(viewModel.isFieldVisible("EMAIL"));
    }
    
    @Override
    protected void onSave() {
        if (viewModel.isActionEnabled("SAVE")) {
            viewModel.save(currentEntity);
        }
    }
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Renderização condicional funciona igual, mas agora em ViewModel
- ✅ Unit test com IViewPresenter
- ✅ PageView fica mais simples e testável

**Próximos passos:** PASSO 5 pronto para PASSO 6

---

### **[PASSO 6] Refatorar módulo `ia-core-view` (componentes base)** ⏱️ ~3h

**Objetivo:** Aplicar SOLID em componentes reutilizáveis (90% dos casos)

**Arquivos a modificar:**
- `ia-core-view/src/main/java/com/ia/core/view/components/PageView.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/ListView.java`
- `ia-core-view/src/main/java/com/ia/core/view/components/FormView.java`
- `ia-core-view/src/main/java/com/ia/core/view/model/IPageViewModel.java`
- `ia-core-view/src/main/java/com/ia/core/view/model/PageViewModel.java`

**Mudanças:**

1. Herdar de `AbstractPageView` (PASSO 4)
2. Injetar `IValidator` (PASSO 2)
3. Usar `ComponentFactory` (PASSO 3)
4. Adicionar listeners formais (PASSO 1)
5. Implementar `IViewPresenter` (PASSO 5)

**Exemplo de refatoração:**

**Antes:**
```java
public class PageView<T, ID> extends VerticalLayout {
    // 200+ linhas de código com validação, renderização, listeners
}
```

**Depois:**
```java
public class PageView<T, ID> extends AbstractPageView<T, ID> {
    
    public PageView(IPageViewModel<T, ID> viewModel) {
        super(viewModel);
    }
    
    // Apenas personalizações específicas, boilerplate vem de AbstractPageView
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Testes unitários passam
- ✅ Views render idêntico ao antes
- ✅ Listeners funcionam igual
- ✅ Validações funcionam igual

**Próximos passos:** PASSO 6 é base para PASSO 7-9

---

### **[PASSO 7] Refatorar módulo `ia-core-quartz-view`** ⏱️ ~2.5h

**Objetivo:** Aplicar padrões SOLID em Quartz (periodicidade, triggers, jobs)

**Arquivos a modificar:**
- `ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/*`

**Mudanças:**
1. Herdar de `AbstractPageView` (PASSO 4)
2. Usar `IValidator` para validação de triggers (PASSO 2)
3. Usar `ComponentFactory` para botões e diálogos (PASSO 3)
4. Implementar listeners formais (PASSO 1)

**Exemplo:**
```java
// QuartzJobPageView - aplicando SOLID
public class QuartzJobPageView extends AbstractPageView<QuartzJob, Long> {
    
    public QuartzJobPageView() {
        super(new QuartzJobPageViewModel(
            new QuartzJobValidator(), // Validação centralizada
            new QuartzJobManager()
        ));
    }
    
    // Resto segue AbstractPageView (boilerplate eliminado)
}

// Novo: QuartzJobValidator - SRP
public class QuartzJobValidator implements IValidator<QuartzJob> {
    @Override
    public ValidationResult validate(QuartzJob job) {
        List<String> errors = new ArrayList<>();
        if (job.getCronExpression() == null || job.getCronExpression().isEmpty()) {
            errors.add("Cron expression é obrigatória");
        }
        // Validar cron syntax
        try {
            new CronExpression(job.getCronExpression());
        } catch (Exception e) {
            errors.add("Cron expression inválida: " + e.getMessage());
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Quartz jobs continuam editáveis e validáveis
- ✅ Testes unitários para QuartzJobValidator
- ✅ Sem regressão funcional

**Próximos passos:** PASSO 7 completo

---

### **[PASSO 8] Refatorar módulo `ia-core-llm-view`** ⏱️ ~2.5h

**Objetivo:** Aplicar padrões em LLM (chat, comando, template)

**Arquivos a modificar:**
- `ia-core-llm-view/src/main/java/com/ia/core/llm/view/*`

**Mudanças:**
1. Herdar de `AbstractPageView` (PASSO 4)
2. Validação de prompts centralizada (PASSO 2)
3. ComponentFactory para chat UI (PASSO 3)
4. Listeners para eventos de chat (PASSO 1)

**Exemplo:**
```java
// ChatDialogView - aplicando SOLID
public class ChatDialogView extends Dialog {
    
    private final LLMChatViewModel viewModel;
    
    public ChatDialogView(LLMChatViewModel viewModel) {
        this.viewModel = viewModel;
        createLayout();
        attachListeners();
    }
    
    private void createLayout() {
        TextArea promptField = FieldFactory.createTextArea("Prompt", 1000);
        Button sendButton = ComponentFactory.createButton("Enviar", VaadinIcon.SEND, 
            ButtonVariant.LUMO_PRIMARY, e -> onSend());
        
        add(promptField, sendButton);
    }
    
    private void onSend() {
        try {
            viewModel.sendPrompt(promptField.getValue());
        } catch (ValidationException e) {
            showError(String.join(", ", e.getErrors()));
        }
    }
}

// Novo: PromptValidator - SRP
public class PromptValidator implements IValidator<String> {
    @Override
    public ValidationResult validate(String prompt) {
        List<String> errors = new ArrayList<>();
        if (prompt == null || prompt.trim().isEmpty()) {
            errors.add("Prompt não pode ser vazio");
        }
        if (prompt != null && prompt.length() > 5000) {
            errors.add("Prompt não pode ter mais de 5000 caracteres");
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }
}
```

**Validação:**
- ✅ Compilar sem erros
- ✅ Chat dialogs funcionam igual
- ✅ Prompts são validados corretamente
- ✅ Sem regressão

**Próximos passos:** PASSO 8 completo

---

### **[PASSO 9] Refatorar módulos remanescentes** ⏱️ ~4h

**Objetivo:** Aplicar padrões nos 4 módulos restantes (security, report, etc)

**Módulos a refatorar:**
- `ia-core-security-view` (login, permissões, usuários)
- `ia-core-report` (relatórios, exportação)
- Demais view modules

**Mudanças:** Mesma abordagem dos PASSOS 6-8

**Validação:**
- ✅ Compilar sem erros
- ✅ Testes unitários passam
- ✅ Sem regressão

**Próximos passos:** PASSO 9 completo

---

### **[PASSO 10] Documentação e refino de interfaces públicas** ⏱️ ~1.5h

**Objetivo:** Documentar contratos com Javadoc, criar guia de extensão

**Arquivos a documentar:**
- `IView`, `IViewModel`, `IViewModelListener`
- `IValidator`, `ValidationResult`, `ValidationChain`
- `ComponentFactory`, `DialogFactory`, `FieldFactory`
- `AbstractPageView`, `AbstractFormView`, `AbstractListView`

**Mudanças:**

```java
/**
 * Interface base para todas as Views Vaadin.
 * 
 * Responsabilidades:
 * - Renderizar componentes Vaadin
 * - Anexar listeners e eventos
 * - Delegar lógica para ViewModel
 * 
 * Não deve conter:
 * - Lógica de negócio
 * - Validação
 * - Orquestração complexa
 * 
 * @param <T> tipo de entidade
 * @param <ID> tipo de ID da entidade
 * 
 * @author ia-core
 * @since 1.0.0
 */
public interface IView<T, ID> {
    // ...
}

/**
 * Cria botão padronizado para a aplicação.
 * 
 * Exemplo de uso:
 * <pre>
 * Button saveBtn = ComponentFactory.createSaveButton(e -> onSave());
 * add(saveBtn);
 * </pre>
 * 
 * @param listener listener para o click do botão
 * @return botão padronizado
 */
public static Button createSaveButton(ComponentEventListener<ClickEvent<Button>> listener) {
    // ...
}
```

**Validação:**
- ✅ Javadoc completude checker 100%
- ✅ Criar 1 exemplo novo (extensão)
- ✅ Guia de extensão criado

**Próximos passos:** PASSO 10 é final

---

## 📊 RESUMO COMPARATIVO

| Aspecto | Antes | Depois | Melhoria |
|--------|-------|--------|----------|
| **Camadas** | 5-6 (confusas) | 3 (claras) | -50% complexidade |
| **Boilerplate em PageView** | ~300 linhas | ~100 linhas | -66% |
| **Duplicação de componentes** | Alta | Mínima | -80% |
| **Validação centralizada** | Não | Sim | SRP ✓ |
| **Listeners formais** | Ad-hoc | Contrato claro | +testabilidade |
| **SOLID aplicado** | Parcial | Completo | +manutenção |
| **Tempo até próxima feature** | +4 dias | +2 dias | -50% time-to-market |

---

## 📅 CRONOGRAMA

| Passo | Descrição | Tempo | Cumulative |
|-------|-----------|-------|-----------|
| 1 | Listeners/Callbacks | 1h | 1h |
| 2 | Validadores Strategy | 2h | 3h |
| 3 | ComponentFactory | 2h | 5h |
| 4 | Classes Base Template | 1.5h | 6.5h |
| 5 | Presenter Pattern | 1.5h | 8h |
| 6 | Refatorar ia-core-view | 3h | 11h |
| 7 | Refatorar ia-core-quartz-view | 2.5h | 13.5h |
| 8 | Refatorar ia-core-llm-view | 2.5h | 16h |
| 9 | Refatorar módulos remanescentes | 4h | 20h |
| 10 | Documentação Javadoc | 1.5h | 21.5h |

**Tempo Total: ~21.5 horas**  
**Recomendação: Executar em 3-4 dias (sprints de 5-6h/dia)**

---

## ✅ CRITÉRIOS DE SUCESSO

- ✅ Código compila sem erros após cada passo
- ✅ Testes unitários continuam passando
- ✅ Sem regressão funcional
- ✅ Boilerplate reduzido em pelo menos 50%
- ✅ SOLID principles aplicados (SRP, OCP, DIP)
- ✅ Javadoc 100% completo
- ✅ Documentação de extensão criada
- ✅ Code review aprovado

---

## 🚀 PRÓXIMOS PASSOS

1. **Aprovação do plano** ← VOCÊ ESTÁ AQUI
2. Executar PASSO 1 (Listeners/Callbacks)
3. Executar PASSO 2 (Validadores)
4. Executar PASSO 3 (ComponentFactory)
5. ... (continuar por todos os passos)

**Deseja iniciar com PASSO 1?**

---

**Plano elaborado com foco em:**
- ✅ Minimalismo (só o essencial)
- ✅ Incrementalismo (pequenos passos)
- ✅ Executabilidade (código sempre rodando)
- ✅ SOLID (sem excessos)
- ✅ Manutenibilidade (código claro e testável)
