package com.ia.core.quartz.service.model.periodicidade.dto;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for RRuleValidator.
 * <p>
 * Tests RFC 5545 recurrence rule validation.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("RRuleValidator Tests")
class RRuleValidatorTestCore extends CoreBaseUnitTest {

    // RRULE Validation Tests

    @Test
    @DisplayName("Should validate RRULE with DAILY frequency")
    void shouldValidateRRuleWithDailyFrequency() {
        // Arrange
        String rrule = "FREQ=DAILY";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate RRULE with WEEKLY frequency")
    void shouldValidateRRuleWithWeeklyFrequency() {
        // Arrange
        String rrule = "FREQ=WEEKLY";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate RRULE with MONTHLY frequency")
    void shouldValidateRRuleWithMonthlyFrequency() {
        // Arrange
        String rrule = "FREQ=MONTHLY";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate RRULE with YEARLY frequency")
    void shouldValidateRRuleWithYearlyFrequency() {
        // Arrange
        String rrule = "FREQ=YEARLY";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate RRULE with additional parameters")
    void shouldValidateRRuleWithAdditionalParameters() {
        // Arrange
        String rrule = "FREQ=DAILY;INTERVAL=2;COUNT=10";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate RRULE with prefix")
    void shouldValidateRRuleWithPrefix() {
        // Arrange
        String rrule = "RRULE:FREQ=DAILY";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should reject null RRULE")
    void shouldRejectNullRRule() {
        // Arrange
        String rrule = null;

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject empty RRULE")
    void shouldRejectEmptyRRule() {
        // Arrange
        String rrule = "";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject blank RRULE")
    void shouldRejectBlankRRule() {
        // Arrange
        String rrule = "   ";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject RRULE missing FREQ parameter")
    void shouldRejectRRuleMissingFreqParameter() {
        // Arrange
        String rrule = "INTERVAL=2";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject RRULE with invalid frequency")
    void shouldRejectRRuleWithInvalidFrequency() {
        // Arrange
        String rrule = "FREQ=HOURLY";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    // EXRULE Validation Tests

    @Test
    @DisplayName("Should validate EXRULE")
    void shouldValidateExRule() {
        // Arrange
        String exrule = "FREQ=WEEKLY";

        // Act
        boolean isValid = RRuleValidator.isValidExRule(exrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate EXRULE with prefix")
    void shouldValidateExRuleWithPrefix() {
        // Arrange
        String exrule = "EXRULE:FREQ=WEEKLY";

        // Act
        boolean isValid = RRuleValidator.isValidExRule(exrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should reject null EXRULE")
    void shouldRejectNullExRule() {
        // Arrange
        String exrule = null;

        // Act
        boolean isValid = RRuleValidator.isValidExRule(exrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject empty EXRULE")
    void shouldRejectEmptyExRule() {
        // Arrange
        String exrule = "";

        // Act
        boolean isValid = RRuleValidator.isValidExRule(exrule);

        // Assert
        assertThat(isValid).isFalse();
    }

    // Parameter Validation Tests

    @Test
    @DisplayName("Should validate FREQ parameter")
    void shouldValidateFreqParameter() {
        // Arrange
        String paramName = "FREQ";
        String value = "DAILY";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate INTERVAL parameter")
    void shouldValidateIntervalParameter() {
        // Arrange
        String paramName = "INTERVAL";
        String value = "2";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate COUNT parameter")
    void shouldValidateCountParameter() {
        // Arrange
        String paramName = "COUNT";
        String value = "10";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYDAY parameter")
    void shouldValidateByDayParameter() {
        // Arrange
        String paramName = "BYDAY";
        String value = "MO,WE,FR";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYMONTHDAY parameter")
    void shouldValidateByMonthDayParameter() {
        // Arrange
        String paramName = "BYMONTHDAY";
        String value = "1,15,31";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYMONTH parameter")
    void shouldValidateByMonthParameter() {
        // Arrange
        String paramName = "BYMONTH";
        String value = "1,6,12";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYSETPOS parameter")
    void shouldValidateBySetPosParameter() {
        // Arrange
        String paramName = "BYSETPOS";
        String value = "1,-1";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate WKST parameter")
    void shouldValidateWkstParameter() {
        // Arrange
        String paramName = "WKST";
        String value = "MO";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYYEARDAY parameter")
    void shouldValidateByYearDayParameter() {
        // Arrange
        String paramName = "BYYEARDAY";
        String value = "1,180,365";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYWEEKNO parameter")
    void shouldValidateByWeekNoParameter() {
        // Arrange
        String paramName = "BYWEEKNO";
        String value = "1,26,53";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYHOUR parameter")
    void shouldValidateByHourParameter() {
        // Arrange
        String paramName = "BYHOUR";
        String value = "0,12,23";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYMINUTE parameter")
    void shouldValidateByMinuteParameter() {
        // Arrange
        String paramName = "BYMINUTE";
        String value = "0,30,59";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate BYSECOND parameter")
    void shouldValidateBySecondParameter() {
        // Arrange
        String paramName = "BYSECOND";
        String value = "0,30,59";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate UNTIL parameter")
    void shouldValidateUntilParameter() {
        // Arrange
        String paramName = "UNTIL";
        String value = "20231231T235959Z";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should reject null parameter values")
    void shouldRejectNullParameterValues() {
        // Arrange
        String paramName = null;
        String value = null;

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should accept unknown parameter")
    void shouldAcceptUnknownParameter() {
        // Arrange
        String paramName = "UNKNOWN";
        String value = "value";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    // Validation Message Tests

    @Test
    @DisplayName("Should return null for valid RRULE")
    void shouldReturnNullForValidRRule() {
        // Arrange
        String rrule = "FREQ=DAILY";

        // Act
        String message = RRuleValidator.getValidationMessage(rrule);

        // Assert
        assertThat(message).isNull();
    }

    @Test
    @DisplayName("Should return error message for null RRULE")
    void shouldReturnErrorMessageForNullRRule() {
        // Arrange
        String rrule = null;

        // Act
        String message = RRuleValidator.getValidationMessage(rrule);

        // Assert
        assertThat(message).isEqualTo("RRULE não pode ser vazio");
    }

    @Test
    @DisplayName("Should return error message for empty RRULE")
    void shouldReturnErrorMessageForEmptyRRule() {
        // Arrange
        String rrule = "";

        // Act
        String message = RRuleValidator.getValidationMessage(rrule);

        // Assert
        assertThat(message).isEqualTo("RRULE não pode ser vazio");
    }

    @Test
    @DisplayName("Should return error message for missing FREQ")
    void shouldReturnErrorMessageForMissingFreq() {
        // Arrange
        String rrule = "INTERVAL=2";

        // Act
        String message = RRuleValidator.getValidationMessage(rrule);

        // Assert
        assertThat(message).isEqualTo("RRULE deve conter parâmetro FREQ");
    }

    @Test
    @DisplayName("Should return error message for invalid format")
    void shouldReturnErrorMessageForInvalidFormat() {
        // Arrange
        String rrule = "FREQ=HOURLY";

        // Act
        String message = RRuleValidator.getValidationMessage(rrule);

        // Assert
        assertThat(message).isEqualTo("Formato de RRULE inválido");
    }

    // Edge Cases

    @Test
    @DisplayName("Should handle case insensitive frequency")
    void shouldHandleCaseInsensitiveFrequency() {
        // Arrange
        String rrule = "freq=daily";

        // Act
        boolean isValid = RRuleValidator.isValidRRule(rrule);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should handle BYDAY with offsets")
    void shouldHandleByDayWithOffsets() {
        // Arrange
        String paramName = "BYDAY";
        String value = "2MO,-1FR";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should reject zero INTERVAL")
    void shouldRejectZeroInterval() {
        // Arrange
        String paramName = "INTERVAL";
        String value = "0";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject negative INTERVAL")
    void shouldRejectNegativeInterval() {
        // Arrange
        String paramName = "INTERVAL";
        String value = "-1";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject non-numeric INTERVAL")
    void shouldRejectNonNumericInterval() {
        // Arrange
        String paramName = "INTERVAL";
        String value = "abc";

        // Act
        boolean isValid = RRuleValidator.isValidParameter(paramName, value);

        // Assert
        assertThat(isValid).isFalse();
    }
}
