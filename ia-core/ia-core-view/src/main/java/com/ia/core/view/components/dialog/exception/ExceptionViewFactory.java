package com.ia.core.view.components.dialog.exception;

import com.ia.core.service.translator.CoreApplicationTranslator;
import com.ia.core.view.components.properties.HasTranslator;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Fábrica para visualização de exceções
 *
 * @author Israel Araújo
 */
@Slf4j
public class ExceptionViewFactory
  implements HasTranslator {
  /** Instância singleton */
  private static ExceptionViewFactory instance = null;

  /**
   * Captura o singleton desta classe
   *
   * @return {@link #instance}
   */
  private static ExceptionViewFactory get() {
    if (instance == null) {
      instance = new ExceptionViewFactory();
    }
    return instance;
  }

    public static void showError(Throwable erro) {
        log.error("Erro ao mostrar exceção", erro);
        VaadinSession.getCurrent().getErrorHandler()
            .error(new ErrorEvent(erro));
        ConfirmDialog dialogo = get().criarDialogo(getErrors(erro));
        dialogo.open();
    }

  /**
   * Captura os erros de forma profunda
   *
   * @param erro {@link Throwable} do erro
   * @return String contendo as mensagem de erro agrupadas por profundidade em
   *         sua causa.
   */
  private static String getErrors(Throwable erro) {
    StringBuilder sb = new StringBuilder();
    if (erro.getLocalizedMessage() != null) {
      sb.append(erro.getLocalizedMessage());
      sb.append("\n");
    }
    Throwable current = erro.getCause();
    while (current != null) {
      if (current.getLocalizedMessage() != null) {
        sb.append(current.getLocalizedMessage());
        sb.append("\n");
      }
      current = current.getCause();
    }
    return sb.toString();
  }

  /**
   * Construtor privado
   */
  private ExceptionViewFactory() {
  }

  /**
   * Cria um diálogo de aviso
   *
   * @param mensagem Mensagem a ser exibida
   * @return {@link ConfirmDialog}m
   */
  private ConfirmDialog criarDialogo(String mensagem) {
    ConfirmDialog dialog = new ConfirmDialog();
    dialog.setHeader($(CoreApplicationTranslator.TITLE.ERROR));
    dialog.setText(mensagem);
    dialog.setConfirmText($(CoreApplicationTranslator.ACTION.OK));
    return dialog;
  }
}
