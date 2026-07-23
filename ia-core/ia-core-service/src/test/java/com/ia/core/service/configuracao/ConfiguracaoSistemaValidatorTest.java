package com.ia.core.service.configuracao;

import com.ia.core.model.configuracao.TipoConfiguracao;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for ConfiguracaoSistemaValidator.
 * <p>
 * Tests the validation of ConfiguracaoSistemaDTO objects with business rules.
 * Segue o padrão de testes do ia-core.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ConfiguracaoSistemaValidator Tests")
class ConfiguracaoSistemaValidatorTest extends CoreBaseUnitTest {

    @Mock
    private Translator translator;

    @Mock
    private BusinessRule<ConfiguracaoSistemaDTO<?>> businessRule;

    private ConfiguracaoSistemaValidator validator;
    private List<BusinessRule<ConfiguracaoSistemaDTO<?>>> businessRules;

    @BeforeEach
    void setUp() {
        businessRules = new ArrayList<>();
        businessRules.add(businessRule);
        validator = new ConfiguracaoSistemaValidator(translator, businessRules);
    }

    @Test
    @DisplayName("Should initialize with translator and business rules")
    void shouldInitializeWithTranslatorAndBusinessRules() {
        // Assert
        assertThat(validator).isNotNull();
        assertThat(validator.getTranslator()).isEqualTo(translator);
        assertThat(validator.getBusinessRules()).hasSize(1);
    }

    @Test
    @DisplayName("Should initialize with empty business rules list")
    void shouldInitializeWithEmptyBusinessRules() {
        // Act
        ConfiguracaoSistemaValidator validatorWithEmptyRules = new ConfiguracaoSistemaValidator(translator, new ArrayList<>());

        // Assert
        assertThat(validatorWithEmptyRules).isNotNull();
        assertThat(validatorWithEmptyRules.getBusinessRules()).isEmpty();
    }

    @Test
    @DisplayName("Should execute business rule during validation")
    void shouldExecuteBusinessRuleDuringValidation() {
        // Arrange
        ConfiguracaoSistemaDTO<?> dto = createTestDto();
        ValidationResult result = ValidationResult.create();
        when(businessRule.isApplicable(any())).thenReturn(true);

        // Act
        validator.validate(dto, result);

        // Assert
        verify(businessRule).isApplicable(eq(dto));
        verify(businessRule).validate(eq(dto), any(ValidationResult.class));
    }

    @Test
    @DisplayName("Should create validator without business rules")
    void shouldCreateValidatorWithoutBusinessRules() {
        // Act
        ConfiguracaoSistemaValidator simpleValidator = new ConfiguracaoSistemaValidator(translator);

        // Assert
        assertThat(simpleValidator).isNotNull();
        assertThat(simpleValidator.getTranslator()).isEqualTo(translator);
    }

    /**
     * Creates a test DTO instance manually.
     * Required because ConfiguracaoSistemaDTO<T> is generic and Instancio
     * cannot instantiate generic types without concrete type parameters.
     */
    @SuppressWarnings("unused")
    private ConfiguracaoSistemaDTO<?> createTestDto() {
        return ConfiguracaoSistemaDTO.<com.ia.core.model.configuracao.ConfiguracaoSistema>builder()
            .chave("test.key")
            .valor("test-value")
            .modulo("test")
            .categoria("test")
            .tipo(TipoConfiguracao.STRING)
            .build();
    }
}