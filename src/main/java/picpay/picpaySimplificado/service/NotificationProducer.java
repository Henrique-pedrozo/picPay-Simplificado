package picpay.picpaySimplificado.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import picpay.picpaySimplificado.entities.Transactions;

@Service
public class NotificationProducer {
    private KafkaTemplate<String, Transactions> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Transactions> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Transactions transactions) {
        kafkaTemplate.send("transaction-notifications", transactions);
    }
}
