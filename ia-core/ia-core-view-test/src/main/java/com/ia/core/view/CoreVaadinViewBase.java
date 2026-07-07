package com.ia.core.view;

import com.ia.test.CoreBaseUnitTest;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * Base class for Vaadin view tests.
 * Provides common functionality and configuration for Vaadin view testing.
 * Uses Vaadin's SpringUIUnitTest pattern for view component testing.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * <p>Characteristics:
 * - UI initialization for view testing
 * - Mocked VaadinService and VaadinRequest
 * - Component testing without full server
 * - No Spring context (pure unit tests)
 *
 * <p>Usage:
 * <pre>
 * {@code
 * class MyViewTest extends CoreVaadinViewBase {
 *     @Test
 *     void testViewRendering() {
 *         MyView view = new MyView();
 *         UI.getCurrent().add(view);
 *         // Your test logic
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@Disabled("Abstract base class - not a test")
@DisplayName("Vaadin View Base")
public abstract class CoreVaadinViewBase extends CoreBaseUnitTest {

    protected MockedStatic<UI> mockedUI;
    protected MockedStatic<VaadinService> mockedVaadinService;

    @BeforeEach
    void setUpVaadin() {
        mockedUI = Mockito.mockStatic(UI.class);
        mockedVaadinService = Mockito.mockStatic(VaadinService.class);

        UI ui = new UI();
        mockedUI.when(UI::getCurrent).thenReturn(ui);

        VaadinRequest request = Mockito.mock(VaadinRequest.class);
        Mockito.when(VaadinService.getCurrentRequest()).thenReturn(request);
    }

    @AfterEach
    void tearDownVaadin() {
        if (mockedUI != null) {
            mockedUI.close();
        }
        if (mockedVaadinService != null) {
            mockedVaadinService.close();
        }
    }

    /**
     * Adds a component to the current UI.
     *
     * @param component the component to add
     */
    protected void addToUI(Component component) {
        UI.getCurrent().add(component);
    }
}
