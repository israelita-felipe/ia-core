package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper para conversão entre entidades de privilégio e funcionalidades.
 * <p>
 * Fornece métodos para converter objetos {@link Privilege} e {@link PrivilegeDTO}
 * em objetos {@link Functionality} que representam funcionalidades do sistema.
 *
 * @author Israel Araújo
 * @see Functionality
 * @see Privilege
 * @see PrivilegeDTO
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface FunctionalityMapper {

  /**
   * Converte uma entidade Privilege para Functionality.
   *
   * @param privilege a entidade Privilege a ser convertida
   * @return a funcionalidade correspondente
   */
  default Functionality toFunctionality(Privilege privilege) {
    if (privilege == null) {
      return null;
    }
    return new Functionality() {
      @Override
      public Set<String> getValues() {
        return privilege.getValues();
      }

      @Override
      public PrivilegeType getType() {
        return privilege.getType();
      }

      @Override
      public String getName() {
        return privilege.getName();
      }
    };
  }

  /**
   * Converte um DTO Privilege para Functionality.
   *
   * @param privilegeDTO o DTO Privilege a ser convertido
   * @return a funcionalidade correspondente
   */
  default Functionality toFunctionality(PrivilegeDTO privilegeDTO) {
    if (privilegeDTO == null) {
      return null;
    }
    return new Functionality() {
      @Override
      public Set<String> getValues() {
        return privilegeDTO.getValues();
      }

      @Override
      public PrivilegeType getType() {
        return privilegeDTO.getType();
      }

      @Override
      public String getName() {
        return privilegeDTO.getName();
      }
    };
  }
}
