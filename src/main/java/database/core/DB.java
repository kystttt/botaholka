package database.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Обёртка вокруг api JDBC
 */
public class DB {
    final String url;
    final String user;
    final String password;

    /**
     * Конструктор для DB
     * @param url Ссылка на подключение БД
     * @param user Имя пользователя при подключении БД
     * @param password Пароль пользователя при подключении БД
     */
    public DB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        initializeDatabase();
    }

    /**
     * Выполняет запрос к таблице, который ее изменяет
     */
    public void executeUpdate(String query) throws SQLException {
        Connection connection = getConnection();
        connection.createStatement().executeUpdate(query);
    }

    /**
     * Выполняет запрос к таблице, и возвращает данные из неё
     */
    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = getConnection();
        return connection.createStatement().executeQuery(query);
    }

    /**
     * Устанавливает соединение с БД
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Создает таблицы в базе данных, если их нет
     */
    private void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users " +
                "(id SERIAL, chat_id INT primary key, state TEXT, ofset INT)";

        String createHistoryTable = "CREATE TABLE IF NOT EXISTS history " +
                "(id SERIAL primary key, chat_id INT, order_id INT, items TEXT, sum INT); ";

        String createReviewsTable = "CREATE TABLE IF NOT EXISTS reviews " +
                "(id SERIAL primary key,chat_id INT, rating_5 INT, text TEXT); ";

        try {
            executeUpdate(createUsersTable);
            executeUpdate(createHistoryTable);
            executeUpdate(createReviewsTable);
            System.out.println("Таблицы успешно созданы или уже существуют.");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблиц: " + e.getMessage());
        }
    }
}
