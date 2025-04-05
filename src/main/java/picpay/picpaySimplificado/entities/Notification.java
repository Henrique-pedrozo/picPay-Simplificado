package picpay.picpaySimplificado.entities;

public record Notification(
        String status,
        Data data
) {
    public record Data(
            String message) {
    }
}
