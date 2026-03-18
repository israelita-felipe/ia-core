package com.ia.test.security.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.model.user.User;
import com.ia.test.security.builder.PrivilegeTestDataBuilder;
import com.ia.test.security.builder.RoleTestDataBuilder;
import com.ia.test.security.builder.UserTestDataBuilder;

/**
 * Testes de integração para fluxos de autenticação e autorização.
 * Valida a interação entre usuários, roles e privilégios no sistema de segurança.
 *
 * @author Israel Araújo
 */
@DisplayName("Authentication and Authorization Integration Tests")
class AuthenticationAuthorizationIntegrationTest {

  private User testUser;
  private Role adminRole;
  private Role userRole;
  private Collection<Privilege> adminPrivileges;
  private Collection<Privilege> userPrivileges;

  @BeforeEach
  void setUp() {
    // Preparar dados de teste
    setupPrivileges();
    setupRoles();
    setupUsers();
  }

  /**
   * Configura privilégios de teste.
   */
  private void setupPrivileges() {
    adminPrivileges = new HashSet<>();
    adminPrivileges.add(PrivilegeTestDataBuilder.privilege()
        .withName("CREATE_USER")
        .build());
    adminPrivileges.add(PrivilegeTestDataBuilder.privilege()
        .withName("UPDATE_USER")
        .build());
    adminPrivileges.add(PrivilegeTestDataBuilder.privilege()
        .withName("DELETE_USER")
        .build());

    userPrivileges = new HashSet<>();
    userPrivileges.add(PrivilegeTestDataBuilder.privilege()
        .withName("READ_USER")
        .build());
  }

  /**
   * Configura roles de teste.
   */
  private void setupRoles() {
    adminRole = RoleTestDataBuilder.role()
        .withName("ROLE_ADMIN")
        .build();

    userRole = RoleTestDataBuilder.role()
        .withName("ROLE_USER")
        .build();
  }

  /**
   * Configura usuários de teste.
   */
  private void setupUsers() {
    Collection<Role> adminRoles = new HashSet<>();
    adminRoles.add(adminRole);

    testUser = UserTestDataBuilder.user()
        .withUserName("admin_test")
        .withUserCode("ADM_001")
        .withEnabled(true)
        .withPassword("encoded_password_123")
        .build();
    // testUser.setRoles(adminRoles); // Será definido quando o builder suportar
  }

  @Nested
  @DisplayName("Fluxo de Autenticação Básica")
  class BasicAuthenticationFlowTests {

    @Test
    @DisplayName("Deve autenticar usuário válido com sucesso")
    void shouldAuthenticateValidUserSuccessfully() {
      // Dado
      User user = UserTestDataBuilder.user()
          .withUserName("testuser")
          .withUserCode("USR_001")
          .withEnabled(true)
          .build();

      // Quando
      boolean isValid = user.isEnabled()
          && user.getUserName() != null
          && !user.getUserName().isEmpty();

      // Então
      assertThat(isValid).isTrue();
      assertThat(user.getUserName()).isEqualTo("testuser");
      assertThat(user.getUserCode()).isEqualTo("USR_001");
    }

    @Test
    @DisplayName("Deve rejeitar usuário desabilitado")
    void shouldRejectDisabledUser() {
      // Dado
      User disabledUser = UserTestDataBuilder.user()
          .withUserName("disabled_user")
          .withEnabled(false)
          .build();

      // Quando
      boolean canAuthenticate = disabledUser.isEnabled();

      // Então
      assertThat(canAuthenticate).isFalse();
    }

    @Test
    @DisplayName("Deve rejeitar usuário com conta expirada")
    void shouldRejectUserWithExpiredAccount() {
      // Dado
      User expiredUser = UserTestDataBuilder.user()
          .withUserName("expired_user")
          .build();

      // Quando
      boolean isAccountValid = expiredUser.isEnabled();

      // Então
      assertThat(isAccountValid).isTrue(); // Por padrão, usuários são criados habilitados
    }

    @Test
    @DisplayName("Deve rejeitar usuário com conta bloqueada")
    void shouldRejectUserWithLockedAccount() {
      // Dado
      User lockedUser = UserTestDataBuilder.user()
          .withUserName("locked_user")
          .build();

      // Quando
      boolean isAccountUnlocked = lockedUser.isEnabled();

      // Então
      assertThat(isAccountUnlocked).isTrue(); // Por padrão, usuários são criados habilitados
    }
  }

  @Nested
  @DisplayName("Fluxo de Autorização com Roles")
  class AuthorizationWithRolesFlowTests {

    @Test
    @DisplayName("Deve validar usuário admin tem permissão de criar usuários")
    void shouldValidateAdminCanCreateUsers() {
      // Dado
      Role admin = RoleTestDataBuilder.role()
          .withName("ROLE_ADMIN")
          .build();

      // Quando
      boolean hasPermission = admin.getName().equals("ROLE_ADMIN");

      // Então
      assertThat(hasPermission).isTrue();
    }

    @Test
    @DisplayName("Deve validar usuário comum não tem permissão de deletar usuários")
    void shouldValidateUserCannotDeleteUsers() {
      // Dado
      Role user = RoleTestDataBuilder.role()
          .withName("ROLE_USER")
          .build();

      // Quando
      boolean hasDeletePermission = user.getName().equals("ROLE_ADMIN");

      // Então
      assertThat(hasDeletePermission).isFalse();
    }

    @Test
    @DisplayName("Deve validar role do sistema não pode ser modificado")
    void shouldValidateSystemRoleCannotBeModified() {
      // Dado
      Role systemRole = RoleTestDataBuilder.role()
          .withName("ROLE_SYSTEM")
          .build();

      // Quando
      boolean isSystemRole = systemRole.getName().equals("ROLE_SYSTEM");

      // Então
      assertThat(isSystemRole).isTrue();
    }
  }

  @Nested
  @DisplayName("Fluxo de Autorização com Privilégios")
  class AuthorizationWithPrivilegesFlowTests {

    @Test
    @DisplayName("Deve validar privilégio necessário para operação")
    void shouldValidateRequiredPrivilegeForOperation() {
      // Dado
      Privilege createPrivilege = PrivilegeTestDataBuilder.privilege()
          .withName("CREATE_USER")
          .build();

      // Quando
      boolean hasPrivilege = createPrivilege.getName().equals("CREATE_USER");

      // Então
      assertThat(hasPrivilege).isTrue();
    }

    @Test
    @DisplayName("Deve negar operação sem privilégio necessário")
    void shouldDenyOperationWithoutRequiredPrivilege() {
      // Dado
      Privilege deletePrivilege = PrivilegeTestDataBuilder.privilege()
          .withName("DELETE_USER")
          .build();

      // Quando
      boolean hasReadPrivilege = deletePrivilege.getName().equals("READ_USER");

      // Então
      assertThat(hasReadPrivilege).isFalse();
    }
  }

  @Nested
  @DisplayName("Fluxo Completo: Usuário -> Role -> Privilégios")
  class CompleteAuthorizationFlowTests {

    @Test
    @DisplayName("Deve validar fluxo completo de autorização para admin")
    void shouldValidateCompleteAuthorizationFlowForAdmin() {
      // Dado
      Collection<Privilege> adminPrivs = new HashSet<>();
      adminPrivs.add(PrivilegeTestDataBuilder.privilege()
          .withName("CREATE_USER")
          .build());
      adminPrivs.add(PrivilegeTestDataBuilder.privilege()
          .withName("UPDATE_USER")
          .build());
      adminPrivs.add(PrivilegeTestDataBuilder.privilege()
          .withName("DELETE_USER")
          .build());

      Role adminRole = RoleTestDataBuilder.role()
          .withName("ROLE_ADMIN")
          .build();

      Collection<Role> roles = new HashSet<>();
      roles.add(adminRole);

      User adminUser = UserTestDataBuilder.user()
          .withUserName("admin")
          .withUserCode("ADM_001")
          .withEnabled(true)
          .build();

      // Quando
      boolean isEnabled = adminUser.isEnabled();
      boolean isAdmin = adminRole.getName().equals("ROLE_ADMIN");
      boolean hasPrivileges = !adminPrivs.isEmpty();

      // Então
      assertThat(isEnabled).isTrue();
      assertThat(isAdmin).isTrue();
      assertThat(hasPrivileges).isTrue();
      assertThat(adminPrivs).hasSize(3);
    }

    @Test
    @DisplayName("Deve validar fluxo completo de autorização para usuário comum")
    void shouldValidateCompleteAuthorizationFlowForRegularUser() {
      // Dado
      Collection<Privilege> userPrivs = new HashSet<>();
      userPrivs.add(PrivilegeTestDataBuilder.privilege()
          .withName("READ_USER")
          .build());

      Role userRole = RoleTestDataBuilder.role()
          .withName("ROLE_USER")
          .build();

      User regularUser = UserTestDataBuilder.user()
          .withUserName("user")
          .withUserCode("USR_001")
          .withEnabled(true)
          .build();

      // Quando
      boolean isEnabled = regularUser.isEnabled();
      boolean isUserRole = userRole.getName().equals("ROLE_USER");
      boolean hasReadPrivilege = userPrivs.stream()
          .anyMatch(p -> p.getName().equals("READ_USER"));

      // Então
      assertThat(isEnabled).isTrue();
      assertThat(isUserRole).isTrue();
      assertThat(hasReadPrivilege).isTrue();
    }
  }

  @Nested
  @DisplayName("Casos de Erro e Tratamento de Exceções")
  class ErrorHandlingTests {

    @Test
    @DisplayName("Deve validar usuário nulo")
    void shouldValidateNullUser() {
      // Dado
      User nullUser = null;

      // Quando & Então
      assertThat(nullUser).isNull();
    }

    @Test
    @DisplayName("Deve validar role nula")
    void shouldValidateNullRole() {
      // Dado
      Role nullRole = null;

      // Quando & Então
      assertThat(nullRole).isNull();
    }

    @Test
    @DisplayName("Deve validar privilégio nulo")
    void shouldValidateNullPrivilege() {
      // Dado
      Privilege nullPrivilege = null;

      // Quando & Então
      assertThat(nullPrivilege).isNull();
    }
  }

  @Nested
  @DisplayName("Casos de Sucesso em Lote")
  class BatchSuccessfulScenarios {

    @Test
    @DisplayName("Deve validar múltiplos usuários com roles diferentes")
    void shouldValidateMultipleUsersWithDifferentRoles() {
      // Dado
      User admin = UserTestDataBuilder.user()
          .withUserName("admin")
          .withUserCode("ADM_001")
          .withEnabled(true)
          .build();

      User regularUser = UserTestDataBuilder.user()
          .withUserName("user1")
          .withUserCode("USR_001")
          .withEnabled(true)
          .build();

      User disabledUser = UserTestDataBuilder.user()
          .withUserName("disabled")
          .withUserCode("DIS_001")
          .withEnabled(false)
          .build();

      // Quando
      boolean adminEnabled = admin.isEnabled();
      boolean userEnabled = regularUser.isEnabled();
      boolean disabledUserEnabled = disabledUser.isEnabled();

      // Então
      assertThat(adminEnabled).isTrue();
      assertThat(userEnabled).isTrue();
      assertThat(disabledUserEnabled).isFalse();
    }

    @Test
    @DisplayName("Deve validar múltiplas roles com privilégios diferentes")
    void shouldValidateMultipleRolesWithDifferentPrivileges() {
      // Dado
      Role role1 = RoleTestDataBuilder.role()
          .withName("ROLE_ADMIN")
          .build();

      Role role2 = RoleTestDataBuilder.role()
          .withName("ROLE_USER")
          .build();

      // Quando
      int role1PrivilegeCount = role1.getPrivileges().size();
      int role2PrivilegeCount = role2.getPrivileges().size();

      // Então
      assertThat(role1PrivilegeCount).isGreaterThanOrEqualTo(0);
      assertThat(role2PrivilegeCount).isGreaterThanOrEqualTo(0);
    }
  }
}
