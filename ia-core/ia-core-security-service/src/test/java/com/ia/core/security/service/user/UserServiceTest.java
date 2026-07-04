package com.ia.core.security.service.user;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.model.user.UserPasswordResetDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

/**
 * Tests for {@link UserService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService")
class UserServiceTest {

    @Mock
    private UserServiceConfig config;

    @Mock
    private BaseEntityRepository<User> repository;

    @Mock
    private BaseEntityMapper<User, com.ia.core.security.service.model.user.UserDTO> mapper;

    @Mock
    private UserPasswordEncoder passwordEncoder;

    @Mock
    private SearchRequestMapper searchRequestMapper;

    @Mock
    private Translator translator;

    @Mock
    private CoreSecurityAuthorizationManager authorizationManager;

    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private LogOperationService logOperationService;

    private TestableUserService service;

    @BeforeEach
    void setUp() {
        lenient().when(config.getRepository()).thenReturn(repository);
        lenient().when(config.getMapper()).thenReturn(mapper);
        lenient().when(config.getPasswordEncoder()).thenReturn(passwordEncoder);
        lenient().when(config.getSearchRequestMapper()).thenReturn(searchRequestMapper);
        lenient().when(config.getTranslator()).thenReturn(translator);
        lenient().when(config.getAuthorizationManager()).thenReturn(authorizationManager);
        lenient().when(config.getSecurityContextService()).thenReturn(securityContextService);
        lenient().when(config.getLogOperationService()).thenReturn(logOperationService);
        lenient().when(config.getValidators()).thenReturn(List.of());
        service = new TestableUserService(config);
    }

    @Nested
    @DisplayName("changePassword")
    class ChangePasswordTests {

        @Test
        @DisplayName("Should throw exception when changeDTO is null")
        void shouldThrowExceptionWhenChangeDTOIsNull() {
            // When & Then
            assertThrows(NullPointerException.class, () -> service.changePassword(null));
        }

        @Test
        @DisplayName("Should throw exception when userCode is null")
        void shouldThrowExceptionWhenUserCodeIsNull() {
            // Given
            UserPasswordChangeDTO change = new UserPasswordChangeDTO();
            change.setOldPassword("oldPass");
            change.setNewPassword("newPass");

            // When & Then
            assertThrows(NullPointerException.class, () -> service.changePassword(change));
        }

        @Test
        @DisplayName("Should throw exception when oldPassword is null")
        void shouldThrowExceptionWhenOldPasswordIsNull() {
            // Given
            UserPasswordChangeDTO change = new UserPasswordChangeDTO();
            change.setUserCode("userCode");
            change.setNewPassword("newPass");

            // When & Then
            assertThrows(NullPointerException.class, () -> service.changePassword(change));
        }

        @Test
        @DisplayName("Should throw exception when newPassword is null")
        void shouldThrowExceptionWhenNewPasswordIsNull() {
            // Given
            UserPasswordChangeDTO change = new UserPasswordChangeDTO();
            change.setUserCode("userCode");
            change.setOldPassword("oldPass");

            // When & Then
            assertThrows(NullPointerException.class, () -> service.changePassword(change));
        }
    }

    @Nested
    @DisplayName("resetPassword")
    class ResetPasswordTests {

        @Test
        @DisplayName("Should throw exception when reset is null")
        void shouldThrowExceptionWhenResetIsNull() {
            // When & Then
            assertThrows(NullPointerException.class, () -> service.resetPassword(null));
        }

        @Test
        @DisplayName("Should throw exception when userCode is null")
        void shouldThrowExceptionWhenUserCodeIsNull() {
            // Given
            UserPasswordResetDTO reset = new UserPasswordResetDTO();

            // When & Then
            assertThrows(NullPointerException.class, () -> service.resetPassword(reset));
        }
    }

    @Nested
    @DisplayName("getConfig")
    class GetConfigTests {

        @Test
        @DisplayName("Should return config from parent class")
        void shouldReturnConfigFromParentClass() {
            // When
            UserServiceConfig result = service.getConfig();

            // Then
            assertThat(result).isEqualTo(config);
        }
    }

    @Nested
    @DisplayName("getFunctionalityTypeName")
    class GetFunctionalityTypeNameTests {

        @Test
        @DisplayName("Should return USER functionality type name")
        void shouldReturnUSERFunctionalityTypeName() {
            // When
            String result = service.getFunctionalityTypeName();

            // Then
            assertThat(result).isEqualTo(UserTranslator.USER);
        }
    }

    private static class TestableUserService extends UserService {
        TestableUserService(UserServiceConfig config) {
            super(config);
        }
    }
}
