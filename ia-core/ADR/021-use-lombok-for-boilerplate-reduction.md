# ADR-021: Usar Lombok para Redução de Boilerplate

## Status

✅ Aceito

## Contexto

O código Java frequentemente contém boilerplate repetitivo como:
- Getters e Setters
- Constructors
- toString(), equals(), hashCode()
- Logger (@Slf4j)
- @RequiredArgsConstructor

O projeto precisa reduzir essa repetição para melhorar:
- Legibilidade do código
- Manutenibilidade
- Produtividade do desenvolvedor
- Redução de erros humanos

## Decisão

Usar **Lombok** como biblioteca de geração de código em tempo de compilação, integrado ao projeto via Maven.

## Detalhes

### Dependência Maven

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

### Anotações Utilizadas

| Anotação | Descrição | Uso |
|----------|-----------|-----|
| `@Getter` | Gera getters | Entidades, DTOs, Configs |
| `@Setter` | Gera setters | DTOs (evitar em entidades) |
| `@NoArgsConstructor` | Construtor vazio | JPA, Jackson |
| `@AllArgsConstructor` | Construtor com todos | DTOs, Services |
| `@RequiredArgsConstructor` | Construtor com obrigatórios | Services, Repos |
| `@Data` | GET + SET + equals + hashCode + toString | DTOs apenas |
| `@Builder` | Builder pattern | DTOs, Objetos complexos |
| `@Slf4j` | Logger via Slf4j | Services, Controllers |

### Uso em Entidades

```java
@Entity
@Table(name = "pessoa")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class Pessoa extends BaseEntity {
    
    @Getter
    @Setter
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Getter
    @Setter
    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;
    
    @Getter
    @Setter
    @Column(length = 14)
    private String cpf;
    
    // Relacionamentos - não usar @Getter/@Setter em cascata
    @OneToMany(mappedBy = "pessoa")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Contato> contatos;
}
```

### Uso em DTOs

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
    private LocalDate dataNascimento;
}
```

### Uso em Services (DI)

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaService extends DefaultSecuredBaseService<Pessoa, PessoaDTO> {
    
    private final PessoaRepository repository;
    private final PessoaMapper mapper;
    
    public void processarAlgo() {
        log.info("Iniciando processamento");
        // ...
    }
}
```

### Uso em Controllers

```java
@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
@Slf4j
public class PessoaController extends ListBaseController<Pessoa, PessoaDTO> {
    
    private final PessoaService service;
    
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> findById(@PathVariable Long id) {
        log.debug("Buscando pessoa com id: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }
}
```

### Uso em Repository

```java
@Repository
@RequiredArgsConstructor
public class PessoaRepository extends BaseEntityRepository<Pessoa> {
    
    private final EntityManager entityManager;
    
    // Métodos customizados...
}
```

### Configuração com MapStruct

Quando Lombok + MapStruct juntos:

```xml
<!-- pom.xml -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </path>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${mapstruct.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### Boas Práticas

| Prática | Recomendação |
|----------|---------------|
| Entidades | Usar `@Getter`/`@Setter` individualmente, não `@Data` |
| DTOs | Usar `@Data` ou `@Getter`/`@Setter` + `@Builder` |
| Services | `@RequiredArgsConstructor` + `@Slf4j` |
| Controllers | `@RequiredArgsConstructor` + `@Slf4j` |
| Evitar | `@Setter` em entidades (use métodos de domínio) |
| equals/hashCode | `callSuper = false` para entidades |

## Consequências

### Positivas

- ✅ Redução significativa de boilerplate
- ✅ Código mais limpo e legível
- ✅ Maior produtividade
- ✅ Menos erros de digitação
- ✅ Integração com Spring, JPA, Jackson, MapStruct

### Negativas

- ❌ Dependência adicional
- ❌ Requer configuração de IDE
- ❌ Debugging pode ser confuso (código gerado)
- ❌ Curva de aprendizado para novos devs

## Status de Implementação

✅ **COMPLETO**

- Lombok em todas as dependências
- Configuração no pom.xml
- Anotações utilizadas em todo o projeto

## Data

2024-01-10

## Revisores

- Team Lead
- Architect

## Referências

1. **Lombok Documentation**
   - URL: https://projectlombok.org/
   - Site oficial

2. **Lombok GitHub Wiki**
   - URL: https://github.com/projectlombok/lombok/wiki
   - Wiki oficial

3. **Baeldung - Lombok Tutorial**
   - URL: https://www.baeldung.com/lombok
   - Guia completo

4. **MapStruct + Lombok Integration**
   - URL: https://mapstruct.org/documentation/stable/reference/html/#_using_mapstruct_with_lombok
   - Integração recomendada

5. **Vlad Mihalcea - Lombok Best Practices**
   - URL: https://vladmihalcea.com/lombok-best-practices/
   - Boas práticas