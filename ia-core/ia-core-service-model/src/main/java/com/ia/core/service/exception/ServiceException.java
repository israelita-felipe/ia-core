package com.ia.core.service.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Classe de retenção de erros.
 *
 * @author Israel Araújo
 */
public class ServiceException
  extends Exception {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -7117586372811635337L;
  /**
   * Exceções
   */
  private Collection<Exception> erros = new HashSet<>();

  /**
   * Construtor padrão.
   */
  public ServiceException() {

  }

  /**
   * Construtor com erro
   *
   * @param error erro a ser adicionado
   */
  public ServiceException(String error) {
    this();
    add(error);
  }

  /**
   * Adiciona uma exceção.
   *
   * @param ex {@link Exception}
   */
  public void add(Exception ex) {
    if (!hasErros()) {
      initCause(ex);
    }
    this.erros.add(ex);
  }

  /**
   * Adiciona uma mensagem de erro.
   *
   * @param error {@link String}
   */
  public void add(String error) {
    this.erros.add(new Exception(error));
  }

  /**
   * @return Stream de erros.
   */
  public Stream<String> getErrors() {
    return this.erros.parallelStream().map(Exception::getMessage);
  }

  @Override
  public String getMessage() {
    return getErrors().reduce(String::join).orElse(null);
  }

  /**
   * @return <code>true</code> caso haja erros.
   */
  public boolean hasErros() {
    return !erros.isEmpty();
  }
}
