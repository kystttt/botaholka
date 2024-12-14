package database.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Обёртка вокруг api JDBC
 */
public class DB {
    final String url;// = "jdbc:postgresql://localhost:5432/botaholka";
    final String user;// = "postgres";
    final String password;// = "1234";

    public DB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void executeUpdate(String query) throws SQLException {
        Connection connection = getConnection();
        connection.createStatement().executeUpdate(query);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = getConnection();
        return connection.createStatement().executeQuery(query);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

//    void createStateTestTable(){
//        getConnection();
//        executeUpdate("drop table test;");
//        String customerTableQuery = "CREATE TABLE test " +
//                "(id SERIAL, state TEXT, chat_id INT primary key)";
//        String customerEntryQuery = "INSERT INTO test(chat_id, state) " +
//                "VALUES (123, 'test')";
//        executeUpdate(customerTableQuery);
//        executeUpdate(customerEntryQuery);
//        closeConnection();
//    }
//
//
}
