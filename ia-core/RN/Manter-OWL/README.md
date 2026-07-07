# Regras de Negócio - Módulo Manter OWL

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter OWL do ia-core-apps, para gerenciamento de ontologias OWL e reasoners.

## Referência
- **CDU**: CDU009-Manter-OWL
- **Service**: ia-core-owl-service
- **Módulo**: ia-core-owl-model

## Entidades

### OntologiaOWL
Representa uma ontologia OWL 2 DL para raciocínio lógico e representação de conhecimento com reasoners.

#### Regras Implementadas

##### OWL_001 - DominioOntologiaOWLUnicoRule
- **Nome**: Domínio de Ontologia OWL Único
- **Descrição**: Garante que o domínio da ontologia OWL seja único no sistema
- **Critérios**:
  - Domínio é obrigatório
  - Deve seguir padrão URI válido
  - Deve ser único no sistema
- **Severidade**: ERRO
- **Referência CDU**: CDU009-Manter-OWL

##### OWL_002 - FormatoOWLValidoRule
- **Nome**: Formato OWL Válido
- **Descrição**: Garante que o formato da ontologia OWL seja suportado
- **Critérios**:
  - Formato deve ser: OWL/XML, RDF/XML, Turtle ou JSON-LD
  - Formato é obrigatório para persistência
- **Severidade**: ERRO
- **Referência CDU**: CDU009-Manter-OWL

##### OWL_003 - ReasonerSelecionadoRule
- **Nome**: Reasoner Selecionado
- **Descrição**: Garante que um reasoner válido seja selecionado para inferências
- **Critérios**:
  - Reasoner deve ser: HERMIT, PELLETA, OPENLLET ou OWLIMPACT
  - Reasoner é obrigatório para operações de inferência
- **Severidade**: ERRO
- **Referência CDU**: CDU009-Manter-OWL

##### OWL_004 - SintaxeOWLValidaRule
- **Nome**: Sintaxe OWL Válida
- **Descrição**: Valida a sintaxe OWL antes de persistir a ontologia
- **Critérios**:
  - Ontologia deve ter sintaxe OWL válida
  - Reasoner deve ser capaz de carregar a ontologia
  - Erros de sintaxe são reportados com detalhes
- **Severidade**: ERRO
- **Referência CDU**: CDU009-Manter-OWL

##### OWL_005 - OntologiaOWLEmUsoNaoPodeSerExcluidaRule
- **Nome**: Ontologia OWL em Uso Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de ontologias OWL associadas a agentes ou processamentos
- **Critérios**:
  - Ontologia OWL não pode ser excluída se utilizada por agentes
  - Ontologia OWL não pode ser excluída se em processamento NLP
  - Sistema lista dependências antes da exclusão
- **Severidade**: ERRO
- **Referência CDU**: CDU009-Manter-OWL

##### OWL_006 - AxiomasValidosRule
- **Nome**: Axiomas Sintaticamente Válidos
- **Descrição**: Garante que os axiomas OWL sejam sintaticamente corretos
- **Critérios**:
  - Axiomas devem ter sintaxe OWL válida
  - Tipos suportados: Classe, ObjectProperty, DataProperty, Annotation
  - Expressões mal-formadas são rejeitadas
- **Severidade**: ERRO
- **Referência CDU**: CDU009-Manter-OWL

##### OWL_007 - InferenciasCalculadasRule
- **Nome**: Inferências Calculadas
- **Descrição**: Garante que as inferências sejam calculadas com o reasoner configurado
- **Critérios**:
  - Sistema executa reasoner após carregar ontologia
  - Inferências são armazenadas para consulta
  - Erros de inconsistência são reportados
- **Severidade**: INFO
- **Referência CDU**: CDU009-Manter-OWL

## Validadores

- `OntologiaOWLValidator` - Orquestra regras de OntologiaOWL

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<OntologiaOWLDTO> {
    @Override
    public String getCode() {
        return "OWL_001";
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
    public void validate(OntologiaOWLDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`