package com.ia.core.view;

import com.ia.test.CoreBaseUnitTest;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for Vaadin view tests.
 * Provides common functionality and configuration for Vaadin view testing.
 * Sets up mock Vaadin environment for component testing.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * @author Israel Araújo
 */
public abstract class CoreBaseVaadinViewTest extends CoreBaseUnitTest {

    @BeforeEach
    void setUpVaadin() {
        // Initialize mock Vaadin environment
        VaadinSession session = new VaadinSession(null);
        VaadinService service = null;
        UI ui = new UI();
        UI.setCurrent(ui);
    }

    @AfterEach
    void tearDownVaadin() {
        UI.setCurrent(null);
    }
}
