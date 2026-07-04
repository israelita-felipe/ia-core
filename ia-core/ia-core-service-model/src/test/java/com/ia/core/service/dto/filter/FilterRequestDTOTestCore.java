package com.ia.core.service.dto.filter;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FilterRequestDTO")
class FilterRequestDTOTestCore extends CoreBaseUnitTest {

    @Nested
    @DisplayName("construtor padrão")
    class ConstrutorPadrao {

        @Test
        @DisplayName("CT001 - Deve criar filtro com campos padrão")
        void deveCriarFiltroCamposPadrao() {
            // Arrange
            String key = "nome";
            OperatorDTO operator = OperatorDTO.EQUAL;
            String value = "João";

            // Act
            FilterRequestDTO filter = FilterRequestDTO.builder()
                .key(key)
                .operator(operator)
                .value(value)
                .build();

            // Assert
            assertThat(filter.getKey()).isEqualTo(key);
            assertThat(filter.getOperator()).isEqualTo(operator);
            assertThat(filter.getValue()).isEqualTo(value);
            assertThat(filter.isNegate()).isFalse();
        }
    }

    @Nested
    @DisplayName("negate")
    class Negate {

        @Test
        @DisplayName("CT002 - Deve configurar negação")
        void deveConfigurarNegacao() {
            // Arrange
            String key = "nome";
            OperatorDTO operator = OperatorDTO.EQUAL;
            String value = "João";

            // Act
            FilterRequestDTO filter = FilterRequestDTO.builder()
                .key(key)
                .operator(operator)
                .value(value)
                .negate(true)
                .build();

            // Assert
            assertThat(filter.isNegate()).isTrue();
        }
    }

    @Nested
    @DisplayName("fieldType")
    class FieldType {

        @Test
        @DisplayName("CT003 - Deve configurar tipo de campo")
        void deveConfigurarTipoCampo() {
            // Arrange
            String key = "idade";
            OperatorDTO operator = OperatorDTO.GREATER_THAN;
            Integer value = 10;
            com.ia.core.model.filter.FieldType fieldType = com.ia.core.model.filter.FieldType.INTEGER;

            // Act
            FilterRequestDTO filter = FilterRequestDTO.builder()
                .key(key)
                .operator(operator)
                .value(value)
                .fieldType(fieldType)
                .build();

            // Assert
            assertThat(filter.getFieldType()).isEqualTo(fieldType);
        }
    }

    @Nested
    @DisplayName("CAMPOS")
    class Campos {

        @Test
        @DisplayName("CT004 - Deve ter constante PROPRIEDADE")
        void deveTerConstantePropriedade() {
            // Act & Assert
            assertThat(FilterRequestDTO.CAMPOS.PROPRIEDADE).isEqualTo("key");
        }

        @Test
        @DisplayName("CT005 - Deve ter constante OPERACAO")
        void deveTerConstanteOperacao() {
            // Act & Assert
            assertThat(FilterRequestDTO.CAMPOS.OPERACAO).isEqualTo("operator");
        }

        @Test
        @DisplayName("CT006 - Deve ter constante TIPO")
        void deveTerConstanteTipo() {
            // Act & Assert
            assertThat(FilterRequestDTO.CAMPOS.TIPO).isEqualTo("fieldType");
        }

        @Test
        @DisplayName("CT007 - Deve ter constante VALOR")
        void deveTerConstanteValor() {
            // Act & Assert
            assertThat(FilterRequestDTO.CAMPOS.VALOR).isEqualTo("value");
        }
    }

    @Nested
    @DisplayName("valor nulo")
    class ValorNulo {

        @Test
        @DisplayName("CT009 - Deve aceitar valor nulo")
        void deveAceitarValorNulo() {
            // Arrange
            String key = "nome";
            OperatorDTO operator = OperatorDTO.EQUAL;

            // Act
            FilterRequestDTO filter = FilterRequestDTO.builder()
                .key(key)
                .operator(operator)
                .value(null)
                .build();

            // Assert
            assertThat(filter.getValue()).isNull();
        }
    }

    @Nested
    @DisplayName("operador IS_NULL")
    class OperadorIsNull {

        @Test
        @DisplayName("CT010 - Deve criar filtro com EQUAL e valor nulo")
        void deveCriarFiltroEqualValorNulo() {
            // Arrange
            String key = "nome";
            OperatorDTO operator = OperatorDTO.EQUAL;

            // Act
            FilterRequestDTO filter = FilterRequestDTO.builder()
                .key(key)
                .operator(operator)
                .value(null)
                .build();

            // Assert
            assertThat(filter.getOperator()).isEqualTo(OperatorDTO.EQUAL);
            assertThat(filter.getValue()).isNull();
        }
    }

    @Nested
    @DisplayName("cloneObject")
    class CloneObject {

        @Test
        @DisplayName("CT011 - Deve criar cópia do objeto")
        void deveCriarCopia() {
            // Arrange
            FilterRequestDTO original = FilterRequestDTO.builder()
                .key("nome")
                .operator(OperatorDTO.EQUAL)
                .value("João")
                .fieldType(com.ia.core.model.filter.FieldType.STRING)
                .negate(true)
                .build();

            // Act
            FilterRequestDTO clone = original.cloneObject();

            // Assert
            assertThat(clone).isNotSameAs(original);
            assertThat(clone.getKey()).isEqualTo(original.getKey());
            assertThat(clone.getOperator()).isEqualTo(original.getOperator());
            assertThat(clone.getValue()).isEqualTo(original.getValue());
            assertThat(clone.getFieldType()).isEqualTo(original.getFieldType());
            assertThat(clone.isNegate()).isEqualTo(original.isNegate());
        }
    }

    @Nested
    @DisplayName("toBuilder")
    class ToBuilder {

        @Test
        @DisplayName("CT012 - Deve criar builder com valores atuais")
        void deveCriarBuilderComValoresAtuais() {
            // Arrange
            FilterRequestDTO original = FilterRequestDTO.builder()
                .key("nome")
                .operator(OperatorDTO.EQUAL)
                .value("João")
                .build();

            // Act
            FilterRequestDTO modified = original.toBuilder()
                .negate(true)
                .build();

            // Assert
            assertThat(modified.getKey()).isEqualTo(original.getKey());
            assertThat(modified.getOperator()).isEqualTo(original.getOperator());
            assertThat(modified.getValue()).isEqualTo(original.getValue());
            assertThat(modified.isNegate()).isTrue();
        }
    }

}
