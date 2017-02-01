package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnector {

    private static final Logger logger = LoggerFactory.getLogger(DbConnector.class);


    private static final String URL = "jdbc:h2:file:./data/BusDB;AUTO_SERVER=TRUE";
//    private static final String URL = "jdbc:h2:file:./BusDB;AUTO_SERVER=TRUE";
//    private static final String URL = "jdbc:h2:!(tcp://)[file:]{path}[;<;,user={user:param},password={password:param},{:identifier}={:param}>]";

    public static ResultSet fetchRows(String query, Object... args) throws SQLException {
        JdbcConnectionPool pool = null;
        try {
            pool = getConnectionPool();
            Connection connection = pool.getConnection();
            PreparedStatement statement = prepare(connection, query, args);
            return statement.executeQuery();
        } catch (JdbcSQLException e){
            logger.error(e.getMessage());
        }finally {
            if (pool != null) {
                pool.dispose();
            }
        }
        return null;
    }

    public static int updateRows(String query, Object... args) throws SQLException {
        JdbcConnectionPool pool = null;
        try {
            pool = getConnectionPool();
            PreparedStatement statement = prepare(pool.getConnection(), query, args);
            return statement.executeUpdate();
        }catch (JdbcSQLException e){
            logger.error(e.getMessage());
        }finally {
            if (pool != null) {
                pool.dispose();
            }
        }
        return 0;
    }

    private static PreparedStatement prepare(Connection connection, String query, Object... args) throws SQLException{

        PreparedStatement statement = connection.prepareStatement(query);
        if(args.length > 0){
            for(int i = 0; i< args.length; i++){
                if(args[i] instanceof String){
                    statement.setString(i+1, (String) args[i]);
                }else{
                    statement.setObject(i+1, args[i]);
                }
            }
        }
        return statement;
    }

    public static boolean query(String... query) {
        boolean res = true;
        JdbcConnectionPool pool = null;
        try {
            pool = getConnectionPool();
            for (int i = 0; i < query.length; i++) {
                Connection connection = pool.getConnection();
                res = res ^ connection.prepareStatement(query[i]).execute();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            if(pool != null){
                pool.dispose();
            }
        }
        return res;
    }

    public static void createTables() throws SQLException {
        DbConnector.updateRows("CREATE TABLE IF NOT EXISTS staff(id IDENTITY, name VARCHAR, login VARCHAR, pwd VARCHAR, sess VARCHAR);");
        DbConnector.updateRows("CREATE TABLE IF NOT EXISTS bus(id IDENTITY, num VARCHAR unique, sits NUMBER);");
        DbConnector.updateRows("CREATE TABLE IF NOT EXISTS passenger(id IDENTITY, fname VARCHAR, lname VARCHAR, birth DATE);");
        DbConnector.updateRows("CREATE TABLE IF NOT EXISTS station(id IDENTITY, title VARCHAR unique);");
        DbConnector.updateRows("CREATE TABLE IF NOT EXISTS ticket(id IDENTITY, bus_id INT, passenger_id INT, root_id INT, dt DATETIME);");
        DbConnector.updateRows("CREATE TABLE IF NOT EXISTS route(id IDENTITY, bus_id INT, sort INT, station_id INT, duration INT);");
        DbConnector.updateRows("DELETE FROM staff WHERE TRUE;");
        DbConnector.updateRows("INSERT INTO staff(name, login, pwd) VALUES('Oleg', 'oleg', 'oleg123'),('Igor', 'igor', 'igor123'),('Anna', 'anna', 'anna123')");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(URL, "root",
                        "root");
    }

    public static JdbcConnectionPool getConnectionPool() throws SQLException {
        return JdbcConnectionPool
                .create(URL, "root", "root");
    }
}
