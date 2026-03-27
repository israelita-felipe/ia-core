# ADR-003: Usar Translator Pattern para Internacionalização (i18n)

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma consistente de gerenciar labels, mensagens de validação e feedbacks para múltiplos idiomas.

## Decisão

Usar **Translator Pattern** com classes Java estáticas para chaves de tradução.

## Detalhes

### Estrutura do Translator

```java
public class PessoaTranslator {
    
    public static final class HELP {
        public static final String PESSOA = "pessoa.help";
        public static final String NOME = "pessoa.help.nome";
    }
    
    public static final String NOME = "pessoa.nome";
    public static final String TIPO = "pessoa.tipo";
    
    public static final class VALIDATION {
        public static final String TIPO_NOT_NULL = "pessoa.validation.tipo.not.null";
        public static final String NOME_NOT_NULL = "pessoa.validation.nome.not.null";
        public static final String NOME_SIZE = "pessoa.validation.nome.size";
    }
    
    public static final class ERROR {
        public static final String NOT_FOUND = "pessoa.error.notfound";
        public static final String DUPLICATE = "pessoa.error.duplicate";
    }
    
    public static final class MESSAGE {
        public static final String CREATED = "pessoa.message.created";
        public static final String UPDATED = "pessoa.message.updated";
        public static final String DELETED = "pessoa.message.deleted";
    }
}
```

### Arquivo de Properties

```properties
# Labels
pessoa.nome=Nome
pessoa.tipo=Tipo

# Validation
pessoa.validation.tipo.not.null=Tipo é obrigatório
pessoa.validation.nome.not.null=Nome é obrigatório
pessoa.validation.nome.size=Nome deve ter entre 3 e 100 caracteres

# Errors
pessoa.error.notfound=Pessoa não encontrada
pessoa.error.duplicate=Pessoa já existe

# Messages
pessoa.message.created=Pessoa criada com sucesso
pessoa.message.updated=Pessoa atualizada com sucesso
pessoa.message.deleted=Pessoa excluída com sucesso
```

## Implementação

### DTO com Validação

```java
public class PessoaDTO {
    @NotNull(message = "{pessoa.validation.tipo.not.null}")
    private TipoPessoa tipo;
    
    @NotNull(message = "{pessoa.validation.nome.not.null}")
    @Size(min = 3, max = 100, message = "{pessoa.validation.nome.size}")
    private String nome;
}
```

### Uso no View

```java
TextField nomeField = new TextField(PessoaTranslator.NOME);
Label tipoLabel = new Label(PessoaTranslator.TIPO);
```

## Consequências

### Positivas

- ✅ Type-safe, autocompletion no IDE
- ✅ Refactoring seguro
- ✅ Consistência entre camadas
- ✅ Suporte a locales

### Negativas

- ❌ Classes podem ficar grandes
- ❌ Requer manutençãoda classe + properties

## Status de Implementação

✅ **COMPLETO**

- 40+ Translators implementados
- Arquivos properties em cada módulo
- Integração com Jakarta Validation

## Data

2024-02-01

## Revisores

- Team Lead
- Architect

## Referências

1. **Spring Framework - Message Source**
   - URL: https://docs.spring.io/spring-framework/reference/core/beans/foundation-messages.html
   - Documentação oficial sobre MessageSource e internacionalização

2. **Baeldung - Spring Internationalization**
   - URL: https://www.baeldung.com/spring-internationalization
   - Guia completo sobre i18n com Spring

3. **Jakarta Bean Validation Specification**
   - URL: https://jakarta.ee/specifications/bean-validation/3.0/bean-validation-spec-3.0.html
   - Especificação oficial com suporte a message interpolation

4. **Java MessageFormat Documentation**
   - URL: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/MessageFormat.html
   - Documentação para formatação de mensagens com parâmetros

5. **Baeldung - Properties with Spring**
   - URL: https://www.baeldung.com/spring-properties-file
   - Boas práticas para carregar e usar arquivos properties
