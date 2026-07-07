# CDU - Manter Resilience Patterns

## 1. Metadados
- **Nome do CDU**: Manter Resilience Patterns
- **Versão**: 1.0
- **Data**: 2026-07-03
- **Autor**: IA Core
- **Status**: Em Desenvolvimento

## 2. Descrição do Caso de Uso

### 2.1. Descrição Breve
O caso de uso "Manter Resilience Patterns" permite aplicar padrões de resiliência (Circuit Breaker, Retry, Bulkhead, Rate Limiter, Timeout) em serviços do sistema ia-core, garantindo tolerância a falhas e disponibilidade mesmo diante de instabilidades em serviços externos, bancos de dados e LLMs.

### 2.2. Objetivos
- Aplicar Circuit Breaker para evitar chamadas a serviços instáveis
- Configurar Retry com backoff exponencial para operações falhas
- Limitar concorrência com Bulkhead
- Controlar taxa de requisições com Rate Limiter
- Definir timeouts para evitar travamentos
- Monitorar métricas de resiliência

### 2.3. Escopo
**Incluído**:
- Aplicação de anotação `@Resilient` em métodos
- Configuração de perfis de resiliência (EXTERNAL_API, LLM_SERVICE, DATABASE)
- Fallback strategies para degradação graceful
- Métricas de resiliência via Micrometer

**Excluído**:
- Configuração de circuit breakers externos (Redis, etc)
- Chaos engineering

## 3. Atores

| Ator | Descrição | Tipo |
|------|-----------|------|
| Desenvolvedor | Usuário que configura resiliência | Primário |
| Administrador | Monitora e ajusta configurações | Secundário |

## 4. Pré-condições

### 4.1. Para Aplicar Resilience
- Método deve estar em classe Spring bean (`@Service`, `@Component`)
- Micrometer deve estar configurado para métricas

### 4.2. Para Configurar Fallback
- Método fallback deve ter assinatura compatível
- Bean fallback deve estar no contexto Spring

## 5. Pós-condições

### 5.1. Pós-condição de Sucesso (Aplicar Resilience)
- Método é envolvido com aspecto de resiliência
- Métricas são coletadas automaticamente
- Fallback é executado quando necessário

## 6. Fluxo Principal

### 6.1. Fluxo: Aplicar Resilience a Método

**Passos**:
1. **Dado** desenvolvedor com classe de serviço
2. **Quando** adiciona `@Resilient(profile = ResilienceProfile.EXTERNAL_API)`
3. **Então** sistema valida perfil existe
4. **Quando** método é executado
5. **Então** aspect verifica circuit breaker status
6. **Se** circuito fechado
   - **Então** execute chamada com retry configurado
   - **Então** colete métricas
7. **Se** circuito aberto
   - **Então** execute fallback se configurado
   - **Então** registre rejeição nas métricas

## 7. Regras de Negócio

| ID | Regra de Negócio | Tipo | Aplicação |
|----|------------------|------|-----------|
| RN001 | Métodos públicos podem ser decorados com `@Resilient` | Validação | Aplicação de resiliência |
| RN002 | Fallback deve ter assinatura `(args, exception)` ou `(args)` | Validação | Configuração de fallback |
| RN003 | Bulkhead limita concorrência por perfil | Limitação | Operações concorrentes |
| RN004 | Rate limiter bloqueia após limite excedido | Limitação | Chamadas externas |

## 8. Perfis de Resiliência

| Perfil | Uso | Retry | CB Threshold | Bulkhead | Timeout |
|--------|-----|-----|--------------|----------|---------|
| EXTERNAL_API | APIs externas | 2 | 40% | 5 | 15s |
| LLM_SERVICE | LLMs (OpenAI, Ollama) | 1 | 30% | 3 | 30s |
| DATABASE | Operações DB | 3 | 60% | 15 | 5s |
| INTERNAL_SERVICE | Feign entre módulos | 3 | 50% | 20 | 10s |

## 9. Referências

- ADR-025: Usar Resilience4j
- ADR-012: Testing Patterns (testes para CDUs)
- Módulo: `ia-core-resilience4j/`