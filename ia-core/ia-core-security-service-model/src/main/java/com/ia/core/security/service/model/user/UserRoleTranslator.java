package com.ia.core.security.service.model.user;

/**
 * Translator para UserRole.
 * 
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio user role translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserRoleTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class UserRoleTranslator {

  public static final class HELP {
    public static final String USER_ROLE = "user.role.help";
    public static final String NAME = "user.role.help.name";
    public static final String PRIVILEGES = "user.role.help.privileges";
  }

  public static final String USER_ROLE_CLASS = UserRoleDTO.class.getCanonicalName();
  public static final String USER_ROLE = "user.role";
  public static final String NAME = "user.role.name";
  public static final String PRIVILEGES = "user.role.privileges";

  public static final class VALIDATION {
    public static final String NAME_REQUIRED = "validation.user.role.name.required";
  }
}
