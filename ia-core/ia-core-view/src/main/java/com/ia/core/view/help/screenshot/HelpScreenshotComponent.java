package com.ia.core.view.help.screenshot;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;

import java.util.function.Consumer;

/**
 * Componente para capturar screenshot de componentes Vaadin.
 * Utiliza html2canvas via NPM para captura no cliente.
 *
 * <p>Exemplo de uso:
 * <pre>{@code
 * HelpScreenshotComponent screenshot = new HelpScreenshotComponent();
 * screenshot.capture(myComponent, bytes -> {
 *     // Processar imagem capturada
 *     saveImage(bytes);
 * });
 * }</pre>
 *
 * <p>Suporta múltiplas capturas simultâneas usando identificadores únicos.
 *
 * @author Israel Araújo
 */

public class HelpScreenshotComponent {


    /**
     * Captura um screenshot de um componente Vaadin.
     *
     * @param component componente que já deve estar anexado ao DOM
     * @param callback  recebe a imagem em Base64 (PNG)
     */
    public static void capture(Component component, Consumer<String> callback) {

        if (component == null) {
            throw new IllegalArgumentException("Component não pode ser nulo.");
        }

        if (!component.getElement().getNode().isAttached()) {
            throw new IllegalStateException("O componente ainda não está anexado ao DOM.");
        }

        UI ui = component.getUI()
            .orElseThrow(() -> new IllegalStateException("Componente não pertence a nenhuma UI."));

        ui.getPage().executeJs(
            """
                return window.captureScreenshot($0);
                """,
            component.getElement()
        ).then(String.class, base64Data -> {
            callback.accept(base64Data);
        });
    }
}
