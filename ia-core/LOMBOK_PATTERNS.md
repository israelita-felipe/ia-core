# Padrões Lombok - IA-Core

Este documento define os padrões de uso do Lombok no projeto **IA-Core**.

---

## 🎯 Princípio Geral

| Tipo de Classe | Annotations | Exemplo |
|----------------|-------------|---------|
| **Entidade JPA** | `@Getter`, `@Setter`, `@SuperBuilder`, `@EqualsAndHashCode(callSuper = true)`, `@NoArgsConstructor`, `@AllArgsConstructor` | [`User.java`](ia-core-security-model/src/main/java/com/ia/core/security/model/user/User.java) |
| **DTO** | `@Data`, `@SuperBuilder(toBuilder = true)`, `@NoArgsConstructor`, `@AllArgsConstructor` | [`UserDTO.java`](ia-core-security-service-model/src/main/java/com/ia/core/security/service/model/user/UserDTO.java) |
| **Entidade com relacionamentos** | Mesmo que entidade JPA + `@EqualsAndHashCode(callSuper = true)` | [`Role.java`](ia-core-security-model/src/main/java/com/ia/core/security/model/role/Role.java) |
| **ViewModels** | `@Getter` + campos finais com `@RequiredArgsConstructor` | [`PageViewModel.java`](ia-core-view/src/main/java/com/ia/core/view/components/page/viewModel/PageViewModel.java) |
| **ServiceConfig** | `@Getter` + `@RequiredArgsConstructor` | [`UserServiceConfig.java`](security-core-service/src/main/java/com/ia/core/security/service/user/UserServiceConfig.java) |
| **Records simples** | `@Data`, `@RequiredArgsConstructor` | [`Size.java`](ia-core-view/src/main/java/com/ia/core/view/utils/Size.java) |

---

## 📋 Regras Específicas

### 1. Entidades JPA

**✅ CORRETO:**
```java
@Entity
@Table(name = "user")
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String nome;
    private String email;
}
```

**❌ INCORRETO:**
```java
@Entity
@Table(name = "user")
@Data // ❌ Não usar @Data em entidades JPA
@SuperBuilder
public class User extends BaseEntity {
    private String nome;
}
```

**Por quê?**
- `@Data` gera `equals()` e `hashCode()` que podem causar problemas com entidades JPA
- Entidades precisam de controle explícito sobre `equals/hashCode`
- `@EqualsAndHashCode(callSuper = true)` garante que a entidade pai seja considerada

### 2. DTOs

**✅ CORRETO:**
```java
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
}
```

**Por quê?**
- DTOs não são entidades JPA, então `@Data` é seguro
- `toBuilder = true` permite criar cópias modificadas facilmente
- Úteis para testes e transformações

### 3. Classes com Campos Finais

**✅ CORRETO:**
```java
@Getter
@RequiredArgsConstructor
public class Size {
    private final int width;
    private final int height;
}
```

**Por quê?**
- `@RequiredArgsConstructor` gera construtor com todos os campos `final`
- Não precisa de `@NoArgsConstructor` quando todos os campos são `final`

### 4. ViewModels

**✅ CORRETO:**
```java
@Getter
public class PageViewModel<T> {
    private IListViewModel<T> listViewModel;
    private IFormEditorViewModel<T> editorViewModel;
    private boolean readOnly = false;
    
    public PageViewModel(PageViewModelConfig<T> config) {
        this.listViewModel = config.createListViewModel();
        this.editorViewModel = config.createEditorViewModel();
    }
}
```

**Por quê?**
- ViewModels frequentemente precisam de inicialização customizada
- `@Getter` apenas, sem `@Setter` para campos que não devem ser modificados externamente

### 5. ServiceConfigs

**✅ CORRETO:**
```java
@Component
@Getter
@RequiredArgsConstructor
public class PessoaServiceConfig 
    extends DefaultSecuredBaseServiceConfig<Pessoa, PessoaDTO> {
    
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
}
```

**Por quê?**
- ServiceConfigs são classes de configuração com dependências injetadas
- `@RequiredArgsConstructor` gera construtor para campos `final`

---

## 📝 Checklist de Correções

### Arquivos que precisam de correção

| Arquivo | Problema | Correção |
|---------|----------|----------|
| `AbstractBaseEntityDTO.java` | Usa `@Data` em DTO que deveria usar `@Getter/@Setter` | Verificar se é realmente DTO ou entidade |
| `Size.java` | ✅ OK | Padrão correto |

### Padrões já corretos (não modificar)

✅ **Entidades JPA:**
- [`BaseEntity.java`](ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java)
- [`User.java`](ia-core-security-model/src/main/java/com/ia/core/security/model/user/User.java)
- [`Role.java`](ia-core-security-model/src/main/java/com/ia/core/security/model/role/Role.java)

✅ **DTOs:**
- [`UserDTO.java`](ia-core-security-service-model/src/main/java/com/ia/core/security/service/model/user/UserDTO.java)
- [`RoleDTO.java`](ia-core-security-service-model/src/main/java/com/ia/core/security/service/model/role/RoleDTO.java)

✅ **ServiceConfigs:**
- [`UserServiceConfig.java`](security-core-service/src/main/java/com/ia/core/security/service/user/UserServiceConfig.java)
- [`RoleServiceConfig.java`](security-core-service/src/main/java/com/ia/core/security/service/role/RoleServiceConfig.java)

---

## 🧪 Verificação

Para verificar se os padrões estão sendo seguidos:

```bash
# Verificar arquivos usando @Data em entidades (problema potencial)
grep -r "@Data" --include="*.java" ia-core/*/src/main/java | grep -E "Entity|@Table"

# Verificar arquivos que usam @Getter/@Setter em DTOs (pode ser aceitável)
grep -r "@Getter" --include="*.java" ia-core/*/service-model/src/main/java
```

---

## 📚 Referências

- [Lombok Documentation](https://projectlombok.org/)
- [Stack Overflow: @Data for JPA Entities](https://stackoverflow.com/questions/39239174)
- [Baeldung: Lombok with JPA](https://www.baeldung.com/lombok-jpa)
