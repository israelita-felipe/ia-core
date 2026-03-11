package com.ia.core.llm.service.model.template;

import java.util.List;

import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para Template de Prompt.
 * <p>
 * Define as operações específicas do domínio de templates de prompt
 * conforme definido no caso de uso Manter-Template.
 *
 * @author Israel Araújo
 */
public interface TemplateUseCase extends CrudUseCase<TemplateDTO> {

  /**
   * Ativa um template.
   *
   * @param templateId ID do template
   */
  void ativar(Long templateId);

  /**
   * Inativa um template.
   *
   * @param templateId ID do template
   */
  void inativar(Long templateId);

  /**
   * Busca templates por finalidade.
   *
   * @param finalidade finalidade do template
   * @return lista de templates
   */
  List<TemplateDTO> findByFinalidade(String finalidade);
}
