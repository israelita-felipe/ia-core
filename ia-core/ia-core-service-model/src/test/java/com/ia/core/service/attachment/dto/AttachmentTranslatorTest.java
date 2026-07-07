package com.ia.core.service.attachment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para AttachmentTranslator.
 */
@DisplayName("AttachmentTranslator Tests")
class AttachmentTranslatorTest {

  @Test
  @DisplayName("deve ter construtor privado")
  void testPrivateConstructor() throws Exception {
    var constructor = AttachmentTranslator.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    var instance = constructor.newInstance();
    assertThat(instance).isNotNull();
  }

  @Test
  @DisplayName("HELP deve ter constantes definidas")
  void testHelpConstants() {
    assertThat(AttachmentTranslator.HELP.ATTACHMENT).isEqualTo("attachment.help");
    assertThat(AttachmentTranslator.HELP.NOME).isEqualTo("attachment.help.filename");
    assertThat(AttachmentTranslator.HELP.DESCRICAO).isEqualTo("attachment.help.description");
  }

  @Test
  @DisplayName("VALIDATION deve ter constantes definidas")
  void testValidationConstants() {
    assertThat(AttachmentTranslator.VALIDATION.FILE_NAME_NOT_NULL).isEqualTo("attachment.validation.filename.not.null");
    assertThat(AttachmentTranslator.VALIDATION.SIZE_NOT_NULL).isEqualTo("attachment.validation.size.not.null");
  }

  @Test
  @DisplayName("RULE deve ter constantes definidas")
  void testRuleConstants() {
    assertThat(AttachmentTranslator.RULE.ARQUIVO_MUITO_GRANDE).isEqualTo("attachment.rule.arquivo.muito.grande");
    assertThat(AttachmentTranslator.RULE.LIMITE_EXCEDIDO).isEqualTo("attachment.rule.limite.excedido");
  }

  @Test
  @DisplayName("MESSAGE deve ter constantes definidas")
  void testMessageConstants() {
    assertThat(AttachmentTranslator.MESSAGE.UPLOAD_SUCCESS).isEqualTo("attachment.message.upload.success");
    assertThat(AttachmentTranslator.MESSAGE.REMOVED_SUCCESS).isEqualTo("attachment.message.removed.success");
  }

  @Test
  @DisplayName("EVENT deve ter constantes definidas")
  void testEventConstants() {
    assertThat(AttachmentTranslator.EVENT.ANEXO_ADICIONADO).isEqualTo("attachment.event.anexo.adicionado");
    assertThat(AttachmentTranslator.EVENT.ANEXO_REMOVIDO).isEqualTo("attachment.event.anexo.removido");
  }

  @Test
  @DisplayName("ATTACHMENT_CLASS deve ser definido")
  void testAttachmentClass() {
    assertThat(AttachmentTranslator.ATTACHMENT_CLASS).isEqualTo("com.ia.core.service.attachment.dto.AttachmentDTO");
  }

  @Test
  @DisplayName("HELP deve ter todas as constantes definidas")
  void testAllHelpConstants() {
    assertThat(AttachmentTranslator.HELP.TAMANHO).isEqualTo("attachment.help.size");
    assertThat(AttachmentTranslator.HELP.TIPO).isEqualTo("attachment.help.media.type");
    assertThat(AttachmentTranslator.HELP.ADD_MANY).isEqualTo("attachment.help.add.many");
    assertThat(AttachmentTranslator.HELP.ADD_ONE).isEqualTo("attachment.help.add.one");
    assertThat(AttachmentTranslator.HELP.DROP_MANY).isEqualTo("attachment.help.drop.many");
    assertThat(AttachmentTranslator.HELP.DROP_ONE).isEqualTo("attachment.help.drop.one");
    assertThat(AttachmentTranslator.HELP.FILE_TOO_BIG).isEqualTo("attachment.help.file.too.big");
    assertThat(AttachmentTranslator.HELP.INCORRECT_FILE_TYPE).isEqualTo("attachment.help.incorrect.file.type");
    assertThat(AttachmentTranslator.HELP.TOO_MANY_FILES).isEqualTo("attachment.help.too.many.files");
    assertThat(AttachmentTranslator.HELP.FILE_REMOVE).isEqualTo("attachment.help.file.remove");
    assertThat(AttachmentTranslator.HELP.FILE_RETRY).isEqualTo("attachment.help.file.retry");
    assertThat(AttachmentTranslator.HELP.FILE_START).isEqualTo("attachment.help.file.start");
    assertThat(AttachmentTranslator.HELP.STATUS_CONECTANDO).isEqualTo("attachment.help.status.connecting");
    assertThat(AttachmentTranslator.HELP.STATUS_HELD).isEqualTo("attachment.help.status.held");
    assertThat(AttachmentTranslator.HELP.STATUS_PROCESSING).isEqualTo("attachment.help.status.processing");
    assertThat(AttachmentTranslator.HELP.STATUS_STALLED).isEqualTo("attachment.help.status.stalled");
    assertThat(AttachmentTranslator.HELP.REMAINING_TIME_PREFIX).isEqualTo("attachment.help.remaining.time.prefix");
    assertThat(AttachmentTranslator.HELP.REMAINING_TIME_UNKNOW).isEqualTo("attachment.help.remaining.time.unknow");
    assertThat(AttachmentTranslator.HELP.FORBIDDEN).isEqualTo("attachment.help.forbidden");
    assertThat(AttachmentTranslator.HELP.SERVER_UNAVALIABLE).isEqualTo("attachment.help.server.unavaliable");
    assertThat(AttachmentTranslator.HELP.UNEXPECTED_SERVER_ERROR).isEqualTo("attachment.help.unexpected.server.error");
  }

  @Test
  @DisplayName("VALIDATION deve ter todas as constantes definidas")
  void testAllValidationConstants() {
    assertThat(AttachmentTranslator.VALIDATION.MEDIA_TYPE_NOT_NULL).isEqualTo("attachment.validation.mediatype.not.null");
    assertThat(AttachmentTranslator.VALIDATION.CONTENT_NOT_NULL).isEqualTo("attachment.validation.content.not.null");
  }

  @Test
  @DisplayName("RULE deve ter todas as constantes definidas")
  void testAllRuleConstants() {
    assertThat(AttachmentTranslator.RULE.TIPO_NAO_PERMITADO).isEqualTo("attachment.rule.tipo.nao.permitado");
  }

  @Test
  @DisplayName("MESSAGE deve ter todas as constantes definidas")
  void testAllMessageConstants() {
    assertThat(AttachmentTranslator.MESSAGE.PROCESSING_ERROR).isEqualTo("attachment.message.processing.error");
  }

  @Test
  @DisplayName("deve ter constantes de campo definidas")
  void testFieldConstants() {
    assertThat(AttachmentTranslator.ATTACHMENT).isEqualTo("attachment");
    assertThat(AttachmentTranslator.TAMANHO).isEqualTo("attachment.size");
    assertThat(AttachmentTranslator.TIPO).isEqualTo("attachment.media.type");
    assertThat(AttachmentTranslator.ADD_MANY).isEqualTo("attachment.add.many");
    assertThat(AttachmentTranslator.ADD_ONE).isEqualTo("attachment.add.one");
    assertThat(AttachmentTranslator.DROP_MANY).isEqualTo("attachment.drop.many");
    assertThat(AttachmentTranslator.DROP_ONE).isEqualTo("attachment.drop.one");
    assertThat(AttachmentTranslator.FILE_TOO_BIG).isEqualTo("attachment.file.too.big");
    assertThat(AttachmentTranslator.INCORRECT_FILE_TYPE).isEqualTo("attachment.incorrect.file.type");
    assertThat(AttachmentTranslator.TOO_MANY_FILES).isEqualTo("attachment.too.many.files");
    assertThat(AttachmentTranslator.FILE_REMOVE).isEqualTo("attachment.file.remove");
    assertThat(AttachmentTranslator.FILE_RETRY).isEqualTo("attachment.file.retry");
    assertThat(AttachmentTranslator.FILE_START).isEqualTo("attachment.file.start");
    assertThat(AttachmentTranslator.STATUS_CONECTANDO).isEqualTo("attachment.status.connecting");
    assertThat(AttachmentTranslator.STATUS_HELD).isEqualTo("attachment.status.held");
    assertThat(AttachmentTranslator.STATUS_PROCESSING).isEqualTo("attachment.status.processing");
    assertThat(AttachmentTranslator.STATUS_STALLED).isEqualTo("attachment.status.stalled");
    assertThat(AttachmentTranslator.REMAINING_TIME_PREFIX).isEqualTo("attachment.remaining.time.prefix");
    assertThat(AttachmentTranslator.REMAINING_TIME_UNKNOW).isEqualTo("attachment.remaining.time.unknow");
    assertThat(AttachmentTranslator.FORBIDDEN).isEqualTo("attachment.forbidden");
    assertThat(AttachmentTranslator.SERVER_UNAVALIABLE).isEqualTo("attachment.server.unavaliable");
    assertThat(AttachmentTranslator.UNEXPECTED_SERVER_ERROR).isEqualTo("attachment.unexpected.server.error");
  }
}
