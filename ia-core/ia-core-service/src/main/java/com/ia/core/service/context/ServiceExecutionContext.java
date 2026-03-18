package com.ia.core.service.context;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;

/**
 * Contexto de execução para operações de serviço.
 * <p>
 * Esta classe encapsula o estado durante a execução de uma operação de serviço,
 * como save, update ou delete. Fornece um objeto container para passar dados
 * através da cadeia de execução sem expor campos internos.
 * </p>
 *
 * <h2>Uso</h2>
 * <pre>
 * ServiceExecutionContext<T, D> context = new ServiceExecutionContext<>(dto);
 * context.setModel(model);
 * context.setSavedEntity(saved);
 * </pre>
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade {@link BaseEntity}
 * @param <D> Tipo do {@link DTO}
 * @since 1.0.0
 */
public class ServiceExecutionContext<T extends BaseEntity, D extends DTO<?>> {

  /**
   * DTO original recebido para operação.
   */
  private final D toSave;

  /**
   * Modelo convertido a ser persistido.
   */
  private T model;

  /**
   * Entidade salva após persistência.
   */
  private T savedEntity;

  /**
   * DTO retornado após a operação.
   */
  private D result;

  /**
   * Flag indicando se a operação foi cancelada por validação.
   */
  private boolean cancelled;

  /**
   * Mensagem de cancelamento, se aplicável.
   */
  private String cancelReason;

  /**
   * Construtor padrão que inicializa o contexto com o DTO a ser salvo.
   *
   * @param toSave DTO a ser processado
   */
  public ServiceExecutionContext(D toSave) {
    this.toSave = toSave;
  }

  /**
   * Retorna o DTO original recebido para operação.
   *
   * @return DTO original
   */
  public D getToSave() {
    return toSave;
  }

  /**
   * Define o modelo convertido.
   *
   * @param model Modelo convertido
   * @return Esta instância para fluência
   */
  public ServiceExecutionContext<T, D> setModel(T model) {
    this.model = model;
    return this;
  }

  /**
   * Retorna o modelo convertido.
   *
   * @return Modelo ou null
   */
  public T getModel() {
    return model;
  }

  /**
   * Define a entidade salva.
   *
   * @param savedEntity Entidade salva
   * @return Esta instância para fluência
   */
  public ServiceExecutionContext<T, D> setSavedEntity(T savedEntity) {
    this.savedEntity = savedEntity;
    return this;
  }

  /**
   * Retorna a entidade salva.
   *
   * @return Entidade salva ou null
   */
  public T getSavedEntity() {
    return savedEntity;
  }

  /**
   * Define o DTO resultado.
   *
   * @param result DTO resultado
   * @return Esta instância para fluência
   */
  public ServiceExecutionContext<T, D> setResult(D result) {
    this.result = result;
    return this;
  }

  /**
   * Retorna o DTO resultado.
   *
   * @return DTO resultado ou null
   */
  public D getResult() {
    return result;
  }

  /**
   * Cancela a operação com reason opcional.
   *
   * @param cancelReason Reason do cancelamento
   */
  public void cancel(String cancelReason) {
    this.cancelled = true;
    this.cancelReason = cancelReason;
  }

  /**
   * Verifica se a operação foi cancelada.
   *
   * @return true se cancelada
   */
  public boolean isCancelled() {
    return cancelled;
  }

  /**
   * Retorna a reason do cancelamento.
   *
   * @return Reason ou null
   */
  public String getCancelReason() {
    return cancelReason;
  }

  /**
   * Verifica se é uma operação de atualização.
   *
   * @return true se o modelo tem ID
   */
  public boolean isUpdate() {
    return model != null && model.getId() != null;
  }
}
