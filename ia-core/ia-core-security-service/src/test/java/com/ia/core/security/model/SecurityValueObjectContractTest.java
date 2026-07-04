package com.ia.core.security.model;

import com.ia.core.security.model.authentication.JwtToken;
import com.ia.core.security.model.authentication.TokenValidationResult;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Security value object contracts")
class SecurityValueObjectContractTest {

  @Nested
  @DisplayName("JwtToken")
  class JwtTokenTests {

    @Test
    @DisplayName("Deve criar token com datas padrão e valor imutável")
    void deveCriarTokenComDatasPadraoEValorImutavel() {
      JwtToken token = JwtToken.from("token.valor.assinatura");

      assertThat(token.getValue()).isEqualTo("token.valor.assinatura");
      assertThat(token.getIssuedAt()).isNotNull();
      assertThat(token.getExpiration()).isAfterOrEqualTo(token.getIssuedAt());
      assertThat(token.isExpiringSoon(JwtToken.DEFAULT_EXPIRATION_SECONDS + 1)).isTrue();
      assertThat(token.toString()).startsWith("JwtToken[prefix=token.va");
    }

    @Test
    @DisplayName("Deve criar token com datas explícitas")
    void deveCriarTokenComDatasExplicitas() {
      Instant issuedAt = Instant.parse("2026-01-01T00:00:00Z");
      Instant expiration = issuedAt.plusSeconds(30);

      JwtToken token = JwtToken.withDates("token.valor.assinatura", issuedAt, expiration);

      assertThat(token.getIssuedAt()).isEqualTo(issuedAt);
      assertThat(token.getExpiration()).isEqualTo(expiration);
      assertThat(token.getSecondsUntilExpiration()).isZero();
      assertThat(token.isExpired()).isTrue();
    }

    @Test
    @DisplayName("Deve rejeitar token vazio")
    void deveRejeitarTokenVazio() {
      assertThatThrownBy(() -> JwtToken.from(" "))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Token JWT");
    }

    @Test
    @DisplayName("Deve comparar por valor")
    void deveCompararPorValor() {
      assertThat(JwtToken.from("abc.def.ghi"))
          .isEqualTo(JwtToken.from("abc.def.ghi"))
          .hasSameHashCodeAs(JwtToken.from("abc.def.ghi"));
    }
  }

  @Nested
  @DisplayName("TokenValidationResult")
  class TokenValidationResultTests {

    @Test
    @DisplayName("Deve representar sucesso")
    void deveRepresentarSucesso() {
      TokenValidationResult result = TokenValidationResult.success();

      assertThat(result.isValid()).isTrue();
      assertThat(result.isInvalid()).isFalse();
      assertThat(result.getErrorMessage()).isEmpty();
      assertThat(result.getErrorCode()).isNull();
      assertThat(result.getDetails()).isEmpty();
    }

    @Test
    @DisplayName("Deve representar falha com detalhes")
    void deveRepresentarFalhaComDetalhes() {
      Map<String, Object> details = Map.of("reason", "signature");

      TokenValidationResult result = TokenValidationResult.failure("Token inválido", "TOKEN_INVALID", details);

      assertThat(result.isInvalid()).isTrue();
      assertThat(result.getErrorMessage()).contains("Token inválido");
      assertThat(result.getErrorCode()).isEqualTo("TOKEN_INVALID");
      assertThat(result.getDetail("reason")).contains("signature");
      assertThat(result.getDetails()).isUnmodifiable();
    }

    @Test
    @DisplayName("Deve combinar resultados inválidos")
    void deveCombinarResultadosInvalidos() {
      TokenValidationResult first = TokenValidationResult.failure("primeiro", "A");
      TokenValidationResult second = TokenValidationResult.failure("segundo", "B");

      TokenValidationResult combined = first.combine(second);

      assertThat(combined.isInvalid()).isTrue();
      assertThat(combined.getErrorMessage()).contains("primeiro; segundo");
      assertThat(combined.getErrorCode()).isEqualTo("A");
    }
  }

  @Nested
  @DisplayName("OperationEnum")
  class OperationEnumTests {

    @Test
    @DisplayName("Deve mapear operações CRUD")
    void deveMapearOperacoesCrud() {
      assertThat(OperationEnum.CREATE.value()).isEqualTo("CREATE");
      assertThat(OperationEnum.READ.value()).isEqualTo("READ");
      assertThat(OperationEnum.UPDATE.value()).isEqualTo("UPDATE");
      assertThat(OperationEnum.DELETE.value()).isEqualTo("DELETE");
      assertThat(OperationEnum.OTHER_VALUE).isEqualTo("OTHER");
    }

    @Test
    @DisplayName("Deve implementar Operation")
    void deveImplementarOperation() {
      assertThat(OperationEnum.CREATE).isInstanceOf(com.ia.core.security.model.functionality.Operation.class);
    }
  }

  @Nested
  @DisplayName("PrivilegeType")
  class PrivilegeTypeTests {

    @Test
    @DisplayName("Deve converter código válido")
    void deveConverterCodigoValido() {
      assertThat(PrivilegeType.of(1)).isEqualTo(PrivilegeType.SYSTEM);
      assertThat(PrivilegeType.of(2)).isEqualTo(PrivilegeType.USER);
    }

    @Test
    @DisplayName("Deve rejeitar código inválido")
    void deveRejeitarCodigoInvalido() {
      assertThatThrownBy(() -> PrivilegeType.of(99))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Código de privilégio inválido");
    }

    @Test
    @DisplayName("Deve expor operações por tipo")
    void deveExporOperacoesPorTipo() {
      assertThat(PrivilegeType.SYSTEM.getOperations())
          .containsExactlyInAnyOrderElementsOf(Set.of(OperationEnum.values()));
      assertThat(PrivilegeType.USER.getOperations()).isEmpty();
    }
  }
}
