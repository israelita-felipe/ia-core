# ADR-019: Usar Service Validator Pattern para Validações de Serviço

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma padronizada de implementar validações em serviços que:
- Permita registro dinâmico de validadores
- Suporte validações compostas e contextuais
- Acumule erros de múltiplas fontes
- Seja removível após uso (try-with-resources)
- Integre com Jakarta Validation e regras de negócio

## Decisão

Usar **Service Validator Pattern** com interface `IServiceValidator<D>` e `HasValidation<D>` para validações de serviço.

## Detalhes

### Interface IServiceValidator

```java
public interface IServiceValidator<D extends Serializable> {

  /** Conjunto de validadores registrados */
  Set<HasValidation<D>> getHasValidation();

  /** Tradutor para i18n */
  Translator getTranslator();

  /** Registra validador temporário (try-with-resources) */
  default ValidatorRegistration registry(HasValidation<D> hasValidator) {
    getHasValidation().add(hasValidator);
    return () -> getHasValidation().remove(hasValidator);
  }

  /** Valida e lança exceção */
  default void validate(D object) throws ValidationException {
    ValidationResult result = ValidationResult.create();
    validate(object, result);
    if (result.hasErrors()) {
      throw new ValidationException(result);
    }
  }

  /** Valida acumulando erros */
  void validate(D object, ValidationResult result);
}
```

### ValidatorRegistration

```java
@FunctionalInterface
interface ValidatorRegistration extends AutoCloseable {
  void remove();

  @Override
  default void close() {
    remove();
  }
}
```

### Exemplo de Uso

```java
@Service
public class PessoaServiceValidator implements IServiceValidator<PessoaDTO> {

  private final Set<HasValidation<PessoaDTO>> validators = new HashSet<>();

  @Override
  public Set<HasValidation<PessoaDTO>> getHasValidation() {
    return validators;
  }

  @Override
  public void validate(PessoaDTO dto, ValidationResult result) {
    // Jakarta Validation
    validateJakarta(dto, result);

    // Validadores registrados
    for (HasValidation<PessoaDTO> validator : validators) {
      validator.validate(dto, result);
    }
  }
}

// Uso com validador temporário
try (var reg = validator.registry(novoValidador)) {
  validator.validate(dto);
} // validador removido automaticamente
```

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| Jakarta Validation apenas | Nativo, declarativo | Limitado para regras complexas |
| Validação manual | Flexível | Não padronizado |
| **IServiceValidator** | Dinâmico, composável, i18n | Mais configuração |

## Consequências

### Positivas
- ✅ Validações dinâmicas e removíveis
- ✅ Try-with-resources para cleanup automático
- ✅ Integração com Jakarta Validation
- ✅ Acumulação de erros estruturada
- ✅ Suporte a i18n via Translator

### Negativas
- ❌ Mais complexidade que validação simples
- ❌ Requer gerenciamento de ciclo de vida

## Status de Implementação

✅ **COMPLETO**

- [`IServiceValidator.java`](../../ia-core-service/src/main/java/com/ia/core/service/validators/IServiceValidator.java) implementado
- Integrado com `ValidationResult` e `ValidationException`

## Data

2024-03-20

## Revisores

- Team Lead
- Architect

## Referências

1. **Jakarta Bean Validation**
   - URL: https://jakarta.ee/specifications/bean-validation/3.0/
   - Especificação oficial

2. **Baeldung - Custom Validators**
   - URL: https://www.baeldung.com/spring-custom-validator
   - Validadores customizados no Spring
