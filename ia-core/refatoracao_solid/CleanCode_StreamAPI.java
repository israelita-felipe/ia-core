package refatoracao_solid;

import java.util.List;

/**
 * Exemplo de uso de Stream API para somar valores.
 */
public class StreamAPIExemplo {
    /**
     * Soma todos os valores da lista.
     * @param valores Lista de inteiros
     * @return Soma
     */
    public int somar(List<Integer> valores) {
        return valores.stream().mapToInt(Integer::intValue).sum();
    }
}
