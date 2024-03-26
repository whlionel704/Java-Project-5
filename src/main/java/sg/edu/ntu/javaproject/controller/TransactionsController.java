package sg.edu.ntu.javaproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sg.edu.ntu.javaproject.entity.Transactions;
import sg.edu.ntu.javaproject.service.TransactionsService;

@RestController
public class TransactionsController {
    private TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping({ "/withdraw", "/withdraw/" })
    public ResponseEntity<Transactions> withdrawTransaction(@Valid @RequestBody Transactions transaction) {
        Transactions newTransaction = transactionsService.withdrawTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
