package sg.edu.ntu.javaproject.service;

import java.util.ArrayList;

import sg.edu.ntu.javaproject.entity.Transactions;

public interface TransactionsService {
    Transactions createTransaction(Transactions transaction);

    Transactions getTransactionById(Integer id);

    void deleteTransactionById(Integer id);

    ArrayList<Transactions> getAllTransactions();
}
