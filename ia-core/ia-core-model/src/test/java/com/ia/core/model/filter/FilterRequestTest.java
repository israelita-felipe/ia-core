package com.ia.core.model.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("FilterRequest")
class FilterRequestTest {

    @Nested
    @DisplayName("builder")
    class Builder {

        @Test
        @DisplayName("Deve criar FilterRequest com todos os campos")
        void deveCriarComTodosOsCampos() {
            FilterRequest filter = FilterRequest.builder()
                .key("nome")
                .operator(Operator.LIKE)
                .fieldType(FieldType.STRING)
                .value("João")
                .negate(false)
                .build();

            assertThat(filter.getKey()).isEqualTo("nome");
            assertThat(filter.getOperator()).isEqualTo(Operator.LIKE);
            assertThat(filter.getFieldType()).isEqualTo(FieldType.STRING);
            assertThat(filter.getValue()).isEqualTo("João");
            assertThat(filter.isNegate()).isFalse();
        }

        @Test
        @DisplayName("Deve ter negate false por padrão")
        void deveSerNegateFalsePorPadrao() {
            FilterRequest filter = FilterRequest.builder()
                .key("campo")
                .operator(Operator.EQUAL)
                .fieldType(FieldType.STRING)
                .build();

            assertThat(filter.isNegate()).isFalse();
        }
    }

    @Nested
    @DisplayName("validate")
    class Validate {

        @Test
        @DisplayName("Deve validar filtro bem-formado sem erros")
        void deveValidarFiltroCorreto() {
            FilterRequest filter = FilterRequest.builder()
                .key("nome")
                .operator(Operator.EQUAL)
                .fieldType(FieldType.STRING)
                .build();

            filter.validate();
        }

        @Test
        @DisplayName("Deve lançar exceção para key null")
        void deveLancarExcecaoParaKeyNull() {
            FilterRequest filter = FilterRequest.builder()
                .key(null)
                .operator(Operator.EQUAL)
                .fieldType(FieldType.STRING)
                .build();

            assertThatThrownBy(filter::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("key");
        }

        @Test
        @DisplayName("Deve lançar exceção para key vazia")
        void deveLancarExcecaoParaKeyVazia() {
            FilterRequest filter = FilterRequest.builder()
                .key("   ")
                .operator(Operator.EQUAL)
                .fieldType(FieldType.STRING)
                .build();

            assertThatThrownBy(filter::validate)
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Deve lançar exceção para operator null")
        void deveLancarExcecaoParaOperatorNull() {
            FilterRequest filter = FilterRequest.builder()
                .key("nome")
                .operator(null)
                .fieldType(FieldType.STRING)
                .build();

            assertThatThrownBy(filter::validate)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("operator");
        }

        @Test
        @DisplayName("Deve lançar exceção para fieldType null")
        void deveLancarExcecaoParaFieldTypeNull() {
            FilterRequest filter = FilterRequest.builder()
                .key("nome")
                .operator(Operator.EQUAL)
                .fieldType(null)
                .build();

            assertThatThrownBy(filter::validate)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("fieldType");
        }
    }

    @Nested
    @DisplayName("getters e setters")
    class GettersSetters {

        @Test
        @DisplayName("Deve usar setters corretamente")
        void deveUsarSettersCorretamente() {
            FilterRequest filter = new FilterRequest();
            filter.setKey("email");
            filter.setOperator(Operator.LIKE);
            filter.setFieldType(FieldType.STRING);
            filter.setValue("test@example.com");
            filter.setNegate(true);

            assertThat(filter.getKey()).isEqualTo("email");
            assertThat(filter.getOperator()).isEqualTo(Operator.LIKE);
            assertThat(filter.getFieldType()).isEqualTo(FieldType.STRING);
            assertThat(filter.getValue()).isEqualTo("test@example.com");
            assertThat(filter.isNegate()).isTrue();
        }
    }
}
