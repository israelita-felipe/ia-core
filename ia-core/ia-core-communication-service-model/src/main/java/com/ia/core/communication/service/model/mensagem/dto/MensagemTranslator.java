package com.ia.core.communication.service.model.mensagem.dto;

/**
 * Translator para Mensagem.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class MensagemTranslator {
  public static final class HELP {
    public static final String MENSAGEM = "mensagem.help";
    public static final String TELEFONE_DESTINATARIO = "mensagem.help.telefone.destinatario";
    public static final String NOME_DESTINATARIO = "mensagem.help.nome.destinatario";
    public static final String CORPO_MENSAGEM = "mensagem.help.corpo.mensagem";
    public static final String TIPO_CANAL = "mensagem.help.tipo.canal";
    public static final String STATUS_MENSAGEM = "mensagem.help.status.mensagem";
    public static final String ID_EXTERNO = "mensagem.help.id.externo";
    public static final String DATA_ENVIO = "mensagem.help.data.envio";
    public static final String DATA_ENTREGA = "mensagem.help.data.entrega";
    public static final String DATA_LEITURA = "mensagem.help.data.leitura";
    public static final String MOTIVO_FALHA = "mensagem.help.motivo.falha";
  }

  public static final String MENSAGEM_CLASS = MensagemDTO.class.getCanonicalName();
  public static final String MENSAGEM = "mensagem";
  public static final String TELEFONE_DESTINATARIO = "mensagem.telefone.destinatario";
  public static final String NOME_DESTINATARIO = "mensagem.nome.destinatario";
  public static final String CORPO_MENSAGEM = "mensagem.corpo.mensagem";
  public static final String TIPO_CANAL = "mensagem.tipo.canal";
  public static final String STATUS_MENSAGEM = "mensagem.status.mensagem";
  public static final String ID_EXTERNO = "mensagem.id.externo";
  public static final String DATA_ENVIO = "mensagem.data.envio";
  public static final String DATA_ENTREGA = "mensagem.data.entrega";
  public static final String DATA_LEITURA = "mensagem.data.leitura";
  public static final String MOTIVO_FALHA = "mensagem.motivo.falha";

  public static final class VALIDATION {
    public static final String TELEFONE_DESTINATARIO_NOT_BLANK = "mensagem.validation.telefone.destinatario.not.blank";
    public static final String TELEFONE_DESTINATARIO_SIZE = "mensagem.validation.telefone.destinatario.size";
    public static final String NOME_DESTINATARIO_SIZE = "mensagem.validation.nome.destinatario.size";
    public static final String CORPO_MENSAGEM_NOT_BLANK = "mensagem.validation.corpo.mensagem.not.blank";
    public static final String TIPO_CANAL_NOT_NULL = "mensagem.validation.tipo.canal.not.null";
    public static final String STATUS_MENSAGEM_NOT_NULL = "mensagem.validation.status.mensagem.not.null";
    public static final String ID_EXTERNO_SIZE = "mensagem.validation.id.externo.size";
    public static final String MOTIVO_FALHA_SIZE = "mensagem.validation.motivo.falha.size";
  }
}