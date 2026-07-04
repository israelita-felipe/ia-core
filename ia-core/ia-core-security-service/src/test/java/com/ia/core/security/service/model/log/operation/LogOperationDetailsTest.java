package com.ia.core.security.service.model.log.operation;

import com.ia.core.security.model.functionality.OperationEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LogOperationDetails")
class LogOperationDetailsTest {

  @Test
  @DisplayName("Deve calcular diff de criação")
  void deveCalcularDiffDeCriacao() {
    LogOperationDTO operation = LogOperationDTO.builder()
        .userName("usuario")
        .userCode("USR")
        .type("User")
        .valueId(1L)
        .operation(OperationEnum.CREATE)
        .newValue("{\"name\":\"João\",\"enabled\":true}")
        .build();

    LogOperationDetails details = new LogOperationDetails(operation);

    assertThat(details.getAllProperties()).containsExactlyInAnyOrder("name", "enabled");
    assertThat(details.getDiffProperties()).containsExactlyInAnyOrder("name", "enabled");
    assertThat(details.getDiffNewValue()).containsEntry("name", "João").containsEntry("enabled", true);
    assertThat(details.getDiffOldValue()).isEmpty();
    assertThat(details.getItens()).hasSize(2);
  }

  @Test
  @DisplayName("Deve calcular diff de atualização")
  void deveCalcularDiffDeAtualizacao() {
    LogOperationDTO operation = LogOperationDTO.builder()
        .userName("usuario")
        .userCode("USR")
        .type("User")
        .valueId(1L)
        .operation(OperationEnum.UPDATE)
        .oldValue("{\"name\":\"João\",\"enabled\":true}")
        .newValue("{\"name\":\"José\",\"enabled\":true,\"email\":\"jose@example.com\"}")
        .build();

    LogOperationDetails details = new LogOperationDetails(operation);

    assertThat(details.getDiffProperties()).containsExactlyInAnyOrder("name", "email");
    assertThat(details.getDiffOldValue()).containsEntry("name", "João");
    assertThat(details.getDiffNewValue()).containsEntry("name", "José").containsEntry("email", "jose@example.com");
    assertThat(details.getItens()).extracting(OperationItemDetails::getProperty)
        .containsExactlyInAnyOrder("name", "email");
  }

  @Test
  @DisplayName("Deve calcular diff de exclusão")
  void deveCalcularDiffDeExclusao() {
    LogOperationDTO operation = LogOperationDTO.builder()
        .userName("usuario")
        .userCode("USR")
        .type("User")
        .valueId(1L)
        .operation(OperationEnum.DELETE)
        .oldValue("{\"name\":\"João\"}")
        .build();

    LogOperationDetails details = new LogOperationDetails(operation);

    assertThat(details.getDiffProperties()).containsExactly("name");
    assertThat(details.getDiffOldValue()).containsEntry("name", "João");
    assertThat(details.getDiffNewValue()).isEmpty();
  }

  @Test
  @DisplayName("Deve clonar detalhes")
  void deveClonarDetalhes() {
    LogOperationDTO operation = LogOperationDTO.builder()
        .userName("usuario")
        .userCode("USR")
        .type("User")
        .valueId(1L)
        .operation(OperationEnum.CREATE)
        .newValue("{\"name\":\"João\"}")
        .build();
    LogOperationDetails details = new LogOperationDetails(operation);

    LogOperationDetails cloned = details.cloneObject();

    assertThat(cloned).isNotSameAs(details);
    assertThat(cloned.getLogOperation()).isSameAs(details.getLogOperation());
  }
}
