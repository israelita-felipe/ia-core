# Regras de Negócio - Módulo Manter Template

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Template do ia-core-apps.

## Entidades

### Template
Representa um template de prompt parametrizável para uso por agentes LLM.

#### Regras Implementadas

##### TMP_001 - IdentificadorTemplateUnicoRule
- **Nome**: Identificador de Template Único
- **Descrição**: Garante que o identificador do template seja único no sistema
- **Critérios**:
  - Identificador é obrigatório
  - Deve ser único por tipo de finalidade
  - Comparação case-insensitive
- **Severidade**: ERRO
- **Referência CDU**: CDU017-Manter-Template

##### TMP_002 - ConteudoObrigatorioRule
- **Nome**: Conteúdo Obrigatório
- **Descrição**: Garante que o conteúdo do template seja informado
- **Critérios**:
  - Conteúdo é obrigatório
  - Não pode estar vazio ou em branco
  - Tamanho mínimo de 1 caractere
- **Severidade**: ERRO
- **Referência CDU**: CDU017-Manter-Template

##### TMP_003 - FormatoParametroValidoRule
- **Nome**: Formato de Parâmetro Válido
- **Descrição**: Garante que os parâmetros seguam o formato esperado
- **Critérios**:
  - Parâmetros devem seguir o padrão {{nome_parametro}}
  - Nomes de parâmetros devem ser válidos (alfanuméricos e sublinhados)
  - Parâmetros declarados devem ser usados no conteúdo
- **Severidade**: ERRO
- **Referência CDU**: CDU017-Manter-Template

##### TMP_004 - TemplateEmUsoNaoPodeSerExcluidoRule
- **Nome**: Template em Uso Não Pode Ser Excluído
- **Descrição**: Impede a exclusão de templates associados a agentes
- **Critérios**:
  - Template não pode ser excluído se utilizado por agentes
  - Sistema lista agentes dependentes
- **Severidade**: ERRO
- **Referência CDU**: CDU017-Manter-Template

##### TMP_005 - HistoricoVersoesTemplateRule
- **Nome**: Histórico de Versões do Template
- **Descrição**: Garante que o histórico de versões seja mantido
- **Critérios**:
  - Cada atualização cria nova versão
  - Conteúdo anterior é armazenado
  - Data da versão é registrada
- **Severidade**: INFO
- **Referência CDU**: CDU017-Manter-Template

##### TMP_006 - FinalidadeTemplateValidaRule
- **Nome**: Finalidade do Template Válida
- **Descrição**: Garante que a finalidade do template seja uma das opções válidas
- **Critérios**:
  - Finalidade deve ser: RESPOSTA_TEXTUAL, EXTRAIR_OBJETO, EXTRAIR_LISTA
  - Finalidade é usada para determinar como o LLM deve processar
- **Severidade**: ERRO
- **Referência CDU**: CDU017-Manter-Template

##### TMP_007 - ValorPadraoParametroValidoRule
- **Nome**: Valor Padrão do Parâmetro Válido
- **Descrição**: Garante que valores padrão dos parâmetros estejam no tipo correto
- **Critérios**:
  - Valor padrão deve corresponder ao tipo do parâmetro
  - Tipos válidos: String, Integer, Boolean, Date, etc.
- **Severidade**: AVISO
- **Referência CDU**: CDU017-Manter-Template

## Validadores

- `TemplateValidator` - Orquestra regras de Template
- `TemplateParametroValidator` - Orquestra regras de TemplateParametro

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<TemplateDTO> {
    @Override
    public String getCode() {
        return "TMP_001";
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
    public void validate(TemplateDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`