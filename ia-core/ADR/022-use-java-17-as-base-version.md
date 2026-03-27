# ADR-022: Usar Java 17 como Versão Base do Projeto

## Status

✅ Aceito

## Contexto

O projeto ia-core-apps precisa definir uma versão do Java que:
- Ofereça recursos modernos de linguagem
- Tenha suporte de longo prazo (LTS)
- Seja compatível com frameworks utilizados
- Tenha performance otimizada

## Decisão

Usar **Java 21 (LTS)** como versão base do projeto, com target de compilação e source.

## Detalhes

### Versão Escolhida

| Versão | Tipo | Suporte | Recursos |
|--------|------|---------|----------|
| Java 8 | LTS | Até 2030 | Lambdas, Streams |
| **Java 11** | LTS | Até 2032 | HttpClient, var |
| **Java 17** | LTS | Até 2029 | Records, Sealed Classes |
| Java 21 | LTS | Até 2031 | Virtual Threads |

### Configuração no pom.xml

```xml
<properties>
    <java.version>21</java.version>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <source>21</source>
        <target>21</target>
        <encoding>UTF-8</encoding>
    </configuration>
</plugin>
```

### Recursos do Java 21 Utilizados

| Recurso | Exemplo de Uso |
|---------|----------------|
| Records | DTOs, projeção de queries |
| Pattern Matching (instanceof) | Validações, casts |
| Switch Expressions | Operadores, status |
| Text Blocks | JSON, SQL inline |
| Sealed Classes | Hierarquias controladas |
| var (Java 10) | Variáveis locais |

### Records para DTOs

```java
// Em vez de classes com getters/setters
public record PessoaDTO(
    Long id,
    String nome,
    TipoPessoa tipo,
    String cpf
) {}

// Com validação no construtor
public record EventoDTO(
    Long id,
    @NotBlank String titulo,
    @NotNull LocalDateTime dataInicio
) {
    public EventoDTO {
        if (dataInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data não pode ser no passado");
        }
    }
}
```

### Pattern Matching

```java
// Antes
if (obj instanceof String) {
    String s = (String) obj;
    // uso de s
}

// Agora
if (obj instanceof String s) {
    // s disponível diretamente
    log.info("String: {}", s);
}
```

### Switch Expressions

```java
// Antes
switch (status) {
    case "ATIVO": return "Ativo";
    case "INATIVO": return "Inativo";
    default: return "Desconhecido";
}

// Agora
String descricao = switch (status) {
    case "ATIVO" -> "Ativo";
    case "INATIVO" -> "Inativo";
    default -> "Desconhecido";
};
```

### Text Blocks

```java
String json = """
{
    "nome": "%s",
    "tipo": "%s"
}
""".formatted(pessoa.getNome(), pessoa.getTipo());

String sql = """
    SELECT p.id, p.nome 
    FROM pessoa p 
    WHERE p.ativo = true
    """;
```

### Compatibility Matrix

| Framework | Versão Mínima | Java 21 |
|-----------|---------------|----------|
| Spring Boot | 2.5.x+ | ✅ |
| Hibernate | 6.0+ | ✅ |
| JUnit 5 | 5.7+ | ✅ |
| MapStruct | 1.5+ | ✅ |

## Consequências

### Positivas

- ✅ Recursos modernos de linguagem
- ✅ Suporte de longo prazo (LTS)
- ✅ Performance melhorada
- ✅ Records para DTOs mais limpos
- ✅ Switch expressions mais seguros

### Negativas

- ❌ Requer Java 17+ nos ambientes
- ❌ Alguns libs antigas podem não suportar
- ❌ Curva de aprendizado para devs de Java 8/11

## Status de Implementação

✅ **COMPLETO**

- Configurado em todos os pom.xml
- Código utiliza recursos do Java 21

## Data

26/03/2026

## Revisores

- Team Lead
- Architect

## Referências

1. **Java 21 Documentation**
   - URL: https://docs.oracle.com/en/java/javase/21/
   - Documentação oficial

2. **Spring Boot - Java Version**
   - URL: https://spring.io/blog/2021/09/02/a-new-era-of-spring-boot
   - Java 17 support

3. **Baeldung - Java 21 Features**
   - URL: https://www.baeldung.com/java-21-new-features
   - Novos recursos

4. **Oracle - Java SE Support**
   - URL: https://www.oracle.com/java/technologies/java-se-support-roadmap.html
   - Roadmap de suporte

5. **Baeldung - Java Records**
   - URL: https://www.baeldung.com/java-record-keyword
   - Tutorial sobre Records