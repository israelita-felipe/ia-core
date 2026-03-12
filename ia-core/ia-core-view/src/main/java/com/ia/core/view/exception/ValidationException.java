package com.ia.core.view.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

import com.ia.core.service.exception.ServiceException;

/**
 * Exception de validação.
 *
 * @author Israel Araújo
 */
public class ValidationException
  extends ServiceException {
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
  @Override
  public void add(Exception ex) {
    this.erros.add(ex);
  }

  /**
   * Adiciona uma mensagem de erro.
   *
   * @param error {@link String}
   */
  @Override
  public void add(String error) {
    this.erros.add(new Exception(error));
  }

  /**
   * @return Stream de erros.
   */
  @Override
  public Stream<String> getErrors() {
    return this.erros.parallelStream().map(Exception::getMessage);
  }

  /**
   * @return <code>true</code> caso haja erros.
   */
  @Override
  public boolean hasErros() {
    return !erros.isEmpty();
  }
}
