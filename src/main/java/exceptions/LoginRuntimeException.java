package exceptions;

/**
 * Created by mvkent on 1/18/2017.
 */
public class LoginRuntimeException extends RuntimeException {
    public LoginRuntimeException(String message) {
        super(message);
    }
}
