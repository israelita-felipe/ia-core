package com.ia.core.service.dto.request;

import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.test.CoreBaseUnitTest;
import org.instancio.Select;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SearchRequestDTO")
class SearchRequestDTOTestCore extends CoreBaseUnitTest {

    @Nested
    @DisplayName("construtor padrão")
    class ConstrutorPadrao {

        @Test
        @DisplayName("CT001 - Deve criar requisição com campos padrão")
        void deveCriarRequisicaoCamposPadrao() {
            // Act
            SearchRequestDTO request = SearchRequestDTO.builder().build();

            // Assert
            assertThat(request.getFilters()).isEmpty();
            assertThat(request.getContext()).isEmpty();
            assertThat(request.getSorts()).isEmpty();
        }
    }

    @Nested
    @DisplayName("filtros")
    class Filtros {

        @Test
        @DisplayName("CT002 - Deve adicionar filtros")
        void deveAdicionarFiltros() {
            // Arrange
            FilterRequestDTO filtro = FilterRequestDTO.builder()
                .key("nome")
                .operator(OperatorDTO.EQUAL)
                .value("João")
                .build();
            List<FilterRequestDTO> filters = List.of(filtro);

            // Act
            SearchRequestDTO request = createFixture(SearchRequestDTO.class,
                Select.field(SearchRequestDTO::getFilters), filters);

            // Assert
            assertThat(request.getFilters()).hasSize(1);
            assertThat(request.getFilters().getFirst().getKey()).isEqualTo("nome");
        }
    }

    @Nested
    @DisplayName("paginação")
    class Paginacao {

        @Test
        @DisplayName("CT003 - Deve configurar paginação")
        void deveConfigurarPaginacao() {
            // Arrange
            Integer page = 0;
            Integer size = 10;

            // Act
            SearchRequestDTO request = SearchRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

            // Assert
            assertThat(request.getPage()).isEqualTo(page);
            assertThat(request.getSize()).isEqualTo(size);
        }
    }

    @Nested
    @DisplayName("filtros de contexto")
    class FiltrosContexto {

        @Test
        @DisplayName("CT004 - Deve adicionar filtros de contexto")
        void deveAdicionarFiltrosContexto() {
            // Arrange
            FilterRequestDTO filtroContexto = FilterRequestDTO.builder()
                .key("ativo")
                .operator(OperatorDTO.EQUAL)
                .value(true)
                .build();
            List<FilterRequestDTO> contextFilters = List.of(filtroContexto);

            // Act
            SearchRequestDTO request = SearchRequestDTO.builder()
                .context(contextFilters)
                .build();

            // Assert
            assertThat(request.getContext()).hasSize(1);
            assertThat(request.getContext().getFirst().getKey()).isEqualTo("ativo");
        }
    }

    @Nested
    @DisplayName("disjunção")
    class Disjuncao {

        @Test
        @DisplayName("CT005 - Deve configurar disjunção")
        void deveConfigurarDisjuncao() {
            // Arrange
            boolean disjunction = true;

            // Act
            SearchRequestDTO request = SearchRequestDTO.builder()
                .disjunction(disjunction)
                .build();

            // Assert
            assertThat(request.isDisjunction()).isTrue();
        }
    }

    @Nested
    @DisplayName("createFilters")
    class CreateFilters {

        @Test
        @DisplayName("CT006 - Deve criar mapa de filtros")
        void deveCriarMapaDeFiltros() {
            // Arrange
            Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();
            SearchRequestDTO.createFilters(filterMap, "Nome", "nome",
                com.ia.core.model.filter.FieldType.STRING, OperatorDTO.EQUAL);
            SearchRequestDTO.createFilters(filterMap, "Idade", "idade",
                com.ia.core.model.filter.FieldType.INTEGER, OperatorDTO.GREATER_THAN);

            // Act & Assert
            assertThat(filterMap).hasSize(2);
            assertThat(filterMap).containsKey(FilterProperty.builder().label("Nome").property("nome").build());
            assertThat(filterMap).containsKey(FilterProperty.builder().label("Idade").property("idade").build());
        }
    }

    @Nested
    @DisplayName("sem filtros")
    class SemFiltros {

        @Test
        @DisplayName("CT007 - Deve criar requisição sem filtros")
        void deveCriarRequisicaoSemFiltros() {
            // Act
            SearchRequestDTO request = SearchRequestDTO.builder().build();

            // Assert
            assertThat(request.getFilters()).isEmpty();
            assertThat(request.getContext()).isEmpty();
        }
    }

    @Nested
    @DisplayName("null checks")
    class NullChecks {

        @Test
        @DisplayName("CT008 - getFilters deve retornar lista vazia quando null")
        void getFiltersNull() {
            // Arrange
            SearchRequestDTO request = new SearchRequestDTO();
            request.setFilters(null);

            // Act
            List<FilterRequestDTO> result = request.getFilters();

            // Assert
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("CT009 - getContext deve retornar lista vazia quando null")
        void getContextNull() {
            // Arrange
            SearchRequestDTO request = new SearchRequestDTO();
            request.setContext(null);

            // Act
            List<FilterRequestDTO> result = request.getContext();

            // Assert
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("CT010 - getSorts deve retornar lista vazia quando null")
        void getSortsNull() {
            // Arrange
            SearchRequestDTO request = new SearchRequestDTO();
            request.setSorts(null);

            // Act
            List<com.ia.core.service.dto.sort.SortRequestDTO> result = request.getSorts();

            // Assert
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("createFilter")
    class CreateFilter {

        @Test
        @DisplayName("CT011 - Deve criar filtro único com valor")
        void deveCriarFiltroUnico() {
            // Arrange
            Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

            // Act
            SearchRequestDTO.createFilter(filterMap, "Nome", "nome",
                com.ia.core.model.filter.FieldType.STRING, "João");

            // Assert
            assertThat(filterMap).hasSize(1);
            FilterProperty key = filterMap.keySet().iterator().next();
            assertThat(key.getLabel()).isEqualTo("Nome");
            assertThat(key.getProperty()).isEqualTo("nome");
            Collection<FilterRequestDTO> filters = filterMap.get(key);
            assertThat(filters).hasSize(1);
            FilterRequestDTO filter = filters.iterator().next();
            assertThat(filter.getOperator()).isEqualTo(OperatorDTO.EQUAL);
            assertThat(filter.getValue()).isEqualTo("João");
        }
    }

    @Nested
    @DisplayName("cloneObject")
    class CloneObject {

        @Test
        @DisplayName("CT012 - Deve criar cópia profunda do objeto")
        void deveCriarCopiaProfunda() {
            // Arrange
            FilterRequestDTO filtro = FilterRequestDTO.builder()
                .key("nome")
                .operator(OperatorDTO.EQUAL)
                .value("João")
                .build();
            com.ia.core.service.dto.sort.SortRequestDTO sort = com.ia.core.service.dto.sort.SortRequestDTO.builder()
                .key("nome")
                .direction(com.ia.core.service.dto.sort.SortDirectionDTO.ASC)
                .build();
            SearchRequestDTO original = SearchRequestDTO.builder()
                .filters(List.of(filtro))
                .context(List.of(filtro))
                .sorts(List.of(sort))
                .page(1)
                .size(10)
                .disjunction(false)
                .build();

            // Act
            SearchRequestDTO clone = original.cloneObject();

            // Assert
            assertThat(clone).isNotSameAs(original);
            assertThat(clone.getFilters()).hasSize(1);
            assertThat(clone.getContext()).hasSize(1);
            assertThat(clone.getSorts()).hasSize(1);
            assertThat(clone.getPage()).isEqualTo(1);
            assertThat(clone.getSize()).isEqualTo(10);
            assertThat(clone.isDisjunction()).isFalse();
        }
    }

    @Nested
    @DisplayName("getAvaliableFilters")
    class GetAvaliableFilters {

        @Test
        @DisplayName("CT013 - Deve retornar mapa vazio por padrão")
        void deveRetornarMapaVazio() {
            // Arrange
            SearchRequestDTO request = new SearchRequestDTO();

            // Act
            Map<FilterProperty, Collection<FilterRequestDTO>> result = request.getAvaliableFilters();

            // Assert
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("propertyFilters")
    class PropertyFilters {

        @Test
        @DisplayName("CT014 - Deve retornar conjunto vazio quando não há filtros STRING")
        void deveRetornarConjuntoVazio() {
            // Arrange
            SearchRequestDTO request = new SearchRequestDTO();

            // Act
            Set<String> result = request.propertyFilters();

            // Assert
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("CAMPOS")
    class Campos {

        @Test
        @DisplayName("CT015 - Deve ter constantes definidas")
        void deveTerConstantesDefinidas() {
            // Assert
            assertThat(SearchRequestDTO.CAMPOS.FILTROS).isEqualTo("filters");
            assertThat(SearchRequestDTO.CAMPOS.ORDENACAO).isEqualTo("sorts");
            assertThat(SearchRequestDTO.CAMPOS.PAGINA).isEqualTo("page");
            assertThat(SearchRequestDTO.CAMPOS.TAMANHO).isEqualTo("size");
        }
    }
}
