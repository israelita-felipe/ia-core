package com.ia.core.model.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EnumUtils")
class EnumUtilsTest {

    @Nested
    @DisplayName("serialize")
    class Serialize {

        @Test
        @DisplayName("Deve serializar enum corretamente")
        void deveSerializarEnumCorretamente() {
            String result = EnumUtils.serialize(DayOfWeek.MONDAY);
            assertThat(result).isEqualTo("java.time.DayOfWeek_#_MONDAY");
        }

        @Test
        @DisplayName("Deve serializar diferentes valores do mesmo enum")
        void deveSerializarDiferentesValores() {
            String monday = EnumUtils.serialize(DayOfWeek.MONDAY);
            String friday = EnumUtils.serialize(DayOfWeek.FRIDAY);

            assertThat(monday).isNotEqualTo(friday);
            assertThat(monday).contains("MONDAY");
            assertThat(friday).contains("FRIDAY");
        }
    }

    @Nested
    @DisplayName("deserialize")
    class Deserialize {

        @Test
        @DisplayName("Deve desserializar enum corretamente")
        void deveDesserializarEnumCorretamente() throws ClassNotFoundException {
            DayOfWeek result = EnumUtils.deserialize("java.time.DayOfWeek_#_MONDAY");
            assertThat(result).isEqualTo(DayOfWeek.MONDAY);
        }

        @Test
        @DisplayName("Deve lançar NullPointerException para texto null")
        void deveLancarNullPointerExceptionParaNull() {
            assertThatThrownBy(() -> EnumUtils.deserialize(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para formato inválido")
        void deveLancarIllegalArgumentExceptionParaFormatoInvalido() {
            assertThatThrownBy(() -> EnumUtils.deserialize("formato_invalido"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Formato inválido");
        }

        @Test
        @DisplayName("Deve lançar ClassNotFoundException para classe inexistente")
        void deveLancarClassNotFoundExceptionParaClasseInexistente() {
            assertThatThrownBy(() -> EnumUtils.deserialize("com.inexistente.Classe_#_VALOR"))
                .isInstanceOf(ClassNotFoundException.class);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para valor de enum inexistente")
        void deveLancarIllegalArgumentExceptionParaValorInexistente() {
            assertThatThrownBy(() -> EnumUtils.deserialize("java.time.DayOfWeek_#_INEXISTENTE"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Valor de enum não encontrado");
        }
    }

    @Nested
    @DisplayName("roundtrip")
    class Roundtrip {

        @Test
        @DisplayName("Deve manter consistência entre serialize e deserialize")
        void deveManterConsistencia() throws ClassNotFoundException {
            DayOfWeek original = DayOfWeek.WEDNESDAY;
            String serialized = EnumUtils.serialize(original);
            DayOfWeek deserialized = EnumUtils.deserialize(serialized);
            assertThat(deserialized).isEqualTo(original);
        }
    }
}
