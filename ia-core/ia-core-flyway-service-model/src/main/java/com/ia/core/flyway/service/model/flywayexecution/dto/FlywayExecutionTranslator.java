package com.ia.core.flyway.service.model.flywayexecution.dto;

/**
 * Translator para FlywayExecution.
 * <p>
 * Contém as constantes de internacionalização (i18n) utilizadas na camada de
 * visualização e validação para o caso de uso de gerenciamento de execuções
 * do Flyway.
 * </p>
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class FlywayExecutionTranslator {

	public static final class HELP {
		public static final String FLYWAY_EXECUTION = "flyway.execution.help";
		public static final String VERSION = "flyway.execution.help.version";
		public static final String DESCRIPTION = "flyway.execution.help.description";
		public static final String TYPE = "flyway.execution.help.type";
		public static final String SCRIPT = "flyway.execution.help.script";
		public static final String CHECKSUM = "flyway.execution.help.checksum";
		public static final String INSTALLED_BY = "flyway.execution.help.installed.by";
		public static final String INSTALLED_ON = "flyway.execution.help.installed.on";
		public static final String EXECUTION_TIME = "flyway.execution.help.execution.time";
		public static final String SUCCESS = "flyway.execution.help.success";
	}

	public static final String FLYWAY_EXECUTION_CLASS = FlywayExecutionDTO.class.getCanonicalName();
	public static final String FLYWAY_EXECUTION = "flyway.execution";
	public static final String VERSION = "flyway.execution.version";
	public static final String DESCRIPTION = "flyway.execution.description";
	public static final String DESCRICAO = "flyway.execution.description";
	public static final String TYPE = "flyway.execution.type";
	public static final String SCRIPT = "flyway.execution.script";
	public static final String CHECKSUM = "flyway.execution.checksum";
	public static final String INSTALLED_BY = "flyway.execution.installed.by";
	public static final String INSTALLED_ON = "flyway.execution.installed.on";
	public static final String EXECUTION_TIME = "flyway.execution.execution.time";
	public static final String SUCCESS = "flyway.execution.success";
	public static final String RANK = "flyway.execution.rank";

	private FlywayExecutionTranslator() {
	}
}