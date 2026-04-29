package com.ia.core.security.service.model.privilege;

/**
 * Translator para PrivilegeOperationContext.
 * 
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio privilege operation context translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationContextTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class PrivilegeOperationContextTranslator {

  public static final class HELP {
    public static final String PRIVILEGE_OPERATION_CONTEXT = "privilege.operation.context.help";
    public static final String CONTEXT_KEY = "privilege.operation.context.help.contextKey";
    public static final String VALUES = "privilege.operation.context.help.values";
  }

  public static final String PRIVILEGE_OPERATION_CONTEXT_CLASS = PrivilegeOperationContextDTO.class
      .getCanonicalName();
  public static final String PRIVILEGE_OPERATION_CONTEXT = "privilege.operation.context";
  public static final String CONTEXT_KEY = "privilege.operation.context.contextKey";
  public static final String VALUES = "privilege.operation.context.values";
}
