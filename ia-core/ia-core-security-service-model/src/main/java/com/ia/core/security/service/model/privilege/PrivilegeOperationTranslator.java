package com.ia.core.security.service.model.privilege;

/**
 * Translator para PrivilegeOperation.
 * 
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class PrivilegeOperationTranslator {

  public static final class HELP {
    public static final String PRIVILEGE_OPERATION = "privilege.operation.help";
    public static final String OPERATION = "privilege.operation.help.operation";
    public static final String CONTEXT = "privilege.operation.help.context";
  }

  public static final String PRIVILEGE_OPERATION_CLASS = PrivilegeOperationDTO.class
      .getCanonicalName();
  public static final String PRIVILEGE_OPERATION = "privilege.operation";
  public static final String OPERATION = "privilege.operation.operation";
  public static final String CONTEXT = "privilege.operation.context";
}
