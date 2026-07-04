package com.ia.core.communication.service.modelomensagem.validators;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
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
 * Test class for ModeloMensagemValidator.
 * <p>
 * Tests the validation of ModeloMensagemDTO objects with business rules.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("ModeloMensagemValidator Tests")
class ModeloMensagemValidatorTest extends CoreBaseUnitTest {

    @Mock
    private Translator translator;

    @Mock
    private BusinessRule<ModeloMensagemDTO> businessRule1;

    @Mock
    private BusinessRule<ModeloMensagemDTO> businessRule2;

    private ModeloMensagemValidator validator;
    private List<BusinessRule<ModeloMensagemDTO>> businessRules;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        businessRules = new ArrayList<>();
        businessRules.add(businessRule1);
        businessRules.add(businessRule2);
        validator = new ModeloMensagemValidator(translator, businessRules);
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
        List<BusinessRule<ModeloMensagemDTO>> emptyRules = new ArrayList<>();

        // Act
        ModeloMensagemValidator validatorWithEmptyRules = new ModeloMensagemValidator(translator, emptyRules);

        // Assert
        assertThat(validatorWithEmptyRules).isNotNull();
        assertThat(validatorWithEmptyRules.getBusinessRules()).isEmpty();
    }

    @Test
    @DisplayName("Should execute all business rules during validation")
    void shouldExecuteAllBusinessRulesDuringValidation() {
        // Arrange
        ModeloMensagemDTO dto = createFixture(ModeloMensagemDTO.class);
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
        ModeloMensagemDTO dto = createFixture(ModeloMensagemDTO.class);
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
        ModeloMensagemDTO dto = createFixture(ModeloMensagemDTO.class);
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
        ModeloMensagemDTO dto = createFixture(ModeloMensagemDTO.class);
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
