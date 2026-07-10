package com.ia.core.security.service.model.log.operation;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.util.JsonUtil;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.*;

/**
 * Detalhes de uma operação de log no sistema.
 * <p>
 * Esta classe fornece informações detalhadas sobre uma operação realizada
 * no sistema, incluindo valores antigos, novos e as diferenças entre eles.
 * Utilizada para auditoria e histórico de mudanças.
 *
 * @author Israel Araújo
 * @see LogOperationDTO
 * @see OperationItemDetailsDTO
 * @since 1.0.0
 */
@SuperBuilder(toBuilder = true)
@Data
public class LogOperationDetailsDTO
  implements DTO<LogOperationDTO> {
  private static final long serialVersionUID = 6394027572652328256L;

  /**
   * Operação de log a ser analisada.
   */
  private final LogOperationDTO logOperation;

  /**
   * Valores antigos antes da operação.
   */
  private final Map<String, Object> oldValue = new HashMap<>();

  /**
   * Novos valores após a operação.
   */
  private final Map<String, Object> newValue = new HashMap<>();

  /**
   * Diferenças calculadas do valor antigo.
   */
  private final Map<String, Object> diffOldValue = new HashMap<>();

  /**
   * Diferenças calculadas do novo valor.
   */
  private final Map<String, Object> diffNewValue = new HashMap<>();

  /**
   * Itens de detalhes da operação.
   */
  private final Collection<OperationItemDetailsDTO> itens = new TreeSet<>();

  /**
   * Construtor padrão.
   *
   * @param logOperation operação de log a ser analisada
   */
  public LogOperationDetailsDTO(LogOperationDTO logOperation) {
    super();
    this.logOperation = logOperation;
    calculateDiff();
  }

  /**
   * Calcula as diferenças entre os valores antigos e novos.
   */
  public void calculateDiff() {
    if (OperationEnum.UPDATE.equals(logOperation.getOperation())) {
      this.oldValue
          .putAll(JsonUtil.getProperties(logOperation.getOldValue()));
      this.newValue
          .putAll(JsonUtil.getProperties(logOperation.getNewValue()));
    } else if (OperationEnum.CREATE.equals(logOperation.getOperation())) {
      this.newValue
          .putAll(JsonUtil.getProperties(logOperation.getNewValue()));
    } else if (OperationEnum.DELETE.equals(logOperation.getOperation())) {
      this.oldValue
          .putAll(JsonUtil.getProperties(logOperation.getOldValue()));
    }
    this.diffNewValue
        .putAll(getLogOperationDiffFromNewValue(this.oldValue,
                                                  (this.newValue)));
    this.diffOldValue
        .putAll(getLogOperationDiffFromOldValue(this.oldValue,
                                                  (this.newValue)));

    this.getDiffProperties().forEach(property -> {
      OperationItemDetailsDTO logOperationItemDetailsDTO = new OperationItemDetailsDTO(property,
                                                                                      getDiffOldValue()
                                                                                          .get(property),
                                                                                      getDiffNewValue()
                                                                                          .get(property));
      this.itens.add(logOperationItemDetailsDTO);
    });
  }

  /**
   * Calcula a diferença entre objetos baseado no novo valor.
   *
   * @param oldObject objeto antigo
   * @param newObject objeto novo
   * @return mapa com as diferenças
   */
  public static Map<String, Object> getLogOperationDiffFromNewValue(Map<String, Object> oldObject,
                                                                    Map<String, Object> newObject) {
    Map<String, Object> diffFromNew = new HashMap<>();
    if (newObject == null) {
      return diffFromNew;
    }
    diffFromNew.putAll(newObject);
    if (oldObject == null) {
      return diffFromNew;
    }
    oldObject.entrySet().forEach(entryFromOldObject -> {
      newObject.entrySet().forEach(entryFromNewObject -> {
        if (Objects.equals(entryFromNewObject, entryFromOldObject)) {
          diffFromNew.remove(entryFromOldObject.getKey());
        }
      });
    });
    return diffFromNew;
  }

  /**
   * Calcula a diferença entre objetos baseado no valor antigo.
   *
   * @param oldObject objeto antigo
   * @param newObject objeto novo
   * @return mapa com as diferenças
   */
  public static Map<String, Object> getLogOperationDiffFromOldValue(Map<String, Object> oldObject,
                                                                    Map<String, Object> newObject) {
    Map<String, Object> diffFromOld = new HashMap<>();
    if (oldObject == null) {
      return diffFromOld;
    }
    diffFromOld.putAll(oldObject);
    if (newObject == null) {
      return diffFromOld;
    }
    newObject.entrySet().forEach(entryFromNewObject -> {
      oldObject.entrySet().forEach(entryFromOldObject -> {
        if (Objects.equals(entryFromNewObject, entryFromOldObject)) {
          diffFromOld.remove(entryFromOldObject.getKey());
        }
      });
    });
    return diffFromOld;
  }

/**
    * Cria uma cópia superficial (clone) deste objeto.
    *
    * @return novo objeto {@link LogOperationDetailsDTO} com os mesmos valores
    */
  @Override
  public LogOperationDetailsDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Retorna todas as propriedades (chaves) presentes nos valores antigos e novos.
   *
   * @return coleção de strings representando as propriedades
   */
  public Collection<String> getAllProperties() {
    Set<String> hashSet = new HashSet<>();
    hashSet.addAll(newValue.keySet());
    hashSet.addAll(oldValue.keySet());
    return hashSet;
  }

  /**
   * Retorna as propriedades que possuem diferenças entre os valores.
   *
   * @return conjunto de strings representando as propriedades diferentes
   */
  public Set<String> getDiffProperties() {
    Set<String> hashSet = new HashSet<>();
    hashSet.addAll(diffNewValue.keySet());
    hashSet.addAll(diffOldValue.keySet());
    return hashSet;
  }

/**
    * Retorna uma representação em string deste objeto.
    *
    * @return string contendo os detalhes da operação
    */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LogOperationDetailsDTO [");
    if (itens != null) {
      itens.forEach(item -> {
        builder.append("\n");
        builder.append(item);
      });
    }
    builder.append("]");
    return builder.toString();
  }

}
