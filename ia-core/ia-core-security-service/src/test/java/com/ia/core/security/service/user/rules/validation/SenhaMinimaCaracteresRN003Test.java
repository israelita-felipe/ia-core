package com.ia.core.security.service.user.rules.validation;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SenhaMinimaCaracteresRN003}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SenhaMinimaCaracteresRN003")
class SenhaMinimaCaracteresRN003Test {

    private static final int MIN_PASSWORD_LENGTH = 8;

    @Mock
    private Translator translator;

    private SenhaMinimaCaracteresRN003 rule;

    @BeforeEach
    void setUp() {
        rule = new SenhaMinimaCaracteresRN003(translator);
    }

    @Test
    @DisplayName("Should return correct code")
    void shouldReturnCorrectCode() {
        assertThat(rule.getCode()).isEqualTo("USER_RN003");
    }

    @Test
    @DisplayName("Should validate successfully when password has 8 characters")
    void shouldValidateSuccessfullyWhenPasswordHas8Characters() {
        // Given
        UserDTO dto = UserDTO.builder().password("12345678").build();
        ValidationResult result = ValidationResult.create();

        // When
        rule.validate(dto, result);

        // Then
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should add error when password has less than 8 characters")
    void shouldAddErrorWhenPasswordHasLessThan8Characters() {
        // Given
        UserDTO dto = UserDTO.builder().password("1234567").build();
        ValidationResult result = ValidationResult.create();

        // When
        rule.validate(dto, result);

        // Then
        assertThat(result.hasErrorLevelErrors()).isTrue();
        assertThat(result.getErrorsByField("password")).hasSize(1);
    }

    @Test
    @DisplayName("Should validate successfully when password is null")
    void shouldValidateSuccessfullyWhenPasswordIsNull() {
        // Given
        UserDTO dto = UserDTO.builder().password(null).build();
        ValidationResult result = ValidationResult.create();

        // When
        rule.validate(dto, result);

        // Then
        assertThat(result.hasErrors()).isFalse();
    }
}
