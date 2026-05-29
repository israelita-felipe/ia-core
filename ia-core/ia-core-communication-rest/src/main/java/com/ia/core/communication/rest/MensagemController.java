package com.ia.core.communication.rest;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.service.mensagem.MensagemService;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Usando a constante do BaseController para o nome do esquema de segurança JWT

/**
 * REST Controller para operações de mensagens.
 * <p>
 * Fornece endpoints REST para gerenciamento de mensagens de comunicação,
 * incluindo envio individual, em massa e em lote.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Envio de mensagens individuais</li>
 *   <li>Envio de mensagens em massa</li>
 *   <li>Envio em lote usando modelos de mensagem</li>
 *   <li>Gerenciamento completo de mensagens via CRUD</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Tag(name = "Mensagem",
      description = "Gerenciamento de mensagens de comunicação")
@RestController
@RequestMapping("/api/${api.version}/mensagem")
public class MensagemController
  extends DefaultBaseController<Mensagem, MensagemDTO> {

  /**
   * Construtor com dependência do serviço de mensagens.
   *
   * @param mensagemService serviço de mensagens (não pode ser nulo)
   */
  public MensagemController(MensagemService mensagemService) {
    super(mensagemService);
  }

  @Override
  public MensagemService getService() {
    return (MensagemService) super.getService();
  }

    /**
     * Envia uma mensagem individual.
     * <p>
     * Este endpoint processa o envio de uma mensagem para um destinatário
     * específico através do canal selecionado.
     *
     * @param dto dados da mensagem (não pode ser nulo)
     * @param request requisição HTTP
     * @return mensagem enviada com status
     */
    @Operation(
        summary = "Envia uma mensagem individual",
        description = "Envia uma mensagem para um destinatário específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mensagem enviada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MensagemDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados da mensagem inválidos"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token inválido ou expirado"
        )
    })
    @SecurityRequirement(name = "Token de Autenticação")
    @PostMapping("/enviar")
    public ResponseEntity<MensagemDTO> enviar(
        @RequestBody MensagemDTO dto,
        HttpServletRequest request) {
        log.info("Recebida requisição para enviar mensagem via {}",
                dto.getTipoCanal());
        MensagemDTO resultado = getService().enviar(dto);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Envia mensagens em massa.
     * <p>
     * Este endpoint processa o envio de múltiplas mensagens de uma só vez,
     * permitindo comunicação com múltiplos destinatários simultaneamente.
     *
     * @param dto dados da requisição de envio em massa (não pode ser nulo)
     * @param request requisição HTTP
     * @return resposta vazia indicando sucesso
     */
    @Operation(
        summary = "Envia mensagens em massa",
        description = "Envia múltiplas mensagens de uma só vez"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mensagens enviadas com sucesso"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token inválido ou expirado"
        )
    })
    @SecurityRequirement(name = "Token de Autenticação")
    @PostMapping("/enviar-massa")
    public ResponseEntity<?> enviarEmMassa(
        @RequestBody EnvioMensagemRequestDTO dto,
        HttpServletRequest request) {
        log.info("Recebida requisição para enviar mensagens em massa");
        return ResponseEntity.ok(getService().enviarEmMassa(dto));
    }

    /**
     * Envia mensagens em lote para todos os contatos de um GrupoContato usando um modelo de mensagem.
     * <p>
     * Este endpoint utiliza um modelo de mensagem pré-definido para enviar
     * mensagens para todos os contatos de um grupo específico. Suporta
     * tratamento de falhas parciais no envio.
     *
     * @param modeloId ID do modelo de mensagem a ser usado (não pode ser nulo)
     * @param grupoId ID do grupo de contatos (não pode ser nulo)
     * @return resposta indicando sucesso ou falhas parciais
     * @throws DataAccessException em caso de erro de acesso aos dados
     */
    @Operation(
        summary = "Envia mensagens em lote",
        description = "Envia mensagens para todos os contatos de um grupo usando um modelo"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Envio concluído com sucesso"
        ),
        @ApiResponse(
            responseCode = "206",
            description = "Envio concluído com falhas parciais"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token inválido ou expirado"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno ao processar o envio"
        )
    })
    @SecurityRequirement(name = "Token de Autenticação")
    @PostMapping("/batch-send")
    public ResponseEntity<?> enviarBatch(
        @Parameter(description = "ID do modelo de mensagem", required = true, example = "1")
        @RequestParam Long modeloId,
        @Parameter(description = "ID do grupo de contatos", required = true, example = "1")
        @RequestParam Long grupoId) {
        log.info("Recebida requisição para envio em lote para modelo {} e grupo {}", modeloId, grupoId);
        try {
            String resultado = getService().enviarBatch(modeloId, grupoId);
            if (resultado.contains("falhas")) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resultado);
            }
            return ResponseEntity.ok(resultado);
        } catch (DataAccessException e) {
            log.error("Erro de acesso aos dados ao processar envio em lote: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar o envio");
        } catch (Exception e) {
            log.error("Erro inesperado ao processar envio em lote: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar o envio");
        }
    }
}
