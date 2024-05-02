package sg.edu.ntu.javaproject.Exception;

public class AccountTypeIsNotExistException extends RuntimeException {
    public AccountTypeIsNotExistException(Integer accountId) {
        super("Account Type with ID " + accountId + " is not exist");
    }
}
