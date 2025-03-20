package picpay.picpaySimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import picpay.picpaySimplificado.DTO.TransactionDTO;
import picpay.picpaySimplificado.entities.Transactions;
import picpay.picpaySimplificado.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
       return transactionService.createTransaction(transactionDTO);
    }
}
