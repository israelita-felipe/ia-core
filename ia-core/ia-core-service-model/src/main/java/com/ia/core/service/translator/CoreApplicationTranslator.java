package com.ia.core.service.translator;

/**
 * Chaves de tradução
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio core application translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CoreApplicationTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class CoreApplicationTranslator {

  public static final String APPLICATION_NAME = "application.name";
  public static final String DETALHAR = "detalhar";
  public static final String PESQUSIAR = "pesquisar";
  public static final String INICIO = "inicio";
  public static final String CANCELAR = "cancelar";
  public static final String HOJE = "hoje";
  public static final String DEFAULT_ACTION = "action";
  public static final String FILTER = "filter";
  public static final String REPORTS = "reports";

  ///////////////////////////////////////////////////////
  /// AÇÕES
  ///////////////////////////////////////////////////////

  public static final class ACTION {
    public static final String SAVE = "action.save";
    public static final String DELETE = "action.delete";
    public static final String CANCEL = "action.cancel";
    public static final String OK = "action.ok";
    public static final String YES = "action.yes";
    public static final String NO = "action.no";
  }

  ///////////////////////////////////////////////////////
  /// HELP - PAGE ACTIONS
  ///////////////////////////////////////////////////////

  public static final class HELP {
    public static final String PAGE_NEW = "help.page.new";
    public static final String PAGE_EDIT = "help.page.edit";
    public static final String PAGE_COPY = "help.page.copy";
    public static final String PAGE_VIEW = "help.page.view";
    public static final String PAGE_DELETE = "help.page.delete";
    public static final String PAGE_PRINT = "help.page.print";
    public static final String PAGE_FILTER = "help.page.filter";
  }

  ///////////////////////////////////////////////////////
  /// HELP - VIEWS
  ///////////////////////////////////////////////////////

  public static final class VIEW {
    public static final String PAGE_LISTING = "view.page.listing";
    public static final String FORM_EDITING = "view.form.editing";
  }

  ///////////////////////////////////////////////////////
  /// HELP - ONLINE
  ///////////////////////////////////////////////////////

  public static final class HELP_ONLINE {
    public static final String BUTTON_ARIA_LABEL = "help.online.button.aria.label";
    public static final String DEFAULT_TITLE = "help.online.default.title";
  }

  ///////////////////////////////////////////////////////
  /// MENSAGENS
  ///////////////////////////////////////////////////////

  public static final class MESSAGE {
    public static final String DELETE_SUCCESS = "dialog.message.success.delete";
    public static final String CONFIRM_DELETE = "dialog.message.confirm.delete";
    public static final String SAVE_SUCCESS = "dialog.message.success.save";
  }

  ///////////////////////////////////////////////////////
  /// TÍTULOS
  ///////////////////////////////////////////////////////

  public static final class TITLE {
    public static final String ERROR = "title.error";
  }

  ///////////////////////////////////////////////////////
  /// MENSAGENS DE ERROR
  ///////////////////////////////////////////////////////

  public static final String ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS = "error.integridade.objeto.dependencias";

  ///////////////////////////////////////////////////////
  /// MENSAGENS DE ERROR - REST API
  ///////////////////////////////////////////////////////

  public static final String ERROR_AUTHENTICATION = "error.authentication";
  public static final String ERROR_AUTHENTICATION_MESSAGE = "error.authentication.message";
  public static final String ERROR_ACCESS_DENIED = "error.access.denied";
  public static final String ERROR_ACCESS_DENIED_MESSAGE = "error.access.denied.message";
  public static final String ERROR_ENTITY_NOT_FOUND = "error.entity.not.found";
  public static final String ERROR_ENTITY_NOT_FOUND_MESSAGE = "error.entity.not.found.message";
  public static final String ERROR_VALIDATION = "error.validation";
  public static final String ERROR_VALIDATION_MESSAGE = "error.validation.message";
  public static final String ERROR_DATA_INTEGRITY = "error.data.integrity";
  public static final String ERROR_DATA_INTEGRITY_MESSAGE = "error.data.integrity.message";
  public static final String ERROR_SERVICE = "error.service";
  public static final String ERROR_SERVICE_MESSAGE = "error.service.message";
  public static final String ERROR_INTERNAL = "error.internal";
  public static final String ERROR_INTERNAL_MESSAGE = "error.internal.message";

}
