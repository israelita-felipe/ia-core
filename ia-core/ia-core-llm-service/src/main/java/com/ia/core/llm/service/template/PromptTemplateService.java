package com.ia.core.llm.service.template;

import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import com.ia.core.llm.model.comando.FinalidadeComandoEnum;

/**
 * Interface para manipulação de Prompt Templates.
 * Abstração para criação e manipulação de templates de prompt.
 *
 * @author Israel Araújo
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
      FinalidadeComandoEnum finalidade, Map<String, Object> params);

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
}
