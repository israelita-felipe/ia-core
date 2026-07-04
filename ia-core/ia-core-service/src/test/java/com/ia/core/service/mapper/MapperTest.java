package com.ia.core.service.mapper;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a interface Mapper.
 */
@DisplayName("Mapper Tests")
class MapperTest {

  @Test
  @DisplayName("toDTOList deve retornar lista vazia quando entrada for null")
  void testToDTOListWithNullInput() {
    TestMapper mapper = new TestMapper();
    List<TestDTO> result = mapper.toDTOList(null);
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toDTOList deve retornar lista vazia quando entrada for vazia")
  void testToDTOListWithEmptyInput() {
    TestMapper mapper = new TestMapper();
    List<TestDTO> result = mapper.toDTOList(Collections.emptyList());
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toDTOList deve mapear lista de entidades para DTOs")
  void testToDTOListWithValidInput() {
    TestMapper mapper = new TestMapper();
    List<TestEntity> entities = new ArrayList<>();
    entities.add(new TestEntity(1L, "Test1"));
    entities.add(new TestEntity(2L, "Test2"));

    List<TestDTO> result = mapper.toDTOList(entities);
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).id).isEqualTo(1L);
    assertThat(result.get(1).id).isEqualTo(2L);
  }

  @Test
  @DisplayName("toDTOSet deve retornar set vazio quando entrada for null")
  void testToDTOSetWithNullInput() {
    TestMapper mapper = new TestMapper();
    Set<TestDTO> result = mapper.toDTOSet(null);
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toDTOSet deve retornar set vazio quando entrada for vazia")
  void testToDTOSetWithEmptyInput() {
    TestMapper mapper = new TestMapper();
    Set<TestDTO> result = mapper.toDTOSet(Collections.emptySet());
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toDTOSet deve mapear set de entidades para DTOs")
  void testToDTOSetWithValidInput() {
    TestMapper mapper = new TestMapper();
    Set<TestEntity> entities = new HashSet<>();
    entities.add(new TestEntity(1L, "Test1"));
    entities.add(new TestEntity(2L, "Test2"));

    Set<TestDTO> result = mapper.toDTOSet(entities);
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
  }

  @Test
  @DisplayName("toModelList deve retornar lista vazia quando entrada for null")
  void testToModelListWithNullInput() {
    TestMapper mapper = new TestMapper();
    List<TestEntity> result = mapper.toModelList(null);
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toModelList deve retornar lista vazia quando entrada for vazia")
  void testToModelListWithEmptyInput() {
    TestMapper mapper = new TestMapper();
    List<TestEntity> result = mapper.toModelList(Collections.emptyList());
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toModelList deve mapear lista de DTOs para entidades")
  void testToModelListWithValidInput() {
    TestMapper mapper = new TestMapper();
    List<TestDTO> dtos = new ArrayList<>();
    dtos.add(new TestDTO(1L, "Test1"));
    dtos.add(new TestDTO(2L, "Test2"));

    List<TestEntity> result = mapper.toModelList(dtos);
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).id).isEqualTo(1L);
    assertThat(result.get(1).id).isEqualTo(2L);
  }

  @Test
  @DisplayName("toModelSet deve retornar set vazio quando entrada for null")
  void testToModelSetWithNullInput() {
    TestMapper mapper = new TestMapper();
    Set<TestEntity> result = mapper.toModelSet(null);
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toModelSet deve retornar set vazio quando entrada for vazia")
  void testToModelSetWithEmptyInput() {
    TestMapper mapper = new TestMapper();
    Set<TestEntity> result = mapper.toModelSet(Collections.emptySet());
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("toModelSet deve mapear set de DTOs para entidades")
  void testToModelSetWithValidInput() {
    TestMapper mapper = new TestMapper();
    Set<TestDTO> dtos = new HashSet<>();
    dtos.add(new TestDTO(1L, "Test1"));
    dtos.add(new TestDTO(2L, "Test2"));

    Set<TestEntity> result = mapper.toModelSet(dtos);
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
  }

  static class TestMapper implements Mapper<TestEntity, TestDTO> {
    @Override
    public TestDTO toDTO(TestEntity entity) {
      if (entity == null) return null;
      return new TestDTO(entity.id, entity.name);
    }

    @Override
    public TestEntity toModel(TestDTO dto) {
      if (dto == null) return null;
      return new TestEntity(dto.id, dto.name);
    }
  }

  static class TestEntity implements Serializable {
    Long id;
    String name;

    TestEntity(Long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  static class TestDTO implements DTO<TestEntity> {
    Long id;
    String name;

    TestDTO(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public TestDTO cloneObject() {
      return new TestDTO(this.id, this.name);
    }
  }
}
