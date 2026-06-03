package com.ia.core.model.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("FormatUtils")
class FormatUtilsTest {

    @Nested
    @DisplayName("constantes")
    class Constantes {

        @Test
        @DisplayName("DATE deve ter formato dd/MM/yyyy")
        void dateDeveSerCorreto() {
            assertThat(FormatUtils.DATE).isEqualTo("dd/MM/yyyy");
        }

        @Test
        @DisplayName("TIME deve ter formato HH:mm:ss")
        void timeDeveSerCorreto() {
            assertThat(FormatUtils.TIME).isEqualTo("HH:mm:ss");
        }

        @Test
        @DisplayName("DATE_TIME_FULL deve combinar DATE e TIME com milissegundos")
        void dateTimeFullDeveSerCorreto() {
            assertThat(FormatUtils.DATE_TIME_FULL).isEqualTo("dd/MM/yyyy HH:mm:ss.SSS");
        }
    }

    @Nested
    @DisplayName("formatDateTime")
    class FormatDateTime {

        @Test
        @DisplayName("Deve formatar data corretamente no padrão brasileiro")
        void deveFormatarDataCorretamente() {
            LocalDateTime dt = LocalDateTime.of(2025, 6, 15, 10, 30, 45);
            String result = FormatUtils.formatDateTime("dd/MM/yyyy HH:mm:ss", dt);
            assertThat(result).isEqualTo("15/06/2025 10:30:45");
        }

        @Test
        @DisplayName("Deve formatar apenas data")
        void deveFormatarApenasData() {
            LocalDateTime dt = LocalDateTime.of(2025, 1, 5, 0, 0, 0);
            String result = FormatUtils.formatDateTime("dd/MM/yyyy", dt);
            assertThat(result).isEqualTo("05/01/2025");
        }

        @Test
        @DisplayName("Deve lançar NullPointerException para pattern null")
        void deveLancarNullPointerExceptionParaPatternNull() {
            assertThatThrownBy(() -> FormatUtils.formatDateTime(null, LocalDateTime.now()))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Deve lançar NullPointerException para dateTime null")
        void deveLancarNullPointerExceptionParaDateTimeNull() {
            assertThatThrownBy(() -> FormatUtils.formatDateTime("dd/MM/yyyy", null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("formatCurrency")
    class FormatCurrency {

        @Test
        @DisplayName("Deve formatar valor monetário")
        void deveFormatarValorMonetario() {
            String result = FormatUtils.formatCurrency(new BigDecimal("1234.56"));
            assertThat(result).isNotBlank();
            assertThat(result).contains("1");
        }

        @Test
        @DisplayName("Deve formatar zero")
        void deveFormatarZero() {
            String result = FormatUtils.formatCurrency(BigDecimal.ZERO);
            assertThat(result).isNotBlank();
        }

        @Test
        @DisplayName("Deve lançar NullPointerException para valor null")
        void deveLancarNullPointerExceptionParaNull() {
            assertThatThrownBy(() -> FormatUtils.formatCurrency(null))
                .isInstanceOf(NullPointerException.class);
        }
    }
}
