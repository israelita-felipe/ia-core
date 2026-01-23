package refatoracao_solid;

/**
 * Exemplo de Decorator Pattern para validadores.
 */
public interface Validator<T> {
    /**
     * Valida o objeto.
     * @param obj Objeto a validar
     */
    void validate(T obj);
}

public abstract class ValidatorDecorator<T> implements Validator<T> {
    protected final Validator<T> next;
    public ValidatorDecorator(Validator<T> next) { this.next = next; }
    public void validate(T obj) {
        doValidate(obj);
        if (next != null) next.validate(obj);
    }
    protected abstract void doValidate(T obj);
}

public class NotNullValidator<T> extends ValidatorDecorator<T> {
    public NotNullValidator(Validator<T> next) { super(next); }
    protected void doValidate(T obj) {
        if (obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
    }
}
