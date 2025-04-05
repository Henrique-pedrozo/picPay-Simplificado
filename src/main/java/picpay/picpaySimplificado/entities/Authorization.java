package picpay.picpaySimplificado.entities;

public record Authorization(
        String status,
        Data data
) {
    public record Data(
            boolean authorization
    ) {
    }
}
