package exception;

public class InvalidClassroomException extends Exception {
    private static final long serialVersionUID = 1460885495420001751L;

    public InvalidClassroomException(String message) {
        super(message);
    }
}