package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DbConnector;

/**
 * Created by mvkent on 1/18/2017.
 */
public class Station implements DbObject {
    private long id;
    private String title;

    public Station(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Station() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Station fetchObject(long id) {
        try {
            ResultSet resultSet = DbConnector.fetchRows("SELECT id, title FROM station WHERE id=?", id);
            while (resultSet.next()) {
                this.id = resultSet.getLong(1);
                this.title = resultSet.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean pushObject() {
        return false;
    }
}
