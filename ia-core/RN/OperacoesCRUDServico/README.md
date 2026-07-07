# Regras de Negócio - Módulo Operações CRUD Serviço

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Operações CRUD Serviço do ia-core-apps.

## Referência
- **CDU**: CDU019-OperacoesCRUDServico
- **Service**: ia-core-service
- **Módulo**: ia-core-service, ia-core-model

## Entidades

### CRUDService
Representa as operações CRUD padronizadas do CrudBaseService.

#### Regras Implementadas

##### CRU_001 - ValidacaoAntesPersistenciaRule
- **Nome**: Validação Antes da Persistência
- **Descrição**: Garante que a validação seja executada antes de qualquer persistência no banco
- **Critérios**:
  - DTO é validado antes do save/update
  - Todos os validadores são executados
  - Erros de validação impedem a persistência
- **Severidade**: ERRO
- **Referência CDU**: CDU019-OperacoesCRUDServico

##### CRU_002 - EventoDominioPublicadoAposEscritaRule
- **Nome**: Evento de Domínio Publicado Após Escrita
- **Descrição**: Garante que eventos de domínio sejam publicados após operações de escrita
- **Critérios**:
  - Evento CREATED é publicado após save bem-sucedido
  - Evento UPDATED é publicado após update bem-sucedido
  - Evento DELETED é publicado após delete bem-sucedido
- **Severidade**: INFO
- **Referência CDU**: CDU019-OperacoesCRUDServico

##### CRU_003 - OperacaoLeituraReadOnlyRule
- **Nome**: Operação de Leitura Read-Only
- **Descrição**: Garante que operações de leitura sejam configuradas como read-only
- **Critérios**:
  - Métodos findById e findAll usam @TransactionalReadOnly
  - readOnly = true é configurado
  - Não permite modificações
- **Severidade**: ERRO
- **Referência CDU**: CDU019-OperacoesCRUDServico

##### CRU_004 - OperacaoEscritaTransacionalRule
- **Nome**: Operação de Escrita Transacional
- **Descrição**: Garante que operações de escrita sejam transacionais
- **Critérios**:
  - Métodos save, update, delete usam @TransactionalWrite
  - Transação é commitada após sucesso
  - Transação é revertida após falha
- **Severidade**: ERRO
- **Referência CDU**: CDU019-OperacoesCRUDServico

##### CRU_005 - EntidadeNaoEncontradaLancaExcecaoRule
- **Nome**: Entidade Não Encontrada Lança Exceção
- **Descrição**: Garante que busca por entidade inexistente lance ServiceException
- **Critérios**:
  - findById lança ServiceException com código ENTITY_NOT_FOUND
  - Mensagem de erro é internacionalizada
- **Severidade**: ERRO
- **Referência CDU**: CDU019-OperacoesCRUDServico

##### CRU_006 - ViolaIntegridadeDadosLancaExcecaoRule
- **Nome**: Violação de Integridade de Dados Lança Exceção
- **Descrição**: Garante que violação de restrições do banco lance ServiceException
- **Critérios**:
  - Erro de integridade lança ServiceException
  - Código DATA_INTEGRITY_VIOLATION é usado
  - Detalhes do erro são logados
- **Severidade**: ERRO
- **Referência CDU**: CDU019-OperacoesCRUDServico

##### CRU_007 - ConversaoDTORealizadaRule
- **Nome**: Conversão DTO Realizada
- **Descrição**: Garante que a conversão DTO ↔ Model seja realizada corretamente
- **Critérios**:
  - Mapper converte DTO para Model antes do processamento
  - Mapper converte Model para DTO após persistência
  - Dados não são perdidos na conversão
- **Severidade**: ERRO
- **Referência CDU**: CDU019-OperacoesCRUDServico

## Validadores

Operações CRUD são orquestradas pelo CrudBaseService.

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<EntidadeDTO> {
    @Override
    public String getCode() {
        return "CRU_001";
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
    public void validate(EntidadeDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`