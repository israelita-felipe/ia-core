package com.ia.core.llm.view.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Catálogo de ferramentas Spring AI específicas da camada view.
 * <p>
 * Define ferramentas para interações específicas da UI Vaadin, como navegação,
 * atualização de componentes e notificações. Estas tools são descobertas automaticamente
 * pelo FerramentaDiscoveryService via scan de pacotes configuráveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ViewToolCatalog {

    /**
     * Navega para a interface do agente.
     * Útil para redirecionar o usuário após ações do agente.
     */
    @Tool(name = "view_navigateToAgent", description = "Navegar para a interface do agente na aplicação Vaadin")
    public String navigateToAgent() {
        return "Navegação para interface do agente solicitada";
    }

    /**
     * Atualiza o status do agente na UI.
     *
     * @param status o novo status a ser exibido
     */
    @Tool(name = "view_updateAgentStatus", description = "Atualizar status do agente na interface Vaadin")
    public String updateAgentStatus(@ToolParam(description = "Status a ser exibido na interface") String status) {
        return "Status atualizado: " + status;
    }

    /**
     * Carrega o histórico de conversas do agente.
     *
     * @param usuarioId ID do usuário para filtrar o histórico
     */
    @Tool(name = "view_loadChatHistory", description = "Carregar histórico de conversas do agente para exibição na UI")
    public String loadChatHistory(@ToolParam(description = "ID do usuário") String usuarioId) {
        return "Histórico de conversas carregado para usuário: " + usuarioId;
    }

    /**
     * Exibe uma notificação na interface.
     *
     * @param message mensagem a ser exibida
     * @param severity nível de severidade (INFO, WARNING, ERROR)
     */
    @Tool(name = "view_showNotification", description = "Exibir notificação na interface Vaadin")
    public String showNotification(@ToolParam(description = "Mensagem da notificação") String message,
                                  @ToolParam(description = "Severidade: INFO, WARNING, ERROR") String severity) {
        return "Notificação [" + severity + "]: " + message;
    }
}
