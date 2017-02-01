package exceptions;

/**
 * Created by mvkent on 1/18/2017.
 */
public class SQLRuntimeException extends RuntimeException {
    public SQLRuntimeException(String message) {
        super(message);
    }

    public SQLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
