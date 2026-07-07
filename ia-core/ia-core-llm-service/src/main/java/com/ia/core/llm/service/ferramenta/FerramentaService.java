package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.llm.service.model.ferramenta.FerramentaActivationDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaUseCase;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de ferramentas.
 * <p>
 * Implementa operações CRUD para ferramentas utilizadas por agentes de IA,
 * incluindo sincronização automática, listagem de ferramentas disponíveis,
 * e operações de skills (ferramentas compostas com tipo=SKILL).
 * Ferramentas são convertidas automaticamente pelo FerramentaMapper.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class FerramentaService
  extends CrudBaseService<Ferramenta, FerramentaDTO>
  implements FerramentaUseCase {

  public FerramentaService(FerramentaServiceConfig config) {
    super(Objects.requireNonNull(config, "config não pode ser null"));
  }

  @Override
  public FerramentaRepository getRepository() {
    return (FerramentaRepository) super.getRepository();
  }

  @Override
  public FerramentaMapper getMapper() {
    return (FerramentaMapper) super.getMapper();
  }

  @Override
  public FerramentaServiceConfig getConfig() {
    return (FerramentaServiceConfig) super.getConfig();
  }

  @Override
  @Tool(description = "Sincroniza as ferramentas disponíveis no sistema a partir do serviço de descoberta automática. " +
                     "O serviço de descoberta escaneia pacotes configurados em busca de beans @Tool, " +
                     "sub-agentes registrados e ferramentas MCP reportadas. Esta operação reconcilia " +
                     "o banco de dados com as ferramentas descobertas, criando novos registros, " +
                     "atualizando registros existentes e marcando como inativos os não encontrados. " +
                     "Útil após deploy de novos serviços ou alteração de configurações de scan.")
  public void syncFromDiscovery() {
    getConfig().getDiscoveryService().syncFromDiscovery();
  }

  @Override
  @Tool(description = "Lista todas as ferramentas disponíveis e ativas no sistema. " +
                     "Retorna uma lista de DTOs contendo informações sobre cada ferramenta: " +
                     "título, descrição, tipo (AGENTE_ESPECIALISTA, TOOL_SPRING, RECURSO_MCP, SKILL), " +
                     "identificador, módulo de origem e status de ativação. Apenas ferramentas " +
                     "com campo ativo=true são retornadas.")
  public List<FerramentaDTO> listAvailable() {
    var repository = getRepository();
    if (repository == null) {
      return List.of();
    }
    var mapper = getMapper();
    var entities = repository.findByAtivoTrue();
    if (entities == null || mapper == null) {
      return List.of();
    }
    return mapper.toDTOList(entities);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FerramentaMetadataDTO> listMetadata() {
    var repository = getRepository();
    if (repository == null) {
      return List.of();
    }
    var entities = repository.findByAtivoTrueAndTipo(TipoFerramentaEnum.SKILL);
    if (entities == null) {
      return List.of();
    }
    return entities.stream()
        .map(ferramenta -> FerramentaMetadataDTO.builder()
            .id(ferramenta.getId())
            .titulo(ferramenta.getTitulo())
            .descricao(ferramenta.getDescricao())
            .tipo(ferramenta.getTipo())
            .subFerramentaCount(ferramenta.getSubFerramentas() == null ? 0 : ferramenta.getSubFerramentas().size())
            .ativo(ferramenta.isAtivo())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public FerramentaActivationDTO loadForActivation(Long id) {
    var repository = getRepository();
    if (repository == null) {
      throw new ServiceException("Repositório não configurado");
    }
    Ferramenta ferramenta = repository.findById(id)
        .orElseThrow(() -> new ServiceException("Ferramenta não encontrada: " + id));
    var mapper = getMapper();
    if (mapper == null) {
      throw new ServiceException("Mapper não configurado");
    }
    FerramentaDTO dto = mapper.toDTO(ferramenta);
    return FerramentaActivationDTO.builder()
        .id(dto.getId())
        .titulo(dto.getTitulo())
        .descricao(dto.getDescricao())
        .instrucoes(ferramenta.getInstrucoes())
        .template(dto.getTemplate())
        .subFerramentas(dto.getSubFerramentas() == null ? new ArrayList<>() : dto.getSubFerramentas())
        .build();
  }
}
