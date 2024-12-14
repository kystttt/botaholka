package database;

import database.core.DB;
import database.core.StateDAO;
import fsm.core.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Тесты для StateDAO
 */
public class StateDAOTest {
    private final StateDAO stateDAO;

    public StateDAOTest(){
        DB db = new DB("jdbc:h2:mem:test", "sa", "");
        stateDAO = new StateDAO(db);
    }

    /**
     * Создание таблицы users
     */
    private void createStateTable(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");

            String tableQuery = "CREATE TABLE users " +
                    "(id SERIAL, state TEXT, chat_id INT primary key);";

            connection.createStatement().executeUpdate(tableQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу/вставить данные в таблицу\n" + e);
        }
    }

    /**
     * Удаление таблицы users
     */
    private void deleteTable() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");
            connection.createStatement().executeUpdate("drop table users;");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицы\n" + e);
        }
    }

    /**
     * Добавление строки(chatId, stateName) в таблицу users
     * @param chatId id пользователя
     * @param state Состояние пользователя
     */
    private void insertInTable(Long chatId, State state) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");
            String customerEntryQuery = "INSERT INTO users(chat_id, state) " +
                    "VALUES (" + chatId + ", '" + state.name() + "');";
            connection.createStatement().executeUpdate(customerEntryQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось вставить данные в таблицу\n" + e);
        }
    }

    /**
     * Проверка получения статуса из таблицы users
     */
    @Test
    public void getStateTest(){
        createStateTable();
        insertInTable(123L, new State("test"));

        State actual = stateDAO.getState(123L);
        State expected = new State("test");

        Assertions.assertEquals(expected, actual);
        Assertions.assertNull(stateDAO.getState(1L));

        deleteTable();
    }

    /**
     * Проверка добавления пользователя в users
     */
    @Test
    public void addUserTest(){
        createStateTable();
        stateDAO.addUser(123L);
        Assertions.assertEquals(new State("start"), stateDAO.getState(123L));
        deleteTable();
    }

    /**
     * Проверка обновления состояния пользователя в таблице users
     */
    @Test
    public void updateStateTest(){
        createStateTable();
        insertInTable(12L, new State("test"));
        stateDAO.updateState(12L, new State("newState"));
        Assertions.assertEquals(new State("newState"),
                stateDAO.getState(12L));
        deleteTable();
    }
}
