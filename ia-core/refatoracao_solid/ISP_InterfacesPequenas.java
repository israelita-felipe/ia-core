package refatoracao_solid;

/**
 * Exemplo de ISP (Interface Segregation Principle):
 * Interfaces pequenas e específicas para cada cliente.
 */
public interface Leitor {
    /**
     * Lê dados.
     */
    void ler();
}

public interface Gravador {
    /**
     * Grava dados.
     */
    void gravar();
}

public class Arquivo implements Leitor, Gravador {
    public void ler() {
        // ...leitura...
    }
    public void gravar() {
        // ...gravação...
    }
}
