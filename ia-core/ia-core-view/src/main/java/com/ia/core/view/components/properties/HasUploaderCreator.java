package com.ia.core.view.components.properties;

import com.ia.core.service.attachment.dto.AttachmentTranslator;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.UploadI18N.AddFiles;
import com.vaadin.flow.component.upload.UploadI18N.DropFiles;
import com.vaadin.flow.component.upload.UploadI18N.File;
import com.vaadin.flow.component.upload.UploadI18N.Uploading;
import com.vaadin.flow.component.upload.UploadI18N.Uploading.RemainingTime;
import com.vaadin.flow.component.upload.UploadI18N.Uploading.Status;

/**
 * Criador de {@link Upload}
 *
 * @author Israel Araújo
 */
public interface HasUploaderCreator
  extends HasHelp, HasTranslator {

  /**
   * Tradutor do upload
   *
   * @return {@link UploadI18N}
   */
  default UploadI18N createUploaderI18N() {
    var uploadI18N = new UploadI18N();
    var addFiles = new AddFiles();
    addFiles.setMany($(AttachmentTranslator.ADD_MANY));
    addFiles.setOne($(AttachmentTranslator.ADD_ONE));
    uploadI18N.setAddFiles(addFiles);
    var dropFiles = new DropFiles();
    dropFiles.setMany($(AttachmentTranslator.DROP_MANY));
    dropFiles.setOne($(AttachmentTranslator.DROP_ONE));
    uploadI18N.setDropFiles(dropFiles);
    var error = new UploadI18N.Error();
    error.setFileIsTooBig($(AttachmentTranslator.FILE_TOO_BIG));
    error.setIncorrectFileType($(AttachmentTranslator.INCORRECT_FILE_TYPE));
    error.setTooManyFiles($(AttachmentTranslator.TOO_MANY_FILES));
    uploadI18N.setError(error);
    var file = new File();
    file.setRemove($(AttachmentTranslator.FILE_REMOVE));
    file.setRetry($(AttachmentTranslator.FILE_RETRY));
    file.setStart($(AttachmentTranslator.FILE_START));
    uploadI18N.setFile(file);
    var uploading = new Uploading();
    var status = new Status();
    status.setConnecting($(AttachmentTranslator.STATUS_CONECTANDO));
    status.setHeld($(AttachmentTranslator.STATUS_HELD));
    status.setProcessing($(AttachmentTranslator.STATUS_PROCESSING));
    status.setStalled($(AttachmentTranslator.STATUS_STALLED));
    uploading.setStatus(status);
    var remainingTime = new RemainingTime();
    remainingTime.setPrefix($(AttachmentTranslator.REMAINING_TIME_PREFIX));
    remainingTime.setUnknown($(AttachmentTranslator.REMAINING_TIME_UNKNOW));
    uploading.setRemainingTime(remainingTime);
    var uploadingError = new Uploading.Error();
    uploadingError.setForbidden($(AttachmentTranslator.FORBIDDEN));
    uploadingError
        .setServerUnavailable($(AttachmentTranslator.SERVER_UNAVALIABLE));
    uploadingError
        .setUnexpectedServerError($(AttachmentTranslator.UNEXPECTED_SERVER_ERROR));
    uploading.setError(uploadingError);
    uploadI18N.setUploading(uploading);
    return uploadI18N;
  }

  /**
   * Cria um campo de upload
   *
   * @param label    título do campo
   * @param help     texto de ajuda
   * @param receiver {@link Receiver}
   * @return {@link Upload}
   */
  default Upload createUploadField(String label, String help,
                                   Receiver receiver) {
    Upload field = new Upload(receiver);
    field.setI18n(createUploaderI18N());
    setHelp(field, help);
    field.setWidthFull();
    return field;
  }

}
