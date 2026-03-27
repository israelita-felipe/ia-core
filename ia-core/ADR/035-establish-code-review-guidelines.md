# ADR-035: Estabelecer Guidelines para Code Review

## Status

✅ Aceito

## Contexto

O projeto precisa de diretrizes claras para code review que:
- Garantam qualidade do código
- Promovam aprendizado coletivo
- Mantenham consistência
- Reduzam bugs em produção
- Facilitem onboarding

## Decisão

Estabelecer um processo estruturado de code review com checklists e diretrizes.

## Detalhes

### Processo de Code Review

```
1. Developer cria Pull Request (PR)
2. Description com contexto e screenshots
3. Auto-review (lint, testes)
4. Reviewer designado
5. Feedback construtivo
6. Ajustes necessários
7. Aprovação + Merge
8. Cleanup branch
```

### Checklist de Revisão

#### 1. Funcionalidade
- [ ] O código faz o que deveria fazer?
- [ ] Casos de borda são tratados?
- [ ] Erros são tratados adequadamente?
- [ ] Logs são adequados?

#### 2. Design
- [ ] Segue os padrões do projeto (ADRs)?
- [ ] Nomes são significativos?
- [ ] Funções/métodos são pequenos?
- [ ] Responsabilidade única?

#### 3. Testes
- [ ] Testes unitários incluidos?
- [ ] Cenários importantes cobertos?
- [ ] Testes são legíveis?

#### 4. Segurança
- [ ] Validação de entrada?
- [ ] SQL Injection prevenida?
- [ ] Dados sensíveis expostos?
- [ ] Autenticação/autorização?

#### 5. Performance
- [ ] N+1 queries evitadas?
- [ ] Caching apropriado?
- [ ] Recursos fechados?

#### 6. Estilo
- [ ] Formatação seguir padrão?
- [ ] Sem warnings de compilação?
- [ ] Código duplicado?

### Critérios de Aprovação

| Tipo de Mudança | Revisores Necessários |
|-----------------|----------------------|
| Bug fix simples | 1 |
| Nova funcionalidade | 2 |
| Mudança arquitetural | 3 |
| Mudança de segurança | 2 + Team Lead |

### Tempo de Resposta

| Prioridade | Tempo Máximo |
|------------|--------------|
| Bug crítico | 4 horas |
| Feature normal | 24 horas |
| Refatoração | 48 horas |
| Documentação | 72 horas |

### Boas Práticas de Feedback

**✅ Bom:**
- "Sugiro usar map() ao invés de for loop para..."
- "Você considerou usar o Specification pattern aqui?"
- "Ótima solução! Pode simplificar usando..."

**❌ Ruim:**
- "Isso está errado."
- "Isso não funciona."
- "Por que você fez assim?"

### Ferramentas Recomendadas

- GitHub/GitLab PRs
- SonarQube para análise estática
- Pre-commit hooks para lint
- CI automatizado

## Consequências

### Positivas

- ✅ Código mais consistente
- ✅ Compartilhamento de conhecimento
- ✅ Bugs encontrados antes da produção
- ✅ Equipe aprende junta

### Negativas

- ❌ Tempo adicional no desenvolvimento
- ❌ Pode criar gargalos
- ❌ Conflitos pessoais se mal executado

## Status de Implementação

✅ **COMPLETO**

- Checklist documentado
- Time treinado
- CI integrado

## Data

2024-03-01

## Revisores

- Team Lead
- Architect

## Referências

1. **Google - Code Review Developer Guide**
   - URL: https://google.github.io/eng-practices/review/
   - Guia oficial do Google

2. **SmartBear - Code Review Checklist**
   - URL: https://www.smartbear.com/learn/code-review/best-practices-for-code-review/
   - Checklist completo

3. **Atlassian - Code Review Best Practices**
   - URL: https://www.atlassian.com/agile/software-development/code-reviews
   - Práticas ágeis

4. **Microsoft - Code Review Guide**
   - URL: https://docs.microsoft.com/en-us/azure/devops/repos/review/
   - Guia da Microsoft

5. **Phabricator - Code Review Best Practices**
   - URL: https://secure.phabricator.com/book/phabricator/article/reviewing/
   - Boas práticas