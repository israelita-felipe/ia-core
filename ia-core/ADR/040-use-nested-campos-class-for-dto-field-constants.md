# ADR-040: Usar Classe CAMPOS Aninhada para Constantes de Campos em DTOs

## Status

✅ Aceito

## Contexto

No desenvolvimento de aplicações com interfaces gráficas (Vaadin) e filtros de busca dinâmicos, é necessário referenciar os nomes dos campos de forma consistente e sem usar reflection. O projeto já utiliza uma abordão onde DTOs possuem uma classe aninhada `CAMPOS` com constantes estáticas `String` para cada atributo.

Esta prática foi identificada na classe `AbstractBaseEntityDTO` e implementada em DTOs específicos como `PessoaDTO`. O padrão evita:

- Uso de reflection para obter nomes de campos
- Strings hardcoded espalhadas pelo código
- Inconsistências entre nomes de campos em diferentes partes do sistema
- Dificuldades na manutenção quando campos são renomeados

### Regra de Nomenclatura DTO

**TODO DTO DEVE IMPLEMENTAR A INTERFACE DTO<> DIRETAMENTE OU INDIRETAMENTE E DEVE TER O SUFIXO `DTO` NO NOME DO ARQUIVO E DA CLASSE.**

Esta regra aplica-se a:
- DTOs simples: `PessoaDTO`, `EventoDTO`
- SearchRequestDTOs: `PessoaSearchRequestDTO`, `EventoSearchRequestDTO`
- Classes que extendem `AbstractDTO`, `AbstractBaseEntityDTO` ou `SearchRequestDTO`
- Classes que implementam diretamente a interface `DTO<T>`

## Decisão

Todos os DTOs devem implementar uma classe aninhada `CAMPOS` contendo constantes estáticas `String` para todos os campos do DTO, incluindo campos herdados. A classe deve seguir o padrão:

```java
@SuppressWarnings("javadoc")
public static class CAMPOS extends ParentDTO.CAMPOS {
    public static final String FIELD_NAME = "fieldName";
    // ... outros campos

    public static Set<String> values() {
        return Set.of(FIELD_NAME, /* ... */);
    }
}
```

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| **Classe CAMPOS aninhada** | - Sem reflection<br>- Type-safe<br>- IntelliSense<br>- Fácil manutenção | - Código adicional<br>- Deve ser mantido manualmente |
| Reflection com Field.getName() | - Automático<br>- Sem código extra | - Performance<br>- Segurança<br>- Fragilidade a ofuscação |
| Strings hardcoded | - Simples | - Erro-prone<br>- Difícil refatoração<br>- Inconsistente |
| Enums para campos | - Type-safe<br>- IntelliSense | - Verbose<br>- Não extensível facilmente |

### Critérios de Decisão

1. **Manutenibilidade**: Campos renomeados devem ser atualizados em um único lugar
2. **Performance**: Evitar reflection em runtime
3. **Type Safety**: Compilador deve detectar erros de nomes de campos
4. **Consistência**: Mesmo padrão em todo o projeto
5. **Facilidade de Uso**: Deve ser simples de implementar e usar

## Detalhes

### Implementação Técnica

#### Estrutura da Classe CAMPOS

```java
public class MeuDTO extends AbstractBaseEntityDTO<MinhaEntidade> {
    // ... campos do DTO

    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        public static final String NOME_CAMPO = "nomeCampo";
        // ... outros campos

        public static Set<String> values() {
            return Set.of(NOME_CAMPO, /* ... */);
        }
    }
}
```

#### Uso em Vaadin Binding

As constantes CAMPOS são essenciais para o binding bidirecional com componentes Vaadin:

```java
// Em FormView ou similar
bind(RecorrenciaDTO.CAMPOS.FREQUENCY, frequencyField);
bind(RecorrenciaDTO.CAMPOS.INTERVAL_VALUE, intervalValueField);
bind(RecorrenciaDTO.CAMPOS.BY_DAY, byDayField);
```

Este padrão garante:
- **Type Safety**: Nomes de campos validados em compile-time
- **Refatoração Segura**: Renomear campos atualiza automaticamente os bindings
- **Consistência**: Mesmo nome usado em todo o código
- **Manutenibilidade**: Mudanças centralizadas na classe CAMPOS

#### Exemplo Completo: RecorrenciaDTO

```java
public class RecorrenciaDTO implements DTO<Recorrencia> {
    // ... campos

    public static class CAMPOS {
        public static final String FREQUENCY = "frequency";
        public static final String INTERVAL_VALUE = "intervalValue";
        public static final String BY_DAY = "byDay";
        public static final String BY_MONTH_DAY = "byMonthDay";
        public static final String BY_MONTH = "byMonth";
        public static final String BY_SET_POSITION = "bySetPosition";
        public static final String UNTIL_DATE = "untilDate";
        public static final String COUNT_LIMIT = "countLimit";
        public static final String WEEK_START_DAY = "weekStartDay";
        public static final String BY_YEAR_DAY = "byYearDay";
        public static final String BY_WEEK_NO = "byWeekNo";
        public static final String BY_HOUR = "byHour";
        public static final String BY_MINUTE = "byMinute";
        public static final String BY_SECOND = "bySecond";
    }
}
```

#### Exemplo de Uso em RecorrenciaFormView

```java
public class RecorrenciaFormView extends FormView<RecorrenciaDTO> {

    private void createFrequencyField(FormLayout layout) {
        frequencyField = createFrequencyField(label, help);
        layout.add(frequencyField, 2);
        bind(RecorrenciaDTO.CAMPOS.FREQUENCY, frequencyField); // Binding type-safe
    }

    private void createByDayField(FormLayout layout) {
        byDayField = createByDayField(label, help);
        layout.add(byDayField, 6);
        bind(RecorrenciaDTO.CAMPOS.BY_DAY, byDayField); // Binding type-safe
    }
}
```

### Estrutura de Classes

```
AbstractBaseEntityDTO
├── CAMPOS (ID, VERSION)
│   └── values()
└── MeuDTO
    ├── CAMPOS extends AbstractBaseEntityDTO.CAMPOS (NOME_CAMPO, OUTRO_CAMPO)
    │   └── values() - inclui campos próprios + herdados
    └── campos do DTO
```

## Consequências

### Positivas

- ✅ **Type Safety**: Erros de nomes de campos detectados em compile-time
- ✅ **Performance**: Sem reflection em runtime
- ✅ **Manutenibilidade**: Campos renomeados em um único lugar
- ✅ **Consistência**: Padrão uniforme em todo o projeto
- ✅ **IntelliSense**: Autocomplete em IDEs
- ✅ **Refatoração Segura**: Ferramentas de refatoração atualizam as constantes

### Negativas

- ❌ **Código Adicional**: Classe CAMPOS deve ser mantida manualmente
- ❌ **Disciplina**: Desenvolvedores devem lembrar de atualizar a classe CAMPOS

## Status de Implementação

✅ **COMPLETO**

- [x] Padrão definido em AbstractBaseEntityDTO
- [x] Implementado em PessoaDTO e outros DTOs
- [x] Uso em Vaadin binding (firePropertyChange)
- [x] Uso em filtros de busca (propertyFilters)
- [x] Documentação criada

## Data

2024-12-19

## Revisores

- Israel Araújo

## Referências

1. **AbstractBaseEntityDTO**: Classe base implementando o padrão
2. **PessoaDTO**: Exemplo de implementação completa
3. **Vaadin Property Binding**: Uso em interfaces gráficas
4. **Specification Pattern**: Uso em filtros dinâmicos (ADR-002)

## Histórico de Revisões

| Versão | Data | Autor | Descrição |
|--------|------|-------|-----------|
| 1.0 | 2024-12-19 | Israel Araújo | Versão inicial |

