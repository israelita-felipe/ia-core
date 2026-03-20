# Exemplos Práticos de Migração: HasTransaction → @Transactional

**Documento de Referência para Implementação**  
**Data:** 19 de Março de 2026  
**Autor:** GitHub Copilot  

---

## 1. EXEMPLO 1: SaveBaseService - Migração Básica

### 1.1 Implementação ATUAL (com HasTransaction)

```java
package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;

/**
 * Interface que salva um {@link BaseEntity}
 */
public interface SaveBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D>, HasTransaction {

  /**
   * IMPLEMENTAÇÃO ATUAL - COM onTransaction()
   * ❌ Problema: Boilerplate, difícil de ler
   */
  @Override
  default D save(D toSave) throws ServiceException {
    beforeSave(toSave);
    ServiceException ex = new ServiceException();
    ServiceExecutionContext<T, D> context = new ServiceExecutionContext<>(toSave);
    
    // ⚠️ Wrapper com onTransaction
    D savedEntity = onTransaction(() -> executeInTransaction(context, ex));
    
    throwIfHasErrors(ex);
    if (savedEntity != null) {
      CrudOperationType operationType = determineOperationType(toSave, savedEntity);
      afterSave(toSave, savedEntity, operationType);
    }
    return savedEntity;
  }

  private D executeInTransaction(ServiceExecutionContext<T, D> context,
                                  ServiceException ex) {
    D toSave = context.getToSave();
    try {
      validate(toSave);
      T model = toModel(toSave);
      model = synchronize(model);
      T saved = getRepository().save(model);
      D dto = toDTO(saved);
      context.setSaved(dto);
      return dto;
    } catch (Exception e) {
      ex.add(e);
    }
    return null;
  }

  // ... outros métodos
}
```

### 1.2 Implementação NOVA (com @Transactional)

```java
package com.ia.core.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Interface que salva um {@link BaseEntity}.
 * <p>
 * Implementação refatorada para utilizar @Transactional do Spring,
 * removendo a necessidade de {@link HasTransaction}.
 * </p>
 *
 * @param <T> Tipo da entidade
 * @param <D> Tipo do DTO
 * @author Israel Araújo
 */
@Slf4j
public interface SaveBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D> {

  /**
   * Salva uma entidade em transação.
   * <p>
   * <b>Configuração Transacional:</b>
   * - readOnly = false: Permite modificações no banco
   * - propagation = REQUIRED: Usa transação existente ou cria nova
   * - isolation = DEFAULT: Deixa banco de dados decidir
   * - timeout = 30 segundos: Proteção contra operações muito longas
   * </p>
   * <p>
   * <b>Fluxo:</b>
   * </p>
   * <ol>
   *   <li>Valida dados da entrada</li>
   *   <li>Sincroniza modelo (lazy loading dentro da transação) ✅</li>
   *   <li>Persiste no banco</li>
   *   <li>Converte para DTO</li>
   *   <li>Publica evento de domínio</li>
   * </ol>
   *
   * @param toSave DTO a ser salvo
   * @return DTO salvo com ID gerado
   * @throws ServiceException em caso de erro
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    timeout = 30
  )
  default D save(D toSave) throws ServiceException {
    log.debug("Iniciando salvamento de: {}", toSave.getClass().getSimpleName());
    
    // Executado ANTES da transação
    beforeSave(toSave);
    
    ServiceException ex = new ServiceException();
    
    try {
      // ✅ DENTRO DA TRANSAÇÃO - seguro fazer lazy loading
      validate(toSave);
      T model = toModel(toSave);
      
      // ✅ Lazy loading agora é seguro (estamos na transação)
      model = synchronize(model);
      
      // ✅ Persistência
      T saved = getRepository().save(model);
      D dto = toDTO(saved);
      
      log.debug("Salvamento concluído com ID: {}", dto.getId());
      return dto;
      
    } catch (Exception e) {
      log.error("Erro ao salvar entidade", e);
      ex.add(e);
      throw ex;  // ✅ Spring faz rollback automaticamente
    }
  }

  /**
   * Validação pré-transacional.
   * Executado ANTES de iniciar a transação.
   *
   * @param toSave DTO a ser validado
   * @throws ServiceException se validação falhar
   */
  default void beforeSave(D toSave) throws ServiceException {
    // Implementação padrão: sem validação
  }

  /**
   * Valida a entidade antes de persistir.
   * Executado DENTRO da transação.
   *
   * @param toSave DTO a validar
   * @throws ServiceException se inválido
   */
  default void validate(D toSave) throws ServiceException {
    // Executar validadores registrados
    getValidators().forEach(v -> v.validate(toSave));
  }

  /**
   * Converte DTO para modelo de entidade.
   *
   * @param dto DTO de entrada
   * @return Entidade mapeada
   */
  default T toModel(D dto) {
    return getMapper().toModel(dto);
  }

  /**
   * Sincroniza modelo com relacionamentos.
   * ✅ Executado DENTRO da transação - lazy loading é seguro aqui!
   *
   * @param model Entidade carregada
   * @return Entidade sincronizada
   */
  default T synchronize(T model) {
    // Implementação padrão: retorna modelo sem modificações
    // Subclasses podem sobrescrever para fazer lazy loading
    return model;
  }

  /**
   * Converte modelo persistido para DTO.
   *
   * @param saved Entidade persistida
   * @return DTO resultado
   */
  default D toDTO(T saved) {
    return getMapper().toDTO(saved);
  }

  /**
   * Executado APÓS a transação ser commitada com sucesso.
   *
   * @param original DTO original da requisição
   * @param saved DTO persistido
   * @param operationType Tipo de operação (CREATED ou UPDATED)
   * @throws ServiceException em caso de erro
   */
  default void afterSave(D original, D saved, CrudOperationType operationType)
    throws ServiceException {
    log.debug("Publicando evento de: {}", operationType);
    publishEvent(saved, operationType);
  }

  /**
   * Determina se é criação ou atualização.
   *
   * @param original DTO original
   * @param saved DTO persistido
   * @return Tipo da operação
   */
  private CrudOperationType determineOperationType(D original, D saved) {
    return original.getId() == null 
      ? CrudOperationType.CREATED 
      : CrudOperationType.UPDATED;
  }
}
```

### 1.3 Explicação das Mudanças

| Aspecto | Antes | Depois | Benefício |
|---------|-------|--------|-----------|
| **Sintaxe** | `onTransaction(() -> {...})` | `@Transactional` | Declarativo e claro |
| **Lazy Loading** | Fora da transação ❌ | Dentro da transação ✅ | Sem LazyInitializationException |
| **Rollback** | Manual | Automático | Menos código |
| **Readabilidade** | Baixa | Alta | Fácil entender fluxo |
| **Timeout** | Não suporta | 30 segundos | Proteção contra travamentos |
| **Propagação** | Fixa (REQUIRED) | Configurável | Flexibilidade |

---

## 2. EXEMPLO 2: SaveSecuredBaseService - Com Auditoria

### 2.1 Implementação ATUAL (com HasTransaction)

```java
package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.SaveBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;

public interface SaveSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, SaveBaseService<T, D> {

  @Override
  default D save(D toSave) throws ServiceException {
    ServiceException ex = new ServiceException();
    
    // ❌ Tudo dentro de onTransaction
    D savedEntity = onTransaction(() -> {
      try {
        getLogOperationService().logBeforeSave(toSave, getRepository(),
                                               getMapper());
        D saved = SaveBaseService.super.save(toSave);
        getLogOperationService().logAfterSave(toSave, saved,
                                              getRepository(), getMapper());
        return saved;
      } catch (Exception e) {
        ex.add(e);
      }
      return null;
    });
    
    checkErrors(ex);
    return savedEntity;
  }
}
```

### 2.2 Implementação NOVA (com @Transactional)

```java
package com.ia.core.security.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.SaveBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Interface para salvar entidades com auditoria.
 * <p>
 * Implementação que combina {@link SaveBaseService} com registro de auditoria
 * usando {@link LogOperationService}.
 * </p>
 *
 * @param <T> Tipo da entidade
 * @param <D> Tipo do DTO
 * @author Israel Araújo
 */
@Slf4j
public interface SaveSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, SaveBaseService<T, D> {

  /**
   * Salva entidade com log de auditoria.
   * <p>
   * <b>Fluxo:</b>
   * </p>
   * <ol>
   *   <li>Registra auditoria PRÉ-SAVE (antes de persistir)</li>
   *   <li>Valida permissões de segurança</li>
   *   <li>Executa save do {@link SaveBaseService}</li>
   *   <li>Registra auditoria PÓS-SAVE (após persistência)</li>
   * </ol>
   * <p>
   * <b>Garantias Transacionais:</b>
   * </p>
   * <ul>
   *   <li>Tudo ocorre em uma única transação (atomicidade)</li>
   *   <li>Se falhar em qualquer ponto, tudo é revertido</li>
   *   <li>Auditoria é persistida apenas se save for bem-sucedido</li>
   * </ul>
   *
   * @param toSave DTO a ser salvo com auditoria
   * @return DTO salvo e auditado
   * @throws ServiceException em caso de erro
   */
  @Override
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    timeout = 30
  )
  default D save(D toSave) throws ServiceException {
    log.debug("Salvando com auditoria: {}", toSave.getClass().getSimpleName());
    
    ServiceException ex = new ServiceException();
    
    try {
      // ✅ Valida permissões ANTES de persistir
      if (!canCreate(toSave) && !canUpdate(toSave)) {
        throw new SecurityException("Sem permissão para salvar esta entidade");
      }
      
      // ✅ Log de pré-operação (dentro da transação)
      getLogOperationService().logBeforeSave(toSave, getRepository(), getMapper());
      
      // ✅ Chama save da classe pai (também em transação)
      D saved = SaveBaseService.super.save(toSave);
      
      // ✅ Log de pós-operação (dentro da mesma transação)
      getLogOperationService().logAfterSave(toSave, saved, getRepository(), 
                                            getMapper());
      
      log.debug("Salvamento com auditoria concluído: {}", saved.getId());
      return saved;
      
    } catch (Exception e) {
      log.error("Erro ao salvar com auditoria", e);
      ex.add(e);
      throw ex;  // ✅ Rollback automático incluindo auditoria
    }
  }

  /**
   * Verifica se usuário pode criar a entidade.
   *
   * @param toSave DTO a validar
   * @return true se pode criar
   */
  default boolean canCreate(D toSave) {
    return getAuthorizationManager().canCreate(this, toSave);
  }

  /**
   * Verifica se usuário pode atualizar a entidade.
   *
   * @param toSave DTO a validar
   * @return true se pode atualizar
   */
  default boolean canUpdate(D toSave) {
    return getAuthorizationManager().canUpdate(this, toSave);
  }
}
```

---

## 3. EXEMPLO 3: UserService - Com Lazy Loading Seguro

### 3.1 Implementação ATUAL (com HasTransaction)

```java
package com.ia.core.security.service.user;

import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService extends DefaultSecuredBaseService<User, UserDTO>
  implements UserUseCase {

  public void changePassword(UserPasswordChangeDTO change)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    
    // ❌ Tudo em onTransaction
    onTransaction(() -> {
      try {
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
            .orElseThrow(() -> new UserNotFountException(
                change.getUserCode()));

        String decryptedOldPassword = UserPasswordEncoder
            .decrypt(change.getOldPassword(), change.getUserCode());
        
        if (getConfig().getPasswordEncoder()
                .matches(decryptedOldPassword, user.getPassword())) {
          user.setPassword(UserPasswordEncoder
              .decrypt(change.getNewPassword(), change.getUserCode()));
          return save(user);
        } else {
          throw new InvalidPasswordException(change.getUserCode());
        }
      } catch (Exception e) {
        ex.add(e);
      }
      return change;
    });
    
    checkErrors(ex);
  }

  // ❌ Problema: Lazy loading pode falhar fora da transação
  @Override
  public User synchronize(User model) throws ServiceException {
    User user = super.synchronize(model);
    user.getPrivileges().forEach(privilege -> {  // ⚠️ PODE FALHAR
      privilege.setUser(user);
      privilege.getOperations().forEach(operation -> {  // ⚠️ PODE FALHAR
        operation.getContext().forEach(context -> {
          context.setPrivilegeOperation(operation);
        });
      });
    });
    return user;
  }
}
```

### 3.2 Implementação NOVA (com @Transactional)

```java
package com.ia.core.security.service.user;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.model.user.UserUseCase;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.model.filter.FieldType;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço de usuários com gerenciamento seguro de transações.
 * <p>
 * Implementação refatorada para usar @Transactional,
 * com lazy loading seguro e granularidade de operações.
 * </p>
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class UserService 
  extends DefaultSecuredBaseService<User, UserDTO>
  implements UserUseCase {

  public UserService(UserServiceConfig config) {
    super(config);
  }

  /**
   * Altera a senha do usuário.
   * <p>
   * <b>Fluxo Transacional:</b>
   * </p>
   * <ol>
   *   <li>Busca usuário no banco</li>
   *   <li>Valida senha anterior</li>
   *   <li>Criptografa nova senha</li>
   *   <li>Persiste mudança</li>
   * </ol>
   * <p>
   * Se qualquer etapa falhar, toda a transação é desfeita.
   * </p>
   *
   * @param change Dados de mudança de senha
   * @throws ServiceException se erro ao alterar
   * @throws UserNotFountException se usuário não encontrado
   * @throws InvalidPasswordException se senha anterior incorreta
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    timeout = 30
  )
  public void changePassword(UserPasswordChangeDTO change)
    throws ServiceException {
    
    log.debug("Alterando senha do usuário: {}", change.getUserCode());
    
    try {
      // ✅ Busca usuário em transação segura
      SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
      searchRequest.getFilters()
          .add(FilterRequestDTO.builder()
              .key("userCode")
              .operator(OperatorDTO.EQUAL)
              .fieldType(FieldType.STRING)
              .value(change.getUserCode())
              .build());
      
      UserDTO user = findAll(searchRequest)
          .orElseThrow(() -> new UserNotFountException(change.getUserCode()))
          .findFirst()
          .orElseThrow(() -> new UserNotFountException(change.getUserCode()));

      // ✅ Valida senha anterior
      String decryptedOldPassword = UserPasswordEncoder
          .decrypt(change.getOldPassword(), change.getUserCode());
      
      if (!getConfig().getPasswordEncoder()
              .matches(decryptedOldPassword, user.getPassword())) {
        throw new InvalidPasswordException(change.getUserCode());
      }

      // ✅ Define nova senha
      user.setPassword(UserPasswordEncoder
          .decrypt(change.getNewPassword(), change.getUserCode()));
      
      // ✅ Persiste em mesma transação
      save(user);
      
      log.info("Senha alterada com sucesso para: {}", change.getUserCode());
      
    } catch (UserNotFountException | InvalidPasswordException e) {
      throw e;  // Re-lança exceções esperadas
    } catch (Exception e) {
      log.error("Erro ao alterar senha", e);
      throw new ServiceException("Erro ao alterar senha", e);
    }
  }

  /**
   * Reseta a senha do usuário para uma nova senha aleatória.
   * <p>
   * <b>Características:</b>
   * </p>
   * <ul>
   *   <li>Gera senha aleatória segura</li>
   *   <li>Persiste no banco em transação</li>
   *   <li>Log da operação para auditoria</li>
   * </ul>
   *
   * @param reset Dados do reset
   * @throws ServiceException se erro
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    timeout = 30
  )
  public void resetPassword(UserPasswordResetDTO reset)
    throws ServiceException {
    
    log.debug("Resetando senha do usuário: {}", reset.getUserCode());
    
    try {
      // Gera nova senha aleatória segura
      String newPassword = UserPasswordEncoder
          .generateDefaultSecureRandomPassword();
      
      log.debug("Nova senha gerada para: {}", reset.getUserCode());

      // Busca usuário
      SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
      searchRequest.getFilters()
          .add(FilterRequestDTO.builder()
              .key("userCode")
              .operator(OperatorDTO.EQUAL)
              .fieldType(FieldType.STRING)
              .value(reset.getUserCode())
              .build());
      
      UserDTO user = findAll(searchRequest)
          .orElseThrow(() -> new UserNotFountException(reset.getUserCode()))
          .findFirst()
          .orElseThrow(() -> new UserNotFountException(reset.getUserCode()));

      // Define nova senha (já criptografada)
      user.setPassword(getConfig().getPasswordEncoder().encode(newPassword));
      
      // Persiste
      save(user);
      
      log.info("Senha resetada para: {}", reset.getUserCode());
      
    } catch (Exception e) {
      log.error("Erro ao resetar senha", e);
      throw new ServiceException("Erro ao resetar senha", e);
    }
  }

  /**
   * Sincroniza modelo com lazy loading seguro.
   * <p>
   * ✅ Executado DENTRO de transação - lazy loading é 100% seguro!
   * </p>
   * <p>
   * <b>Operações:</b>
   * </p>
   * <ul>
   *   <li>Inicializa coleção de privilégios (com Hibernate.initialize)</li>
   *   <li>Inicializa operações de cada privilégio</li>
   *   <li>Inicializa contextos de cada operação</li>
   *   <li>Sincroniza relacionamentos bidirecionais</li>
   * </ul>
   *
   * @param model Usuário a sincronizar
   * @return Usuário sincronizado com todos os relacionamentos carregados
   * @throws ServiceException se erro
   */
  @Override
  public User synchronize(User model) throws ServiceException {
    User user = super.synchronize(model);
    
    // ✅ Dentro da transação - SEGURO fazer lazy loading
    Hibernate.initialize(user.getPrivileges());
    
    user.getPrivileges().forEach(privilege -> {
      privilege.setUser(user);
      
      // ✅ Inicializa operações
      Hibernate.initialize(privilege.getOperations());
      
      privilege.getOperations().forEach(operation -> {
        // ✅ Inicializa contextos
        Hibernate.initialize(operation.getContext());
        
        operation.getContext().forEach(context -> {
          context.setPrivilegeOperation(operation);
        });
      });
    });
    
    return user;
  }
}
```

---

## 4. EXEMPLO 4: DeleteSecuredBaseService - Com REQUIRES_NEW

### 4.1 Implementação NOVA (com @Transactional)

```java
package com.ia.core.security.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.DeleteBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Interface para deletar entidades com auditoria independente.
 * <p>
 * Implementação que usa REQUIRES_NEW para auditoria, garantindo
 * que o delete suceda mesmo se auditoria falhar.
 * </p>
 *
 * @param <T> Tipo da entidade
 * @param <D> Tipo do DTO
 * @author Israel Araújo
 */
@Slf4j
public interface DeleteSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, DeleteBaseService<T, D> {

  /**
   * Deleta entidade com auditoria.
   * <p>
   * <b>Características Transacionais:</b>
   * </p>
   * <ul>
   *   <li>Delete usa transação REQUIRED (padrão)</li>
   *   <li>Auditoria usa transação REQUIRES_NEW (independente)</li>
   *   <li>Se auditoria falhar, delete é mantido</li>
   *   <li>Se delete falhar, nada é persistido</li>
   * </ul>
   * <p>
   * <b>Fluxo:</b>
   * </p>
   * <ol>
   *   <li>Busca entidade a deletar</li>
   *   <li>Valida permissões</li>
   *   <li>Deleta do banco</li>
   *   <li>Registra auditoria (em nova transação)</li>
   * </ol>
   *
   * @param id ID da entidade a deletar
   * @return DTO da entidade deletada
   * @throws ServiceException se erro
   */
  @Override
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    timeout = 30
  )
  default D delete(Long id) throws ServiceException {
    log.debug("Deletando entidade com ID: {}", id);
    
    try {
      // Busca entidade
      D dto = find(id);
      
      if (dto == null) {
        throw new ServiceException("Entidade não encontrada com ID: " + id);
      }
      
      // Valida permissão
      if (!canDelete(dto)) {
        throw new SecurityException("Sem permissão para deletar esta entidade");
      }
      
      // Deleta
      DeleteBaseService.super.delete(id);
      
      // ✅ Registra auditoria em transação INDEPENDENTE
      registrarAuditoriaDelete(id, dto);
      
      log.info("Entidade deletada com sucesso: {}", id);
      return dto;
      
    } catch (Exception e) {
      log.error("Erro ao deletar entidade", e);
      throw new ServiceException("Erro ao deletar entidade", e);
    }
  }

  /**
   * Registra auditoria de deleção em transação independente.
   * <p>
   * <b>IMPORTANTE:</b> Este método usa {@link Propagation#REQUIRES_NEW}
   * para garantir que a auditoria é registrada mesmo que falhe.
   * Se a auditoria falhar, o delete é mantido.
   * </p>
   *
   * @param id ID da entidade deletada
   * @param deleted DTO da entidade deletada
   */
  @Transactional(
    readOnly = false,
    propagation = Propagation.REQUIRES_NEW,
    isolation = Isolation.DEFAULT,
    timeout = 10
  )
  default void registrarAuditoriaDelete(Long id, D deleted) {
    try {
      log.debug("Registrando auditoria de deleção: {}", id);
      getLogOperationService().logDelete(id, deleted, getRepository(), getMapper());
      log.debug("Auditoria de deleção registrada com sucesso");
    } catch (Exception e) {
      // ⚠️ Log mas não re-lança exceção
      // Delete foi bem-sucedido, auditoria falhou não é motivo para reverter
      log.warn("Falha ao registrar auditoria de deleção", e);
    }
  }

  /**
   * Verifica se usuário pode deletar a entidade.
   *
   * @param dto DTO a validar
   * @return true se pode deletar
   */
  default boolean canDelete(D dto) {
    return getAuthorizationManager().canDelete(this, dto);
  }
}
```

---

## 5. EXEMPLO 5: Query Service - Com readOnly=true

### 5.1 Implementação NOVA (com @Transactional readOnly)

```java
package com.ia.core.security.service.privilege;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeUseCase;
import com.ia.core.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço de privilégios com queries otimizadas.
 * <p>
 * Implementação que usa @Transactional com readOnly=true
 * para operações de leitura, permitindo otimizações do banco.
 * </p>
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class PrivilegeService
  extends DefaultSecuredBaseService<Privilege, PrivilegeDTO>
  implements PrivilegeUseCase {

  /**
   * Busca todos os privilégios.
   * <p>
   * <b>Otimizações com readOnly=true:</b>
   * </p>
   * <ul>
   *   <li>Banco de dados não aloca log de transação</li>
   *   <li>Não há locks pessimistas desnecessários</li>
   *   <li>Hibernate pode usar cache agressivamente</li>
   *   <li>Performance melhorada em queries pesadas</li>
   * </ul>
   *
   * @return Stream de privilégios
   * @throws ServiceException se erro
   */
  @Transactional(
    readOnly = true,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT
  )
  public java.util.stream.Stream<PrivilegeDTO> findAllPrivileges()
    throws ServiceException {
    
    log.debug("Buscando todos os privilégios");
    
    try {
      // ✅ readOnly=true permite que o banco otimize
      return getRepository().findAll()
          .stream()
          .map(this::toDTO);
      
    } catch (Exception e) {
      log.error("Erro ao buscar privilégios", e);
      throw new ServiceException("Erro ao buscar privilégios", e);
    }
  }

  /**
   * Busca privilégio por ID.
   *
   * @param id ID do privilégio
   * @return DTO do privilégio
   * @throws ServiceException se erro
   */
  @Transactional(
    readOnly = true,
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT
  )
  public PrivilegeDTO findPrivilegeById(Long id) throws ServiceException {
    log.debug("Buscando privilégio: {}", id);
    
    try {
      return getRepository().findById(id)
          .map(this::toDTO)
          .orElseThrow(() -> new ServiceException(
              "Privilégio não encontrado: " + id));
      
    } catch (Exception e) {
      log.error("Erro ao buscar privilégio", e);
      throw new ServiceException("Erro ao buscar privilégio", e);
    }
  }

  /**
   * Helper para converter modelo para DTO.
   *
   * @param model Modelo
   * @return DTO
   */
  private PrivilegeDTO toDTO(Privilege model) {
    return getMapper().toDTO(model);
  }
}
```

---

## 6. CHECKLIST DE MIGRAÇÃO

### Para cada serviço que usa onTransaction():

- [ ] Remover import de HasTransaction
- [ ] Adicionar `@Transactional` com configuração apropriada
- [ ] Configurar readOnly=true para leitura
- [ ] Configurar readOnly=false para escrita
- [ ] Definir timeout=30 para operações críticas
- [ ] Usar propagation apropriado:
  - [ ] REQUIRED para operações normais
  - [ ] REQUIRES_NEW para auditoria independente
  - [ ] NESTED para operações que podem falhar parcialmente
  - [ ] SUPPORTS para operações opcionais
- [ ] Adicionar Hibernate.initialize() para lazy loading
- [ ] Adicionar logs com @Slf4j
- [ ] Remover bloco try-catch manual (Spring trata)
- [ ] Re-lançar exceções apropriadas
- [ ] Adicionar javadoc completo
- [ ] Escrever testes de transação
- [ ] Executar testes antes de fazer commit

---

## 7. CONCLUSÃO

A migração de `onTransaction()` para `@Transactional` é:

✅ **Mais legível** - Código declarativo  
✅ **Mais eficiente** - Otimizações do Spring  
✅ **Mais seguro** - Lazy loading automático  
✅ **Mais flexível** - Propagação configurável  
✅ **Mais testável** - Framework handles transações  

Tempo estimado: 2-3 horas por serviço com testes.

---

**Documento preparado por:** GitHub Copilot  
**Status:** Pronto para Implementação
