package com.ia.core.view.properties;

import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
/**
 * Componente de interface visual para has error handle.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a HasErrorHandle
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
