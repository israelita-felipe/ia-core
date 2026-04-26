package com.ia.core.communication.rest;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.modelomensagem.ModeloMensagemService;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller para operações de modelos de mensagens.
 *
 * @author Israel Araújo
 */
@Slf4j
@Tag(name = "ModeloMensagem",
     description = "Gerenciamento de modelos de mensagens")
@RestController
@RequestMapping("/api/${api.version}/modelo/mensagem")
public class ModeloMensagemController
  extends DefaultBaseController<ModeloMensagem, ModeloMensagemDTO> {

  public ModeloMensagemController(ModeloMensagemService modeloMensagemService) {
    super(modeloMensagemService);
  }

  @Override
  public ModeloMensagemService getService() {
    return (ModeloMensagemService) super.getService();
  }

  @PostMapping("/{id}/aplicar-template")
  public ResponseEntity<?> aplicarTemplate(@PathVariable Long id,
                                           @RequestBody Map<String, String> parametros,
                                           HttpServletRequest request) {
    log.info("Aplicando template {} com parâmetros", id);
    String corpo = getService().aplicarTemplate(id, parametros);
    return ResponseEntity.ok(corpo);
  }
}
