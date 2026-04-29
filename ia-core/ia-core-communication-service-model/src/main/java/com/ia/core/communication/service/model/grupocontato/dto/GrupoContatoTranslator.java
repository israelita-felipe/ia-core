package com.ia.core.communication.service.model.grupocontato.dto;

/**
 * Translator para GrupoContato.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio grupo contato translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a GrupoContatoTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuppressWarnings("javadoc")
public class GrupoContatoTranslator {
  public static final class HELP {
    public static final String GRUPO_CONTATO = "grupo.contato.help";
    public static final String NOME = "grupo.contato.help.nome";
    public static final String DESCRICAO = "grupo.contato.help.descricao";
    public static final String ATIVO = "grupo.contato.help.ativo";
  }

  public static final String GRUPO_CONTATO_CLASS = GrupoContatoDTO.class.getCanonicalName();
  public static final String GRUPO_CONTATO = "grupo.contato";
  public static final String NOME = "grupo.contato.nome";
  public static final String DESCRICAO = "grupo.contato.descricao";
  public static final String ATIVO = "grupo.contato.ativo";

  public static final class VALIDATION {
    public static final String NOME_NOT_BLANK = "grupo.contato.validation.nome.not.blank";
    public static final String NOME_SIZE = "grupo.contato.validation.nome.size";
    public static final String DESCRICAO_SIZE = "grupo.contato.validation.descricao.size";
  }
}