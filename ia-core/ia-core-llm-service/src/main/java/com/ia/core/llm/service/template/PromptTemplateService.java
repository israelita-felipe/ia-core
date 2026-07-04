package com.ia.core.llm.service.template;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;

/**
 * Interface para manipulação de Prompt Templates.
 * <p>
 * Abstração para criação e manipulação de templates de prompt.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface PromptTemplateService {

  /**
   * Cria um prompt simples com documento e texto.
   *
   * @param document o documento base
   * @param text a pergunta/texto
   * @return prompt criado
   */
  Prompt createSimplePrompt(String document, String text);

  /**
   * Cria um prompt com template de sistema.
   *
   * @param document o documento base
   * @param text a pergunta/texto
   * @param systemTemplate o template do sistema
   * @param finalidade o tipo de retorno esperado
   * @param params parâmetros adicionais para o template
   * @return prompt criado
   */
  Prompt createSystemPrompt(String document, String text, String systemTemplate,
      FinalidadePromptEnum finalidade, Map<String, Object> params);

  /**
   * Cria uma mensagem a partir de um template de sistema.
   *
   * @param systemTemplate o template do sistema
   * @param params parâmetros para o template
   * @return mensagem criada
   */
  Message createSystemMessage(String systemTemplate, Map<String, Object> params);

  /**
   * Cria um PromptTemplate básico.
   *
   * @param templateString template no formato {placeholder}
   * @return prompt template
   */
  PromptTemplate createPromptTemplate(String templateString);

  /**
   * Processa um template do banco de dados com parâmetros.
   *
   * @param templateId identificador do template no banco
   * @param params mapa de parâmetros para substituição no template
   * @return texto do template processado com parâmetros substituídos
   */
  String processTemplate(String templateId, Map<String, Object> params);

  /**
   * Recupera um template do banco de dados pelo identificador.
   *
   * @param templateId identificador do template no banco
   * @return texto do template original
   */
  String getTemplateById(String templateId);
}
