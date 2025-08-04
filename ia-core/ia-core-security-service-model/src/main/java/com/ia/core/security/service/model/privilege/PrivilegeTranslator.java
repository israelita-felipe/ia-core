package com.ia.core.security.service.model.privilege;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class PrivilegeTranslator {
  public static final class HELP {
    public static final String PRIVILEGE = "privilege.help";
    public static final String NOME = "privilege.help.name";
    public static final String ROLES = "privilege.help.roles";
  }
  public static final String PRIVILEGE_CLASS = PrivilegeDTO.class
      .getCanonicalName();
  public static final String PRIVILEGE = "privilege";
  public static final String NOME = "privilege.name";

  public static final String ROLES = "privilege.roles";
}
