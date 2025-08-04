package com.ia.core.service.attachment.dto;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class AttachmentTranslator {
  public static final class HELP {
    public static final String ATTACHMENT = "attachment.help";
    public static final String NOME = "attachment.help.filename";
    public static final String DESCRICAO = "attachment.help.description";
    public static final String TAMANHO = "attachment.help.size";
    public static final String TIPO = "attachment.help.media.type";
    public static final String ADD_MANY = "attachment.help.add.many";
    public static final String ADD_ONE = "attachment.help.add.one";
    public static final String DROP_MANY = "attachment.help.drop.many";
    public static final String DROP_ONE = "attachment.help.drop.one";
    public static final String FILE_TOO_BIG = "attachment.help.file.too.big";
    public static final String INCORRECT_FILE_TYPE = "attachment.help.incorrect.file.type";
    public static final String TOO_MANY_FILES = "attachment.help.too.many.files";
    public static final String FILE_REMOVE = "attachment.help.file.remove";
    public static final String FILE_RETRY = "attachment.help.file.retry";
    public static final String FILE_START = "attachment.help.file.start";
    public static final String STATUS_CONECTANDO = "attachment.help.status.connecting";
    public static final String STATUS_HELD = "attachment.help.status.held";
    public static final String STATUS_PROCESSING = "attachment.help.status.processing";
    public static final String STATUS_STALLED = "attachment.help.status.stalled";
    public static final String REMAINING_TIME_PREFIX = "attachment.help.remaining.time.prefix";
    public static final String REMAINING_TIME_UNKNOW = "attachment.help.remaining.time.unknow";
    public static final String FORBIDDEN = "attachment.help.forbidden";
    public static final String SERVER_UNAVALIABLE = "attachment.help.server.unavaliable";
    public static final String UNEXPECTED_SERVER_ERROR = "attachment.help.unexpected.server.error";
  }

  public static final class VALIDATION {
    public static final String FILE_NAME_NOT_NULL = "attachment.validation.filename.not.null";
    public static final String SIZE_NOT_NULL = "attachment.validation.size.not.null";
    public static final String MEDIA_TYPE_NOT_NULL = "attachment.validation.mediatype.not.null";
    public static final String CONTENT_NOT_NULL = "attachment.validation.content.not.null";
  }

  public static final String ATTACHMENT_CLASS = AttachmentDTO.class
      .getCanonicalName();
  public static final String ATTACHMENT = "attachment";
  public static final String NOME = "attachment.filename";
  public static final String DESCRICAO = "attachment.description";
  public static final String TAMANHO = "attachment.size";
  public static final String TIPO = "attachment.media.type";
  public static final String ADD_MANY = "attachment.add.many";
  public static final String ADD_ONE = "attachment.add.one";
  public static final String DROP_MANY = "attachment.drop.many";
  public static final String DROP_ONE = "attachment.drop.one";
  public static final String FILE_TOO_BIG = "attachment.file.too.big";
  public static final String INCORRECT_FILE_TYPE = "attachment.incorrect.file.type";
  public static final String TOO_MANY_FILES = "attachment.too.many.files";
  public static final String FILE_REMOVE = "attachment.file.remove";
  public static final String FILE_RETRY = "attachment.file.retry";
  public static final String FILE_START = "attachment.file.start";
  public static final String STATUS_CONECTANDO = "attachment.status.connecting";
  public static final String STATUS_HELD = "attachment.status.held";
  public static final String STATUS_PROCESSING = "attachment.status.processing";
  public static final String STATUS_STALLED = "attachment.status.stalled";
  public static final String REMAINING_TIME_PREFIX = "attachment.remaining.time.prefix";
  public static final String REMAINING_TIME_UNKNOW = "attachment.remaining.time.unknow";
  public static final String FORBIDDEN = "attachment.forbidden";
  public static final String SERVER_UNAVALIABLE = "attachment.server.unavaliable";
  public static final String UNEXPECTED_SERVER_ERROR = "attachment.unexpected.server.error";
}
