package beans;

import java.util.Date;

/**
 * Created by mvkent on 1/18/2017.
 */
public class Passenger {
    private String firstName;
    private String lastName;
    private Date date;

    public Passenger(String firstName, String lastName, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
    }
}
