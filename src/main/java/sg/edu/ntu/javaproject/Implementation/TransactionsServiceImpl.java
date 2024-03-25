package sg.edu.ntu.javaproject.Implementation;

import java.util.ArrayList;

import sg.edu.ntu.javaproject.entity.Transactions;
import sg.edu.ntu.javaproject.repository.TransactionsRepository;
import sg.edu.ntu.javaproject.service.TransactionsService;

public class TransactionsServiceImpl implements TransactionsService {
    private TransactionsRepository transactionsRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Transactions createTransaction(Transactions transaction) {

        return null;
    }

    @Override
    public ArrayList<Transactions> getAllTransactions() {

        return null;
    }

    @Override
    public ArrayList<Transactions> getTransactionsByCustomerId() {

        return null;
    }

    @Override
    public Transactions getTransactionsById(Integer id) {

        return null;
    }

}
