package refatoracao_solid;

/**
 * Exemplo de LSP (Liskov Substitution Principle):
 * Subtipos podem ser usados no lugar do tipo base sem efeitos colaterais.
 */
public class AreaCalculator {
    /**
     * Calcula a área de qualquer forma.
     * @param forma Forma geométrica
     * @return Área
     */
    public double calcularArea(Forma forma) {
        return forma.area();
    }
}

abstract class Forma {
    /**
     * Calcula a área da forma.
     * @return Área
     */
    public abstract double area();
}

class Retangulo extends Forma {
    private double largura, altura;
    public Retangulo(double largura, double altura) {
        this.largura = largura;
        this.altura = altura;
    }
    public double area() { return largura * altura; }
}

class Quadrado extends Forma {
    private double lado;
    public Quadrado(double lado) { this.lado = lado; }
    public double area() { return lado * lado; }
}
