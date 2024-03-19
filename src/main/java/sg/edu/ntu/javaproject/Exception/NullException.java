package sg.edu.ntu.javaproject.Exception;

public class NullException extends RuntimeException {
    public NullException(StringBuilder nullValue) {
        super(nullValue + " must be provided");
    }
}
