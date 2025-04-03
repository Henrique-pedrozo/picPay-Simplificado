package picpay.picpaySimplificado.exceptions;

public class SelfTransactionException extends RuntimeException {
    public SelfTransactionException(String message) {
        super(message);
    }
}
