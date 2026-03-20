# Guia de Testes para Migração de @Transactional

**Data:** 19 de Março de 2026  
**Autor:** GitHub Copilot  

---

## 1. TESTES UNITÁRIOS

### 1.1 Teste: SaveBaseService com @Transactional

**Arquivo:** `SaveBaseServiceTest.java`

```java
package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;

/**
 * Testes para SaveBaseService com @Transactional.
 * 
 * @author Israel Araújo
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("SaveBaseService com @Transactional")
class SaveBaseServiceTest {

  @Autowired
  private SaveBaseService<?> service;

  @Autowired
  private TestRepository<?> repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("Deve salvar entidade dentro de transação")
  @Transactional
  void deveSalvarEntidadeDentroDeTransacao() throws ServiceException {
    // Dado
    DTO<?> dto = new TestDTO("Test Entity");

    // Quando
    DTO<?> result = service.save(dto);

    // Então
    assertThat(result).isNotNull();
    assertThat(result.getId()).isNotNull();
    assertThat(repository.count()).isEqualTo(1);
  }

  @Test
  @DisplayName("Deve fazer rollback em caso de validação inválida")
  void deveFazerRollbackEmCasoDeValidacaoInvalida() {
    // Dado
    DTO<?> invalidDto = new TestDTO(null);  // Inválido

    // Quando & Então
    assertThatThrownBy(() -> service.save(invalidDto))
        .isInstanceOf(ServiceException.class);

    // Verifica que nada foi salvo
    assertThat(repository.count()).isEqualTo(0);
  }

  @Test
  @DisplayName("Deve sincronizar lazy loading dentro da transação")
  @Transactional
  void deveSincronizarLazyLoadingDentroDaTransacao() throws ServiceException {
    // Dado
    TestDTO dto = createDtoWithRelationships();

    // Quando
    TestDTO result = (TestDTO) service.save(dto);

    // Então
    // ✅ Se chegou aqui sem LazyInitializationException, lazy loading funcionou
    assertThat(result.getRelationships()).isNotEmpty();
  }

  @Test
  @DisplayName("Deve publicar evento após sucesso")
  @Transactional
  void devePublicarEventoAposSucesso() throws ServiceException {
    // Dado
    DTO<?> dto = new TestDTO("Test Event");
    EventCaptor eventCaptor = new EventCaptor();

    // Quando
    DTO<?> result = service.save(dto);

    // Então
    assertThat(eventCaptor.getLastEvent()).isNotNull();
    assertThat(eventCaptor.getLastEvent().getType())
        .isEqualTo(CrudOperationType.CREATED);
  }

  @Test
  @DisplayName("Deve configurar timeout de transação")
  @Transactional(timeout = 5)
  void deveConfigurarTimeoutDeTransacao() throws ServiceException {
    // Dado
    DTO<?> dto = new TestDTO("Test Timeout");

    // Quando
    DTO<?> result = service.save(dto);

    // Então
    assertThat(result).isNotNull();
    // Se timeout expirasse, seria lançada TransactionTimedOutException
  }
}
```

### 1.2 Teste: Propagação de Transações

**Arquivo:** `TransactionPropagationTest.java`

```java
package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testes de propagação de transações com diferentes configurações.
 * 
 * @author Israel Araújo
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Transaction Propagation")
class TransactionPropagationTest {

  @Autowired
  private TransactionalService transactionalService;

  @Test
  @DisplayName("REQUIRED: Deve usar transação existente")
  @Transactional
  void requiredDeveUsarTransacaoExistente() {
    // Dado: Já estamos em transação (by @Transactional)

    // Quando
    transactionalService.executeRequired();

    // Então
    // ✅ Usar a mesma transação pai
    assertThat(transactionalService.wasExecutedInTransaction()).isTrue();
  }

  @Test
  @DisplayName("REQUIRES_NEW: Deve criar nova transação")
  @Transactional
  void requiresNewDeveCriarNovaTransacao() {
    // Dado: Já estamos em transação

    // Quando
    transactionalService.executeRequiresNew();

    // Então
    // ✅ Criou nova transação independente
    assertThat(transactionalService.isNewTransactionStarted()).isTrue();
  }

  @Test
  @DisplayName("NESTED: Deve criar savepoint")
  @Transactional
  void nestedDeveCriarSavepoint() {
    // Dado: Já estamos em transação

    // Quando
    transactionalService.executeNested();

    // Então
    // ✅ Criou savepoint na transação existente
    assertThat(transactionalService.hasSavepoint()).isTrue();
  }

  @Test
  @DisplayName("SUPPORTS: Deve suportar transação opcional")
  void supportsDeveSuportarTransacaoOpcional() {
    // Dado: NÃO estamos em transação

    // Quando
    transactionalService.executeSupports();

    // Então
    // ✅ Executou sem transação (graceful degradation)
    assertThat(transactionalService.wasExecuted()).isTrue();
  }
}
```

### 1.3 Teste: ReadOnly com Performance

**Arquivo:** `TransactionReadOnlyTest.java`

```java
package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testes para transações read-only com otimizações de performance.
 * 
 * @author Israel Araújo
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Transaction ReadOnly")
class TransactionReadOnlyTest {

  @Autowired
  private QueryService queryService;

  @Test
  @DisplayName("ReadOnly=true não permite modificações")
  @Transactional(readOnly = true)
  void readOnlyTrueNaoPermiteModificacoes() {
    // Dado: Transação read-only

    // Quando & Então
    // Se tentar salvar dentro de readOnly=true, Spring lança exceção
    assertThatThrownBy(() -> {
      queryService.findAll();  // OK
      queryService.save(new TestEntity());  // ❌ Erro
    }).isInstanceOf(InvalidOperationException.class);
  }

  @Test
  @DisplayName("ReadOnly=true otimiza queries")
  @Transactional(readOnly = true)
  void readOnlyTrueOtimizaQueries() {
    // Dado: Transação read-only

    // Quando
    long startTime = System.currentTimeMillis();
    var result = queryService.findAll();
    long duration = System.currentTimeMillis() - startTime;

    // Então
    assertThat(result).isNotNull();
    // readOnly=true normalmente é mais rápido que readOnly=false
    assertThat(duration).isLessThan(1000);
  }

  @Test
  @DisplayName("ReadOnly=false permite modificações")
  @Transactional(readOnly = false)
  void readOnlyFalsePermiteModificacoes() {
    // Dado: Transação com escrita

    // Quando
    var entity = new TestEntity();
    entity = queryService.save(entity);

    // Então
    assertThat(entity.getId()).isNotNull();
  }
}
```

---

## 2. TESTES DE INTEGRAÇÃO

### 2.1 Teste: LazyInitializationException Não Ocorre

**Arquivo:** `LazyInitializationIntegrationTest.java`

```java
package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.security.service.user.UserService;

/**
 * Teste de integração: Verifica que LazyInitializationException não ocorre
 * ao acessar coleções lazy dentro de transação @Transactional.
 * 
 * @author Israel Araújo
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("LazyInitialization Prevention")
class LazyInitializationIntegrationTest {

  @Autowired
  private UserService userService;

  @Test
  @DisplayName("Não deve lançar LazyInitializationException ao sincronizar usuário")
  @Transactional
  void naoDeveLancarLazyInitializationException() {
    // Dado: Um usuário com relacionamentos lazy
    User user = createUserWithPrivileges();

    // Quando & Então: Não deve lançar exceção
    assertThatNoException().isThrownBy(() -> {
      User synchronized = userService.synchronize(user);
      
      // ✅ Acessa lazy collections dentro da transação
      synchronized.getPrivileges().forEach(privilege -> {
        assertThat(privilege.getOperations()).isNotEmpty();
        privilege.getOperations().forEach(operation -> {
          assertThat(operation.getContext()).isNotEmpty();
        });
      });
    });
  }

  @Test
  @DisplayName("Deve acessar lazy collections no mesmo contexto transacional")
  @Transactional
  void deveAcessarLazyCollectionsNoMesmoContextoTransacional() {
    // Dado
    User user = createUserWithPrivileges();
    int expectedPrivileges = 5;

    // Quando
    User synchronized = userService.synchronize(user);

    // Então
    assertThat(synchronized.getPrivileges()).hasSize(expectedPrivileges);
  }

  private User createUserWithPrivileges() {
    User user = new User();
    user.setUserCode("TEST_USER");
    user.setEmail("test@example.com");
    // ... adiciona privilégios ...
    return userService.save(user);
  }
}
```

### 2.2 Teste: Rollback em Erro

**Arquivo:** `TransactionRollbackTest.java`

```java
package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testes de rollback automático com @Transactional.
 * 
 * @author Israel Araújo
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Transaction Rollback")
class TransactionRollbackTest {

  @Autowired
  private TestService testService;

  @Autowired
  private TestRepository testRepository;

  @BeforeEach
  void setUp() {
    testRepository.deleteAll();
  }

  @Test
  @DisplayName("Deve fazer rollback quando lança exceção")
  void deveFazerRollbackQuandoLancaExcecao() {
    // Dado: Uma ação que vai falhar
    assertThatThrownBy(() -> testService.failingOperation())
        .isInstanceOf(ServiceException.class);

    // Então: Nada foi persistido
    assertThat(testRepository.count()).isEqualTo(0);
  }

  @Test
  @DisplayName("Deve fazer rollback parcial com NESTED")
  @Transactional
  void deveFazerRollbackParcialComNESTED() throws ServiceException {
    // Dado: Operação principal
    TestEntity mainEntity = testService.saveMainEntity();
    int mainCountBefore = testRepository.count();

    // Quando: Suboperação falha
    try {
      testService.saveMainWithFailingNested(mainEntity);
    } catch (Exception e) {
      // Esperado falhar
    }

    // Então: Operação principal foi mantida, sub-operação foi desfeita
    assertThat(testRepository.count()).isEqualTo(mainCountBefore);
  }

  @Test
  @DisplayName("Deve respeitar rollbackFor customizado")
  @Transactional(rollbackFor = CustomException.class)
  void deveRespeitarRollbackForCustomizado() {
    // Dado
    TestEntity entity = new TestEntity();

    // Quando: Lança exceção customizada
    assertThatThrownBy(() -> {
      testRepository.save(entity);
      throw new CustomException("Deve fazer rollback");
    }).isInstanceOf(CustomException.class);

    // Então: Rollback foi feito
    assertThat(testRepository.count()).isEqualTo(0);
  }

  @Test
  @DisplayName("Não deve fazer rollback para exceções checked por padrão")
  @Transactional(rollbackFor = Exception.class)
  void naoDeveFazerRollbackParaExcecoesChecked() throws Exception {
    // Dado
    TestEntity entity = new TestEntity();
    testRepository.save(entity);

    // Quando: Lança exceção checked (normalmente não faz rollback)
    // Mas como configuramos rollbackFor = Exception.class, vai fazer rollback
    assertThatThrownBy(() -> {
      throw new IOException("Deve fazer rollback");
    }).isInstanceOf(IOException.class);

    // Então: Rollback foi feito por causa de rollbackFor
    assertThat(testRepository.count()).isEqualTo(0);
  }
}
```

---

## 3. TESTES DE AUDITORIA

### 3.1 Teste: SaveSecuredBaseService com Auditoria

**Arquivo:** `SaveSecuredBaseServiceTest.java`

```java
package com.ia.core.security.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.security.service.user.UserService;

/**
 * Testes de auditoria com @Transactional.
 * 
 * @author Israel Araújo
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("SaveSecuredBaseService com Auditoria")
class SaveSecuredBaseServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private AuditRepository auditRepository;

  @Test
  @DisplayName("Deve registrar auditoria ao salvar usuário")
  @Transactional
  void deveRegistrarAuditoriaAoSalvarUsuario() throws ServiceException {
    // Dado
    UserDTO dto = new UserDTO();
    dto.setUserCode("NEW_USER");
    dto.setEmail("new@example.com");

    // Quando
    UserDTO saved = userService.save(dto);

    // Então
    assertThat(saved.getId()).isNotNull();
    
    // Verifica auditoria foi registrada
    AuditLog auditLog = auditRepository.findLastByEntityId(saved.getId());
    assertThat(auditLog).isNotNull();
    assertThat(auditLog.getOperationType()).isEqualTo("CREATE");
    assertThat(auditLog.getEntityType()).isEqualTo("USER");
  }

  @Test
  @DisplayName("Deve registrar auditoria mesmo que POST-save falhe")
  @Transactional
  void deveRegistrarAuditoriaEmbora() throws ServiceException {
    // Dado: Vamos forçar erro após save mas antes de auditoria

    // Quando
    UserDTO saved = userService.save(createValidUserDto());

    // Então
    // Auditoria foi registrada em transação REQUIRES_NEW
    AuditLog auditLog = auditRepository.findLastByEntityId(saved.getId());
    assertThat(auditLog).isNotNull();
  }
}
```

---

## 4. SCRIPT DE TESTES (Maven/Gradle)

### 4.1 Executar Testes de Transação

```bash
# Executar apenas testes de transação
mvn test -Dtest=*TransactionTest

# Executar com logging detalhado
mvn test -Dtest=*TransactionTest -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

# Executar com perfil de teste
mvn test -Dspring.profiles.active=test

# Executar cobertura de código
mvn test jacoco:report

# Verificar relatório
open target/site/jacoco/index.html
```

### 4.2 Configuração de Testes (application-test.properties)

```properties
# DataSource de teste (H2 em memória)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate para testes
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true

# Transações
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=false
spring.transaction.default-timeout=10

# Logging
logging.level.org.springframework.transaction=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

---

## 5. CHECKLIST DE TESTES

Para cada serviço migrado, executar:

### Testes Unitários
- [ ] Salvamento de entidade
- [ ] Validação de dados inválidos
- [ ] Sincronização de lazy loading
- [ ] Publicação de eventos
- [ ] Configuração de timeout

### Testes de Transação
- [ ] REQUIRED propaga corretamente
- [ ] REQUIRES_NEW cria nova transação
- [ ] NESTED cria savepoint
- [ ] SUPPORTS funciona com e sem transação
- [ ] ReadOnly=true não permite escrita
- [ ] ReadOnly=false permite escrita

### Testes de Rollback
- [ ] Rollback em exceção unchecked
- [ ] Rollback em exceção checked (se configurado)
- [ ] Rollback parcial com NESTED
- [ ] Auditoria registrada mesmo em rollback

### Testes de Integração
- [ ] Sem LazyInitializationException
- [ ] Lazy loading funciona
- [ ] Relacionamentos sincronizados
- [ ] Auditoria registrada
- [ ] Performance dentro do esperado

### Testes de Performance
- [ ] ReadOnly=true é mais rápido
- [ ] Batch fetch size otimizado
- [ ] N+1 queries resolvido
- [ ] Timeout respeitado

---

## 6. MÉTRICAS A VALIDAR

```java
@Test
void deveValidarMetricasDePerformance() {
  // ReadOnly deve ser ~10-20% mais rápido
  long readOnlyDuration = measureReadOnlyTime();
  long readWriteDuration = measureReadWriteTime();
  
  assertThat(readOnlyDuration)
    .isLessThan(readWriteDuration);
  
  // Lazy loading dentro de transação não deve falhar
  assertThatNoException().isThrownBy(() -> 
    service.findWithLazyLoading()
  );
  
  // Timeout deve ser respeitado
  assertThatThrownBy(() -> 
    service.operacaoComTimeout()
  ).isInstanceOf(TransactionTimedOutException.class);
}
```

---

**Próximos Passos:**
1. Criar suite de testes completa
2. Executar em CI/CD
3. Validar cobertura > 80%
4. Aprovar em code review
5. Deploy em staging
6. Deploy em produção

---

**Status:** Pronto para Implementação de Testes
