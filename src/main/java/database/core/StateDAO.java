package database.core;

import fsm.core.State;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Управление таблицей пользователей
 */
public class StateDAO {
    private final DB db;
    private final String tableName = "users";

    public StateDAO(DB db) {
        this.db = db;
    }

    /**
     * Получение состояния пользователя из таблицы пользователей
     * @param chatId id пользователя
     * @return null - если поле состояния пусто,
     * State - состояние пользователя
     */
    public State getState(Long chatId) {
        String query = "select state " +
                "from " + tableName +
                " where chat_id = " + chatId + ";";
        try {
            ResultSet resultSet = db.executeQuery(query);
            resultSet.next();
            String stateName = resultSet.getString("state");
            return new State(stateName);
        } catch (SQLException e) {
            System.out.println("Не удалось получить состояние пользователя из таблицы users\n" + e);
            return null;
        }
    }

    /**
     * Добавляет пользователя в таблицу пользователей
     */
    public void addUser(Long chatId){
        String query = "insert into " + tableName +
                "(chat_id, state) values (" +
                chatId + ", 'start');";
        try {
            db.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Не удалось добавить пользователя в таблицу users\n" + e);
        }

    }

    /**
     * Обновляет состояние пользователя по его id
     * @param chatId id пользователя
     * @param newState Новое состояние
     * @return (true) - обновление прошло успешно,
     * (false) - не успешно
     */
    public boolean updateState(Long chatId, State newState) {
        String query = "update " + tableName +
                " set state = '" + newState.name() + "'" +
                " where chat_id = " + chatId + ";";

        try {
            db.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Не удалось обновить состояние пользователя в таблице пользователей\n" + e);
            return false;
        }
    }
}
