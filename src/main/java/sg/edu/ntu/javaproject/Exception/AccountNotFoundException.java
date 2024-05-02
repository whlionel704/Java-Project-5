package sg.edu.ntu.javaproject.Exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Integer accountId) {
        super("Could not find account with id: " + accountId);
    }

}
