# Regras de Negócio - Módulo Tratamento Exceções Serviço

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Tratamento de Exceções de Serviço do ia-core-apps.

## Entidades

### ServiceException
Representa uma exceção específica para a camada de serviço com suporte a agregação de múltiplos erros.

#### Regras Implementadas

##### EXC_001 - CodigoPadraoDefinidoRule
- **Nome**: Código Padrão Definido
- **Descrição**: Construtor padrão usa código "SERVICE_ERROR"
- **Critérios**:
  - ServiceException() usa código padrão "SERVICE_ERROR"
  - Mensagem padrão "Erro de serviço" é definida
  - Permite identificação rápida de erros genéricos
- **Severidade**: INFO
- **Referência CDU**: CDU026-TratamentoExcecoesServico

##### EXC_002 - CausaPrimeiroErroRule
- **Nome**: Causa Atualizada no Primeiro Erro
- **Descrição**: A causa original (Throwable) é mantida apenas no primeiro erro adicionado
- **Critérios**:
  - initCause() é chamado apenas no primeiro add()
  - Erros subsequentes são adicionados à coleção sem alterar causa
  - Permite rastreio correto da exceção raiz
- **Severidade**: INFO
- **Referência CDU**: CDU026-TratamentoExcecoesServico

##### EXC_003 - AgregacaoUsaHashSetRule
- **Nome**: Agregação Usa HashSet
- **Descrição**: Erros são mantidos em HashSet para evitar duplicatas
- **Critérios**:
  - Collection internamente é HashSet
  - Duplicatas são automaticamente ignoradas
  - Performance O(1) para verificação de existência
- **Severidade**: INFO
- **Referência CDU**: CDU026-TratamentoExcecoesServico

##### EXC_004 - MensagemConcatenadaRule
- **Nome**: Mensagem Concatenada
- **Descrição**: getMessage() concatena todas as mensagens de erro
- **Critérios**:
  - Mensagens são concatenadas via reduce(String::join)
  - Retorna null se não houver erros
  - Permite exibição única de todos os erros
- **Severidade**: INFO
- **Referência CDU**: CDU026-TratamentoExcecoesServico

##### EXC_005 - RetrocompatibilidadeMantidaRule
- **Nome**: Retrocompatibilidade Mantida
- **Descrição**: Código existente que usa ServiceException continua funcionando
- **Critérios**:
  - Construtores existentes não são modificados
  - Comportamento legacy é preservado
  - Novos métodos são aditivos
- **Severidade**: INFO
- **Referência CDU**: CDU026-TratamentoExcecoesServico

##### EXC_006 - ThreadSafetyConsideradaRule
- **Nome**: Thread Safety Considerada
- **Descrição**: HashSet não é thread-safe; sincronização externa é necessária
- **Critérios**:
  - Documentação advertindo sobre uso concorrente
  - Recomenda sincronização externa em ambientes concorrentes
  - Alternative thread-safe pode ser implementada
- **Severidade**: AVISO
- **Referência CDU**: CDU026-TratamentoExcecoesServico

## Validadores

- `ServiceExceptionRules` - Validações complementares para ServiceException

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<ServiceException> {
    @Override
    public String getCode() {
        return "EXC_001";
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
    public void validate(ServiceException entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-011: Padrões de Tratamento de Exceções
- ADR-053: Usar CDU para Documentação de Casos de Uso
- Service Base: `com.ia.core.service.rules.BusinessRule`