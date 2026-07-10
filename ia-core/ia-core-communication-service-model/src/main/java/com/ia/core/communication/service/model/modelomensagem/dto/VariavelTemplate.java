package com.ia.core.communication.service.model.modelomensagem.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Enum com todas as variáveis disponíveis para templates de mensagem.
 * <p>
 * Cada constante representa uma variável que pode ser usada em templates
 * através da sintaxe {{chave}}. Esta abordagem permite que o sistema
 * substitua as variáveis por valores reais durante o envio de mensagens.
 * </p>
 *
 * <p>Exemplo de uso em template:
 * <pre>{@code
 * Olá {{nome}}, seu agendamento para o evento {{evento.nome}}
 * será no dia {{evento.data}} às {{evento.hora}}.
 * }</pre></p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum VariavelTemplate implements Variavel {
    // === CRIAÇÃO ===
    CRIADO_EM("criadoEm", "Data de criação", "data", "criacao", true),
    ATUALIZADO_EM("atualizadoEm", "Data de atualização", "data", "criacao", true),

    // === CONTATO ===
    NOME("nome", "Nome do destinatário", "texto", "contato", false),
    TELEFONE("telefone", "Telefone do destinatário", "texto", "contato", false),
    EMAIL("email", "E-mail do destinatário", "texto", "contato", false),

    // === MENSAGEM ===
    CORPO_MENSAGEM("corpoMensagem", "Corpo da mensagem", "texto", "mensagem", false),
    DATA_ENVIO("dataEnvio", "Data de envio da mensagem", "data", "mensagem", false),
    STATUS("status", "Status da mensagem", "texto", "mensagem", false),

    // === MODELO ===
    NOME_MODELO("nome", "Nome do modelo", "texto", "modelo", false),
    DESCRICAO_MODELO("descricao", "Descrição do modelo", "texto", "modelo", false),
    CORPO_MODELO("corpoModelo", "Corpo do modelo", "texto", "modelo", false),
    TIPO_CANAL("tipoCanal", "Tipo do canal", "texto", "modelo", false),
    ATIVO_MODELO("ativo", "Modelo ativo", "booleano", "modelo", false),

    // === GRUPO ===
    NOME_GRUPO("nome", "Nome do grupo", "texto", "grupo", false),
    DESCRICAO_GRUPO("descricao", "Descrição do grupo", "texto", "grupo", false),
    ATIVO_GRUPO("ativo", "Grupo ativo", "booleano", "grupo", false),

    // === EVENTO ===
    NOME_EVENTO("nomeEvento", "Nome do evento", "texto", "evento", false),
    DATA_EVENTO("dataEvento", "Data do evento", "data", "evento", false),
    HORA_EVENTO("horaEvento", "Hora do evento", "hora", "evento", false),

    // === SISTEMA ===
    DATA_ATUAL("dataAtual", "Data atual", "data", "sistema", false),
    HORA_ATUAL("horaAtual", "Hora atual", "hora", "sistema", false),

    // === IGREJA ===
    NOME_IGREJA("nomeIgreja", "Nome da igreja", "texto", "igreja", false),
    ENDERECO_IGREJA("enderecoIgreja", "Endereço da igreja", "texto", "igreja", false),
    TELEFONE_IGREJA("telefoneIgreja", "Telefone da igreja", "texto", "igreja", false),
    EMAIL_IGREJA("emailIgreja", "E-mail da igreja", "texto", "igreja", false);

    private final String chave;
    private final String descricao;
    private final String tipo;
    private final String categoria;
    private final boolean obrigatoria;

    /**
     * Construtor das constantes do enum.
     *
     * @param chave chave única para identificação no template (não pode ser null)
     * @param descricao descrição amigável para exibição ao usuário
     * @param tipo tipo de dado (texto, data, hora, booleano)
     * @param categoria categoria da variável para agrupamento
     * @param obrigatoria indica se a variável é obrigatória em templates
     */
    VariavelTemplate(String chave, String descricao, String tipo, String categoria, boolean obrigatoria) {
        this.chave = chave;
        this.descricao = descricao;
        this.tipo = tipo;
        this.categoria = categoria;
        this.obrigatoria = obrigatoria;
    }

    @Override
    public String getChave() {
        return "{{" + chave + "}}";
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o tipo de dado da variável.
     *
     * @return tipo da variável (texto, data, hora, ou booleano)
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna a categoria da variável.
     *
     * @return categoria (contato, mensagem, modelo, grupo, evento, sistema, igreja)
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Verifica se a variável é obrigatória em templates.
     *
     * @return {@code true} se obrigatória, {@code false} caso contrário
     */
    public boolean isObrigatoria() {
        return obrigatoria;
    }

    /**
     * Busca uma variável pelo nome da chave.
     *
     * @param chave a chave da variável (ex: "nome")
     * @return optional contendo a variável ou vazio se não encontrada
     */
    public static Optional<VariavelTemplate> buscarPorChave(String chave) {
        return Arrays.stream(values())
            .filter(v -> v.chave.equals(chave))
            .findFirst();
    }

    /**
     * Lista todas as chaves das variáveis disponíveis.
     * <p>
     * As chaves são retornadas no formato {@code {{chave}} para exibição
     * na interface de cadastro de templates.
     * </p>
     *
     * @return lista de chaves no formato de placeholder
     */
    public static List<String> listarChaves() {
        return Arrays.stream(values())
            .map(v -> "{{" + v.chave + "}}")
            .collect(Collectors.toList());
    }
}