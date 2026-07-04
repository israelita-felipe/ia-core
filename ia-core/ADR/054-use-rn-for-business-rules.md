# ADR 054: Uso de RN para Documentação de Regras de Negócio

## Status

**Aceito**

## Contextualização

O projeto ia-core-apps precisa de uma estratégia consistente para documentação de regras de negócio que garanta clareza, rastreabilidade e alinhamento com casos de uso e testes. Atualmente, existem regras de negócio implementadas em código, mas falta uma abordagem padronizada para sua documentação e estruturação.

## Decisões

### 1. Estrutura Centralizada de Regras de Negócio

**Decisão**: Criar estrutura centralizada para documentação de regras de negócio ao lado da estrutura de CDUs

**Localização**: `/home/israel/git/ia-core-apps/ia-core/RN/`

**Estrutura**:
```
RN/
├── README.md
├── ModuloFuncionalidade1/
│   └── README.md
├── ModuloFuncionalidade2/
│   └── README.md
└── ...
```

**Justificativa**:
- Facilita descoberta e manutenção de regras de negócio
- Mantém proximidade com CDUs para rastreabilidade
- Permite organização por módulo funcional
- Segue padrão já estabelecido no projeto gestor-igreja

### 2. Padrão de Nomenclatura de Regras

**Decisão**: Usar padrão de nomenclatura consistente para códigos de regras de negócio

**Formato**: `<PREFIXO>_<NUMERO>`

**Prefixos por Módulo**:
- 3 letras em maiúsculas identificando o módulo
- Exemplos: FAM (Família), PES (Pessoa), FIN (Financeiro), CON (Contato), ESC (Escala), EVT (Evento), LEI (Leitura), END (Endereço)

**Numeração**:
- 3 dígitos sequenciais por módulo (001, 002, 003, ...)
- Reinicia numeração para cada módulo

**Exemplos**:
- FAM_001 - FamiliaNomeObrigatorioRule
- PES_001 - PessoaNomeObrigatorioRule
- FIN_001 - FinanceiroSaldoPositivoRule

**Justificativa**:
- Facilita identificação rápida do módulo
- Permite expansão sem conflitos
- Segue padrão já utilizado no projeto gestor-igreja
- Alinhado com nomenclatura de CDUs (CDU001, CDU002, etc.)

### 3. Estrutura do Documento de Regras de Negócio

**Decisão**: Cada módulo funcional deve ter um documento README.md com estrutura padronizada

**Estrutura do Documento**:
```markdown
# Regras de Negócio - Nome do Módulo

## Visão Geral

Breve descrição do propósito do módulo e escopo das regras.

## Referência

- **CDU**: `CDU/NomeDoModulo/README.md`
- **Service**: `nome-do-service`
- **Módulo**: `ia-core-*-service`

## Regras de Negócio

### PREFIXO_001 - NomeDaRule

- **Nome**: Nome descritivo da regra em português
- **Descrição**: Descrição clara do propósito da regra
- **Critérios**:
    - Critério 1
    - Critério 2
    - Critério 3
- **Severidade**: ERRO | AVISO | INFORMATIVO
- **Referência CDU**: RN001 (ou número da regra no CDU)
- **Fluxo Alternativo**: X.Y (opcional, se aplicável)

### PREFIXO_002 - OutraRule

...

## Padrão de Implementação

Exemplo de código Java implementando a regra.

## Referências

- **CDU**: NomeDoModulo
- **CDU**: OutroModuloRelacionado (se aplicável)
- **Service**: NomeDoService
```

**Justificativa**:
- Documentação completa e estruturada
- Rastreabilidade direta com CDUs
- Facilita compreensão por desenvolvedores e analistas
- Inclui exemplos de implementação

### 4. Princípios para Definição de Regras de Negócio

**Decisão**: Seguir princípios estabelecidos pelo Business Rules Manifesto e BABOK

**Princípios Fundamentais**:

1. **Atomicidade**: Cada regra deve descrever um único conceito ou restrição
2. **Declaratividade**: Regras devem ser expressas de forma declarativa, não imperativa
3. **Independência de Processo**: Regras não devem estar contidas em processos ou procedimentos
4. **Testabilidade**: Regras devem permitir condições pass/falha mensuráveis
5. **Vocabulário de Domínio**: Regras devem usar vocabulário padrão do negócio
6. **Rastreabilidade**: Regras devem ser rastreáveis a requisitos e casos de uso
7. **Versão Única**: Regras devem ter uma única fonte de verdade

**Justificativa**:
- Baseado em padrões da indústria (Business Rules Manifesto, BABOK)
- Facilita manutenção e evolução das regras
- Permite reutilização em diferentes contextos
- Garante clareza e consistência

### 5. Tipos de Regras de Negócio

**Decisão**: Classificar regras em categorias para melhor organização

**Categorias**:

1. **Regras Definicionais (Estruturais)**
   - Definem estrutura de conceitos de negócio
   - Sempre carregam sentido de necessidade ou impossibilidade
   - Exemplo: "Uma família deve ter pelo menos um responsável"

2. **Regras Comportamentais**
   - Guiam ou influenciam comportamento
   - Podem ser permissivas ou restritivas
   - Exemplo: "Uma pessoa pode pertencer a uma única família"

3. **Regras de Validação**
   - Validam dados e entradas
   - Focam em integridade de dados
   - Exemplo: "Nome não pode ser nulo ou vazio"

4. **Regras de Integridade**
   - Garantem consistência de relacionamentos
   - Focam em restrições de negócio
   - Exemplo: "Pessoa não pode estar em mais de uma família"

**Justificativa**:
- Facilita compreensão do propósito de cada regra
- Permite estratégias de teste específicas por tipo
- Alinhado com classificação do Business Rules Manifesto

### 6. Severidade de Regras

**Decisão**: Classificar regras por nível de severidade para priorização e tratamento

**Níveis de Severidade**:

1. **ERRO**
   - Bloqueia operação
   - Deve ser corrigido obrigatoriamente
   - Exemplo: Campo obrigatório ausente

2. **AVISO**
   - Não bloqueia operação
   - Requer atenção mas permite continuação
   - Exemplo: Ausência de responsável definido

3. **INFORMATIVO**
   - Apenas informativo
   - Não impacta operação
   - Exemplo: Confirmação de sucesso

**Justificativa**:
- Permite tratamento diferenciado por tipo de violação
- Facilita priorização de correções
- Alinhado com padrões de validação de sistemas

### 7. Relação com ADR-012 (Testing Patterns)

**Decisão**: Integrar documentação de regras de negócio com estratégia de testes

**Integração**:

1. **Casos de Teste Baseados em Regras**
   - Cada regra de negócio deve ter casos de teste correspondentes
   - Testes devem validar critérios de aceitação da regra
   - Documentos de test-cases devem referenciar códigos de regras

2. **Nomenclatura de Testes**
   - Usar código da regra no nome do teste
   - Exemplo: `testFAM001_NomeObrigatorio()`

3. **Cobertura de Regras**
   - JaCoCo deve cobrir implementação de regras
   - Testes devem validar cenários de sucesso e falha
   - Regras críticas (ERRO) devem ter cobertura mais alta

**Justificativa**:
- Garante que regras de negócio sejam testadas
- Facilita rastreabilidade entre regras e testes
- Alinha documentação com implementação

### 8. Relação com ADR-053 (CDU - Casos de Uso)

**Decisão**: Integrar regras de negócio com documentação de casos de uso

**Integração**:

1. **Mapeamento Bidirecional**
   - CDUs devem referenciar regras de negócio aplicáveis
   - Regras de negócio devem referenciar CDUs onde se aplicam
   - Campo "Referência CDU" no documento de regras

2. **Fluxos e Regras**
   - Fluxos principais e alternativos do CDU devem citar regras
   - Regras devem indicar quais fluxos do CDU afetam
   - Campo "Fluxo Alternativo" opcional no documento de regras

3. **Consistência de Nomenclatura**
   - RN001 no CDU deve mapear para PREFIXO_001 nas regras
   - Manter consistência entre documentos

**Justificativa**:
- Garante rastreabilidade completa entre requisitos e regras
- Facilita impacto analysis quando regras mudam
- Mantém consistência entre documentações

### 9. Padrão de Implementação em Código

**Decisão**: Seguir padrão de implementação já estabelecido no projeto gestor-igreja

**Interface BusinessRule**:
```java
@ValidatorScope
public class XxxRN001 implements BusinessRule<DTO> {
    private static final String CODE = "PREFIXO_001";
    private final Translator translator;

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public void validate(DTO dto, ValidationResult result) {
        // Implementação da validação
    }
}
```

**Localização**:
- `src/main/java/com/ia/core/{modulo}/service/{modulo}/rules/`

**Justificativa**:
- Reutiliza padrão já validado no projeto
- Facilita integração com Service Validator Pattern (ADR-019)
- Permite injeção de dependências e internacionalização

### 10. Governança e Manutenção

**Decisão**: Estabelecer processo de governança para manutenção de regras

**Processo**:

1. **Criação de Regra**
   - Analisar requisitos e CDUs
   - Identificar regras necessárias
   - Documentar em RN/Modulo/README.md
   - Implementar em código
   - Criar testes correspondentes

2. **Alteração de Regra**
   - Atualizar documento RN
   - Atualizar implementação
   - Atualizar testes
   - Notificar equipes impactadas
   - Registrar histórico de mudanças

3. **Desativação de Regra**
   - Marcar como DEPRECATED no documento
   - Manter histórico
   - Remover implementação após período de transição

**Justificativa**:
- Garante controle de mudanças
- Mantém histórico para auditoria
- Facilita comunicação entre equipes

## Referências

### Referências da Indústria

- **Business Rules Manifesto**: https://businessrulesgroup.org/brmanifesto.htm
- **BABOK Guide - Business Rules Analysis**: https://www.iiba.org/knowledgehub/business-analysis-body-of-knowledge-babok-guide/10-techniques/10-9-business-rules-analysis/
- **Agile Modeling - Business Rules**: https://agilemodeling.com/artifacts/businessrule.htm
- **Business Rules Community**: https://www.brcommunity.com/articles.php?id=b525

### Referências Acadêmicas (arXiv)

- **BREX: Business Rule Extraction Benchmark**: https://arxiv.org/html/2505.18542
- **RuleCNL: Controlled Natural Language for Business Rules**: https://ar5iv.labs.arxiv.org/html/1406.2096
- **DeepRule: Automated Business Rule Generation**: https://arxiv.org/abs/2512.03607v1
- **Rule Module Inheritance**: https://ar5iv.labs.arxiv.org/html/1808.08634
- **SBVR-based Business Rule Creation**: https://dl.acm.org/doi/10.1145/3299771.3299786

### ADRs Relacionados

- **ADR-012**: Testing Patterns - Integração com testes de regras de negócio
- **ADR-018**: Business Rule Chain Pattern - Implementação de cadeias de regras
- **ADR-019**: Service Validator Pattern - Validação dinâmica com regras
- **ADR-053**: Use CDU for Use Case Documentation - Mapeamento com casos de uso

### Projetos de Referência

- **gestor-igreja/Biblia/RN**: Estrutura de regras de negócio existente
- **ia-core-test**: Classes base para testes de regras

## Consequências

### Positivas

- Documentação consistente e padronizada de regras de negócio
- Rastreabilidade completa entre CDUs, regras e testes
- Facilidade de manutenção e evolução das regras
- Alinhamento com padrões da indústria
- Integração natural com ADRs existentes

### Negativas

- Curva de aprendizado inicial para equipe
- Sobrecarga de documentação para regras simples
- Necessidade de disciplina para manter sincronia entre documentos

### Mitigações

- Fornecer treinamento e exemplos para equipe
- Criar templates e ferramentas para automação
- Estabelecer revisões periódicas de documentação
- Usar validação automatizada para verificar consistência
