package refatoracao_solid;

/**
 * Exemplo de SRP (Single Responsibility Principle):
 * Cada classe tem uma única responsabilidade.
 */
public class UsuarioService {
    private final UsuarioRepository repository;
    private final EmailService emailService;

    /**
     * Cria um novo usuário e envia email de boas-vindas.
     * @param usuario Usuário a ser criado
     */
    public void criarUsuario(Usuario usuario) {
        repository.salvar(usuario);
        emailService.enviarBoasVindas(usuario.getEmail());
    }
}

class UsuarioRepository {
    /**
     * Salva o usuário no banco de dados.
     * @param usuario Usuário
     */
    public void salvar(Usuario usuario) {
        // ...persistência...
    }
}

class EmailService {
    /**
     * Envia email de boas-vindas.
     * @param email Email do usuário
     */
    public void enviarBoasVindas(String email) {
        // ...envio de email...
    }
}

class Usuario {
    private String nome;
    private String email;
    // getters/setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
