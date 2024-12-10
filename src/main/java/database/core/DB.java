package database.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Обёртка вокруг api JDBC
 */
public class DB {
    private Connection connection;
    final String url = "jdbc:postgresql://localhost:5432/botaholka";
    final String user = "postgres";
    final String password = "1234";
    private final String tableName;

    public DB(String tableName) {
        this.tableName = tableName;
    }

    int executeUpdate(String query) {
        try{
            connection.createStatement().executeUpdate(query);
            return 1;
        } catch (SQLException e){
            System.out.println(e);
            return 0;
        }
    }

    ResultSet executeQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }

    int getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии ДБ");
        }
    }

    void createStateTestTable(){
        getConnection();
        executeUpdate("drop table test;");
        String customerTableQuery = "CREATE TABLE test " +
                "(id SERIAL, state TEXT, chat_id INT primary key)";
        String customerEntryQuery = "INSERT INTO test(chat_id, state) " +
                "VALUES (123, 'test')";
        executeUpdate(customerTableQuery);
        executeUpdate(customerEntryQuery);
        closeConnection();
    }

    void createReviewTestTable(){
        getConnection();
        executeUpdate("drop table test;");
        String customerTableQuery = "CREATE TABLE test " +
                "(id SERIAL primary key,chat_id INT, rating_5 INT, text TEXT); ";
        String customerEntryQuery = "INSERT INTO test(chat_id, rating_5, text) " +
                "VALUES (123, 4, 'test')";
        executeUpdate(customerTableQuery);
        executeUpdate(customerEntryQuery);
        closeConnection();
    }

    void createHistoryTestTable() {
        getConnection();
        executeUpdate("drop table test;");
        String customerTableQuery = "CREATE TABLE test " +
                "(id SERIAL primary key, chat_id INT, order_id INT, items TEXT, sum INT); ";
        String customerEntryQuery = "INSERT INTO test(chat_id, order_id, items, sum) " +
                "VALUES (123, 4, 'test..', 90)";
        executeUpdate(customerTableQuery);
        executeUpdate(customerEntryQuery);
        closeConnection();
    }
    public String getTableName() {
        return tableName;
    }


}
