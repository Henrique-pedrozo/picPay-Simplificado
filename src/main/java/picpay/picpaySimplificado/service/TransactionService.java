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
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    private RestTemplate restTemplate;

    public void transaction(TransactionDTO transactionDTO) throws Exception {
        Optional<Users> sender = this.userService.findById(transactionDTO.senderId());
        Optional<Users> receiver = this.userService.findById(transactionDTO.receiverId());
        userService.validateTransaction(sender.orElseThrow(), transactionDTO.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());

        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transactions transactions = new Transactions();

        transactions.setAmount(transactionDTO.value());
        transactions.setSender(sender.get());
        transactions.setReceiver(receiver.get());
        transactions.setTimestamp(LocalDateTime.now());

        sender.get().setBalance(sender.get().getBalance() - transactionDTO.value());
        receiver.get().setBalance(receiver.get().getBalance() + transactionDTO.value());
        this.transactionRepository.save(transactions);
        this.userService.saveUsers(sender.get());
        this.userService.saveUsers(receiver.get());
    }

    public boolean authorizeTransaction(Optional<Users> sender, Double value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody() != null) {
            String message = (String) authorizationResponse.getBody().get("message");

            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }
}
