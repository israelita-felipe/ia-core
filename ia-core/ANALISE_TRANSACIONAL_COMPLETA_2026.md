# Análise de Transações com @Transactional - Projeto ia-core-apps

**Data de Análise:** 19 de Março de 2026  
**Versão do Spring Boot:** 3.5.5  
**Versão do Java:** 21  
**Autor:** GitHub Copilot  

---

## 1. SITUAÇÃO ATUAL DO PROJETO

### 1.1 Implementação Atual com HasTransaction

O projeto atualmente utiliza uma solução **manual** de gerenciamento de transações através da interface `HasTransaction`. Esta solução foi criada como workaround para problemas com anotações `@Transactional`.

**Arquivo:** `/home/israel/git/ia-core-apps/ia-core/ia-core-service/src/main/java/com/ia/core/service/HasTransaction.java`

#### Características da Implementação Atual:

```java
public interface HasTransaction extends HasTransactionManager {
  default <E> E onTransaction(boolean readOnly, Supplier<E> action) {
    TransactionStatus status = getTransactionManager()
        .getTransaction(createTransactionDefinition(false));
    try {
      E result = action.get();
      if (status.isNewTransaction() && !status.isReadOnly()) {
        getTransactionManager().commit(status);
      }
      return result;
    } catch (Exception e) {
      if (status.isNewTransaction() && !status.isCompleted()) {
        getTransactionManager().rollback(status);
      }
      throw e;
    }
  }

  default TransactionDefinition createTransactionDefinition(boolean readOnly) {
    DefaultTransactionDefinition defaultTransactionDefinition = 
        new DefaultTransactionDefinition();
    defaultTransactionDefinition
        .setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
    defaultTransactionDefinition
        .setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_UNCOMMITTED);
    defaultTransactionDefinition.setReadOnly(readOnly);
    return defaultTransactionDefinition;
  }
}
```

**Propagação Configurada:** `PROPAGATION_REQUIRED` (padrão)  
**Isolamento Configurado:** `ISOLATION_READ_UNCOMMITTED` (baixo isolamento)

---

### 1.2 Uso Atual em SaveBaseService

**Arquivo:** `/home/israel/git/ia-core-apps/ia-core/ia-core-service/src/main/java/com/ia/core/service/SaveBaseService.java`

```java
default D save(D toSave) throws ServiceException {
    beforeSave(toSave);
    ServiceException ex = new ServiceException();
    ServiceExecutionContext<T, D> context = new ServiceExecutionContext<>(toSave);
    D savedEntity = onTransaction(() -> executeInTransaction(context, ex));
    throwIfHasErrors(ex);
    // ...
    return savedEntity;
}

private D executeInTransaction(ServiceExecutionContext<T, D> context, 
                                ServiceException ex) {
    D toSave = context.getToSave();
    try {
      validate(toSave);
      T model = toModel(toSave);
      model = synchronize(model);  // ⚠️ PONTO CRÍTICO
      // ...
      T saved = getRepository().save(model);
      return toDTO(saved);
    } catch (Exception e) {
      ex.add(e);
    }
    return null;
}
```

### 1.3 Exemplos de Uso no Projeto

1. **SaveSecuredBaseService** - Sobrescreve save() com onTransaction()
2. **UserService** - Usa onTransaction() em changePassword() e resetPassword()
3. **PrivilegeService** - Usa onTransaction() em findAll()
4. **SchedulerConfigService** - Usa onTransaction() em múltiplas operações
5. **CoreUserDetailsService** - Usa onTransaction() para carregamento de usuários
6. **DeleteSecuredBaseService** - Usa onTransaction() em operações de exclusão

---

## 2. PROBLEMAS IDENTIFICADOS

### 2.1 Problema Principal: LazyInitializationException

**Causa Raiz:** Associações lazy carregadas fora da transação

**Cenário de Erro:**
```java
// Dentro de onTransaction()
T model = synchronize(model);  // Tenta acessar atributos lazy fora da transação ativa
```

**Exemplo do UserService:**
```java
public User synchronize(User model) throws ServiceException {
    User user = super.synchronize(model);
    user.getPrivileges().forEach(privilege -> {  // ⚠️ LAZY LOADING
      privilege.setUser(user);
      privilege.getOperations().forEach(operation -> {  // ⚠️ LAZY LOADING
        operation.getContext().forEach(context -> {
          context.setPrivilegeOperation(operation);
        });
      });
    });
    return user;
}
```

**Por que ocorre:**
- A transação é criada, a ação é executada
- Quando sai de onTransaction(), a transação é finalizada
- Se houver lazy loading após a ação, ocorre LazyInitializationException

### 2.2 Problema com Nível de Isolamento

**Isolamento Atual:** `ISOLATION_READ_UNCOMMITTED`
- Permite **dirty reads** (leitura de dados não commitados)
- Baixo nível de proteção contra race conditions
- Adequado apenas para cenários muito específicos

**Melhor Prática:**
- Use `ISOLATION_DEFAULT` (deixe o banco decidir)
- Ou `ISOLATION_READ_COMMITTED` para a maioria dos casos
- `ISOLATION_REPEATABLE_READ` para cenários críticos

### 2.3 Falta de Granularidade em Transações

**Problema:**
```java
default D save(D toSave) throws ServiceException {
    beforeSave(toSave);              // Fora da transação?
    // ...
    D savedEntity = onTransaction(() -> executeInTransaction(context, ex));
    // ...
    afterSave(toSave, savedEntity, operationType);  // Fora da transação?
}
```

A anotação `@Transactional` permite **granularidade fino** do que entra e sai da transação.

### 2.4 Ausência de @Transactional

**Descoberta:** `grep -r "@Transactional"` retorna **zero resultados** no projeto

Isso significa que:
1. Não há aproveitamento de otimizações do Spring
2. Sem suporte a aspectos (`@EnableTransactionManagement`)
3. Sem suporte a herança de configuração de transações
4. Sem suporte a propagação automática entre métodos

### 2.5 Propagação Sempre REQUIRED

**Limitação Atual:**
```java
defaultTransactionDefinition
    .setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
```

Todos os métodos sempre fazem a mesma coisa:
- Criam transação se não existe
- Reutilizam se existe

**Falta:**
- `PROPAGATION_REQUIRES_NEW` - Para operações independentes
- `PROPAGATION_NESTED` - Para sub-transações
- `PROPAGATION_SUPPORTS` - Para operações que podem ou não ser transacionais

### 2.6 Readabilidade e Manutenibilidade

**Complexidade Atual:**
```java
// Código atual - Difícil de entender
onTransaction(() -> {
    try {
        getLogOperationService().logBeforeSave(toSave, ...);
        D saved = SaveBaseService.super.save(toSave);
        getLogOperationService().logAfterSave(toSave, saved, ...);
        return saved;
    } catch (Exception e) {
        ex.add(e);
    }
    return null;
});
```

**Com @Transactional - Claro e Declarativo:**
```java
@Transactional
public D save(D toSave) throws ServiceException {
    // Código limpo e semanticamente claro
}
```

---

## 3. ANÁLISE DE IMPACTO: Por Que @Transactional Não Funciona?

### 3.1 Possíveis Causas do Problema Original

1. **Falta de `@EnableTransactionManagement`** 
   - Necessário para ativar AOP de transações
   - Pode estar faltando na configuração do Spring

2. **Métodos Interface vs Implementação**
   - `@Transactional` em interfaces de métodos default não funciona
   - Spring AOP precisa da implementação concreta
   - Solução: Usar `@Transactional` em classes, não em interfaces

3. **Lazy Initialization Outside Session**
   - Mesmo com @Transactional, se houver processamento pós-transação
   - Necessário usar `FetchType.EAGER` ou `@Transactional(readOnly=true)`

4. **Cascata de Transações**
   - Múltiplas camadas de transações não configuradas corretamente
   - Sem `propagation` explícito

### 3.2 Configuração Spring Data JPA

Verificado: **Spring Data JPA** automáticamente:
- Registra `DataSourceTransactionManager`
- Ativa `@EnableTransactionManagement` implicitamente (Spring Boot 3.5.5)
- Configura JPA transaction manager

**Portanto:** O suporte está disponível!

---

## 4. SOLUÇÃO PROPOSTA: Migração para @Transactional

### 4.1 Estratégia Geral

#### Fase 1: Habilitar @Transactional de Forma Segura
- Criar abstract base service com @Transactional
- Configurar readOnly correto para operações de leitura
- Configurar propagation appropriado

#### Fase 2: Substituir Gradualmente
- Manter HasTransaction como fallback
- Converter métodos um a um
- Testar cada mudança

#### Fase 3: Remover HasTransaction
- Após testes completos
- Documentar mudanças

### 4.2 Anotação @Transactional - Configuração Recomendada

```java
// Para operações de LEITURA
@Transactional(readOnly = true)
public D find(Long id) { ... }

// Para operações de ESCRITA (padrão)
@Transactional
public D save(D toSave) { ... }

// Para operações que precisam de nova transação
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void auditOperation(Operation op) { ... }

// Para operações que suportam (mas não requerem) transação
@Transactional(propagation = Propagation.SUPPORTS)
public void optionalLogging() { ... }

// Para operações aninhadas (subtransações)
@Transactional(propagation = Propagation.NESTED)
public void nestedOperation() { ... }
```

### 4.3 Configuração de Isolamento Recomendada

```java
// Padrão (deixar banco decidir)
@Transactional(isolation = Isolation.DEFAULT)

// Leitura Commitada (READ_COMMITTED) - Comum
@Transactional(isolation = Isolation.READ_COMMITTED)

// Leitura Repetível - Quando precisa de consistência
@Transactional(isolation = Isolation.REPEATABLE_READ)

// Serializável - Máxima proteção (mais lento)
@Transactional(isolation = Isolation.SERIALIZABLE)
```

### 4.4 Configuração de Timeout

```java
// 30 segundos de timeout
@Transactional(timeout = 30)
public D slowOperation(D dto) { ... }

// -1 = sem timeout (padrão)
@Transactional(timeout = -1)
public D normalOperation(D dto) { ... }
```

---

## 5. PLANO DE IMPLEMENTAÇÃO

### 5.1 Configuração Inicial

#### Passo 1: Criar Interface Base com @Transactional

**Arquivo a criar:** `TransactionalBaseService.java`

```java
package com.ia.core.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface base que define o comportamento transacional do serviço.
 * Implementa as melhores práticas de transações do Spring.
 *
 * @author Israel Araújo
 */
public interface TransactionalBaseService {

  /**
   * Executa lógica de leitura em transação read-only.
   * <p>
   * Características:
   * - readOnly = true: Otimização do banco para operações de leitura
   * - propagation = REQUIRED: Usa transação existente ou cria nova
   * - isolation = DEFAULT: Deixa banco de dados decidir
   * </p>
   *
   * @param action Ação a ser executada
   * @return Resultado da ação
   * @param <E> Tipo do resultado
   */
  @Transactional(
    readOnly = true,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT
  )
  default <E> E executeReadOnly(java.util.function.Supplier<E> action) {
    return action.get();
  }

  /**
   * Executa lógica de escrita em transação.
   * <p>
   * Características:
   * - readOnly = false: Permite modificações
   * - propagation = REQUIRED: Usa transação existente ou cria nova
   * - isolation = DEFAULT: Deixa banco de dados decidir
   * </p>
   *
   * @param action Ação a ser executada
   * @return Resultado da ação
   * @param <E> Tipo do resultado
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT
  )
  default <E> E executeWrite(java.util.function.Supplier<E> action) {
    return action.get();
  }

  /**
   * Executa lógica de escrita em nova transação.
   * <p>
   * Caractéristicas:
   * - readOnly = false: Permite modificações
   * - propagation = REQUIRES_NEW: Cria nova transação sempre
   * - isolation = DEFAULT: Deixa banco de dados decidir
   * </p>
   * Útil para operações que devem ser independentes (auditoria, logs).
   *
   * @param action Ação a ser executada
   * @return Resultado da ação
   * @param <E> Tipo do resultado
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRES_NEW,
    isolation = Isolation.DEFAULT
  )
  default <E> E executeWriteIndependent(java.util.function.Supplier<E> action) {
    return action.get();
  }

  /**
   * Executa lógica com suporte opcional a transação.
   * <p>
   * Características:
   * - readOnly = true: Sem modificações
   * - propagation = SUPPORTS: Usa transação se existir
   * - isolation = DEFAULT: Deixa banco de dados decidir
   * </p>
   * Útil para logging, métricas que podem rodar com ou sem transação.
   *
   * @param action Ação a ser executada
   * @return Resultado da ação
   * @param <E> Tipo do resultado
   */
  @Transactional(
    readOnly = true,
    propagation = Propagation.SUPPORTS,
    isolation = Isolation.DEFAULT
  )
  default <E> E executeOptional(java.util.function.Supplier<E> action) {
    return action.get();
  }

  /**
   * Executa lógica em subtransação (nested).
   * <p>
   * Características:
   * - readOnly = false: Permite modificações
   * - propagation = NESTED: Cria savepoint na transação existente
   * - isolation = DEFAULT: Deixa banco de dados decidir
   * </p>
   * Útil para operações que podem falhar sem afetar a transação pai.
   *
   * @param action Ação a ser executada
   * @return Resultado da ação
   * @param <E> Tipo do resultado
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.NESTED,
    isolation = Isolation.DEFAULT
  )
  default <E> E executeNested(java.util.function.Supplier<E> action) {
    return action.get();
  }
}
```

#### Passo 2: Criar Classe Base Transacional para SaveBaseService

**Arquivo a modificar:** `SaveBaseService.java`

Substituir:
```java
default D save(D toSave) throws ServiceException {
    beforeSave(toSave);
    ServiceException ex = new ServiceException();
    ServiceExecutionContext<T, D> context = new ServiceExecutionContext<>(toSave);
    D savedEntity = onTransaction(() -> executeInTransaction(context, ex));
    // ...
}
```

Por:
```java
@Override
@Transactional(
  readOnly = false,
  propagation = Propagation.REQUIRED,
  isolation = Isolation.DEFAULT,
  timeout = 30
)
default D save(D toSave) throws ServiceException {
    beforeSave(toSave);
    ServiceException ex = new ServiceException();
    ServiceExecutionContext<T, D> context = new ServiceExecutionContext<>(toSave);
    D savedEntity = executeInTransaction(context, ex);
    throwIfHasErrors(ex);
    if (savedEntity != null) {
      CrudOperationType operationType = determineOperationType(toSave, savedEntity);
      afterSave(toSave, savedEntity, operationType);
    }
    return savedEntity;
}

/**
 * Executa a lógica de transação para salvar a entidade.
 * Este método é executado dentro da transação criada por @Transactional
 */
private D executeInTransaction(ServiceExecutionContext<T, D> context, 
                                ServiceException ex) {
    // ... implementação atual ...
}
```

### 5.2 Migração de Serviços

#### Exemplo 1: SaveSecuredBaseService

**Antes:**
```java
@Override
default D save(D toSave) throws ServiceException {
    ServiceException ex = new ServiceException();
    D savedEntity = onTransaction(() -> {
      try {
        // ... código ...
        return SaveBaseService.super.save(toSave);
      } catch (Exception e) {
        ex.add(e);
      }
      return null;
    });
    checkErrors(ex);
    return savedEntity;
}
```

**Depois:**
```java
@Override
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
default D save(D toSave) throws ServiceException {
    ServiceException ex = new ServiceException();
    try {
      getLogOperationService().logBeforeSave(toSave, getRepository(), getMapper());
      D saved = SaveBaseService.super.save(toSave);
      getLogOperationService().logAfterSave(toSave, saved, getRepository(), getMapper());
      return saved;
    } catch (Exception e) {
      ex.add(e);
      checkErrors(ex);
      return null;
    }
}
```

#### Exemplo 2: UserService

**Antes:**
```java
public void changePassword(UserPasswordChangeDTO change) throws ServiceException {
    ServiceException ex = new ServiceException();
    onTransaction(() -> {
      try {
        // ... código ...
        return save(user);
      } catch (Exception e) {
        ex.add(e);
      }
      return change;
    });
    checkErrors(ex);
}
```

**Depois:**
```java
@Transactional(readOnly = false)
public void changePassword(UserPasswordChangeDTO change) throws ServiceException {
    SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
    searchRequest.getFilters()
        .add(FilterRequestDTO.builder()
            .key("userCode")
            .operator(OperatorDTO.EQUAL)
            .fieldType(FieldType.STRING)
            .value(change.getUserCode())
            .build());
    
    UserDTO user = findAll(searchRequest).get()
        .findFirst()
        .orElseThrow(() -> new UserNotFountException(change.getUserCode()));

    String decryptedOldPassword = UserPasswordEncoder
        .decrypt(change.getOldPassword(), change.getUserCode());
    
    if (getConfig().getPasswordEncoder()
            .matches(decryptedOldPassword, user.getPassword())) {
      user.setPassword(UserPasswordEncoder
          .decrypt(change.getNewPassword(), change.getUserCode()));
      save(user);
    } else {
      throw new InvalidPasswordException(change.getUserCode());
    }
}
```

### 5.3 Configuração Spring

Garantir que está em `application.properties` ou `application.yml`:

```properties
# Habilitar transações (Spring Boot já faz por padrão)
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=false
spring.jpa.properties.hibernate.default_batch_fetch_size=20

# Log de SQL com parâmetros
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.generate_statistics=false

# Timeout padrão de transação
spring.transaction.default-timeout=30
```

### 5.4 Resolução de LazyInitializationException

**Solução 1: Usar FetchType.EAGER (Não recomendado - performance)**
```java
@OneToMany(fetch = FetchType.EAGER)
private Set<Privilege> privileges;
```

**Solução 2: Usar Hibernate.initialize() dentro da transação (Recomendado)**
```java
@Transactional(readOnly = false)
public User synchronize(User model) throws ServiceException {
    // Dentro da transação, safe lazy loading
    Hibernate.initialize(model.getPrivileges());
    model.getPrivileges().forEach(privilege -> {
      privilege.setUser(model);
      Hibernate.initialize(privilege.getOperations());
      privilege.getOperations().forEach(operation -> {
        Hibernate.initialize(operation.getContext());
        operation.getContext().forEach(context -> {
          context.setPrivilegeOperation(operation);
        });
      });
    });
    return model;
}
```

**Solução 3: Usar EntityGraph (Melhor prática)**
```java
@EntityGraph(attributePaths = {"privileges", "privileges.operations", "privileges.operations.context"})
User findById(Long id);
```

**Solução 4: Usar DTOs com queries otimizadas (Arquitetura limpa)**
```java
@Query("""
  SELECT NEW com.ia.core.security.service.model.user.UserDTO(
    u.id, u.userCode, u.email,
    (SELECT COUNT(p) FROM Privilege p WHERE p.user = u)
  )
  FROM User u
  WHERE u.id = :id
""")
UserDTO findByIdOptimized(@Param("id") Long id);
```

---

## 6. PASSOS DA IMPLEMENTAÇÃO

### Fase 1: Setup (1-2 horas)
- [x] Análise completa
- [ ] Criar `TransactionalBaseService`
- [ ] Atualizar dependências se necessário
- [ ] Criar branch de desenvolvimento

### Fase 2: Testes (2-3 horas)
- [ ] Escrever testes para SaveBaseService com @Transactional
- [ ] Escrever testes para operações de leitura/escrita
- [ ] Testar propagação de transações
- [ ] Testar lazy loading dentro de transações

### Fase 3: Migração Gradual (4-6 horas)
- [ ] Atualizar SaveBaseService
- [ ] Atualizar SaveSecuredBaseService
- [ ] Atualizar UserService
- [ ] Atualizar PrivilegeService
- [ ] Atualizar SchedulerConfigService
- [ ] Atualizar CoreUserDetailsService
- [ ] Atualizar DeleteSecuredBaseService

### Fase 4: Validação (2-3 horas)
- [ ] Execução de testes completos
- [ ] Teste de performance
- [ ] Validação com dados reais
- [ ] Code review

### Fase 5: Cleanup (1-2 horas)
- [ ] Remover onTransaction() dos serviços
- [ ] Deprecar HasTransaction (opcional manter como fallback)
- [ ] Documentação final
- [ ] Pull request e merge

---

## 7. COMPARAÇÃO: HasTransaction vs @Transactional

| Aspecto | HasTransaction | @Transactional |
|---------|----------------|-----------------|
| **Sintaxe** | `onTransaction(() -> {...})` | `@Transactional` |
| **Readabilidade** | Baixa (boilerplate) | Alta (declarativa) |
| **Performance** | Manual, sem otimizações | Otimizado pelo Spring |
| **Propagação** | Sempre REQUIRED | Configurável (REQUIRED, REQUIRES_NEW, etc) |
| **Isolamento** | Sempre READ_UNCOMMITTED | Configurável (DEFAULT, READ_COMMITTED, etc) |
| **ReadOnly** | Configurável | Configurável |
| **Timeout** | Não suporta | Configurável |
| **AOP** | Não | Sim |
| **Herança** | Não | Sim |
| **Aspectos** | Não | Sim (logging, validação) |
| **Testabilidade** | Difícil (hard to mock) | Fácil (framework handles) |
| **Lazy Loading** | Precisa ser careful | Automático dentro transação |
| **Debugging** | Difícil | Fácil (stack trace claro) |
| **Cascade** | Manual | Automático |
| **Rollback automático** | Manual | Automático em exceções |

---

## 8. RECOMENDAÇÕES FINAIS

### 8.1 Best Practices para @Transactional

1. **Sempre usar em métodos públicos** (não em privados)
2. **Definir readOnly=true para queries** (otimização)
3. **Usar propagation apropriado** (não apenas REQUIRED)
4. **Configurar timeout** para operações críticas
5. **Não fazer I/O pesado dentro de transações** (HTTP, file system)
6. **Inicializar lazy collections** antes de retornar
7. **Usar @Transactional em nível de serviço** (não em controllers)
8. **Testar propagação de exceções** após migração

### 8.2 Problemas Comuns a Evitar

| Problema | Solução |
|----------|---------|
| LazyInitializationException | Usar Hibernate.initialize() ou FetchType.EAGER |
| Deadlocks em concorrência | Usar isolamento apropriado e timeout |
| Transação não é criada | Usar em método público, não privado |
| Rollback não acontece | Garantir que é exceção unchecked ou configurar rollbackFor |
| Performance ruim | Usar readOnly=true, select específicos, batch fetch |
| Deadlock detector | Aumentar timeout, revisar queries N+1 |

### 8.3 Monitoramento Pós-Migração

```yaml
# Adicionar em application.properties
logging.level.org.springframework.transaction.interceptor=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
spring.jpa.properties.hibernate.generate_statistics=true
```

---

## 9. CONCLUSÃO

A migração de `HasTransaction` para `@Transactional` é **viável e altamente recomendada** pois:

✅ **Mantém a mesma semântica** de transações  
✅ **Melhora readabilidade** do código  
✅ **Aproveita otimizações** do Spring  
✅ **Permite granularidade fina** (propagation, isolation, timeout)  
✅ **Facilita testes** e debugging  
✅ **Segue padrões da indústria** Spring Java  
✅ **Reduz boilerplate code**  
✅ **Resolve LazyInitializationException** naturalmente  

**Tempo estimado:** 8-16 horas para migração completa com testes.

---

## 10. PRÓXIMOS PASSOS

1. Criar branch: `feature/migrate-to-transactional-annotations`
2. Implementar `TransactionalBaseService`
3. Criar suite de testes
4. Migrar serviços um a um
5. Validar com dados reais
6. Code review e merge
7. Deploy em staging
8. Deploy em produção

---

**Documento preparado por:** GitHub Copilot  
**Data:** 19 de Março de 2026  
**Status:** Análise Completa e Pronto para Implementação
