package com.ia.core.security.service.model.user;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class UserTranslator {
  public static final class HELP {
    public static final String USER = "user.help";
    public static final String NOME = "user.help.userName";
    public static final String CODIGO = "user.help.userCode";
    public static final String SENHA = "user.help.password";
    public static final String HABILITADO = "user.help.enabled";
    public static final String CONTA_NAO_EXPIRADA = "user.help.accountNotExpired";
    public static final String CONTA_NAO_BLOQUEADA = "user.help.accountNotLocked";
    public static final String CREDENCIAIS_NAO_EXPIRADAS = "user.help.credentialsNotExpired";
  }
  public static final String USER_CLASS = UserDTO.class.getCanonicalName();
  public static final String USER = "user";
  public static final String NOME = "user.userName";
  public static final String CODIGO = "user.userCode";
  public static final String SENHA = "user.password";
  public static final String HABILITADO = "user.enabled";
  public static final String CONTA_NAO_EXPIRADA = "user.accountNotExpired";
  public static final String CONTA_NAO_BLOQUEADA = "user.accountNotLocked";

  public static final String CREDENCIAIS_NAO_EXPIRADAS = "user.credentialsNotExpired";

  public static final class VALIDATION {
    public static final String USER_NAME_REQUIRED = "validation.user.userName.required";
    public static final String USER_NAME_SIZE = "validation.user.userName.size";
    public static final String USER_CODE_REQUIRED = "validation.user.userCode.required";
    public static final String USER_CODE_SIZE = "validation.user.userCode.size";
    public static final String PASSWORD_SIZE = "validation.user.password.size";
  }
}
