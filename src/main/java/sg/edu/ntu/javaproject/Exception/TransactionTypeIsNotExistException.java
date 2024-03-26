package sg.edu.ntu.javaproject.Exception;

public class TransactionTypeIsNotExistException extends RuntimeException{
    public TransactionTypeIsNotExistException(Integer id) {
        super("Transaction Type with ID " + id + " is not exist");
    }
}
