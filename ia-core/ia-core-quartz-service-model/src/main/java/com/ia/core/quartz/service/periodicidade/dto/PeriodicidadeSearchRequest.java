package com.ia.core.quartz.service.periodicidade.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * @author Israel Araújo
 */
class PeriodicidadeSearchRequest
  extends SearchRequestDTO {
  /** Filtros */
  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>() {
    /** Serial UID */
    private static final long serialVersionUID = -8351015313648757622L;
    /**
     * Ativo
     */
    {
      put(FilterProperty.builder().label("Ativo").property("ativo").build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("ativo")
                            .fieldType(FieldTypeDTO.BOOLEAN)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("ativo")
                            .fieldType(FieldTypeDTO.BOOLEAN)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Dia Todo
     */
    {
      put(FilterProperty.builder().label("Dia todo").property("diaTodo")
          .build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("diaTodo")
                            .fieldType(FieldTypeDTO.BOOLEAN)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("diaTodo")
                            .fieldType(FieldTypeDTO.BOOLEAN)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Periódico
     */
    {
      put(FilterProperty.builder().label("Periódico").property("periodico")
          .build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("periodico")
                            .fieldType(FieldTypeDTO.BOOLEAN)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("periodico")
                            .fieldType(FieldTypeDTO.BOOLEAN)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Data de início
     */
    {
      put(FilterProperty.builder().label("Data de início")
          .property("dataInicio").build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("dataInicio")
                            .fieldType(FieldTypeDTO.DATE)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("dataInicio")
                            .fieldType(FieldTypeDTO.DATE)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Data de fim
     */
    {
      put(FilterProperty.builder().label("Data de fim").property("dataFim")
          .build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("dataFim")
                            .fieldType(FieldTypeDTO.DATE)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("dataFim")
                            .fieldType(FieldTypeDTO.DATE)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Hora de início
     */
    {
      put(FilterProperty.builder().label("Hora de início")
          .property("horaInicio").build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("horaInicio")
                            .fieldType(FieldTypeDTO.TIME)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("horaInicio")
                            .fieldType(FieldTypeDTO.TIME)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Hora de fim
     */
    {
      put(FilterProperty.builder().label("Hora de fim").property("horaFim")
          .build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("horaFim")
                            .fieldType(FieldTypeDTO.TIME)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("horaFim")
                            .fieldType(FieldTypeDTO.TIME)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
  };

  /**
   * Construtor padrão
   */
  protected PeriodicidadeSearchRequest() {
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
