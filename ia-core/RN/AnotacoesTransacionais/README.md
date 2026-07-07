# Regras de Negócio - Módulo Anotações Transacionais

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Anotações Transacionais do ia-core-apps.

## Referência
- **CDU**: CDU021-AnotacoesTransacionais
- **Service**: ia-core-service
- **Módulo**: ia-core-service

## Entidades

### AnotacaoTransacional
Representa anotações transacionais customizadas para controle de comportamento.

#### Regras Implementadas

##### TRN_001 - TransactionalWriteUsaREQUIREDRule
- **Nome**: @TransactionalWrite Usa REQUIRED
- **Descrição**: Garante que @TransactionalWrite use propagation REQUIRED
- **Critérios**:
  - @TransactionalWrite configura propagation = REQUIRED
  - readOnly = false é configurado
  - timeout padrão é 30 segundos
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

##### TRN_002 - TransactionalReadOnlyUsaReadOnlyRule
- **Nome**: @TransactionalReadOnly Usa readOnly
- **Descrição**: Garante que @TransactionalReadOnly use readOnly = true
- **Critérios**:
  - @TransactionalReadOnly configura readOnly = true
  - propagation = REQUIRED é usado
  - Otimização de performance para leituras
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

##### TRN_003 - TransactionalMandatoryExigeTransacaoExistenteRule
- **Nome**: @TransactionalMandatory Exige Transação Existente
- **Descrição**: Garante que @TransactionalMandatory exija transação existente
- **Critérios**:
  - @TransactionalMandatory configura propagation = MANDATORY
  - Lança exceção se não houver transação
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

##### TRN_004 - NonTransactionalSuspendeTransacaoRule
- **Nome**: @NonTransactional Suspende Transação
- **Descrição**: Garante que @NonTransactional suspenda transação existente
- **Critérios**:
  - @NonTransactional configura propagation = NOT_SUPPORTED
  - Método executa sem transação
  - Transação anterior é retomada após conclusão
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

##### TRN_005 - TransactionalNestedCriaTransacaoAninhadaRule
- **Nome**: @TransactionalNested Cria Transação Aninhada
- **Descrição**: Garante que @TransactionalNested crie transação aninhada
- **Critérios**:
  - @TransactionalNested configura propagation = NESTED
  - Permite rollback parcial
  - Transação pai não é afetada
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

##### TRN_006 - TransactionalWriteIndependentUsaREQUIRES_NEWRule
- **Nome**: @TransactionalWriteIndependent Usa REQUIRES_NEW
- **Descrição**: Garante que @TransactionalWriteIndependent use REQUIRES_NEW
- **Critérios**:
  - Cria nova transação independente
  - Transação existente é suspensa
  - Útil para log de auditoria
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

##### TRN_007 - TimeoutTransacaoValidoRule
- **Nome**: Timeout da Transação Válido
- **Descrição**: Garante que o timeout configurado seja respeitado
- **Critérios**:
  - Timeout padrão é 30 segundos
  - Transações que excedem timeout são revertidas
  - TransactionTimedOutException é lançada
- **Severidade**: ERRO
- **Referência CDU**: CDU021-AnotacoesTransacionais

## Validadores

Anotações são validadas pelo Spring Transaction Interceptor.

## Padrão de Implementação

Anotações transacionais customizadas seguem o padrão:

```java
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, timeout = 30)
public @interface TransactionalWrite {
}

@Transactional(propagation = Propagation.REQUIRED, readOnly = true, timeout = 30)
public @interface TransactionalReadOnly {
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Spring Transaction Management Documentation