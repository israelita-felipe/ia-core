package com.ia.core.owl.service.validation;

import com.ia.core.llm.service.model.ontologia.ExplicacaoInconsistenciaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para explicar inconsistências em linguagem natural.
 * <p>
 * Converte mensagens técnicas do reasoner em explicações compreensíveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class ExplicadorInconsistencia {

  public ExplicadorInconsistencia() {
  }

  /**
   * Explica uma inconsistência em linguagem natural.
   *
   * @param mensagemTecnica mensagem técnica do reasoner
   * @param classesInsatisfativeis classes insatisfatíveis
   * @return explicação em linguagem natural
   */
  public ExplicacaoInconsistenciaDTO explicar(String mensagemTecnica,
                                          List<String> classesInsatisfativeis) {
    log.debug("Explicando inconsistência: {}, classes: {}", mensagemTecnica, classesInsatisfativeis);

    String tipoInconsistencia = identificarTipo(mensagemTecnica);
    String explicacaoNatural = gerarExplicacao(tipoInconsistencia, classesInsatisfativeis);
    List<String> sugestoes = gerarSugestoes(tipoInconsistencia, classesInsatisfativeis);

    return ExplicacaoInconsistenciaDTO.builder()
        .mensagemTecnica(mensagemTecnica)
        .explicacaoNatural(explicacaoNatural)
        .axiomasCausadores(classesInsatisfativeis)
        .tipoInconsistencia(tipoInconsistencia)
        .sugestoesCorrecao(sugestoes)
        .gravidade("ERROR")
        .build();
  }

  private String identificarTipo(String mensagemTecnica) {
    if (mensagemTecnica.contains("unsatisfiable") || mensagemTecnica.contains("insatisfatível")) {
      return "CLASSE_INSATISFATIVEL";
    } else if (mensagemTecnica.contains("inconsistent")) {
      return "ONTOLOGIA_INCONSISTENTE";
    } else if (mensagemTecnica.contains("cardinality")) {
      return "CARDINALIDADE";
    } else {
      return "DESCONHECIDO";
    }
  }

  private String gerarExplicacao(String tipo, List<String> classes) {
    switch (tipo) {
      case "CLASSE_INSATISFATIVEL":
        if (classes.isEmpty()) {
          return "A ontologia contém classes que não podem ser instanciadas. Isso geralmente ocorre quando há contradições nas definições de classe.";
        }
        return String.format("As seguintes classes são insatisfatíveis (não podem ter instâncias): %s. " +
                           "Isso indica que há contradições nas definições dessas classes.",
                           String.join(", ", classes));
      case "ONTOLOGIA_INCONSISTENTE":
        return "A ontologia é inconsistente, o que significa que não é possível satisfazer todas as restrições simultaneamente.";
      case "CARDINALIDADE":
        return "Há um conflito nas restrições de cardinalidade das propriedades.";
      default:
        return "Foi detectada uma inconsistência na ontologia. Revise os axiomas adicionados recentemente.";
    }
  }

  private List<String> gerarSugestoes(String tipo, List<String> classes) {
    List<String> sugestoes = new ArrayList<>();

    switch (tipo) {
      case "CLASSE_INSATISFATIVEL":
        sugestoes.add("Revise as definições de subclasse das classes insatisfatíveis");
        sugestoes.add("Verifique se há restrições contraditórias (ex: SubClassOf e DisjointClasses simultâneas)");
        if (!classes.isEmpty()) {
          sugestoes.add("Analise especificamente a classe: " + classes.get(0));
        }
        break;
      case "ONTOLOGIA_INCONSISTENTE":
        sugestoes.add("Identifique e remova o axioma que causou a inconsistência");
        sugestoes.add("Use o reasoner para obter uma explicação detalhada");
        break;
      case "CARDINALIDADE":
        sugestoes.add("Verifique as restrições de cardinalidade mínima e máxima");
        sugestoes.add("Certifique-se de que minCardinality <= maxCardinality");
        break;
      default:
        sugestoes.add("Revise os axiomas adicionados recentemente");
        sugestoes.add("Use o reasoner para obter mais detalhes");
    }

    return sugestoes;
  }
}
