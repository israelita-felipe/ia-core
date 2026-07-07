package com.ia.core.security.service.user.rules;

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
import static org.mockito.Mockito.lenient;

/**
 * Tests for {@link UserCodeUnicoRN001}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserCodeUnicoRN001")
class UserCodeUnicoRN001Test {

    @Mock
    private Translator translator;

    private UserCodeUnicoRN001 rule;

    @BeforeEach
    void setUp() {
        rule = new UserCodeUnicoRN001(translator);
    }

    @Test
    @DisplayName("Should return correct code")
    void shouldReturnCorrectCode() {
        assertThat(rule.getCode()).isEqualTo("USER_RN001");
    }

    @Test
    @DisplayName("Should return correct name from translator")
    void shouldReturnCorrectName() {
        lenient().when(translator.getTranslation("user.rule.codigo.unico.name")).thenReturn("Código único");
        assertThat(rule.getName()).isEqualTo("Código único");
    }

    @Test
    @DisplayName("Should validate successfully when userCode is valid")
    void shouldValidateSuccessfullyWhenUserCodeIsValid() {
        // Given
        UserDTO dto = UserDTO.builder().userCode("validUser").build();
        ValidationResult result = ValidationResult.create();

        // When
        rule.validate(dto, result);

        // Then
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should validate successfully when userCode is null")
    void shouldValidateSuccessfullyWhenUserCodeIsNull() {
        // Given
        UserDTO dto = UserDTO.builder().userCode(null).build();
        ValidationResult result = ValidationResult.create();

        // When
        rule.validate(dto, result);

        // Then
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should validate successfully when dto is null")
    void shouldValidateSuccessfullyWhenDtoIsNull() {
        // Given
        ValidationResult result = ValidationResult.create();

        // When
        rule.validate(null, result);

        // Then
        assertThat(result.hasErrors()).isFalse();
    }
}
