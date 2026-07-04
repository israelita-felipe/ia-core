# Regras de Negócio - Módulo Manter REST

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter REST do ia-core-apps.

## Referência
- **CDU**: CDU012-Manter-REST
- **Service**: ia-core-rest
- **Módulo**: ia-core-rest

## Entidades

### EndpointAPI
Representa um endpoint de API REST configurado no sistema.

#### Regras Implementadas

##### API_001 - PathUnicoRule
- **Nome**: Path Único
- **Descrição**: Garante que o path do endpoint seja único no sistema
- **Critérios**:
  - Path não pode ser duplicado
  - Comparação case-sensitive para paths
- **Severidade**: ERRO
- **Referência CDU**: RN001

##### API_002 - MetodoObrigatorioRule
- **Nome**: Método Obrigatório
- **Descrição**: Garante que o método HTTP seja informado e válido
- **Critérios**:
  - Método é obrigatório
  - Deve ser um dos: GET, POST, PUT, DELETE, PATCH
- **Severidade**: ERRO
- **Referência CDU**: RN002

##### API_003 - ControllerMetodoObrigatorioRule
- **Nome**: Controller e Método Obrigatórios
- **Descrição**: Garante que controller e método sejam informados e existam
- **Critérios**:
  - Controller é obrigatório
  - Método no controller é obrigatório
  - Sistema verifica existência do método
- **Severidade**: ERRO
- **Referência CDU**: RN003

##### API_004 - VersaoSemanticaValidaRule
- **Nome**: Versão Semântica Válida
- **Descrição**: Garante que a versão da API siga o padrão semântico
- **Critérios**:
  - Versão deve seguir padrão semântico (ex: v1.0.0)
  - Formato: MAJOR.MINOR.PATCH
- **Severidade**: ERRO
- **Referência CDU**: RN004

##### API_005 - DocumentacaoGeradaRule
- **Nome**: Documentação Gerada
- **Descrição**: Garante que a documentação OpenAPI seja gerada após configuração
- **Critérios**:
  - OpenAPI é gerado automaticamente
  - Documentação é atualizada após mudanças
- **Severidade**: INFO
- **Referência CDU**: RN005

##### API_006 - AutenticacaoConfiguradaRule
- **Nome**: Autenticação Configurada
- **Descrição**: Valida as opções de autenticação do endpoint
- **Critérios**:
  - Autenticação pode ser: NONE, BASIC, BEARER, API_KEY
  - Configurações são validadas antes da aplicação
- **Severidade**: ERRO
- **Referência CDU**: RN006

##### API_007 - RateLimitValidoRule
- **Nome**: Rate Limit Válido
- **Descrição**: Garante que o rate limiting seja configurado corretamente
- **Critérios**:
  - Rate limit deve ser positivo se informado
  - Período deve ser: MINUTO, HORA ou DIA
- **Severidade**: AVISO
- **Referência CDU**: RN007

## Validadores

- `EndpointAPIValidator` - Orquestra regras de EndpointAPI

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<EndpointAPIDTO> {
    private static final String CODE = "API_001";
    
    @Override
    public String getCode() {
        return CODE;
    }
    // ...
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`