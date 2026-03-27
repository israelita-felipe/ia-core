package com.ia.core.communication.service.model.modelomensagem.dto;

/**
 * Translator para ModeloMensagem.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class ModeloMensagemTranslator {
  public static final class HELP {
    public static final String MODELO_MENSAGEM = "modelo.mensagem.help";
    public static final String NOME = "modelo.mensagem.help.nome";
    public static final String DESCRICAO = "modelo.mensagem.help.descricao";
    public static final String CORPO_MODELO = "modelo.mensagem.help.corpo.modelo";
    public static final String TIPO_CANAL = "modelo.mensagem.help.tipo.canal";
    public static final String ATIVO = "modelo.mensagem.help.ativo";
  }

  public static final String MODELO_MENSAGEM_CLASS = ModeloMensagemDTO.class.getCanonicalName();
  public static final String MODELO_MENSAGEM = "modelo.mensagem";
  public static final String NOME = "modelo.mensagem.nome";
  public static final String DESCRICAO = "modelo.mensagem.descricao";
  public static final String CORPO_MODELO = "modelo.mensagem.corpo.modelo";
  public static final String TIPO_CANAL = "modelo.mensagem.tipo.canal";
  public static final String ATIVO = "modelo.mensagem.ativo";

  public static final class VALIDATION {
    public static final String NOME_NOT_BLANK = "modelo.mensagem.validation.nome.not.blank";
    public static final String NOME_SIZE = "modelo.mensagem.validation.nome.size";
    public static final String DESCRICAO_SIZE = "modelo.mensagem.validation.descricao.size";
    public static final String CORPO_MODELO_NOT_BLANK = "modelo.mensagem.validation.corpo.modelo.not.blank";
    public static final String TIPO_CANAL_NOT_NULL = "modelo.mensagem.validation.tipo.canal.not.null";
  }
}