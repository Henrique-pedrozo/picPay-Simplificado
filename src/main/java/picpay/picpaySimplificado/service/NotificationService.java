package picpay.picpaySimplificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import picpay.picpaySimplificado.DTO.NotificationDTO;
import picpay.picpaySimplificado.entities.Users;

import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(Optional<Users> users, String message) throws Exception {
        String email = users.get().getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        System.out.println("Notificação enviada com sucesso");
    }
}
