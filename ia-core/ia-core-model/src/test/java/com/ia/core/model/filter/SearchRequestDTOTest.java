package com.ia.core.model.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SearchRequest")
class SearchRequestTest {

    @Nested
    @DisplayName("valores padrão")
    class ValoresPadrao {

        @Test
        @DisplayName("Deve ter page 0 por padrão")
        void deveSerPage0PorPadrao() {
            SearchRequest request = SearchRequest.builder().build();
            assertThat(request.getPage()).isEqualTo(0);
        }

        @Test
        @DisplayName("Deve ter size 100 por padrão")
        void deveSerSize100PorPadrao() {
            SearchRequest request = SearchRequest.builder().build();
            assertThat(request.getSize()).isEqualTo(100);
        }

        @Test
        @DisplayName("Deve ter disjunction true por padrão")
        void deveSerDisjunctionTruePorPadrao() {
            SearchRequest request = SearchRequest.builder().build();
            assertThat(request.isDisjunction()).isTrue();
        }

        @Test
        @DisplayName("Deve ter listas vazias por padrão")
        void deveSerListasVaziasPorPadrao() {
            SearchRequest request = SearchRequest.builder().build();
            assertThat(request.getFilters()).isEmpty();
            assertThat(request.getSorts()).isEmpty();
            assertThat(request.getContext()).isEmpty();
        }
    }

    @Nested
    @DisplayName("getFilters null-safe")
    class GetFiltersNullSafe {

        @Test
        @DisplayName("Deve retornar lista vazia quando filters é null")
        void deveRetornarListaVaziaQuandoFiltersNull() {
            SearchRequest request = new SearchRequest();
            request.setFilters(null);
            assertThat(request.getFilters()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Deve retornar lista quando filters tem elementos")
        void deveRetornarListaComElementos() {
            FilterRequest filter = FilterRequest.builder()
                .key("nome").operator(Operator.EQUAL).fieldType(FieldType.STRING).build();
            SearchRequest request = SearchRequest.builder()
                .filters(new ArrayList<>(List.of(filter))).build();
            assertThat(request.getFilters()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("getSorts null-safe")
    class GetSortsNullSafe {

        @Test
        @DisplayName("Deve retornar lista vazia quando sorts é null")
        void deveRetornarListaVaziaQuandoSortsNull() {
            SearchRequest request = new SearchRequest();
            request.setSorts(null);
            assertThat(request.getSorts()).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("getContext null-safe")
    class GetContextNullSafe {

        @Test
        @DisplayName("Deve retornar lista vazia quando context é null")
        void deveRetornarListaVaziaQuandoContextNull() {
            SearchRequest request = new SearchRequest();
            request.setContext(null);
            assertThat(request.getContext()).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("builder")
    class BuilderTest {

        @Test
        @DisplayName("Deve construir SearchRequest com valores personalizados")
        void deveConstruirComValoresPersonalizados() {
            SearchRequest request = SearchRequest.builder()
                .page(2)
                .size(50)
                .disjunction(false)
                .build();

            assertThat(request.getPage()).isEqualTo(2);
            assertThat(request.getSize()).isEqualTo(50);
            assertThat(request.isDisjunction()).isFalse();
        }
    }
}
