package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for converting between Privilege/PrivilegeDTO and Functionality.
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface FunctionalityMapper {

  /**
   * Converts a Privilege entity to a Functionality.
   *
   * @param privilege the privilege entity
   * @return the functionality
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
   * Converts a PrivilegeDTO to a Functionality.
   *
   * @param privilegeDTO the privilege DTO
   * @return the functionality
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
