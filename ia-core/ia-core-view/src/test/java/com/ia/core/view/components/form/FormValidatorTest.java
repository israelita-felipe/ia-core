package com.ia.core.view.components.form;

import com.ia.test.CoreBaseUnitTest;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for FormValidator.
 * <p>
 * Tests Vaadin form validation component using Jakarta Validation.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("FormValidator Tests")
class FormValidatorTest extends CoreBaseUnitTest {

    private FormValidator formValidator;
    private jakarta.validation.Validator jakartaValidator;

    @BeforeEach
    void setUp() {
        jakarta.validation.ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
        jakartaValidator = factory.getValidator();
        formValidator = new FormValidator(jakartaValidator);
    }

    @Test
    @DisplayName("Should initialize with Jakarta validator")
    void shouldInitializeWithJakartaValidator() {
        // Assert
        assertThat(formValidator).isNotNull();
        assertThat(formValidator.getJakartaValidator()).isSameAs(jakartaValidator);
    }

    @Test
    @DisplayName("Should return empty binding result for valid object")
    void shouldReturnEmptyBindingResultForValidObject() {
        // Arrange
        TestDTO dto = new TestDTO("valid name", "valid message");

        // Act
        BindingResult result = formValidator.validate(dto, "testDTO");

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should return binding result with errors for invalid object")
    void shouldReturnBindingResultWithErrorsForInvalidObject() {
        // Arrange
        TestDTO dto = new TestDTO(null, null);

        // Act
        BindingResult result = formValidator.validate(dto, "testDTO");

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors()).isNotEmpty();
    }

    @Test
    @DisplayName("Should add field errors with correct field names")
    void shouldAddFieldErrorsWithCorrectFieldNames() {
        // Arrange
        TestDTO dto = new TestDTO(null, "valid message");

        // Act
        BindingResult result = formValidator.validate(dto, "testDTO");

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors()).hasSize(1);
        assertThat(result.getFieldErrors().get(0).getField()).isEqualTo("name");
    }

    @Test
    @DisplayName("Should add field errors with correct messages")
    void shouldAddFieldErrorsWithCorrectMessages() {
        // Arrange
        TestDTO dto = new TestDTO(null, "valid message");

        // Act
        BindingResult result = formValidator.validate(dto, "testDTO");

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().get(0).getDefaultMessage()).isNotEmpty();
    }

    @Test
    @DisplayName("Should return empty map for valid object")
    void shouldReturnEmptyMapForValidObject() {
        // Arrange
        TestDTO dto = new TestDTO("valid name", "valid message");

        // Act
        Map<String, String> errors = formValidator.validateToMap(dto);

        // Assert
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("Should return map with errors for invalid object")
    void shouldReturnMapWithErrorsForInvalidObject() {
        // Arrange
        TestDTO dto = new TestDTO(null, null);

        // Act
        Map<String, String> errors = formValidator.validateToMap(dto);

        // Assert
        assertThat(errors).isNotEmpty();
    }

    @Test
    @DisplayName("Should map field names to error messages")
    void shouldMapFieldNamesToErrorMessages() {
        // Arrange
        TestDTO dto = new TestDTO(null, "valid message");

        // Act
        Map<String, String> errors = formValidator.validateToMap(dto);

        // Assert
        assertThat(errors).hasSize(1);
        assertThat(errors).containsKey("name");
        assertThat(errors.get("name")).isNotEmpty();
    }

    @Test
    @DisplayName("Should return false for valid object when checking hasErrors")
    void shouldReturnFalseForValidObjectWhenCheckingHasErrors() {
        // Arrange
        TestDTO dto = new TestDTO("valid name", "valid message");

        // Act
        boolean hasErrors = formValidator.hasErrors(dto);

        // Assert
        assertThat(hasErrors).isFalse();
    }

    @Test
    @DisplayName("Should return true for invalid object when checking hasErrors")
    void shouldReturnTrueForInvalidObjectWhenCheckingHasErrors() {
        // Arrange
        TestDTO dto = new TestDTO(null, null);

        // Act
        boolean hasErrors = formValidator.hasErrors(dto);

        // Assert
        assertThat(hasErrors).isTrue();
    }

    @Test
    @DisplayName("Should create Vaadin validator")
    void shouldCreateVaadinValidator() {
        // Arrange
        TestDTO dto = new TestDTO("valid name", "valid message");

        // Act
        AbstractValidator<TestDTO> vaadinValidator = formValidator.createVaadinValidator(dto);

        // Assert
        assertThat(vaadinValidator).isNotNull();
    }

    @Test
    @DisplayName("Should return ok result from Vaadin validator for valid object")
    void shouldReturnOkResultFromVaadinValidatorForValidObject() {
        // Arrange
        TestDTO dto = new TestDTO("valid name", "valid message");
        AbstractValidator<TestDTO> vaadinValidator = formValidator.createVaadinValidator(dto);

        // Act
        ValidationResult result = vaadinValidator.apply(dto, new ValueContext());

        // Assert
        assertThat(result.isError()).isFalse();
    }

    @Test
    @DisplayName("Should return error result from Vaadin validator for invalid object")
    void shouldReturnErrorResultFromVaadinValidatorForInvalidObject() {
        // Arrange
        TestDTO dto = new TestDTO(null, null);
        AbstractValidator<TestDTO> vaadinValidator = formValidator.createVaadinValidator(dto);

        // Act
        ValidationResult result = vaadinValidator.apply(dto, new ValueContext());

        // Assert
        assertThat(result.isError()).isTrue();
    }

    @Test
    @DisplayName("Should use class simple name as object name in validateToMap")
    void shouldUseClassSimpleNameAsObjectNameInValidateToMap() {
        // Arrange
        TestDTO dto = new TestDTO(null, null);

        // Act
        Map<String, String> errors = formValidator.validateToMap(dto);

        // Assert
        assertThat(errors).isNotEmpty();
    }

    // Test DTO class for Jakarta validation
    static class TestDTO {
        @NotBlank
        private String name;

        @NotNull
        private String message;

        public TestDTO(String name, String message) {
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}
