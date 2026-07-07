package com.ia.core.view.help.e2e;

import com.ia.core.view.help.e2e.HelpOnlineAccessibilityE2eTest.TestHelpView;
import com.ia.core.view.help.e2e.HelpDialogAccessibilityE2eTest.TestDialogView;
import com.ia.core.view.help.e2e.HelpHighContrastE2eTest.TestHighContrastView;
import com.ia.core.view.help.e2e.HelpKeyboardNavigationE2eTest.TestKeyboardView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Configuração mínima de Spring Boot para testes E2E.
 * Importa as views de teste para registro de rotas Vaadin.
 *
 * @author Israel Araújo
 */
@SpringBootApplication
@Import({
    TestHelpView.class,
    TestDialogView.class,
    TestHighContrastView.class,
    TestKeyboardView.class
})
public class E2eTestApplication {
}
