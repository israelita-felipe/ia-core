package com.ia.core.service.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

import com.ia.core.model.exception.DomainException;

/**
 * Classe de retenção de erros de serviço.
 * Mantida para retrocompatibilidade com código existente.
 *
 * @author Israel Araújo
 */
public class ServiceException
  extends DomainException {

  private static final long serialVersionUID = -7117586372811635337L;

  private static final String DEFAULT_ERROR_CODE = "SERVICE_ERROR";

  /**
   * Exceções
   */
  private Collection<Exception> erros = new HashSet<>();

  /**
   * Construtor padrão.
   */
  public ServiceException() {
    super(DEFAULT_ERROR_CODE, "Erro de serviço");
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
   * Construtor com código de erro e mensagem.
   *
   * @param errorCode código de erro
   * @param message   mensagem de erro
   */
  public ServiceException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * Construtor com código de erro, mensagem e causa.
   *
   * @param errorCode código de erro
   * @param message   mensagem de erro
   * @param cause     causa original
   */
  public ServiceException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
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
