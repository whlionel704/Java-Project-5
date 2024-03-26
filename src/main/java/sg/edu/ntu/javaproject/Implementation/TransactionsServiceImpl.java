package sg.edu.ntu.javaproject.Implementation;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;


import sg.edu.ntu.javaproject.Exception.CustomerAndAccountNotFoundException;
import sg.edu.ntu.javaproject.Exception.InsufficientBalanceException;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.Transactions;
import sg.edu.ntu.javaproject.repository.AccountRepository;
import sg.edu.ntu.javaproject.repository.CustomerRepository;
import sg.edu.ntu.javaproject.repository.TransactionsRepository;
import sg.edu.ntu.javaproject.service.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    private TransactionsRepository transactionsRepository;
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository, CustomerRepository customerRepository,
            AccountRepository accountRepository) {
        this.transactionsRepository = transactionsRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Transactions withdrawTransaction(Transactions transaction) {
        // check if customerid with account type is exist
        if (!accountRepository.existByAccountType(transaction.getCustomerId(), transaction.getAccountTypeId())) {
            throw new CustomerAndAccountNotFoundException(transaction.getCustomerId(), transaction.getAccountTypeId());
        }
        // check if customer have enough balance on their account type
        Optional<Account> optionalAccount = accountRepository
                .findByCustomerIdAndAccountTypeId(transaction.getCustomerId(), transaction.getAccountTypeId());
        if (optionalAccount.isPresent()) {
            Account savedAccount = optionalAccount.get();
            if (savedAccount.getBalance() < transaction.getAmount()) {
                throw new InsufficientBalanceException(savedAccount.getBalance(), transaction.getAmount());
            }
        } else
            throw new CustomerAndAccountNotFoundException(transaction.getCustomerId(), transaction.getAccountTypeId());
        Account savedAccount = optionalAccount.get();
        int balanceBefore = savedAccount.getBalance();
        int balanceAfter = (balanceBefore - transaction.getAmount());
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transaction.setTransactionTypeId(2);
        savedAccount.setBalance(balanceAfter);
        accountRepository.save(savedAccount);
        return transactionsRepository.save(transaction);

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
