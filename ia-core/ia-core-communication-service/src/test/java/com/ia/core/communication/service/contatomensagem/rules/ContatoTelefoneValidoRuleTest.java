package com.ia.core.communication.service.contatomensagem.rules;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.Severity;
import com.ia.core.service.validators.ValidationResult;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ContatoTelefoneValidoRule.
 * <p>
 * Tests phone number validation in contact messages.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("ContatoTelefoneValidoRule Tests")
class ContatoTelefoneValidoRuleTest extends CoreBaseUnitTest {

    @Mock
    private Translator translator;

    private ContatoTelefoneValidoRule rule;

    @BeforeEach
    void setUp() {
        rule = new ContatoTelefoneValidoRule(translator);
    }

    @Test
    @DisplayName("Should initialize with translator")
    void shouldInitializeWithTranslator() {
        // Assert
        assertThat(rule).isNotNull();
        assertThat(rule.getTranslator()).isEqualTo(translator);
    }

    @Test
    @DisplayName("Should return correct code")
    void shouldReturnCorrectCode() {
        // Act
        String code = rule.getCode();

        // Assert
        assertThat(code).isEqualTo("CTR_001");
    }

    @Test
    @DisplayName("Should return correct name")
    void shouldReturnCorrectName() {
        // Act
        String name = rule.getName();

        // Assert
        assertThat(name).isEqualTo("ContatoTelefoneValidoRule");
    }

    @Test
    @DisplayName("Should return correct description")
    void shouldReturnCorrectDescription() {
        // Act
        String description = rule.getDescription();

        // Assert
        assertThat(description).isEqualTo("Verifica se o telefone do contato é válido");
    }

    @Test
    @DisplayName("Should add error for null contact")
    void shouldAddErrorForNullContact() {
        // Arrange
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(null, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("contato");
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("Contato não pode ser nulo");
        assertThat(result.getErrors().get(0).getSeverity()).isEqualTo(Severity.ERROR);
    }

    @Test
    @DisplayName("Should add error for null phone")
    void shouldAddErrorForNullPhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone(null);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("telefone");
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("Telefone é obrigatório");
    }

    @Test
    @DisplayName("Should add error for empty phone")
    void shouldAddErrorForEmptyPhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("telefone");
    }

    @Test
    @DisplayName("Should add error for phone too short (9 digits)")
    void shouldAddErrorForPhoneTooShort() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("123456789");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("telefone");
        assertThat(result.getErrors().get(0).getMessage()).contains("entre 10 e 20");
    }

    @Test
    @DisplayName("Should add error for phone too long (21 digits)")
    void shouldAddErrorForPhoneTooLong() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("123456789012345678901");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("telefone");
        assertThat(result.getErrors().get(0).getMessage()).contains("entre 10 e 20");
    }

    @Test
    @DisplayName("Should not add error for phone with minimum length (10 digits)")
    void shouldNotAddErrorForPhoneWithMinimumLength() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("1234567890");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should not add error for phone with maximum length (20 digits)")
    void shouldNotAddErrorForPhoneWithMaximumLength() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("12345678901234567890");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should not add error for phone within valid range (11 digits)")
    void shouldNotAddErrorForPhoneWithinValidRange() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("11987654321");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should strip formatting from phone")
    void shouldStripFormattingFromPhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("(11) 98765-4321");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should strip special characters from phone")
    void shouldStripSpecialCharactersFromPhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("+55-11-98765-4321");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should strip spaces from phone")
    void shouldStripSpacesFromPhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("11 98765 4321");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should validate Brazilian mobile phone")
    void shouldValidateBrazilianMobilePhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("11987654321");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should validate Brazilian landline")
    void shouldValidateBrazilianLandline() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("1134567890");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should validate international phone")
    void shouldValidateInternationalPhone() {
        // Arrange
        ContatoMensagemDTO dto = createFixture(ContatoMensagemDTO.class);
        dto.setTelefone("15551234567");
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }
}
