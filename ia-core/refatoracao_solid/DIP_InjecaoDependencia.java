package refatoracao_solid;

/**
 * Exemplo de DIP (Dependency Inversion Principle):
 * Dependa de abstrações, não de implementações concretas.
 */
public class PedidoService {
    private final Pagamento pagamento;
    /**
     * Injeta a dependência via construtor.
     * @param pagamento Implementação de Pagamento
     */
    public PedidoService(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    /**
     * Processa o pagamento do pedido.
     */
    public void processarPagamento(double valor) {
        pagamento.pagar(valor);
    }
}

interface Pagamento {
    /**
     * Realiza o pagamento.
     * @param valor Valor a ser pago
     */
    void pagar(double valor);
}

class PagamentoCartao implements Pagamento {
    public void pagar(double valor) {
        // ...pagamento com cartão...
    }
}

class PagamentoBoleto implements Pagamento {
    public void pagar(double valor) {
        // ...pagamento com boleto...
    }
}
