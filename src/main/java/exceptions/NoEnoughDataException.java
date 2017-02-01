package exceptions;

/**
 * Created by mvkent on 1/18/2017.
 */
public class NoEnoughDataException extends Exception{
    public NoEnoughDataException() {
        super("Not enough data error");
    }

    public NoEnoughDataException(String message) {
        super(message);
    }
}
