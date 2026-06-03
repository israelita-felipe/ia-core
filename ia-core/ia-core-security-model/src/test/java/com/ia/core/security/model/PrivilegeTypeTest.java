package com.ia.core.security.model;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PrivilegeType")
class PrivilegeTypeTest {

    @Nested
    @DisplayName("SYSTEM")
    class SystemType {

        @Test
        @DisplayName("Deve ter todas as operações CRUD")
        void deveTerTodasOperacoes() {
            assertThat(PrivilegeType.SYSTEM.getOperations())
                .containsExactlyInAnyOrder(OperationEnum.values());
        }
    }

    @Nested
    @DisplayName("USER")
    class UserType {

        @Test
        @DisplayName("Deve ter nenhuma operação por padrão")
        void deveNaoTerOperacoes() {
            assertThat(PrivilegeType.USER.getOperations()).isEmpty();
        }
    }

    @Nested
    @DisplayName("of")
    class OfMethod {

        @Test
        @DisplayName("Deve retornar SYSTEM para código 1")
        void deveRetornarSystemParaCodigo1() {
            assertThat(PrivilegeType.of(1)).isEqualTo(PrivilegeType.SYSTEM);
        }

        @Test
        @DisplayName("Deve retornar USER para código 2")
        void deveRetornarUserParaCodigo2() {
            assertThat(PrivilegeType.of(2)).isEqualTo(PrivilegeType.USER);
        }

        @Test
        @DisplayName("Deve lançar exceção para código inválido")
        void deveLancarExcecaoParaCodigoInvalido() {
            assertThatThrownBy(() -> PrivilegeType.of(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Código de privilégio inválido");
        }
    }

    @Nested
    @DisplayName("OperationEnum")
    class OperationEnumTest {

        @Test
        @DisplayName("Deve ter 4 operações CRUD")
        void deveTerQuatroOperacoes() {
            assertThat(OperationEnum.values()).hasSize(4);
        }

        @Test
        @DisplayName("CREATE deve retornar valor 'CREATE'")
        void createDeveRetornarValorCorreto() {
            assertThat(OperationEnum.CREATE.value()).isEqualTo("CREATE");
        }

        @Test
        @DisplayName("DELETE deve retornar valor 'DELETE'")
        void deleteDeveRetornarValorCorreto() {
            assertThat(OperationEnum.DELETE.value()).isEqualTo("DELETE");
        }

        @Test
        @DisplayName("READ deve retornar valor 'READ'")
        void readDeveRetornarValorCorreto() {
            assertThat(OperationEnum.READ.value()).isEqualTo("READ");
        }

        @Test
        @DisplayName("UPDATE deve retornar valor 'UPDATE'")
        void updateDeveRetornarValorCorreto() {
            assertThat(OperationEnum.UPDATE.value()).isEqualTo("UPDATE");
        }

        @Test
        @DisplayName("toString deve retornar value()")
        void toStringDeveRetornarValue() {
            assertThat(OperationEnum.CREATE.toString()).isEqualTo("CREATE");
        }
    }
}
