package com.ia.core.security.service.model.role;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio role translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RoleTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class RoleTranslator {
  public static final class HELP {
    public static final String ROLE = "role.help";
    public static final String NOME = "role.help.name";
    public static final String USERS = "role.help.users";
    public static final String PRIVILEGES = "role.help.privileges";
  }
  public static final String ROLE_CLASS = RoleDTO.class.getCanonicalName();
  public static final String ROLE = "role";
  public static final String NOME = "role.name";
  public static final String USERS = "role.users";

  public static final String PRIVILEGES = "role.privileges";

  public static final class VALIDATION {
    public static final String NAME_REQUIRED = "validation.role.name.required";
    public static final String NAME_SIZE = "validation.role.name.size";
  }
}
