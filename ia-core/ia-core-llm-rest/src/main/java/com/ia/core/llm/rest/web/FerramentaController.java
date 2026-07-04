package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.model.ferramenta.FerramentaDiscoverable;
import com.ia.core.llm.service.ferramenta.FerramentaDiscoveryService;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.rest.control.DefaultBaseController;
import com.ia.core.service.dto.request.SearchRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para gerenciamento de ferramentas.
 * <p>
 * Expõe endpoints para operações CRUD em ferramentas utilizadas por agentes de IA.
 * Inclui endpoint para listar ferramentas descobertas com filtros e paginação usando o padrão SearchRequest.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/ferramentas")
@Tag(name = "Ferramenta", description = "Gerenciamento de ferramentas utilizadas por agentes de IA")
public class FerramentaController
  extends DefaultBaseController<Ferramenta, FerramentaDTO> {

  private final FerramentaService ferramentaService;
  private final FerramentaDiscoveryService ferramentaDiscoveryService;

  public FerramentaController(FerramentaService ferramentaService,
                              FerramentaDiscoveryService ferramentaDiscoveryService) {
    super(ferramentaService);
    this.ferramentaService = ferramentaService;
    this.ferramentaDiscoveryService = ferramentaDiscoveryService;
  }

  /**
   * Lista todas as ferramentas descobertas (do contexto Spring e do banco de dados)
   * com suporte a filtros e paginação usando o padrão SearchRequest.
   *
   * @param searchRequestDTO requisição de busca com filtros, ordenação e paginação
   * @return página de ferramentas descobertas
   */
  @GetMapping("/descobertas")
  @Operation(summary = "Lista ferramentas descobertas",
             description = "Retorna todas as ferramentas descobertas do contexto Spring e do banco de dados com filtros e paginação")
  public Page<FerramentaDiscoverable> listDiscoverable(SearchRequestDTO searchRequestDTO) {
    log.debug("Listando ferramentas descobertas com SearchRequest: {}", searchRequestDTO);
    return ferramentaDiscoveryService.listDiscoverable(searchRequestDTO);
  }

}
