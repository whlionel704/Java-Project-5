package sg.edu.ntu.javaproject.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.javaproject.Exception.CustomerNotFoundException;
import sg.edu.ntu.javaproject.Exception.TransactionNotFoundException;
import sg.edu.ntu.javaproject.entity.Transactions;
import sg.edu.ntu.javaproject.repository.TransactionsRepository;
import sg.edu.ntu.javaproject.service.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    private TransactionsRepository transactionsRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Transactions createTransaction(Transactions transaction) {
        return transactionsRepository.save(transaction);
    }

    @Override
    public ArrayList<Transactions> getAllTransactions() {
        List<Transactions> allTransactions = transactionsRepository.findAll();
        // throw new UnsupportedOperationException("Unimplemented method 'getAllCustomers'");
        return (ArrayList<Transactions>) allTransactions;
    }


    @Override
    public Transactions getTransactionById(Integer transactions_id) {
        return transactionsRepository.findById(transactions_id).orElseThrow(() -> new TransactionNotFoundException(transactions_id));
    }

    @Override
    public void deleteTransactionById(Integer transactions_id) {
        if (transactionsRepository.existsById(transactions_id)){
            throw new CustomerNotFoundException(transactions_id);
        }
        transactionsRepository.deleteById(transactions_id);
    }

}
