# Plano de Melhorias - ia-core-apps e gestor-igreja/Biblia

## Sumário

1. [Visão Geral](#visão-geral)
2. [Análise dos Projetos](#análise-dos-projetos)
   - [2.1 Estrutura do ia-core-apps](#21-estrutura-do-ia-core-apps)
   - [2.2 Estrutura do gestor-igreja/Biblia](#22-estrutura-do-gestor-igrejabiblia)
   - [2.3 Padrões Existentes](#23-padrões-existentes)
3. [Melhorias Propostas](#melhorias-propostas)
   - [3.1 Fundamentos e Infraestrutura](#31-fundamentos-e-infraestrutura)
   - [3.2 Arquitetura e Design](#32-arquitetura-e-design)
   - [3.3 Performance e Otimização](#33-performance-e-otimização)
   - [3.4 Segurança](#34-segurança)
   - [3.5 Testes e Qualidade](#35-testes-e-qualidade)
   - [3.6 Documentação](#36-documentação)
4. [Checklist de Implementação](#checklist-de-implementação)
5. [Roadmap](#roadmap)
6. [Referências](#referências)

---

## 1. Visão Geral

Este documento apresenta um plano abrangente de melhorias para os projetos **ia-core-apps** e **gestor-igreja/Biblia**, baseando-se nas melhores práticas de programação e padrões consolidados de mercado.

### Objetivos

- Uniformizar a arquitetura entre os projetos
- Melhorar a qualidade do código
- Aumentar a cobertura de testes
- Otimizar performance
- Fortalecer a segurança
- Melhorar a documentação

---

## 2. Análise dos Projetos

### 2.1 Estrutura do ia-core-apps

O projeto **ia-core-apps** é um framework base que fornece componentes compartilhados para aplicações Java/Spring Boot. Estrutura atual:

```
ia-core-apps/
├── ia-core/                          # Módulo pai
│   ├── ia-core-model/               # Entidades e modelos de domínio
│   ├── ia-core-service/             # Serviços base
│   ├── ia-core-rest/               # Controllers REST base
│   ├── ia-core-view/               # Componentes Vaadin
│   ├── ia-core-llm-model/          # Modelos de LLM
│   ├── ia-core-llm-service/        # Serviços de LLM
│   ├── ia-core-llm-view/           # View de LLM
│   ├── ia-core-quartz/             # Modelo de agendamento
│   ├── ia-core-quartz-service/     # Serviços de agendamento
│   ├── ia-core-quartz-view/        # View de agendamento
│   ├── ia-core-nlp/               # Processamento de linguagem natural
│   ├── ia-core-grammar/            # Gramáticas ANTLR
│   ├── ia-core-flyway/            # Migrações de banco
│   ├── ia-core-security/           # Serviços de segurança
│   └── ADR/                       # Arquitetura Decision Records
```

### 2.2 Estrutura do gestor-igreja/Biblia

O projeto **Biblia** é uma aplicação de gestão religiosa built on top do ia-core-apps:

```
gestor-igreja/Biblia/
├── biblia-model/                   # Entidades JPA
├── biblia-service/                 # Serviços de negócio
├── biblia-service-model/           # DTOs e Translators
├── biblia-rest/                   # Controllers REST
├── biblia-view/                  # Interface Vaadin
├── biblia-nlp/                   # NLP específico
├── biblia-grammar/               # Gramáticas específicas
├── CDU/                          # Casos de Uso
└── biblia.xml                    # Configurações
```

### 2.3 Padrões Existentes

Os projetos já seguem diversos padrões estabelecidos:

| Padrão | Status | Descrição |
|--------|--------|-----------|
| **Clean Architecture** | ✅ Implementado | Separação em camadas |
| **SOLID** | ✅ Parcial | SRP, OCP aplicados |
| **MVVM** | ✅ Implementado | ViewModels e Views |
| **DTO Pattern** | ✅ Implementado | DTOs com Translators |
| **Service Pattern** | ✅ Implementado | @Service com injeção |
| **Specification Pattern** | ✅ Implementado | Filtros dinâmicos |
| **Domain Events** | ✅ Implementado | Publicação automática |
| **MapStruct** | ✅ Implementado | Mapeamento DTO-Entidade |
| **Jakarta Validation** | ✅ Parcial | Validações em DTOs |
| **i18n** | ✅ Parcial | Arquivos properties |

---

## 3. Melhorias Propostas

### 3.1 Fundamentos e Infraestrutura

#### 3.1.1 Validação Jakarta Completa

**Problema:** Validação inconsistente entre DTOs.

**Solução:** Completar validação em todos os DTOs seguindo o padrão.

**Arquivos de referência:**
- [`CODING_STANDARDS.md`](ia-core/CODING_STANDARDS.md)
- [BIBLIA_REFACTORATION_PLAN.md](gestor-igreja/Biblia/BIBLIA_REFACTORATION_PLAN.md)

**DTOs pendentes no BiblIA:**
- [ ] `FamiliaDTO` - Adicionar validações
- [ ] `PessoaDTO` - Verificar validações
- [ ] `ContaDTO` - Verificar validações
- [ ] `DoacaoDTO` - Adicionar validações
- [ ] `TransferenciaDTO` - Verificar validações

#### 3.1.2 Internacionalização (i18n)

**Problema:** Strings hardcoded em Views e Services.

**Solução:** Completar arquivo de traduções.

**Arquivo de referência:**
- [`translations_biblia_pt_BR.properties`](gestor-igreja/Biblia/biblia-service-model/src/main/resources/i18n/translations_biblia_pt_BR.properties)

#### 3.1.3 Padronização de Nomenclatura

**Problema:** Inconsistências na nomenclatura de classes e métodos.

**Solução:** Aplicar convenções definidas em [`CODING_STANDARDS.md`](ia-core/CODING_STANDARDS.md).

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| Services | `NomeService` | `EventoService` |
| Repositories | `NomeRepository` | `EventoRepository` |
| DTOs | `NomeDTO` | `EventoDTO` |
| ViewModels | `NomeViewModel` | `EventoFormViewModel` |

---

### 3.2 Arquitetura e Design

#### 3.2.1 EntityGraph e Performance

**Problema:** Queries N+1 em entidades com relacionamentos.

**Solução:** Adicionar `@NamedEntityGraph` em entidades.

**Entidades que precisam de EntityGraph:**
- [ ] `MovimentoFinanceiro` - com `conta` e `tipoIntencao`
- [ ] `Evento` - com `inscricoes` e `materiais`
- [ ] `Conta` - com `movimentos`
- [ ] `Familia` - com `integrantes`

**Arquivo de referência:**
- [ENTITYGRAPH_OPTIMIZATION.md](gestor-igreja/Biblia/ENTITYGRAPH_OPTIMIZATION.md)

#### 3.2.2 Índices de Banco de Dados

**Problema:** Falta de índices para queries frequentes.

**Solução:** Criar migrações Flyway com índices.

```sql
-- MovimentoFinanceiro
CREATE INDEX idx_movimento_tipo ON tb_movimento_financeiro(tipo);
CREATE INDEX idx_movimento_conta ON tb_movimento_financeiro(conta_id);
CREATE INDEX idx_movimento_data ON tb_movimento_financeiro(data_movimento);

-- Evento
CREATE INDEX idx_evento_data ON tb_evento(data_evento);
CREATE INDEX idx_inscricao_evento ON tb_inscricao(evento_id);
```

#### 3.2.3 Separação de Responsabilidades (SRP)

**Problema:** Services com múltiplas responsabilidades.

**Solução:** Separar services por domínio.

| Service Atual | Problema | Novo Service |
|--------------|----------|--------------|
| `EventoService` | CRUD + Inscrição + Materiais | Separar `InscricaoService`, `MaterialEventoService` |
| `MovimentoFinanceiroService` | CRUD + Relatórios | Separar `RelatorioFinanceiroService` |
| `ContaService` | CRUD + Transferências | Separar `TransferenciaService` |

#### 3.2.4 Domain Events Expansion

**Problema:** Eventos de domínio não utilizados em todo o sistema.

**Solução:** Expandir uso de eventos para outras operações.

```java
// Exemplos de eventos a implementar
- EventoCriadoEvent
- EventoAtualizadoEvent
- InscricaoConfirmadaEvent
- PagamentoRecebidoEvent
- MembroAdicionadoEvent
```

---

### 3.3 Performance e Otimização

#### 3.3.1 Cache Strategy

**Problema:** Dados frequentemente acessados sem cache.

**Solução:** Implementar cache com Spring Cache.

| Dado | Estratégia | TTL |
|------|-------------|-----|
| Calendários | Cacheable | 1 hora |
| Configurações | Cacheable | 24 horas |
| Eventos do mês | Cacheable | 5 minutos |
| Estatísticas | Cacheable | 15 minutos |

#### 3.3.2 Paginação

**Problema:** Listas sem paginação.

**Solução:** Implementar Pageable em todas as listas.

```java
// Exemplo
public Page<EventoDTO> findAll(Pageable pageable) {
    return eventoRepository.findAll(pageable).map(this::toDTO);
}
```

#### 3.3.3 Query Optimization

**Problema:** Queries complexas não otimizadas.

**Solução:** Usar EntityGraph e projection.

**Arquivo de referência:**
- [`PERFORMANCE_OPTIMIZATION_PLAN.md`](ia-core/PERFORMANCE_OPTIMIZATION_PLAN.md)

---

### 3.4 Segurança

#### 3.4.1 Autenticação e Autorização

**Melhorias sugeridas:**
- [ ] Implementar OAuth2/OpenID Connect
- [ ] Adicionar Rate Limiting
- [ ] Implementar auditoria de segurança
- [ ] Adicionar CORS configurável

**Arquivo de referência:**
- [FASE_Y_SEGURANCA_AUTENTICACAO.md](ia-core/FASE_Y_SEGURANCA_AUTENTICACAO.md)

#### 3.4.2 Proteção de Dados

**Melhorias:**
- [ ] Criptografia de dados sensíveis
- [ ] Mask de dados em logs
- [ ] Sanitização de inputs

---

### 3.5 Testes e Qualidade

#### 3.5.1 Cobertura de Testes

**Meta:** > 70% de cobertura.

| Módulo | Atual | Meta |
|--------|-------|------|
| ia-core-model | 70% | 80% |
| ia-core-service | 60% | 80% |
| biblia-service | 40% | 70% |
| biblia-model | 50% | 70% |

#### 3.5.2 Estrutura de Testes

```
src/test/java/
├── unit/
│   └── {modulo}/
│       └── services/
├── integration/
│   └── repositories/
└── acceptance/
    └── {funcionalidade}/
```

**Arquivo de referência:**
- [`TESTING_PLAN.md`](ia-core/TESTING_PLAN.md)

#### 3.5.3 Qualidade de Código

**Ferramentas a integrar:**
- [ ] SonarQube
- [ ] JaCoCo (cobertura)
- [ ] SpotBugs
- [ ] Checkstyle

---

### 3.6 Documentação

#### 3.6.1 Documentação de Código

**Melhorias:**
- [ ] Completar Javadoc em todas as classes públicas
- [ ] Adicionar @since tags
- [ ] Adicionar @see e {@link} referências
- [ ] Criar exemplos de uso

**Padrão de Javadoc:**
```java
/**
 * Descrição do método.
 * <p>
 * Explicação adicional sobre o comportamento.
 *
 * @param param1 descrição do parâmetro
 * @return descrição do retorno
 * @throws Excecao возможная descrição
 * @since 1.0.0
 * @see ClasseRelacionada
 */
```

#### 3.6.2 Documentação de API

**Melhorias:**
- [ ] Completar anotações OpenAPI/Swagger
- [ ] Adicionar descrições em português
- [ ] Documentar códigos de erro

#### 3.6.3 Documentação de Arquitetura

**Melhorias:**
- [ ] Criar ARCHITECTURE.md para BiblIA
- [ ] Atualizar CONTRIBUTING.md
- [ ] Criar RUNBOOK.md para operações

---

## 4. Checklist de Implementação

### Fase 1: Fundamentos

- [ ] Completar validação Jakarta em todos os DTOs
- [ ] Completar arquivo i18n translations_biblia_pt_BR.properties
- [ ] Remover strings hardcoded
- [ ] Aplicar convenções de nomenclatura

### Fase 2: Arquitetura

- [ ] Adicionar @NamedEntityGraph em entidades
- [ ] Atualizar repositories para usar EntityGraph
- [ ] Criar índices Flyway
- [ ] Separar services com múltiplas responsabilidades
- [ ] Expandir uso de Domain Events

### Fase 3: Performance

- [ ] Implementar cache com Spring Cache
- [ ] Adicionar paginação em todas as listas
- [ ] Otimizar queries com projection
- [ ] Implementar lazy loading eficiente

### Fase 4: Segurança

- [ ] Implementar OAuth2/OpenID
- [ ] Adicionar Rate Limiting
- [ ] Implementar auditoria de segurança
- [ ] Configurar CORS

### Fase 5: Testes

- [ ] Implementar testes unitários
- [ ] Implementar testes de integração
- [ ] Configurar CI/CD
- [ ] Configurar SonarQube

### Fase 6: Documentação

- [ ] Completar Javadoc
- [ ] Completar documentação OpenAPI
- [ ] Criar ARCHITECTURE.md para BiblIA
- [ ] Atualizar CONTRIBUTING.md

---

## 5. Roadmap

### Versão 1.5.x - Qualidade de Código

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Validação Jakarta | Todos DTOs validados |
| 2 | i18n | Strings externalizadas |
| 3 | Nomenclatura | Código padronizado |

### Versão 2.0.x - Arquitetura

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | EntityGraph | Queries otimizadas |
| 2 | SRP | Services separados |
| 3 | Events | Sistema de eventos completo |

### Versão 2.5.x - Performance

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Cache | Performance melhorada |
| 2 | Paginação | Lists paginadas |
| 3 | Queries | Queries otimizadas |

### Versão 3.0.x - Segurança e Qualidade

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Segurança | OAuth2 implementado |
| 2 | Testes | > 70% cobertura |
| 3 | Docs | Documentação completa |

---

## 6. Referências

### Documentos Existentes

- [CODING_STANDARDS.md](ia-core/CODING_STANDARDS.md)
- [ARCHITECTURE.md](ia-core/ARCHITECTURE.md)
- [BIBLIA_REFACTORATION_PLAN.md](gestor-igreja/Biblia/BIBLIA_REFACTORATION_PLAN.md)
- [PLANO_REFATORACAO_BIBLIA.md](gestor-igreja/Biblia/PLANO_REFATORACAO_BIBLIA.md)
- [PERFORMANCE_OPTIMIZATION_PLAN.md](ia-core/PERFORMANCE_OPTIMIZATION_PLAN.md)
- [TESTING_PLAN.md](ia-core/TESTING_PLAN.md)
- [JAVADOC_STANDARDS.md](ia-core/JAVADOC_STANDARDS.md)

### Recursos Externos

- SOLID Principles
- Clean Code (Robert Martin)
- Domain-Driven Design (Eric Evans)
- Spring Boot Best Practices
- Vaadin Best Practices

---

*Documento criado em: 2025-02-17*
*Última atualização: 2025-02-17*
*Versão: 1.0.0*
