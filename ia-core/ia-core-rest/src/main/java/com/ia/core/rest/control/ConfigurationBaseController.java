package com.ia.core.rest.control;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.service.configuracao.AbstractConfiguracaoService;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Controller REST para gerenciamento de configurações do sistema.
 * <p>
 * Expõe endpoints para listagem, busca e gerenciamento de configurações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ConfigurationBaseController<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>> extends DefaultBaseController<T, D> {

    public ConfigurationBaseController(AbstractConfiguracaoService<T, D> service) {
        super(service);
    }

    public AbstractConfiguracaoService<T, D> getService() {
        return (AbstractConfiguracaoService<T, D>) super.getService();
    }

    @Operation(
        summary = "Lista todos os módulos com configurações",
        description = "Retorna uma lista com os nomes dos módulos que possuem configurações cadastradas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de módulos retornada com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = List.class))
    )
    @GetMapping("/modulos")
    public ResponseEntity<List<String>> listModulos() {
        return ResponseEntity.ok(getService().findModulos());
    }

    @Operation(
        summary = "Lista configurações de um módulo",
        description = "Retorna todas as configurações pertencentes ao módulo especificado, ordenadas por categoria"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de configurações retornada com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = List.class))
    )
    @GetMapping("/modulo/{modulo}")
    public ResponseEntity<List<D>> listByModulo(
        @Parameter(description = "Nome do módulo") @PathVariable String modulo) {
        return ResponseEntity.ok(getService().findByModulo(modulo));
    }

}
