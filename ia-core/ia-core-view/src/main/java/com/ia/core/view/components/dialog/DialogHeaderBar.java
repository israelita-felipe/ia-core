package com.ia.core.view.components.dialog;

import java.util.ArrayList;
import java.util.Locale;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import jakarta.validation.constraints.NotNull;

/**
 * A component which implements the Dialog header bar. The header bar by default
 * shows both the close button and the maximize/restore button.
 * <ul>
 * <li>call {@link #setMaximizeVisible(boolean)} to hide the maximize
 * button,</li>
 * <li>call {@link #setCaption(String)} to set the header bar caption (defaults
 * to an empty string)</li>
 * </ul>
 * <h3>Integration with Dialog</h3> Simply call the {@link #addTo(Dialog)} which
 * integrates the header bar into the dialog.
 * <p>
 * </p>
 * Warning: this will remove the dialog content padding; simply wrap the
 * contents with a <code>VerticalLayout</code> which adds padding of its own.
 *
 * @author Martin Vysny <mavi@vaadin.com>
 */
public class DialogHeaderBar
  extends HorizontalLayout {
  /**
   * Represents a header bar button extending {@link Icon} to show corresponding
   * icon for an action.
   */
  static class HeaderBarButton
    extends Icon {
    /** Serial UID */
    private static final long serialVersionUID = -6666905509700485250L;

    /**
     * Default constructor
     */
    public HeaderBarButton() {
      setSize("16px");
      getElement().getStyle().set("margin", "8px");
      getElement().getStyle().set("cursor", "pointer");
    }

    /**
     * @param icon Icon to use in the button bar
     */
    public HeaderBarButton(VaadinIcon icon) {
      this();
      setIcon(icon);
    }

    /**
     * @param icon Set the icon
     */
    public void setIcon(VaadinIcon icon) {
      getElement().setAttribute("icon", "vaadin:"
          + icon.name().toLowerCase(Locale.ENGLISH).replace('_', '-'));
    }
  }

  /** Serial UID */
  private static final long serialVersionUID = -296314020813553471L;

  /**
   * Creates a new header bar, inserts it into the dialog, reconfigures the
   * dialog to have no content padding, and returns the header bar.
   *
   * @param dialog the dialog to control, not null.
   * @return the header bar.
   */
  @NotNull
  public static DialogHeaderBar addTo(@NotNull Dialog dialog) {
    final DialogHeaderBar headerBar = new DialogHeaderBar(dialog);
    dialog.getHeader().add(headerBar);
    return headerBar;
  }

  /**
   * Utility function, see https://github.com/vaadin/vaadin-dialog/issues/220
   * for more details.
   *
   * @param dialog center the dialog
   */
  private static void center(@NotNull Dialog dialog) {
    final ArrayList<String> styles = new ArrayList<>();
    if (dialog.getWidth() != null) {
      styles.add("width: " + dialog.getWidth());
    }
    if (dialog.getHeight() != null) {
      styles.add("height: " + dialog.getHeight());
    }
    dialog.getElement().executeJs("this.$.overlay.$.overlay.style = '"
        + String.join(";", styles) + "';");
  }

  /** Caption of this header */
  private final Div caption = new Div();
  /** Current dialog of this header */
  @NotNull
  private final Dialog dialog;
  /** Maximized status */
  private boolean maximized = false;
  /** Previous width */
  private String prevWidth = null;

  /** Previous height */
  private String prevHeight = null;

  /** Maximize/Restore button */
  private final HeaderBarButton maximizeRestoreButton = new HeaderBarButton();

  /** Close button */
  private final HeaderBarButton closeButton = new HeaderBarButton(VaadinIcon.CLOSE);

  /**
   * Creates the header bar.
   *
   * @param dialog the header bar will control this dialog. However, the header
   *               bar will not insert automatically into the dialog; use the
   *               {@link #addTo(Dialog)} to do that.
   */
  public DialogHeaderBar(@NotNull Dialog dialog) {
    this.dialog = dialog;
    getElement().getStyle().set("user-select", "none"); // prevent caption
                                                        // selection when
                                                        // dragging the dialog
    addClassName("draggable"); // in order for the Dialog to be draggable by the
                               // header bar
    setWidthFull();
    addAndExpand(caption);
    caption.getStyle().set("font-size", "var(--lumo-font-size-l)");
    caption.getStyle().set("padding", "4px 16px");

    add(maximizeRestoreButton);
    maximizeRestoreButton.addClickListener(e -> {
      toggleMaximized();
      update();
    });
    add(closeButton);
    closeButton.addClickListener(e -> {
      close();
    });

    update();
  }

  /**
   * Closes the dialog; simply calls {@link Dialog#close()}.
   */
  public void close() {
    dialog.close();
  }

  /**
   * Returns the dialog caption.
   *
   * @return the caption, or blank string when there's no caption (the default).
   */
  @NotNull
  public String getCaption() {
    return this.caption.getText();
  }

  /**
   * @return {@link HeaderBarButton} for close
   */
  @NotNull
  HeaderBarButton getCloseButton() {
    return closeButton;
  }

  /**
   * Returns the owner dialog.
   *
   * @return the owner dialog.
   */
  @NotNull
  public Dialog getDialog() {
    return dialog;
  }

  /**
   * @return {@link HeaderBarButton} for restore
   */
  @NotNull
  HeaderBarButton getMaximizeRestoreButton() {
    return maximizeRestoreButton;
  }

  /**
   * Returns true if the dialog is currently maximized.
   *
   * @return true if the dialog is currently maximized.
   */
  public boolean isMaximized() {
    return maximized;
  }

  /**
   * @return true if the maximize/restore button is visible (the default).
   */
  public boolean isMaximizeVisible() {
    return getMaximizeRestoreButton().isVisible();
  }

  /**
   * Sets the dialog caption.
   *
   * @param caption the caption, or blank string to remove the caption.
   * @return this
   */
  @NotNull
  public DialogHeaderBar setCaption(@NotNull String caption) {
    this.caption.setText(caption);
    return this;
  }

  /**
   * Sets the dialog to either "maximized" state (taking all the screen's real
   * estate) or "restored" state (the default).
   *
   * @param maximized true if the dialog should be maximized, false if the
   *                  dialog should be in the "restored" state.
   * @return this
   */
  @NotNull
  public DialogHeaderBar setMaximized(boolean maximized) {
    if (this.maximized != maximized) {
      toggleMaximized();
    }
    return this;
  }

  /**
   * Makes the maximize/restore button visible or invisible.
   *
   * @param isMaximizeVisible set {@link #maximizeRestoreButton} visible
   * @return this
   */
  @NotNull
  public DialogHeaderBar setMaximizeVisible(boolean isMaximizeVisible) {
    getMaximizeRestoreButton().setVisible(isMaximizeVisible);
    return this;
  }

  /**
   * Alternate between maximized and default
   */
  private void toggleMaximized() {
    if (maximized) {
      dialog.setWidth(prevWidth);
      dialog.setHeight(prevHeight);
      maximized = false;
    } else {
      prevWidth = dialog.getWidth();
      prevHeight = dialog.getHeight();
      dialog.setWidth("100%");
      dialog.setHeight("100%");
      maximized = true;
    }
  }

  /**
   * Update informations on dialog
   */
  private void update() {
    if (maximized) {
      maximizeRestoreButton.setIcon(VaadinIcon.COMPRESS_SQUARE);
    } else {
      maximizeRestoreButton.setIcon(VaadinIcon.EXPAND_SQUARE);
    }
    dialog.setDraggable(!maximized);
    dialog.setResizable(!maximized);
    caption.getStyle().set("cursor", maximized ? "default" : "move");
    center(dialog);
  }
}
