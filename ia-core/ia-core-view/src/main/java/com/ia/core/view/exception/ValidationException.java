package com.ia.core.view.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Exception de validação.
 *
 * @author Israel Araújo
 */
public class ValidationException
  extends Exception {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -176099666014084747L;
  /**
   * Exceções
   */
  private Collection<Exception> erros = new HashSet<>();

  /**
   * Construtor padrão.
   */
  public ValidationException() {
    super();
  }

  /**
   * Construtor padrão.
   *
   * @param message Mensagem padrão.
   */
  public ValidationException(String message) {
    super(message);
  }

  /**
   * Adiciona uma exceção.
   *
   * @param ex {@link Exception}
   */
  public void add(Exception ex) {
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

  /**
   * @return <code>true</code> caso haja erros.
   */
  public boolean hasErros() {
    return !erros.isEmpty();
  }
}
