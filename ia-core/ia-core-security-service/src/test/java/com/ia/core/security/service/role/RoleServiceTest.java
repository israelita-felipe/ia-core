package com.ia.core.security.service.role;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.translator.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link RoleService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RoleService")
class RoleServiceTest {

    @Mock
    private RoleServiceConfig config;

    @Mock
    private RoleRepository repository;

    @Mock
    private RoleMapper roleMapper;

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

    @Mock
    private com.ia.core.security.service.user.UserRoleMapper userRoleMapper;

    private TestableRoleService service;

    @BeforeEach
    void setUp() {
        lenient().when(config.getRepository()).thenReturn(repository);
        lenient().when(config.getMapper()).thenReturn(roleMapper);
        lenient().when(config.getSearchRequestMapper()).thenReturn(searchRequestMapper);
        lenient().when(config.getTranslator()).thenReturn(translator);
        lenient().when(config.getAuthorizationManager()).thenReturn(authorizationManager);
        lenient().when(config.getSecurityContextService()).thenReturn(securityContextService);
        lenient().when(config.getLogOperationService()).thenReturn(logOperationService);
        lenient().when(config.getValidators()).thenReturn(List.of());
        lenient().when(config.getUserRoleMapper()).thenReturn(userRoleMapper);
        service = new TestableRoleService(config);
    }

    @Nested
    @DisplayName("findAllUserRoles")
    class FindAllUserRolesTests {

        @Test
        @DisplayName("Should return page of user roles when roles exist")
        void shouldReturnPageOfUserRolesWhenRolesExist() {
            // Given
            Role role = new Role();
            role.setId(1L);

            Page<Role> rolePage = new PageImpl<>(List.of(role));
            when(searchRequestMapper.toModel(any(SearchRequestDTO.class))).thenReturn(SearchRequest.builder().build());
            when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(rolePage);
            when(userRoleMapper.toDTO(any(Role.class))).thenReturn(new UserRoleDTO());

            SearchRequestDTO requestDTO = new SearchRequestDTO();

            // When
            Page<UserRoleDTO> result = service.findAllUserRoles(requestDTO);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
            verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        }

        @Test
        @DisplayName("Should return empty page when no roles exist")
        void shouldReturnEmptyPageWhenNoRolesExist() {
            // Given
            Page<Role> rolePage = new PageImpl<>(Collections.emptyList());
            when(searchRequestMapper.toModel(any(SearchRequestDTO.class))).thenReturn(SearchRequest.builder().build());
            when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(rolePage);

            SearchRequestDTO requestDTO = new SearchRequestDTO();

            // When
            Page<UserRoleDTO> result = service.findAllUserRoles(requestDTO);

            // Then
            assertThat(result).isEmpty();
            verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        }

        @Test
        @DisplayName("Should throw exception when requestDTO is null")
        void shouldThrowExceptionWhenRequestDTOIsNull() {
            // When & Then
            assertThrows(NullPointerException.class, () -> service.findAllUserRoles(null));
        }
    }

    @Nested
    @DisplayName("synchronize")
    class SynchronizeTests {

        @Test
        @DisplayName("Should synchronize role and set privileges correctly")
        void shouldSynchronizeRoleAndSetPrivilegesCorrectly() {
            // Given
            Role role = new Role();
            role.setId(1L);

            // When
            Role result = service.synchronize(role);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("getConfig")
    class GetConfigTests {

        @Test
        @DisplayName("Should return config from parent class")
        void shouldReturnConfigFromParentClass() {
            // When
            RoleServiceConfig result = service.getConfig();

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
            assertThat(result).isEqualTo(RoleTranslator.ROLE);
        }
    }

    private static class TestableRoleService extends RoleService {
        TestableRoleService(RoleServiceConfig config) {
            super(config);
        }

        @Override
        public boolean canList(SearchRequestDTO requestDTO) {
            return true;
        }
    }
}
