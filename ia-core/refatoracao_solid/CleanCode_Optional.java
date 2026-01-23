package refatoracao_solid;

import java.util.Optional;

/**
 * Exemplo de uso de Optional para evitar nulls.
 */
public class OptionalExemplo {
    /**
     * Retorna o nome do usuário, se presente.
     * @param usuario Usuário
     * @return Nome ou "Desconhecido"
     */
    public String obterNome(Usuario usuario) {
        return Optional.ofNullable(usuario)
            .map(Usuario::getNome)
            .orElse("Desconhecido");
    }
}

class Usuario {
    private String nome;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
