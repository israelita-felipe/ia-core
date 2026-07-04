package com.ia.core.communication.service.grupocontato.validators;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.Severity;
import com.ia.core.service.validators.ValidationError;
import com.ia.core.service.validators.ValidationResult;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for GrupoContatoValidator.
 * <p>
 * Tests the validation of GrupoContatoDTO objects with business rules.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("GrupoContatoValidator Tests")
class GrupoContatoValidatorTest extends CoreBaseUnitTest {

    @Mock
    private Translator translator;

    @Mock
    private BusinessRule<GrupoContatoDTO> businessRule1;

    @Mock
    private BusinessRule<GrupoContatoDTO> businessRule2;

    private GrupoContatoValidator validator;
    private List<BusinessRule<GrupoContatoDTO>> businessRules;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        businessRules = new ArrayList<>();
        businessRules.add(businessRule1);
        businessRules.add(businessRule2);
        validator = new GrupoContatoValidator(translator, businessRules);
    }

    @Test
    @DisplayName("Should initialize with translator and business rules")
    void shouldInitializeWithTranslatorAndBusinessRules() {
        // Assert
        assertThat(validator).isNotNull();
        assertThat(validator.getTranslator()).isEqualTo(translator);
        assertThat(validator.getBusinessRules()).hasSize(2);
    }

    @Test
    @DisplayName("Should initialize with empty business rules list")
    void shouldInitializeWithEmptyBusinessRules() {
        // Arrange
        List<BusinessRule<GrupoContatoDTO>> emptyRules = new ArrayList<>();

        // Act
        GrupoContatoValidator validatorWithEmptyRules = new GrupoContatoValidator(translator, emptyRules);

        // Assert
        assertThat(validatorWithEmptyRules).isNotNull();
        assertThat(validatorWithEmptyRules.getBusinessRules()).isEmpty();
    }

    @Test
    @DisplayName("Should execute all business rules during validation")
    void shouldExecuteAllBusinessRulesDuringValidation() {
        // Arrange
        GrupoContatoDTO dto = createFixture(GrupoContatoDTO.class);
        ValidationResult result = ValidationResult.create();
        when(businessRule1.isApplicable(any())).thenReturn(true);
        when(businessRule2.isApplicable(any())).thenReturn(true);

        // Act
        validator.validate(dto, result);

        // Assert
        verify(businessRule1).isApplicable(eq(dto));
        verify(businessRule1).validate(eq(dto), any(ValidationResult.class));
        verify(businessRule2).isApplicable(eq(dto));
        verify(businessRule2).validate(eq(dto), any(ValidationResult.class));
    }

    @Test
    @DisplayName("Should add errors from business rules to result")
    void shouldAddErrorsFromBusinessRulesToResult() {
        // Arrange
        GrupoContatoDTO dto = createFixture(GrupoContatoDTO.class);
        ValidationResult result = ValidationResult.create();
        when(businessRule1.isApplicable(any())).thenReturn(true);
        when(businessRule2.isApplicable(any())).thenReturn(true);

        doAnswer(invocation -> {
            ValidationResult r = invocation.getArgument(1);
            r.addError(new ValidationError("field", "error message", Severity.ERROR, null));
            return null;
        }).when(businessRule1).validate(any(), any());

        // Act
        validator.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
    }

    @Test
    @DisplayName("Should accumulate errors from multiple business rules")
    void shouldAccumulateErrorsFromMultipleBusinessRules() {
        // Arrange
        GrupoContatoDTO dto = createFixture(GrupoContatoDTO.class);
        ValidationResult result = ValidationResult.create();
        when(businessRule1.isApplicable(any())).thenReturn(true);
        when(businessRule2.isApplicable(any())).thenReturn(true);

        doAnswer(invocation -> {
            ValidationResult r = invocation.getArgument(1);
            r.addError(new ValidationError("field1", "error1", Severity.ERROR, null));
            return null;
        }).when(businessRule1).validate(any(), any());

        doAnswer(invocation -> {
            ValidationResult r = invocation.getArgument(1);
            r.addError(new ValidationError("field2", "error2", Severity.ERROR, null));
            return null;
        }).when(businessRule2).validate(any(), any());

        // Act
        validator.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(2);
    }

    @Test
    @DisplayName("Should handle validation with no errors")
    void shouldHandleValidationWithNoErrors() {
        // Arrange
        GrupoContatoDTO dto = createFixture(GrupoContatoDTO.class);
        ValidationResult result = ValidationResult.create();
        when(businessRule1.isApplicable(any())).thenReturn(true);
        when(businessRule2.isApplicable(any())).thenReturn(true);

        // Act
        validator.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should handle null DTO gracefully")
    void shouldHandleNullDTOGracefully() {
        // Arrange
        ValidationResult result = ValidationResult.create();
        when(businessRule1.isApplicable(any())).thenReturn(true);
        when(businessRule2.isApplicable(any())).thenReturn(true);

        // Act
        validator.validate(null, result);

        // Assert
        verify(businessRule1).isApplicable(isNull());
        verify(businessRule1).validate(isNull(), any(ValidationResult.class));
    }
}
