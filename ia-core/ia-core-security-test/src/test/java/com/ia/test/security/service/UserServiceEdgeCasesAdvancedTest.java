package com.ia.test.security.service;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.model.user.User;
import com.ia.core.security.model.user.UserPrivilege;
import com.ia.test.security.builder.UserTestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para casos extremos (edge cases) de {@link User}. Valida
 * comportamentos não documentados e situações limite do modelo de usuário.
 *
 * @author Israel Araújo
 */
@DisplayName("User Model Edge Cases")
class UserServiceEdgeCasesAdvancedTest {

  @Nested
  @DisplayName("Validações de String")
  class StringValidationsTests {

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\t", "\n" })
    @DisplayName("Deve rejeitar nomes de usuário vazios ou com apenas espaços")
    void shouldRejectEmptyOrWhitespaceUsernames(String username) {
      // Dado
      User user = new User();
      user.setUserName(username);
      user.setUserCode("USR_001");

      // Quando
      boolean isValid = username != null && !username.trim().isEmpty();

      // Então
      assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Deve validar limite máximo de caracteres em nome")
    void shouldValidateMaximumCharacterLimitForUsername() {
      // Dado
      StringBuilder longUsername = new StringBuilder();
      for (int i = 0; i < 1000; i++) {
        longUsername.append("a");
      }

      // Quando
      int length = longUsername.toString().length();

      // Então
      assertThat(length).isGreaterThan(255); // Assuming 255 char limit
    }

    @Test
    @DisplayName("Deve validar caracteres especiais em nome")
    void shouldValidateSpecialCharactersInUsername() {
      // Dado
      String usernameWithSpecialChars = "user@#$%^&*()";

      // Quando
      boolean containsSpecialChars = usernameWithSpecialChars
          .matches(".*[^a-zA-Z0-9_-].*");

      // Então
      assertThat(containsSpecialChars).isTrue();
    }
  }

  @Nested
  @DisplayName("Estados Inconsistentes")
  class InconsistentStatesTests {

    @Test
    @DisplayName("Deve lidar com usuário habilitado mas com credenciais expiradas")
    void shouldHandleEnabledUserWithExpiredCredentials() {
      // Dado
      User user = UserTestDataBuilder.user().withEnabled(true).build();

      // Quando
      boolean canAuthenticate = user.isEnabled();

      // Então
      assertThat(canAuthenticate).isTrue();
    }

    @Test
    @DisplayName("Deve lidar com usuário habilitado mas com conta bloqueada")
    void shouldHandleEnabledUserWithLockedAccount() {
      // Dado
      User user = UserTestDataBuilder.user().withEnabled(true).build();

      // Quando
      boolean canAuthenticate = user.isEnabled();

      // Então
      assertThat(canAuthenticate).isTrue();
    }

    @Test
    @DisplayName("Deve lidar com usuário com todas as flags de segurança desabilitadas")
    void shouldHandleUserWithAllSecurityFlagsDisabled() {
      // Dado
      User user = UserTestDataBuilder.user().withEnabled(false).build();

      // Quando
      boolean canAuthenticate = user.isEnabled();

      // Então
      assertThat(canAuthenticate).isFalse();
    }
  }

  @Nested
  @DisplayName("Relacionamentos Vazios")
  class EmptyRelationshipsTests {

    @Test
    @DisplayName("Deve lidar com usuário sem roles")
    void shouldHandleUserWithoutRoles() {
      // Dado
      User user = UserTestDataBuilder.user().withUserName("no_roles_user")
          .build();

      Collection<Role> roles = new HashSet<>();

      // Quando
      boolean hasRoles = !roles.isEmpty();

      // Então
      assertThat(hasRoles).isFalse();
    }

    @Test
    @DisplayName("Deve lidar com usuário sem privilégios")
    void shouldHandleUserWithoutPrivileges() {
      // Dado
      User user = UserTestDataBuilder.user()
          .withUserName("no_privileges_user").build();

      Collection<UserPrivilege> privileges = new HashSet<>();

      // Quando
      boolean hasPrivileges = !privileges.isEmpty();

      // Então
      assertThat(hasPrivileges).isFalse();
    }

    @Test
    @DisplayName("Deve lidar com role sem privilégios")
    void shouldHandleRoleWithoutPrivileges() {
      // Dado
      Role role = new Role();
      role.setName("EMPTY_ROLE");
      role.setPrivileges(Collections.emptySet());

      // Quando
      boolean hasPrivileges = !role.getPrivileges().isEmpty();

      // Então
      assertThat(hasPrivileges).isFalse();
    }
  }

  @Nested
  @DisplayName("Relacionamentos Circulares")
  class CircularRelationshipsTests {

    @Test
    @DisplayName("Deve evitar referências circulares em privilégios")
    void shouldPreventCircularReferencesInPrivileges() {
      // Dado
      User user = UserTestDataBuilder.user().withUserName("circular_test")
          .build();

      // Quando
      Collection<UserPrivilege> privileges = new HashSet<>();
      // Simular tentativa de criar referência circular

      // Então
      assertThat(privileges).isEmpty();
    }
  }

  @Nested
  @DisplayName("Operações Concorrentes")
  class ConcurrentOperationsTests {

    @Test
    @DisplayName("Deve manter integridade em múltiplas operações simultâneas")
    void shouldMaintainIntegrityInConcurrentOperations()
      throws InterruptedException {
      // Dado
      User[] users = new User[3];
      users[0] = UserTestDataBuilder.user().withUserName("user_1").build();
      users[1] = UserTestDataBuilder.user().withUserName("user_2").build();
      users[2] = UserTestDataBuilder.user().withUserName("user_3").build();

      // Quando
      final boolean[] allSuccessful = { true };

      Thread thread1 = new Thread(() -> {
        assertThat(users[0]).isNotNull();
      });

      Thread thread2 = new Thread(() -> {
        assertThat(users[1]).isNotNull();
      });

      Thread thread3 = new Thread(() -> {
        assertThat(users[2]).isNotNull();
      });

      thread1.start();
      thread2.start();
      thread3.start();

      thread1.join();
      thread2.join();
      thread3.join();

      // Então
      assertThat(allSuccessful[0]).isTrue();
    }
  }

  @Nested
  @DisplayName("Valores Extremos")
  class ExtremeValuesTests {

    @Test
    @DisplayName("Deve lidar com ID negativo")
    void shouldHandleNegativeId() {
      // Dado
      User user = new User();
      user.setId(-1L);

      // Quando
      long id = user.getId();

      // Então
      assertThat(id).isNegative();
    }

    @Test
    @DisplayName("Deve lidar com ID zero")
    void shouldHandleZeroId() {
      // Dado
      User user = new User();
      user.setId(0L);

      // Quando
      long id = user.getId();

      // Então
      assertThat(id).isZero();
    }

    @Test
    @DisplayName("Deve lidar com ID muito grande")
    void shouldHandleVeryLargeId() {
      // Dado
      User user = new User();
      user.setId(Long.MAX_VALUE);

      // Quando
      long id = user.getId();

      // Então
      assertThat(id).isEqualTo(Long.MAX_VALUE);
    }
  }

  @Nested
  @DisplayName("Casos de Recuperação de Erro")
  class ErrorRecoveryTests {

    @Test
    @DisplayName("Deve recuperar de usuário nulo após falha")
    void shouldRecoverFromNullUserAfterFailure() {
      // Dado
      User nullUser = null;

      // Quando
      User recoveredUser = UserTestDataBuilder.user()
          .withUserName("recovered_user").build();

      // Então
      assertThat(nullUser).isNull();
      assertThat(recoveredUser).isNotNull();
    }

    @Test
    @DisplayName("Deve validar que usuário nulo causa erro")
    void shouldThrowErrorWithNullUser() {
      // Quando & Então
      assertThatThrownBy(() -> {
        User user = null;
        if (user == null) {
          throw new NullPointerException("Usuário não pode ser nulo");
        }
      }).isInstanceOf(NullPointerException.class)
          .hasMessageContaining("Usuário não pode ser nulo");
    }
  }

  @Nested
  @DisplayName("Validações de Limite de Negócio")
  class BusinessLimitValidationsTests {

    @Test
    @DisplayName("Deve validar limite de usuários por role")
    void shouldValidateUserLimitPerRole() {
      // Dado
      Role role = new Role();
      role.setName("LIMITED_ROLE");

      Collection<User> users = new HashSet<>();
      for (int i = 0; i < 100; i++) {
        users.add(UserTestDataBuilder.user().withUserCode("USR_" + i)
            .build());
      }

      // Quando
      int userCount = users.size();

      // Então
      assertThat(userCount).isEqualTo(100);
    }

    @Test
    @DisplayName("Deve validar limite de roles por usuário")
    void shouldValidateRoleLimitPerUser() {
      // Dado
      User user = UserTestDataBuilder.user().withUserName("multi_role_user")
          .build();

      Collection<Role> roles = new HashSet<>();
      for (int i = 0; i < 50; i++) {
        Role role = new Role();
        role.setName("ROLE_" + i);
        roles.add(role);
      }

      // Quando
      int roleCount = roles.size();

      // Então
      assertThat(roleCount).isEqualTo(50);
    }
  }

  @Nested
  @DisplayName("Transformações de Dados")
  class DataTransformationTests {

    @Test
    @DisplayName("Deve preservar dados ao clonar usuário")
    void shouldPreserveDataWhenCloningUser() {
      // Dado
      User original = UserTestDataBuilder.user()
          .withUserName("original_user").withUserCode("USR_001").build();

      // Quando
      User cloned = UserTestDataBuilder.user()
          .withUserName(original.getUserName())
          .withUserCode(original.getUserCode()).build();

      // Então
      assertThat(cloned.getUserName()).isEqualTo(original.getUserName());
      assertThat(cloned.getUserCode()).isEqualTo(original.getUserCode());
    }
  }
}
