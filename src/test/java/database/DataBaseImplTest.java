package database;

import fsm.core.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты для запросов к базе данных
 */
public class DataBaseImplTest {
    DataBaseImpl db = new DataBaseImpl("test");

    private void createCustomerTable(){
        db.getConnection();
        db.executeUpdate("drop table test;");
        String customerTableQuery = "CREATE TABLE test " +
                "(id SERIAL, state TEXT, chat_id INT primary key)";
        String customerEntryQuery = "INSERT INTO test(chat_id, state) " +
                "VALUES (123, 'test')";
        db.executeUpdate(customerTableQuery);
        db.executeUpdate(customerEntryQuery);
        db.closeConnection();
    }

    /**
     * Тест на получение правильного состояния из базы данных
     */
    @Test
    void getTest(){
        createCustomerTable();
        State actual = db.getState(123L);
        Assertions.assertEquals(new State("test"), actual);
        actual = db.getState(1234L);
        Assertions.assertEquals(new State("start"), actual);

    }

    /**
     * Тест на корректное изменения состояния в базе данных
     */
    @Test
    void setTest(){
        createCustomerTable();
        int queryResponse = db.setState(123L, new State("StateTest"));
        Assertions.assertEquals(1, queryResponse);
        Assertions.assertEquals(new State("StateTest"), db.getState(123L));
    }
}
