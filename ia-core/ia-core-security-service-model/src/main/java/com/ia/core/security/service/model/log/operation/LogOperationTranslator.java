package com.ia.core.security.service.model.log.operation;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio log operation translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LogOperationTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class LogOperationTranslator {
  public static final class HELP {
    public static final String LOG_OPERATION = "log.operation.help";
    public static final String USER_NAME = "log.operation.help.user.name";
    public static final String USER_CODE = "log.operation.help.user.code";
    public static final String TYPE = "log.operation.help.type";
    public static final String OLD_VALUE = "log.operation.help.old.value";
    public static final String NEW_VALUE = "log.operation.help.new.value";
    public static final String DATE_TIME = "log.operation.help.date.time";
    public static final String OPERATION = "log.operation.help.operation";
    public static final String PROPERTY = "log.operation.help.property";
  }
  public static final String LOG_OPERATION_CLASS = LogOperationDTO.class
      .getCanonicalName();
  public static final String LOG_OPERATION = "log.operation";
  public static final String USER_NAME = "log.operation.user.name";
  public static final String USER_CODE = "log.operation.user.code";
  public static final String TYPE = "log.operation.type";
  public static final String OLD_VALUE = "log.operation.old.value";
  public static final String NEW_VALUE = "log.operation.new.value";
  public static final String DATE_TIME = "log.operation.date.time";
  public static final String OPERATION = "log.operation.operation";

  public static final String PROPERTY = "log.operation.property";
}
