package sg.edu.ntu.javaproject;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.edu.ntu.javaproject.entity.AccountType;
import sg.edu.ntu.javaproject.repository.AccountTypeRepository;

@Component
public class DataLoader {
    private AccountTypeRepository accountTypeRepository;

    public DataLoader(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @PostConstruct
    public void loadData() {
        accountTypeRepository.deleteAll();
        accountTypeRepository.save(new AccountType("savings"));
        accountTypeRepository.save(new AccountType("current"));
    }
}
