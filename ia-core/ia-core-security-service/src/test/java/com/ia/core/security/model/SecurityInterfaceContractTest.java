package com.ia.core.security.model;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Security interface contracts")
class SecurityInterfaceContractTest {

  @Test
  @DisplayName("AuthenticationResponse deve ser implementada por resposta JWT")
  void authenticationResponseDeveSerImplementadaPorRespostaJwt() {
    assertThat(new JwtAuthenticationResponseDTO()).isInstanceOf(AuthenticationResponse.class);
  }

  @Test
  @DisplayName("Functionality deve ordenar por nome com null safety")
  void functionalityDeveOrdenarPorNomeComNullSafety() {
    Functionality first = new TestFunctionality("B", null, Set.of());
    Functionality second = new TestFunctionality("A", null, Set.of());
    Functionality nullName = new TestFunctionality(null, null, Set.of());

    assertThat(first.compareTo(second)).isPositive();
    assertThat(second.compareTo(first)).isNegative();
    assertThat(first.compareTo(nullName)).isPositive();
    assertThat(nullName.compareTo(first)).isNegative();
    assertThat(first.compareTo(first)).isZero();
  }

  @Test
  @DisplayName("Operation deve expor valor da operação")
  void operationDeveExporValorDaOperacao() {
    Operation operation = OperationEnum.UPDATE;

    assertThat(operation.value()).isEqualTo("UPDATE");
  }

  private static class TestFunctionality implements Functionality {
    private final String name;
    private final PrivilegeType type;
    private final Set<String> values;

    private TestFunctionality(String name, PrivilegeType type, Set<String> values) {
      this.name = name;
      this.type = type;
      this.values = values;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public PrivilegeType getType() {
      return type;
    }

    @Override
    public Set<String> getValues() {
      return values;
    }
  }
}
