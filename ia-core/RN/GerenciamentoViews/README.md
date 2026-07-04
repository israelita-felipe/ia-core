# Regras de Negócio - Módulo Gerenciamento Views

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Gerenciamento Views do ia-core-apps, para interfaces Vaadin.

## Entidades

### View
Representa uma interface de usuário Vaadin que segue o padrão IView.

### ViewModel
Representa o modelo de dados de uma view que segue o padrão IViewModel.

#### Regras Implementadas

##### VIW_001 - ViewImplementaIViewRule
- **Nome**: View Implementa IView
- **Descrição**: Garante que todas as views implementem a interface IView
- **Critérios**:
  - Views devem implementar interface IView
  - getViewModel() deve retornar instância válida
  - Translator deve estar configurado
- **Severidade**: ERRO
- **Referência CDU**: CDU022-GerenciamentoViews

##### VIW_002 - ViewModelImplementaIViewModelRule
- **Nome**: ViewModel Implementa IViewModel
- **Descrição**: Garante que todos os ViewModels implementem a interface IViewModel
- **Critérios**:
  - ViewModels devem implementar interface IViewModel
  - Estado de readOnly deve ser gerenciável
  - Métodos de estado devem funcionar corretamente
- **Severidade**: ERRO
- **Referência CDU**: CDU022-GerenciamentoViews

##### VIW_003 - MensagensErroInternacionalizadasRule
- **Nome**: Mensagens de Erro Internacionalizadas
- **Descrição**: Garante que todas as mensagens de erro sejam internacionalizadas
- **Critérios**:
  - Erros devem usar Translator para obtenção de mensagens
  - Chaves de tradução devem existir
  - Locale padrão é pt_BR
- **Severidade**: ERRO
- **Referência CDU**: CDU022-GerenciamentoViews

##### VIW_004 - HelpTextInternacionalizadoRule
- **Nome**: Help Text Internacionalizado
- **Descrição**: Garante que o texto de ajuda seja internacionalizado quando configurado
- **Critérios**:
  - Help text deve usar Translator quando presente
  - Chaves de tradução devem existir para help text
  - Fallback para texto original se tradução não encontrada
- **Severidade**: AVISO
- **Referência CDU**: CDU022-GerenciamentoViews

##### VIW_005 - TempoRenderizacaoAceitavelRule
- **Nome**: Tempo de Renderização Aceitável
- **Descrição**: Views devem ser renderizadas rapidamente para boa experiência do usuário
- **Critérios**:
  - Renderização deve ocorrer em menos de 200ms
  - Componentes devem ser inicializados eficientemente
  - Lazy loading pode ser usado quando necessário
- **Severidade**: INFO
- **Referência CDU**: CDU022-GerenciamentoViews

## Validadores

- `ViewValidator` - Orquestra regras de View
- `ViewModelValidator` - Orquestra regras de ViewModel

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<ViewDTO> {
    @Override
    public String getCode() {
        return "VIW_001";
    }

    @Override
    public String getName() {
        return "Minha Regra";
    }

    @Override
    public String getDescription() {
        return "Descrição da regra";
    }

    @Override
    public void validate(ViewDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-039: Vaadin TestBench para testes E2E
- ADR-053: Usar CDU para Documentação de Casos de Uso
- Service Base: `com.ia.core.service.rules.BusinessRule`