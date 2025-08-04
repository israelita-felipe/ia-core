package com.ia.core.view.properties;

import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;

/**
 * @author Israel Araújo
 */
public interface HasErrorHandle {
  /**
   * Logger da aplicação
   */
  final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
      .getLogger(HasErrorHandle.class);

  /**
   * Captura um erro;
   *
   * @param ex Erro.
   */
  default void handleError(Exception ex) {
    if (isShowErrorStackTrace()) {
      LOG.error(ex.getLocalizedMessage(), ex);
    }
    ExceptionViewFactory.showError(ex);
  }

  /**
   * Se deve ser logado o stacktrace
   *
   * @return <code>true</code> por padrão
   */
  default boolean isShowErrorStackTrace() {
    return true;
  }
}
