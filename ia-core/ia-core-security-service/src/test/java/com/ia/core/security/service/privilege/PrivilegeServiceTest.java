package com.ia.core.security.service.privilege;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link PrivilegeService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PrivilegeService")
class PrivilegeServiceTest {

    @Mock
    private PrivilegeServiceConfig config;

    @Mock
    private PrivilegeRepository repository;

    @Mock
    private PrivilegeMapper privilegeMapper;

    @Mock
    private SearchRequestMapper searchRequestMapper;

    @Mock
    private Translator translator;

    @Mock
    private CoreSecurityAuthorizationManager authorizationManager;

    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private LogOperationService logOperationService;

    private TestablePrivilegeService service;

    @BeforeEach
    void setUp() {
        lenient().when(config.getRepository()).thenReturn(repository);
        lenient().when(config.getMapper()).thenReturn(privilegeMapper);
        lenient().when(config.getSearchRequestMapper()).thenReturn(searchRequestMapper);
        lenient().when(config.getTranslator()).thenReturn(translator);
        lenient().when(config.getAuthorizationManager()).thenReturn(authorizationManager);
        lenient().when(config.getSecurityContextService()).thenReturn(securityContextService);
        lenient().when(config.getLogOperationService()).thenReturn(logOperationService);
        lenient().when(config.getValidators()).thenReturn(List.of());
        service = new TestablePrivilegeService(config);
    }

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("Should return list of privileges when privileges exist")
        void shouldReturnListOfPrivilegesWhenPrivilegesExist() {
            // Given
            Privilege privilege = new Privilege();
            privilege.setId(1L);
            PrivilegeDTO dto = new PrivilegeDTO();
            dto.setId(1L);

            when(repository.findAll()).thenReturn(List.of(privilege));

            // When
            List<PrivilegeDTO> result = service.findAll();

            // Then
            assertThat(result).isNotNull();
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no privileges exist")
        void shouldReturnEmptyListWhenNoPrivilegesExist() {
            // Given
            when(repository.findAll()).thenReturn(List.of());

            // When
            List<PrivilegeDTO> result = service.findAll();

            // Then
            assertThat(result).isEmpty();
            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("exitsByName")
    class ExistsByNameTests {

        @Test
        @DisplayName("Should return true when privilege exists with given name")
        void shouldReturnTrueWhenPrivilegeExistsWithName() {
            // Given
            String name = "ADMIN";
            when(repository.existsByName(name)).thenReturn(true);

            // When
            boolean result = service.exitsByName(name);

            // Then
            assertThat(result).isTrue();
            verify(repository, times(1)).existsByName(name);
        }

        @Test
        @DisplayName("Should return false when privilege does not exist with given name")
        void shouldReturnFalseWhenPrivilegeDoesNotExistWithName() {
            // Given
            String name = "UNKNOWN";
            when(repository.existsByName(name)).thenReturn(false);

            // When
            boolean result = service.exitsByName(name);

            // Then
            assertThat(result).isFalse();
            verify(repository, times(1)).existsByName(name);
        }

        @Test
        @DisplayName("Should throw exception when name is null")
        void shouldThrowExceptionWhenNameIsNull() {
            // When & Then
            assertThrows(NullPointerException.class, () -> service.exitsByName(null));
        }
    }

    @Nested
    @DisplayName("getConfig")
    class GetConfigTests {

        @Test
        @DisplayName("Should return config from parent class")
        void shouldReturnConfigFromParentClass() {
            // When
            PrivilegeServiceConfig result = service.getConfig();

            // Then
            assertThat(result).isEqualTo(config);
        }
    }

    @Nested
    @DisplayName("getFunctionalityTypeName")
    class GetFunctionalityTypeNameTests {

        @Test
        @DisplayName("Should return ROLE functionality type name")
        void shouldReturnROLEFunctionalityTypeName() {
            // When
            String result = service.getFunctionalityTypeName();

            // Then
            assertThat(result).isEqualTo(PrivilegeTranslator.PRIVILEGE);
        }
    }

    @Nested
    @DisplayName("getRepository")
    class GetRepositoryTests {

        @Test
        @DisplayName("Should return repository cast to PrivilegeRepository")
        void shouldReturnRepositoryCastToPrivilegeRepository() {
            // Given
            when(config.getRepository()).thenReturn(repository);

            // When
            PrivilegeRepository result = service.getRepository();

            // Then
            assertThat(result).isEqualTo(repository);
        }
    }

    private static class TestablePrivilegeService extends PrivilegeService {
        TestablePrivilegeService(PrivilegeServiceConfig config) {
            super(config);
        }
    }
}
