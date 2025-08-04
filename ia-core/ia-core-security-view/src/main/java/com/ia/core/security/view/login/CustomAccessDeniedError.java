package com.ia.core.security.view.login;

import com.vaadin.flow.router.AccessDeniedException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.RouteAccessDeniedError;
import com.vaadin.flow.server.HttpStatusCode;

/**
 * Rota para acesso negado.
 *
 * @author Israel Araújo
 */
public class CustomAccessDeniedError
  extends RouteAccessDeniedError {
  /** Serial UID */
  private static final long serialVersionUID = 2183590324210168518L;

  @Override
  public int setErrorParameter(BeforeEnterEvent event,
                               ErrorParameter<AccessDeniedException> parameter) {
    getElement().setText("Acesso não autorizado");
    return HttpStatusCode.UNAUTHORIZED.getCode();
  }
}
