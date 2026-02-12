# ADR-008: Arquitetura MVVM com ViewModel e ViewModelConfig

## Status

✅ Aceito

## Contexto

O projeto precisa de uma arquitetura clara para a camada de apresentação que separe lógica de negócios de lógica de UI, facilitando testes e manutenção.

## Decisão

Usar **MVVM (Model-View-ViewModel)** com separação entre View (componentes UI), ViewModel (lógica de apresentação) e ViewModelConfig (injeção de dependências).

## Detalhes

### Estrutura do Módulo View

```
biblia-view/
├── src/main/java/com/ia/biblia/view/
│   ├── evento/
│   │   ├── page/EventoPageView.java          # View (Page)
│   │   ├── page/EventoPageViewModel.java     # ViewModel
│   │   ├── page/EventoPageViewModelConfig.java # Config
│   │   ├── form/EventoFormView.java          # View (Form)
│   │   ├── form/EventoFormViewModel.java     # ViewModel
│   │   └── list/EventoListView.java          # View (List)
│   ├── pessoa/
│   ├── familia/
│   └── ...
```

### ViewModel (Lógica de Apresentação)

```java
@Scope("prototype")
public class EventoPageViewModel extends ViewModel<EventoDTO> {

    private final EventoService eventoService;
    private final EventoTranslator translator;

    public EventoPageViewModel(EventoPageViewModelConfig config) {
        super(config);
        this.eventoService = config.getEventoService();
        this.translator = EventoTranslator.INSTANCE;
    }

    @Override
    protected void onSearch(SearchRequestDTO request) {
        Page<EventoDTO> page = eventoService.findAll(request);
        setItems(page.getContent());
        setTotalCount(page.getTotalElements());
    }

    public void loadEventoWithRelations(Long id) {
        EventoDTO evento = eventoService.findByIdWithAll(id);
        setSelectedItem(evento);
    }

    public List<LocalDTO> buscarLocais() {
        return localService.findAll();
    }
}
```

### ViewModelConfig (Injeção de Dependências)

```java
@Component
public class EventoPageViewModelConfig extends ViewModelConfig<EventoDTO> {

    private final EventoService eventoService;
    private final LocalService localService;

    @Autowired
    public EventoPageViewModelConfig(
            EventoClient client,
            EventoService eventoService,
            LocalService localService,
            SecurityManager securityManager) {
        super(client, securityManager);
        this.eventoService = eventoService;
        this.localService = localService;
    }

    @Override
    public EventoPageViewModel createViewModel() {
        return new EventoPageViewModel(this);
    }

    public EventoService getEventoService() {
        return eventoService;
    }

    public LocalService getLocalService() {
        return localService;
    }
}
```

### View (Componentes UI)

```java
public class EventoPageView extends View {

    private final EventoPageViewModel viewModel;

    public EventoPageView(EventoPageViewModelConfig config) {
        this.viewModel = config.createViewModel();
        buildUI();
    }

    private void buildUI() {
        // Composição de componentes
        EventoListView listView = new EventoListView(viewModel);
        EventoFormView formView = new EventoFormView(viewModel);
        
        add(listView);
        add(formView);
        
        viewModel.addPropertyChangeListener(evt -> refresh());
    }
}
```

## Boas Práticas

### 1. ViewModel Deve Conter Apenas Lógica de Apresentação

```java
// ✅ CORRETO - Lógica de apresentação
public class EventoPageViewModel {
    public void loadEventoWithRelations(Long id) {
        // Carrega com EntityGraph
        EventoDTO evento = eventoService.findByIdWithAll(id);
        setSelectedItem(evento);
    }
    
    public List<String> getEventoStatuses() {
        return Arrays.asList(EventoStatus.AGENDADO.name(), ...);
    }
}

// ❌ INCORRETO - Lógica de negócios
public class EventoPageViewModel {
    public void calcularEstatisticasEvento(Long eventoId) {
        // Isso deveria estar no serviço
    }
}
```

### 2. ViewModel Deve Ser `@Scope("prototype")`

```java
@Scope("prototype") // Nova instância por UI component
public class EventoPageViewModel extends ViewModel<EventoDTO> { ... }
```

### 3. Comunicação Via Observables

```java
public class EventoPageViewModel extends ViewModel<EventoDTO> {

    private final ObservableValue<EventoDTO> selectedItem = new ObservableValue<>();

    public void setSelectedItem(EventoDTO item) {
        this.selectedItem.setValue(item);
        firePropertyChange("selectedItem", null, item);
    }

    public ObservableValue<EventoDTO> selectedItemProperty() {
        return selectedItem;
    }
}
```

## Consequências

### Positivas

- ✅ Separação clara de responsabilidades
- ✅ Testabilidade (ViewModel pode ser testado unitariamente)
- ✅ Reusabilidade de ViewModels
- ✅ Injeção de dependências via Config
- ✅ Facilita binding de dados na UI

### Negativas

- ❌ Curva de aprendizado para desenvolvedores
- ❌ Mais classes para manter
- ❌ Requer disciplina para não misturar responsabilidades

## Status de Implementação

✅ **COMPLETO**

- 21+ ViewModels implementados
- 21+ ViewModelConfigs implementados
- Padrão aplicado em todos os módulos do Biblia

## Data

2024-03-25

## Revisores

- Team Lead
- Architect
