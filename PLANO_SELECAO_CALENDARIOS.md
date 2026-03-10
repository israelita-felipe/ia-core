# Plano de Implementação: Seleção Múltipla de Calendários na Página de Visualização de Eventos

## 1. Visão Geral do Projeto

Este documento detalha o plano de implementação para adicionar uma funcionalidade de seleção múltipla de calendários na página de visualização de eventos por período (`EventoPeriodoPageView`). O objetivo é permitir que o usuário escolha quais calendários deseja visualizar no calendário, filtrando assim os eventos exibidos.

## 2. Análise do Estado Atual

### 2.1 Estrutura Atual

A implementação atual da página de visualização de eventos (`EventoPeriodoPageView`) apresenta a seguinte estrutura:

- **Arquivo**: `biblia-view/src/main/java/com/ia/biblia/view/evento/page/EventoPeriodoPageView.java`
- **ViewModel**: `biblia-view/src/main/java/com/ia/biblia/view/evento/page/EventoPeriodoPageViewModel.java`
- **Gerenciador**: `biblia-view/src/main/java/com/ia/biblia/view/evento/EventoManager.java`
- **Cliente**: `biblia-view/src/main/java/com/ia/biblia/view/evento/EventoClient.java`

### 2.2 Métodos Existentes

O método atual `getCalendar(startDate, endDate)` não suporta filtragem por calendários. A API REST fornece um endpoint que retorna todos os eventos de todos os calendários.

### 2.3 Classes Relevantes

- `CalendarioManager`: Gerenciador para manipular calendários
- `CalendarioClient`: Cliente Feign para comunicação com API de calendários
- `CalendarioDTO`: DTO que representa um calendário

## 3. Requisitos Funcionais

### 3.1 RF001: Seleção Múltipla de Calendários

O sistema deve apresentar uma caixa de seleção múltipla (multi-select combobox) que exiba todos os calendários disponíveis cadastrados no sistema.

**Critérios de Aceitação:**
- A caixa de seleção deve permitir múltiplas escolhas
- Por padrão, todos os calendários devem estar selecionados
- A seleção deve persistir durante a sessão do usuário

### 3.2 RF002: Filtragem de Eventos por Calendário

O sistema deve filtrar os eventos exibidos no calendário de acordo com os calendários selecionados.

**Critérios de Aceitação:**
- Apenas eventos dos calendários selecionados devem ser exibidos
- A filtragem deve ocorrer automaticamente ao selecionar/deselecionar calendários
- O calendário deve ser atualizado imediatamente após a mudança de seleção

### 3.3 RF003: Atualização Automática ao Mudar de Mês

Quando o usuário navegar para outro mês, os eventos filtrados devem ser carregados automaticamente.

**Critérios de Aceitação:**
- A seleção de calendários deve ser mantida ao navegar entre meses
- Os eventos devem ser recarregados com base nos calendários selecionados

## 4. Arquitetura da Solução

### 4.1 Diagrama de Classes

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    ARQUITETURA DA SOLUÇÃO                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                     EventoPeriodoPageView                            │   │
│  │  - EventoCalendarView calendarView                                  │   │
│  │  - MultiSelectComboBox<CalendarioDTO> calendarioSelector           │   │
│  │  + createCalendarioSelector(): MultiSelectComboBox                 │   │
│  │  + onCalendarioSelectionChange(List<CalendarioDTO>)                │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                 EventoPeriodoPageViewModel                          │   │
│  │  - List<CalendarioDTO> selectedCalendarios                          │   │
│  │  - List<CalendarioDTO> availableCalendarios                         │   │
│  │  + getAvailableCalendarios(): List<CalendarioDTO>                  │   │
│  │  + setSelectedCalendarios(List<CalendarioDTO>)                      │   │
│  │  + loadCalendarWithCalendarios(LocalDate, LocalDate, List<Long>)   │   │
│  │  + loadCalendarios()                                                │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      EventoManager                                  │   │
│  │  + getCalendar(LocalDate, LocalDate, List<Long>)                   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      EventoClient                                   │   │
│  │  + getCalendar(LocalDate, LocalDate, List<Long>): EventoCalendarDTO│   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│                         API REST (biblia-rest)                             │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 4.2 Fluxo de Dados

```
1. Usuário acessa a página EventoPeriodoPageView
                    │
                    ▼
2. ViewModel carrega lista de calendários disponíveis (loadCalendarios)
                    │
                    ▼
3. View exibe MultiSelectComboBox com calendários (todos selecionados por padrão)
                    │
                    ▼
4. Usuário seleciona/desseleciona calendários
                    │
                    ▼
5. View chama ViewModel.onCalendarioSelectionChange()
                    │
                    ▼
6. ViewModel chama EventoManager.getCalendar() com IDs dos calendários selecionados
                    │
                    ▼
7. EventoManager delega para EventoClient.getCalendar() com filtros
                    │
                    ▼
8. API REST retorna EventoCalendarDTO filtrado por calendários
                    │
                    ▼
9. View atualiza EventoCalendarView com os novos dados
```

## 5. Plano de Implementação

### 5.1 Fase 1: Modificações na API REST (biblia-rest)

#### 5.1.1 Modificar EventoController

**Arquivo**: `biblia-rest/src/main/java/com/ia/biblia/rest/evento/EventoController.java`

**Alterações necessárias:**
- Adicionar novo método endpoint que aceita lista de IDs de calendários como parâmetro
- O endpoint existente pode ser mantido para compatibilidade regressiva

**Código proposto:**

```java
/**
 * Retorna o calendário de eventos para um período específico, filtrado por calendários.
 *
 * @param startDate data inicial do período
 * @param endDate data final do período
 * @param calendariosIds lista opcional de IDs de calendários para filtrar (pode ser null ou vazio)
 * @return calendário de eventos
 */
@GetMapping("/calendar")
public ResponseEntity<EventoCalendarDTO> getCalendar(
    @RequestParam LocalDate startDate,
    @RequestParam LocalDate endDate,
    @RequestParam(required = false) List<Long> calendariosIds) {
    
    EventoCalendarDTO calendar = eventoService.getCalendar(startDate, endDate, calendariosIds);
    return ResponseEntity.ok(calendar);
}
```

#### 5.1.2 Modificar EventoService

**Arquivo**: `biblia-service/src/main/java/com/ia/biblia/service/evento/EventoService.java`

**Alterações necessárias:**
- Adicionar novo método que aceita lista de IDs de calendários
- Implementar lógica de filtragem

**Código proposto:**

```java
/**
 * Retorna o calendário de eventos para um período, opcionalmente filtrado por calendários.
 *
 * @param startDate data inicial
 * @param endDate data final
 * @param calendariosIds lista de IDs de calendários para filtrar (pode ser null)
 * @return calendário de eventos
 */
public EventoCalendarDTO getCalendar(LocalDate startDate, LocalDate endDate, 
                                     List<Long> calendariosIds) {
    // Se não há filtro, retorna todos os eventos
    if (calendariosIds == null || calendariosIds.isEmpty()) {
        return getCalendar(startDate, endDate);
    }
    
    // Otherwise, filter by calendar IDs
    EventoCalendarDTO calendar = getCalendar(startDate, endDate);
    // Filter occurrences by selected calendars
    // ... implementation
    return filteredCalendar;
}
```

### 5.2 Fase 2: Modificações no Cliente (biblia-view)

#### 5.2.1 Modificar EventoClient

**Arquivo**: `biblia-view/src/main/java/com/ia/biblia/view/evento/EventoClient.java`

**Alterações necessárias:**
- Adicionar novo método que aceita lista de IDs de calendários

**Código proposto:**

```java
/**
 * Retorna o calendário de eventos para um período específico, filtrado por calendários.
 *
 * @param startDate data inicial do período
 * @param endDate data final do período
 * @param calendariosIds lista opcional de IDs de calendários para filtrar
 * @return calendário de eventos
 */
@GetMapping("/calendar")
EventoCalendarDTO getCalendar(
    @RequestParam LocalDate startDate,
    @RequestParam LocalDate endDate,
    @RequestParam(required = false) List<Long> calendariosIds);
```

#### 5.2.2 Modificar EventoManager

**Arquivo**: `biblia-view/src/main/java/com/ia/biblia/view/evento/EventoManager.java`

**Alterações necessárias:**
- Adicionar novo método que aceita lista de IDs de calendários

**Código proposto:**

```java
/**
 * Retorna o calendário de eventos para um período específico, filtrado por calendários.
 *
 * @param startDate data inicial do período
 * @param endDate data final do período
 * @param calendariosIds lista de IDs de calendários para filtrar (pode ser null)
 * @return calendário de eventos
 */
public EventoCalendarDTO getCalendar(LocalDate startDate, LocalDate endDate, 
                                    List<Long> calendariosIds) {
    return getClient().getCalendar(startDate, endDate, calendariosIds);
}
```

### 5.3 Fase 3: Modificações no ViewModel

#### 5.3.1 Modificar EventoPeriodoPageViewModel

**Arquivo**: `biblia-view/src/main/java/com/ia/biblia/view/evento/page/EventoPeriodoPageViewModel.java`

**Alterações necessárias:**
- Adicionar campo para armazenar calendários disponíveis e selecionados
- Adicionar método para carregar calendários
- Adicionar método para carregar calendário com filtro de calendários
- Modificar métodos existentes para usar a seleção de calendários

**Código proposto:**

```java
@Getter
private List<CalendarioDTO> availableCalendarios = new ArrayList<>();

@Getter
private List<CalendarioDTO> selectedCalendarios = new ArrayList<>();

/**
 * Carrega a lista de calendários disponíveis.
 */
public void loadCalendarios() {
    try {
        availableCalendarios = getConfig().getCalendarioManager().findAll();
        selectedCalendarios = new ArrayList<>(availableCalendarios);
    } catch (Exception e) {
        handleError(e);
        availableCalendarios = new ArrayList<>();
    }
}

/**
 * Define os calendários selecionados e recarrega o calendário.
 *
 * @param calendarios lista de calendários selecionados
 */
public void setSelectedCalendarios(List<CalendarioDTO> calendarios) {
    this.selectedCalendarios = calendarios != null ? calendarios : new ArrayList<>();
    loadCalendar();
}

/**
 * Retorna os IDs dos calendários selecionados.
 *
 * @return lista de IDs ou null se todos estiverem selecionados
 */
public List<Long> getSelectedCalendariosIds() {
    if (selectedCalendarios == null || selectedCalendarios.isEmpty()) {
        return null;
    }
    if (selectedCalendarios.size() == availableCalendarios.size()) {
        return null; // Retorna null para buscar todos
    }
    return selectedCalendarios.stream()
        .map(CalendarioDTO::getId)
        .collect(Collectors.toList());
}

/**
 * Carrega os dados do calendário para o período atual com os calendários selecionados.
 */
public void loadCalendar() {
    try {
        setModel(getConfig().getEventoManager().getCalendar(startDate, endDate,
            getSelectedCalendariosIds()));
    } catch (Exception e) {
        handleError(e);
        setModel(null);
    }
}
```

### 5.4 Fase 4: Modificações na View

#### 5.4.1 Modificar EventoPeriodoPageViewModelConfig

**Arquivo**: `biblia-view/src/main/java/com/ia/biblia/view/evento/page/EventoPeriodoPageViewModelConfig.java`

**Alterações necessárias:**
- Adicionar injeção do CalendarioManager

**Código proposto:**

```java
/**
 * Configuração para EventoPeriodoPageViewModel.
 */
@Getter
public class EventoPeriodoPageViewModelConfig {
    private EventoManager eventoManager;
    private CalendarioManager calendarioManager;
    private boolean readOnly;
    
    public EventoPeriodoPageViewModelConfig(
            EventoManager eventoManager,
            CalendarioManager calendarioManager,
            boolean readOnly) {
        this.eventoManager = eventoManager;
        this.calendarioManager = calendarioManager;
        this.readOnly = readOnly;
    }
}
```

#### 5.4.2 Modificar EventoPeriodoPageViewModel (Construtor)

**Alterações necessárias:**
- Modificar construtor para chamar loadCalendarios()

**Código proposto:**

```java
public EventoPeriodoPageViewModel(EventoPeriodoPageViewModelConfig config) {
    super(config);
    this.eventoCalendarViewModel = createEventoCalendarViewModel(config);
    updateDateRange();
    loadCalendarios(); // NOVO: Carrega calendários disponíveis
    loadCalendar();
}
```

#### 5.4.3 Modificar EventoPeriodoPageView

**Arquivo**: `biblia-view/src/main/java/com/ia/biblia/view/evento/page/EventoPeriodoPageView.java`

**Alterações necessárias:**
- Adicionar import para MultiSelectComboBox
- Adicionar campo para o seletor de calendários
- Criar método para construir o seletor
- Adicionar listener para mudança de seleção
- Adicionar o seletor ao layout

**Componente Visual:**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  [◄] Janeiro 2024 [►]  [Hoje]           [Calendários: ☑ Geral ☑ Crianças]  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐                              │
│  │ Dom │ Seg │ Ter │ Qua │ Qui │ Sex │ Sáb │                              │
│  ├─────┼─────┼─────┼─────┼─────┼─────┼─────┤                              │
│  │     │     │  1  │  2  │  3  │  4  │  5  │                              │
│  │     │     │     │     │     │     │     │                              │
│  ├─────┼─────┼─────┼─────┼─────┼─────┼─────┤                              │
│  │  6  │  7  │  8  │  9  │ 10  │ 11  │ 12  │                              │
│  │     │     │     │     │     │     │     │                              │
│  └─────┴─────┴─────┴─────┴─────┴─────┴─────┘                              │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│  [Imprimir Calendário]  [Imprimir Escalas]                                  │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Código proposto:**

```java
// Novos imports
import com.ia.biblia.service.calendario.dto.CalendarioDTO;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

// Novo campo
private MultiSelectComboBox<CalendarioDTO> calendarioSelector;

// No construtor, após criar toolbar
toolbar.add(createCalendarioSelector(), createAlignRightLayout());

// Novo método
private MultiSelectComboBox<CalendarioDTO> createCalendarioSelector() {
    calendarioSelector = new MultiSelectComboBox<>();
    calendarioSelector.setLabel("Calendários");
    calendarioSelector.setItems(getViewModel().getAvailableCalendarios());
    calendarioSelector.setValue(getViewModel().getSelectedCalendarios());
    calendarioSelector.setWidth("300px");
    calendarioSelector.addValueChangeListener(event -> {
        getViewModel().setSelectedCalendarios(new ArrayList<>(event.getValue()));
        refreshCalendar();
    });
    return calendarioSelector;
}

// Helper para alinhar à direita
private HorizontalLayout createAlignRightLayout() {
    HorizontalLayout layout = new HorizontalLayout();
    layout.setWidthFull();
    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    return layout;
}

// Atualizar refreshCalendar
public void refreshCalendar() {
    calendarioSelector.setItems(getViewModel().getAvailableCalendarios());
    calendarioSelector.setValue(getViewModel().getSelectedCalendarios());
    calendarView.setCalendarData(getViewModel().getModel());
}
```

## 6. Testes

### 6.1 Testes Unitários

- Testar EventoService.getCalendar() com lista de IDs
- Testar EventoManager.getCalendar() com lista de IDs
- Testar EventoPeriodoPageViewModel com seleção de calendários

### 6.2 Testes de Integração

- Testar endpoint REST com filtro de calendários
- Testar a UI com o componente de seleção múltipla

### 6.3 Testes Manuais

- Verificar se a seleção múltipla funciona corretamente
- Verificar se a filtragem de eventos está correta
- Verificar se a navegação entre meses mantém a seleção

## 7. Riscos e Mitigações

| Risco | Probabilidade | Impacto | Mitigação |
|-------|---------------|---------|-----------|
| API REST não suporta filtragem | Média | Alto | Adicionar novo endpoint ou modificar existente |
| Performance com muitos calendários | Baixa | Médio | Implementar cache se necessário |
| Complexidade no componente UI | Média | Baixo | Usar componente pronto do Vaadin |

## 8. Estimativa de Esforço

| Fase | Atividade | Estimativa |
|------|-----------|------------|
| 1 | Modificações na API REST | 2 horas |
| 2 | Modificações no Cliente | 1 hora |
| 3 | Modificações no ViewModel | 2 horas |
| 4 | Modificações na View | 3 horas |
| 5 | Testes | 2 horas |
| **Total** | | **10 horas** |

## 9. Critérios de Aceitação

- [ ] A página EventoPeriodoPageView exibe uma caixa de seleção múltipla
- [ ] A caixa de seleção exibe todos os calendários cadastrados
- [ ] Por padrão, todos os calendários estão selecionados
- [ ] Selecionar/desselecionar calendários filtra os eventos exibidos
- [ ] A filtragem funciona corretamente ao navegar entre meses
- [ ] O sistema funciona corretamente sem regressões

## 10. Referências

- [Vaadin MultiSelectComboBox](https://vaadin.com/docs/latest/components/multi-select-combo-box)
- [BiblIA EventoManager](file:///home/israel/git/gestor-igreja/Biblia/biblia-view/src/main/java/com/ia/biblia/view/evento/EventoManager.java)
- [BiblIA EventoClient](file:///home/israel/git/gestor-igreja/Biblia/biblia-view/src/main/java/com/ia/biblia/view/evento/EventoClient.java)
