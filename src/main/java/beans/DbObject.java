package beans;

/**
 * Created by mvkent on 1/18/2017.
 */
public interface DbObject {

    long getId();
    DbObject fetchObject(long id);
    boolean pushObject();
}
