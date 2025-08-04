package com.ia.core.security.service.model.log.operation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.util.JsonUtil;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@SuperBuilder(toBuilder = true)
@Data
public class LogOperationDetails
  implements DTO<LogOperationDTO> {
  private static final long serialVersionUID = 6394027572652328256L;
  /**
   * @param oldObject
   * @param newObject
   * @return
   */
  public static Map<String, Object> getLogOperationDiffFromNewValue(Map<String, Object> oldObject,
                                                                    Map<String, Object> newObject) {
    Map<String, Object> diffFromNew = new HashMap<>();
    diffFromNew.putAll(newObject);
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
   * @param oldObject
   * @param newObject
   * @return
   */
  public static Map<String, Object> getLogOperationDiffFromOldValue(Map<String, Object> oldObject,
                                                                    Map<String, Object> newObject) {
    Map<String, Object> diffFromOld = new HashMap<>();
    diffFromOld.putAll(oldObject);
    newObject.entrySet().forEach(entryFromNewObject -> {
      oldObject.entrySet().forEach(entryFromOldObject -> {
        if (Objects.equals(entryFromNewObject, entryFromOldObject)) {
          diffFromOld.remove(entryFromOldObject.getKey());
        }
      });
    });
    return diffFromOld;
  }
  private final LogOperationDTO logOperation;
  private final Map<String, Object> oldValue = new HashMap<>();
  private final Map<String, Object> newValue = new HashMap<>();
  private final Map<String, Object> diffOldValue = new HashMap<>();

  private final Map<String, Object> diffNewValue = new HashMap<>();

  private final Collection<OperationItemDetails> itens = new TreeSet<>();

  /**
   * @param logOperation
   */
  public LogOperationDetails(LogOperationDTO logOperation) {
    super();
    this.logOperation = logOperation;
    calculateDiff();
  }

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
      OperationItemDetails logOperationItemDetails = new OperationItemDetails(property,
                                                                                    getDiffOldValue()
                                                                                        .get(property),
                                                                                    getDiffNewValue()
                                                                                        .get(property));
      this.itens.add(logOperationItemDetails);
    });
  }

  @Override
  public LogOperationDetails cloneObject() {
    return toBuilder().build();
  }

  public Collection<String> getAllProperties() {
    Set<String> hashSet = new HashSet<>();
    hashSet.addAll(newValue.keySet());
    hashSet.addAll(oldValue.keySet());
    return hashSet;
  }

  public Set<String> getDiffProperties() {
    Set<String> hashSet = new HashSet<>();
    hashSet.addAll(diffNewValue.keySet());
    hashSet.addAll(diffOldValue.keySet());
    return hashSet;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LogOperationDetails [");
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
