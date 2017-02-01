package beans;

/**
 * Created by mvkent on 1/18/2017.
 */
public class Ticket {
    private Bus bus;
    private Passenger passenger;

    public Ticket(Bus bus, Passenger passenger) {
        this.bus = bus;
        this.passenger = passenger;
    }
}
