package com.ia.core.model.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FieldType")
class FieldTypeTest {

    @Nested
    @DisplayName("BOOLEAN")
    class BooleanType {

        @Test
        @DisplayName("Deve retornar true para string 'true'")
        void deveRetornarTrueParaStringTrue() {
            assertThat(FieldType.BOOLEAN.parse("true")).isEqualTo(true);
        }

        @Test
        @DisplayName("Deve retornar false para string 'false'")
        void deveRetornarFalseParaStringFalse() {
            assertThat(FieldType.BOOLEAN.parse("false")).isEqualTo(false);
        }

        @Test
        @DisplayName("Deve retornar false para null")
        void deveRetornarFalseParaNull() {
            assertThat(FieldType.BOOLEAN.parse(null)).isEqualTo(false);
        }

        @Test
        @DisplayName("Deve retornar Boolean quando já é Boolean")
        void deveRetornarBooleanQuandoJaEBoolean() {
            assertThat(FieldType.BOOLEAN.parse(Boolean.TRUE)).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("CHAR")
    class CharType {

        @Test
        @DisplayName("Deve retornar primeiro caractere da string")
        void deveRetornarPrimeiroCaractere() {
            assertThat(FieldType.CHAR.parse("ABC")).isEqualTo('A');
        }

        @Test
        @DisplayName("Deve retornar null para string vazia")
        void deveRetornarNullParaStringVazia() {
            assertThat(FieldType.CHAR.parse("")).isNull();
        }

        @Test
        @DisplayName("Deve retornar Character quando já é Character")
        void deveRetornarCharacterQuandoJaE() {
            assertThat(FieldType.CHAR.parse('X')).isEqualTo('X');
        }
    }

    @Nested
    @DisplayName("DATE")
    class DateType {

        @Test
        @DisplayName("Deve fazer parse de data no formato brasileiro")
        void deveParseDataFormatoBrasileiro() {
            Object result = FieldType.DATE.parse("25/12/2025");
            assertThat(result).isEqualTo(LocalDate.of(2025, 12, 25));
        }

        @Test
        @DisplayName("Deve retornar valor original para formato inválido")
        void deveRetornarOriginalParaFormatoInvalido() {
            Object result = FieldType.DATE.parse("invalid-date");
            assertThat(result).isEqualTo("invalid-date");
        }
    }

    @Nested
    @DisplayName("TIME")
    class TimeType {

        @Test
        @DisplayName("Deve fazer parse de hora corretamente")
        void deveParseHoraCorretamente() {
            Object result = FieldType.TIME.parse("14:30:00");
            assertThat(result).isEqualTo(LocalTime.of(14, 30, 0));
        }

        @Test
        @DisplayName("Deve retornar valor original para formato inválido")
        void deveRetornarOriginalParaFormatoInvalido() {
            Object result = FieldType.TIME.parse("not-a-time");
            assertThat(result).isEqualTo("not-a-time");
        }
    }

    @Nested
    @DisplayName("DATE_TIME")
    class DateTimeType {

        @Test
        @DisplayName("Deve fazer parse de datetime completo")
        void deveParseDateTimeCompleto() {
            Object result = FieldType.DATE_TIME.parse("15/06/2025 10:30:45.000");
            assertThat(result).isEqualTo(LocalDateTime.of(2025, 6, 15, 10, 30, 45, 0));
        }

        @Test
        @DisplayName("Deve retornar valor original para formato inválido")
        void deveRetornarOriginalParaFormatoInvalido() {
            Object result = FieldType.DATE_TIME.parse("invalid");
            assertThat(result).isEqualTo("invalid");
        }
    }

    @Nested
    @DisplayName("STRING")
    class StringType {

        @Test
        @DisplayName("Deve retornar toString do valor")
        void deveRetornarToString() {
            assertThat(FieldType.STRING.parse("hello")).isEqualTo("hello");
        }

        @Test
        @DisplayName("Deve retornar string vazia para null")
        void deveRetornarStringVaziaParaNull() {
            assertThat(FieldType.STRING.parse(null)).isEqualTo("");
        }

        @Test
        @DisplayName("Deve converter número para string")
        void deveConverterNumeroParaString() {
            assertThat(FieldType.STRING.parse(42)).isEqualTo("42");
        }
    }

    @Nested
    @DisplayName("LONG")
    class LongType {

        @Test
        @DisplayName("Deve fazer parse de string para long")
        void deveParseStringParaLong() {
            assertThat(FieldType.LONG.parse("12345")).isEqualTo(12345L);
        }

        @Test
        @DisplayName("Deve retornar Long quando já é Long")
        void deveRetornarLongQuandoJaE() {
            assertThat(FieldType.LONG.parse(99L)).isEqualTo(99L);
        }

        @Test
        @DisplayName("Deve retornar 0L para valor inválido")
        void deveRetornarZeroParaValorInvalido() {
            assertThat(FieldType.LONG.parse("abc")).isEqualTo(0L);
        }
    }

    @Nested
    @DisplayName("INTEGER")
    class IntegerType {

        @Test
        @DisplayName("Deve fazer parse de string para integer")
        void deveParseStringParaInteger() {
            assertThat(FieldType.INTEGER.parse("42")).isEqualTo(42);
        }

        @Test
        @DisplayName("Deve retornar Integer quando já é Integer")
        void deveRetornarIntegerQuandoJaE() {
            assertThat(FieldType.INTEGER.parse(7)).isEqualTo(7);
        }

        @Test
        @DisplayName("Deve retornar 0 para valor inválido")
        void deveRetornarZeroParaValorInvalido() {
            assertThat(FieldType.INTEGER.parse("xyz")).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("DOUBLE")
    class DoubleType {

        @Test
        @DisplayName("Deve fazer parse de string para double")
        void deveParseStringParaDouble() {
            assertThat(FieldType.DOUBLE.parse("3.14")).isEqualTo(3.14);
        }

        @Test
        @DisplayName("Deve retornar Double quando já é Double")
        void deveRetornarDoubleQuandoJaE() {
            assertThat(FieldType.DOUBLE.parse(2.5)).isEqualTo(2.5);
        }

        @Test
        @DisplayName("Deve retornar 0.0 para valor inválido")
        void deveRetornarZeroParaValorInvalido() {
            assertThat(FieldType.DOUBLE.parse("not-number")).isEqualTo(0.0);
        }
    }

    @Nested
    @DisplayName("OBJECT")
    class ObjectType {

        @Test
        @DisplayName("Deve retornar valor sem conversão")
        void deveRetornarValorSemConversao() {
            Object obj = new Object();
            assertThat(FieldType.OBJECT.parse(obj)).isSameAs(obj);
        }

        @Test
        @DisplayName("Deve retornar null para null")
        void deveRetornarNullParaNull() {
            assertThat(FieldType.OBJECT.parse(null)).isNull();
        }
    }
}
