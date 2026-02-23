package com.ia.core.security.service.model.role;

import com.ia.core.security.service.model.privilege.PrivilegeOperationTranslator;

/**
 * Translator para RolePrivilege.
 * 
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class RolePrivilegeTranslator {

  public static final class HELP {
    public static final String ROLE_PRIVILEGE = "role.privilege.help";
    public static final String PRIVILEGE = "role.privilege.help.privilege";
    public static final String OPERATIONS = "role.privilege.help.operations";
  }

  public static final String ROLE_PRIVILEGE_CLASS = RolePrivilegeDTO.class.getCanonicalName();
  public static final String ROLE_PRIVILEGE = "role.privilege";
  public static final String PRIVILEGE = "role.privilege.privilege";
  public static final String OPERATIONS = "role.privilege.operations";

  public static final class VALIDATION {
    public static final String PRIVILEGE_REQUIRED = "validation.role.privilege.privilege.required";
  }
}
