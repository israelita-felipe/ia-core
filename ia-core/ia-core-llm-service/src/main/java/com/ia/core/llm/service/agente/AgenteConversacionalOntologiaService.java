package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.agente.RespostaAgente;
import com.ia.core.llm.service.model.agente.TurnoConversacao;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.tool.base.OWLTool;
import com.ia.core.owl.service.tool.base.OWLTool.OntologyContext;
import com.ia.core.owl.service.tool.base.OWLToolRegistry;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import com.ia.core.owl.service.validation.ValidadorOntologia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Serviço para agente conversacional guiado por ontologia.
 * <p>
 * Gerencia conversações onde a ontologia é construída incrementalmente
 * e as respostas do LLM são validadas contra axiomas formais.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class AgenteConversacionalOntologiaService {

  private final ChatModel chatModel;
  private final LLMCommunicator llmCommunicator;
  private final DefaultOwlService owlService;
  private final ValidadorOntologia validador;
  private final LoopLLMRaciocinador reasonerLoop;
  private final OWLToolRegistry toolRegistry;

  public AgenteConversacionalOntologiaService(ChatModel chatModel,
                                            LLMCommunicator llmCommunicator,
                                            DefaultOwlService owlService,
                                            ValidadorOntologia validador,
                                            LoopLLMRaciocinador reasonerLoop,
                                            OWLToolRegistry toolRegistry) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
    this.owlService = owlService;
    this.validador = validador;
    this.reasonerLoop = reasonerLoop;
    this.toolRegistry = toolRegistry;
  }

  public ValidadorOntologia getValidador() {
    return validador;
  }

  public LoopLLMRaciocinador getReasonerLoop() {
    return reasonerLoop;
  }

  public OWLToolRegistry getToolRegistry() {
    return toolRegistry;
  }

  /**
   * Cria uma nova sessão de conversação.
   *
   * @param userId ID do usuário
   * @param dominio domínio da conversação
   * @return contexto da conversação criado
   */
  public ContextoConversacao criarSessao(String userId, String dominio) {
    String sessionId = UUID.randomUUID().toString();

    OntologiaDTO ontologia = OntologiaDTO.builder()
        .iri("http://example.com/conversa/" + sessionId)
        .nome("Ontologia da Conversa: " + dominio)
        .descricao("Ontologia construída incrementalmente durante a conversa")
        .consistente(true)
        .dataCriacao(LocalDateTime.now())
        .ultimaModificacao(LocalDateTime.now())
        .axiomas(new ArrayList<>())
        .build();

    ContextoConversacao contexto = ContextoConversacao.builder()
        .sessionId(sessionId)
        .userId(userId)
        .dominio(dominio)
        .ontologia(ontologia)
        .historico(new ArrayList<>())
        .dataInicio(LocalDateTime.now())
        .ultimaAtividade(LocalDateTime.now())
        .totalAxiomasExtraidos(0)
        .ontologiaConsistente(true)
        .inconsistenciasCorrigidas(0)
        .build();

    log.info("Sessão criada: sessionId={}, userId={}, dominio={}", sessionId, userId, dominio);
    return contexto;
  }

  /**
   * Processa uma mensagem do usuário na conversação.
   *
   * @param contexto contexto da conversação
   * @param mensagem mensagem do usuário
   * @return resposta do agente
   */
  public RespostaAgente processarMensagem(ContextoConversacao contexto, String mensagem) {
    log.info("Processando mensagem na sessão: sessionId={}", contexto.getSessionId());

    // Identifica o construtor OWL apropriado
    String construtorIdentificado = identificarConstrutor(mensagem);

    // Localiza a tool apropriada
    OWLTool tool = toolRegistry.getTool(construtorIdentificado)
        .orElseThrow(() -> new IllegalArgumentException(
            "Construtor não suportado: " + construtorIdentificado));

    // Executa a tool para gerar axiomas
    OntologyContext ontContext = new OntologyContext(
        contexto.getOntologia().getConteudo(),
        List.of(), // classes existentes
        List.of()  // propriedades existentes
    );

    List<AxiomaDTO> axiomasGerados = tool.generateAxioms(mensagem, ontContext);

    // Valida os axiomas
    List<AxiomaDTO> axiomasValidados = new ArrayList<>();
    boolean inconsistenciaCorrigida = false;
    String explicacaoCorrecao = null;
    int iteracoesUsadas = 0;

    for (AxiomaDTO axioma : axiomasGerados) {
      ResultadoValidacao resultado = validador.validarAxioma(axioma);
      iteracoesUsadas += resultado.getIteracoesUsadas();

      if (resultado.isConsistente()) {
        axiomasValidados.add(axioma);
      } else {
        // Tenta corrigir usando loop LLM-Reasoner
        var feedback = reasonerLoop.corrigirAxioma(
            axioma, mensagem, resultado.getExplicacao());

        if (feedback.isAxiomaValido()) {
          // Na implementação completa, aqui adicionariamos o axioma corrigido
          inconsistenciaCorrigida = true;
          explicacaoCorrecao = feedback.getExplicacao();
        }
      }
    }

    // Adiciona axiomas válidos à ontologia
    if (!axiomasValidados.isEmpty()) {
      try {
        owlService.addAxioms(() -> axiomasValidados);
      } catch (com.ia.core.owl.service.exception.OWLParserException | org.semanticweb.owlapi.model.OWLOntologyCreationException e) {
        log.error("Erro ao adicionar axiomas à ontologia", e);
      }
      contexto.getOntologia().getAxiomas().addAll(
          axiomasValidados.stream().map(AxiomaDTO::getManchesterSyntax).toList());
      contexto.setTotalAxiomasExtraidos(contexto.getTotalAxiomasExtraidos() + axiomasValidados.size());
      contexto.getOntologia().setUltimaModificacao(LocalDateTime.now());
    }

    // Cria turno de conversação
    TurnoConversacao turno = TurnoConversacao.builder()
        .numeroTurno(contexto.getHistorico().size() + 1)
        .mensagemUsuario(mensagem)
        .respostaAgente(gerarRespostaNatural(mensagem, axiomasValidados))
        .axiomasExtraidos(axiomasValidados)
        .construtorIdentificado(construtorIdentificado)
        .timestamp(LocalDateTime.now())
        .validacaoSucesso(!axiomasValidados.isEmpty())
        .iteracoesValidacao(iteracoesUsadas)
        .build();

    contexto.getHistorico().add(turno);
    contexto.setUltimaAtividade(LocalDateTime.now());

    // Verifica consistência da ontologia
    ResultadoValidacao validacaoOntologia = validador.validarOntologiaAtual();
    contexto.setOntologiaConsistente(validacaoOntologia.isConsistente());

    // Constrói resposta
    RespostaAgente resposta = RespostaAgente.builder()
        .agentResponse(turno.getRespostaAgente())
        .extractedAxioms(axiomasValidados)
        .ontologyStatus(RespostaAgente.OntologiaStatus.builder()
            .iri(contexto.getOntologia().getIri())
            .consistent(contexto.isOntologiaConsistente())
            .classCount(contarClasses(contexto))
            .axiomCount(contexto.getTotalAxiomasExtraidos())
            .warningsCount(0)
            .build())
        .iteracoesUsadas(iteracoesUsadas)
        .inconsistenciaCorrigida(inconsistenciaCorrigida)
        .explicacaoCorrecao(explicacaoCorrecao)
        .build();

    log.info("Mensagem processada: sessionId={}, axiomasValidados={}",
             contexto.getSessionId(), axiomasValidados.size());

    return resposta;
  }

  /**
   * Identifica o construtor OWL apropriado para a mensagem.
   */
  private String identificarConstrutor(String mensagem) {
    String lower = mensagem.toLowerCase();

    if (lower.contains("é um") || lower.contains("é uma") || lower.contains("é um tipo de")) {
      return "SubClassOf";
    } else if (lower.contains("é o mesmo que") || lower.contains("equivalente")) {
      return "EquivalentClasses";
    } else if (lower.contains("tem como domínio") || lower.contains("se aplica a")) {
      return "ObjectPropertyDomain";
    } else if (lower.contains("leva a") || lower.contains("tem como range")) {
      return "ObjectPropertyRange";
    }

    // Padrão default
    return "SubClassOf";
  }

  /**
   * Gera uma resposta em linguagem natural.
   */
  private String gerarRespostaNatural(String mensagem, List<AxiomaDTO> axiomas) {
    if (axiomas.isEmpty()) {
      return "Entendi sua mensagem, mas não consegui extrair axiomas OWL válidos. " +
             "Por favor, reformule sua descrição.";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Axioma(s) adicionado(s) à ontologia:\n");
    for (AxiomaDTO axioma : axiomas) {
      sb.append("- ").append(axioma.getManchesterSyntax()).append("\n");
    }
    sb.append("\nA ontologia agora contém ").append(axiomas.size())
      .append(" novo(s) axioma(s).");

    return sb.toString();
  }

  /**
   * Conta classes na ontologia (simplificado).
   */
  private int contarClasses(ContextoConversacao contexto) {
    // Na implementação completa, isso usaria o OWL API para contar classes
    return contexto.getTotalAxiomasExtraidos() / 2; // Estimativa simplificada
  }

  /**
   * Encerra uma sessão de conversação.
   *
   * @param sessionId ID da sessão
   */
  public void encerrarSessao(String sessionId) {
    log.info("Sessão encerrada: sessionId={}", sessionId);
    // Na implementação completa, persistiria o contexto ou limparia da memória
  }
}
