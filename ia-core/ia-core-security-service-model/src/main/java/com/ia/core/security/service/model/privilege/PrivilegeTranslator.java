package com.ia.core.security.service.model.privilege;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio privilege translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class PrivilegeTranslator {
  public static final class HELP {
    public static final String PRIVILEGE = "privilege.help";
    public static final String NOME = "privilege.help.name";
    public static final String TYPE = "privilege.help.type";
    public static final String ROLES = "privilege.help.roles";
    public static final String VALUES = "privilege.help.values";
  }

  public static final String PRIVILEGE_CLASS = PrivilegeDTO.class
      .getCanonicalName();
  public static final String PRIVILEGE = "privilege";
  public static final String NOME = "privilege.name";
  public static final String TYPE = "privilege.type";
  public static final String ROLES = "privilege.roles";
  public static final String VALUES = "privilege.values";

  public static final class VALIDATION {
    public static final String NAME_REQUIRED = "validation.privilege.name.required";
    public static final String NAME_SIZE = "validation.privilege.name.size";
  }
}
