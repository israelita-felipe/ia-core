# Regras de Negócio - Módulo Manter Resilience

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Resilience Patterns do ia-core-apps, para aplicação de padrões de resiliência Resilience4j.

## Entidades

### ResilienceProfile
Perfil de configuração para padrões de resiliência.

### ResilientAnnotation
Anotação para aplicação de resiliência em métodos.

## Regras Implementadas

##### RES_001 - MetodoPublicoResilienteRule
- **Nome**: Método Público pode Ser Decorado com @Resilient
- **Descrição**: Apenas métodos públicos em beans Spring podem ser decorados com @Resilient
- **Critérios**:
  - Método deve ser público
  - Classe deve ser Spring bean (@Service, @Component)
  - Aspect é aplicado apenas a métodos públicos
- **Severidade**: ERRO
- **Referência CDU**: CDU046-Manter-Resilience

##### RES_002 - FallbackAssinaturaValidaRule
- **Nome**: Fallback Deve Ter Assinatura Válida
- **Descrição**: Métodos fallback devem ter assinatura compatível com o método alvo
- **Critérios**:
  - Fallback pode ter assinatura (args, exception) ou (args)
  - Parâmetros do fallback devem ser subconjunto dos parâmetros originais
  - Return type deve ser compatível ou void
- **Severidade**: ERRO
- **Referência CDU**: CDU046-Manter-Resilience

##### RES_003 - BulkheadLimitaConcorrenciaRule
- **Nome**: Bulkhead Limita Concorrência por Perfil
- **Descrição**: O padrão Bulkhead limita a concorrência de operações por perfil configurado
- **Critérios**:
  - EXTERNAL_API: limite de 5 execuções concorrentes
  - LLM_SERVICE: limite de 3 execuções concorrentes
  - DATABASE: limite de 15 execuções concorrentes
  - INTERNAL_SERVICE: limite de 20 execuções concorrentes
- **Severidade**: INFO
- **Referência CDU**: CDU046-Manter-Resilience

##### RES_004 - RateLimiterBloqueiaExcessoRule
- **Nome**: Rate Limiter Bloqueia Após Limite Excedido
- **Descrição**: O padrão RateLimiter bloqueia requisições após o limite ser excedido
- **Critérios**:
  - Requisições excedentes são rejeitadas
  - Métricas de rejeição são coletadas
  - Fallback é executado quando rate limit é atingido
- **Severidade**: INFO
- **Referência CDU**: CDU046-Manter-Resilience

##### RES_005 - CircuitBreakerRegistraMetricasRule
- **Nome**: Circuit Breaker Registra Métricas
- **Descrição**: Métricas de circuit breaker são coletadas automaticamente via Micrometer
- **Critérios**:
  - Métricas de sucesso/falha são coletadas
  - Métricas de abertura/fechamento são monitoradas
  - Métricas são exportadas para sistema de monitoramento
- **Severidade**: INFO
- **Referência CDU**: CDU046-Manter-Resilience

## Validadores

- `ResilienceValidator` - Validações para configurações de resiliência

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<ResilienceConfig> {
    @Override
    public String getCode() {
        return "RES_001";
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
    public void validate(ResilienceConfig entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Perfis de Resiliência Configurados

| Perfil | Uso | Retry | CB Threshold | Bulkhead | Timeout |
|--------|-----|-------|--------------|----------|---------|
| EXTERNAL_API | APIs externas | 2 | 40% falhas | 5 | 15s |
| LLM_SERVICE | LLMs (OpenAI, Ollama) | 1 | 30% falhas | 3 | 30s |
| DATABASE | Operações DB | 3 | 60% falhas | 15 | 5s |
| INTERNAL_SERVICE | Feign entre módulos | 3 | 50% falhas | 20 | 10s |

## Referências

- ADR-025: Usar Resilience4j
- ADR-053: Usar CDU para Documentação de Casos de Uso
- Service Base: `com.ia.core.service.rules.BusinessRule`