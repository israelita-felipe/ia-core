# Templates de Testes Spring Boot

Este documento contém templates completos para testes unitários e de integração em aplicações Spring Boot.

---

## 1. Dependências do pom.xml

```xml
<!-- Dependências de teste -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Banco H2 para testes -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- Lombok (opcional) -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

---

## 2. Arquivo de Propriedades para Teste

### src/test/resources/application-test.properties

```properties
# ============================================
# Configuração do Banco de Dados H2 em Memória
# ============================================
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# ============================================
# Configuração JPA/Hibernate
# ============================================
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# ============================================
# Configuração do Flyway (desabilitado para testes)
# ============================================
spring.flyway.enabled=false

# ============================================
# Configuração de Log (reduzir ruído)
# ============================================
logging.level.root=WARN
logging.level.com.exemplo.projeto=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# ============================================
# Configuração de Servidor
# ============================================
server.port=0
```

---

## 3. Estrutura de Pacotes de Teste

```
src/test/java/com/exemplo/projeto/
├── controller/
│   └── MeuControllerTest.java
├── service/
│   ├── MeuServiceTest.java
│   └── usecase/
│       ├── ReadOnlyUseCaseTest.java
│       └── CrudUseCaseTest.java
├── repository/
│   └── MeuRepositoryTest.java
└── integration/
    └── MeuIntegrationTest.java
```

---

## 4. Modelo de Exemplo

### src/main/java/com/exemplo/projeto/model/MeuModelo.java

```java
package com.exemplo.projeto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_meu_modelo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeuModelo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

---

## 5. DTO de Exemplo

### src/main/java/com/exemplo/projeto/dto/MeuModeloDTO.java

```java
package com.exemplo.projeto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeuModeloDTO implements Serializable {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
```

---

## 6. Teste de Service (Unitário)

### src/test/java/com/exemplo/projeto/service/MeuServiceTest.java

```java
package com.exemplo.projeto.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.projeto.dto.MeuModeloDTO;
import com.exemplo.projeto.exception.ResourceNotFoundException;
import com.exemplo.projeto.mapper.MeuModeloMapper;
import com.exemplo.projeto.model.MeuModelo;
import com.exemplo.projeto.repository.MeuModeloRepository;

/**
 * Testes unitários para MeuService.
 * 
 * <p>Utiliza JUnit 5 e Mockito para testes de unidade.</p>
 * 
 * @author Exemplo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de MeuService")
class MeuServiceTest {

    @Mock
    private MeuModeloRepository repository;

    @Mock
    private MeuModeloMapper mapper;

    @InjectMocks
    private MeuService service;

    private MeuModelo modelo;
    private MeuModeloDTO dto;

    /**
     * Configuração inicial para cada teste.
     */
    @BeforeEach
    void setUp() {
        modelo = MeuModelo.builder()
            .id(1L)
            .nome("Nome de Teste")
            .descricao("Descrição de Teste")
            .build();

        dto = MeuModeloDTO.builder()
            .id(1L)
            .nome("Nome de Teste")
            .descricao("Descrição de Teste")
            .build();
    }

    @Nested
    @DisplayName("Testes de findById")
    class FindByIdTests {

        @Test
        @DisplayName("Deve retornar DTO quando modelo existir")
        void deveRetornarDTQUandoModeloExistir() {
            // Given
            when(repository.findById(1L)).thenReturn(Optional.of(modelo));
            when(mapper.toDTO(modelo)).thenReturn(dto);

            // When
            MeuModeloDTO result = service.findById(1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getNome()).isEqualTo("Nome de Teste");
            verify(repository).findById(1L);
            verify(mapper).toDTO(modelo);
        }

        @Test
        @DisplayName("Deve lançar exceção quando modelo não existir")
        void deveLancarExcecaoQuandoModeloNaoExistir() {
            // Given
            when(repository.findById(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> service.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("não encontrado");
            verify(repository).findById(999L);
        }
    }

    @Nested
    @DisplayName("Testes de save")
    class SaveTests {

        @Test
        @DisplayName("Deve salvar novo modelo quando ID for null")
        void deveSalvarNovoModeloQuandoIdForNull() {
            // Given
            dto.setId(null);
            when(mapper.toModel(dto)).thenReturn(modelo);
            when(mapper.toDTO(modelo)).thenReturn(dto);
            when(repository.save(modelo)).thenReturn(modelo);

            // When
            MeuModeloDTO result = service.save(dto);

            // Then
            assertThat(result).isNotNull();
            verify(mapper).toModel(dto);
            verify(repository).save(modelo);
            verify(mapper).toDTO(modelo);
        }

        @Test
        @DisplayName("Deve atualizar modelo existente quando ID não for null")
        void deveAtualizarModeloExistenteQuandoIdNaoForNull() {
            // Given
            when(mapper.toModel(dto)).thenReturn(modelo);
            when(mapper.toDTO(modelo)).thenReturn(dto);
            when(repository.save(modelo)).thenReturn(modelo);

            // When
            MeuModeloDTO result = service.save(dto);

            // Then
            assertThat(result).isNotNull();
            verify(mapper).toModel(dto);
            verify(repository).save(modelo);
        }
    }

    @Nested
    @DisplayName("Testes de delete")
    class DeleteTests {

        @Test
        @DisplayName("Deve excluir modelo quando existir")
        void deveExcluirModeloQuandoExistir() {
            // Given
            when(repository.findById(1L)).thenReturn(Optional.of(modelo));
            doNothing().when(repository).delete(modelo);

            // When
            service.delete(1L);

            // Then
            verify(repository).findById(1L);
            verify(repository).delete(modelo);
        }

        @Test
        @DisplayName("Deve lançar exceção quando modelo não existir")
        void deveLancarExcecaoQuandoModeloNaoExistir() {
            // Given
            when(repository.findById(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class);
            verify(repository).findById(999L);
            verify(repository, never()).delete(any());
        }
    }
}
```

---

## 7. Teste de Controller (Slice Test)

### src/test/java/com/exemplo/projeto/controller/MeuControllerTest.java

```java
package com.exemplo.projeto.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.exemplo.projeto.dto.MeuModeloDTO;
import com.exemplo.projeto.exception.GlobalExceptionHandler;
import com.exemplo.projeto.exception.ResourceNotFoundException;
import com.exemplo.projeto.service.MeuService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes de slice para MeuController.
 * 
 * <p>Utiliza @WebMvcTest para testar apenas a camada web.</p>
 * 
 * @author Exemplo
 */
@WebMvcTest(controllers = { MeuController.class, GlobalExceptionHandler.class })
@DisplayName("Testes de MeuController")
class MeuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeuService service;

    private static final String BASE_URL = "/api/v1/modelos";

    @Test
    @DisplayName("Deve retornar 200 e lista de modelos")
    void deveRetornar200EListaDeModelos() throws Exception {
        // Given
        MeuModeloDTO dto = MeuModeloDTO.builder()
            .id(1L)
            .nome("Modelo 1")
            .descricao("Descrição 1")
            .build();

        when(service.findAll(any())).thenReturn(new PageImpl<>(List.of(dto)));

        // When & Then
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].nome").value("Modelo 1"));
    }

    @Test
    @DisplayName("Deve retornar 200 e modelo pelo ID")
    void deveRetornar200EModeloPeloId() throws Exception {
        // Given
        MeuModeloDTO dto = MeuModeloDTO.builder()
            .id(1L)
            .nome("Modelo 1")
            .descricao("Descrição 1")
            .build();

        when(service.findById(1L)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nome").value("Modelo 1"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando modelo não existir")
    void deveRetornar404QuandoModeloNaoExistir() throws Exception {
        // Given
        when(service.findById(999L))
            .thenThrow(new ResourceNotFoundException("Modelo não encontrado"));

        // When & Then
        mockMvc.perform(get(BASE_URL + "/999")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 201 ao criar modelo")
    void deveRetornar201AoCriarModelo() throws Exception {
        // Given
        MeuModeloDTO requestDto = MeuModeloDTO.builder()
            .nome("Novo Modelo")
            .descricao("Nova Descrição")
            .build();

        MeuModeloDTO responseDto = MeuModeloDTO.builder()
            .id(1L)
            .nome("Novo Modelo")
            .descricao("Nova Descrição")
            .build();

        when(service.save(any(MeuModeloDTO.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nome").value("Novo Modelo"));
    }

    @Test
    @DisplayName("Deve retornar 204 ao excluir modelo")
    void deveRetornar204AoExcluirModelo() throws Exception {
        // Given
        doNothing().when(service).delete(1L);

        // When & Then
        mockMvc.perform(delete(BASE_URL + "/1"))
            .andExpect(status().isNoContent());
        verify(service).delete(1L);
    }
}
```

---

## 8. Teste de Repository (Slice Test)

### src/test/java/com/exemplo/projeto/repository/MeuRepositoryTest.java

```java
package com.exemplo.projeto.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.exemplo.projeto.model.MeuModelo;

/**
 * Testes de repository usando @DataJpaTest.
 * 
 * <p>Utiliza banco H2 em memória configurado em application-test.properties.</p>
 * 
 * @author Exemplo
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes de MeuRepository")
class MeuRepositoryTest {

    @Autowired
    private MeuModeloRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar e buscar modelo pelo ID")
    void deveSalvarEBuscarModeloPeloId() {
        // Given
        MeuModelo modelo = MeuModelo.builder()
            .nome("Modelo de Teste")
            .descricao("Descrição de Teste")
            .build();
        MeuModelo saved = repository.save(modelo);

        // When
        Optional<MeuModelo> result = repository.findById(saved.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("Modelo de Teste");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver registros")
    void deveRetornarListaVaziaQuandoNaoHouverRegistros() {
        // When
        var result = repository.findAll();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve excluir modelo")
    void deveExcluirModelo() {
        // Given
        MeuModelo modelo = MeuModelo.builder()
            .nome("Modelo de Teste")
            .descricao("Descrição de Teste")
            .build();
        MeuModelo saved = repository.save(modelo);

        // When
        repository.deleteById(saved.getId());

        // Then
        Optional<MeuModelo> result = repository.findById(saved.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar modelos por nome")
    void deveBuscarModelosPorNome() {
        // Given
        MeuModelo modelo1 = MeuModelo.builder()
            .nome("Produto A")
            .descricao("Descrição A")
            .build();
        MeuModelo modelo2 = MeuModelo.builder()
            .nome("Produto B")
            .descricao("Descrição B")
            .build();
        repository.save(modelo1);
        repository.save(modelo2);

        // When
        var result = repository.findByNomeContainingIgnoreCase("Produto");

        // Then
        assertThat(result).hasSize(2);
    }
}
```

---

## 9. Teste de Integração

### src/test/java/com/exemplo/projeto/integration/MeuIntegrationTest.java

```java
package com.exemplo.projeto.integration;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.exemplo.projeto.dto.MeuModeloDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes de integração completos.
 * 
 * <p>Testa o fluxo completo: criar e consultar.</p>
 * 
 * @author Exemplo
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Testes de Integração - MeuModelo")
class MeuIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/modelos";

    @Test
    @DisplayName("Deve criar e consultar modelo - Fluxo completo")
    void deveCriarEConsultarModelo_fluxoCompleto() throws Exception {
        // 1. Criar modelo
        MeuModeloDTO requestDto = MeuModeloDTO.builder()
            .nome("Modelo Integração")
            .descricao("Descrição para teste de integração")
            .build();

        MvcResult createResult = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andReturn();

        // Extrair ID do modelo criado
        String responseJson = createResult.getResponse().getContentAsString();
        MeuModeloDTO responseDto = objectMapper.readValue(responseJson, MeuModeloDTO.class);
        Long createdId = responseDto.getId();

        // 2. Consultar modelo criado
        mockMvc.perform(get(BASE_URL + "/" + createdId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(createdId))
            .andExpect(jsonPath("$.nome").value("Modelo Integração"))
            .andExpect(jsonPath("$.descricao").value("Descrição para teste de integração"));
    }

    @Test
    @DisplayName("Deve listar modelos - Retornar página vazia inicialmente")
    void deveListarModelos_retornarPaginaVaziaInicialmente() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @DisplayName("Deve retornar 404 para modelo inexistente")
    void deveRetornar404ParaModeloInexistente() throws Exception {
        mockMvc.perform(get(BASE_URL + "/99999")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
```

---

## 10. Testes de UseCase (Herança)

### src/test/java/com/exemplo/projeto/service/usecase/ReadOnlyUseCaseTest.java

```java
package com.exemplo.projeto.service.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.projeto.dto.MeuModeloDTO;
import com.exemplo.projeto.mapper.MeuModeloMapper;
import com.exemplo.projeto.model.MeuModelo;
import com.exemplo.projeto.repository.MeuModeloRepository;

/**
 * Classe abstrata para testes de UseCase somente leitura.
 * 
 * <p>Fornece testes genéricos para operações de leitura.</p>
 * 
 * @param <T> Tipo da entidade
 * @param <D> Tipo do DTO
 * @author Exemplo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de ReadOnlyUseCase")
public abstract class ReadOnlyUseCaseTest<T, D> {

    @Mock
    protected MeuModeloRepository repository;

    @Mock
    protected MeuModeloMapper mapper;

    /**
     * Retorna o serviço que implementa o UseCase.
     */
    protected abstract Object getService();

    /**
     * Cria uma entidade de exemplo.
     */
    protected abstract T createEntity(Long id);

    /**
     * Cria um DTO de exemplo.
     */
    protected abstract D createDTO(Long id);

    /**
     * Obtém o ID de um DTO.
     */
    protected abstract Long getDtoId(D dto);

    @Nested
    @DisplayName("Testes de find(Long)")
    class FindTests {

        @Test
        @DisplayName("Deve retornar DTO quando entidade existir")
        void deveRetornarDTOQuandoEntidadeExistir() {
            // Given
            T entity = createEntity(1L);
            D dto = createDTO(1L);
            
            when(repository.findById(1L)).thenReturn(Optional.of(entity));
            when(mapper.toDTO(entity)).thenReturn(dto);

            // When
            Object result = getService();

            // Then
            assertNotNull(result);
        }
    }
}
```

---

## 11. Observações Importantes

### Configurações Recomendadas

1. **Use AssertJ** para asserções mais legíveis
2. **Use @Nested** para organizar testes em classes internas
3. **Use @DisplayName** para descrições claras nos testes
4. **Use @BeforeEach** para configuração inicial
5. **Use @ActiveProfiles("test")** para perfil de teste
6. **Evite @MockBean** em testes de unidade (use @Mock)
7. **Use @DataJpaTest** para testes de repository

### Nomenclatura de Métodos de Teste

Utilize o padrão: `deve[ Ação ]quando[ Condição ]`

Exemplos:
- `deveRetornarDTQUandoEntidadeExistir`
- `deveLancarExcecaoQuandoEntidadeNaoExistir`
- `deveSalvarNovoModeloQuandoIdForNull`

### Boas Práticas

1. **Given-When-Then**: Estruture seus testes com essas três seções
2. **Um teste, uma afirmação**: Prefira múltiplas verificações simples
3. **Mocks devem ser verificados**: Always verify your mocks
4. **Cenários de sucesso e falha**: Sempre teste ambos os casos
5. **Testes determinísticos**: Evite dependências de tempo ou aleatoriedade
