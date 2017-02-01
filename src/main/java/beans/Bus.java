package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DbConnector;

/**
 * Created by mvkent on 1/18/2017.
 */
public class Bus implements Coach, DbObject{
    private long id;
    private String number;
    private int sitsCount;

    public Bus(long id, String number, int sits) {
        this.id = id;
        this.number = number;
        this.sitsCount = sits;
    }

    public Bus() {
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public int getSitsCount() {
        return sitsCount;
    }

    @Override
    public boolean book(int seatNumber) {
        return false;
    }

    @Override
    public Bus fetchObject(long id) {
        try {
            ResultSet resultSet = DbConnector.fetchRows("SELECT id, num, sits FROM bus WHERE id=?", id);
            while (resultSet.next()) {
                this.id = resultSet.getLong(1);
                this.number = resultSet.getString(2);
                this.sitsCount = resultSet.getInt(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean cancelBook(int seatNumber) {
        return false;
    }

    @Override
    public boolean pushObject() {
        return false;
    }
}
