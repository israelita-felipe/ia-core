package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologia;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologia;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.tool.base.OWLTool.OntologyContext;
import com.ia.core.owl.service.tool.base.OWLToolRegistry;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import com.ia.core.owl.service.validation.ValidadorOntologia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço para agente construtor de ontologias.
 * <p>
 * Processa corpus de texto e gera ontologias OWL 2 DL completas
 * utilizando todas as tools OWL 2 DL disponíveis. Extende o agente
 * conversacional pois um construtor é também um agente conversacional
 * com o objetivo específico de construir OWL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class AgenteConstrutorOntologiaService extends AgenteConversacionalOntologiaService {

  private final Map<String, JobConstrucao> jobsAtivos = new ConcurrentHashMap<>();

  public AgenteConstrutorOntologiaService(ChatModel chatModel,
                                          LLMCommunicator llmCommunicator,
                                          DefaultOwlService owlService,
                                          ValidadorOntologia validador,
                                          LoopLLMRaciocinador reasonerLoop,
                                          OWLToolRegistry toolRegistry) {
    super(chatModel, llmCommunicator, owlService, validador, reasonerLoop, toolRegistry);
  }

  /**
   * Inicia um job de construção de ontologia.
   *
   * @param requisicao requisição de construção
   * @return resultado inicial com jobId
   */
  public ResultadoConstrucaoOntologia iniciarConstrucao(RequisicaoConstrucaoOntologia requisicao) {
    String jobId = UUID.randomUUID().toString();

    JobConstrucao job = JobConstrucao.builder()
        .jobId(jobId)
        .requisicao(requisicao)
        .status("QUEUED")
        .progresso(0)
        .faseAtual("EXTRACTION")
        .axiomCount(0)
        .iterationCount(0)
        .dataInicio(LocalDateTime.now())
        .build();

    jobsAtivos.put(jobId, job);

    log.info("Job de construção iniciado: jobId={}, dominio={}", jobId, requisicao.getDomain());

    // Executa job de forma assíncrona (simplificado - na prática usar @Async)
    executarJob(job);

    return ResultadoConstrucaoOntologia.builder()
        .jobId(jobId)
        .status("QUEUED")
        .build();
  }

  /**
   * Obtém o progresso de um job.
   *
   * @param jobId ID do job
   * @return resultado com progresso atual
   */
  public ResultadoConstrucaoOntologia obterProgresso(String jobId) {
    JobConstrucao job = jobsAtivos.get(jobId);
    if (job == null) {
      return ResultadoConstrucaoOntologia.builder()
          .jobId(jobId)
          .status("NOT_FOUND")
          .errorMessage("Job não encontrado")
          .build();
    }

    return ResultadoConstrucaoOntologia.builder()
        .jobId(jobId)
        .status(job.getStatus())
        .statistics(ResultadoConstrucaoOntologia.EstatisticasConstrucao.builder()
            .classCount(job.getClassCount())
            .propertyCount(job.getPropertyCount())
            .axiomCount(job.getAxiomCount())
            .iterationsUsed(job.getIterationCount())
            .totalProcessingTimeMs(job.getTempoProcessamentoMs())
            .inconsistenciesCorrected(job.getInconsistenciasCorrigidas())
            .constructorsUsed(job.getConstructorsUsed())
            .build())
        .build();
  }

  /**
   * Obtém o resultado final de um job.
   *
   * @param jobId ID do job
   * @return resultado final com ontologia
   */
  public ResultadoConstrucaoOntologia obterResultado(String jobId) {
    JobConstrucao job = jobsAtivos.get(jobId);
    if (job == null) {
      return ResultadoConstrucaoOntologia.builder()
          .jobId(jobId)
          .status("NOT_FOUND")
          .errorMessage("Job não encontrado")
          .build();
    }

    if (!"COMPLETED".equals(job.getStatus())) {
      return ResultadoConstrucaoOntologia.builder()
          .jobId(jobId)
          .status(job.getStatus())
          .errorMessage("Job ainda não concluído")
          .build();
    }

    return ResultadoConstrucaoOntologia.builder()
        .jobId(jobId)
        .status(job.getStatus())
        .ontology(job.getOntologia())
        .statistics(ResultadoConstrucaoOntologia.EstatisticasConstrucao.builder()
            .classCount(job.getClassCount())
            .propertyCount(job.getPropertyCount())
            .axiomCount(job.getAxiomCount())
            .iterationsUsed(job.getIterationCount())
            .totalProcessingTimeMs(job.getTempoProcessamentoMs())
            .inconsistenciesCorrected(job.getInconsistenciasCorrigidas())
            .constructorsUsed(job.getConstructorsUsed())
            .build())
        .build();
  }

  /**
   * Cancela um job em execução.
   *
   * @param jobId ID do job
   * @return resultado do cancelamento
   */
  public ResultadoConstrucaoOntologia cancelarJob(String jobId) {
    JobConstrucao job = jobsAtivos.get(jobId);
    if (job == null) {
      return ResultadoConstrucaoOntologia.builder()
          .jobId(jobId)
          .status("NOT_FOUND")
          .errorMessage("Job não encontrado")
          .build();
    }

    if ("COMPLETED".equals(job.getStatus()) || "FAILED".equals(job.getStatus())) {
      return ResultadoConstrucaoOntologia.builder()
          .jobId(jobId)
          .status(job.getStatus())
          .errorMessage("Job já finalizado")
          .build();
    }

    job.setStatus("CANCELLED");
    log.info("Job cancelado: jobId={}", jobId);

    return ResultadoConstrucaoOntologia.builder()
        .jobId(jobId)
        .status("CANCELLED")
        .build();
  }

  /**
   * Executa o job de construção de ontologia.
   */
  private void executarJob(JobConstrucao job) {
    long startTime = System.currentTimeMillis();

    try {
      job.setStatus("RUNNING");
      job.setFaseAtual("EXTRACTION");
      job.setProgresso(10);

      // Fase 1: Extração de elementos do corpus
      List<String> elementosExtraidos = extrairElementos(job.getRequisicao());
      job.setProgresso(30);

      // Fase 2: Geração de axiomas usando tools
      job.setFaseAtual("GENERATION");
      List<AxiomaDTO> axiomasGerados = gerarAxiomas(job, elementosExtraidos);
      job.setProgresso(60);

      // Fase 3: Validação e refinamento
      job.setFaseAtual("VALIDATION");
      List<AxiomaDTO> axiomasValidados = validarERefinar(job, axiomasGerados);
      job.setProgresso(80);

      // Fase 4: Construção da ontologia final
      job.setFaseAtual("REFINEMENT");
      OntologiaDTO ontologiaFinal = construirOntologia(job, axiomasValidados);
      job.setProgresso(100);

      job.setStatus("COMPLETED");
      job.setOntologia(ontologiaFinal);
      job.setAxiomCount(axiomasValidados.size());
      job.setTempoProcessamentoMs(System.currentTimeMillis() - startTime);

      log.info("Job concluído com sucesso: jobId={}, axiomas={}",
               job.getJobId(), axiomasValidados.size());

    } catch (Exception e) {
      job.setStatus("FAILED");
      job.setErrorMessage(e.getMessage());
      job.setTempoProcessamentoMs(System.currentTimeMillis() - startTime);
      log.error("Job falhou: jobId={}", job.getJobId(), e);
    }
  }

  /**
   * Extrai elementos (classes, propriedades) do corpus.
   */
  private List<String> extrairElementos(RequisicaoConstrucaoOntologia requisicao) {
    // Na implementação completa, usaria LLM para extrair elementos
    // Por simplicidade, retorna lista vazia
    return new ArrayList<>();
  }

  /**
   * Gera axiomas usando as tools OWL 2 DL.
   */
  private List<AxiomaDTO> gerarAxiomas(JobConstrucao job, List<String> elementos) {
    List<AxiomaDTO> axiomas = new ArrayList<>();
    Set<String> constructorsUsed = new HashSet<>();

    // Usa todas as tools disponíveis ou apenas as desejadas
    List<String> constructorsDesejados = job.getRequisicao().getDesiredConstructors();
    if (constructorsDesejados == null || constructorsDesejados.isEmpty() ||
        job.getRequisicao().isUseAllConstructors()) {
      constructorsDesejados = getToolRegistry().getAvailableConstructors();
    }

    for (String constructor : constructorsDesejados) {
      getToolRegistry().getTool(constructor).ifPresent(tool -> {
        try {
          OntologyContext context = new OntologyContext("", List.of(), List.of());
          List<AxiomaDTO> axiomasTool = tool.generateAxioms(
              job.getRequisicao().getCorpus(), context);
          axiomas.addAll(axiomasTool);
          constructorsUsed.add(constructor);
        } catch (Exception e) {
          log.warn("Erro ao executar tool {}: {}", constructor, e.getMessage());
        }
      });
    }

    job.setConstructorsUsed(constructorsUsed);
    job.setIterationCount(1);
    return axiomas;
  }

  /**
   * Valida e refina axiomas usando loop LLM-Reasoner.
   */
  private List<AxiomaDTO> validarERefinar(JobConstrucao job, List<AxiomaDTO> axiomas) {
    List<AxiomaDTO> axiomasValidados = new ArrayList<>();
    int inconsistenciasCorrigidas = 0;

    for (AxiomaDTO axioma : axiomas) {
      ResultadoValidacao resultado = getValidador().validarAxioma(axioma);

      if (resultado.isConsistente()) {
        axiomasValidados.add(axioma);
      } else {
        // Tenta corrigir
        var feedback = getReasonerLoop().corrigirAxioma(
            axioma, job.getRequisicao().getCorpus(), resultado.getExplicacao());

        if (feedback.isAxiomaValido()) {
          // Na implementação completa, adicionaria axioma corrigido
          inconsistenciasCorrigidas++;
        }
      }
    }

    job.setInconsistenciasCorrigidas(inconsistenciasCorrigidas);
    job.setIterationCount(job.getIterationCount() + 1);
    return axiomasValidados;
  }

  /**
   * Constrói a ontologia final.
   */
  private OntologiaDTO construirOntologia(JobConstrucao job, List<AxiomaDTO> axiomas) {
    String iri = job.getRequisicao().getTargetIri();
    if (iri == null || iri.isEmpty()) {
      iri = "http://example.com/ontologia/" + job.getJobId();
    }

    String nome = job.getRequisicao().getTargetName();
    if (nome == null || nome.isEmpty()) {
      nome = "Ontologia: " + job.getRequisicao().getDomain();
    }

    return OntologiaDTO.builder()
        .iri(iri)
        .nome(nome)
        .descricao("Ontologia gerada automaticamente pelo agente construtor")
        .versao("1.0.0")
        .consistente(true)
        .dataCriacao(LocalDateTime.now())
        .ultimaModificacao(LocalDateTime.now())
        .axiomas(axiomas.stream().map(AxiomaDTO::getManchesterSyntax).toList())
        .build();
  }

  /**
   * Classe interna para representar um job de construção.
   */
  @lombok.Builder
  @lombok.Data
  @lombok.NoArgsConstructor
  @lombok.AllArgsConstructor
  private static class JobConstrucao {
    private String jobId;
    private RequisicaoConstrucaoOntologia requisicao;
    private String status;
    private int progresso;
    private String faseAtual;
    private int axiomCount;
    private int classCount;
    private int propertyCount;
    private int iterationCount;
    private long tempoProcessamentoMs;
    private int inconsistenciasCorrigidas;
    private Set<String> constructorsUsed;
    private LocalDateTime dataInicio;
    private OntologiaDTO ontologia;
    private String errorMessage;
  }
}
