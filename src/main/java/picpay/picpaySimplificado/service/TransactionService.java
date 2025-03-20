package picpay.picpaySimplificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import picpay.picpaySimplificado.DTO.TransactionDTO;
import picpay.picpaySimplificado.entities.Transactions;
import picpay.picpaySimplificado.entities.Users;
import picpay.picpaySimplificado.repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public ResponseEntity<String> createTransaction(TransactionDTO transactionDTO) throws Exception {
        Optional<Users> sender = this.userService.findById(transactionDTO.senderId());
        Optional<Users> receiver = this.userService.findById(transactionDTO.receiverId());

        if (!sender.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Remetente não encontrado");
        }
        if (!receiver.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recebedor não encontrado");
        }

        Transactions transactions = new Transactions();

        transactions.setAmount(transactionDTO.value());
        transactions.setSender(sender.get());
        transactions.setReceiver(receiver.get());
        transactions.setTimestamp(LocalDateTime.now());

        ResponseEntity<String> validationResponse = userService.validateTransaction(sender, transactionDTO.value());
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        sender.get().setBalance(sender.get().getBalance() - transactionDTO.value());
        receiver.get().setBalance(receiver.get().getBalance() + transactionDTO.value());

        this.transactionRepository.save(transactions);
        this.userService.saveUsers(sender.get());
        this.userService.saveUsers(receiver.get());

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação realizada com sucesso");
        return ResponseEntity.ok().body("A transação realizada com sucesso! Saldo do remetente: " + sender.get().getBalance());
    }

//    public ResponseEntity<String> authorizeTransaction(Optional<Users> sender, Double value) {
//
//    }
}
