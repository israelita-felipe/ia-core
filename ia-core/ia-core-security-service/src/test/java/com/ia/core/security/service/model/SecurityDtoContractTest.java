package com.ia.core.security.service.model;

import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordResetDTO;
import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.test.support.SecurityTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.ia.core.security.test.support.SecurityTestSupport.assertCampos;
import static com.ia.core.security.test.support.SecurityTestSupport.assertDtoContract;
import static com.ia.core.security.test.support.SecurityTestSupport.assertPropertyFilters;

@DisplayName("Security DTO contracts")
class SecurityDtoContractTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource("dtoContracts")
  void shouldExposeDtoContract(Class<?> type, List<String> fields, List<String> propertyFilters) throws Exception {
    assertDtoContract(type, fields);
    assertPropertyFilters(type, propertyFilters);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("camposContracts")
  void shouldExposeCamposConstants(Class<?> type, List<String> fields) throws Exception {
    assertCampos(type, fields);
  }

 static Stream<Arguments> dtoContracts() {
   return Stream.of(
       Arguments.of(JwtAuthenticationResponseDTO.class, List.of("token", "refreshToken"), List.of()),
       Arguments.of(LogOperationDTO.class, List.of("userName", "userCode", "type", "valueId", "oldValue", "newValue", "dateTimeOperation", "operation"), List.of("type", "userCode")),
       Arguments.of(PrivilegeDTO.class, List.of("name", "type", "values"), List.of("name")),
        Arguments.of(PrivilegeOperationContextDTO.class, List.of("contextKey", "values"), List.of()),
        Arguments.of(PrivilegeOperationDTO.class, List.of("operation", "context"), List.of()),
        Arguments.of(RoleDTO.class, List.of("name", "users", "privileges"), List.of("name")),
        Arguments.of(RolePrivilegeDTO.class, List.of("privilege", "operations"), List.of()),
        Arguments.of(UserDTO.class, List.of("userName", "userCode", "password", "enabled", "accountNotExpired", "accountNotLocked", "credentialsNotExpired", "privileges", "roles"), List.of("userName", "userCode")),
        Arguments.of(UserPasswordChangeDTO.class, List.of("userCode", "oldPassword", "newPassword"), List.of()),
        Arguments.of(UserPasswordResetDTO.class, List.of("userCode", "userCodeRequester"), List.of()),
        Arguments.of(UserPrivilegeDTO.class, List.of("privilege", "operations"), List.of()),
        Arguments.of(UserRoleDTO.class, List.of("name", "privileges"), List.of("name"))
    );
  }

 static Stream<Arguments> camposContracts() {
   return Stream.of(
       Arguments.of(LogOperationDTO.class, List.of("id", "version", "userCode", "operation", "type", "dateTimeOperation", "userName", "valueId", "oldValue", "newValue")),
       Arguments.of(PrivilegeDTO.class, List.of("id", "version", "name", "type", "values")),
        Arguments.of(PrivilegeOperationContextDTO.class, List.of("id", "version", "contextKey", "values")),
        Arguments.of(PrivilegeOperationDTO.class, List.of("id", "version", "operation", "context")),
        Arguments.of(RoleDTO.class, List.of("id", "version", "name", "users", "privileges")),
        Arguments.of(RolePrivilegeDTO.class, List.of("id", "version", "privilege", "operations")),
        Arguments.of(UserDTO.class, List.of("id", "version", "userName", "userCode", "password", "enabled", "accountNotExpired", "accountNotLocked", "credentialsNotExpired", "privileges", "roles")),
        Arguments.of(UserPrivilegeDTO.class, List.of("id", "version", "privilege", "operations")),
        Arguments.of(UserRoleDTO.class, List.of("id", "version", "name", "privileges"))
    );
  }
}
