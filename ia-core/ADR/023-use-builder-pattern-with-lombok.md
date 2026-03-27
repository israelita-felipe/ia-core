# ADR-023: Usar Builder Pattern com Lombok para Criação de Objetos

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma padronizada e fluente de criar objetos complexos, especialmente DTOs, sem:
- Construtores com muitos parâmetros
- telescoping constructor antipattern
- code repetido para Setting valores

## Decisão

Usar **Builder Pattern** gerado pelo Lombok (`@Builder`) para criação de objetos, com validação opcional no construtor.

## Detalhes

### Ativação do Builder

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
    private Long id;
    private String nome;
    private TipoPessoa tipo;
    private String cpf;
}
```

### Uso básico

```java
// Criação de objeto
PessoaDTO pessoa = PessoaDTO.builder()
    .nome("João Silva")
    .tipo(TipoPessoa.FISICA)
    .cpf("12345678901")
    .build();

// Encadeamento fluente
PessoaDTO pessoa = PessoaDTO.builder()
    .nome("João Silva")
    .tipo(TipoPessoa.FISICA)
    .cpf("12345678901")
    .build();
```

### Com Validação no Construtor

```java
@Builder
public record EventoDTO(
    Long id,
    @NotBlank String titulo,
    @NotNull LocalDateTime dataInicio,
    LocalDateTime dataFim
) {
    public EventoDTO {
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("Data fim não pode ser antes do início");
        }
    }
}
```

### Builder para Entidades

```java
@Entity
@Table(name = "evento")
public class Evento extends BaseEntity {
    
    @Getter
    @Setter
    private String titulo;
    
    @Getter
    @Setter
    private LocalDateTime dataInicio;
    
    // Builder manual (não usar @Builder em entidades JPA)
    public static Evento create(String titulo, LocalDateTime dataInicio) {
        Evento evento = new Evento();
        evento.setTitulo(titulo);
        evento.setDataInicio(dataInicio);
        return evento;
    }
}
```

### Builder para Testes

```java
@Test
void deveCriarPessoa() {
    // Fixture com Builder
    PessoaDTO pessoa = PessoaDTO.builder()
        .nome("João Silva")
        .tipo(TipoPessoa.FISICA)
        .cpf("12345678901")
        .build();
    
    assertThat(pessoa.getNome()).isEqualTo("João Silva");
}
```

### Builder com Valores Padrão

```java
@Builder
public class ConfiguracaoDTO {
    
    @Builder.Default
    private Boolean ativo = true;
    
    @Builder.Default
    private String tema = "claro";
    
    @Builder.Default
    private Integer paginaTamanho = 20;
}
```

###Builder com método fromEntity

```java
public class PessoaDTO {
    
    @Builder
    public static PessoaDTO fromEntity(Pessoa pessoa) {
        return PessoaDTO.builder()
            .id(pessoa.getId())
            .nome(pessoa.getNome())
            .tipo(pessoa.getTipo())
            .cpf(pessoa.getCpf())
            .build();
    }
}
```

## Consequências

### Positivas

- ✅ Código fluente e legível
- ✅ Construtores não precisam de muitos parâmetros
- ✅ Valores opcionais são claros
- ✅ Validação no construtor do record
- ✅ Builder padrão vs customizado

### Negativas

- ❌ Código adicional no bytecode
- ❌ Em entidades JPA, usar factory methods
- ❌ Não usar em classes com muito estado

## Status de Implementação

✅ **COMPLETO**

- @Builder em todos os DTOs
- Factory methods em entidades
- Testes usam Builders

## Data

2024-01-15

## Revisores

- Team Lead
- Architect

## Referências

1. **Lombok - @Builder**
   - URL: https://projectlombok.org/features/Builder
   - Documentação oficial

2. **Lombok - @Builder with Default**
   - URL: https://projectlombok.org/features/Builder#default-values
   - Valores padrão

3. **Java Design Patterns - Builder**
   - URL: https://refactoring.guru/design-patterns/builder
   - Padrão de projeto

4. **Baeldung - Lombok Builder**
   - URL: https://www.baeldung.com/lombok-builder
   - Tutorial

5. **Effective Java - Builder**
   - Item 2: Consider a builder when faced with many constructor parameters