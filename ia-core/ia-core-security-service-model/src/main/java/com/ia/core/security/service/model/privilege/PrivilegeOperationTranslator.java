package com.ia.core.security.service.model.privilege;

/**
 * Translator para PrivilegeOperation.
 * 
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio privilege operation translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
