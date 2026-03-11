package com.ia.core.llm.service.model.comando;

import java.util.List;

import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para Comando de Sistema.
 * <p>
 * Define as operações específicas do domínio de comandos de sistema
 * conforme definido no caso de uso Manter-Comando-Sistema.
 *
 * @author Israel Araújo
 */
public interface ComandoSistemaUseCase extends CrudUseCase<ComandoSistemaDTO> {

  /**
   * Ativa um comando de sistema.
   *
   * @param comandoId ID do comando
   */
  void ativar(Long comandoId);

  /**
   * Inativa um comando de sistema.
   *
   * @param comandoId ID do comando
   */
  void inativar(Long comandoId);

  /**
   * Busca comandos por finalidade.
   *
   * @param finalidade finalidade do comando
   * @return lista de comandos
   */
  List<ComandoSistemaDTO> findByFinalidade(String finalidade);

  /**
   * Busca comandos ativos.
   *
   * @return lista de comandos ativos
   */
  List<ComandoSistemaDTO> findAtivos();
}
