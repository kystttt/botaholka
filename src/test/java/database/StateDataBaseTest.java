package database;

import database.core.StateDataBase;
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
        stateDataBase.createTestTable();
        State actual = stateDataBase.get(123L).getFirst();
        Assertions.assertEquals(new State("test"), actual);
        actual = stateDataBase.get(1234L).getFirst();
        Assertions.assertEquals(new State("start"), actual);

    }

    /**
     * Тест на корректное изменения состояния в базе данных
     */
    @Test
    void setTest(){
        stateDataBase.createTestTable();
        int queryResponse = stateDataBase.set(123L, new State("StateTest"));
        Assertions.assertEquals(1, queryResponse);
        Assertions.assertEquals(new State("StateTest"), stateDataBase.get(123L).getFirst());
    }
}
