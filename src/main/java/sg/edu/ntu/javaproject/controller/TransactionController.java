package sg.edu.ntu.javaproject.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import sg.edu.ntu.javaproject.entity.Transactions;
import sg.edu.ntu.javaproject.service.TransactionsService;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {
    private TransactionsService transactionsService;
    private ObjectMapper objectMapper;

    public TransactionController(TransactionsService transactionsService, ObjectMapper objectMapper){
        this.transactionsService = transactionsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("")
    public ResponseEntity<ArrayList<Transactions>> getAllTransactions() throws JsonProcessingException{
        ArrayList<Transactions> allTransactions = transactionsService.getAllTransactions(); 
        String customerJson = objectMapper.writeValueAsString(allTransactions);
        log.info("Retrieved all customers : " +customerJson);
        return new ResponseEntity<>(allTransactions, HttpStatus.OK);
    }

    //get transaction by id

    //delete transaction by id

}
