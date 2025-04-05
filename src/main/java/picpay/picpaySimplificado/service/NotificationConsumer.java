package picpay.picpaySimplificado.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import picpay.picpaySimplificado.entities.Notification;
import picpay.picpaySimplificado.entities.Transactions;
import picpay.picpaySimplificado.exceptions.NotificationException;

@Service
public class NotificationConsumer {

    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder restClient) {
        this.restClient = restClient.
                baseUrl("https://util.devi.tools/api/v1/notify)").
                build();
    }

    @KafkaListener(topics = "transaction-notifications", groupId = "picpaySimplificado")
    public void receiveNotification(Transactions transactions) {
        var reponse = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if (reponse.getStatusCode().isError() || !reponse.getStatusCode().is2xxSuccessful()) {
            throw new NotificationException("Error sending notification");
        }
    }
}
