package com.ia.core.flyway;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.flywayexecution.AbstractFlywayExecutionService;
import com.ia.core.flyway.service.flywayexecution.FlywayExecutionServiceConfig;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FlywayExecutionServiceTest {

    private TestableFlywayExecutionService service;

    @BeforeEach
    void setUp() {
        var config = mock(FlywayExecutionServiceConfig.class);
        service = new TestableFlywayExecutionService(config);
    }

    @Test
    public void shouldAddSuccessFilterWhenListingSuccessfulMigrations() {
        var request = new SearchRequestDTO();

        var page = service.listSuccessful(request);

        assertThat(page).isNotNull();
        assertThat(service.requestCaptured()).isSameAs(request);
        assertThat(request.getContext()).anySatisfy(filter -> {
            assertThat(filter.getKey()).isEqualTo("success");
            assertThat(filter.getFieldType()).isEqualTo(FieldType.BOOLEAN);
            assertThat(filter.getOperator()).isEqualTo(OperatorDTO.EQUAL);
            assertThat(filter.getValue()).isEqualTo(Boolean.TRUE);
        });
    }

    @Test
    public void shouldAddFailureFilterWhenListingFailedMigrations() {
        var request = new SearchRequestDTO();

        var page = service.listFailed(request);

        assertThat(page).isNotNull();
        assertThat(service.requestCaptured()).isSameAs(request);
        assertThat(request.getContext()).anySatisfy(filter -> {
            assertThat(filter.getKey()).isEqualTo("success");
            assertThat(filter.getFieldType()).isEqualTo(FieldType.BOOLEAN);
            assertThat(filter.getOperator()).isEqualTo(OperatorDTO.EQUAL);
            assertThat(filter.getValue()).isEqualTo(Boolean.FALSE);
        });
    }

    private static class TestableFlywayExecutionService
        extends AbstractFlywayExecutionService<FlywayExecution, FlywayExecutionDTO<FlywayExecution>> {

        private SearchRequestDTO requestCaptured;

        TestableFlywayExecutionService(FlywayExecutionServiceConfig<FlywayExecution, FlywayExecutionDTO<FlywayExecution>> config) {
            super(config);
        }

        @Override
        public Page<FlywayExecutionDTO<FlywayExecution>> findAll(SearchRequestDTO requestDTO) {
            requestCaptured = requestDTO;
            return Page.empty();
        }

        SearchRequestDTO requestCaptured() {
            return requestCaptured;
        }
    }
}
