package com.ia.core.rest;

import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.util.JsonUtil;
import com.ia.test.CoreBaseUnitTest;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Base class for API tests.
 * Provides common functionality and configuration for REST API tests.
 * Uses MockMvc for HTTP request/response testing.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 * Subclasses must override {@link #createController()}, {@link #resourceUrl()}, and {@link #createDto()}.
 *
 * <p>Characteristics:
 * - MockMvc for HTTP testing without full server startup
 * - ObjectMapper for JSON serialization/deserialization
 * - Auto-configuration of MockMvc
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @SpringBootTest(classes = MyApplication.class)
 * @AutoConfigureMockMvc
 * class MyAPITest extends CoreAPIBase {
 *     @Override
 *     protected Object createController() {
 *         return new MyController();
 *     }
 *
 *     @Override
 *     protected String resourceUrl() {
 *         return "resource";
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@Disabled("Abstract base class - not a test")
@ExtendWith(MockitoExtension.class)
@DisplayName("API Base")
public abstract class CoreAPIBase extends CoreBaseUnitTest {

     protected MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(createController()).addPlaceholderValue("api.version", this.apiVersion).build();
    }

    protected abstract Object createController();

    @Value("${api.version}")
    @Getter
    private String apiVersion = "v1.0.0";

    protected abstract String resourceUrl();

    /**
     * Converts an object to JSON string.
     *
     * @param object the object to convert
     * @return JSON string representation
     */
    protected String toJson(Object object) {
        try {
            return JsonUtil.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * Converts a JSON string to an object of the specified type.
     *
     * @param json the JSON string
     * @param clazz the target class
     * @param <T> the type of the class
     * @return the deserialized object
     */
    protected <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JsonUtil.fromJson(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }


    protected AbstractBaseEntityDTO createDto() {
        return createFixture(AbstractBaseEntityDTO.class);
    }

    /**
     * Default test for listing all resources.
     * Can be overridden in subclasses for specific behavior.
     *
     * @throws Exception if test execution fails
     */
    protected void deveListarTodos() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/"+this.apiVersion+"/"+this.resourceUrl()+"/all")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(createFixture(SearchRequestDTO.class))))
            .andExpect(status().isOk());
    }

    /**
     * Default test for finding by ID.
     * Can be overridden in subclasses for specific behavior.
     *
     * @throws Exception if test execution fails
     */
    protected void deveBuscarPorId() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/"+this.apiVersion+"/"+this.resourceUrl()+"/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    /**
     * Default test for creating a new resource.
     * Can be overridden in subclasses for specific behavior.
     *
     * @throws Exception if test execution fails
     */
    protected void deveCriarNovo() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/"+this.apiVersion+"/"+this.resourceUrl())
                .contentType(MediaType.APPLICATION_JSON).
            content(toJson(createDto())))
            .andExpect(status().isCreated());
    }

    /**
     * Default test for deleting a resource.
     * Can be overridden in subclasses for specific behavior.
     *
     * @throws Exception if test execution fails
     */
    protected void deveDeletar() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/"+this.apiVersion+"/"+this.resourceUrl()+"/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}
