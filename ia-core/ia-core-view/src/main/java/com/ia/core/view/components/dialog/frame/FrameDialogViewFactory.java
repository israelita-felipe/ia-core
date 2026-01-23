package com.ia.core.view.components.dialog.frame;

import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.properties.HasTranslator;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.html.IFrame;

/**
 * Fábrica para diálogos de confirmação
 *
 * @author Israel Araújo
 */
public class FrameDialogViewFactory
  implements HasTranslator {
  /** Instância singleton */
  private static FrameDialogViewFactory instance = null;

  /**
   * Captura o singleton desta classe
   *
   * @return {@link #instance}
   */
  private static FrameDialogViewFactory get() {
    if (instance == null) {
      instance = new FrameDialogViewFactory();
    }
    return instance;
  }

  /**
   * Exibe o diálogo de confirmação
   *
   * @param title Título da ação
   * @param src   Conteúdo
   */
  public static void show(String title, String src) {
    get().showFrame(title, src);
  }

  /**
   * Construtor privado
   */
  private FrameDialogViewFactory() {
  }

  /**
   * Cria um diálog de confirmação e abre o diálogo
   *
   * @param title Título do diálogo
   * @param src   Conteúdo do frame
   */
  private void showFrame(String title, String src) {
    IFrame iframe = new IFrame(src);
    iframe.getStyle().setBorder("0");
    iframe.setSizeFull();
    Dialog dialog = new Dialog(iframe);
    dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
    DialogHeaderBar header = DialogHeaderBar.addTo(dialog);
    header.setMaximized(true);
    dialog.setHeaderTitle(title);
    dialog.open();
  }
}
