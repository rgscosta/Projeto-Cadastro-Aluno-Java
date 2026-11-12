package exception;

public class InvalidNameException extends Exception {
    private static final long serialVersionUID = -7805251706088653847L;

    public InvalidNameException(String message) {
        super(message);
    }
}