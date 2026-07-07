# Regras de Negócio - Módulo Manter Ontologia

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Ontologia do ia-core-apps.

## Entidades

### Ontologia
Representa uma ontologia OWL 2 DL para uso por agentes LLM.

#### Regras Implementadas

##### ONT_001 - DominioObritatorioRule
- **Nome**: Domínio Obrigatório
- **Descrição**: Garante que o domínio da ontologia seja informado
- **Critérios**:
  - Domínio é obrigatório para cadastro
  - Domínio deve ser significativo
- **Severidade**: ERRO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_002 - CorpusNaoVazioRule
- **Nome**: Corpus Não Vazio
- **Descrição**: Garante que o corpus para geração de ontologia não seja vazio
- **Critérios**:
  - Corpus não pode ser vazio ou nulo
  - Texto descritivo deve ser fornecido
- **Severidade**: ERRO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_003 - OntologiaEmUsoNaoPodeSerExcluidaRule
- **Nome**: Ontologia em Uso Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de ontologias associadas a agentes
- **Critérios**:
  - Ontologia não pode ser excluída se utilizada por agentes
  - Sistema lista agentes dependentes
- **Severidade**: ERRO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_004 - EstatisticasAtualizadasRule
- **Nome**: Estatísticas Atualizadas
- **Descrição**: Garante que as estatísticas da ontologia sejam calculadas e mantidas atualizadas
- **Critérios**:
  - Total de classes deve ser calculado
  - Total de propriedades deve ser calculado
  - Total de axiomas deve ser calculado
  - Estatísticas são recalculadas após mudanças
- **Severidade**: INFO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_005 - SintaxeOWLValidaRule
- **Nome**: Sintaxe OWL Válida
- **Descrição**: Valida a sintaxe OWL antes de persistir a ontologia
- **Critérios**:
  - Ontologia deve ter sintaxe OWL válida
  - Reasoner deve ser capaz de carregar a ontologia
  - Erros de sintaxe são reportados
- **Severidade**: ERRO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_006 - TempoGeracaoLimitadoRule
- **Nome**: Tempo de Geração Limitado
- **Descrição**: Limita o tempo para geração de ontologia via LLM
- **Critérios**:
  - Geração deve ocorrer em menos de 30 segundos
  - Sistema oferece processamento assíncrono como alternativa
- **Severidade**: AVISO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_007 - IdentificadorOntologiaUnicoRule
- **Nome**: Identificador de Ontologia Único
- **Descrição**: Garante que o identificador da ontologia seja único
- **Critérios**:
  - Identificador é obrigatório
  - Deve ser único no sistema
- **Severidade**: ERRO
- **Referência CDU**: CDU010-Manter-Ontologia

##### ONT_008 - FormatoOntologiaValidoRule
- **Nome**: Formato de Ontologia Válido
- **Descrição**: Garante que o formato da ontologia seja suportado
- **Critérios**:
  - Formato deve ser: OWL/XML, Turtle ou JSON-LD
  - Sistema converte entre formatos se necessário
- **Severidade**: ERRO
- **Referência CDU**: CDU010-Manter-Ontologia

## Validadores

- `OntologiaValidator` - Orquestra regras de Ontologia

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<OntologiaDTO> {
    @Override
    public String getCode() {
        return "ONT_001";
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
    public void validate(OntologiaDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`