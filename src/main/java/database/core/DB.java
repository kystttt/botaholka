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
//    void createReviewTestTable(){
//        getConnection();
//        executeUpdate("drop table test;");
//        String customerTableQuery = "CREATE TABLE test " +
//                "(id SERIAL primary key,chat_id INT, rating_5 INT, text TEXT); ";
//        String customerEntryQuery = "INSERT INTO test(chat_id, rating_5, text) " +
//                "VALUES (123, 4, 'test')";
//        executeUpdate(customerTableQuery);
//        executeUpdate(customerEntryQuery);
//        closeConnection();
//    }
//
//    void createHistoryTestTable() {
//        getConnection();
//        executeUpdate("drop table test;");
//        String customerTableQuery = "CREATE TABLE test " +
//                "(id SERIAL primary key, chat_id INT, order_id INT, items TEXT, sum INT); ";
//        String customerEntryQuery = "INSERT INTO test(chat_id, order_id, items, sum) " +
//                "VALUES (123, 4, 'test..', 90)";
//        executeUpdate(customerTableQuery);
//        executeUpdate(customerEntryQuery);
//        closeConnection();
//    }
}
