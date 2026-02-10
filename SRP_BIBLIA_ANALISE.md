# FASE O: Separação de Responsabilidades (SRP) - Biblia

## Objetivo
Identificar e separar serviços que possuem responsabilidades múltiplas, aplicando o Single Responsibility Principle no projeto Biblia.

## Princípio SRP
> "Uma classe deve ter apenas um motivo para mudar"

---

## Análise do ia-core

### 1. LLMTransformationService ✅ JÁ TRATADO
**Status:** `@Deprecated` - Já marcado para depreciação

O serviço [`LLMTransformationService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java) foi identificado com responsabilidades múltiplas:
- Processamento de imagem (binarização, compressão)
- Extração de texto via LLM

**Solução implementada:**
- [`ImageProcessingService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/ImageProcessingService.java) - Responsabilidade única: processamento de imagens
- [`TextExtractionService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/TextExtractionService.java) - Responsabilidade única: extração de texto

### 2. DefaultSecuredBaseService
**Status:** ✅ OK - Apenas autenticação/autorização

O [`DefaultSecuredBaseService`](ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/transform/LLMTransformationService.java) tem responsabilidade única:
- Gerenciamento de segurança (autenticação/autorização)
- CRUD genérico para entidades seguras

---

## Análise do Biblia

### 1. MovimentoFinanceiroService ⚠️ VIOLAÇÃO SRP

**Localização:** `biblia-service/src/main/java/com/ia/biblia/service/financeiro/MovimentoFinanceiroService.java`

#### Problema Identificado
O serviço opera com **múltiplas entidades financeiras**:

| Método | Entidade | Responsabilidade |
|--------|----------|------------------|
| `processarDespesa()` | Despesa | Processamento de despesas |
| `processarReceita()` | Receita | Processamento de receitas |
| `processarTransferencia()` | Transferencia | Processamento de transferências |

#### Violação de SRP
> O serviço tem **3 motivos para mudar**:
> 1. Alteração nas regras de negócio de despesas
> 2. Alteração nas regras de negócio de receitas
> 3. Alteração nas regras de negócio de transferências

#### Proposta de Refatoração

**ANTES (Violação SRP):**
```java
@Service
public class MovimentoFinanceiroService 
  extends DefaultSecuredBaseService<MovimentoFinanceiro, MovimentoFinanceiroDTO> {
    
    public void processarDespesa(DespesaDTO dto) {}
    public void processarReceita(ReceitaDTO dto) {}
    public void processarTransferencia(TransferenciaDTO dto) {}
}
```

**DEPOIS (SRP Aplicado):**

```java
// biblia-service/src/main/java/com/ia/biblia/service/financeiro/DespesaService.java
@Service
public class DespesaService extends DefaultSecuredBaseService<Despesa, DespesaDTO> {
    // Responsabilidade: Apenas processamento de despesas
    public void processarDespesa(DespesaDTO dto) {
        // Validação específica de despesa
        // Cálculo detotais
        // Persistência
    }
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/financeiro/ReceitaService.java
@Service
public class ReceitaService extends DefaultSecuredBaseService<Receita, ReceitaDTO> {
    // Responsabilidade: Apenas processamento de receitas
    public void processarReceita(ReceitaDTO dto) {
        // Validação específica de receita
        // Cálculo detotais
        // Persistência
    }
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/financeiro/TransferenciaService.java
@Service
public class TransferenciaService extends DefaultSecuredBaseService<Transferencia, TransferenciaDTO> {
    // Responsabilidade: Apenas processamento de transferências
    public void processarTransferencia(TransferenciaDTO dto) {
        // Validação específica de transferência
        // Atualização de saldos
        // Persistência
    }
}
```

**Serviço de Orchestration (opcional):**
```java
// biblia-service/src/main/java/com/ia/biblia/service/financeiro/FinanceiroOrchestrationService.java
@Service
public class FinanceiroOrchestrationService {
    private final DespesaService despesaService;
    private final ReceitaService receitaService;
    private final TransferenciaService transferenciaService;
    
    // Coordenação de operações financeiras complexas
}
```

---

### 2. EventoService ⚠️ VALIDAÇÃO INLINE

**Localização:** `biblia-service/src/main/java/com/ia/biblia/service/evento/EventoService.java`

#### Problema Identificado
O serviço contém **validação inline** que pode ser extraída:

```java
// ANTES - Validação inline no service
@Service
public class EventoService extends DefaultSecuredBaseService<Evento, EventoDTO> {
    
    public Evento criarEvento(EventoDTO dto) {
        // Validação inline
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new ValidationException("Descrição é obrigatória");
        }
        if (dto.getLocal() == null) {
            throw new ValidationException("Local é obrigatório");
        }
        if (dto.getPeriodicidade() == null) {
            throw new ValidationException("Periodicidade é obrigatória");
        }
        // ... mais validações
    }
}
```

#### Violação de SRP
> O serviço tem **2 motivos para mudança**:
> 1. Regras de negócio de eventos
> 2. Regras de validação de eventos

#### Proposta de Refatoração

```java
// biblia-service/src/main/java/com/ia/biblia/service/evento/EventoValidator.java
@Component
public class EventoValidator implements Validator<EventoDTO> {
    
    @Override
    public void validate(EventoDTO dto) {
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new ValidationException("Descrição é obrigatória");
        }
        if (dto.getLocal() == null) {
            throw new ValidationException("Local é obrigatório");
        }
        if (dto.getPeriodicidade() == null) {
            throw new ValidationException("Periodicidade é obrigatória");
        }
    }
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/evento/EventoService.java
@Service
public class EventoService extends DefaultSecuredBaseService<Evento, EventoDTO> {
    
    private final EventoValidator eventoValidator;
    
    public EventoService(EventoValidator eventoValidator) {
        this.eventoValidator = eventoValidator;
    }
    
    @Override
    public Evento criarEvento(EventoDTO dto) {
        // Validação extraída para validator
        eventoValidator.validate(dto);
        
        // Lógica de negócio do evento
        return super.criarEvento(dto);
    }
}
```

---

### 3. PessoaService ⚠️ LÓGICA COMPLEXA

**Localização:** `biblia-service/src/main/java/com/ia/biblia/service/pessoa/PessoaService.java`

#### Problema Identificado
O serviço contém **lógica complexa e متعددة responsabilidades**:

| Responsabilidade | Métodos |
|------------------|---------|
| Gerenciamento básico de pessoas | `criar()`, `atualizar()`, `excluir()` |
| Gestão de contatos | `adicionarContato()`, `removerContato()` |
| Gestão de endereços | `adicionarEndereco()`, `removerEndereco()` |
| Validação de documentos | `validarCPF()`, `validarCNPJ()` |
| Cálculo de idade | `calcularIdade()` |

#### Violação de SRP
> O serviço tem **5 motivos para mudança**:
> 1. Operações CRUD de pessoas
> 2. Gestão de contatos
> 3. Gestão de endereços
> 4. Validação de documentos
> 5. Cálculos auxiliares

#### Proposta de Refatoração

```java
// biblia-service/src/main/java/com/ia/biblia/service/pessoa/PessoaService.java
@Service
public class PessoaService extends DefaultSecuredBaseService<Pessoa, PessoaDTO> {
    
    private final PessoaValidator validator;
    private final ContatoService contatoService;
    private final EnderecoService enderecoService;
    
    // Apenas CRUD básico de pessoas
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/pessoa/ContatoService.java
@Service
public class ContatoService {
    // Responsabilidade: Gestão de contatos
    public void adicionarContato(Pessoa pessoa, ContatoDTO dto) {}
    public void removerContato(Pessoa pessoa, Long contatoId) {}
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/pessoa/EnderecoService.java
@Service
public class EnderecoService {
    // Responsabilidade: Gestão de endereços
    public void adicionarEndereco(Pessoa pessoa, EnderecoDTO dto) {}
    public void removerEndereco(Pessoa pessoa, Long enderecoId) {}
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/pessoa/DocumentoValidator.java
@Component
public class DocumentoValidator {
    // Responsabilidade: Validação de documentos
    public boolean validarCPF(String cpf) {}
    public boolean validarCNPJ(String cnpj) {}
}
```

```java
// biblia-service/src/main/java/com/ia/biblia/service/pessoa/PessoaCalculator.java
@Component
public class PessoaCalculator {
    // Responsabilidade: Cálculos auxiliares
    public int calcularIdade(Pessoa pessoa) {}
}
```

---

## Resumo das Mudanças Necessárias

### Biblia - MovimentoFinanceiroService

| Ação | Arquivo | Tipo |
|------|---------|------|
| Criar | `DespesaService.java` | Novo |
| Criar | `ReceitaService.java` | Novo |
| Criar | `TransferenciaService.java` | Novo |
| Criar | `FinanceiroOrchestrationService.java` | Novo (opcional) |
| Remover | `MovimentoFinanceiroService.java` | Remoção |

### Biblia - EventoService

| Ação | Arquivo | Tipo |
|------|---------|------|
| Criar | `EventoValidator.java` | Novo |
| Modificar | `EventoService.java` | Refatoração |

### Biblia - PessoaService

| Ação | Arquivo | Tipo |
|------|---------|------|
| Criar | `ContatoService.java` | Novo |
| Criar | `EnderecoService.java` | Novo |
| Criar | `DocumentoValidator.java` | Novo |
| Criar | `PessoaCalculator.java` | Novo |
| Modificar | `PessoaService.java` | Refatoração |

---

## Benefícios da Refatoração

| Benefício | Descrição |
|-----------|-----------|
| **SRP** | Cada serviço tem uma única responsabilidade |
| **Testabilidade** | Serviços menores são mais fáceis de testar |
| **Reusabilidade** | Validadores e calculators podem ser reutilizados |
| **Manutenibilidade** | Alterações em validação não afetam lógica de negócio |
| **Coesão** | Classes coesas com responsabilidades bem definidas |
| **Escalabilidade** | Novas funcionalidades podem ser adicionadas facilmente |

---

## Ordem de Execução Recomendada

1. **MovimentoFinanceiroService** (Alta Prioridade)
   - Criar novos serviços especializados
   - Atualizar dependências nos controllers
   - Migrar dados gradualmente

2. **EventoService** (Média Prioridade)
   - Criar EventoValidator
   - Refatorar EventoService para usar validator

3. **PessoaService** (Média Prioridade)
   - Criar serviços auxiliares (Contato, Endereco)
   - Criar validadores e calculators
   - Refatorar PessoaService

---

## Status de Implementação

| Serviço | Status | Observações |
|---------|--------|-------------|
| LLMTransformationService (ia-core) | ✅ Concluído | Já tratado anteriormente |
| MovimentoFinanceiroService (Biblia) | ⏳ Pendente | Aguardando implementação |
| EventoService (Biblia) | ⏳ Pendente | Aguardando implementação |
| PessoaService (Biblia) | ⏳ Pendente | Aguardando implementação |

---

## Referências

- [RELATORIO_REVISAO_BIBLIA.md](RELATORIO_REVISAO_BIBLIA.md)
- [SRP_REFACTORATION_PLAN.md](SRP_REFACTORATION_PLAN.md)
- [ia-core/SRP_STATUS.md](ia-core/SRP_STATUS.md)
