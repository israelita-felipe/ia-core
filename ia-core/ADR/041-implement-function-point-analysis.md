# ADR-041: Implementar Análise de Pontos de Função para Medição Funcional

## Status

✅ Proposto

## Contexto

O projeto necessita de uma forma padronizada e automatizada de medir o tamanho funcional das funcionalidades desenvolvidas, permitindo:

- Estimativas mais precisas de esforço e custo
- Controle de produtividade da equipe
- Benchmarking com padrões da indústria
- Suporte a decisões de negócio baseadas em métricas objetivas

Atualmente, não existe um processo formal para medição funcional, resultando em estimativas inconsistentes e falta de métricas comparáveis.

Como projeto brasileiro no contexto governamental, seguiremos as diretrizes do SISP (Sistema de Informações do Poder Executivo), especificamente os roteiros de métricas v2.3 e v3.0, que adaptam a Análise de Pontos de Função (APF) para projetos de TI do governo federal, integrando com o SIP (Software Improvement Program) para medição de produtividade e esforço.

## Decisão

Implementar **Análise de Pontos de Função (APF)** seguindo o padrão SISP v3.0, baseado no IFPUG 4.3.1 mas adaptado para o contexto brasileiro governamental, integrado ao processo de desenvolvimento através de um padrão de projeto Decorator-Observer.

## Detalhes

### Metodologia APF conforme SISP

A Análise de Pontos de Função conta a funcionalidade entregue ao usuário através de:

#### Funções de Dados
- **ALI (Arquivos Lógicos Internos)**: Dados mantidos pelo sistema
- **AIE (Arquivos de Interface Externa)**: Dados de referência de outros sistemas

#### Funções Transacionais
- **EE (Entradas Externas)**: Manutenção de dados (inclusão, alteração, exclusão)
- **SE (Saídas Externas)**: Relatórios, telas e interfaces de saída
- **CE (Consultas Externas)**: Recuperação de dados sem manutenção

#### Complexidade
Cada função é classificada como Baixa, Média ou Alta baseada em:
- **DETs (Tipos de Elementos de Dados)**: Campos únicos de entrada/saída
- **RETs (Tipos de Elementos de Registro)**: Grupos lógicos de dados em ALI/AIE
- **FTRs (Tipos de Arquivo Referenciado)**: Arquivos referenciados pelas transações

#### Fatores de Ajuste
14 fatores gerais do sistema que influenciam a complexidade, com graus de influência de 0 a 5, conforme roteiro SISP v3.0.

#### Integração com SIP
- Cálculo de produtividade (PF/hora) baseado em benchmarks governamentais
- Estimativa de esforço considerando maturidade de processo
- Relatórios padronizados para acompanhamento de projetos

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| APF SISP v3.0 | Adaptado para governo brasileiro, integração SIP | Específico para contexto nacional |
| FPA IFPUG puro | Padrão internacional, validado | Não considera adaptações locais |
| COSMIC | Simples, orientado a processos | Menos adotado no governo |
| Use Case Points | Integrado a UML | Menos preciso |
| Story Points | Ágil, relativo | Não comparável externamente |
| Manual/Sem medição | Sem overhead | Sem métricas objetivas |

### Critérios de Decisão

1. **Padronização Governamental**: Seguir SISP para conformidade com normas brasileiras
2. **Automação**: Integração no processo de desenvolvimento
3. **Comparabilidade**: Métricas consistentes e comparáveis no contexto federal
4. **Integração SIP**: Suporte a medição de produtividade e benchmarking

## Implementação

### Padrão de Projeto Decorator-Observer

```java
// Observer - Notifica mudanças para contagem
public interface FunctionPointObserver {
    void onFunctionalityChange(FunctionalityEvent event);
}

// Decorator - Adiciona contagem sem alterar core
public class FunctionPointDecorator<T> implements Service<T> {
    private final Service<T> delegate;
    private final FunctionPointAnalyzer analyzer;

    public FunctionPointDecorator(Service<T> delegate, FunctionPointAnalyzer analyzer) {
        this.delegate = delegate;
        this.analyzer = analyzer;
    }

    @Override
    public T execute(Operation op) {
        T result = delegate.execute(op);
        analyzer.analyze(op); // Conta PF automaticamente
        return result;
    }
}

// Analyzer principal
public class SispFunctionPointAnalyzer {
    public int analyze(Functionality functionality) {
        int dataPoints = countDataFunctions(functionality);
        int transactionalPoints = countTransactionalFunctions(functionality);
        double adjustmentFactor = calculateAdjustmentFactor(functionality);

        return (int) Math.round((dataPoints + transactionalPoints) * adjustmentFactor);
    }

    private int countDataFunctions(Functionality f) {
        // Contagem ALI/AIE conforme SISP
    }

    private int countTransactionalFunctions(Functionality f) {
        // Contagem EE/SE/CE conforme SISP
    }

    private double calculateAdjustmentFactor(Functionality f) {
        // 14 fatores SISP
    }
}
```

### Integração com Desenvolvimento

#### Anotações para Contagem Automática
```java
@FunctionPoint(
    type = FunctionType.DATA,
    dataType = DataFunctionType.ALI,
    estimatedDET = 15,
    estimatedRET = 2
)
public class UserEntity {
    // implementação
}

@FunctionPoint(
    type = FunctionType.TRANSACTIONAL,
    transactionalType = TransactionalFunctionType.EE,
    estimatedDET = 8,
    estimatedFTR = 2
)
public class CreateUserUseCase {
    // implementação
}
```

### Integração na Camada View

A camada de view contribui para funções transacionais, especialmente SE e CE, através de interfaces de usuário e endpoints REST:

- **SE (Saídas Externas)**: Telas, dashboards, relatórios exibidos ao usuário
- **CE (Consultas Externas)**: Formulários de busca, filtros, consultas interativas
- **EE (Entradas Externas)**: Formulários de entrada de dados

**Exemplo de Anotação em Controller**:
```java
@FunctionPoint(
    type = FunctionType.TRANSACTIONAL,
    transactionalType = TransactionalFunctionType.SE,
    estimatedDET = 30,  // Campos no relatório (nome, valor, data, etc.)
    estimatedFTR = 5    // Arquivos referenciados (pedidos, clientes, produtos)
)
@RestController
public class RelatorioVendasController {
    @GetMapping("/relatorios/vendas")
    public RelatorioVendasDTO gerarRelatorio(@RequestParam FiltroRelatorio filtro) {
        // Endpoint que retorna dados para exibição em dashboard
    }
}
```

**Métricas para View Layer**:
- **DET**: Número de campos únicos na interface (inputs + outputs)
- **FTR**: Número de entidades de dados referenciadas na view
- **Complexidade**: Baixa (<10 DET), Média (10-20 DET), Alta (>20 DET)
- **Exemplo**: Dashboard com gráfico de vendas e tabela de pedidos: 25 DET, 4 FTR → 6 PF (SE média)

### Workflow de Contagem

1. **Análise de Requisitos**: Contagem inicial para estimativa usando tabelas SISP
2. **Desenvolvimento**: Anotação das classes e métodos com metadados SISP
3. **Build**: Validação e cálculo automático baseado em v3.0
4. **Deploy**: Registro das métricas finais no sistema SIP
5. **Camada View**: Contagem de interfaces e endpoints como SE/CE durante implementação de UI

## Consequências

### Positivas

- ✅ **Estimativas Mais Precisas**: Baseadas em métricas objetivas do SISP
- ✅ **Controle de Produtividade**: Integração com SIP para benchmarks governamentais
- ✅ **Benchmarking**: Comparação com projetos federais
- ✅ **Decisões de Negócio**: Suporte a planejamento baseado em dados SISP
- ✅ **Qualidade Consistente**: Padronização conforme normas brasileiras

### Negativas

- ❌ **Curva de Aprendizado**: Equipe precisa dominar conceitos APF e SISP
- ❌ **Overhead Inicial**: Tempo para anotação e configuração
- ❌ **Manutenção**: Necessidade de manter anotações atualizadas conforme v3.0

### Riscos

- **Contagens Incorretas**: Mitigado por revisões e validações baseadas em roteiros SISP
- **Resistência Cultural**: Mitigado por treinamentos e demonstração de valor no contexto governamental
- **Manutenção de Precisão**: Mitigado por ferramentas automatizadas

## Status de Implementação

🚧 **EM DESENVOLVIMENTO**

- [ ] Padrão Decorator-Observer implementado
- [ ] Sistema de anotações criado conforme SISP
- [ ] Integração com build configurada
- [ ] Documentação completa
- [ ] Treinamentos realizados

## Data

2026-05-06

## Revisores

- Team Lead
- Architect
- Product Owner

## Referências

1. **Roteiro de Métricas do SISP v2.3**
   - Guia para medição de software no governo brasileiro
   - Ênfase em contagem detalhada de ALI/AIE e EE/SE/CE

2. **Roteiro de Métricas do SISP v3.0**
   - Versão atualizada com integrações SIP
   - Fatores de ajuste e relatórios padronizados

3. **IFPUG - Function Point Counting Practices Manual**
   - URL: https://www.ifpug.org/publications/
   - Base técnica para SISP

4. **Software Improvement Group (SIG)**
   - URL: https://www.softwareimprovementgroup.com/
   - Metodologia SIP 3.0 integrada ao SISP

5. **International Function Point Users Group**
   - URL: https://www.ifpug.org/
   - Organização mantenedora do padrão IFPUG

6. **Capers Jones - Software Engineering Best Practices**
   - Referência sobre medição de software

7. **Albrecht, A.J. - Measuring Application Development Productivity**
   - Artigo original sobre pontos de função (1979)

8. **ISO/IEC 24570:2018 - Software engineering**
   - Padrão internacional para medição funcional
