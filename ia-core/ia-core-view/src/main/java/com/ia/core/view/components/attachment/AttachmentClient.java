package com.ia.core.view.components.attachment;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.client.DefaultBaseClient;
import feign.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente para anexos
 *
 * @param <T> Tipo do anexo
 * @author Israel Araújo
 */
public interface AttachmentClient<T extends AttachmentDTO<?>>
    extends DefaultBaseClient<T> {

    /**
     * Realiza o download de um arquivo
     *
     * @param id Identificador do arquivo
     * @return {@link Response} contendo as informações de download do arquivo
     */
    @GetMapping("/download/{id}")
    @Resilient(ResilienceProfile.INTERNAL_SERVICE)
    public Response download(@PathVariable("id") String id);
}
