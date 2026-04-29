package com.ia.core.security.model.privilege.converter;

import com.ia.core.security.model.privilege.PrivilegeType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa o conversor de dados para privilege type.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeTypeConverter
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Converter(autoApply = true)
public class PrivilegeTypeConverter
  implements AttributeConverter<PrivilegeType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(PrivilegeType attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getCodigo();
  }

  @Override
  public PrivilegeType convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return PrivilegeType.of(dbData);
  }

}
