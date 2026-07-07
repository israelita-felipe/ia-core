package com.ia.core.security.service.user.rules;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.service.translator.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UsuarioAtivoRN002}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioAtivoRN002")
class UsuarioAtivoRN002Test {

    @Mock
    private Translator translator;

    private UsuarioAtivoRN002 rule;

    @BeforeEach
    void setUp() {
        rule = new UsuarioAtivoRN002(translator);
    }

    @Test
    @DisplayName("Should return correct code")
    void shouldReturnCorrectCode() {
        assertThat(rule.getCode()).isEqualTo("USER_RN002");
    }

    @Test
    @DisplayName("Should return true when user is active")
    void shouldReturnTrueWhenUserIsActive() {
        // Given
        UserDTO dto = UserDTO.builder()
            .enabled(true)
            .accountNotExpired(true)
            .accountNotLocked(true)
            .credentialsNotExpired(true)
            .build();

        // When
        boolean result = rule.isUserActive(dto);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false when user is disabled")
    void shouldReturnFalseWhenUserIsDisabled() {
        // Given
        UserDTO dto = UserDTO.builder()
            .enabled(false)
            .accountNotExpired(true)
            .accountNotLocked(true)
            .credentialsNotExpired(true)
            .build();

        // When
        boolean result = rule.isUserActive(dto);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when account is locked")
    void shouldReturnFalseWhenAccountIsLocked() {
        // Given
        UserDTO dto = UserDTO.builder()
            .enabled(true)
            .accountNotExpired(true)
            .accountNotLocked(false)
            .credentialsNotExpired(true)
            .build();

        // When
        boolean result = rule.isUserActive(dto);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when dto is null")
    void shouldReturnFalseWhenDtoIsNull() {
        // When
        boolean result = rule.isUserActive(null);

        // Then
        assertThat(result).isFalse();
    }
}
