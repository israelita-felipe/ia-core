# Regras de Negócio - Módulo Contexto Execução Serviço

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Contexto Execução Serviço do ia-core-apps.

## Referência
- **CDU**: CDU020-ContextoExecucaoServico
- **Service**: ia-core-service
- **Módulo**: ia-core-service

## Entidades

### ServiceExecutionContext
Representa o contexto de execução de uma operação de serviço.

#### Regras Implementadas

##### CTX_001 - ContextoImutavelAposPersistenciaRule
- **Nome**: Contexto Imutável Após Persistência
- **Descrição**: Garante que o contexto não possa ser modificado após a persistência
- **Critérios**:
  - Contexto é imutável após setSavedEntity
  - Tentativa de modificação é ignorada ou lança exceção
- **Severidade**: ERRO
- **Referência CDU**: CDU020-ContextoExecucaoServico

##### CTX_002 - CancelamentoImpedePersistenciaRule
- **Nome**: Cancelamento Impede Persistência
- **Descrição**: Garante que operações canceladas não sejam persistidas
- **Critérios**:
  - Se isCancelled() retorna true, persistência é abortada
  - CancelReason deve conter o motivo do cancelamento
- **Severidade**: ERRO
- **Referência CDU**: CDU020-ContextoExecucaoServico

##### CTX_003 - IsUpdateVerificaIDModeloRule
- **Nome**: IsUpdate Verifica ID do Modelo
- **Descrição**: Garante que isUpdate() identifique corretamente operações de atualização
- **Critérios**:
  - isUpdate() retorna true se model.getId() não é nulo
  - isUpdate() retorna false se model.getId() é nulo (criação)
- **Severidade**: INFO
- **Referência CDU**: CDU020-ContextoExecucaoServico

##### CTX_004 - DadosContextoConsistentesRule
- **Nome**: Dados do Contexto Consistentes
- **Descrição**: Garante que todos os dados do contexto sejam consistentes
- **Critérios**:
  - toSave, model, savedEntity e result devem estar consistentes
  - Dados de uma fase não são sobrescritos incorretamente
- **Severidade**: ERRO
- **Referência CDU**: CDU020-ContextoExecucaoServico

##### CTX_005 - GetToSaveRetornaDTOOriginalRule
- **Nome**: GetToSave Retorna DTO Original
- **Descrição**: Garante que getToSave retorne o DTO original fornecido
- **Critérios**:
  - getToSave retorna o DTO exatamente como foi passado
  - Sem modificações durante a execução
- **Severidade**: INFO
- **Referência CDU**: CDU020-ContextoExecucaoServico

##### CTX_006 - CancelReasonInformadoRule
- **Nome**: CancelReason Informado
- **Descrição**: Garante que o motivo do cancelamento seja registrado
- **Critérios**:
  - Quando cancel() é chamado, cancelReason é armazenado
  - Motivo pode ser nulo ou descrição detalhada
- **Severidade**: AVISO
- **Referência CDU**: CDU020-ContextoExecucaoServico

## Validadores

O contexto é validado implicitamente pelos serviços que o utilizam.

## Padrão de Implementação

ServiceExecutionContext é um container de estado, não uma regra de negócio formal. Sua implementação segue:

```java
public class ServiceExecutionContext<D, T> {
    private D toSave;
    private T model;
    private T savedEntity;
    private D result;
    private boolean cancelled;
    private String cancelReason;

    public void cancel(String cancelReason) {
        this.cancelled = true;
        this.cancelReason = cancelReason;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    // ... outros métodos
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.context.ServiceExecutionContext`