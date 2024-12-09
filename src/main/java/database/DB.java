package database;

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

    void createCustomerTable(){
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

    public String getTableName() {
        return tableName;
    }
}
