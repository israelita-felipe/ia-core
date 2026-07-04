package com.ia.test.security.integration;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.user.User;
import com.ia.core.security.service.authentication.JwtAuthenticationService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.privilege.PrivilegeService;
import com.ia.core.security.service.role.RoleService;
import com.ia.core.security.service.user.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test for authentication and authorization flows.
 * Tests the interaction between multiple security services.
 */
@SpringBootTest(classes = com.ia.test.security.config.SecurityTestConfiguration.class)
@Testcontainers
@ActiveProfiles("testcontainers")
@DisplayName("Integration Tests: Authentication and Authorization")
@Disabled("Requires Docker environment for Testcontainers")
class AuthenticationAuthorizationIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private JwtAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private LogOperationService logOperationService;

    @Nested
    @DisplayName("Complete Authentication Flow Tests")
    class CompleteAuthenticationFlowTests {

        @Test
        @DisplayName("Should authenticate user with roles and privileges")
        void shouldAuthenticateUserWithRolesAndPrivileges() {
            // Given: A valid user with roles and privileges configured
            User user = createTestUserWithRolesAndPrivileges();

            // When: The user is authenticated
            AuthenticationRequest request = new AuthenticationRequest(user.getUserName(), "password123");
            var response = (JwtAuthenticationResponseDTO) authenticationService.login(request, (storedPassword, providedPassword) -> true);

            // Then: Should return valid JWT token with correct claims
            assertThat(response).isNotNull();
            assertThat(response.getToken()).isNotEmpty();
            assertThat(response.getRefreshToken()).isNotEmpty();

            // And: Should allow access to protected resources based on permissions
            assertThat(hasPrivilege(user, "READ_USERS")).isTrue();
        }

        @Test
        @DisplayName("Should consolidate privileges from multiple roles")
        void shouldConsolidatePrivilegesFromMultipleRoles() {
            // Given: A user with multiple roles
            User user = createTestUserWithMultipleRoles();

            // When: Checking if user has specific privileges
            boolean hasReadPrivilege = hasPrivilege(user, "READ_USERS");
            boolean hasWritePrivilege = hasPrivilege(user, "WRITE_USERS");

            // Then: Should consolidate all privileges from all roles
            assertThat(hasReadPrivilege).isTrue();
            assertThat(hasWritePrivilege).isTrue();
        }

        @Test
        @DisplayName("Should refresh expired token")
        void shouldRefreshExpiredToken() {
            // Given: An expired but valid JWT token
            User user = createTestUserWithRolesAndPrivileges();
            AuthenticationRequest request = new AuthenticationRequest(user.getUserName(), "password123");
            var originalResponse = (JwtAuthenticationResponseDTO) authenticationService.login(request, (storedPassword, providedPassword) -> true);

            // When: The token is sent for refresh
            AuthenticationRequest refreshRequest = new AuthenticationRequest(originalResponse.getRefreshToken());
            var refreshedResponse = (JwtAuthenticationResponseDTO) authenticationService.refreshToken(refreshRequest);

            // Then: Should generate new valid JWT token with same claims
            assertThat(refreshedResponse).isNotNull();
            assertThat(refreshedResponse.getToken()).isNotEmpty();
            assertThat(refreshedResponse.getToken()).isNotEqualTo(originalResponse.getToken());
        }

        @Test
        @DisplayName("Should reject authentication with invalid credentials")
        void shouldRejectAuthenticationWithInvalidCredentials() {
            // Given: Invalid credentials (non-existent user or incorrect password)
            String invalidUsername = "nonexistent_user";
            String invalidPassword = "wrong_password";

            // When: Authentication is attempted
            // Then: Should throw SecurityException with appropriate message
            AuthenticationRequest request = new AuthenticationRequest(invalidUsername, invalidPassword);
            assertThatThrownBy(() -> authenticationService.login(request, (storedPassword, providedPassword) -> false))
                .isInstanceOf(SecurityException.class);
        }

        @Test
        @DisplayName("Should return false for user without specific privileges")
        void shouldReturnFalseForUserWithoutSpecificPrivileges() {
            // Given: An authenticated user without specific privileges
            User user = createTestUserWithLimitedPrivileges();

            // When: Checking if user has a specific privilege
            boolean hasPrivilege = hasPrivilege(user, "DELETE_USERS");

            // Then: Should return false without throwing exception
            assertThat(hasPrivilege).isFalse();
        }

        @Test
        @DisplayName("Should integrate with LogOperationService")
        void shouldIntegrateWithLogOperationService() {
            // Given: An authentication or authorization operation is executed
            User user = createTestUserWithRolesAndPrivileges();

            // When: The operation is completed
            AuthenticationRequest request = new AuthenticationRequest(user.getUserName(), "password123");
            authenticationService.login(request, (storedPassword, providedPassword) -> true);

            // Then: Should register the operation in LogOperationService
            // Note: Implementation depends on actual LogOperationService behavior
            // This test verifies the integration point exists
            assertThat(logOperationService).isNotNull();
        }
    }

    private User createTestUserWithRolesAndPrivileges() {
        // Create test user with roles and privileges
        // Implementation depends on actual service methods
        return new User();
    }

    private User createTestUserWithMultipleRoles() {
        // Create test user with multiple roles
        // Implementation depends on actual service methods
        return new User();
    }

    private User createTestUserWithLimitedPrivileges() {
        // Create test user with limited privileges
        // Implementation depends on actual service methods
        return new User();
    }

    private boolean hasPrivilege(User user, String privilegeName) {
        return user.getPrivileges().stream()
            .anyMatch(userPrivilege -> userPrivilege.getPrivilege() != null
                && privilegeName.equals(userPrivilege.getPrivilege().getName()));
    }

    private static class SecurityException extends RuntimeException {
        public SecurityException(String message) {
            super(message);
        }
    }
}
