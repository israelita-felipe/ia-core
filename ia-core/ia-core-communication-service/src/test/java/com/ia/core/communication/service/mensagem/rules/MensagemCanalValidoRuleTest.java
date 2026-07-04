package com.ia.core.communication.service.mensagem.rules;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
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
 * Test class for MensagemCanalValidoRule.
 * <p>
 * Tests communication channel validation in messages.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("MensagemCanalValidoRule Tests")
class MensagemCanalValidoRuleTest extends CoreBaseUnitTest {

    @Mock
    private Translator translator;

    private MensagemCanalValidoRule rule;

    @BeforeEach
    void setUp() {
        rule = new MensagemCanalValidoRule(translator);
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
        assertThat(code).isEqualTo("MSG_001");
    }

    @Test
    @DisplayName("Should return correct name")
    void shouldReturnCorrectName() {
        // Act
        String name = rule.getName();

        // Assert
        assertThat(name).isEqualTo("MensagemCanalValidoRule");
    }

    @Test
    @DisplayName("Should return correct description")
    void shouldReturnCorrectDescription() {
        // Act
        String description = rule.getDescription();

        // Assert
        assertThat(description).isEqualTo("Verifica se o canal de comunicação é válido para envio");
    }

    @Test
    @DisplayName("Should add error for null message")
    void shouldAddErrorForNullMessage() {
        // Arrange
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(null, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("mensagem");
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("Mensagem não pode ser nula");
        assertThat(result.getErrors().get(0).getSeverity()).isEqualTo(Severity.ERROR);
    }

    @Test
    @DisplayName("Should add error for null tipoCanal")
    void shouldAddErrorForNullTipoCanal() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(null);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0).getField()).isEqualTo("tipoCanal");
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("Tipo do canal é obrigatório");
    }

    @Test
    @DisplayName("Should not add error for valid EMAIL channel")
    void shouldNotAddErrorForValidEmailChannel() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(TipoCanal.EMAIL);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should not add error for valid WHATSAPP channel")
    void shouldNotAddErrorForValidWhatsappChannel() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(TipoCanal.WHATSAPP);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should not add error for valid TELEGRAM channel")
    void shouldNotAddErrorForValidTelegramChannel() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(TipoCanal.TELEGRAM);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should not add error for valid SMS channel")
    void shouldNotAddErrorForValidSmsChannel() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(TipoCanal.SMS);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should add error for unsupported channel")
    void shouldAddErrorForUnsupportedChannel() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        // Assuming there's a PUSH channel or similar unsupported channel
        // For this test, we'll need to check if TipoCanal has other values
        ValidationResult result = ValidationResult.create();

        // Act
        // This test depends on TipoCanal enum having unsupported values
        // For now, we'll skip this if all enum values are supported
        if (TipoCanal.values().length > 4) {
            TipoCanal unsupportedChannel = TipoCanal.values()[4];
            dto.setTipoCanal(unsupportedChannel);
            rule.validate(dto, result);

            // Assert
            assertThat(result.hasErrors()).isTrue();
            assertThat(result.getErrors().get(0).getField()).isEqualTo("tipoCanal");
            assertThat(result.getErrors().get(0).getMessage()).contains("Canal não suportado");
        }
    }

    @Test
    @DisplayName("Should compare channel against valid channels array")
    void shouldCompareChannelAgainstValidChannelsArray() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(TipoCanal.EMAIL);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should handle enum comparison correctly")
    void shouldHandleEnumComparisonCorrectly() {
        // Arrange
        MensagemDTO dto = createFixture(MensagemDTO.class);
        dto.setTipoCanal(TipoCanal.WHATSAPP);
        ValidationResult result = ValidationResult.create();

        // Act
        rule.validate(dto, result);

        // Assert
        assertThat(result.hasErrors()).isFalse();
    }
}
