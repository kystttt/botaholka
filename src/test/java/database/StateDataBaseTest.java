package database;

import fsm.core.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты для запросов к базе данных
 */
public class StateDataBaseTest {
    StateDataBase stateDataBase = new StateDataBase("test");

    /**
     * Тест на получение правильного состояния из базы данных
     */
    @Test
    void getTest(){
        stateDataBase.createCustomerTable();
        State actual = stateDataBase.get(123L);
        Assertions.assertEquals(new State("test"), actual);
        actual = stateDataBase.get(1234L);
        Assertions.assertEquals(new State("start"), actual);

    }

    /**
     * Тест на корректное изменения состояния в базе данных
     */
    @Test
    void setTest(){
        stateDataBase.createCustomerTable();
        int queryResponse = stateDataBase.set(123L, new State("StateTest"));
        Assertions.assertEquals(1, queryResponse);
        Assertions.assertEquals(new State("StateTest"), stateDataBase.get(123L));
    }
}
