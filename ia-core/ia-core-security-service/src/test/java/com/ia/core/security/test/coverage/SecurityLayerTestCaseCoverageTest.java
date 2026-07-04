package com.ia.core.security.test.coverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("Security Layer Test Case Coverage")
class SecurityLayerTestCaseCoverageTest {

  private static final Path TEST_CASES = Path.of("src/test/resources/test-cases");
  private static final List<String> DTO_AND_MODEL_CLASSES = List.of(
      "AuthenticationResponse",
      "Functionality",
      "Operation",
      "JwtAuthenticationResponseDTO",
      "LogOperationDTO",
      "LogOperationDetails",
      "LogOperationSearchRequest",
      "OperationItemDetails",
      "PrivilegeDTO",
      "PrivilegeOperationContextDTO",
      "PrivilegeOperationDTO",
      "PrivilegeSearchRequest",
      "RoleDTO",
      "RolePrivilegeDTO",
      "RoleSearchRequest",
      "UserDTO",
      "UserPasswordChangeDTO",
      "UserPasswordResetDTO",
      "UserPrivilegeDTO",
      "UserRoleDTO",
      "UserSearchRequest",
      "AuthenticationRequest",
      "JwtToken",
      "TokenValidationResult",
      "OperationEnum",
      "LogOperation",
      "Privilege",
      "PrivilegeOperation",
      "PrivilegeOperationContext",
      "PrivilegeType",
      "Role",
      "RolePrivilege",
      "User",
      "UserPrivilege"
  );
  private static final List<String> STACK_LAYERS = List.of("Model", "Repository", "Mapper", "ServiceModel", "Service", "API", "View");

  @Test
  @DisplayName("Should have one Markdown test case for every Security DTO/model and layer")
  void shouldHaveOneMarkdownTestCaseForEverySecurityDtoModelAndLayer() {
    for (String targetClass : DTO_AND_MODEL_CLASSES) {
      for (String layer : expectedLayers(targetClass)) {
        Path file = TEST_CASES.resolve(targetClass + "-" + layer + "-Layer.md");
        assertThat(Files.isRegularFile(file))
            .as("Caso de teste ausente: " + file)
            .isTrue();
      }
    }
  }

  @Test
  @DisplayName("Should include ADR adherence section in every Security test case")
  void shouldIncludeAdrAdherenceSectionInEverySecurityTestCase() throws Exception {
    for (String targetClass : DTO_AND_MODEL_CLASSES) {
      for (String layer : expectedLayers(targetClass)) {
        Path file = TEST_CASES.resolve(targetClass + "-" + layer + "-Layer.md");
        String content = Files.readString(file);

        assertThat(content).contains("## Aderência a ADRs");
        assertThat(content).contains("### Matriz de conformidade");
        assertThat(content).contains("### Referências ADR");
      }
    }
  }

  private static List<String> expectedLayers(String targetClass) {
    if (targetClass.endsWith("DTO") || targetClass.endsWith("SearchRequest") || targetClass.endsWith("Details")) {
      return STACK_LAYERS;
    }
    return List.of("Model");
  }
}
