package com.ia.core.communication.rest;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.modelomensagem.ModeloMensagemService;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Usando a constante do BaseController para o nome do esquema de segurança JWT

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

  /**
   * Aplica um template de mensagem com os parâmetros fornecidos.
   *
   * @param id         ID do modelo de mensagem
   * @param parametros mapa de parâmetros para substituição no template
   * @param request    requisição HTTP
   * @return corpo da mensagem com os parâmetros aplicados
   */
  @Operation(
      summary = "Aplica template de mensagem",
      description = "Aplica os parâmetros fornecidos a um template de mensagem"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Template aplicado com sucesso",
          content = @Content(mediaType = "text/plain",
              schema = @Schema(implementation = String.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Modelo de mensagem não encontrado"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Token inválido ou expirado"
      )
  })
  @SecurityRequirement(name = "Token de Autenticação")
  @PostMapping("/{id}/aplicar-template")
  public ResponseEntity<?> aplicarTemplate(
      @Parameter(description = "ID do modelo de mensagem", required = true, example = "1")
      @PathVariable Long id,
      @Parameter(description = "Parâmetros para substituição no template")
      @RequestBody Map<String, String> parametros,
      HttpServletRequest request) {
    log.info("Aplicando template {} com parâmetros", id);
    String corpo = getService().aplicarTemplate(id, parametros);
    return ResponseEntity.ok(corpo);
  }
}
