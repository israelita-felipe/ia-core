package com.ia.core.service.translator;

/**
 * Translator para internacionalização (i18n) do módulo Core Application.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class CoreApplicationTranslator {

    /**
     * Chaves de tradução para labels.
     */
    public static final class LABEL {
        public static final String APPLICATION_NAME = "application.name";
        public static final String DETALHAR = "detalhar";
        public static final String PESQUISAR = "pesquisar";
        public static final String INICIO = "inicio";
        public static final String CANCELAR = "cancelar";
        public static final String HOJE = "hoje";
        public static final String FILTER = "filter";
        public static final String REPORTS = "reports";
    }

    /**
     * Chaves de tradução para ações.
     */
    public static final class ACTION {
        public static final String SAVE = "action.save";
        public static final String DELETE = "action.delete";
        public static final String CANCEL = "action.cancel";
        public static final String OK = "action.ok";
        public static final String YES = "action.yes";
        public static final String NO = "action.no";
        public static final String DEFAULT_ACTION = "action.default";
    }

    /**
     * Chaves de tradução para help de páginas.
     */
    public static final class HELP {
        public static final String PAGE_NEW = "help.page.new";
        public static final String PAGE_EDIT = "help.page.edit";
        public static final String PAGE_COPY = "help.page.copy";
        public static final String PAGE_VIEW = "help.page.view";
        public static final String PAGE_DELETE = "help.page.delete";
        public static final String PAGE_PRINT = "help.page.print";
        public static final String PAGE_FILTER = "help.page.filter";
    }

    /**
     * Chaves de tradução para views.
     */
    public static final class VIEW {
        public static final String PAGE_LISTING = "view.page.listing";
        public static final String FORM_EDITING = "view.form.editing";
    }

    /**
     * Chaves de tradução para help online.
     */
    public static final class HELP_ONLINE {
        public static final String BUTTON_ARIA_LABEL = "help.online.button.aria.label";
        public static final String DEFAULT_TITLE = "help.online.default.title";
    }

    /**
     * Chaves de tradução para mensagens de erro.
     */
    public static final class ERROR {
        public static final String INTEGRITY_OBJECT_HAS_DEPENDENCIES = "error.integridade.objeto.dependencias";
        public static final String AUTHENTICATION = "error.authentication";
        public static final String AUTHENTICATION_MESSAGE = "error.authentication.message";
        public static final String ACCESS_DENIED = "error.access.denied";
        public static final String ACCESS_DENIED_MESSAGE = "error.access.denied.message";
        public static final String ENTITY_NOT_FOUND = "error.entity.not.found";
        public static final String ENTITY_NOT_FOUND_MESSAGE = "error.entity.not.found.message";
        public static final String VALIDATION = "error.validation";
        public static final String VALIDATION_MESSAGE = "error.validation.message";
        public static final String DATA_INTEGRITY = "error.data.integrity";
        public static final String DATA_INTEGRITY_MESSAGE = "error.data.integrity.message";
        public static final String SERVICE = "error.service";
        public static final String SERVICE_MESSAGE = "error.service.message";
        public static final String INTERNAL = "error.internal";
        public static final String INTERNAL_MESSAGE = "error.internal.message";
    }

    /**
     * Chaves de tradução para mensagens de sucesso.
     */
    public static final class MESSAGE {
        public static final String DELETE_SUCCESS = "dialog.message.success.delete";
        public static final String CONFIRM_DELETE = "dialog.message.confirm.delete";
        public static final String SAVE_SUCCESS = "dialog.message.success.save";
    }

    // Backward compatibility constants for MESSAGE
    public static final String MESSAGE_DELETE_SUCCESS = MESSAGE.DELETE_SUCCESS;
    public static final String MESSAGE_CONFIRM_DELETE = MESSAGE.CONFIRM_DELETE;
    public static final String MESSAGE_SAVE_SUCCESS = MESSAGE.SAVE_SUCCESS;

    /**
     * Chaves de tradução para títulos.
     */
    public static final class TITLE {
        public static final String ERROR = "title.error";
    }

    // Backward compatibility - aliases for the new nested class structure
    public static final String APPLICATION_NAME = LABEL.APPLICATION_NAME;
    public static final String DETALHAR = LABEL.DETALHAR;
    public static final String PESQUISAR = LABEL.PESQUISAR;
    public static final String INICIO = LABEL.INICIO;
    public static final String CANCELAR = LABEL.CANCELAR;
    public static final String HOJE = LABEL.HOJE;
    public static final String FILTER = LABEL.FILTER;
    public static final String REPORTS = LABEL.REPORTS;

    public static final String ERROR_AUTHENTICATION = ERROR.AUTHENTICATION;
    public static final String ERROR_AUTHENTICATION_MESSAGE = ERROR.AUTHENTICATION_MESSAGE;
    public static final String ERROR_ACCESS_DENIED = ERROR.ACCESS_DENIED;
    public static final String ERROR_ACCESS_DENIED_MESSAGE = ERROR.ACCESS_DENIED_MESSAGE;
    public static final String ERROR_ENTITY_NOT_FOUND = ERROR.ENTITY_NOT_FOUND;
    public static final String ERROR_ENTITY_NOT_FOUND_MESSAGE = ERROR.ENTITY_NOT_FOUND_MESSAGE;
    public static final String ERROR_VALIDATION = ERROR.VALIDATION;
    public static final String ERROR_VALIDATION_MESSAGE = ERROR.VALIDATION_MESSAGE;
    public static final String ERROR_DATA_INTEGRITY = ERROR.DATA_INTEGRITY;
    public static final String ERROR_DATA_INTEGRITY_MESSAGE = ERROR.DATA_INTEGRITY_MESSAGE;
    public static final String ERROR_SERVICE = ERROR.SERVICE;
    public static final String ERROR_SERVICE_MESSAGE = ERROR.SERVICE_MESSAGE;
    public static final String ERROR_INTERNAL = ERROR.INTERNAL;
    public static final String ERROR_INTERNAL_MESSAGE = ERROR.INTERNAL_MESSAGE;

    public static final String ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS = ERROR.INTEGRITY_OBJECT_HAS_DEPENDENCIES;

    // Backward compatibility - DEFAULT_ACTION constant
    public static final String DEFAULT_ACTION = ACTION.DEFAULT_ACTION;
}
