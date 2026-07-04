package com.ia.core.rest;

import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.util.JsonUtil;
import com.ia.test.CoreBaseUnitTest;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
 * class MyAPITest extends CoreBaseAPITest {
 *     @Test
 *     void testGetEndpoint() throws Exception {
 *         mockMvc.perform(get("/api/resource"))
 *             .andExpect(status().isOk());
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
public abstract class CoreBaseAPITest extends CoreBaseUnitTest {

     protected MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(createController()).addPlaceholderValue("api.version", this.apiVersion).build();
    }

    protected abstract Object createController();

    @Value("${api.version}")
    @Getter
    private String apiVersion = "v1.0.0";

    protected String resourceUrl(){
        return "";
    };

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


    @Test
    @DisplayName("GET /api/${api.version}/{resource}/all")
    protected void deveListarTodos() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/"+this.apiVersion+"/"+this.resourceUrl()+"/all")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(createFixture(SearchRequestDTO.class))))
            .andExpect(status().isOk());
    }

    @DisplayName("GET /api/${api.version}/{resource}/{id}")
    @Test
    protected void deveBuscarPorId() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/"+this.apiVersion+"/"+this.resourceUrl()+"/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    @DisplayName("POST /api/${api.version}/{resource}")
    @Test
    protected void deveCriarNovo() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/"+this.apiVersion+"/"+this.resourceUrl())
                .contentType(MediaType.APPLICATION_JSON).
            content(toJson(createDto())))
            .andExpect(status().isCreated());
    }

    protected AbstractBaseEntityDTO createDto() {
        return createFixture(AbstractBaseEntityDTO.class);
    }


    @DisplayName("DELETE /api/${api.version}/{resource}/{id}")
    @Test
    protected void deveDeletar() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/"+this.apiVersion+"/"+this.resourceUrl()+"/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}
