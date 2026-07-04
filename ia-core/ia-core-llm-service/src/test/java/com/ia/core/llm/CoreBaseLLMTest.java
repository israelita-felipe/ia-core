package com.ia.core.llm;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Base class for LLM (Large Language Model) tests.
 * Provides common functionality and configuration for LLM service tests.
 * Uses Mockito to mock ChatModel for testing without actual LLM calls.
 *
 * <p>Characteristics:
 * - Mockito extension for mocking
 * - Mocked ChatModel to avoid actual LLM API calls
 * - Configurable mock responses
 * - No Spring context (pure unit tests)
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @ExtendWith(MockitoExtension.class)
 * class MyLLMServiceTest extends CoreBaseLLMTest {
 *     @Mock
 *     private ChatModel chatModel;
 *
 *     @InjectMocks
 *     private MyLLMService service;
 *
 *     @Test
 *     void testLLMService() {
 *         when(chatModel.call(any(Prompt.class)))
 *             .thenReturn(mockResponse);
 *         // Your test logic
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LLM Test")
public abstract class CoreBaseLLMTest extends CoreBaseUnitTest {

    /**
     * Creates a mock ChatResponse with the specified content.
     *
     * @param content the response content
     * @return a mock ChatResponse
     */
    protected ChatResponse createMockResponse(String content) {
        ChatResponse response = Mockito.mock(ChatResponse.class);
        when(response.getResult()).thenReturn(Mockito.mock(Generation.class));
        return response;
    }

    /**
     * Configures the ChatModel to return a specific response.
     *
     * @param chatModel the ChatModel to configure
     * @param response the response to return
     */
    protected void configureChatModelResponse(ChatModel chatModel, ChatResponse response) {
        when(chatModel.call(any(Prompt.class))).thenReturn(response);
    }
}
