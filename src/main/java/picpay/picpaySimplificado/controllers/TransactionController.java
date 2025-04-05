package picpay.picpaySimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import picpay.picpaySimplificado.DTO.TransactionDTO;
import picpay.picpaySimplificado.entities.Transactions;
import picpay.picpaySimplificado.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transactions>> getAllTransactions() {
        return ResponseEntity.ok().body(transactionService.findAllTransactions());
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        return transactionService.createTransaction(transactionDTO);
    }
}
