# Regras de Negócio - Módulo Manter NLP

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter NLP do ia-core-apps.

## Entidades

### ProcessamentoTexto
Representa o processamento de texto via técnicas de NLP.

#### Regras Implementadas

##### NLP_001 - TokenizerProcessaTextoRule
- **Nome**: Tokenizer Processa Texto
- **Descrição**: Garante que o tokenizer processe o texto e gere tokens válidos
- **Critérios**:
  - Texto deve ser processado pelo tokenizer
  - Lista de tokens deve ser retornada
  - Metadados devem ser gerados
- **Severidade**: ERRO
- **Referência CDU**: CDU008-Manter-NLP

##### NLP_002 - ExtracaoEntidadesValidaRule
- **Nome**: Extração de Entidades Válida
- **Descrição**: Garante que a extração de entidades identifique os tipos corretos
- **Critérios**:
  - Identifica pessoas, locais, datas e números
  - Tipos de entidades são classificados corretamente
  - Lista de entidades é retornada
- **Severidade**: ERRO
- **Referência CDU**: CDU008-Manter-NLP

##### NLP_003 - ParsingLiturgicoValidoRule
- **Nome**: Parsing Litúrgico Válido
- **Descrição**: Garante que o parsing de texto litúrgico extraia referências bíblicas
- **Critérios**:
  - Texto é parseado com gramática configurada
  - Referências bíblicas são extraídas
  - Estrutura processada é retornada
- **Severidade**: ERRO
- **Referência CDU**: CDU008-Manter-NLP

##### NLP_004 - ModeloNLPConfiguradoRule
- **Nome**: Modelo NLP Configurado
- **Descrição**: Garante que o modelo NLP esteja configurado antes do processamento
- **Critérios**:
  - Modelo NLP deve estar disponível
  - Configuração deve ser válida
- **Severidade**: ERRO
- **Referência CDU**: CDU008-Manter-NLP

##### NLP_005 - TextoNaoVazioParaProcessamentoRule
- **Nome**: Texto Não Vazio para Processamento
- **Descrição**: Garante que o texto fornecido para processamento não seja vazio
- **Critérios**:
  - Texto não pode ser vazio ou nulo
  - Texto deve ter conteúdo processável
- **Severidade**: ERRO
- **Referência CDU**: CDU008-Manter-NLP

##### NLP_006 - GramaticaConfiguradaRule
- **Nome**: Gramática Configurada
- **Descrição**: Garante que a gramática de parsing esteja configurada para textos litúrgicos
- **Critérios**:
  - Gramática ANTLR deve estar disponível
  - Gramática deve estar carregada no sistema
- **Severidade**: ERRO
- **Referência CDU**: CDU008-Manter-NLP

## Validadores

- `ProcessamentoTextoValidator` - Orquestra regras de ProcessamentoTexto

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<ProcessamentoTextoDTO> {
    @Override
    public String getCode() {
        return "NLP_001";
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
    public void validate(ProcessamentoTextoDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`