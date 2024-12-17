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


}
