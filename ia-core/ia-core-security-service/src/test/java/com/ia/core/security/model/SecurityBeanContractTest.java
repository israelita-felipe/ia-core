package com.ia.core.security.model;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeOperation;
import com.ia.core.security.model.privilege.PrivilegeOperationContext;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.model.role.RolePrivilege;
import com.ia.core.security.model.user.User;
import com.ia.core.security.model.user.UserPrivilege;
import com.ia.core.security.test.support.SecurityTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.ia.core.security.test.support.SecurityTestSupport.assertBeanContract;

@DisplayName("Security model bean contracts")
class SecurityBeanContractTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource("beanContracts")
  void shouldExposeBeanContract(Class<?> type, List<String> fields) throws Exception {
    assertBeanContract(type, fields);
  }

  static Stream<Arguments> beanContracts() {
    return Stream.of(
        Arguments.of(AuthenticationRequest.class, List.of("codUsuario", "senha", "refreshToken")),
        Arguments.of(LogOperation.class, List.of("userName", "userCode", "valueId", "type", "oldValue", "newValue", "dateTimeOperation", "operation")),
        Arguments.of(Privilege.class, List.of("name", "type", "values")),
        Arguments.of(PrivilegeOperation.class, List.of("operation", "context")),
        Arguments.of(PrivilegeOperationContext.class, List.of("privilegeOperation", "contextKey", "values")),
        Arguments.of(Role.class, List.of("name", "users", "privileges")),
        Arguments.of(RolePrivilege.class, List.of("privilege", "role", "operations")),
        Arguments.of(User.class, List.of("userName", "userCode", "password", "enabled", "accountNotExpired", "accountNotLocked", "credentialsNotExpired", "roles", "privileges")),
        Arguments.of(UserPrivilege.class, List.of("privilege", "user", "operations"))
    );
  }
}
