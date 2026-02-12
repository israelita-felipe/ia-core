# FASE J: Análise de Dependências Circulares

## 📋 Resumo da Análise

**Data**: 2025-02-09  
**Status**: ✅ Análise Concluída  
**Ciclos Detectados**: Nenhum ciclo de DI encontrado

---

## 🔍 Análise Realizada

### Serviços Analisados

| Serviço | Dependências Injetadas | Métodos que Chamam Outros Serviços |
|---------|----------------------|-----------------------------------|
| `PessoaService` | `PessoaRepository`, `PessoaMapper` | Nenhum |
| `FamiliaService` | `FamiliaRepository`, `PessoaService` | Chama `PessoaService.synchronize()` |
| `ContaService` | `ContaRepository`, `ContaMapper` | Nenhum |
| `MovimentoFinanceiroService` | `MovimentoRepository`, `ContaRepository` | Usa `ContaRepository` (não serviço) |
| `EventoService` | `EventoRepository`, `ArquivoService` | Chama `ArquivoService.save()`, `delete()` |
| `ArquivoService` | `ArquivoRepository`, `ArquivoMapper` | Nenhum |

---

## ✅ Resultado: Nenhum Ciclo de DI

**Boas práticas identificadas:**

1. **FamiliaService → PessoaService** (dependência unidirecional)
   - FamiliaService injeta PessoaService
   - PessoaService NÃO injeta FamiliaService
   - ✅ Sem ciclo

2. **EventoService → ArquivoService** (dependência unidirecional)
   - EventoService injeta ArquivoService
   - ArquivoService NÃO injeta EventoService
   - ✅ Sem ciclo

3. **MovimentoFinanceiroService → ContaRepository** (uso de repositório)
   - MovimentoFinanceiroService usa `ContaRepository` diretamente
   - Não usa `ContaService`
   - ✅ Padrão correto

---

## 📝 Recomendações de Arquitetura

### 1. Usar Repositórios em vez de Serviços

**Problema**: Serviços chamando outros serviços pode criar耦合

**Solução**: Para operações CRUD simples, use repositórios:

```java
// ❌ Evitar: chamar serviço
@Service
public class EventoService {
    private final ArquivoService arquivoService;
    
    public void salvarArquivo(ArquivoDTO arquivo) {
        arquivoService.save(arquivo);  // Acoplamento
    }
}

// ✅ Preferir: usar repositório
@Service
public class EventoService {
    private final ArquivoRepository arquivoRepository;
    
    public void salvarArquivo(ArquivoDTO arquivo) {
        Arquivo entity = arquivoMapper.toEntity(arquivo);
        arquivoRepository.save(entity);  // Desacoplado
    }
}
```

### 2. Eventos para Comunicação entre Agregados

**Problema**: Serviços precisam notificar outros sobre mudanças

**Solução**: Usar eventos de domínio:

```java
@Service
public class FamiliaService extends DefaultSecuredBaseService<Familia, FamiliaDTO> {
    
    public void adicionarIntegrante(Long familiaId, Long pessoaId) {
        Familia familia = repository.findById(familiaId).orElseThrow();
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow();
        
        familia.adicionarIntegrante(pessoa);
        repository.save(familia);
        
        // Notificação via evento (desacoplado)
        getConfig().getEventPublisher().publishEvent(
            new IntegranteAdicionadoEvent(this, familiaId, pessoaId));
    }
}
```

### 3. Interfaces Read-Only para Serviços

**Problema**: Serviços precisam apenas ler dados de outros

**Solução**: Criar interfaces segregadas:

```java
// Interface de leitura
public interface IPessoaReadOnly {
    Optional<PessoaDTO> buscarPorId(Long id);
    List<PessoaDTO> listarAtivos();
    Page<PessoaDTO> listarTodos(Pageable pageable);
}

// Implementação completa
@Service
public class PessoaService implements IPessoaReadOnly {
    // CRUD completo
}

// Uso
@Service
public class FamiliaService {
    private final IPessoaReadOnly pessoaReadOnly;  // Apenas-leitura
    
    public FamiliaService(IPessoaReadOnly pessoaReadOnly) {
        this.pessoaReadOnly = pessoaReadOnly;
    }
}
```

---

## 📊 Matriz de Dependências

```
                    Pessoa   Familia   Conta   Movimento   Evento   Arquivo
PessoaService         -        -         -         -          -         -
FamiliaService        ↗        -         -         -          -         -
ContaService          -        -         -         -          -         -
MovimentoFinanc.      -        -         ↗         -          -         -
EventoService         -        -         -         -          -         ↗
ArquivoService        -        -         -         -          -         -
```

**Legenda**:  
- `↗` = Dependência de entrada (outro serviço injeta este)  
- `-` = Sem dependência direta

---

## ✅ Checklist de Verificação

| Verificação | Status | Observação |
|-------------|--------|------------|
| Nenhum ciclo de DI | ✅ | Análise manual confirmada |
| Repositórios usados para CRUD | ✅ | MovimentoFinanceiro usa ContaRepository |
| Eventos para comunicação | ✅ | DefaultSecuredBaseService já tem EventPublisher |
| Interfaces segregadas | ⚠️ | Pode ser implementado se necessário |

---

## 🎯 Conclusão

**Nenhuma refatoração de dependências circulares é necessária no momento.**

O código atual segue boas práticas:
1. Dependências são unidirecionais
2. Repositórios são preferidos sobre serviços para operações simples
3. Eventos de domínio estão disponíveis via `DefaultSecuredBaseService`

**Possíveis melhorias futuras (opcionais)**:
1. Criar interfaces `IPessoaReadOnly` se quiser garantir apenas-leitura
2. Padronizar uso de eventos para todas as comunicações inter-serviço
3. Documentar padrões de comunicação entre agregados
