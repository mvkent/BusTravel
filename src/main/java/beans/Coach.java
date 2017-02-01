package beans;

/**
 * Created by mvkent on 1/18/2017.
 */
public interface Coach extends DbObject{
    boolean book(int seatNumber);
    boolean cancelBook(int seatNumber);
}
