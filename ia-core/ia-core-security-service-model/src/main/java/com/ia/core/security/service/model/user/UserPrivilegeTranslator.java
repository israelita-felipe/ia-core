package com.ia.core.security.service.model.user;

/**
 * Translator para UserPrivilege.
 * 
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class UserPrivilegeTranslator {

  public static final class HELP {
    public static final String USER_PRIVILEGE = "user.privilege.help";
    public static final String PRIVILEGE = "user.privilege.help.privilege";
    public static final String OPERATIONS = "user.privilege.help.operations";
  }

  public static final String USER_PRIVILEGE_CLASS = UserPrivilegeDTO.class.getCanonicalName();
  public static final String USER_PRIVILEGE = "user.privilege";
  public static final String PRIVILEGE = "user.privilege.privilege";
  public static final String OPERATIONS = "user.privilege.operations";

  public static final class VALIDATION {
    public static final String PRIVILEGE_REQUIRED = "validation.user.privilege.privilege.required";
  }
}
