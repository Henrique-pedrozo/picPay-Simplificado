package picpay.picpaySimplificado.exceptions;

public class ConflictEmailException extends RuntimeException {
    public ConflictEmailException(String message) {
        super(message);
    }
}
