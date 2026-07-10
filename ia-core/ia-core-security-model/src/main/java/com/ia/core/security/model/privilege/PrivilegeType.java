package com.ia.core.security.model.privilege;

import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.model.functionality.OperationEnum;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enumeração que representa os tipos de privilégio disponíveis no sistema.
 * <p>
 * Define as categorias de privilégios que determinam o escopo e as permissões
 * associadas a cada privilégio. Cada tipo possui um conjunto específico de
 * operações permitidas, controlando o acesso a diferentes funcionalidades
 * e recursos do sistema.
 * <p>
 * Esta enumeração é utilizada em conjunto com {@link Privilege} para
 * definir o nível de acesso e as operações permitidas para cada privilégio
 * configurado no sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum PrivilegeType {
    /**
     * Tipo de privilégio de sistema.
     * <p>
     * Representa privilégios globais aplicáveis a todo o sistema, incluindo
     * funcionalidades administrativas, configurações gerais e operações que
     * afetam múltiplos usuários ou módulos. Possui acesso a todas as operações
     * disponíveis no sistema, conforme definido por {@link OperationEnum}.
     * <p>
     * Este tipo de privilégio é tipicamente atribuído a administradores,
     * gestores de sistema e usuários com responsabilidades amplas.
     */
    SYSTEM(1) {
        @Override
        public Set<Operation> getOperations() {
            return Set.of(OperationEnum.values());
        }
    },

    /**
     * Tipo de privilégio específico de usuário.
     * <p>
     * Representa privilégios restritos a funcionalidades individuais ou
     * contextos específicos. Não possui operações pré-definidas por padrão,
     * sendo as permissões atribuídas de forma granular conforme necessário.
     * <p>
     * Este tipo de privilégio é tipicamente atribuído a usuários finais
     * com necessidades de acesso limitadas a recursos específicos.
     */
    USER(2) {
        @Override
        public Set<Operation> getOperations() {
            return Set.of();
        }
    };

    /**
     * Obtém o tipo de privilégio correspondente ao código fornecido.
     * <p>
     * Realiza uma busca linear pelos valores do enum para encontrar o tipo
     * de privilégio cujo código corresponde ao valor fornecido. A comparação
     * é feita usando {@link Objects#equals(Object, Object)} para segurança
     * contra null.
     *
     * @param codigo o código numérico do tipo de privilégio a ser buscado
     * @return o tipo de privilégio correspondente ao código, ou null se
     *         nenhum tipo correspondente for encontrado
     */
    /**
     * Código numérico que identifica unicamente o tipo de privilégio.
     * <p>
     * Este código é utilizado para serialização, persistência e comparação
     * de tipos de privilégio. Cada constante do enum possui um código único
     * que não deve ser alterado após a definição para manter compatibilidade.
     * <p>
     * O código é imutável e é definido no momento da construção da constante.
     */
    private final int codigo;

    /**
     * Construtor privado para inicializar um tipo de privilégio com seu código.
     * <p>
     * Este construtor é utilizado exclusivamente durante a definição das
     * constantes do enum. O código fornecido é armazenado e não pode ser
     * modificado após a construção.
     *
     * @param codigo código numérico que identifica o tipo de privilégio
     */
    private PrivilegeType(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtém o tipo de privilégio correspondente ao código fornecido.
     * <p>
     * Realiza uma busca linear pelos valores do enum para encontrar o tipo
     * de privilégio cujo código corresponde ao valor fornecido. A comparação
     * é feita usando {@link Objects#equals(Object, Object)} para segurança
     * contra null.
     * <p>
     * <b>Security Note:</b> Este método é usado para converter códigos de
     * privilégio de fontes externas (banco de dados, tokens, etc.). Um código
     * inválido representa uma possível tentativa de acesso com privilégio não
     * definido e deve ser tratado como erro de segurança.
     *
     * @param codigo o código numérico do tipo de privilégio a ser buscado
     * @return o tipo de privilégio correspondente ao código
     * @throws IllegalArgumentException se nenhum tipo correspondente for encontrado
     * @bugfix SECURITY: Alterado de retornar null para lançar IllegalArgumentException.
     * Retornar null pode causar NullPointerException em código de autorização
     * ou, pior, ser tratado como "privilégio inexistente = sem acesso" de forma
     * inconsistente. Lançar exceção torna o erro explícito e forçando tratamento.
     * Código inválido pode indicar tentativa de privilege escalation.
     */
    public static PrivilegeType of(int codigo) {
        return Stream.of(values())
            .filter(tipo -> Objects.equals(codigo, tipo.codigo))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Código de privilégio inválido: " + codigo + ". " +
                    "Valores válidos: " + Arrays.stream(values())
                    .map(t -> t.codigo + "=" + t.name())
                    .collect(Collectors.toList())
            ));
    }

    /**
     * @return {@link #codigo}
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtém o conjunto de operações permitidas para este tipo de privilégio.
     * <p>
     * Retorna as operações que podem ser executadas por privilégios deste tipo.
     * A implementação de cada constante define seu conjunto específico de operações
     * com base nas políticas de segurança do sistema.
     *
     * @return conjunto de operações permitidas, nunca null. Pode ser um conjunto vazio
     * se nenhuma operação específica estiver associada a este tipo
     */
    public abstract Set<Operation> getOperations();
}
