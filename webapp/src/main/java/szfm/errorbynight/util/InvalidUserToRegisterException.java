package szfm.errorbynight.util;

public class InvalidUserToRegisterException extends Exception {

    public InvalidUserToRegisterException() {
        super();
    }

    public InvalidUserToRegisterException(String message) {
        super(message);
    }
    public InvalidUserToRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserToRegisterException(Throwable cause) {
        super(cause);
    }
    protected InvalidUserToRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
