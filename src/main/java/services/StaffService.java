package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Staff;
import utils.DbConnector;
import exceptions.SQLRuntimeException;
import spark.Request;

/**
 * Created by mvkent on 1/18/2017.
 */
public class StaffService {
    private static StaffService instance = null;

    private Staff current = null;

    public static StaffService getInstance() {
        if (instance == null) {
            instance = new StaffService();
        }
        return instance;
    }

    public Staff current(Request request) {
        String sessionId = request.cookie("JSESSIONID");
        return current(sessionId);
    }

    public Staff current(String sessionId) {
        if(current != null){
            return current;
        }
        current = getStaffBySession(sessionId);
        if (current != null) {
            return current;
        }
        return null;
    }

    public Staff login(String session, String login, String password){
        setStaffSession(session, login, password);
        return getStaffBySession(session);
    }

    public void logout(String session){
        try{
            Connection connection = DbConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE staff SET sess='' WHERE sess=?");
            preparedStatement.setString(1, session);
            preparedStatement.execute();
            drop();
        }catch (SQLException e){
            throw new SQLRuntimeException(e.getMessage(), e);
        }
    }

    public void setStaffSession(String session, String login, String password){
        try{
            DbConnector.updateRows("UPDATE staff SET sess=? WHERE login=? AND pwd=?", session, login, password);
        }catch (SQLException e){
            throw new SQLRuntimeException(e.getMessage(), e);
        }
    }

    public Staff getStaffBySession(String session){
        try{
            ResultSet resultSet = DbConnector.fetchRows("SELECT id, name, login FROM staff WHERE sess=?", session);
            if(resultSet.next()){
                return new Staff(resultSet.getLong("id"), resultSet.getString("name"),resultSet.getString("login"));
            }
        }catch (SQLException e){
            System.out.println("Customer for session "+ session + " not found");
        }
        return null;
    }

    public void drop() {
        current = null;
    }
}
