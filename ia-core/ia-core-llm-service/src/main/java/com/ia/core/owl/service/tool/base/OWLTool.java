package com.ia.core.owl.service.tool.base;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import java.util.List;

/**
 * Interface base para todas as tools OWL 2 DL.
 * <p>
 * Cada tool é responsável por gerar um tipo específico de axioma OWL
 * a partir de descrições em linguagem natural.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface OWLTool {

  /**
   * Nome do construtor OWL 2 DL que esta tool implementa.
   *
   * @return nome do construtor (ex: "SubClassOf", "ObjectPropertyDomain")
   */
  String getConstructorName();

  /**
   * Descrição do construtor em linguagem natural.
   *
   * @return descrição do construtor
   */
  String getDescription();

  /**
   * Template de prompt para o LLM.
   *
   * @return template de prompt
   */
  String getPromptTemplate();

  /**
   * Processa a descrição em linguagem natural e gera axiomas OWL.
   *
   * @param naturalLanguageDescription descrição em linguagem natural
   * @param context contexto ontológico atual (opcional)
   * @return lista de axiomas gerados
   */
  List<AxiomaDTO> generateAxioms(String naturalLanguageDescription,
                                  OntologyContext context);

  /**
   * Valida se o axioma gerado está sintaticamente correto.
   *
   * @param axiom axioma a validar
   * @return true se válido, false caso contrário
   */
  boolean validateAxiom(AxiomaDTO axiom);

  /**
   * Retorna exemplos de uso do construtor.
   *
   * @return lista de exemplos em linguagem natural
   */
  List<String> getExamples();

  /**
   * Classe interna para representar contexto ontológico.
   */
  class OntologyContext {
    private final String manchesterSyntax;
    private final List<String> existingClasses;
    private final List<String> existingProperties;

    public OntologyContext(String manchesterSyntax,
                          List<String> existingClasses,
                          List<String> existingProperties) {
      this.manchesterSyntax = manchesterSyntax;
      this.existingClasses = existingClasses;
      this.existingProperties = existingProperties;
    }

    public String toManchesterSyntax() {
      return manchesterSyntax;
    }

    public List<String> getExistingClasses() {
      return existingClasses;
    }

    public List<String> getExistingProperties() {
      return existingProperties;
    }
  }
}
