package com.ia.core.llm.service.model.agente;

/**
 * Translator para internacionalização de agentes construtores de ontologias.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class AgenteConstrutorTranslator {

  public static final String DOMAIN = "agenteConstrutor.domain";
  public static final String CORPUS = "agenteConstrutor.corpus";
  public static final String TARGET_IRI = "agenteConstrutor.targetIri";
  public static final String TARGET_NAME = "agenteConstrutor.targetName";
  public static final String DESIRED_CONSTRUCTORS = "agenteConstrutor.desiredConstructors";
  public static final String MAX_ITERATIONS = "agenteConstrutor.maxIterations";
  public static final String DETAIL_LEVEL = "agenteConstrutor.detailLevel";
  public static final String USE_ALL_CONSTRUCTORS = "agenteConstrutor.useAllConstructors";
  public static final String LANGUAGE = "agenteConstrutor.language";

  public static final class VALIDATION {
    public static final String DOMAIN_REQUIRED = "agenteConstrutor.validation.domain.required";
    public static final String CORPUS_REQUIRED = "agenteConstrutor.validation.corpus.required";
    public static final String MAX_ITERATIONS_REQUIRED = "agenteConstrutor.validation.maxIterations.required";
    public static final String MAX_ITERATIONS_MIN = "agenteConstrutor.validation.maxIterations.min";
  }

  public static final class ERROR {
    public static final String JOB_NOT_FOUND = "agenteConstrutor.error.job.notfound";
    public static final String JOB_FAILED = "agenteConstrutor.error.job.failed";
    public static final String CONSTRUCTION_TIMEOUT = "agenteConstrutor.error.construction.timeout";
  }

  public static final class MESSAGE {
    public static final String JOB_STARTED = "agenteConstrutor.message.job.started";
    public static final String JOB_COMPLETED = "agenteConstrutor.message.job.completed";
    public static final String ONTOLOGY_GENERATED = "agenteConstrutor.message.ontology.generated";
    public static final String JOB_CANCELLED = "agenteConstrutor.message.job.cancelled";
  }

  public static final class STATUS {
    public static final String QUEUED = "agenteConstrutor.status.queued";
    public static final String RUNNING = "agenteConstrutor.status.running";
    public static final String COMPLETED = "agenteConstrutor.status.completed";
    public static final String FAILED = "agenteConstrutor.status.failed";
  }
}
