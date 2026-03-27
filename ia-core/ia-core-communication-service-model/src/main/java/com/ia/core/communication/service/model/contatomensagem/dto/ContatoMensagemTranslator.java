package com.ia.core.communication.service.model.contatomensagem.dto;

/**
 * Translator para ContatoMensagem.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class ContatoMensagemTranslator {
  public static final class HELP {
    public static final String CONTATO_MENSAGEM = "contato.mensagem.help";
    public static final String GRUPO_CONTATO = "contato.mensagem.help.grupo.contato";
    public static final String TELEFONE = "contato.mensagem.help.telefone";
    public static final String NOME = "contato.mensagem.help.nome";
  }

  public static final String CONTATO_MENSAGEM_CLASS = ContatoMensagemDTO.class.getCanonicalName();
  public static final String CONTATO_MENSAGEM = "contato.mensagem";
  public static final String GRUPO_CONTATO = "contato.mensagem.grupo.contato";
  public static final String TELEFONE = "contato.mensagem.telefone";
  public static final String NOME = "contato.mensagem.nome";

  public static final class VALIDATION {
    public static final String GRUPO_CONTATO_NOT_NULL = "contato.mensagem.validation.grupo.contato.not.null";
    public static final String TELEFONE_NOT_BLANK = "contato.mensagem.validation.telefone.not.blank";
    public static final String TELEFONE_SIZE = "contato.mensagem.validation.telefone.size";
    public static final String NOME_SIZE = "contato.mensagem.validation.nome.size";
  }
}