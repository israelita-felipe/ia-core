# ADR-018: Usar Business Rule Chain para Validações de Negócio

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma flexível e composável de implementar regras de negócio que:
- Possam ser combinadas em cadeias de validação
- Permitam execução sequencial com parada condicional
- Acumulem erros de múltiplas regras
- Sejam reutilizáveis entre diferentes serviços
- Tenham identificação única para rastreabilidade

## Decisão

Usar **Business Rule Chain Pattern** com interface `BusinessRule<T>` e classe `BusinessRuleChain<T>` para composição de validações.

## Detalhes

### Interface BusinessRule

```java
public interface BusinessRule<T extends Serializable> extends Serializable {

  /** Código único: [DOMÍNIO]_001 (ex: USR_001, CTE_001) */
  String getCode();

  /** Nome para exibição */
  String getName();

  /** Descrição detalhada */
  String getDescription();

  /** Tradutor para i18n */
  Translator getTranslator();

  /** Verifica se a regra é aplicável */
  default boolean isApplicable(T object) {
    return true;
  }

  /** Executa a validação */
  void validate(T object, ValidationResult result);
}
```

### BusinessRuleChain

```java
public class BusinessRuleChain<T extends Serializable> {

  private final List<BusinessRule<? super T>> rules = new ArrayList<>();
  private Function<T, Boolean> stopCondition;

  public BusinessRuleChain<T> addRule(BusinessRule<? super T> rule) {
    rules.add(rule);
    return this;
  }

  public BusinessRuleChain<T> stopWhen(Function<T, Boolean> stopCondition) {
    this.stopCondition = stopCondition;
    return this;
  }

  public void validate(T object) throws ValidationException {
    ValidationResult result = ValidationResult.create();
    validate(object, result);
    if (result.hasErrors()) {
      throw new ValidationException(result);
    }
  }

  public ValidationException validateAndCollect(T object) {
    ValidationResult result = ValidationResult.create();
    validate(object, result);
    return result.hasErrors() ? new ValidationException(result) : null;
  }
}
```

### Exemplo de Uso

```java
// Definindo uma regra
public class EmailUnicoRule implements BusinessRule<PessoaDTO> {

  @Override
  public String getCode() {
    return "PESSOA_001";
  }

  @Override
  public String getName() {
    return "Email deve ser único";
  }

  @Override
  public void validate(PessoaDTO dto, ValidationResult result) {
    if (repository.existsByEmail(dto.getEmail())) {
      result.addError("email", translator.get(PESSOA_EMAIL_DUPLICADO));
    }
  }
}

// Compondo cadeia de regras
BusinessRuleChain<PessoaDTO> chain = BusinessRuleChain.<PessoaDTO>create()
    .addRule(new EmailUnicoRule())
    .addRule(new CpfValidoRule())
    .addRule(new DataNascimentoRule())
    .stopWhen(dto -> dto.getEmail() == null);

// Executando
chain.validate(pessoaDTO);
```

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| Validação inline | Simples | Não reutilizável |
| Jakarta Validation | Nativo, declarativo | Limitado para regras complexas |
| **BusinessRuleChain** | Composável, rastreável, i18n | Mais boilerplate |
| Specification Pattern | Flexível | Focado em queries, não validação |

## Consequências

### Positivas
- ✅ Regras reutilizáveis entre serviços
- ✅ Composição flexível de validações
- ✅ Código único para rastreabilidade (PESSOA_001)
- ✅ Condição de parada configurável
- ✅ Acumulação de múltiplos erros
- ✅ Suporte a i18n via Translator

### Negativas
- ❌ Mais boilerplate que validação inline
- ❌ Requer disciplina para manter regras coesas
- ❌ Cadeias longas podem ser difíceis de debugar

## Status de Implementação

✅ **COMPLETO**

- [`BusinessRule.java`](../../ia-core-service/src/main/java/com/ia/core/service/rules/BusinessRule.java) implementado
- [`BusinessRuleChain.java`](../../ia-core-service/src/main/java/com/ia/core/service/rules/BusinessRuleChain.java) implementado
- Integrado com `ValidationResult` e `ValidationException`

## Data

2024-03-25

## Revisores

- Team Lead
- Architect

## Referências

1. **Chain of Responsibility Pattern**
   - URL: https://refactoring.guru/design-patterns/chain-of-responsibility
   - Padrão GoF original

2. **Martin Fowler - Specification Pattern**
   - URL: https://martinfowler.com/apsupp/spec.pdf
   - Padrão relacionado para regras de negócio

3. **Baeldung - Chain of Responsibility**
   - URL: https://www.baeldung.com/chain-of-responsibility-java
   - Implementação em Java
