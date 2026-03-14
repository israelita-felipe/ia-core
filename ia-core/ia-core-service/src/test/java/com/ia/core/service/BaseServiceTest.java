package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

/**
 * Testes para os métodos default de {@link BaseService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BaseService")
class BaseServiceTest {

    @Mock
    private BaseEntityMapper<TestEntity, TestDTO> mapper;

    @Mock
    private BaseEntityRepository<TestEntity> repository;

    @Mock
    private SearchRequestMapper searchRequestMapper;

    @Mock
    private Translator translator;

    private TestBaseService service;

    @BeforeEach
    void setUp() {
        service = new TestBaseService(mapper, repository, searchRequestMapper, translator);
    }

    @Nested
    @DisplayName("toDTO")
    class TestesToDTO {

        @Test
        @DisplayName("Deve mapear entidade para DTO")
        void deveMapearEntidadeParaDTO() {
            // Dado
            TestEntity entity = new TestEntity();
            entity.setId(1L);
            TestDTO dto = new TestDTO();
            dto.setId(1L);
            when(mapper.toDTO(entity)).thenReturn(dto);

            // Quando
            TestDTO result = service.toDTO(entity);

            // Então
            assertThat(result).isEqualTo(dto);
            verify(mapper).toDTO(entity);
        }

        @Test
        @DisplayName("Deve retornar null quando entidade for null")
        void deveRetornarNullQuandoEntidadeForNull() {
            // Quando
            TestDTO result = service.toDTO(null);

            // Então
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("toModel")
    class TestesToModel {

        @Test
        @DisplayName("Deve mapear DTO para entidade")
        void deveMapearDTOParaEntidade() {
            // Dado
            TestDTO dto = new TestDTO();
            dto.setId(1L);
            TestEntity entity = new TestEntity();
            entity.setId(1L);
            when(mapper.toModel(dto)).thenReturn(entity);

            // Quando
            TestEntity result = service.toModel(dto);

            // Então
            assertThat(result).isEqualTo(entity);
            verify(mapper).toModel(dto);
        }

        @Test
        @DisplayName("Deve retornar null quando DTO for null")
        void deveRetornarNullQuandoDTOForNull() {
            // Quando
            TestEntity result = service.toModel(null);

            // Então
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("checkErrors")
    class TestesCheckErrors {

        @Test
        @DisplayName("Deve lançar exceção quando o serviço tiver erros")
        void deveLancarExcecaoQuandoServicoTiverErros() {
            // Dado
            ServiceException exception = new ServiceException();
            exception.add("Erro 1");
            exception.add("Erro 2");

            // Quando & Então
            assertThatThrownBy(() -> service.checkErrors(exception))
                .isInstanceOf(ServiceException.class);
        }

        @Test
        @DisplayName("Não deve lançar quando o serviço não tiver erros")
        void deveNaoLancarQuandoServicoNaoTiverErros() {
            // Dado
            ServiceException exception = new ServiceException();

            // Quando & Então
            service.checkErrors(exception); // Não deve lançar
        }
    }

    @Nested
    @DisplayName("getMapper")
    class TestesGetMapper {

        @Test
        @DisplayName("Deve retornar o mapper")
        void deveRetornarOMapper() {
            // Quando
            Mapper<TestEntity, TestDTO> result = service.getMapper();

            // Então
            assertThat(result).isEqualTo(mapper);
        }
    }

    @Nested
    @DisplayName("getRepository")
    class TestesGetRepository {

        @Test
        @DisplayName("Deve retornar o repositório")
        void deveRetornarORepositorio() {
            // Quando
            BaseEntityRepository<TestEntity> result = service.getRepository();

            // Então
            assertThat(result).isEqualTo(repository);
        }
    }

    @Nested
    @DisplayName("getSearchRequestMapper")
    class TestesGetSearchRequestMapper {

        @Test
        @DisplayName("Deve retornar o search request mapper")
        void deveRetornarOSearchRequestMapper() {
            // Quando
            SearchRequestMapper result = service.getSearchRequestMapper();

            // Então
            assertThat(result).isEqualTo(searchRequestMapper);
        }
    }

    @Nested
    @DisplayName("getTranslator")
    class TestesGetTranslator {

        @Test
        @DisplayName("Deve retornar o tradutor")
        void deveRetornarOTradutor() {
            // Quando
            Translator result = service.getTranslator();

            // Então
            assertThat(result).isEqualTo(translator);
        }
    }

    // Implementação de teste
    static class TestBaseService implements BaseService<TestEntity, TestDTO> {
        private final BaseEntityMapper<TestEntity, TestDTO> mapper;
        private final BaseEntityRepository<TestEntity> repository;
        private final SearchRequestMapper searchRequestMapper;
        private final Translator translator;

        TestBaseService(BaseEntityMapper<TestEntity, TestDTO> mapper,
                        BaseEntityRepository<TestEntity> repository,
                        SearchRequestMapper searchRequestMapper,
                        Translator translator) {
            this.mapper = mapper;
            this.repository = repository;
            this.searchRequestMapper = searchRequestMapper;
            this.translator = translator;
        }

        @Override
        public BaseEntityMapper<TestEntity, TestDTO> getMapper() {
            return mapper;
        }

        @Override
        public BaseEntityRepository<TestEntity> getRepository() {
            return repository;
        }

        @Override
        public SearchRequestMapper getSearchRequestMapper() {
            return searchRequestMapper;
        }

        @Override
        public Translator getTranslator() {
            return translator;
        }

        @Override
        public org.springframework.transaction.PlatformTransactionManager getTransactionManager() {
            return mock(org.springframework.transaction.PlatformTransactionManager.class);
        }
    }

    // Entidades de teste
    static class TestEntity extends BaseEntity {
        private static final long serialVersionUID = 1L;
    }

    static class TestDTO implements DTO<TestEntity> {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public DTO<TestEntity> cloneObject() {
            return this;
        }
    }
}
