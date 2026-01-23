package refatoracao_solid;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Exemplo de OCP (Open/Closed Principle) usando Strategy Pattern.
 * Novos descontos podem ser adicionados sem alterar o código existente.
 */
public class CalculadoraDesconto {
    private final Map<String, DescontoStrategy> strategies = new HashMap<>();

    public CalculadoraDesconto() {
        strategies.put("PADRAO", new DescontoPadrao());
        strategies.put("VIP", new DescontoVip());
    }

    /**
     * Calcula o desconto usando a estratégia informada.
     * @param tipo Tipo de desconto
     * @param valor Valor base
     * @return Valor com desconto
     */
    public BigDecimal calcular(String tipo, BigDecimal valor) {
        return strategies.getOrDefault(tipo, new DescontoPadrao()).aplicar(valor);
    }
}

interface DescontoStrategy {
    /**
     * Aplica o desconto ao valor.
     * @param valor Valor base
     * @return Valor com desconto
     */
    BigDecimal aplicar(BigDecimal valor);
}

class DescontoPadrao implements DescontoStrategy {
    public BigDecimal aplicar(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.95"));
    }
}

class DescontoVip implements DescontoStrategy {
    public BigDecimal aplicar(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.90"));
    }
}
