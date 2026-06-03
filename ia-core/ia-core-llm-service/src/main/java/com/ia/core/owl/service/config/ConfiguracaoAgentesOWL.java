package com.ia.core.owl.service.config;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.OpenlletReasonerService;
import com.ia.core.owl.service.tool.annotation.AnnotationPropertyDomainTool;
import com.ia.core.owl.service.tool.annotation.AnnotationPropertyRangeTool;
import com.ia.core.owl.service.tool.annotation.SubAnnotationPropertyOfTool;
import com.ia.core.owl.service.tool.base.OWLTool;
import com.ia.core.owl.service.tool.base.OWLToolRegistry;
import com.ia.core.owl.service.tool.classexpression.*;
import com.ia.core.owl.service.tool.dataproperty.DisjointDataPropertiesTool;
import com.ia.core.owl.service.tool.dataproperty.EquivalentDataPropertiesTool;
import com.ia.core.owl.service.tool.dataproperty.SubDataPropertyOfTool;
import com.ia.core.owl.service.tool.individual.NegativeDataPropertyAssertionTool;
import com.ia.core.owl.service.tool.individual.NegativeObjectPropertyAssertionTool;
import com.ia.core.owl.service.tool.objectproperty.*;
import com.ia.core.owl.service.validation.ExplicadorInconsistencia;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import com.ia.core.owl.service.validation.ValidadorOntologia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração Spring para agentes guiados por ontologias.
 * <p>
 * Segue o padrão ADR-004 para injeção de dependências via ServiceConfig.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class ConfiguracaoAgentesOWL {

  /**
   * Cria registry de tools OWL 2 DL.
   *
   * @param tools lista de tools OWL disponíveis
   * @return registry de tools
   */
  @Bean
  public OWLToolRegistry toolRegistry(List<OWLTool> tools) {
    log.info("Configurando OWLToolRegistry com {} tools", tools.size());
    return new OWLToolRegistry(tools);
  }

  /**
   * Cria validador de ontologias.
   *
   * @param owlService serviço OWL
   * @param reasonerService serviço de raciocínio
   * @return validador de ontologias
   */
  @Bean
  public ValidadorOntologia validadorOntologia(DefaultOwlService owlService,
                                               OpenlletReasonerService reasonerService) {
    log.info("Configurando ValidadorOntologia");
    return new ValidadorOntologia(owlService, reasonerService);
  }

  /**
   * Cria explicador de inconsistências.
   *
   * @param reasonerService serviço de raciocínio
   * @return explicador de inconsistências
   */
  @Bean
  public ExplicadorInconsistencia explicadorInconsistencia(OpenlletReasonerService reasonerService) {
    log.info("Configurando ExplicadorInconsistencia");
    return new ExplicadorInconsistencia(reasonerService);
  }

  /**
   * Cria loop LLM-Reasoner para auto-correção.
   *
   * @param chatModel modelo de chat
   * @param llmCommunicator comunicador LLM
   * @param validador validador de ontologias
   * @param explicador explicador de inconsistências
   * @return loop LLM-Reasoner
   */
  @Bean
  public LoopLLMRaciocinador loopLLMRaciocinador(ChatModel chatModel,
                                               LLMCommunicator llmCommunicator,
                                               ValidadorOntologia validador,
                                               ExplicadorInconsistencia explicador) {
    log.info("Configurando LoopLLMRaciocinador");
    return new LoopLLMRaciocinador(chatModel, llmCommunicator, validador, explicador);
  }

  /**
   * Cria tool SubClassOf.
   *
   * @param chatModel modelo de chat
   * @param llmCommunicator comunicador LLM
   * @param owlService serviço OWL
   * @return tool SubClassOf
   */
  @Bean
  public SubClassOfTool subClassOfTool(ChatModel chatModel,
                                       LLMCommunicator llmCommunicator,
                                       DefaultOwlService owlService,
                                       TemplateService templateService,
                                       FerramentaService ferramentaService) {
    log.info("Configurando SubClassOfTool");
    return new SubClassOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  /**
   * Cria tool EquivalentClasses.
   *
   * @param chatModel modelo de chat
   * @param llmCommunicator comunicador LLM
   * @param owlService serviço OWL
   * @return tool EquivalentClasses
   */
  @Bean
  public EquivalentClassesTool equivalentClassesTool(ChatModel chatModel,
                                                     LLMCommunicator llmCommunicator,
                                                     DefaultOwlService owlService,
                                                     TemplateService templateService,
                                                     FerramentaService ferramentaService) {
    log.info("Configurando EquivalentClassesTool");
    return new EquivalentClassesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  /**
   * Cria tool ObjectPropertyDomain.
   *
   * @param chatModel modelo de chat
   * @param llmCommunicator comunicador LLM
   * @param owlService serviço OWL
   * @return tool ObjectPropertyDomain
   */
  @Bean
  public ObjectPropertyDomainTool objectPropertyDomainTool(ChatModel chatModel,
                                                          LLMCommunicator llmCommunicator,
                                                          DefaultOwlService owlService,
                                                          TemplateService templateService,
                                                          FerramentaService ferramentaService) {
    log.info("Configurando ObjectPropertyDomainTool");
    return new ObjectPropertyDomainTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectPropertyRangeTool objectPropertyRangeTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectPropertyRangeTool");
    return new ObjectPropertyRangeTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.objectproperty.InverseObjectPropertiesTool inverseObjectPropertiesTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando InverseObjectPropertiesTool");
    return new com.ia.core.owl.service.tool.objectproperty.InverseObjectPropertiesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.objectproperty.TransitiveObjectPropertyTool transitiveObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando TransitiveObjectPropertyTool");
    return new com.ia.core.owl.service.tool.objectproperty.TransitiveObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.objectproperty.SymmetricObjectPropertyTool symmetricObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando SymmetricObjectPropertyTool");
    return new com.ia.core.owl.service.tool.objectproperty.SymmetricObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.objectproperty.FunctionalObjectPropertyTool functionalObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando FunctionalObjectPropertyTool");
    return new com.ia.core.owl.service.tool.objectproperty.FunctionalObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.dataproperty.DataPropertyDomainTool dataPropertyDomainTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataPropertyDomainTool");
    return new com.ia.core.owl.service.tool.dataproperty.DataPropertyDomainTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.dataproperty.DataPropertyRangeTool dataPropertyRangeTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataPropertyRangeTool");
    return new com.ia.core.owl.service.tool.dataproperty.DataPropertyRangeTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.dataproperty.FunctionalDataPropertyTool functionalDataPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando FunctionalDataPropertyTool");
    return new com.ia.core.owl.service.tool.dataproperty.FunctionalDataPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.individual.ClassAssertionTool classAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ClassAssertionTool");
    return new com.ia.core.owl.service.tool.individual.ClassAssertionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.individual.ObjectPropertyAssertionTool objectPropertyAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectPropertyAssertionTool");
    return new com.ia.core.owl.service.tool.individual.ObjectPropertyAssertionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.individual.DataPropertyAssertionTool dataPropertyAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataPropertyAssertionTool");
    return new com.ia.core.owl.service.tool.individual.DataPropertyAssertionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.individual.SameIndividualTool sameIndividualTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando SameIndividualTool");
    return new com.ia.core.owl.service.tool.individual.SameIndividualTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.individual.DifferentIndividualsTool differentIndividualsTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DifferentIndividualsTool");
    return new com.ia.core.owl.service.tool.individual.DifferentIndividualsTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public com.ia.core.owl.service.tool.annotation.AnnotationAssertionTool annotationAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando AnnotationAssertionTool");
    return new com.ia.core.owl.service.tool.annotation.AnnotationAssertionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DisjointClassesTool disjointClassesTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DisjointClassesTool");
    return new DisjointClassesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectSomeValuesFromTool objectSomeValuesFromTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectSomeValuesFromTool");
    return new ObjectSomeValuesFromTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectAllValuesFromTool objectAllValuesFromTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectAllValuesFromTool");
    return new ObjectAllValuesFromTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectHasValueTool objectHasValueTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectHasValueTool");
    return new ObjectHasValueTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectMinCardinalityTool objectMinCardinalityTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectMinCardinalityTool");
    return new ObjectMinCardinalityTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectMaxCardinalityTool objectMaxCardinalityTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectMaxCardinalityTool");
    return new ObjectMaxCardinalityTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectExactCardinalityTool objectExactCardinalityTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectExactCardinalityTool");
    return new ObjectExactCardinalityTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public SubObjectPropertyOfTool subObjectPropertyOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando SubObjectPropertyOfTool");
    return new SubObjectPropertyOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public UnionOfTool unionOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando UnionOfTool");
    return new UnionOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public IntersectionOfTool intersectionOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando IntersectionOfTool");
    return new IntersectionOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ComplementOfTool complementOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ComplementOfTool");
    return new ComplementOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DataSomeValuesFromTool dataSomeValuesFromTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataSomeValuesFromTool");
    return new DataSomeValuesFromTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DataAllValuesFromTool dataAllValuesFromTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataAllValuesFromTool");
    return new DataAllValuesFromTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DataHasValueTool dataHasValueTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataHasValueTool");
    return new DataHasValueTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DataMinCardinalityTool dataMinCardinalityTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataMinCardinalityTool");
    return new DataMinCardinalityTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DataMaxCardinalityTool dataMaxCardinalityTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataMaxCardinalityTool");
    return new DataMaxCardinalityTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DataExactCardinalityTool dataExactCardinalityTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DataExactCardinalityTool");
    return new DataExactCardinalityTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public EquivalentObjectPropertiesTool equivalentObjectPropertiesTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando EquivalentObjectPropertiesTool");
    return new EquivalentObjectPropertiesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DisjointObjectPropertiesTool disjointObjectPropertiesTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DisjointObjectPropertiesTool");
    return new DisjointObjectPropertiesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DisjointUnionTool disjointUnionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DisjointUnionTool");
    return new DisjointUnionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public OneOfTool oneOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando OneOfTool");
    return new OneOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public HasSelfTool hasSelfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando HasSelfTool");
    return new HasSelfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public InverseFunctionalObjectPropertyTool inverseFunctionalObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando InverseFunctionalObjectPropertyTool");
    return new InverseFunctionalObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ReflexiveObjectPropertyTool reflexiveObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ReflexiveObjectPropertyTool");
    return new ReflexiveObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public IrreflexiveObjectPropertyTool irreflexiveObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando IrreflexiveObjectPropertyTool");
    return new IrreflexiveObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public AsymmetricObjectPropertyTool asymmetricObjectPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando AsymmetricObjectPropertyTool");
    return new AsymmetricObjectPropertyTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public ObjectPropertyChainTool objectPropertyChainTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando ObjectPropertyChainTool");
    return new ObjectPropertyChainTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public SubDataPropertyOfTool subDataPropertyOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando SubDataPropertyOfTool");
    return new SubDataPropertyOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public EquivalentDataPropertiesTool equivalentDataPropertiesTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando EquivalentDataPropertiesTool");
    return new EquivalentDataPropertiesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public DisjointDataPropertiesTool disjointDataPropertiesTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando DisjointDataPropertiesTool");
    return new DisjointDataPropertiesTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public NegativeObjectPropertyAssertionTool negativeObjectPropertyAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando NegativeObjectPropertyAssertionTool");
    return new NegativeObjectPropertyAssertionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public NegativeDataPropertyAssertionTool negativeDataPropertyAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando NegativeDataPropertyAssertionTool");
    return new NegativeDataPropertyAssertionTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public SubAnnotationPropertyOfTool subAnnotationPropertyOfTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando SubAnnotationPropertyOfTool");
    return new SubAnnotationPropertyOfTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public AnnotationPropertyDomainTool annotationPropertyDomainTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando AnnotationPropertyDomainTool");
    return new AnnotationPropertyDomainTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Bean
  public AnnotationPropertyRangeTool annotationPropertyRangeTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService, TemplateService templateService, FerramentaService ferramentaService) {
    log.info("Configurando AnnotationPropertyRangeTool");
    return new AnnotationPropertyRangeTool(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }
}
